package springMVC.dao.mybatis;

import springMVC.dao.baseDAO;
import springMVC.utils.Criteria;
import springMVC.vo.baseAPIKeyVO;
import springMVC.vo.baseVO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class baseDAOImpl implements baseDAO {

	@Autowired
	@Resource(name = "springMVCSession")
	private SqlSession springMVCSession;

	// API 헤더에 담긴 인증키값 조회
	@Override
	public int checkBaseApi(baseAPIKeyVO vo) throws SQLException {
		int dbResultCnt = springMVCSession.selectOne("baseMapper.checkbaseApi", vo);
		return dbResultCnt;
	}

	// CREATE
	// insert, 데이터 추가
	// '신규등록' 버튼
	@Override
	public int insertData(baseVO vo) throws SQLException {
		int insertData = springMVCSession.insert("baseMapper.insertDataMssql", vo);
		return insertData;
	}

	// SELECT
	// 리스트 (String / ModelMap 공용)
	@Override
	public List<Map<String, Object>> selectDataListPage(baseVO vo, Criteria criteria) throws SQLException {

		// List<Map<String, Object>> 안에 객체를 더 전달하기 위한 Map 선언
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("baseVO", vo);
		paramMap.put("Criteria", criteria);
		List<Map<String, Object>> DataListPage = springMVCSession.selectList("baseMapper.selectDataListPage", paramMap);
		return DataListPage;
	}

	// 토탈카운트
	@Override
	public int getTotalCount(Criteria criteria) throws SQLException {
		int getTotalCount = springMVCSession.selectOne("baseMapper.getTotalCount", criteria);
		return getTotalCount;
	}

	// 데이터 상세조회
	@Override
	public List<Map<String, Object>> selectDataDetail(baseVO vo) throws SQLException {
		// List<Map<String, Object>> 안에 객체를 더 전달하기 위한 Map 선언
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("baseVO", vo);
		List<Map<String, Object>> DataDetail = springMVCSession.selectList("baseMapper.selectDataDetail", paramMap);
		return DataDetail;
	}

	// UPDATE
	// 데이터 업데이트, 전체 컬럼
	@Override
	public int updateData(baseVO vo) throws SQLException {
		int updateData = springMVCSession.update("baseMapper.updateData", vo);
		return updateData;
	}

	// DELETE
	// 데이터 삭제
	@Override
	public int deleteData(baseVO vo) throws SQLException {
		int deleteData = springMVCSession.delete("baseMapper.deleteData", vo);
		return deleteData;
	}
}
