<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />"> 
<!-- jquery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!-- datepicker -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script type="text/javascript" src="<c:url value="/resources/js/datepicker.js" />"></script>
<link rel="stylesheet" href="<c:url value="/resources/css/datepicker.css" />">
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
	$(function() {
		var $startDate = $('.hasDatepicker1');
		var $endDate = $('.hasDatepicker2'); 
		$startDate.datepicker({
			autoHide: true
			, format: "yyyy-mm-dd"
		});
		$endDate.datepicker({
			autoHide: true
			, format: "yyyy-mm-dd"
			, startDate: $startDate.datepicker('getDate')
		});
		$startDate.on('change', function () {
			$endDate.datepicker('setStartDate', $startDate.datepicker('getDate'));
		});
	});
	// 파라미터 존재 유무 판단하여 검색 조건 유지
	var sdp = getParameterByName('startDate');
	var edp = getParameterByName('endDate');
	if(sdp!=null || edp!=null || sdp!='' || edp!='') {
		$("#startDate").val(sdp);
		$("#endDate").val(edp);
	}
	var keyword = getParameterByName('keyword');
	if(keyword!=null || keyword!='') { $("#cntSearch").val(keyword); }
	if(keyword==null || keyword=='null') { $("#cntSearch").val(''); }
	var searchType = getParameterByName('searchType');
	if(!searchType) { $("#selectSearch").val('w').prop("selected", true); }
	else { $("#selectSearch").val(searchType).prop("selected", true); }
});//ready


$(document).on('click', '#btn_write', function() {
	location.href = "/board/write";
});
$(document).on('click', '#btn_search', function() {
	var st = $("#selectSearch").val(); if(st=='') { st=null; }
	var kw = $("#cntSearch").val(); if(kw=='') { kw=null; }
	var sd = $("#startDate").val();
	var ed = $("#endDate").val();
	if(!sd || !ed || sd==null || ed==null || sd=='' || ed=='') {
		location.href="/board/list" 
			+"?searchType="+st
			+"&keyword="+kw
			+"&perPageNum="+$("#perPageNum option:selected").val()
	}else{
	location.href="/board/list" 
		+"?searchType="+st
		+"&keyword="+kw
		+"&perPageNum="+$("#perPageNum option:selected").val()
		+"&startDate="+sd
		+"&endDate="+ed
	}
});
function chageSelectElement() {
	var st = $("#selectSearch").val(); if(st=='') { st=null; }
	var kw = $("#cntSearch").val(); if(kw=='') { kw=null; }
	var sd = $("#startDate").val();
	var ed = $("#endDate").val();
	if(!st || st==null || st==''){
		if(!sd || !ed || sd==null || ed==null || sd=='' || ed=='') {
			location.href="/board/list" 
				+"?keyword="+kw
				+"&perPageNum="+$("#perPageNum option:selected").val()
		}else{
		location.href="/board/list" 
			+"?keyword="+kw
			+"&perPageNum="+$("#perPageNum option:selected").val()
			+"&startDate="+sd
			+"&endDate="+ed
		}
	}
	else{
	if(!sd || !ed || sd==null || ed==null || sd=='' || ed=='') {
		location.href="/board/list" 
			+"?searchType="+st
			+"&keyword="+kw
			+"&perPageNum="+$("#perPageNum option:selected").val()
	}else{
	location.href="/board/list" 
		+"?searchType="+st
		+"&keyword="+kw
		+"&perPageNum="+$("#perPageNum option:selected").val()
		+"&startDate="+sd
		+"&endDate="+ed
	}
	}
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
  <!-- datepicker -->
  <input type="text" class="hasDatepicker1" id="startDate" value="" >
  <span>~</span>
  <input type="text" class="hasDatepicker2" id="endDate" value="" >
  <!-- select box 조회(전체, 아이디, 제목) -->
  <select id="selectSearch" name="searchType" >
    <option value="w" selected >전체</option>
    <option value="t">제목</option>
    <option value="i">ID</option>
  </select>
  <input type="text" name="keyword" id="cntSearch" title="검색어" >
  <button id="btn_search">조회</button>
</div>

<div>
<!-- 게시글 삭제 이동 -->
<button>삭제</button>
<!-- 페이징 드롭다운 -->
<select name="selectSearch" id="perPageNum" onchange="chageSelectElement()" >
  <option value="10" <c:if test ="${Criteria.perPageNum eq 10}">selected="selected"</c:if> >10개씩 보기</option>
  <option value="15" <c:if test ="${Criteria.perPageNum eq 15}">selected="selected"</c:if> >15개씩 보기</option>
  <option value="20" <c:if test ="${Criteria.perPageNum eq 20}">selected="selected"</c:if> >20개씩 보기</option>
</select>
<!-- 게시글 작성 이동 -->
<button id="btn_write">등록</button>
</div>

<div id="page">
<table class="table">
  <thead>
    <tr>
      <th> <input type="checkbox"> </th>
      <th>제목</th>
      <th>ID</th>
      <th>등급</th>
      <th>등록일</th>
    </tr>
  </thead>
  <tbody>
   <c:forEach items="${DataListPageSearchResult}" var="board" >
    <tr>
      <td> <input type="checkbox"> </td>
      <td> <a href="/board/detail${pageMaker.makeQuery(pageMaker.criteria.page)}&bno=${board.bno}"> ${board.titleKor} </a> </td>
      <td> ${board.ID} </td>
      <td> ${board.grade} </td>
      <td> ${board.registeredDate} </td>
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
					<a href="/board/list${pageMaker.makeQuery(idx)}">					
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