<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
var bno = getParameterByName('bno');
$(document).on('click', '#btn_modify', function() {
	location.href='/board/modify?bno='+bno;
});
$(document).on('click', '#btn_delete', function() {
	var chk = confirm("정말 삭제하시겠습니까?");
	 if (chk) {
			$.ajax({
				type:'POST'
				, url : '/board/delete.do'
				, contentType : 'application/x-www-form-urlencoded; charset=utf-8'
				, data: { bno : bno }
				, dataType : 'json'
				, success : function(data) {
					// JSON.stringify(data);
					// JSON.stringify(data.API_CODE);
					// JSON.stringify(data.API_CODE['rtcode'])
					var code = JSON.stringify(data.API_CODE['rtcode']).replace (/"/g,''); // 따옴표 제거
					if(code == '200') { alert('게시글 삭제 성공'); location.href = "${pageContext.request.contextPath}/board/list"; }
				}
				, error : function(xhr, status, error) { }
			});
	   }
});
</script>
</head>
<body>
<!-- 게시글 목록 이동 -->

<button id="btn_list">
  <a href="/board/list${Criteria.makeQuery()}" > 목록 </a>
</button>


<table class="table">
  <tr>
    <th> ID </th>
    <td> ${DataResult.ID} </td>
  </tr>
  <tr>
    <th> 이름 </th>
    <td> ${DataResult.name} </td>
  </tr>
  <tr>
    <th> grade </th>
    <td> ${DataResult.grade} </td>
  </tr>  
  <tr>
    <th> gender </th>
    <td> ${DataResult.gender} </td>
  </tr>
  <tr>
    <th> 제목(국문) </th>
    <td> ${DataResult.titleKor} </td>
  </tr>
  <tr>
    <th> 제목(영문) </th>
    <td> ${DataResult.titleEng} </td>   
  </tr>
  <tr>
    <th> 내용(국문) </th>
    <td> ${DataResult.contentKor} </td>
  </tr>
  <tr>
    <th> 내용(영문) </th>
    <td> ${DataResult.contentEng} </td>
  </tr>
  <tr>
    <th> 전화 </th>
    <td> ${DataResult.phoneNum} </td>
  </tr>
  <tr>
    <th> 생년월일 </th>
    <td> ${DataResult.birthDate} </td>
  </tr>
  <tr>
    <th> 주민등록번호 앞자리 </th>
    <td> ${DataResult.juminNo} </td>
  </tr>
  
  <tr>
    <th> 작성일 </th>
    <td> ${DataResult.registeredDate} </td>
  </tr>
  <tr>
    <th> 수정일 </th>
    <td> ${DataResult.changedDate} </td>
  </tr>
  
</table>

<button id="btn_modify" >수정</button>
<button id="btn_delete" >삭제</button>
</body>
</html>