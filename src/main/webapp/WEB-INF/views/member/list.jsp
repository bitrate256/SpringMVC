<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>목록</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />"> 
<!-- jquery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!-- datepicker -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<!-- validation -->
<script type="text/javascript" src="<c:url value="/resources/js/validation_check.js" />"></script>
<script type="text/javascript">
function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
$(document).ready(function() { 
	// 파라미터 존재 유무 판단하여 검색 조건 유지
	var keyword = getParameterByName('keyword');
	if(keyword!=null || keyword!='') { $("#cntSearch").val(keyword); }
	if(keyword==null || keyword=='null') { $("#cntSearch").val(''); }
	var searchType = getParameterByName('searchType');
	if(searchType=='n') { $("#selectSearch").val(searchType).prop("selected", true); }
});//ready

$(document).on('click', '#btn_write', function() {
	location.href = "/member/write";
});
$(document).on('click', '#btn_search', function() {
	var st = $("#selectSearch").val();
	if(st=='') { st=null; }
	var kw = $("#cntSearch").val();
	if(kw=='') { kw=null; }
	location.href="/member/list"
		//+"?searchType="+$("#selectSearch option:selected").val()
		//+"&keyword="+$("#cntSearch").val()
		+"?searchType="+st
		+"&keyword="+kw
});
function chageSelectElement() {
	var st = $("#selectSearch").val();
	if(st=='') { st=null; }
	var kw = $("#cntSearch").val();
	if(kw=='') { kw=null; }
	location.href="/board/list"
		//+"?searchType="+$("#selectSearch option:selected").val()
		//+"&keyword="+$("#cntSearch").val()
		+"?searchType="+st
		+"&keyword="+kw
}
function errorSubmit() {
	var regDate = new Date();
	$.ajax({
		type:'POST'
		, url:'오류 로그 수집 서버'
		, beforeSend : function(xhr){ xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
									xhr.setRequestHeader("apikey", "errorerrorerror"); }
		, data: { fromPath: "키오스크"
				, method: "list"
				, msg: "리스트 조회중 에러 발생"
				, regId: "등록ID"
				, siteId: "사이트ID"
				, deviceId: "디바이스ID" }
		, dataType : 'json'
		, success : function(data) {
			var code = JSON.stringify(data.API_CODE['rtcode']).replace (/"/g,''); // 따옴표 제거
			if(code == '200') { alert('리스트 조회중 오류가 발생했습니다'); }
		}
		, error : function(xhr, status, error) { }
	});
}
</script>
<style type="text/css">
li {
	list-style: none;
	float: left;
	padding: 6px;
}
a:link { color: black; text-decoration: none; }
a:visited { color: black; text-decoration: none; }
a:hover { color: blue; text-decoration: underline; }
th { text-align: center; }
table { text-align: center; }
h1 { padding: 40px; }
</style>
</head>
<body>
<div>
  <select id="selectSearch" name="searchType" >
    <option value="" selected >전체</option>
    <option value="n">회원이름</option>
  </select>
  <input type="text" name="keyword" id="cntSearch" title="검색어" >
  <button id="btn_search">조회</button>
</div>

<div>
<!-- 게시글 작성 이동 -->
<button id="btn_write">등록</button>
<form action="/excel/download" method="get">
	<button type="submit">Excel</button>
</form>
</div>

<div id="page">
<table class="table">
  <thead>
    <tr>
      <th>번호</th>
      <th>회원이름</th>
      <th>회원등급</th>
      <th>사용유무</th>
      <th>등록일</th>
    </tr>
  </thead>
  <tbody>
   <c:forEach items="${DataListPageSearchResult}" var="member" >
    <tr>
      <td> ${member.memberSeq} </td>
      <td> <a href="/member/detail${pageMaker.makeQuery(pageMaker.criteria.page)}&memberSeq=${member.memberSeq}"> ${member.memberName} </a> </td>
      <td> ${member.memberGrade} </td>
      <td> ${member.memberUseYn} </td>
      <td> ${member.regDate} </td>
    </tr>
   </c:forEach>
  </tbody>
</table>

	<!-- paging -->
	<div class="text-center">
		<ul class="pagination">	
			<!-- 맨처음 버튼 -->
			<li>
				<a href="list${pageMaker.makeQuery(pageMaker.firstPage)}">맨처음</a>
			</li>
			<!-- 이전 버튼 -->
			<c:if test="${pageMaker.prev}">
				<li>
					<a href="list${pageMaker.makeQuery(pageMaker.startPage-1)}">이전</a>
				</li>
			</c:if>
			<!-- 페이지 번호 (시작 페이지 번호부터 끝 페이지 번호까지) -->
			<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var ="idx">
				<li <c:out value="${pageMaker.criteria.page == idx ? 'class=active' : '' }"/>>
					<a href="/member/list${pageMaker.makeQuery(idx)}">					
						<span>${idx}</span>
					</a>
				</li>
			</c:forEach>
			<!-- next 버튼 -->
			<c:if test="${pageMaker.next}">
				<li>
			    	<a href="list${pageMaker.makeQuery(pageMaker.endPage + 1)}">다음</a>
				</li>
			</c:if>
			<li>
				<a href="list${pageMaker.makeQuery(pageMaker.finalPage)}">맨끝</a>
			</li>
		</ul>
	</div>
	<!-- /paging -->
</div>

</body>
</html>