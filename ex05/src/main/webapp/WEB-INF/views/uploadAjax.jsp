<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<style>
.uploadResult{
	width:100%;
	background-color: gray;
}
.uploadResult ul{
	display:flex;
	flex-flow: row;
	justify-content: center;
	align-items: center;
}
.uploadResult ul li {
	list-style: none;
	padding: 10px;
}
.uploadResult ul li img{
	width : 200px;
}
.uploadResult ul li span{
	color:white;
}

.bigPictureWrapper {
	position : absolute;
	display : none;
	justify-content : center;
	align-items : center;
	top : 0%;
	width : 100%;
	background-color : grey;
	z-index : 100;
	background:rgba(255,255,255,0.5);
}

.bigPicture {
	position : relative;
	display : flex;
	justify-content : center;
	align-items : center;
}

.bigPicture img{
	width : 600px;
}
</style>

<title>UPLOAD with AJAX</title>
</head>
<body>
	
	<div class='uploadDiv'>
		<input type="file" name="uploadFile" multiple>
	</div>
	
	<div class="uploadResult">
		<ul>
		
		</ul>
	</div>

	<button id="uploadBtn">UPLOAD</button>
	
	<div class='bigPictureWrapper'>
		<div class='bigPicture'>
		</div>
	</div>

<script>
$(document).ready(function() {
	
	// FILE 확장자,사이즈 사전 처리	
	var regex = new RegExp("(.*?)\.(exe|sh|zip|alz|jar|jpg)&");
	var maxSize = 524880; 	// 5MB
	
	function checkExtension(fileName, fileSize){
		
		if(fileSize >= maxSize){
			alert("파일 사이즈 초과");
			return false;
		}
		
		if(regex.test(fileName)){
			alert("해당 종류의 파일은 업로드할 수 없습니다.");
			return false;
		}
		
		return true;
	} // end function checkExtension

// FILE 업로드 이벤트처리
	var cloneObj = $(".uploadDiv").clone();
	
	$("#uploadBtn").on("click", function(e){
		var formData = new FormData();
		
		var inputFile = $("input[name='uploadFile']"); 
		var files = inputFile[0].files;
		
		console.log(files);
		
		//add filedate to formdata
		for (var i = 0; i < files.length; i++) {
			if(!checkExtension(files[i].name, files[i].size) ){
				return false;
			}else{
			formData.append("uploadFile", files[i]); }
		}
		
		$.ajax({
			url : '/uploadAjaxAction',
			processData : false,
			contentType : false,
			data : formData,
				type : 'POST',
				dataType: 'json',
				success : function(result) {
					console.log(result);
					
					showUploadedFile(result);
					
					$(".uploadDiv").html(cloneObj.html());
				}
		});	// end ajax
	
	});	 //end function uploadBtnclick
	
// 파일 이름 출력	
	var uploadResult = $(".uploadResult ul");
	
	function showUploadedFile(uploadResultArr){
		var str = "";
		
		$(uploadResultArr).each(
				
			function(i, obj){
				
				if(!obj.image){
					var fileCallPath = encodeURIComponent( obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName );
				
					var fileLink = fileCallpath.replace(new RegExp(/\\/g), "/");
					
					str += "<li><div><a href='/download?fileName=" + fileCallPath + "'>";
					str += "<img src='https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQqBkWVMOwxWPv4jDWhaytTugDikyu4f45V4-qWOrLnP1e51XBl'>";
					str += obj.fileName + "</a>" + "<span data-file=\'" + fileCallPath + "\' data-type='file'> x </span>";
					str += "</div></li>";
					
				}else{
					//str += "<li>" + obj.fileName + "<li>";
 					var fileCallPath = encodeURIComponent( obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName );					
					
 					var originPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;
 					
 					originPath = originPath.replace(new RegExp(/\\/g), "/" );
					 					
					//str += "<li><img src='/display?fileName="+fileCallPath+"'><li>";
					str += "<li><a href=\"javascript:showImage(\'" + originPath + "\')\">";
					str += "<img src='/display?fileName=" + fileCallPath + "'></a>";
					str += "<span data-file=\'" + fileCallPath + "'\ data-type = 'image'> x </span>" + "<li>"; 
				}
			});
		uploadResult.append(str);
	} // end function showUploadedFile

}); // end $(document).ready 

function showImage(fileCallPath){
	$('.bigPictureWrapper').css('display','flex').show();
	
	$('.bigPicture')
	.html("<img src='/display?fileName="+encodeURI(fileCallPath)+"'>")
	.animate({width:'100%', height:'100%'},1000);
}
function(".bigPictureWrapper").on("click", function(e){
	$(".bigPicture").animate({width:'0%', height : '0%'}, 1000);
	setTimeout(() => {
		$(this).hide();
	}, 1000);
});
</script>	
</body>
</html>