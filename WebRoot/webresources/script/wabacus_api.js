/*************************************************************
 * 文件说明：
 *		这里所有全局变量和函数都可以供客户端调用。
 *************************************************************/

 /**
  * 定义可编辑报表的访问模式
  */
 var WX_ACCESSMODE_READ='read';//浏览模式，只对editabledetail报表类型有效
 
 var WX_ACCESSMODE_ADD='add';//添加模式，对editabledetail和form报表类型有效
 
 var WX_ACCESSMODE_UPDATE='update';//修改模式，对editabledetail和form报表类型有效
 
 var WX_ACCESSMODE_READONLY='readonly';//只读模式，对所有可编辑报表类型有效
 
 /**
  * 定义可编辑报表的保存动作类型
  */
 var WX_SAVETYPE_SAVE='save';//保存操作
 
 var WX_SAVETYPE_DELETE='delete';//删除操作

/**
 * 存放同一页面中所有数据自动列表报表/表单中被选中的行对象
 * 此对象为一类似JAVA中的Map<reportGuid,Map<trid,trObj>>对象，其中reportGuid为报表的guid，Map<trid,trObj>为此报表所有被选中的<tr/>对象，以<tr/>的id为键，<tr/>对象为值。
 * 因此要获取某个报表所有被选中的行对象时，只需根据这个报表的<report/>的guid得到被选中行的<tr/>对象集合，循环它就可以得到所有被选中行的对象
 * 如果被选中的行对象是可编辑数据自动列表报表新增的<tr/>，则trObj.getAttribute('EDIT_TYPE')=='add'
 * 一般在行选中回调函数中可能会需要取用这些被选中行对象
 * 下面getAllSelectedTrObjs(pageid,reportid)方法即为获取某个报表/表单所有选中行对象
 */
var WX_selectedTrObjs;

/**
 * 刷新某个组件的显示
 * @param pageid 页面ID
 * @param componentid 要刷新的组件，如果为空或为pageid，则刷新整个页面的显示
 * @param isReset 是否重置被刷新组件的状态
 */
function refreshComponentDisplay(pageid,componentid,isReset)
{
	var componentguid=getComponentGuidById(pageid,componentid);
	var cmetaDataObj=getReportMetadataObj(componentguid);//虽然可能不是报表，但以报表的形式获取，方便后面取从报表ID
	if(cmetaDataObj==null) return;
	var url=getComponentUrl(pageid,cmetaDataObj.refreshComponentGuid,cmetaDataObj.slave_reportid);
	if(url==null||url=='') return;
	if(isReset===true)
	{
		url=resetComponentUrl(pageid,componentid,url,null);
	}
	refreshComponent(url);
}

/**
 *所有输入框的onKeyPress事件
 */
function onInputBoxKeyPress(event)
{
 	var intKey=-1;
	var srcObj;
	if(window.event)
	{
		intKey=window.event.keyCode;
		srcObj=window.event.srcElement;
	}else
	{
		intKey=event.which;
		srcObj=event.target;
	}
	if(intKey==13)
	{
		var frmObj=getParentElementObj(srcObj,'FORM');
		if(frmObj!=null)
		{
			var idx=0;
			for(;idx<frmObj.elements.length;idx++)
			{
				if(frmObj.elements[idx]==srcObj)
				  break;
			}
			if(idx==frmObj.elements.length) return false;
			var nextObj;
			var times=0;
			while(true)
			{//将焦点转到当前输入框的下一个输入框中
			   idx=(idx+1)%frmObj.elements.length;
				nextObj=frmObj.elements[idx];
				if(++times==frmObj.elements.length||nextObj==srcObj) break;//又回到了最开始的输入框，则跳出
				if(!nextObj||nextObj.disabled)
				{//当前输入框是禁用状态，则取下一个
					continue;
				}
				nextObj.focus();
				break;
			}
		}
		return false;
	}else
 	{
 		return true;
 	}
}


/**
 * 获取某个报表所有选中行的<tr/>对象
 * @param pageid 报表所在页面ID
 * @param reportid 报表id
 * @return 返回本报表被选中行的对象数组，且按行号从小到大排好序
 */
