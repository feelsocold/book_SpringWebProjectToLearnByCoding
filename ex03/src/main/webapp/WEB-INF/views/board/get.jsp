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
<meta charset="UTF-8">x
<title>Insert title here</title>
</head>
<body>

		<div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Board Read</h1>
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
                            		<label>Writer</label> <input class="form-control" name="writer"
                            		value='<c:out value="${board.writer }" />' readonly="readonly">
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
                            	
                            	<button data-oper="modify" class="btn btn-default"
                            		onClick="location.href='/board/modify?bno=<c:out value="${board.bno }" />'">MODIFY</button>
                            	<button data-oper="list" class="btn btn-default"
                            		onClick="location.href='/board/remove?bno=<c:out value="${board.bno }" />'">LIST</button>
                            	
                            	<form id="operForm" action="/board/modify" method="get">
                            		<input type="hidden" id="bno" name="bno" value='<c:out value="${board.bno }" />'>
                            		<input type="hidden" name="pageNum" value='<c:out value="${cri.pageNum }" />'>
                            		<input type="hidden" name="amount" value='<c:out value="${cri.amount}" />'>
                            	</form>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
         <!-- 댓글리스트 div 시작-->
          	<div class="row">
          		<div class="col-lg-12">
				 <div class="panel panel-default">
				 	<div class="panel-heading">
						<i class="fa fa-comments fa-fw"></i> REPLY
						<button id="addReplyBtn" class="btn btn-primary btn-xs pull-right">
						NEW REPLY</button>
					</div>
				 	<!-- /.panel-heading -->
				 	<div class="panel-body">
				 		<ul class="chat">
				 			<!-- start reply -->
				 			<li class="left clearfix" data-rno='12'>
				 			  <div>
				 			  	<div class="header">
				 			  		<strong class="primary-font">user00</strong>
				 			  		<small class="pull-right text-muted">2018-01-01 13:13</small>
				 			  	</div>
				 			  	<br>
				 			  	<p>GOOD</p>
				 			  </div>
				 			<!-- end reply -->
				 		</ul>
				 		<!-- ./ end ul -->
				 	</div>
				 	<!-- /.panel-body -->
				 	
				 	<div class='panel-footer'>
				 	</div>
				 	
				 </div>          		
          		</div>
          	</div>
          	<!-- ./ end row -->
         </div>
	<!-- 댓글 추가 Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>댓글</label>
						<input class="form-control" name="reply" value="NEW REPLY!!!">
						<label>작성자</label>
						<input class="form-control" name="replyer" value="replyer">
					</div>
					<div class="form-group">
						<label>Reply Date</label>
						<input class="form-control" name="replyDate" value=''>
					</div>
				</div>
				<div class="modal-footer">
					<button id="modalModBtn" type="button" class="btn btn-warning">Modify</button>
					<button id="modalRemoveBtn" type="button" class="btn btn-danger">Remove</button>
					<button id='modalRegisterBtn' type="button" class="btn btn-primary">Register</button>
					<button id="modalCloseBtn" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<%@include file="../includes/footer.jsp" %>
	
</body>

<script type="text/javascript" src="/resources/js/reply.js"></script>
<script type="text/javascript">
	console.log("=========");
	console.log("JS TEST");
		
	var bnoValue = '<c:out value = "${board.bno}" />';
	
	//for replyService add test
	/* replyService.add(
		{reply:"JS TEST", replyer:"tester", bno:bnoValue}
		,
		function(result){
			alert("RESULT : " + result);
		}
	); */
	
	//댓글리스트
	replyService.getList({bno:bnoValue, page:1}, function(list){
		
		for(var i = 0, len = list.length || 0; i < len; i ++){
			console.log(list[i]);
		}
		
	});
	
	//댓글 삭제 test
	/* replyService.remove(33, function(count) {
		
		console.log(count);
		
		if(count === "success"){
			alert("REMOVED");
		}
	}, function(err){
		//alert("ERROR~");
	}); */
	
	//댓글 수정 test
	/* replyService.update({
		rno : 22,
		bno : bnoValue,
		reply : "MODIFIED REPLY ... "
	}, function(result){
		alert("수정 완료");
	}); */
	
	//특정댓글 조회
	replyService.get(29, function(data){
		console.log(data);
	});
	
</script>

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

