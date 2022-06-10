package springMVC.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import springMVC.utils.Criteria;
import springMVC.vo.baseVO;

public interface BoardDAO {

	int getTotalCount(Criteria cri) throws SQLException;

	int regPost(baseVO adposVO) throws SQLException;

	baseVO boardDetail(baseVO adposVO) throws SQLException;

	int modifyPost(baseVO adposVO) throws SQLException;

	int deletePost(baseVO adposVO) throws SQLException;

	List<Map<String, Object>> selectDataListPage(baseVO adposVO, Criteria cri) throws SQLException;

}