function getAllSelectedTrObjs(pageid,reportid)
{
	if(WX_selectedTrObjs==null) return null; 
	var reportguid=getComponentGuidById(pageid,reportid);
	var selectTrObjs=WX_selectedTrObjs[reportguid];
	if(selectTrObjs==null) return null;
	var tridPrex=null;
	var maxRownum=-1;//存放所有选中的行中最大的行号
	var idxTmp;
	for(var key in selectTrObjs)
	{
		idxTmp=key.lastIndexOf('_tr_');
		if(idxTmp<0) continue;
		var myrownum=parseInt(key.substring(idxTmp+'_tr_'.length),10);//取到本tr的行号
		if(myrownum>maxRownum) maxRownum=myrownum;
		if(tridPrex==null) tridPrex=key.substring(0,idxTmp+'_tr_'.length);
	}
	var resultsArr=new Array();
	if(maxRownum<0)
	{//如果没有取到最大行号，可能没有选中行，即selectTrObjs为空，或者<tr/>的id不合法
		for(var key in selectTrObjs)
		{
			resultsArr[resultsArr.length]=selectTrObjs[key];
		}
	}else
	{
		for(var i=0;i<=maxRownum;i++)
		{//将选中行根据行号从小到大放入结果集中
			if(selectTrObjs[tridPrex+i]!=null) resultsArr[resultsArr.length]=selectTrObjs[tridPrex+i];
		}
	}
	return resultsArr;
}

/**
 * 判断某行是否被选中
 */
function isSelectedRow(trObj)
{
	if(WX_selectedTrObjs==null) return false;
	if(trObj==null||!isListReportDataTrObj(trObj)) return false;
	var trid=trObj.getAttribute('id');
	var reportguid=trid.substring(0,trid.lastIndexOf('_tr_'));//报表的guid
  	if(reportguid=='') return false;
	var allSelectedTrObjs=WX_selectedTrObjs[reportguid];
	if(allSelectedTrObjs==null||allSelectedTrObjs[trid]==null) return false;
	return true;
}

/**
 * 选中行对象
 * @param trObj 被选中的<tr/>对象
 * @param shouldInvokeOnloadMethod 是否需要执行行选中的回调函数（包括配置的或框架自动生成的，比如刷新从报表等），默认为不执行，传入true则执行
 */
function selectReportDataRow(trObj,shouldInvokeOnloadMethod)
{
	if(trObj==null) return;
	var trid=trObj.getAttribute("id");
	if(trid==null||trid=='') return;
  	var reportguid=trid.substring(0,trid.lastIndexOf('_tr_'));//报表的guid
  	if(reportguid=='') return;
  	var rowselecttype=getRowSelectType(reportguid);
  	if(rowselecttype!='checkbox'&&rowselecttype!='radiobox'&&rowselecttype!='single'&&rowselecttype!='multiply') return;
  	WX_selectedTrObj_temp=trObj;
  	WX_shouldInvokeOnloadMethod_temp=shouldInvokeOnloadMethod;
  	if(isHasIgnoreSlaveReportsSavingData(reportguid))
  	{
  		wx_confirm('本操作可能会丢失对从报表数据的修改，是否继续？',null,null,null,doSelectReportDataRowImpl);
  	}else
  	{
  		doSelectReportDataRowImpl('ok');
  	}
}

/**
 * 取消某行的选中状态
 */
function deselectReportDataRow(trObj,shouldInvokeOnloadMethod)
{
	if(trObj==null||!isSelectedRow(trObj)) return;
	var trid=trObj.getAttribute("id");
	if(trid==null||trid=='') return;
  	var reportguid=trid.substring(0,trid.lastIndexOf('_tr_'));//报表的guid
  	if(reportguid=='') return;
  	var rowselecttype=getRowSelectType(reportguid);
  	if(rowselecttype!='checkbox'&&rowselecttype!='radiobox'&&rowselecttype!='single'&&rowselecttype!='multiply') return;
  	WX_selectedTrObj_temp=trObj;
  	WX_shouldInvokeOnloadMethod_temp=shouldInvokeOnloadMethod;
  	if(isHasIgnoreSlaveReportsSavingData(reportguid))
  	{
  		wx_confirm('本操作可能会丢失对从报表数据的修改，是否继续？',null,null,null,doDeselectReportDataRowImpl);
  	}else
  	{
  		doDeselectReportDataRowImpl('ok');
  	}  	
}

