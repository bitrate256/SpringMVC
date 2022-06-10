package springMVC.utils;

import org.springframework.web.util.UriComponentsBuilder;

//외부에서 입력되는 데이터 : page, perPageNum (Criteria 에서 넘어옴)
//DB에서 계산되는 데이터 : totalDataCount(데이터 전체 개수, 실제 게시물 수)
//계산을 통해 만들어지는 데이터 : startPage(시작페이지), endPage(끝 페이지), prev(이전 버튼 활성화 여부), next(다음 버튼 활성화 여부)

//PageMaker. 페이지 번호를 출력
public class PageMaker {
	private int displayPageCnt = 10; // 화면에 보여질 페이지 번호 수
	private int totalDataCount; // 실제 게시물 수
	private int startPage; // 현재 페이지 기준 시작 페이지 번호
	private int endPage; // 현재 페이지 기준 끝 페이지 번호
	private boolean prev; // 이전 버튼 활성화 여부
	private boolean next; // 다음 버튼 활성화 여부
	private Criteria criteria; // page(현재 페이지), perPageNum(페이지 당 보여질 게시물의 수)
	private int finalPage; // 맨끝
	private int firstPage; // 맨처음

	// 생성자
	public PageMaker(Criteria criteria) {
		this.criteria = criteria;
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	// 전체 게시물의 수를 입력 받음
	public void setTotalCount(int totalDataCount) {
		this.totalDataCount = totalDataCount;
		calcData();
	}

	// startPage, endPage, prev, next 를 계산
	public void calcData() {
		int page = this.criteria.getPage();
		int perPageNum = this.criteria.getPerPageNum();

		// 예: 현재 페이지가 13이면 13/10 = 1.3 올림-> 2 끝페이지는 2*10=20
		this.endPage = (int) (Math.ceil(page / (double) displayPageCnt) * displayPageCnt);

		// 예: 현재 페이지가 13이면 20-10 +1 = 11
		this.startPage = (this.endPage - displayPageCnt) + 1;

		// 실제로 사용되는 페이지의 수
		// 예: 전체 게시물 수가 88개이면 88/10=8.8 올림-> 9
		int tempEndPage = (int) (Math.ceil(totalDataCount / (double) perPageNum));

		// 만약 계산된 끝 페이지 번호보다 실제 사용되는 페이지 수가 더 작으면 실제 사용될 페이지 번호만 보여줌
		if (this.endPage > tempEndPage) {
			this.endPage = tempEndPage;
		}

		this.prev = (startPage != 1); // startPage 1이 아니면 false
		this.next = (endPage * perPageNum < totalDataCount); // 아직 더 보여질 페이지가 있으면 true
	}

	// 멤버변수 getter, setter
	public int getDisplayPageCnt() {
		return displayPageCnt;
	}

	public void setDisplayPageCnt(int displayPageCnt) {
		this.displayPageCnt = displayPageCnt;
	}

	public int getTotalDataCount() {
		return totalDataCount;
	}

	public void setTotalDataCount(int totalDataCount) {
		this.totalDataCount = totalDataCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getFinalPage() {
		return finalPage;
	}

	public void setFinalPage(int finalPage) {
		this.finalPage = finalPage;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public String makeQuery(int page) {
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance().queryParam("page", page)
				.queryParam("perPageNum", this.criteria.getPerPageNum());

		if (this.criteria.getSearchType() != null) {
			uriComponentsBuilder.queryParam("searchType", this.criteria.getSearchType()).queryParam("keyword",
					this.criteria.getKeyword());
		}
		return uriComponentsBuilder.build().encode().toString();
	}

}