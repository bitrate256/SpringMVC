package springMVC.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import springMVC.utils.Criteria;
import springMVC.vo.BaseVO;

public interface BoardDAO {

	int getTotalCount(Criteria cri) throws SQLException;

	int regPost(BaseVO baseVO) throws SQLException;

	BaseVO boardDetail(BaseVO baseVO) throws SQLException;

	int modifyPost(BaseVO baseVO) throws SQLException;

	int deletePost(BaseVO baseVO) throws SQLException;

	List<Map<String, Object>> selectDataListPage(BaseVO baseVO, Criteria cri) throws SQLException;

}
