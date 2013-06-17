function winpage1(){
	wx_winpage('/CloudCarManage/rolepermission1.jsp',{width:500,lock:true});
}
function winpage2(roleid){
	wx_winpage('/CloudCarManage/rolepermission2.jsp?roleid='+roleid,{width:620,height:410,lock:true});
}