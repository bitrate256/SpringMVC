package springMVC.service;

import springMVC.dao.BaseDAO;
import springMVC.utils.Criteria;
import springMVC.vo.BaseVO;
import springMVC.vo.BaseAPIKeyVO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseService {
	
	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private BaseDAO baseDAO;

	// API 헤더에 담긴 인증키값 조회
	public int checkBaseApi(String aPIKey) throws SQLException {
		BaseAPIKeyVO vo = new BaseAPIKeyVO();
		vo.setAPIKey(aPIKey);
		return baseDAO.checkBaseApi(vo);
	}

	// CREATE
	// insert, 레코드 추가
	// '신규등록' 버튼
	public int insertData(String ID, String name, String grade, int gender, String titleKor, String titleEng,
			String contentKor, String contentEng, String phoneNum, String birthDate, int juminNo, String registeredDate,
			String changedDate) throws SQLException {
		BaseVO vo = new BaseVO();
		vo.setID(ID);
		vo.setName(name);
		vo.setGrade(grade);
		vo.setGender(gender);
		vo.setTitleKor(titleKor);
		vo.setTitleEng(titleEng);
		vo.setContentKor(contentKor);
		vo.setContentEng(contentEng);
		vo.setPhoneNum(phoneNum);
		vo.setBirthDate(birthDate);
		vo.setJuminNo(juminNo);
		vo.setRegisteredDate(registeredDate);
		vo.setChangedDate(changedDate);
		return baseDAO.insertData(vo);
	}

	// SELECT
	// 리스트 (String / ModelMap 공용)
	public List<Map<String, Object>> selectDataListPage(String keyword, String startDate, String endDate, String searchType, Criteria criteria) throws SQLException {
		BaseVO vo = new BaseVO();
//		vo.setID(ID);
//		vo.setTitleKor(titleKor);
		vo.setKeyword(keyword);
//		vo.setRegisteredDate(registeredDate);
		vo.setStartDate(startDate);
		vo.setEndDate(endDate);
		vo.setSearchType(searchType);
//		log.debug("서비스 setID : " + ID);
//		log.debug("서비스 setTitleKor : " + titleKor);
		log.debug("서비스 setKeyword : " + keyword);
//		log.debug("서비스 setRegisteredDate : " + registeredDate);
		log.debug("서비스 setStartDate : " + startDate);
		log.debug("서비스 setEndDate : " + endDate);
		log.debug("서비스 setSearchType : " + searchType);

		return baseDAO.selectDataListPage(vo, criteria);
	}

	// 토탈카운트
	public int getTotalCount(Criteria criteria) throws SQLException {
		return baseDAO.getTotalCount(criteria);
	}

	// 데이터 상세조회
	public List<Map<String, Object>> selectDataDetail(int bno) throws SQLException {
		BaseVO vo = new BaseVO();
		vo.setBno(bno);
		log.debug("서비스 setBno : " + bno);

		return baseDAO.selectDataDetail(vo);
	}

	// UPDATE
	// 데이터 업데이트, 전체 컬럼
	public int updateData(int bno, String iD, String name, String grade, int gender, String titleKor, String titleEng,
			String contentKor, String contentEng, String phoneNum, String birthDate, int juminNo, String registeredDate,
			String changedDate) throws SQLException {
		BaseVO vo = new BaseVO();
		vo.setBno(bno);
		vo.setID(iD);
		vo.setName(name);
		vo.setGrade(grade);
		vo.setGender(gender);
		vo.setTitleKor(titleKor);
		vo.setTitleEng(titleEng);
		vo.setContentKor(contentKor);
		vo.setContentEng(contentEng);
		vo.setPhoneNum(phoneNum);
		vo.setBirthDate(birthDate);
		vo.setJuminNo(juminNo);
		vo.setChangedDate(changedDate);
		return baseDAO.updateData(vo);
	}

	// DELETE
	// 데이터 삭제
	public int deleteData(int bno) throws SQLException {
		BaseVO vo = new BaseVO();
		vo.setBno(bno);
		return baseDAO.deleteData(vo);
	}

	// 예외처리 테스트용 service
	public static List<HashMap<String, Object>> getTicketList() {
		// TODO Auto-generated method stub
		return null;
	}

	public static HashMap<String, Object> getTicketDetail(String ticketId) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<HashMap<String, Object>> getKioskDetailImgList(String ticketId) {
		// TODO Auto-generated method stub
		return null;
	}

	

	

}
