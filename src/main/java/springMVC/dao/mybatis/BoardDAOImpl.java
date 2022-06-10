package springMVC.dao.mybatis;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import springMVC.dao.BoardDAO;
import springMVC.utils.Criteria;
import springMVC.vo.baseVO;

@Repository
public class BoardDAOImpl implements BoardDAO {

	@Autowired
	@Resource(name = "springMVCSession")
	private SqlSession springMVCSession;

	@Override
	public int getTotalCount(Criteria cri) throws SQLException {
		return springMVCSession.selectOne("BoardMapper.getTotalCountMssql", cri);
	}

	@Override
	public int regPost(baseVO adposVO) throws SQLException {
		int rtCnt = springMVCSession.insert("BoardMapper.insertDataMssql", adposVO);
		return rtCnt;
	}

	@Override
	public baseVO boardDetail(baseVO adposVO) throws SQLException {
		baseVO DataResult = springMVCSession.selectOne("BoardMapper.selectDataDetailMssql", adposVO);
		return DataResult;
	}

	@Override
	public int modifyPost(baseVO adposVO) throws SQLException {
		int rtCnt = springMVCSession.update("BoardMapper.updateDataMssql", adposVO);
		return rtCnt;
	}

	@Override
	public int deletePost(baseVO adposVO) throws SQLException {
		int rtCnt = springMVCSession.delete("BoardMapper.deleteDataMssql", adposVO);
		return rtCnt;
	}

	@Override
	public List<Map<String, Object>> selectDataListPage(baseVO adposVO, Criteria cri) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("baseVO", adposVO);
		paramMap.put("Criteria", cri);
		List<Map<String, Object>> DataListPageSearch = springMVCSession.selectList("BoardMapper.selectDataListPage",
				paramMap);
		return DataListPageSearch;
	}

}