/*******************************************************************************
 *保存本次进行服务器端增删改操作的数据，可以在保存后的回调函数中aftersave访问到
 *WX_ALL_SAVEING_DATA为一Map对象，键为当前保存的所有报表guid（因为可能绑定保存多个报表），
 *                              值为相应报表保存的所有数据，它为一个Array对象，数组中每个Object对象存放保存的一行记录，在Object中以参数名为键，参数值为值
 *		对于可编辑细览报表类型，此Array对象绝大多数情况下只有一个Object对象，因为这种报表类型一次只能增、删、改一条记录。
 *      对于数据自动列表报表，则可能有多个Object对象，因为它可以同时增、删、改多条记录。							
 *      如果某个报表在保存时存在用户自己添加到WX_CUSTOMIZE_DATAS中准备保存的数据，则它们存放在一个单独的Object对象中，放在数组Array对象中。
 *			此时如果是细览报表，存放其保存值的Array对象也有两个元素了。
 *		存放每条记录的Object对象都有一个特殊的键WX_TYPE,它的值为add、update、delete、customize之一，前面三个是表示对当前记录进行哪种操作，
 *          后面的customize表示当前记录是存放所有用户自定义的保存参数值，如果是EditableDetailReportType及其子类型（比如普通表单）的用户自定义保存数据，
 *                                                                    则WX_TYPE值为customize.add、customize.update、customize.delete
 ******************************************************************************/
var WX_ALL_SAVEING_DATA;

/**
 * @param dynDefaultValues：添加当前新行时某个或某些列的默认值，对于可编辑列表报表类型的下拉框/单选框，还可以指定默认值在<td/>中的显示label（因为它们是点击时才会显示输入框，所以要先显示默认值对应的label）。
 *									指定方式为json字符串，格式如下所示:
 *										{col1:value1,col1$label:label1,col3:value3,...}，
 *										其中col1、col3为相应列的column属性配置值，对于可编辑列表报表的下拉框或单选框，如果要指定某个列默认值对应的显示值，则通过键：column$label进行指定
 *			关于添加新记录方法的更多介绍，请参看《常用接口方法》相关介绍
 */
function addListReportNewDataRow(pageid,reportid,dynDefaultValues)
{
	var reportguid=getComponentGuidById(pageid,reportid);
	addNewDataRow(pageid,reportguid,dynDefaultValues);
}

/**
 * 设置一个或多个输入框的值（对任意可编辑报表类型及查询条件输入框均有效）
 * @param isConditionbox 要设置值的输入框是否是查询条件输入框，true：是查询条件输入框；false：可编辑报表编辑输入框
 * @param newvaluesObj 传入所有要设置新值的输入框及相应的新值，通过json组织，格式为：{name1:"newvalue1",name2:"newvalue2",...}，其中的name分两种情况：
 *								如果当前是设置查询条件输入框的值，则其中的name1、name2等分别为输入框所在查询条件<condition/>的name属性；
 *								如果当前是设置可编辑报表列上的输入框的值，则其中的name1、name2等分别为要设置新值输入框所在列的<col/>的column属性。
 *							newvalue1、newvalue2等为为它们设置的新值
 * @param conditionsObj 此参数只对设置editablelist2/listform两种报表类型的编辑列上输入框值有效，用于定位哪些行上相应输入框的值，如果没指定此条件，则设置所有记录行上这些列的值。
 *             此参数也是通过json方式组织，如果只有一个条件，则格式为{name:"column1",value:"value1",matchmode:"equals"}或者为[{name:"column1",value:"value1",matchmode:"equals"}]，
 *												   如果有多个条件，则格式为：[{name:"column1",value:"value1",matchmode:"equals"},{name:"column2",oldvalue:"value2",matchmode:"include"},...]
 *					关于此方法的更多使用说明，请参看《常用接口方法》相关介绍
 */
function setReportInputBoxValue(pageid,reportid,isConditionbox,newvaluesObj,conditionsObj)
{
	var reportguid=getComponentGuidById(pageid,reportid);
	if(isConditionbox)
	{//当前是设置查询条件输入框的值
		setConditionInputBoxValue(reportguid,newvaluesObj);
	}else
	{//编辑输入框
		var metadataObj=getReportMetadataObj(reportguid);
		if(metadataObj.reportfamily==ReportFamily.EDITABLELIST2||metadataObj.reportfamily==ReportFamily.LISTFORM)
		{
			setEditableListReportColValue(reportguid,newvaluesObj,conditionsObj,false);
		}else if(metadataObj.reportfamily==ReportFamily.EDITABLEDETAIL2)
		{
			setEditableDetail2ReportColValue(reportguid,newvaluesObj,false);
		}else if(metadataObj.reportfamily==ReportFamily.EDITABLEDETAIL||metadataObj.reportfamily==ReportFamily.FORM)
		{
			setEditableDetailReportColValue(reportguid,newvaluesObj,false);
		}
	}
}

