console.log("Reply Module~");

var replyService = (function(){
//	댓글 등록
	function add(reply, callback, error){
		console.log("add reply~~~");	

		$.ajax({
			type : 'post',
			url : '/replies/new',
			data : JSON.stringify(reply),
			contentType : "application/json; charset=utf-8",
			success : function(result, status, xhr){
				if(callback) {
					callback(result);
				}
			},
			error : function(xhr, status, er) {
				if(error) {
					error(er);
				}
			}
 	 	})
	}
//	댓글 리스트
	function getList(param, callback, error){
		var bno = param.bno;
		var page = param.page || 1;
		
		$.getJSON("/replies/pages/" + bno + "/" + page + ".json",
			function(data){
				if(callback){
					//callback(data);						// 댓글 목록만 가져오는 경우
					callback(data.replyCnt, data.list);		// 댓글 숫자와 목록을 가져오는 경우
				}
			}).fail(function(xhr, status, err){
		  if (error) {
			error();		
		   }
		 });
	 }  
	
//	댓글 삭제
	function remove(rno, callback, error){
		$.ajax({
			type : 'delete',
			url : '/replies/' + rno,
			success : function(deleteResult, status, xhr) {
				if (callback) {
					callback(deleteResult);
				}
			},
			error : function(xhr, status, er) {
				if(error){
					error(er)
				}
			}
			
		})
	}
//	댓글 수정
	function update(reply, callback, error){
		console.log("RNO : " + reply.rno);
		
		$.ajax({
			type : 'put',
			url : '/replies/' + reply.rno,
			data : JSON.stringify(reply),
			contentType : "application/json; charset=utf-8",
			success : function(result, status, xhr){
				callback(result);
			},
			error : function(xhr, status, er){
				if(error){
					error(er);
				}
			}
		})
	}
//	특정 번호 댓글 조회
	function get(rno, callback, error){
		$.get("/replies/" + rno + ".json", function(result){
			if(callback){
				callback(result);
			}
		}).fail(function(xhr, status, err){
			if(error){
				error();
			}
		});
	}
	
//	시간 처리
	function displayTime(timeValue){
		
		var today = new Date();

		var gap = today.getTime() - timeValue;
		
		var dateObj = new Date(timeValue);
		var str = "";
		
		//alert(dateObj);
		
		if(gap < (1000 * 60 * 60 * 24)) {
//			var hh = dateObj.getHours();
//			var mi = dateObj.getMinutes();
//			var ss = dateObj.getSeconds();
//		
//			return [ (hh > 9 ? '' : '0') + hh, ":", (mi > 9 ? '' : '0') + mi,
//				':', (ss > 9 ? '' : '0') + ss ].join('');
			
			var min = 60 * 1000;
			//var c = new Date()
			//var d = new Date(dt);
			var minsAgo = Math.floor((today - dateObj) / (min));

			var result = {
				'raw': dateObj.getFullYear() + '-' + (dateObj.getMonth() + 1 > 9 ? '' : '0') + (dateObj.getMonth() + 1) + '-' + (dateObj.getDate() > 9 ? '' : '0') +  dateObj.getDate() + ' ' + (dateObj.getHours() > 9 ? '' : '0') +  dateObj.getHours() + ':' + (dateObj.getMinutes() > 9 ? '' : '0') +  dateObj.getMinutes() + ':'  + (dateObj.getSeconds() > 9 ? '' : '0') +  dateObj.getSeconds(),
				'formatted': '',
			};

			if (minsAgo < 60) { // 1시간 내
				return result.formatted = minsAgo + '분 전  ';
			} else if (minsAgo < 60 * 24) { // 하루 내
				return result.formatted = Math.floor(minsAgo / 60) + '시간 전  '; }
//			} else { // 하루 이상
//				result.formatted = Math.floor(minsAgo / 60 / 24) + '일 전';
//			};
//
//			return formatDate;
			
		}else{
			var yy = dateObj.getFullYear();
			var mm = dateObj.getMonth() + 1;
			var dd = dateObj.getDate();
			
			return [ yy, '/', (mm > 9 ? '' : '0') + mm, '/',
				(dd > 9 ? '' : '0') + dd ].join('');
		}
			
	};
	
	return {
		add : add,
		get : get,
		getList : getList,
		remove : remove,
		update : update,
		displayTime : displayTime
	};
	
})();