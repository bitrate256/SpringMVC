package springMVC.dao.mybatis;

import springMVC.dao.BaseDAO;
import springMVC.utils.Criteria;
import springMVC.vo.BaseVO;
import springMVC.vo.BaseAPIKeyVO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BaseDAOImpl implements BaseDAO {

	@Autowired
	@Resource(name = "springMVCSession")
	private SqlSession springMVCSession;

	// API 헤더에 담긴 인증키값 조회
	@Override
	public int checkBaseApi(BaseAPIKeyVO baseAPIKeyVO) throws SQLException {
		return springMVCSession.selectOne("baseMapper.checkbaseApi", baseAPIKeyVO);
	}

	// CREATE
	// insert, 데이터 추가
	// '신규등록' 버튼
	@Override
	public int insertData(BaseVO baseVO) throws SQLException {
		return springMVCSession.insert("baseMapper.insertDataMssql", baseVO);
	}

	// SELECT
	// 리스트 (String / ModelMap 공용)
	@Override
	public List<Map<String, Object>> selectDataListPage(BaseVO baseVO, Criteria criteria) throws SQLException {

		// List<Map<String, Object>> 안에 객체를 더 전달하기 위한 Map 선언
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("baseVO", baseVO);
		paramMap.put("Criteria", criteria);
		return springMVCSession.selectList("baseMapper.selectDataListPage", paramMap);
	}

	// 토탈카운트
	@Override
	public int getTotalCount(Criteria criteria) throws SQLException {
		return springMVCSession.selectOne("baseMapper.getTotalCount", criteria);
	}

	// 데이터 상세조회
	@Override
	public List<Map<String, Object>> selectDataDetail(BaseVO baseVO) throws SQLException {
		// List<Map<String, Object>> 안에 객체를 더 전달하기 위한 Map 선언
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("baseVO", baseVO);
		return springMVCSession.selectList("baseMapper.selectDataDetail", paramMap);
	}

	// UPDATE
	// 데이터 업데이트, 전체 컬럼
	@Override
	public int updateData(BaseVO baseVO) throws SQLException {
		return springMVCSession.update("baseMapper.updateData", baseVO);
	}

	// DELETE
	// 데이터 삭제
	@Override
	public int deleteData(BaseVO baseVO) throws SQLException {
		return springMVCSession.delete("baseMapper.deleteData", baseVO);
	}
}
