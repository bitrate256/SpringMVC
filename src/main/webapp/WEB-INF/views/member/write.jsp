<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	.imgDeleteBtn{
	    position: absolute;
	    top: 0;
	    right: 5%;
	    background-color: #ef7d7d;
	    color: wheat;
	    font-weight: 900;
	    width: 30px;
	    height: 30px;
	    border-radius: 50%;
	    line-height: 26px;
	    text-align: center;
	    border: none;
	    display: block;
	    cursor: pointer;	
	}
</style>
<title>작성</title>
<!-- jquery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!-- datepicker -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<!-- validation -->
<script type="text/javascript" src="<c:url value="/resources/js/validation_check.js" />"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/validation_check.js?v=<%=new java.util.Date().getTime()%>" ></script>
<script type="text/javascript">
$(document).on('click', '#btn_list', function() {
	location.href = "${pageContext.request.contextPath}/member/list";
});
$(document).on('click', '#btn_register', function() {
	if(!item_check($('[id=name]'))) return;
	if(!item_check($('select[id=select]'))) return;
	if(!item_check($('input[name=radio]'))) return;
	// ------------ o k ------------
	frmSubmit();
});

// 상시 실행중인 비동기 함수
$(document).ready(function() {
	// 라디오 버튼 클릭 이벤트
  	$("input:radio[name=radio]").click(function() {
		var vv = $('input:radio[name=radio]:checked').val();
	    if(vv == 'Y') {
	    	$('#gender').val('Y');
	    }
	    else if(vv == 'N') {
	    	$('#gender').val('N');
	    }
	    else {
	    	$('#gender').val('');
	    }
  	});
  
	// 이미지 삭제 버튼
	$("#uploadResult").on("click", ".imgDeleteBtn", function(e){
	  deleteFile();
	});
	// 프리뷰
	$("#preview").on('change', function(){
	    readURL(this);
	});
});

