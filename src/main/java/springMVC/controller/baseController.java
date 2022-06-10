package springMVC.controller;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import springMVC.service.baseService;
import springMVC.utils.Criteria;
import springMVC.utils.MapCodePut;
import springMVC.utils.PageMaker;
import springMVC.utils.RegularExpression;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class baseController {

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private baseService baseService;

	// CREATE
	// insert, 데이터 추가
	// '신규등록' 버튼
	@RequestMapping(value = "/pos/insert", method = RequestMethod.POST)
	public String insertData(HttpServletRequest request, @RequestBody HashMap<String, Object> param)
			throws IOException, SQLException, ParseException {
		log.debug("CREATE(Insert) 시작");
		/* result map 선언 */
		Map<String, Object> map_result = new HashMap<String, Object>();

		// 정규표현식 클래스 호출
		RegularExpression reg = new RegularExpression();

		// MapCode 함수 호출
		MapCodePut mapCodePut = new MapCodePut();

		/* 전달받은 APIKey 값 */
		String APIKey = request.getHeader("APIKey");
		log.debug("DataInsert APIKey값 / 대상 확인 : " + APIKey);
		// 전달받은 APIKey값 유효성 확인
		if (reg.isNumeric(APIKey) == false) {
			log.error("APIKey 값이 올바르지 않습니다 : " + APIKey);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / APIKey 값이 올바르지 않습니다");
			map_result.put("API_CODE", map_code);
			return map_result.toString();
		}

		/* insert할 값 받기 */
		String ID = param.get("ID").toString();
		String name = param.get("name").toString();
		String grade = param.get("grade").toString();
		int gender = Integer.parseInt(param.get("gender").toString());
		String titleKor = param.get("titleKor").toString();
		String titleEng = param.get("titleEng").toString();
		String contentKor = param.get("contentKor").toString();
		String contentEng = param.get("contentEng").toString();
		String phoneNum = param.get("phoneNum").toString();
		String birthDate = param.get("birthDate").toString();
		int juminNo = Integer.parseInt(param.get("juminNo").toString());
		/* insert할 값 유효성 검사 예시 (필요 따라서 조정할 것) */
		if (reg.isBlankSpecial(ID) == false || reg.isKorean(name) == false || reg.isMob1(phoneNum) == false) {
			log.error("저장할 값이 올바르지 않습니다 : " + ID + ", " + name + ", " + phoneNum);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터가 올바르지 않습니다");
			map_result.put("API_CODE", map_code);
			return map_result.toString();
		}

		// 현재 날짜 생성
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		// 등록일
		String registeredDate = format.format(nowDate);
		// 변경일
		String changedDate = format.format(nowDate);

		/* APIKey값을 DB 값과 대조 */
		int dbResultCnt = 0;
		try {
			// 저장된 APIKey값이 존재하면 그대로 진행
			dbResultCnt = baseService.checkBaseApi(APIKey);
			log.debug("확인한 키값 : " + dbResultCnt);
		} catch (Exception e) {
			// 확인되지 않으면 예외처리
			log.error("APIKey 확인 불가 ==> " + e);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / APIKey 값이 올바르지 않습니다");
			map_result.put("API_CODE", map_code);
			return map_result.toString();
		}

		// APIKey값이 일치하는 데이터가 1개 있을 때 확인 성공.
		if (dbResultCnt == 1) {
			/* db insert에 필요한 데이터 가져오기 */
			int rtCnt = 0;
			try {
				// 실제 저장
				rtCnt = baseService.insertData(ID, name, grade, gender, titleKor, titleEng, contentKor,
						contentEng, phoneNum, birthDate, juminNo, registeredDate, changedDate);
			} catch (Exception e) {
				// 저장중 오류시 예외처리
				log.error("DB 커넥션 오류 ==> " + e);
				Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터 저장중 오류가 발생했습니다");
				map_result.put("API_CODE", map_code);
				return map_result.toString();
			}
			if (rtCnt == 1) {
				// 저장 성공
				Map<String, Object> map_code = mapCodePut.MapCode("200", "OK", "성공");
				map_result.put("API_CODE", map_code);
				return map_result.toString();
			} else {
				// 저장 실패
				Map<String, Object> map_code = mapCodePut.MapCode("500", "Internal Server Error", "내부 서버 오류");
				map_result.put("API_CODE", map_code);
				return map_result.toString();
			}
		} else {
			// APIKey 확인결과 접근권한 없음
			Map<String, Object> map_code = mapCodePut.MapCode("403", "FORBIDDEN", "계정에 접근권한 없음");
			map_result.put("API_CODE", map_code);
			return map_result.toString();
		}
	}

	// SELECT
	// 리스트 (String)
	@RequestMapping(value = "/pos/listPage", method = RequestMethod.POST)
	public String selectDataListPage(HttpServletRequest request, @RequestBody HashMap<String, Object> param,
			Criteria criteria, Model model) throws IOException, SQLException, ParseException, Exception {
		System.out.println("DataController String 시작");

		// result map 선언 (최종 출력)
		Map<String, Object> map_result = new HashMap<String, Object>();

		// 정규표현식 클래스 호출
		RegularExpression reg = new RegularExpression();

		// MapCode 함수 호출
		MapCodePut mapCodePut = new MapCodePut();

		/* 전달받은 APIKey 값 */
		String APIKey = request.getHeader("APIKey");
		log.debug("DataInsert APIKey값 / 대상 확인 : " + APIKey);
		// 전달받은 APIKey값 유효성 확인
		if (reg.isNumeric(APIKey) == false) {
			log.error("APIKey 값이 올바르지 않습니다 : " + APIKey);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / APIKey 값이 올바르지 않습니다");
			map_result.put("API_CODE", map_code);
			return map_result.toString();
		}

		/* APIKey값을 DB 값과 대조 */
		int dbResultCnt = 0;
		try {
			// 저장된 APIKey값이 존재하면 그대로 진행
			dbResultCnt = baseService.checkBaseApi(APIKey);
			log.debug("확인한 키값 : " + dbResultCnt);
		} catch (Exception e) {
			// 확인되지 않으면 예외처리
			log.error("APIKey 확인 불가 ==> " + e);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / APIKey 값이 올바르지 않습니다");
			map_result.put("API_CODE", map_code);
			return map_result.toString();
		}

		if (dbResultCnt == 1) {
			// 정규표현식 클래스 호출
			RegularExpression re = new RegularExpression();

			// 검색조건은 keyword로 받음
			// 기본 값은 공백으로, 전달받은 keyword가 null값이 아닐 때 전달받은 keyword를 String으로 저장
			Object keywordObj = param.get("keyword");
			String keyword = "";
			if (keywordObj != null) {
				keyword = keywordObj.toString();
			}
			log.debug("String keyword : " + keyword);

			// registeredDate 검색조건, 유효성 검사 적용하지 않음
			// startDate - endDate
			Object startDateObj = param.get("startDate");
			String startDate = "";
			if (startDateObj != null) {
				startDate = startDateObj.toString();
			}
			log.debug("String keyword : " + startDate);

			Object endDateObj = param.get("endDate");
			String endDate = "";
			if (endDateObj != null) {
				endDate = endDateObj.toString();
			}
			log.debug("String endDate : " + endDate);

			// 검색타입
			Object searchTypeObj = param.get("searchType");
			String searchType = "";
			if (searchTypeObj != null) {
				searchType = searchTypeObj.toString();
			}
			log.debug("String searchType : " + searchType);

			// 조회
			final List<Map<String, Object>> DataListPageSearch;
			try {
				// 검색조건과 페이징으로 실제 조회
				DataListPageSearch = baseService.selectDataListPage(keyword, startDate, endDate, searchType, criteria);
			} catch (Exception e) {
				// 조회중 오류시 예외처리
				log.error("DB 커넥션 오류 ==> " + e);
//				e.getStackTrace();
				Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터 조회중 오류가 발생했습니다");
				map_result.put("API_CODE", map_code);
				return map_result.toString();
			}

			log.debug("리스트 DataListPageSearch : " + DataListPageSearch);

			// 조회한 리스트를 모델에 추가
			model.addAttribute("list", DataListPageSearch);
			// PageMaker 객체 생성
			PageMaker pageMaker = new PageMaker(criteria);

			// 토탈카운트 (전체 게시물 수를 조회)
			int totalCount = baseService.getTotalCount(criteria);
			log.debug("int totalCount : " + totalCount);
			// pageMaker로 전달 -> pageMaker는 startPage, endPage, prev, next를 계산함
			pageMaker.setTotalCount(totalCount);
			// pageMaker의 계산결과를 모델에 추가
			model.addAttribute("pageMaker", pageMaker);
			log.debug("PageMaker : " + pageMaker);

			if (DataListPageSearch.isEmpty()) {
				Map<String, Object> map_code = mapCodePut.MapCode("200", "OK", "성공 / 검색결과 없음");
				map_result.put("API_CODE", map_code);
				Map<String, Object> map_empty = mapCodePut.MapEmpty("", "", "", "", "", "", "");
				DataListPageSearch.add(map_empty);
				map_result.put("DataListPageSearchResult", DataListPageSearch);
				return map_result.toString();
			} else {
				Map<String, Object> map_code = mapCodePut.MapCode("200", "OK", "성공");
				map_result.put("API_CODE", map_code);
				map_result.put("DataListPageSearchResult", DataListPageSearch);
				return map_result.toString();
			}
		} else {
			// APIKey 확인결과 접근권한 없음
			Map<String, Object> map_code = mapCodePut.MapCode("403", "FORBIDDEN", "계정에 접근권한 없음");
			map_result.put("API_CODE", map_code);
			return map_result.toString();
		}
	}

	// SELECT
	// 리스트 (ModelMap)
	@RequestMapping(value = "/pos/listPage/returnModelMap", method = RequestMethod.POST)
	public Object selectDataListPageModelMap(HttpServletRequest request, @RequestBody HashMap<String, Object> param,
			Criteria criteria, Model model) throws IOException, SQLException, ParseException, Exception {
		System.out.println("DataController ModelMap 시작");

		// 모델맵 선언
		ModelMap modelMap = new ModelMap();

		// result map 선언 (최종 출력)
		Map<String, Object> map_result = new HashMap<String, Object>();

		// 정규표현식 클래스 호출
		RegularExpression reg = new RegularExpression();

		// MapCode 함수 호출
		MapCodePut mapCodePut = new MapCodePut();

		/* 전달받은 APIKey 값 */
		String APIKey = request.getHeader("APIKey");
		log.debug("DataInsert APIKey값 / 대상 확인 : " + APIKey);
		// 전달받은 APIKey값 유효성 확인
		if (reg.isNumeric(APIKey) == false) {
			log.error("APIKey 값이 올바르지 않습니다 : " + APIKey);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / APIKey 값이 올바르지 않습니다");
			map_result.put("API_CODE", map_code);
			modelMap.addAttribute("map_result", map_result);
			return modelMap;
		}

		/* APIKey값을 DB 값과 대조 */
		int dbResultCnt = 0;
		try {
			// 저장된 APIKey값이 존재하면 그대로 진행
			dbResultCnt = baseService.checkBaseApi(APIKey);
			log.debug("확인한 키값 : " + dbResultCnt);
		} catch (Exception e) {
			// 확인되지 않으면 예외처리
			log.error("APIKey 확인 불가 ==> " + e);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / APIKey 값이 올바르지 않습니다");
			map_result.put("API_CODE", map_code);
			modelMap.addAttribute("map_result", map_result);
			return modelMap;
		}

		if (dbResultCnt == 1) {
			// 정규표현식 클래스 호출
			RegularExpression re = new RegularExpression();

			// 검색조건은 keyword로 받음
			// 기본 값은 공백으로, 전달받은 keyword가 null값이 아닐 때 전달받은 keyword를 String으로 저장
			Object keywordObj = param.get("keyword");
			String keyword = "";
			if (keywordObj != null) {
				keyword = keywordObj.toString();
			}
			log.debug("String keyword : " + keyword);

			// registeredDate 검색조건, 유효성 검사 적용하지 않음
			// startDate - endDate
			Object startDateObj = param.get("startDate");
			String startDate = "";
			if (startDateObj != null) {
				startDate = startDateObj.toString();
			}
			log.debug("String keyword : " + startDate);

			Object endDateObj = param.get("endDate");
			String endDate = "";
			if (endDateObj != null) {
				endDate = endDateObj.toString();
			}
			log.debug("String endDate : " + endDate);

			// 검색타입
			Object searchTypeObj = param.get("searchType");
			String searchType = "";
			if (searchTypeObj != null) {
				searchType = searchTypeObj.toString();
			}
			log.debug("String searchType : " + searchType);

			// 조회
			final List<Map<String, Object>> DataListPageSearch;
			try {
				// 검색조건과 페이징으로 실제 조회
				DataListPageSearch = baseService.selectDataListPage(keyword, startDate, endDate, searchType,
						criteria);
			} catch (Exception e) {
				// 조회중 오류시 예외처리
				log.error("DB 커넥션 오류 ==> " + e);
				Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터 조회중 오류가 발생했습니다");
				map_result.put("API_CODE", map_code);
				modelMap.addAttribute("map_result", map_result);
				return modelMap;
			}

			log.debug("리스트 DataListPageSearch : " + DataListPageSearch);

			// PageMaker 객체 생성
			PageMaker pageMaker = new PageMaker(criteria);
			// 토탈카운트 (전체 게시물 수를 조회)
			int totalCount = baseService.getTotalCount(criteria);
			log.debug("int totalCount : " + totalCount);
			// pageMaker로 전달 -> pageMaker는 startPage, endPage, prev, next를 계산함
			pageMaker.setTotalCount(totalCount);
			// pageMaker의 계산결과를 모델에 추가
			modelMap.put("pageMaker", pageMaker);
			log.debug("PageMaker : " + pageMaker);

			if (DataListPageSearch.isEmpty()) {
				Map<String, Object> map_code = mapCodePut.MapCode("200", "OK", "성공 / 검색결과 없음");
				map_result.put("API_CODE", map_code);
				Map<String, Object> map_empty = mapCodePut.MapEmpty("", "", "", "", "", "", "");
				DataListPageSearch.add(map_empty);
				map_result.put("DataListPageSearchResult", DataListPageSearch);
				modelMap.addAttribute("map_result", map_result);
				return modelMap;
			} else {
				Map<String, Object> map_code = mapCodePut.MapCode("200", "OK", "성공");
				map_result.put("API_CODE", map_code);
				map_result.put("DataListPageSearchResult", DataListPageSearch);
				modelMap.addAttribute("map_result", map_result);
				return modelMap;
			}
		} else {
			// APIKey 확인결과 접근권한 없음
			Map<String, Object> map_code = mapCodePut.MapCode("403", "FORBIDDEN", "계정에 접근권한 없음");
			map_result.put("API_CODE", map_code);
			modelMap.addAttribute("map_result", map_result);
			return modelMap;
		}
	}

	// SELECT
	// 데이터 상세조회
	@RequestMapping(value = "/pos/detail", method = RequestMethod.POST)
	public String selectDataDetail(HttpServletRequest request, @RequestBody HashMap<String, Object> param,
			Criteria criteria, Model model) throws IOException, SQLException, ParseException, Exception {
		System.out.println("selectDataDetail 시작");

		// result map 선언 (최종 출력)
		Map<String, Object> map_result = new HashMap<String, Object>();

		// 정규표현식 클래스 호출
		RegularExpression reg = new RegularExpression();

		// MapCode 함수 호출
		MapCodePut mapCodePut = new MapCodePut();

		/* 전달받은 APIKey 값 */
		String APIKey = request.getHeader("APIKey");
		log.debug("DataInsert APIKey값 / 대상 확인 : " + APIKey);
		// 전달받은 APIKey값 유효성 확인
		if (reg.isNumeric(APIKey) == false) {
			log.error("APIKey 값이 올바르지 않습니다 : " + APIKey);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / APIKey 값이 올바르지 않습니다");
			map_result.put("API_CODE", map_code);
			return map_result.toString();
		}

		/* APIKey값을 DB 값과 대조 */
		int dbResultCnt = 0;
		try {
			// 저장된 APIKey값이 존재하면 그대로 진행
			dbResultCnt = baseService.checkBaseApi(APIKey);
			log.debug("확인한 키값 : " + dbResultCnt);
		} catch (Exception e) {
			// 확인되지 않으면 예외처리
			log.error("APIKey 확인 불가 ==> " + e);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / APIKey 값이 올바르지 않습니다");
			map_result.put("API_CODE", map_code);
			return map_result.toString();
		}

		if (dbResultCnt == 1) {
			// 정규표현식 클래스 호출
			RegularExpression re = new RegularExpression();

			// 글번호, 유효성 검사 적용하지 않음
			int bno = Integer.parseInt((String) param.get("bno"));
			log.debug("String bno : " + bno);

			// 조회
			final List<Map<String, Object>> DataDetail;
			try {
				// 글번호로 실제 조회
				DataDetail = baseService.selectDataDetail(bno);
			} catch (Exception e) {
				// 조회중 오류시 예외처리
				log.error("DB 커넥션 오류 ==> " + e);
				Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터 조회중 오류가 발생했습니다");
				map_result.put("API_CODE", map_code);
				return map_result.toString();
			}

			log.debug("DataDetail : " + DataDetail);

			if (DataDetail.isEmpty()) {
				Map<String, Object> map_code = mapCodePut.MapCode("404", "NOT FOUND", "요청한 페이지를 찾을 수 없습니다");
				map_result.put("API_CODE", map_code);
				Map<String, Object> map_empty = mapCodePut.MapEmpty("", "", "", "", "", "", "");
				DataDetail.add(map_empty);
				map_result.put("DataDetailResult", DataDetail);
				return map_result.toString();
			} else {
				Map<String, Object> map_code = mapCodePut.MapCode("200", "OK", "성공");
				map_result.put("API_CODE", map_code);
				map_result.put("DataDetailResult", DataDetail);
				return map_result.toString();
			}
		} else {
			// APIKey 확인결과 접근권한 없음
			Map<String, Object> map_code = mapCodePut.MapCode("403", "FORBIDDEN", "계정에 접근권한 없음");
			map_result.put("API_CODE", map_code);
			return map_result.toString();
		}
	}

	// UPDATE
	// 데이터 업데이트, 전체 컬럼
	@RequestMapping(value = "/pos/update", method = RequestMethod.POST)
	public String updateData(HttpServletRequest request, @RequestBody HashMap<String, Object> param)
			throws IOException, SQLException, ParseException {
		log.debug("Update 시작");
		/* result map 선언 */
		Map<String, Object> map_result = new HashMap<String, Object>();

		// 정규표현식 클래스 호출
		RegularExpression reg = new RegularExpression();

		// MapCode 함수 호출
		MapCodePut mapCodePut = new MapCodePut();

		/* 전달받은 APIKey 값 */
		String APIKey = request.getHeader("APIKey");
		log.debug("DataInsert APIKey값 / 대상 확인 : " + APIKey);
		// 전달받은 APIKey값 유효성 확인
		if (reg.isNumeric(APIKey) == false) {
			log.error("APIKey 값이 올바르지 않습니다 : " + APIKey);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / APIKey 값이 올바르지 않습니다");
			map_result.put("API_CODE", map_code);
			return map_result.toString();
		}

		/* update할 값 받기 */
		int bno = Integer.parseInt(param.get("bno").toString());
		System.out.println("bno : " + bno);
		String iD = param.get("ID").toString();
		String name = param.get("name").toString();
		String grade = param.get("grade").toString();
		int gender = Integer.parseInt(param.get("gender").toString());
		String titleKor = param.get("titleKor").toString();
		String titleEng = param.get("titleEng").toString();
		String contentKor = param.get("contentKor").toString();
		String contentEng = param.get("contentEng").toString();
		String phoneNum = param.get("phoneNum").toString();
		String birthDate = param.get("birthDate").toString();
		int juminNo = Integer.parseInt(param.get("juminNo").toString());
		/* insert할 값 유효성 검사 예시 (필요 따라서 조정할 것) */
		if (reg.isBlankSpecial(iD) == false || reg.isKorean(name) == false || reg.isMob1(phoneNum) == false) {
			log.error("저장할 값이 올바르지 않습니다 : " + iD + ", " + name + ", " + phoneNum);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터가 올바르지 않습니다");
			map_result.put("API_CODE", map_code);
			return map_result.toString();
		}

		// 현재 날짜 생성
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		// 등록일
		String registeredDate = format.format(nowDate);
		// 변경일
		String changedDate = format.format(nowDate);

		/* APIKey값을 DB 값과 대조 */
		int dbResultCnt = 0;
		try {
			// 저장된 APIKey값이 존재하면 그대로 진행
			dbResultCnt = baseService.checkBaseApi(APIKey);
			log.debug("확인한 키값 : " + dbResultCnt);
		} catch (Exception e) {
			// 확인되지 않으면 예외처리
			log.error("APIKey 확인 불가 ==> " + e);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / APIKey 값이 올바르지 않습니다");
			map_result.put("API_CODE", map_code);
			return map_result.toString();

		}

		// APIKey값이 일치하는 데이터가 1개 있을 때 확인 성공.
		if (dbResultCnt == 1) {
			/* db insert에 필요한 데이터 가져오기 */
			int rtCnt = 0;
			try {
				// 실제 저장
				rtCnt = baseService.updateData(bno, iD, name, grade, gender, titleKor, titleEng, contentKor,
						contentEng, phoneNum, birthDate, juminNo, registeredDate, changedDate);
			} catch (Exception e) {
				// 저장중 오류시 예외처리
				log.error("DB 커넥션 오류 ==> " + e);
				Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터 저장중 오류가 발생했습니다");
				map_result.put("API_CODE", map_code);
				return map_result.toString();
			}
			if (rtCnt == 1) {
				// 저장 성공
				Map<String, Object> map_code = mapCodePut.MapCode("200", "OK", "성공");
				map_result.put("API_CODE", map_code);
				return map_result.toString();
			} else if (rtCnt == 0) {
				// 저장 실패 (대상 데이터를 찾을 수 없음)
				Map<String, Object> map_code = mapCodePut.MapCode("404", "Not Found", "대상 데이터를 찾을 수 없음");
				map_result.put("API_CODE", map_code);
				return map_result.toString();
			} else {
				// 저장 실패 (기타)
				Map<String, Object> map_code = mapCodePut.MapCode("500", "Internal Server Error", "내부 서버 오류");
				map_result.put("API_CODE", map_code);
				return map_result.toString();
			}
		} else {
			// APIKey 확인결과 접근권한 없음
			Map<String, Object> map_code = mapCodePut.MapCode("403", "FORBIDDEN", "계정에 접근권한 없음");
			map_result.put("API_CODE", map_code);
			return map_result.toString();
		}
	}

	// DELETE
	// 데이터 삭제
	@RequestMapping(value = "/pos/delete", method = RequestMethod.POST)
	public String deleteData(HttpServletRequest request, @RequestBody HashMap<String, Object> param)
			throws IOException, SQLException, ParseException {
		log.debug("Delete 시작");
		/* result map 선언 */
		Map<String, Object> map_result = new HashMap<String, Object>();

		// 정규표현식 클래스 호출
		RegularExpression reg = new RegularExpression();

		// MapCode 함수 호출
		MapCodePut mapCodePut = new MapCodePut();

		/* 전달받은 APIKey 값 */
		String APIKey = request.getHeader("APIKey");
		log.debug("DataInsert APIKey값 / 대상 확인 : " + APIKey);
		// 전달받은 APIKey값 유효성 확인
		if (reg.isNumeric(APIKey) == false) {
			log.error("APIKey 값이 올바르지 않습니다 : " + APIKey);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / APIKey 값이 올바르지 않습니다");
			map_result.put("API_CODE", map_code);
			return map_result.toString();
		}

		/* APIKey값을 DB 값과 대조 */
		int dbResultCnt = 0;
		try {
			// 저장된 APIKey값이 존재하면 그대로 진행
			dbResultCnt = baseService.checkBaseApi(APIKey);
			log.debug("확인한 키값 : " + dbResultCnt);
		} catch (Exception e) {
			// 확인되지 않으면 예외처리
			log.error("APIKey 확인 불가 ==> " + e);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / APIKey 값이 올바르지 않습니다");
			map_result.put("API_CODE", map_code);
			return map_result.toString();
		}

		// APIKey값이 일치하는 데이터가 1개 있을 때 확인 성공.
		if (dbResultCnt == 1) {
			/* db insert에 필요한 데이터 가져오기 */
			int rtCnt = 0;
			int bno = Integer.parseInt(param.get("bno").toString());
			try {
				// 실제 삭제
				rtCnt = baseService.deleteData(bno);
			} catch (Exception e) {
				// 저장중 오류시 예외처리
				log.error("DB 커넥션 오류 ==> " + e);
				Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터 삭제중 오류가 발생했습니다");
				map_result.put("API_CODE", map_code);
				return map_result.toString();
			}
			if (rtCnt == 1) {
				// 삭제 성공
				Map<String, Object> map_code = mapCodePut.MapCode("200", "OK", "성공");
				map_result.put("API_CODE", map_code);
				return map_result.toString();
			} else if (rtCnt == 0) {
				// 삭제 실패 (대상 데이터를 찾을 수 없음)
				Map<String, Object> map_code = mapCodePut.MapCode("404", "Not Found", "대상 데이터를 찾을 수 없음");
				map_result.put("API_CODE", map_code);
				return map_result.toString();
			} else {
				// 삭제 실패 (기타)
				Map<String, Object> map_code = mapCodePut.MapCode("500", "Internal Server Error", "내부 서버 오류");
				map_result.put("API_CODE", map_code);
				return map_result.toString();
			}
		} else {
			// APIKey 확인결과 접근권한 없음
			Map<String, Object> map_code = mapCodePut.MapCode("403", "FORBIDDEN", "계정에 접근권한 없음");
			map_result.put("API_CODE", map_code);
			return map_result.toString();
		}
	}

}
