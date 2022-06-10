<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 이미지 첨부용 스타일 -->
<style type="text/css">
	#result_card img{
		max-width: 100%;
	    height: auto;
	    display: block;
	    padding: 5px;
	    margin-top: 10px;
	    margin: auto;	
	}
	#result_card {
		position: relative;
	}
	
</style>
<title>게시글 상세보기</title>
<!-- jquery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<script type="text/javascript">
function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
var memberSeq = getParameterByName('memberSeq');
console.log(memberSeq);
$(document).on('click', '#btn_modify', function() {
	location.href='/member/modify?memberSeq='+memberSeq;
});

$(document).ready(function() {
	
	let uploadResult = $("#uploadResult");
	//파일 정보
	var uploadPath = $('#uploadPath').val();
	var uuid = $('#uuid').val();
	var fileName = $('#fileName').val();
	var thumbNailFileName = $('#thumbNailFileName').val();
	console.log("uploadPath : " + uploadPath);
	console.log("uuid : " + uuid);
	console.log("fileName : " + fileName);
	console.log("thumbNailFileName : " + thumbNailFileName);

	// 이미지 조회 결과가 없으면?
	if (fileName.length === 0) {
		let str = "";
		uploadResult.html(str);
		return;
	} else {
		// 이미지 조회 결과가 있으면?
		let str = "";
		let fileCallPath = encodeURIComponent(uploadPath + "/" + fileName);
		let fileCallPathdecode = decodeURIComponent(uploadPath + "/" + fileName);
		let thumbNailFileCallPath = decodeURIComponent(uploadPath + "/thumbnail_" + fileName);
		
		str += "<div id='result_card'>";
		str += "<li><a href='/download?fileName=" + fileCallPath + "'>"
		+ "<img src='/images/" + fileCallPathdecode + "'>" + "</a></li>"
		str += "<input type='hidden' id='originalFile' name='originalFile' value='"+ fileCallPathdecode +"'>";
		str += "<input type='hidden' id='thumbNail' name='thumbNail' value='"+ thumbNailFileCallPath +"'>";
		str += "</div>";
		uploadResult.html(str);
	}
});

$(document).on('click', '#btn_delete', function() {
	const chk = confirm("정말 삭제하시겠습니까?");
	let imageDeleteYn = false;
	 if (chk) {
		 if(document.getElementById("result_card") != null) {
			 console.log("result_card 있음. 이미지 삭제 함수 추가");
			 console.log("imageDeleteYn : " + imageDeleteYn);
             imageDeleteYn = true;
			 deleteFile();
		 }
		 console.log("result_card 없음. 텍스트만 삭제");
		 console.log("imageDeleteYn : " + imageDeleteYn);
			$.ajax({
				type:'POST',
				url : '/member/delete.do',
				contentType : 'application/x-www-form-urlencoded; charset=utf-8',
				data: { memberSeq : memberSeq , imageDeleteYn : imageDeleteYn },
				dataType : 'json',
				success : function(data) {
					var code = JSON.stringify(data.API_CODE['rtcode']).replace (/"/g,''); // 따옴표 제거
					if(code == '200') { alert('게시글 삭제 성공'); location.href = "${pageContext.request.contextPath}/member/list"; }
				}
				, error : function(xhr, status, error) { errorSubmit(); }
			});
	   }
});

/* 이미지 삭제 함수 */
function deleteFile() {
		
		let existFileName = $("#originalFile").val();
		let existThumbNailFileName = $("#thumbNail").val();
		let existUploadPath = $('#uploadPath').val();
		let targetDiv = $("#result_card");
		
		console.log(existFileName);
		console.log(existThumbNailFileName);

		$.ajax({
			url : '/deleteFile', // utils의 Image클래스에서 삭제 url
			data : { existFileName : existFileName,
				existThumbNailFileName : existThumbNailFileName },
			dataType : 'text', // 전송하는 targetFile은 문자 데이터이기 때문에 'text'를 지정함
			type : 'POST',
			success : function(result) {
				console.log(result);

				targetDiv.remove();
				$("input[type='file']").val("");
			},
			error : function(result) {
				console.log(result);

				alert("파일을 삭제하지 못하였습니다.")
			}
	});
}

function errorSubmit() {
	$.ajax({
		type:'POST'
		, url:'오류 로그 수집 서버'
			, beforeSend : function(xhr){ xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				xhr.setRequestHeader("apikey", "errorerrorerror"); }
			, data: { fromPath: "키오스크",
					  method: "deleteData",
					  msg: "게시글 삭제중 에러 발생",
					  regId: "등록ID",
					  siteId: "사이트ID",
					  deviceId: "디바이스ID" }
		, dataType : 'json'
		, success : function(data) {
			var code = JSON.stringify(data.API_CODE['rtcode']).replace (/"/g,''); // 따옴표 제거
			if(code == '200') { alert('삭제중 오류가 발생했습니다'); }
		}
		, error : function(xhr, status, error) { }
	});
}

</script>
</head>
<body>
<!-- 게시글 목록 이동 -->

<button id="btn_list">
  <a href="/member/list${Criteria.makeQuery()}">목록</a>
</button>


<table class="table">
  <tr>
    <th> 회원이름</th>
    <td> ${DataResult.memberName} </td>
  </tr>
  <tr>
    <th> 회원등급 </th>
    <td> ${DataResult.memberGrade} </td>
  </tr>
  <tr>
    <th> 사용유무 </th>
    <td> ${DataResult.memberUseYn} </td>
  </tr>
    <!-- 이미지 폼 -->
  <tr class="form_section">
	<th>이미지</th>
		<td>
				<input type="hidden" id="fileItem" name='uploadFile' style="height: 30px;" multiple>
				<input type="hidden" name="uploadPath" id="uploadPath" value="${ImageResult.uploadPath}" />
				<input type="hidden" name="uuid" id="uuid" value="${ImageResult.uuid}" />
				<input type="hidden" name="fileName" id="fileName" value="${ImageResult.fileName}" />
				<input type="hidden" name="thumbNailFileName" id="thumbNailFileName" value="thumbnail_${ImageResult.fileName}" />
				<div id="uploadResult">
				</div>
	   </td>
  </tr>
</table>

<button id="btn_modify" >수정</button>
<button id="btn_delete" >삭제</button>
</body>
</html>