/**
 * 设置一个或多个编辑列上的值，对所有可编辑报表类型都有效
 * 各参数意义与上面setReportInputBoxValue()方法完全相同
 */
function setEditableReportColValue(pageid,reportid,newvaluesObj,conditionsObj)
{
	var reportguid=getComponentGuidById(pageid,reportid);
	var metadataObj=getReportMetadataObj(reportguid);
	if(metadataObj.reportfamily==ReportFamily.EDITABLELIST2||metadataObj.reportfamily==ReportFamily.LISTFORM)
	{
		setEditableListReportColValue(reportguid,newvaluesObj,conditionsObj,true);
	}else if(metadataObj.reportfamily==ReportFamily.EDITABLEDETAIL2)
	{
		setEditableDetail2ReportColValue(reportguid,newvaluesObj,true);
	}else if(metadataObj.reportfamily==ReportFamily.EDITABLEDETAIL||metadataObj.reportfamily==ReportFamily.FORM)
	{
		setEditableDetailReportColValue(reportguid,newvaluesObj,true);
	}
}

/**
 * 设置查询条件输入框的值
 * @param paramname 要设置新值的查询条件<condition/>的name属性值
 * @param paramvalue 为此查询条件输入框设置的新值
 */
function setInputboxValueForCondition(pageid,reportid,paramname,paramvalue)
{
	if(paramname==null||paramname=='') return;
	if(paramvalue==null) paramvalue='';
	var reportguid=getComponentGuidById(pageid,reportid);
	var newvalue='{'+paramname+':"'+jsonParamEncode(paramvalue)+'"}';
	setConditionInputBoxValue(reportguid,getObjectByJsonString(newvalue));
}

/**
 * 设置可编辑数据细览报表及普通表单某列输入框的值（包括edtaibledetail/form，但不包括editabledetail2）
 * @param paramname 列<col/>的column属性配置值
 * @param paramvalue 为此列输入框设置的新值
 */
function setInputboxValueForDetailReport(pageid,reportid,paramname,paramvalue)
{
	if(paramname==null||paramname=='') return;
	if(paramvalue==null) paramvalue='';
	var reportguid=getComponentGuidById(pageid,reportid);
	var newvalue='{'+paramname+':"'+jsonParamEncode(paramvalue)+'"}';
	setEditableDetailReportColValue(reportguid,getObjectByJsonString(newvalue),false);
}

/**
 * 设置可编辑数据自动列表报表editablelist2和列表表单listform中某行上的列值
 * @param trObj 要设置列值的行对象
 * @param newvaluesObj 要设置的列的column及相应的新值，以json字符串组织，与上面的setReportInputBoxValue()方法中的newvaluesObj格式一致，
 *						  这里不用指定条件参数，因为已经传入了要设置值的<tr/>对象了 
 */
function setEditableListReportColValueInRow(pageid,reportid,trObj,newvaluesObj)
{
	var reportguid=getComponentGuidById(pageid,reportid);
	if(!isEditableListReportTr(reportguid,trObj)) return;
	setEditableListReportColValueInRowImpl(reportguid,trObj,newvaluesObj,true);
}

/**
 * 获取可编辑报表的各列新旧数据
 * @param columnsObj 指定要获取列数据的<col/>的column集合，指定格式为：{column1:true,column2:true,......}，其中column1、column2等为要获取数据的<col/>的column属性值
 *						如果没有指定，或指定为null，则取所有列数据
 *	@return	如果是可编辑细览报表，则返回一个Object对象，里面存放所有需要获取的列数据对象，它有如下三个属性column:存放<col/>的column属性配置值；value存放当前列的现在数据；oldvalue存放当前列的原始数据
 *				如果是可编辑数据列表报表或列表表单，则返回Array对象，里面每个元素是对应一行记录的数据对象，数据结构与上面细览报表的Object对象一致
 * 其它参数的意义与上面setEditableReportColValue()方法中相应参数相同
 */
