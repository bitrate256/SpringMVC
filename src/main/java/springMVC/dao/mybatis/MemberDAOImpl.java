package springMVC.dao.mybatis;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import springMVC.dao.MemberDAO;
import springMVC.utils.Criteria;
import springMVC.vo.ImageVO;
import springMVC.vo.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {
	
	// 로깅
	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	@Resource(name = "springMVCSession")
	private SqlSession springMVCSession;

	@Override
	public List<Map<String, Object>> selectDataListPage(MemberVO memberVO, Criteria criteria) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("MemberVO", memberVO);
		paramMap.put("Criteria", criteria);
		return springMVCSession.selectList("MemberMapper.selectMemberListPage", paramMap);
	}
	
	@Override
	public List<MemberVO> selectDataListPageExcel(MemberVO memberVO, Criteria criteria) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("MemberVO", memberVO);
		paramMap.put("Criteria", criteria);
		return springMVCSession.selectList("MemberMapper.selectDataListPageExcel", paramMap);
	}

	@Override
	public int getTotalCount(Criteria criteria) throws SQLException {
		return springMVCSession.selectOne("MemberMapper.getTotalCountMember", criteria);
	}

	@Override
	public MemberVO memberDetail(MemberVO memberVO) throws SQLException {
		return springMVCSession.selectOne("MemberMapper.memberDetail", memberVO);
	}

	@Override
	public int regMember(MemberVO memberVO) throws SQLException {
		return springMVCSession.insert("MemberMapper.regMember", memberVO);
	}

	@Override
	public int modifyMember(MemberVO memberVO) throws SQLException {
		return springMVCSession.update("MemberMapper.modifyMember", memberVO);
	}

	@Override
	public int deleteMember(MemberVO memberVO) throws SQLException {
		return springMVCSession.delete("MemberMapper.deleteMember", memberVO);
	}
	
	@Override
	public void insertImage(ImageVO imageVO) throws SQLException {
		springMVCSession.insert("ImageMapper.insertImage", imageVO);
	}
	// memberSeq 획득
	@Override
	public MemberVO getMemberSeq(MemberVO memberVO) throws SQLException {
		return springMVCSession.selectOne("MemberMapper.getMemberSeq", memberVO);
	}

	@Override
	public ImageVO getImage(int memberSeq) throws SQLException {
		return springMVCSession.selectOne("ImageMapper.getImage", memberSeq);
	}

	@Override
	public void deleteImageOnly(int memberSeq) throws SQLException {
		log.debug("DAO Impl");
		springMVCSession.delete("ImageMapper.deleteImageOnly", memberSeq);
		log.debug("이미지 삭제");
	}
}