function frmSubmit() {
	
	/* 이미지 존재시 삭제 (이미지 하나만 선택해서 업로드 하도록) */
	if($(".imgDeleteBtn").length > 0){
		deleteFile();
	}
	
	// FormData 객체를 인스턴스화 하여 변수에 저장
	// FormData 객체에 데이터를 추가하려면
	// FormData.append(key, value)
	var formData = new FormData();
	
	var fileInput = $('input[name="uploadFile"]');
	
	console.log("fileInput : " + fileInput);
	
	var memberName = document.getElementById("name").value;
	var memberGrade = document.getElementById("select").value;
	var memberUseYn = document.getElementById("gender").value;
	
	formData.append('memberName', memberName);
	formData.append('memberGrade', memberGrade);
	formData.append('memberUseYn', memberUseYn);
	
	/* Jquery
	"type이 file인 <input> 요소(element)"[0].files
	   Javascript
	"type이 file인 <input> 요소(element)".files */
	// FileList 객체 접근
	var fileList = fileInput[0].files;
	// FileList의 요소로 있는 File 객체에 접근
	var fileObj = fileList[0];
	
	// 선택한 파일이 개발자가 허용하는 파일이 아닐 시에 경고창과 함께 <input> change 이벤트 메서드에서 벗어나도록 구현
	// 조건 : jpg/png 파일만 허용, 파일 크기는 1MB
	var regex = new RegExp("(.*?)\.(jpg|png)$");
	var maxSize = 1048576; //1MB
	
	function fileCheck(fileName, fileSize){
		if(fileSize >= maxSize){
			alert("파일 사이즈 초과");
			return false;
		}
		if(!regex.test(fileName)){
			alert("해당 종류의 파일은 업로드할 수 없습니다.");
			return false;
		}
		return true;
	}

	// 	업로드파일 존재 여부 판단
	try {
		if (fileObj === undefined) {
			// 존재하지 않으면 텍스트만 등록
			console.log("업로드파일 존재하지 않음 fileObj.name : " + fileObj.name);
		} else {
			// 존재하면 파일체크
			console.log("업로드파일 존재함 fileObj.name : " + fileObj.name);
			// fileCheck 통과시 검사 통과 알림
			if(!fileCheck(fileObj.name, fileObj.size)){
				return false;
			}
			// 사용자가 선택한 파일을 FormData에 "UploadFile" 이름(key)으로 추가
			for(let i = 0; i < fileList.length; i++){
				formData.append("uploadFile", fileList[i]);
			}
		}
	} catch (error) {
		console.log(error.message);
	}
	
	  $.ajax({
			type:'POST',
			url:'/member/register.do',
			contentType : false,
			data: formData,
			dataType : 'json',
			processData : false,
			success : function(data) {
				var code = JSON.stringify(data.API_CODE['rtcode']).replace (/"/g,''); // 따옴표 제거
				if(code == '200') {
					alert('게시글 작성 성공');
					location.href = "${pageContext.request.contextPath}/member/list";
				} else if (code == '400') {
					alert('올바른 이미지 파일이 아닙니다');
					location.href = "${pageContext.request.contextPath}/member/list";
				}
			}
			, error : function(xhr, status, error) {
				// 오류정보 전송
				errorSubmit();
			}
		});
};

function errorSubmit() {
	var regDate = new Date();
	$.ajax({
		type:'POST'
		, url:'오류 로그 수집 서버'
		, beforeSend : function(xhr){ xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
									xhr.setRequestHeader("apikey", "errorerrorerror"); }
		, data: { fromPath: "키오스크"
				, method: "regPost"
				, msg: "게시글 저장중 에러 발생"
				, regId: "등록ID"
				, siteId: "사이트ID"
				, deviceId: "디바이스ID" }
		, dataType : 'json'
		, success : function(data) {
			var code = JSON.stringify(data.API_CODE['rtcode']).replace (/"/g,''); // 따옴표 제거
			if(code == '200') {
				alert('저장중 오류가 발생했습니다');
				location.href = "${pageContext.request.contextPath}/member/list";
			}
		}
		, error : function(xhr, status, error) { }
	});
};

// 미리보기 이미지 제거 + 서버에 이미지 파일 삭제 요청을 수행
/* 이미지 삭제 */
function deleteFile(){
	
	let targetFile = $(".imgDeleteBtn").data("file");
		
	let targetDiv = $("#result_card");
	
	$.ajax({
		url: '/deleteFile', // utils의 Image클래스에서 삭제 url
		data : { fileName : targetFile }, // 객체 초기자를 활용해 fileName 속성명에 targetFile(이미지 파일 경로) 속성 값을 부여함.
		                                  // 서버의 메서트 파라미터에 String fileName을 선언하였기 때문에 스프링에서 해당 데이터를 매핑할 것
		dataType : 'text', // 전송하는 targetFile은 문자 데이터이기 때문에 'text'를 지정함
		type : 'POST',
		success : function(result){
			console.log(result);
			
			targetDiv.remove();
			$("input[type='file']").val("");
		},
		error : function(result){
			console.log(result);
			
			alert("파일을 삭제하지 못하였습니다.")
		}
   });
}
//업로드 대기 이미지 미리보기
function readURL(input) {
	if (input.files && input.files[0]) {
	  var reader = new FileReader();
	  reader.onload = function(e) {
	    document.getElementById('preview').src = e.target.result;
	  };
	  reader.readAsDataURL(input.files[0]);
	} else {
	  document.getElementById('preview').src = "";
	}
}
</script>
</head>
<body>
<!-- 게시글 목록 이동 -->
<button id="btn_list">목록</button>

<form method='post' name='form0' id='form0' accept-charset='utf-8' >
<table class="table">
  <tr>
    <th> 이름 </th>
    <td> <input type="text" name="memberName" title="성함" onKeyup="this.value=this.value.replace(/[^ㄱ-ㅎ|가-힣|a-z|A-Z]/g,'');" id="name" >
         <input type="hidden" name="memberSeq" id="memberSeq" value="${DataResult.memberSeq}" />
    </td>
  </tr>
  <tr>
    <th> 등급 </th>
    <td>
      <select id="select" title="" name="memberGrade" >
        <option value=""></option>
        <option value="A">A</option>
        <option value="B">B</option>
        <option value="C">C</option>
        <option value="D">D</option>
      </select>
    </td>
  </tr>  
  <tr>
    <th> 라디오 버튼 </th>
    <td>
      <input type="hidden" id="gender" name="memberUseYn" value="">
      <input type="radio" name="radio" title="" value="Y"> 사용
      <input type="radio" name="radio" title="" value="N"> 비사용
    </td>
  </tr>
  
  <!-- 이미지 폼 -->
  <tr class="form_section">
	<th> 이미지 </th>
	<td>
	  <input type="file" id ="uploadFile" name='uploadFile' onchange="readURL(this)" style="height: 30px;" multiple>
	  <input type="hidden" name="uploadPath" id="uploadPath" value="${ImageResult.uploadPath}" />
	  <input type="hidden" name="uuid" id="uuid" value="${ImageResult.uuid}" />
	  <input type="hidden" name="fileName" id="fileName" value="${ImageResult.fileName}" />
	   
	  <div id="uploadResult">
	    <img id="preview" />
      </div>
	</td>
  </tr>

</table>
</form>

<button id="btn_register">확인</button>
</body>
</html>