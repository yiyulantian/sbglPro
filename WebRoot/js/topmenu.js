$(document).ready(function(){
	var a=$("#mainMenu a")
	a.each(function(i){
		$(this).click(function(){
			$(".menuSelect").removeClass("menuSelect");
		  	$(this).addClass("menuSelect");
		});
	});
});
//招生咨询
function advisory(path){
	parent.document.getElementById("leftFrame").setAttribute("src", path+"/frameset/left.jsp?bigCatalog=1x");
	parent.document.getElementById("mainFrame").setAttribute("src", path + "/frameset/blankInfo.jsp?serialId="+new Date().toString());
}


//学生管理
function studentmanage(path){
	//alert("进入我的js函数。。。");
	//$("#leftFrame").attr("src", path+"/frameset/leftTreeProject.jsp?serialId="+new Date().toString());
	//$("#mainFrame").attr("src", path + "/frameset/projectInfo.jsp?serialId="+new Date().toString());
	parent.document.getElementById("leftFrame").setAttribute("src", path+"/frameset/left.jsp?bigCatalog=11");
	parent.document.getElementById("mainFrame").setAttribute("src", path + "/frameset/blankInfo.jsp?serialId="+new Date().toString());
}
function classmanage(path){
	//alert("进入我的js函数。。。");
	//$("#leftFrame").attr("src", path+"/frameset/leftTreeProject.jsp?serialId="+new Date().toString());
	//$("#mainFrame").attr("src", path + "/frameset/projectInfo.jsp?serialId="+new Date().toString());
	parent.document.getElementById("leftFrame").setAttribute("src", path+"/frameset/left.jsp?bigCatalog=12");
	parent.document.getElementById("mainFrame").setAttribute("src", path + "/frameset/blankInfo.jsp?serialId="+new Date().toString());
}
function staffmanage(path){
	//alert("进入我的js函数。。。");
	//$("#leftFrame").attr("src", path+"/frameset/leftTreeProject.jsp?serialId="+new Date().toString());
	//$("#mainFrame").attr("src", path + "/frameset/projectInfo.jsp?serialId="+new Date().toString());
	parent.document.getElementById("leftFrame").setAttribute("src", path+"/frameset/left.jsp?bigCatalog=13");
	parent.document.getElementById("mainFrame").setAttribute("src", path + "/frameset/blankInfo.jsp?serialId="+new Date().toString());
}
function chargesys(path){
	parent.document.getElementById("leftFrame").setAttribute("src", path+"/frameset/left.jsp?bigCatalog=14");
	parent.document.getElementById("mainFrame").setAttribute("src", path + "/frameset/blankInfo.jsp?serialId="+new Date().toString());

	
}