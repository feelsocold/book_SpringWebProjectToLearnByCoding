<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../includes/header.jsp" %>
<!-- jstl -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		<div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Board Register</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        
                        <div class="panel-heading">
                            Board Register
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            
                            	<div class="form-group">
                            		<label>Bno</label> <input class="form-control" name="bno" 
                            		value='<c:out value="${board.bno }" />' readonly="readonly">
                            	</div>
                            	
                            	<div class="form-group">
									<label>Title</label>
									<input class="form-control" name="title"
									value='<c:out value="${board.title }" />' readonly="readonly"></input>	
                            	</div>
                            	
                            	<div class="form-group">
                            		<label>Text area</label>
                            		<textarea class="form-control" rows="5" name='content' readonly='readonly' style="text-align: left;"><c:out value="${board.content }" /></textarea>
                            		
                            	</div>
                            	
                            	<div class="form-group">
                            		<label>Writer</label> <input class="form-control" name="writer"
                            		value='<c:out value="${board.writer }" />' readonly="readonly">
                            	</div>
                            	
                            	<button data-oper="modify" class="btn btn-default"
                            	onClick="location.href='/board/modify?bno=<c:out value="${board.bno }" />'">MODIFY</button>
                            	<button data-oper="list" class="btn btn-default"
                            	onClick="location.href='/board/remove?bno=<c:out value="${board.bno }" />'">LIST</button>
                            	
                            	<form id="operForm" action="/board/modify" method="get">
                            		<input type="hidden" id="bno" name="bno" value='<c:out value="${board.bno }" />'>
                            	</form>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
          </div>  
   	<%@include file="../includes/footer.jsp" %>
</body>

<script type="text/javascript">
	$(document).ready(function() {
		var operForm = $("#operForm");
		
		$("button[data-oper='modify']").on("click", function(e){
			operForm.attr("action", "/board/modify").submit();
		});
		
		$("button[data-oper='list']").on("click", function(e){
			operForm.find("#bno").remove();
			operForm.attr("action", "/board/list").submit();
			operForm.submit();
		});
	});
	
	

</script>
</html>