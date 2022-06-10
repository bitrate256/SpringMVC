package springMVC.vo;

import java.util.List;

public class MemberVO {

	private int memberSeq;
	private String memberName;
	private String memberGrade;
	private String memberUseYn;
	private String regDate;
	private String searchType;
	private String keyword;
	private String dbmsMode;

	/* 이미지 정보 리스트 */
	private List<ImageVO> imageList;

	public int getMemberSeq() {
		return memberSeq;
	}

	public void setMemberSeq(int memberSeq) {
		this.memberSeq = memberSeq;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberGrade() {
		return memberGrade;
	}

	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}

	public String getMemberUseYn() {
		return memberUseYn;
	}

	public void setMemberUseYn(String memberUseYn) {
		this.memberUseYn = memberUseYn;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
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

	public String getDbmsMode() {
		return dbmsMode;
	}

	public void setDbmsMode(String dbmsMode) {
		this.dbmsMode = dbmsMode;
	}

	public List<ImageVO> getImageList() {
		return imageList;
	}

	public void setImageList(List<ImageVO> imageList) {
		this.imageList = imageList;
	}

	@Override
	public String toString() {
		return "MemberVO [memberSeq=" + memberSeq + ", memberName=" + memberName + ", memberGrade=" + memberGrade
				+ ", memberUseYn=" + memberUseYn + ", regDate=" + regDate + ", searchType=" + searchType + ", keyword="
				+ keyword + ", dbmsMode=" + dbmsMode + ", uploadPath=" + ", uuid=" + ", fileName="
			    + ", imageList=" + imageList + "]";
	}

	public Object get(String memberSeq) {
		// TODO Auto-generated method stub
		return memberSeq;
	}
}