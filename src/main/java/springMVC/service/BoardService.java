package springMVC.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springMVC.dao.BoardDAO;
import springMVC.utils.Criteria;
import springMVC.vo.baseVO;

@Service
public class BoardService {

	@Autowired
	private BoardDAO BoardDAO;

	public int getTotalCount(Criteria cri) throws Exception {
		return BoardDAO.getTotalCount(cri);
	}

	public int regPost(baseVO adposVO) throws SQLException {
		return BoardDAO.regPost(adposVO);
	}

	public baseVO boardDetail(baseVO adposVO) throws SQLException {
		return BoardDAO.boardDetail(adposVO);
	}

	public int modifyPost(baseVO adposVO) throws SQLException {
		return BoardDAO.modifyPost(adposVO);
	}

	public int deletePost(baseVO adposVO) throws SQLException {
		return BoardDAO.deletePost(adposVO);
	}

	// 게시글 목록 검색 적용
	public List<Map<String, Object>> selectDataListPage(baseVO adposVO, Criteria cri) throws SQLException {
		return BoardDAO.selectDataListPage(adposVO, cri);
	}

}