function getEditableReportColValues(pageid,reportid,columnsObj,conditionsObj)
{
	var reportguid=getComponentGuidById(pageid,reportid);
	var metadataObj=getReportMetadataObj(reportguid);
	if(metadataObj.reportfamily==ReportFamily.EDITABLELIST||
		metadataObj.reportfamily==ReportFamily.EDITABLELIST2||
		metadataObj.reportfamily==ReportFamily.LISTFORM)
	{
		return getEditableListReportColValues(pageid,reportguid,columnsObj,conditionsObj);
	}else if(metadataObj.reportfamily==ReportFamily.EDITABLEDETAIL2)
	{
		return getEditableDetailReportColValues(pageid,reportguid,columnsObj,true);
	}else if(metadataObj.reportfamily==ReportFamily.EDITABLEDETAIL||metadataObj.reportfamily==ReportFamily.FORM)
	{
		return getEditableDetailReportColValues(pageid,reportguid,columnsObj,false);
	}
	return null;
}

/**
 * 获取指定行上指定列的数据
 * @param columnsObj 与上页接口方法中columnsObj完全一样，如果值为null，则获取此行所有列的数据
 */
function getEditableListReportColValuesInRow(trObj,columnsObj)
{
	return getAllColValueByParentElementObjs(trObj.getElementsByTagName('TD'),columnsObj);
}

/**
 * 保存一个或多个可编辑报表的数据
 * paramsObj结构为
 *		pageid:"pageid",
 *		savingReportIds:[{reportid:"reportid1",reporttype:"reportype1",updatetype:"save"},{reportid:"reportid2",reporttype:"reportype2",updatetype:"delete|all"},...]
 *		updatetype有save、delete两种取值，对于editablelist2/listform两种数据自动列表报表，在delete时后面可以跟上|all表示删除本页面上此报表所有数据，如果没指定，则只删除选中的行。
 */
function saveEditableReportData(paramsObj)
{
	//stopSaveForDemo();return;
	saveEditableReportDataImpl(paramsObj);
}

/**
 * 删除可编辑数据自动列表报表的数据行
 * @param trObjArray 被删除的行对象或行对象数组，被删除的行可以是已有的行，也可以是新增的行
 */
function deleteEditableListReportRows(pageid,reportid,trObjArray)
{
	//stopSaveForDemo();return;
	if(trObjArray==null) return;
	var reportguid=getComponentGuidById(pageid,reportid);
	deleteEditableListReportRowsImpl(reportguid,convertToArray(trObjArray));
}

/**
 * 针对某个报表调用服务器端的JAVA类进行处理，在调用JAVA类时会传入此报表的数据
 *
 * @param pageid 要操作的报表所在页面<page/>的id属性，必需参数
 * @param reportid 要操作的报表ID，必需参数
 * @param serverClassName 要调用的服务器端类的全限定类名，必需参数
 * @param shouldRefreshPage 操作完报表数据后是否要刷新报表显示，可选参数，默认值为true
 * @param conditionsObj 对于editablelist2/listform两种报表类型，指定要传入哪些行的数据到服务器端类，指定方式与上面获取和设置这两种报表类型列数据时的conditionsObj参数完全一致，可选参数
 * @param paramsObj 以json方式指定不是报表数据的其它数据，在被调用的JAVA类的方法中会有一个专门的参数存放这个JS参数传入的值
 * @param beforeCallbackMethod 在取到报表数据后准备传入后台调用前执行，可以在此方法中取到报表数据，并可中止调用的执行
 * @param afterCallbackMethod 调用完服务器端类后要执行的客户端回调函数
 */
function invokeServerActionForReportData(pageid,reportid,serverClassName,conditionsObj,paramsObj,shouldRefreshPage,beforeCallbackMethod,afterCallbackMethod)
{
	if(pageid==null||pageid=='')
	{
		wx_warn('pageid参数不能为空，必须指定页面ID');
		return;
	}
	if(reportid==null||reportid=='')
	{
		wx_warn('reportid参数不能为空，必须指定报表ID');
		return;
	}
	if(serverClassName==null||serverClassName=='')
	{
		wx_warn('serverClassName参数不能为空，必须指定要调用的服务器端类的全限定类名');
		return;
	}
	if(shouldRefreshPage==null) shouldRefreshPage=true;
	invokeServerActionForReportDataImpl(pageid,reportid,serverClassName,conditionsObj,paramsObj,shouldRefreshPage,beforeCallbackMethod,afterCallbackMethod);
}

/**
 * 针对页面或页面上某个组件调用服务器端的操作，并传入参数。
 * 	这里的传入的组件ID只影响执行时机和刷新页面时刷新范围，即是在哪个组件的初始化方法中调用此服务器端类，以及调用完操作后刷新哪个组件。
 * @param pageid 页面ID
 * @param componentid 当前操作所针对的组件的id，如果为空，则相当于针对整个页面
 * @param datas 调用服务器端方法时传入的数据，可以是一个对象，也可以是一个数组，如果是一个对象，则传入格式为{a1:'b1',a2:'b2',...}，如果是一个对象数组，则格式为[{a1:'b1',a2:'b2',...},{c1:'d1',c2:'d2',...}]
 */
