<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 이미지 첨부용 스타일 -->
<style type="text/css">
#result_card img {
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

.imgDeleteBtn {
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
<title>수정</title>
<!-- jquery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!-- datepicker -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<!-- validation -->
<script type="text/javascript" src="<c:url value="/resources/js/validation_check.js" />"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/validation_check.js?v=<%=new java.util.Date().getTime()%>"></script>
<script type="text/javascript">
function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
var memberSeq = getParameterByName('memberSeq');
console.log(memberSeq);
// 상시 실행중인 비동기 함수
$(document).ready(function() {

	$("input:radio[name=radio]").click(function() { // 라디오 버튼 클릭 이벤트
		var vv = $('input:radio[name=radio]:checked').val();
		if (vv === 'Y') {
			$('#gender').val('Y');
		} else if (vv === 'N') {
			$('#gender').val('N');
		} else {
			$('#gender').val('');
		}
	});
	// 업로드 대기중인 파일이 있으면?
	$("#uploadFile").change(function(){
		$("#preview").empty();
		$("#result_card").empty();
	    readURL(this);
	});
	/* 기존 이미지 출력 */
	let uploadResult = $("#uploadResult");
	// 파일 정보
	console.log('업로드 된 기존 파일');
	var existUploadPath = $('#existUploadPath').val();
	var existUuid = $('#existUuid').val();
	var existFileName = $('#existFileName').val();
	var existThumbNailFileName = $('#existThumbNailFileName').val();
	console.log("existUploadPath : " + existUploadPath);
	console.log("existUuid : " + existUuid);
	console.log("existFileName : " + existFileName);
	console.log("existThumbNailFileName : " + existThumbNailFileName);
	
	// 이미지 조회 결과가 없으면?
	if (existFileName.length === 0) {
		let str = "";
		uploadResult.append(str);
		return;
	} else {
		// 이미지 조회 결과가 있으면?
		let str = "";
		let fileCallPath = decodeURIComponent(existUploadPath + "/" + existFileName);
		let thumbNailFileCallPath = decodeURIComponent(existUploadPath + "/thumbnail_" + existFileName);
		str += "<div id='result_card'>";
		str += "<img src='/images/" + fileCallPath + "'>";
		str += "<div class='imgDeleteBtn' data-file='" + fileCallPath + "'>X</div>";
		
		str += "<input type='hidden' id='fileName' name='imageList[0].fileName' value='"+ existFileName +"'>";
		str += "<input type='hidden' id='thumbNailFileName' name='imageList[0].thumbNailFileName' value='"+ existThumbNailFileName +"'>";
		str += "<input type='hidden' id='uuid' name='imageList[0].uuid' value='"+ existUuid +"'>";
		str += "<input type='hidden' id='uploadPath' name='imageList[0].uploadPath' value='"+ existUploadPath +"'>";
		str += "</div>";
		uploadResult.append(str);
		
		let existImage = "";
		existImage += "<div id='exist_image'>";
		existImage += "<input type='hidden' id='originalFile' name='originalFile' value='"+ fileCallPath +"'>";
		existImage += "<input type='hidden' id='thumbNail' name='thumbNail' value='"+ thumbNailFileCallPath +"'>";
		existImage += "</div>";
		uploadResult.append(existImage);
	}
	
	// 이미지 삭제 버튼
	$("#uploadResult").on("click", ".imgDeleteBtn", function(e){
		document.getElementById("preview").style.display = "none";
		document.getElementById("result_card").remove();
	    readURL(this);
	});
});

$(document).on('click', '#btn_cancel', function() { // 이전 페이지 이동
	history.back();
});

$(document).on('click', '#btn_modify', function() {
	if (!item_check($('[id=name]')))
		return;
	if (!item_check($('select[id=select]')))
		return;
	if (!item_check($('input[name=radio]')))
		return;
	// ------------ o k ------------
	frmSubmit();
});

function fileCheck(fileName, fileSize) {
	// 선택한 파일이 개발자가 허용하는 파일이 아닐 시에 경고창과 함께 <input> change 이벤트 메서드에서 벗어나도록 구현
	// 조건 : jpg/png 파일만 허용, 파일 크기는 1MB
	let regex = new RegExp("(.*?)\.(jpg|png)$");
	let maxSize = 5242880; // 5MB

	if (fileSize >= maxSize) {
		alert("파일 사이즈 초과");
		return false;
	}
	if (!regex.test(fileName)) {
		alert("해당 종류의 파일은 업로드할 수 없습니다.");
		return false;
	}
	console.log("업로드 파일검증 통과");
	return true;
}

// frmSubmit()을 글 작성 폼과 동일한 양식으로
function frmSubmit() {

	// FormData 객체를 인스턴스화 하여 변수에 저장
	// FormData 객체에 데이터를 추가하려면
	// FormData.append(key, value)
	let formData = new FormData();

	console.log("폼데이터 선언");
	var imageEditYn = false;
	var uploadStart = false;
	// 게시글 수정시 이미지 수정/삭제/변동없음 판단
	if (document.getElementById("result_card") !== null && document.getElementById("exist_image") !== null
			&& document.getElementById("preview").getAttribute("src") !== null ) {
		console.log("result_card 있음 / exist_image 있음 / preview 있음");
		console.log("게시글에 이미지 있음 / 이미지만 수정");
		imageEditYn = true;
		uploadStart = true;
		console.log("imageEditYn : " + imageEditYn);
		console.log("uploadStart : " + uploadStart);
	} else if (document.getElementById("result_card") === null && document.getElementById("exist_image") === null
			&& document.getElementById("preview").getAttribute("src") === null ) {
		console.log("result_card 없음 / exist_image 없음 / preview 없음");
		console.log("게시글에 이미지 없음 / 이미지 삭제 시도 하지 않고 글만 수정");
		console.log("imageEditYn : " + imageEditYn);
		console.log("uploadStart : " + uploadStart);
	} else if (document.getElementById("result_card") === null && document.getElementById("exist_image") !== null
			&& document.getElementById("preview").getAttribute("src") === null ) {
		console.log("result_card 없음 / exist_image 있음 / preview 없음");
		console.log("현재 이미지 삭제");
		imageEditYn = true;
		console.log("imageEditYn : " + imageEditYn);
		console.log("uploadStart : " + uploadStart);
	} else if ( document.getElementById("result_card") === null && document.getElementById("exist_image") !== null
			&& document.getElementById("preview").getAttribute("src") !== null ) {
		console.log("result_card 없음 / exist_image 있음 / preview 있음");
		console.log("게시글에 이미지 있음 / 이미지 수정 업로드");
		imageEditYn = true;
		uploadStart = true;
		console.log("imageEditYn : " + imageEditYn);
	} else if (document.getElementById("result_card") === null && document.getElementById("exist_image") === null
			&& document.getElementById("preview").getAttribute("src") !== null ) {
		console.log("result_card 없음 / exist_image 없음 / preview 있음");
		console.log("게시글에 이미지 없음 / 이미지 신규 업로드");
		imageEditYn = true;
		uploadStart = true;
		console.log("imageEditYn : " + imageEditYn);
		console.log("uploadStart : " + uploadStart);
	} else if (document.getElementById("result_card") !== null && document.getElementById("exist_image") !== null
			&& document.getElementById("preview").getAttribute("src") === null ) {
		console.log("result_card 있음 / exist_image 있음 / preview 없음");
		console.log("게시글에 이미지 있음 / 이미지 삭제하지 않고 글만 수정");
		console.log("imageEditYn : " + imageEditYn);
		console.log("uploadStart : " + uploadStart);
	}
	
	const memberSeq = document.getElementById("memberSeq").value;
	const memberName = document.getElementById("name").value;
	const memberGrade = document.getElementById("select").value;
	const memberUseYn = document.getElementById("gender").value;
	
	const existUploadPath = document.getElementById("existUploadPath").value;
	const existUuid = document.getElementById("existUuid").value;
	
	formData.append("memberSeq", memberSeq);
	formData.append("memberName", memberName);
	formData.append("memberGrade", memberGrade);
	formData.append("memberUseYn", memberUseYn);
	formData.append("imageEditYn", imageEditYn);
	formData.append("uploadStart", uploadStart);
	
	formData.append("existUploadPath", existUploadPath);
	formData.append("existUuid", existUuid);
	// uploadFile 가 있으면 이미지 추가
	if( uploadStart === true ){
		const fileInput = $('input[name="uploadFile"]');
		console.log("fileInput : " + fileInput);
		// FileList 객체 접근
		let fileList = fileInput[0].files;
		// FileList의 요소로 있는 File 객체에 접근
		let fileObj = fileList[0];
		// 존재하면 파일체크
		console.log("업로드파일 존재함 fileObj.name : " + fileObj.name);
		// fileCheck 통과시 검사 통과 알림
		if(!fileCheck(fileObj.name, fileObj.size)){
			console.log("파일체크(용량/확장자) 통과 실패");
			return false;
		}
		// 사용자가 선택한 파일을 FormData에 "UploadFile" 이름(key)으로 추가
		for(let i = 0; i < fileList.length; i++){
			// 업로드 파일 객체
			formData.append("uploadFile", fileList[i]);
			// 삭제할 기존 파일 이름
			formData.append("existFileName", $("#originalFile").val());
			formData.append("existThumbNailFileName", $("#thumbNail").val());
			console.log("파일 삭제 데이터 추가");
		}
		console.log("uploadFile 확인됨");
	} else {
		// uploadFile 없음
		console.log("uploadFile 없음");
		// 삭제할 기존 파일 이름
		formData.append("existFileName", $("#originalFile").val());
		formData.append("existThumbNailFileName", $("#thumbNail").val());
	}
	console.log("업로드 파일 존재 여부 판단 종료");
	
	$.ajax({
		type : 'POST',
		url : '/member/modify.do',
		contentType : false,
		data : formData,
		dataType : 'json',
		processData : false,
		async:false,
		success : function(data) {
			const code = JSON.stringify(data.API_CODE['rtcode']).replace(/"/g, ''); // 따옴표 제거
			if (code === '200') {
				alert('게시글 수정 성공');
				location.href = "/member/detail?memberSeq=" + memberSeq;
			} else if (code === '500') {
				alert('내부 서버 오류');
				location.href = "/member/detail?memberSeq=" + memberSeq;
			}
		},
		error : function(request, status, error) {
			errorSubmit();
		}
	});
	console.log("ajax 실행 후");
}


	function errorSubmit() {
		$.ajax({
			type : 'POST',
			url : '오류로그수집서버',
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				xhr.setRequestHeader("apikey", "errorerrorerror");
			},
			data : {
				fromPath : "키오스크",
				method : "updateData",
				msg : "게시글 수정중 에러 발생",
				regId : "등록ID",
				siteId : "사이트ID",
				deviceId : "디바이스ID"
			},
			dataType : 'json',
			success : function(data) {
				const code = JSON.stringify(data.API_CODE['rtcode']).replace(/"/g, ''); // 따옴표 제거
				if (code === '200') {
					alert('저장중 오류 발생, 올바른 이미지 파일이 아닐 수 있습니다.');
					location.href = "/member/detail?memberSeq=" + memberSeq;
				}
			},
			error : function(xhr, status, error) {
			}
		});
	}
	
// 미리보기 이미지 제거 + 서버에 이미지 파일 삭제 요청을 수행
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
 
//업로드 대기 이미지 미리보기
function readURL(input) {
	document.getElementById("preview").style.display = "";
	if (input.files && input.files[0]) {
	var reader = new FileReader();
	reader.onload = function(e) {
	  document.getElementById('preview').src = e.target.result;
	};
	reader.readAsDataURL(input.files[0]);
	} else {
	  // document.getElementById('preview').src = "";
	  console.log("preview 이미지 없음");
	}
}
</script>
</head>
<body>
	<!-- 게시글 수정 취소 및 이전 페이지 이동 -->
	<button id="btn_cancel">취소</button>

	<form method='post' name='form0' id='form0' accept-charset='utf-8' enctype = "multipart/form-data" >
		<!-- enctype="multipart/form-data" -->
		<table class="table">
			<tr>
				<th>회원이름</th>
				<td>
					<input type="text" name="memberName" title="성함" onKeyup="this.value=this.value.replace(/[^ㄱ-ㅎ|가-힣|a-z|A-Z]/g,'');" id="name" value="${DataResult.memberName}">
					<input type="hidden" name="memberSeq" id="memberSeq" value="${DataResult.memberSeq}" />
				</td>
			</tr>
			<tr>
				<th>회원등급</th>
				<td>
					<select id="select" title="" name="memberGrade">
						<option value=""></option>
						<option value="A" <c:if test ="${DataResult.memberGrade eq 'A'}">selected="selected"</c:if>>A</option>
						<option value="B" <c:if test ="${DataResult.memberGrade eq 'B'}">selected="selected"</c:if>>B</option>
						<option value="C" <c:if test ="${DataResult.memberGrade eq 'C'}">selected="selected"</c:if>>C</option>
						<option value="D" <c:if test ="${DataResult.memberGrade eq 'D'}">selected="selected"</c:if>>D</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>라디오 버튼</th>
				<td>
					<input type="hidden" id="gender" name="memberUseYn" value="${DataResult.memberUseYn}">
					<input type="radio" name="radio" title="" value="Y">사용
					<input type="radio" name="radio" title="" value="N">비사용
				</td>
			</tr>
			<!-- 이미지 폼 -->
			<tr class="form_section">
				<th>이미지</th>
				<td>
				  <input type="file" id="uploadFile" name='uploadFile' onchange="readURL(this);" style="height: 30px;" multiple >
				  <input type="hidden" name="existUploadPath" id="existUploadPath" value="${ImageResult.uploadPath}" />
				  <input type="hidden" name="existUuid" id="existUuid" value="${ImageResult.uuid}" />
				  <input type="hidden" name="existFileName" id="existFileName" value="${ImageResult.fileName}" />
				  <input type="hidden" name="existThumbNailFileName" id="existThumbNailFileName" value="thumbnail_${ImageResult.fileName}" />
					
				  <div id="uploadResult">
					<img id="preview" />
				  </div>
				</td>
			</tr>
		</table>
	</form>

	<button id="btn_modify">수정</button>

	<script type="text/javascript">
		/* 라디오 버튼 체크 */
		var gender = $('#gender').val();
		if (gender === "1") {
			$("input:radio[name='radio']:radio[value='Y']").prop("checked",
					true);
		} else {
			$("input:radio[name='radio']:radio[value='N']").prop("checked",
					true);
		}
	</script>
</body>
</html>