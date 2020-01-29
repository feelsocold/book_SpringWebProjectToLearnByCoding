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
                            <form role="form" action="/board/modify" method="post">
                            	<div class="form-group">
                            		<label>Bno</label> <input class="form-control" name="bno" 
                            		value='<c:out value="${board.bno }" />' readonly>
                            	</div>
                            	
                            	<div class="form-group">
									<label>Title</label>
									<input class="form-control" name="title"
									value='<c:out value="${board.title }" />'></input>	
                            	</div>
                            	
                            	<div class="form-group">
                            		<label>Text area</label>
                            		<textarea class="form-control" rows="5" name='content' ><c:out value="${board.content }" /></textarea>
                            		
                            	</div>
                            	
                            	<div class="form-group">
                            		<label>Writer</label> <input class="form-control" name="writer"
                            		value='<c:out value="${board.writer }" />' >
                            	</div>
                            	
                            	<input type="hidden" name="pageNum" value='<c:out value="${cri.pageNum }" />'>
                            	<input type="hidden" name="amount" value='<c:out value="${cri.amount}" />'>
                            	
                            	<button type="submit" data-oper="modify" class="btn btn-default">MODIFY</button> 
                            	<button type="submit" data-oper="remove" class="btn btn-danger">DELETE</button> 
                            	<button type="submit" data-oper="list" class="btn btn-info" >LIST</button>
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
		var formObj = $("form");
		
		$("button").on("click", function(e){
			e.preventDefault();
			
			var operation = $(this).data("oper");
			
			console.log(operation);
			
			if(operation === 'remove'){
				formObj.attr("action", "/board/remove");
			}else if(operation === 'list'){
				//move to list
				formObj.attr("action", "/board/list").attr("method", "get");
				var pageNumTag = $("input[name='pageNum']").clone();
				var amountTag = $("input[name='amount']").clone();
				
				formObj.empty;
				formObj.append("pageNumTag");
				formObj.append("amountTag");
				
				/* self.location = "/board/list";
				return; */
			}
			formObj.submit();
		})
	
	});
	
</script>

</html>