function invokeServerActionForComponent(pageid,componentid,serverClassName,datas,shouldRefreshPage,afterCallbackMethod)
{
	if(pageid==null||pageid=='')
	{
		wx_warn('pageid参数不能为空，必须指定页面ID');
		return;
	}
	if(serverClassName==null||serverClassName=='')
	{
		wx_warn('serverClassName参数不能为空，必须指定要调用的服务器端类的全限定类名');
		return;
	}
	if(shouldRefreshPage==null) shouldRefreshPage=true;
	invokeServerActionForComponentImpl(pageid,componentid,serverClassName,datas,shouldRefreshPage,afterCallbackMethod);
}

/**
 * 调用componentid组件（可以是报表或任意容器等组件）上配置的ServerSQLActionButton按钮事件，传入数据执行其配置的SQL语句
 * 注意：被客户端调用的ServerSQLActionButton按钮配置的shouldRefreshPage和callbackMethod无效，必须通过这里传这两个参数，因为shouldRefreshPage在客户端需要使用，callbackMethod放在这里传比较灵活
 *	@param componentid 被调用的按钮所在组件的ID
 * @param buttonname 被调用按钮对应<button/>标签的name属性
 * @param datas 传给服务器端类的数据
 */
function invokeComponentSqlActionButton(pageid,componentid,buttonname,datas,paramsObj,shouldRefreshPage,afterCallbackMethod)
{
	if(buttonname==null)
	{
		wx_warn('必须指定被调用按钮的name');
		return;
	}
	invokeServerActionForReportData(pageid,componentid,'button{'+buttonname+'}',datas,paramsObj,shouldRefreshPage,null,afterCallbackMethod);
}

/**
 * 本次操作是在客户端向服务器端发起一次普通操作，和某个报表或报表页面没有关系
 * 就是调用一次服务器端serverClassName类的executeServerAction()方法，并传入datas参数
 *
 * @param datas  调用服务器端方法时传入的数据，可以是一个对象，也可以是一个数组，如果是一个对象，则传入格式为{a1:'b1',a2:'b2',...}，如果是一个对象数组，则格式为[{a1:'b1',a2:'b2',...},{c1:'d1',c2:'d2',...}]
 *						注意：这里传的是json数据，因此最外面不能用引号括住
 * @param callbackMethod 服务器端执行完后返回时会调用回调方法，执行时会自动传入服务器端返回的字符串做为参数
 * @param onErrorMethod 调用服务器端方法出错时的处理函数
 */
function invokeServerAction(serverClassName,datas,afterCallbackMethod,onErrorMethod)
{
	if(serverClassName==null||serverClassName=='')
	{
		wx_warn('serverClassName参数不能为空，必须指定要调用的服务器端类的全限定类名');
		return;
	}
	invokeServerActionImpl(serverClassName,datas,afterCallbackMethod,onErrorMethod);
}

/**************************************************
 * 显示/隐藏正在加载页面提示接口方法
 *************************************************/
/**
 * 显示正在加载提示信息
 */
function displayLoadingMessage()
{
	window.status='loading...';
   var imgobj=document.getElementById('LOADING_IMG_ID');
	if(imgobj!=null)
	{
		imgobj.style.display='block';
		var documentSize=getDocumentSize();
		var documentScrollSize=getDocumentScroll();
		imgobj.style.top = (documentSize.height+documentScrollSize.scrollTop-imgobj.clientHeight) + "px";
   	imgobj.style.left = (documentSize.width+documentScrollSize.scrollLeft-imgobj.clientWidth) + "px";
	}
} 

/**
 * 隐藏正在加载提示信息
 */
function hideLoadingMessage()
{
	window.status='';
	var imgobj=document.getElementById('LOADING_IMG_ID');
   if(imgobj!=null) imgobj.style.display='none';
}

function changeEditedInputboxDisplayStyle(editedElementObj)
{
	if(editedElementObj==null) return;
	//editedElementObj.style.backgroundColor='lightblue';
	editedElementObj.style.background='#ffd700 url(webresources/skin/dirty.gif) no-repeat 0 0';
}
/**************************************************
 * 信息提示接口方法
 *************************************************/

