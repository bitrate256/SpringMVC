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
import springMVC.vo.BaseVO;

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
	public int regPost(BaseVO baseVO) throws SQLException {
		return springMVCSession.insert("BoardMapper.insertDataMssql", baseVO);
	}

	@Override
	public BaseVO boardDetail(BaseVO baseVO) throws SQLException {
		return springMVCSession.selectOne("BoardMapper.selectDataDetailMssql", baseVO);
	}

	@Override
	public int modifyPost(BaseVO baseVO) throws SQLException {
		return springMVCSession.update("BoardMapper.updateDataMssql", baseVO);
	}

	@Override
	public int deletePost(BaseVO baseVO) throws SQLException {
		return springMVCSession.delete("BoardMapper.deleteDataMssql", baseVO);
	}

	@Override
	public List<Map<String, Object>> selectDataListPage(BaseVO baseVO, Criteria cri) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("BoardVO", baseVO);
		paramMap.put("Criteria", cri);
		return springMVCSession.selectList("BoardMapper.selectDataListPage", paramMap);
	}

}