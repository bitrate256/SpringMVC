package springMVC.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springMVC.dao.BoardDAO;
import springMVC.utils.Criteria;
import springMVC.vo.BaseVO;

@Service
public class BoardService {

	@Autowired
	private BoardDAO BoardDAO;

	public int getTotalCount(Criteria cri) throws Exception {
		return BoardDAO.getTotalCount(cri);
	}

	public int regPost(BaseVO baseVO) throws SQLException {
		return BoardDAO.regPost(baseVO);
	}

	public BaseVO boardDetail(BaseVO baseVO) throws SQLException {
		return BoardDAO.boardDetail(baseVO);
	}

	public int modifyPost(BaseVO baseVO) throws SQLException {
		return BoardDAO.modifyPost(baseVO);
	}

	public int deletePost(BaseVO baseVO) throws SQLException {
		return BoardDAO.deletePost(baseVO);
	}

	// 게시글 목록 검색 적용
	public List<Map<String, Object>> selectDataListPage(BaseVO baseVO, Criteria cri) throws SQLException {
		return BoardDAO.selectDataListPage(baseVO, cri);
	}

}
