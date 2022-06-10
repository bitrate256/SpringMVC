<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
<!-- jquery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!-- datepicker -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script type="text/javascript" src="<c:url value="/resources/js/datepicker.js" />"></script>
<link rel="stylesheet" href="<c:url value="/resources/css/datepicker.css" />">
<!-- validation -->
<script type="text/javascript" src="<c:url value="/resources/js/validation_check.js" />"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/validation_check.js?v=<%=new java.util.Date().getTime()%>" ></script>
<script type="text/javascript">
$(function() {
	var $date = $('.docs-date');
	$date.datepicker({
		autoHide: true
		, format: "yyyy-mm-dd"
	});
});
$(document).on('click', '#btn_cancel', function() { // 이전 페이지 이동
	history.back();
});
$(document).on('click', '#btn_modify', function() {
	if(!item_check($('[id=name]'))) return;
	if(!item_check($('input[name=radio]'))) return;
	if(!item_check($('[id=title]'))) return;
	if(!item_check($('[id=titleEng]'))) return;
	if(!item_check($('[id=cnt]'))) return;
	if(!item_check($('[id=cntEng]'))) return;
	if(!item_check($('[id=tel]'))) return;
	if(!item_check($('[id=date]'))) return;
	if(!item_check($('[id=regNoFront]'))) return;
	if(!item_check($('select[id=select]'))) return;
	// ------------ o k ------------
	frmSubmit();
});

function fn_deleteFile(obj) {
   obj.parent().remove();
}
function fn_addFile() {
    var str = "<div class='file-group'><input type='file' id='uploadFile' name='uploadFile' /><a href='#this' name='file-delete'>삭제</a></div>";
    $("#file-list").append(str);
    $("a[name='file-delete']").on("click", function(e) {
      confirm("삭제 하시겠습니까?");
      e.preventDefault();
      var parent = $(this).closest('.file-group');
      var quantity = parent.find("#file_idx").val();
      if(quantity > 0){
        arrDel.push(quantity);
        fn_deleteFile($(this));
      }else{
        fn_deleteFile($(this));
      }
    });
}
$("a[name='file-delete']").on("click", function(e) {
  confirm("삭제 하시겠습니까?");
  e.preventDefault();
  var parent = $(this).closest('.file-group');
  var quantity = parent.find("#file_idx").val();
  if(quantity.length > 0){
      arrDel.push(quantity);
	  fn_deleteFile($(this));
    }else{
       fn_deleteFile($(this));
    }
});
$(document).on("change", "#uploadFile", function(){
  if($(this).val() != ""){
    //확장자 체크 
    var ext = $(this).val().split(".").pop().toLowerCase();
    if($.inArray(ext, ["gif", "jpg", "png"])==-1) {
      alert("gif, jpg, jpeg, png 파일만 업로드 해주세요.");
      $(this).val(""); 
      return;
    }//if
	for(var i=0; i<this.files.length; i++){
	  var fileSize = this.files[i].size; 
	  var fsmb = (fileSize / (1024 * 1024)).toFixed(2); 
	  var maxSize = 1024 * 1024 * 5;
	  var msmb = (maxSize / (1024 * 1024));
	  if(fileSize > maxSize){
		alert(this.files[i].name + "(이)가 용량 5MB를 초과했습니다."+"\n\n" + fsmb + "MB /" + msmb + "MB");
		$(this).val(""); 
	  }//if
    }//for 
  }//if
});

