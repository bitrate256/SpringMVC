package springMVC.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import springMVC.utils.Criteria;
import springMVC.vo.ImageVO;
import springMVC.vo.MemberVO;

public interface MemberDAO {

	List<Map<String, Object>> selectDataListPage(MemberVO memberVO, Criteria criteria) throws SQLException;
	
	List<MemberVO> selectDataListPageExcel(MemberVO memberVO, Criteria criteria) throws SQLException;

	int getTotalCount(Criteria criteria) throws SQLException;

	MemberVO memberDetail(MemberVO memberVO) throws SQLException;

	int regMember(MemberVO memberVO) throws SQLException;

	int modifyMember(MemberVO memberVO) throws SQLException;

	int deleteMember(MemberVO memberVO) throws SQLException;
	
	/* 이미지 등록 */
	void insertImage(ImageVO imageVO) throws SQLException;
	
	// memberSeq 획득
	MemberVO getMemberSeq(MemberVO memberVO) throws SQLException;

	ImageVO getImage(int memberSeq) throws SQLException;
	
	// 이미지만 삭제
	void deleteImageOnly(int memberSeq) throws SQLException;
}
