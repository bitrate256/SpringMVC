package springMVC.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import springMVC.service.BoardService;
import springMVC.utils.MapCodePut;
import springMVC.utils.RegularExpression;
import springMVC.vo.baseVO;

@RestController
public class BoardRestController {

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private BoardService BoardService;

	/*
	 * 게시글 등록
	 */
	@RequestMapping(value = "/board/register.do", method = RequestMethod.POST, produces = "application/text; charset=UTF-8")
	public String regPost(HttpServletResponse response, HttpServletRequest request, baseVO adposVO)
			throws IOException, SQLException, ParseException {

		/* result map 선언 */
		Map<String, Object> map_result = new HashMap<String, Object>();
		// 정규표현식 클래스 호출
		RegularExpression reg = new RegularExpression();
		// MapCode 함수 호출
		MapCodePut mapCodePut = new MapCodePut();
		// 현재 날짜 생성
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		String today = sdf.format(nowDate);
		// JSON
		ObjectMapper mapper = new ObjectMapper();
		//
		adposVO.setRegisteredDate(today);

		/*
		 * insert할 값 유효성 검사 예시 (필요 따라서 조정할 것) if (reg.isBlankSpecial(adposVO.getID()) ==
		 * false || reg.isKorean(adposVO.getName()) == false ||
		 * reg.isMob1(adposVO.getPhoneNum()) == false) {
		 * log.error("저장할 값이 올바르지 않습니다 >>>> ID :" + adposVO.getID() + ", Name : " +
		 * adposVO.getName() + ", phone : " + adposVO.getPhoneNum()); Map<String,
		 * Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST",
		 * "잘못된 요청 / 데이터가 올바르지 않습니다"); map_result.put("API_CODE", map_code); return new
		 * ResponseEntity<Map<String, Object>>(map_result, HttpStatus.BAD_REQUEST); }
		 */

		int rtCnt = 0;
		try {
			rtCnt = BoardService.regPost(adposVO);
		} catch (Exception e) {
			// 저장중 오류시 예외처리
			log.error("DB 커넥션 오류 ==> " + e);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터 저장 중 오류가 발생했습니다");
			map_result.put("API_CODE", map_code);
			String json = mapper.writeValueAsString(map_result);
			return json;
		}
		if (rtCnt == 1) {
			// 저장 성공
			Map<String, Object> map_code = mapCodePut.MapCode("200", "OK", "성공");
			map_result.put("API_CODE", map_code);
			String json = mapper.writeValueAsString(map_result);
			return json;
		} else {
			// 저장 실패
			Map<String, Object> map_code = mapCodePut.MapCode("500", "Internal Server Error", "내부 서버 오류");
			map_result.put("API_CODE", map_code);
			String json = mapper.writeValueAsString(map_result);
			return json;
		}
	}

	/*
	 * 게시글 수정
	 */
	@RequestMapping(value = "/board/modify.do", method = RequestMethod.POST, produces = "application/text; charset=UTF-8")
	public String updateData(HttpServletResponse response, HttpServletRequest request, baseVO adposVO)
			throws IOException, SQLException, ParseException {

		/* result map 선언 */
		Map<String, Object> map_result = new HashMap<String, Object>();
		// 정규표현식 클래스 호출
		RegularExpression reg = new RegularExpression();
		// MapCode 함수 호출
		MapCodePut mapCodePut = new MapCodePut();
		// 현재 날짜 생성
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		String today = sdf.format(nowDate);
		// JSON
		ObjectMapper mapper = new ObjectMapper();
		//
		adposVO.setChangedDate(today);

		int rtCnt = 0;
		try {
			rtCnt = BoardService.modifyPost(adposVO);
		} catch (Exception e) {
			// 저장 중 오류시 예외처리
			log.error("DB 커넥션 오류 ==> " + e);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터 저장중 오류가 발생했습니다");
			map_result.put("API_CODE", map_code);
			String json = mapper.writeValueAsString(map_result);
			return json;
		}
		if (rtCnt == 1) {
			// 수정 성공
			Map<String, Object> map_code = mapCodePut.MapCode("200", "OK", "성공");
			map_result.put("API_CODE", map_code);
			String json = mapper.writeValueAsString(map_result);
			return json;
		} else {
			// 수정 실패
			Map<String, Object> map_code = mapCodePut.MapCode("500", "Internal Server Error", "내부 서버 오류");
			map_result.put("API_CODE", map_code);
			String json = mapper.writeValueAsString(map_result);
			return json;
		}
	}

	/*
	 * 게시글 삭제
	 */
	@RequestMapping(value = "/board/delete.do", method = RequestMethod.POST)
	public String deleteData(HttpServletRequest request, @RequestParam("bno") Integer bno)
			throws IOException, SQLException, ParseException {

		/* result map 선언 */
		Map<String, Object> map_result = new HashMap<String, Object>();
		// 정규표현식 클래스 호출
		RegularExpression reg = new RegularExpression();
		// MapCode 함수 호출
		MapCodePut mapCodePut = new MapCodePut();
		// JSON
		ObjectMapper mapper = new ObjectMapper();
		//
		baseVO adposVO = new baseVO();
		//
		adposVO.setBno(bno);

		int rtCnt = 0;
		try {
			rtCnt = BoardService.deletePost(adposVO);
		} catch (Exception e) {
			// 저장중 오류시 예외처리
			log.error("DB 커넥션 오류 ==> " + e);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터 저장중 오류가 발생했습니다");
			map_result.put("API_CODE", map_code);
			String json = mapper.writeValueAsString(map_result);
			return json;
		}
		if (rtCnt == 1) {
			// 삭제 성공
			Map<String, Object> map_code = mapCodePut.MapCode("200", "OK", "성공");
			map_result.put("API_CODE", map_code);
			String json = mapper.writeValueAsString(map_result);
			return json;
		} else {
			// 삭제 실패
			Map<String, Object> map_code = mapCodePut.MapCode("500", "Internal Server Error", "내부 서버 오류");
			map_result.put("API_CODE", map_code);
			String json = mapper.writeValueAsString(map_result);
			return json;
		}

	}

}