function frmSubmit() {
	var formData = $("#form0").serialize();
	$.ajax({
		type:'POST'
		, url:'/board/modify.do'
		, contentType : 'application/x-www-form-urlencoded; charset=utf-8'
		, data: formData
		, dataType : 'json'
		, success : function(data) {
			var bno = $('#bno').val();
			// JSON.stringify(data);
			// JSON.stringify(data.API_CODE);
			// JSON.stringify(data.API_CODE['rtcode'])
			var code = JSON.stringify(data.API_CODE['rtcode']).replace (/"/g,''); // 따옴표 제거
			if(code == '200') { alert('게시글 수정 성공'); location.href = "/board/detail?bno="+bno; }
		}
		, error : function(xhr, status, error) { }
	});
}

$(document).ready(function() {
  $("input:radio[name=radio]").click(function() { // 라디오 버튼 클릭 이벤트
	var vv = $('input:radio[name=radio]:checked').val();
    if(vv == '1') { $('#gender').val('1'); }
    else if(vv == '2') { $('#gender').val('2'); }
    else { $('#gender').val(''); }
  });
});
</script>
</head>
<body>
<!-- 게시글 수정 취소 및 이전 페이지 이동 -->
<button id="btn_cancel">취소</button>

<form method='post' name='form0' id='form0' accept-charset='utf-8' > <!-- enctype="multipart/form-data" -->
<table class="table">
  <tr>
    <th> ID </th>
    <td> <input type="text" name="ID" title="ID" 
    	  id="ID" value="${DataResult.ID}" >
    	  <input type="hidden" name="bno" id="bno" value="${DataResult.bno}" />
    </td>
  </tr>
  <tr>
    <th> 이름 </th>
    <td> <input type="text" name="name" title="성함" onKeyup="this.value=this.value.replace(/[^ㄱ-ㅎ|가-힣|a-z|A-Z]/g,'');" 
    	  id="name" value="${DataResult.name}" >
    </td>
  </tr>
  <tr>
    <th> 등급 </th>
    <td>
      <select id="select" title="" name="grade" >
        <option value=""></option>
        <option value="1" <c:if test ="${DataResult.grade eq '1'}">selected="selected"</c:if> >1</option>
        <option value="2" <c:if test ="${DataResult.grade eq '2'}">selected="selected"</c:if> >2</option>
      </select>
    </td>
  </tr>  
  <tr>
    <th> 라디오 버튼 </th>
    <td>
      <input type="hidden" id="gender" name="gender" value="${DataResult.gender}">
      <input type="radio" name="radio" title="" value="1"> 1
      <input type="radio" name="radio" title="" value="2"> 2
    </td>
  </tr>
  <tr>
    <th> 제목(국문) </th>
    <td> <input type="text" id="title" title="제목"
    	  name="titleKor" value="${DataResult.titleKor}" >
    </td>
  </tr>
  <tr>
    <th> 제목(영문) </th>
    <td> <input type="text" id="titleEng" title="제목" onKeyup="this.value=this.value.replace(/[^0-9|a-z|A-Z]/g,'');"
  	  name="titleEng" value="${DataResult.titleEng}" >
    </td>    
  </tr>
  <tr>
    <th> 내용(국문) </th>
    <td> <input type="text" id="cnt" title="내용"
    	  name="contentKor" value="${DataResult.contentKor}" >
    </td>
  </tr>
  <tr>
    <th> 내용(영문) </th>
    <td> <input type="text" id="cntEng" title="내용"
    	  name="contentEng" value="${DataResult.contentEng}" >
    </td>
  </tr>
  <tr>
    <th> 전화 </th>
    <td> <input type="tel" id="tel" title="전화번호" onKeyup="this.value=this.value.replace(/[^0-9|-]/g,'');"
    	  placeholder=" '-'포함하여 입력하세요." 
    	  name="phoneNum" value="${DataResult.phoneNum}" >
    </td>
  </tr>
  <tr>
    <th> 생년월일 </th>
    <td>
     <input type="text" id="date" class="docs-date" title="생년월일"
      name="birthDate" value="${DataResult.birthDate}" >
    </td>
  </tr>
  <tr>
    <th> 주민등록번호 앞자리 </th>
    <td> <input type="text" id="regNoFront" title="주민등록번호 앞자리" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');"
  	  	  name="juminNo" value="${DataResult.juminNo}" >
    </td>
  </tr>
  <!--
  <tr>
    <th> 첨부파일 </th>
    <td> <input type='file' name='file' /> </td>
  </tr>
  <tr>
    <th> 이미지 </th>
    <td> <input type='file' name='image' /> </td>
  </tr> -->
</table>
    <!--
    <div class="form-group" id="file-list">
      <a href="#this" onclick="fn_addFile()">파일추가</a>
    
        <div class="file-group">
            <input type='file' id='uploadFile' name='uploadFile' />
            <a href='#this' name='file-delete'>삭제</a> 
        </div>
    </div>-->
    
</form>

<button id="btn_modify">완료</button>

<script type="text/javascript">
/* 라디오 버튼 체크 */
var gender = $('#gender').val();
if(gender==="1") {
	$("input:radio[name='radio']:radio[value='1']").prop("checked",true);
}
else{ $("input:radio[name='radio']:radio[value='2']").prop("checked",true); }
</script>
</body>
</html>