/**
 * 通过alert方式提示信息，下面的参数除message外都有默认值，可以不传
 * @param message 提示内容
 * @param title 提示窗口的标题
 *	@param width 提示窗口的宽度
 * @param height 提示窗口的高度
 * @param handler 提示时回调函数 默认值为null
 * @param showMask 是否显示遮罩
 * @param interval 是否自动隐藏，如果指定为>0的数，则指定秒后自动隐藏
 */
//function wx_alert(message,title,width,height,handler,showMask,interval)
function wx_alert(message,paramsObj)
{
	if(paramsObj==null) paramsObj=new Object();
	if(!isExistPromptObj())
	{
		alert(message);
	}else if(WXConfig.prompt_dialog_type=='ymprompt')
	{
		if(message!=null) paramsObj.message=message;
		ymPrompt.alert(paramsObj);
	}else
	{
		if(message!=null) paramsObj.content=message;
		if(paramsObj.lock==null) paramsObj.lock=true;
		art.dialog(paramsObj);
	}
}

/**
 * 通过alert方式提示信息，下面的参数除message外都有默认值，可以不传
 * @param message 提示内容
 * @param title 提示窗口的标题
 *	@param width 提示窗口的宽度
 * @param height 提示窗口的高度
 * @param handler 提示时回调函数 默认值为null
 * @param showMask 是否显示遮罩
 * @param interval 是否自动隐藏，如果指定为>0的数，则指定秒后自动隐藏
 */
//function wx_warn(message,title,width,height,handler,showMask,interval)
function wx_warn(message,paramsObj)
{
	if(paramsObj==null) paramsObj=new Object();
	if(!isExistPromptObj())
	{
		alert(message);
	}else if(WXConfig.prompt_dialog_type=='ymprompt')
	{
		if(message!=null) paramsObj.message=message;
		ymPrompt.alert(paramsObj);
	}else
	{
		if(message!=null) paramsObj.content=message;
		if(paramsObj.lock==null) paramsObj.lock=true;
		if(paramsObj.time==null) paramsObj.time=2;
		if(paramsObj.icon==null) paramsObj.icon='warning';
		art.dialog(paramsObj);
	}
}

/**
 * 提示出错信息
 * 参数意义与wx_alert相同
 */
//function wx_error(message,title,width,height,handler,showMask,interval)
function wx_error(message,paramsObj)
{
	if(paramsObj==null) paramsObj=new Object();
	if(!isExistPromptObj())
	{
		alert(message);
	}else if(WXConfig.prompt_dialog_type=='ymprompt')
	{
		if(message!=null) paramsObj.message=message;
		ymPrompt.errorInfo(paramsObj);
	}else
	{
		if(message!=null) paramsObj.content=message;
		if(paramsObj.lock==null) paramsObj.lock=true;
		if(paramsObj.time==null) paramsObj.time=2;
		if(paramsObj.icon==null) paramsObj.icon='error';
		art.dialog(paramsObj);
	}
}

/**
 * 提示成功信息
 * 参数意义与wx_alert相同
 */
//function wx_success(message,title,width,height,handler,showMask,interval)
function wx_success(message,paramsObj)
{
	if(paramsObj==null) paramsObj=new Object();
	if(!isExistPromptObj())
	{
		alert(message);
	}else if(WXConfig.prompt_dialog_type=='ymprompt')
	{
		if(message!=null) paramsObj.message=message;
		ymPrompt.succeedInfo(paramsObj);
	}else
	{
		if(message!=null) paramsObj.content=message;
		if(paramsObj.lock==null) paramsObj.lock=true;
		if(paramsObj.time==null) paramsObj.time=2;
		if(paramsObj.icon==null) paramsObj.icon='succeed';
		art.dialog(paramsObj);
	}
}

/**
 * 提示确认信息，除handler参数外，其它参数意义与wx_alert相同
 * 在这个方法的handler参数中，可以声明一个参数，比如名字为input，当用户点击提示窗口的“确定”按钮时，会传入'ok'，当用户点击“取消”按钮时，会传入'cancel'
 * 这样用户就可以在handler处理函数中根据用户点击的按钮决定需要做哪种处理工作
 */
