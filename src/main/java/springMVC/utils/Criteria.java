package springMVC.utils;

import org.springframework.web.util.UriComponentsBuilder;

//페이징 처리 기준 클래스
public class Criteria {
	private int page; // 현재 페이지의 번호
	private int perPageNum; // 페이지당 출력할 레코드(게시글)의 갯수

	// 검색 조건 처리를 위한 criteria의 변화
	// type과 keyword 변수 추가. getTypeArr은 검색 조건이 T, W, C로 구성되어 있을 때 검색 조건을 배열로 만들어 한
	// 번에 처리하기 위함이다.
	// 외부에서 사용자가 선택한 카테고리를 대표하는 문자열을 type으로 전달받는다(페이지에서 제목 또는 내용 선택 시 "TC" 전달됨)
	// 사용자가 검색하고 싶은 키워드를 keyword로 전달받는다.
	private String searchType;
	private String keyword;

	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;
		this.searchType = null;
		this.keyword = null;
	}

	// pageStart를 반환
	public int getPageStart() {
		return (this.page - 1) * perPageNum;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if (page <= 0) {
			this.page = 1;
		} else {
			this.page = page;
		}
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		if (perPageNum <= 0 || perPageNum > 100) {
			this.perPageNum = 10;
		} else {
			this.perPageNum = perPageNum;
		}
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String makeQuery() {
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance().queryParam("page", this.page)
				.queryParam("perPageNum", this.perPageNum);

		if (searchType != null) {
			uriComponentsBuilder.queryParam("searchType", this.searchType).queryParam("keyword", this.keyword);
		}
		return uriComponentsBuilder.build().encode().toString();
	}

	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + "]";
	}

	// 검색 조건 처리를 위한 criteria의 변화
	// 사용자가 다중 항목을 선택했을 경우 각 항목을 분리해야 하기 때문에,
	// 단일 항목들을 문자열 타입으로 리턴해준다.
	// MyBatis에서는 getter를 찾아서 실행하므로, typeArr변수 선언 없이 getter만 선언한다.
	public String[] getTypeArr() {
		return searchType == null ? new String[] {} : searchType.split("");
	}

}