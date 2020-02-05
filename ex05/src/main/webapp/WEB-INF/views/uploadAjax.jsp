<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<title>UPLOAD with AJAX</title>
</head>
<body>
	
	<div class='uploadDiv'>
		<input type="file" name="uploadFile" multiple>
	</div>

	<button id="uploadBtn">UPLOAD</button>

<script>
$(document).ready(function() {
	$("#uploadBtn").on("click", function(e){
		alert("click");
		var formData = new FormData();
		
		var inputFile = $("input[name='uploadFile']"); 
		var files = inputFile[0].files;
		
		console.log(files);
		
		//add filedate to formdata
		for (var i = 0; i < files.length; i++) {
			formData.append("uploadFile", files[i]);
		}
		
		$.ajax({
			url : '/uploadAjaxAction',
			processData : false,
			contentType : false,
			data : formData,
			type : 'POST',
			success : function(result) {
				slert("UPLOAD");
			}
		});	// end ajax
	
	});	
});
	
</script>	
</body>
</html>