<!-- 페이지가 열리는 순간 댓글리스트 가져오기 -->
<script type="text/javascript">
	$(document).ready(function() {
					
		var bnoValue = '<c:out value = "${board.bno}" />';
		var replyUL = $(".chat");
	
		showList(1);
	
/*		function showList(page) {replyService.getList({bno : bnoValue,	page : page || 1},function(list) {
					
					var str = "";

					if (list == null
							|| list.length == 0) {
						replyUL.html("");

						return;
					}
					for (var i = 0, len = list.length || 0; i < len; i++) {
						str += "<li class='left clearfix' data-rno='" + list[i].rno + "'>";
						str += "		<div><div class='header'><strong class='primary-font'>";
						str += list[i].replyer
								+ "</strong>";
						str += "		<small class='pull-right text-muted'>";
						str += replyService.displayTime(list[i].replyDate) + "</small></div>";
						str += "		<br><p>"
								+ list[i].reply
								+ "</p></div></div></li>";
								
					}
					replyUL.html(str);

				}); //end function */
		 function showList(page){
			console.log("show REPLYLIST PAGE-" + page);
			replyService.getList({bno:bnoValue, page : page || 1},
			
				function(replyCnt, list){
					
					console.log("replyCnt : " + replyCnt );
					console.log("list : " + list);
					console.log(list);
					
					if(page == -1){		//새로운 댓글 추가시 호출하여 댓글의 숫자를 파악하고, 마지막 페이지를 호출한다.
						pageNum = Math.ceil(replyCnt/10.0);
						showList(pageNum);
						return;
					}
					
					var str = "";
					
					if(list == null || list.length == 0){
						return;
					}
					
					for (var i = 0, len = list.length || 0; i < len; i++) {
						str += "<li class='left clearfix' data-rno='" + list[i].rno + "'>";
						str += "		<div><div class='header'><strong class='primary-font'>";
						str += list[i].replyer
								+ "</strong>";
						str += "		<small class='pull-right text-muted'>";
						str += replyService.displayTime(list[i].replyDate) + "</small></div>";
						str += "		<br><p>"
								+ list[i].reply
								+ "</p></div></div></li>";
								
					}
					replyUL.html(str);	
		 		
					showReplyPage(replyCnt);
						
			}); // end function		
	} //end showList
	
/* 댓글 추가 시작 시 버튼 이벤트 처리 */

	var modal = $(".modal");
	var modalInputReply = modal.find("input[name='reply']");
	var modalInputReplyer = modal.find("input[name='replyer']");
	var modalInputReplyDate = modal.find("input[name='replyDate']");
	
	var modalModBtn = $("#modalModBtn");
	var modalRemoveBtn = $("#modalRemoveBtn");
	var modalRegisterBtn = $("#modalRegisterBtn");
	
	$("#addReplyBtn").on("click", function(e){
		
		modal.find("input").val("");
		modalInputReplyDate.closest("div").hide();
		modal.find("button[id != 'modalCloseBtn']").hide();
		
		modalRegisterBtn.show();
		
		$(".modal").modal("show");
	});
	
/* 새로운 댓글 추가 처리 */
	modalRegisterBtn.on("click", function(e){
		var reply = {
				reply : modalInputReply.val(),
				replyer : modalInputReplyer.val(),
				bno : bnoValue
		};
		replyService.add(reply, function(result){
			//alert(result);
			
			modal.find("input").val("");
			modal.modal("hide");
			
			//showList(1);
			showList(-1);
		});
	});

/* 댓글 클릭 이벤트 처리 */

	//댓글 조회 이벤트 처리
	$(".chat").on("click", "li", function(e){
		var rno = $(this).data("rno");
		
		replyService.get(rno, function(reply){
			modalInputReply.val(reply.reply);
			modalInputReplyer.val(reply.replyer).attr("readonly", "readonly");
			modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly", "readonly");
			modal.data("rno", reply.rno);

			modal.find("button[id != 'modalCloseBtn']").hide();
			modalModBtn.show();
			modalRemoveBtn.show();
			
			$(".modal").modal("show");
		});
	});
	
	//댓글 수정
	modalModBtn.on("click", function(e){
		var reply = { rno:modal.data("rno"), reply : modalInputReply.val() };

		replyService.update(reply, function(result){
			alert(result);
			modal.modal("hide");
			showList(1);
			showList(pageNum);
		});
	});	
	
	//댓글 삭제
	modalRemoveBtn.on("click", function(e){
		var rno = modal.data("rno");
		
		replyService.remove(rno, function(result){
			alert(result);
			modal.modal("hide");
			showList(1);
			showList(pageNum);	
		});
	});
	
/* 댓글페이징 */
 	var pageNum = 1;
 	var replyPageFooter = $(".panel-footer");
 	
 	function showReplyPage(replyCnt){
 			
 		var endNum = Math.ceil(pageNum / 10.0) * 10;
 		var startNum = endNum - 9;
 		
 		var prev = startNum != 1;
 		var next = false;
 		
 		if(endNum * 10 >= replyCnt){
 			endNum = Math.ceil(replyCnt/10.0);
 		}
 		if(endNum * 10 < replyCnt){
 			next = true;
 		}
 		
 		var str = "<ul class='pagination pull-right'>";
 		
 		if(prev){
 			str += "<li class='page-item'><a class='page-link' href='" + (startNum -1) + "'>PRE</a></li>";
 		}
 		
 		for(var i = startNum; i <= endNum; i++){
 			var active = pageNum == i? "active":"";
 			str += "<li class='page-item " + active + " '><a class='page-link' href='" + i + "'>" + i + "</a></li>";
 		}
 		
 		if(next){
 			str += "<li class='page-item'><a class='page-link' href='" + (endNum + 1) + "'>NEXT</a></li>";
 		}
 		
 		str += "</ul></div>";
 		
 		console.log(str);
 		
 		replyPageFooter.html("ㅇㅅㅇ");
 		replyPageFooter.html(str);
 	}
 	
 	//댓글 페이지 이동
 	replyPageFooter.on("click", "li a", function(e){
		e.preventDefault();
 		console.log("page click");
 		
		var targetPageNum = $(this).attr("href");
		
		console.log("targetPageNum : " + targetPageNum);
		
		pageNum = targetPageNum;
		
		showList(pageNum);
 	})
 	

	
});	
	
	
	
	
</script>
</html>