package springMVC.dao;

import springMVC.utils.Criteria;
import springMVC.vo.baseAPIKeyVO;
import springMVC.vo.baseVO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface baseDAO {

	// API 헤더에 담긴 인증키값 조회
	int checkBaseApi(baseAPIKeyVO vo) throws SQLException;

	// CREATE
	// insert, 데이터 추가
	// '신규등록' 버튼
	int insertData(baseVO vo) throws SQLException;

	// SELECT
	// 리스트 (String / ModelMap 공용)
	List<Map<String, Object>> selectDataListPage(baseVO vo, Criteria criteria) throws SQLException;
	// 토탈카운트
	int getTotalCount(Criteria criteria) throws SQLException;

	// 데이터 상세조회
	List<Map<String, Object>> selectDataDetail(baseVO vo) throws SQLException;

	// UPDATE
	// 데이터 업데이트, 전체 컬럼
	int updateData(baseVO vo) throws SQLException;

	// DELETE
	// 데이터 삭제
	int deleteData(baseVO vo) throws SQLException;
}