var WX_CANCEL_HANDLER;
function wx_confirm(message,title,width,height,okHandler,cancelHandler)
{
	if(!isExistPromptObj()) return;
	if(WXConfig.prompt_dialog_type=='ymprompt')
	{
		WX_CANCEL_HANDLER=cancelHandler;//对于这种提示组件，这里只能传一个函数，用户选择“确定”还是“取消”是由这个函数根据input值决定的，所以放在全局变量中，用户需要的时候可以调用
		ymPrompt.confirmInfo({message:message,title:title,width:width,height:height,handler:okHandler});
	}else
	{
		art.dialog.confirm(message,okHandler,cancelHandler);
	}
}

/**
 * 上面的wx_confirm()方法弹出确认窗口后，用户是否选中了“是”
 */
function wx_isOkConfirm(input)
{
	//alert(input);
	if(WXConfig.prompt_dialog_type=='ymprompt')
	{
		return input=='ok';
	}
	return true;
}

/**
 * 用户点击在wx_confirm()弹出窗口中点击取消时调用此方法可以执行取消事件，只有ymPrompt提示组件需要
 */
function wx_callCancelEvent()
{
	if(WXConfig.prompt_dialog_type=='ymprompt')
	{//只有这种提示组件需要调用，artdialog提示组件在调confirm时为取消事件用一个单独的位置指定，提示组件在取消时会自动调用
		if(WX_CANCEL_HANDLER!=null) WX_CANCEL_HANDLER();
	}
}

/**
 * 以窗口的形式显示提示信息
 * @param maxbtn true：显示窗口最大化按钮；false：不显示窗口最大化按钮
 *	@param minbtn true：显示窗口最小化按钮；false：不显示窗口最小化按钮
 */
//function wx_win(message,title,width,height,maxbtn,minbtn,handler,showMask,interval)
function wx_win(message,paramsObj)
{
	if(!isExistPromptObj()) return;
	if(paramsObj==null) paramsObj=new Object();
	if(WXConfig.prompt_dialog_type=='ymprompt')
	{
		if(message!=null) paramsObj.message=message;
		ymPrompt.win(paramsObj);
	}else
	{
		if(message!=null) paramsObj.content=message;
		if(paramsObj.lock==null) paramsObj.lock=true;
		art.dialog(paramsObj);
	}
}

/**
 * 以窗口形式显示新页面
 * @param url：要显示的新页面URL
 */
//function wx_winpage(url,title,width,height,maxbtn,minbtn,handler)
function wx_winpage(url,paramsObj)
{
	if(!isExistPromptObj()) return;
	if(paramsObj==null) paramsObj=new Object();
	if(WXConfig.prompt_dialog_type=='ymprompt')
	{
		paramsObj.iframe=true;
		if(url!=null) paramsObj.message=url;
		ymPrompt.win(paramsObj);
		if(paramsObj.initsize=='max')
		{//最大化窗口
			ymPrompt.max();
		}else if(paramsObj.initsize=='min')
		{//最小化窗口
			ymPrompt.min();
		}
	}else
	{
		if(paramsObj.lock==null) paramsObj.lock=true;
		if(paramsObj.initsize=='max')
		{//指定了最大化窗口显示
			 paramsObj.width='100%';
		    paramsObj.height='100%';
		    paramsObj.left='0%';
		    paramsObj.top='0%';
		    paramsObj.fixed=true;
		    paramsObj.drag=false;
		}else if(paramsObj.initsize=='min')
		{//最小化窗口
			 paramsObj.width='0';
		    paramsObj.height='0';
		}
		ART_DIALOG_OBJ=art.dialog.open(url,paramsObj);
	}
}
/**
 * 关闭用wx_winpage()弹出的窗口
 */
function closePopupWin(delaytime)
{
	if(!isExistPromptObj()) return;
	if(WXConfig.prompt_dialog_type=='ymprompt')
	{
		if(ymPrompt==null) return;
		ymPrompt.doHandler('close',null);
	}else
	{
		if(ART_DIALOG_OBJ==null) return;
		if(delaytime!=null&&delaytime>0)
		{	
			setTimeout(function(){ART_DIALOG_OBJ.close();},delaytime*1000);
		}else
		{
			ART_DIALOG_OBJ.close();
		}
		//art.dialog.close();
	}
}
/**
 * 判断是否加载了相应类型的提示组件对象
 */
function isExistPromptObj()
{
	if((WXConfig.prompt_dialog_type=='ymprompt'&&(typeof ymPrompt!='undefined'))) return true;
	if(typeof art!='undefined') return true;
	return false;
}

/*********************************************
 * 常用功能函数
 ********************************************/
 
var WX_API_LOADED=true;//用于标识此js文件加载完 