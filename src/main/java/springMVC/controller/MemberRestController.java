package springMVC.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import springMVC.service.MemberService;
import springMVC.utils.ExceptionHandler;
import springMVC.utils.MapCodePut;
import springMVC.vo.ImageVO;
import springMVC.vo.MemberVO;

@RestController
public class MemberRestController {

	// 로깅
	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private MemberService MemberService;

	/*
	 * 게시글 등록
	 * 
	 * 이미지 등록 순서? 1. regPost controller 에서 시작 (파일 데이터를 @RequestParam으로 받음) 2.
	 * service insert 에 파일 데이터를 param으로 보냄
	 * 
	 * utils image : 실제 이미지 파일 전송 (컨트롤러와 무관하게 실행) controller regPost : 이미지의 데이터를 DB로
	 * 전송 (DB에 글 등록시 발생한 SEQ를 기반으로 파일 데이터를 삽입. 파일 데이터는 Image Utils 에서 받아와야 함)
	 */
	@RequestMapping(value = "/member/register.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public String regPost(HttpServletResponse response, HttpServletRequest request, MemberVO memberVO, ImageVO imageVO,
			@RequestParam(value = "uploadFile", required = false) MultipartFile[] uploadFile,
			Map<String, Object> params) throws Exception {
		log.debug("uploadFile.........." + Arrays.toString(uploadFile));
		log.debug("params.........." + params);

		/* result map 선언 */
		Map<String, Object> map_result = new HashMap<String, Object>();
		// MapCode 함수 호출
		MapCodePut mapCodePut = new MapCodePut();
		// 현재 날짜 생성
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		String today = sdf.format(nowDate);
		// JSON
		ObjectMapper mapper = new ObjectMapper();
		//
		memberVO.setRegDate(today);

		ExceptionHandler exceptionHandler = new ExceptionHandler();

		// memberSeq Int값 확보 준비
		int memberSeq = 0;

		int rtCnt = 0;
		// 저장중 오류시 예외처리
		try {
			// 멤버 저장
			rtCnt = MemberService.regMember(memberVO);
			// selectKey 리턴 결과
			memberSeq = memberVO.getMemberSeq();
			log.debug("게시글 등록에서 자동증가 삽입한 memberSeq : " + memberSeq);
			imageVO.setMemberSeq(memberSeq);

			// 이미지 데이터 저장
			params.put("NewRegYN", true);
			params = MemberService.insertImage(params, memberVO, imageVO, uploadFile);
		} catch (Exception e) {
			exceptionHandler.printDBErrorLog(e); // 에러출력
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터 저장중 오류가 발생했습니다");
			map_result.put("API_CODE", map_code);
			return mapper.writeValueAsString(map_result);
		}
		if (rtCnt == 1) {
			// 저장 성공
			Map<String, Object> map_code = mapCodePut.MapCode("200", "OK", "성공");
			map_result.put("API_CODE", map_code);
			return mapper.writeValueAsString(map_result);
		} else {
			// 저장 실패
			Map<String, Object> map_code = mapCodePut.MapCode("500", "Internal Server Error", "내부 서버 오류");
			map_result.put("API_CODE", map_code);
			return mapper.writeValueAsString(map_result);
		}
	}

	/*
	 * 수정
	 */
	@RequestMapping(value = "/member/modify.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public String updateData(HttpServletResponse response, HttpServletRequest request, MemberVO memberVO,
			ImageVO imageVO, @RequestParam(value = "uploadFile", required = false) MultipartFile[] uploadFile,
			Map<String, Object> params, boolean imageEditYn) throws Exception {
		log.debug("uploadFile.........." + Arrays.toString(uploadFile));
		log.debug("params.........." + params);

		/* result map 선언 */
		Map<String, Object> map_result = new HashMap<String, Object>();
		// MapCode 함수 호출
		MapCodePut mapCodePut = new MapCodePut();
		// JSON
		ObjectMapper mapper = new ObjectMapper();

		log.debug("imageEditYn : " + imageEditYn);

		ExceptionHandler exceptionHandler = new ExceptionHandler();
		
		int rtCnt = 0;
		// 수정중 오류시 예외처리
		try {
			rtCnt = MemberService.modifyMember(memberVO);

			int memberSeq = memberVO.getMemberSeq();

			// selectKey 리턴 결과
			log.debug("전달받은 memberSeq : " + memberSeq);
			imageVO.setMemberSeq(memberSeq);
			
//////////////////////////////////////// params 이미지 데이터가 존재하면 이미지 데이터 저장 ////////////////////////////////////////
			if (uploadFile != null) {
				log.debug("업로드파일 존재함 : " + Arrays.toString(uploadFile));
				// 기존 파일과 함께 넘긴 후 image utils 에서 분기
				try {
					// 이미지 데이터 저장
					params = MemberService.insertImage(params, memberVO, imageVO, uploadFile);
				} catch (DataAccessException e) {
					exceptionHandler.printDBErrorLog(e); // 에러출력
					Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터 저장중 오류가 발생했습니다");
					map_result.put("API_CODE", map_code);
					return map_result.toString();
				}
				// params 값에서 검증실패 결과가 오면 실패 처리하기
				boolean imageVerify = params.get("imageVerify") != null;
				if (imageVerify) {
					// 이미지 검증 성공
					log.debug("이미지 검증 성공 : " + true);
				} else {
					// 이미지 검증 실패
					log.debug("이미지 검증 실패 : " + false);
					throw new Exception("이미지 검증 실패 Exception");
				}
				log.debug("insertImage : " + rtCnt);
//////////////////////////////////////// 업로드파일 존재하지 않으므로 이미지 삭제 ////////////////////////////////////////
			} else if (imageEditYn) {
				log.debug("업로드파일 존재하지 않으므로 이미지 삭제");
				log.debug("imageEditYn : " + true);
				try {
					MemberService.deleteImageOnly(memberSeq);
				} catch (DataAccessException e) {
					exceptionHandler.printDBErrorLog(e); // 에러출력
					Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 이미지 삭제중 오류가 발생했습니다");
					map_result.put("API_CODE", map_code);
					return map_result.toString();
				}
			} else {
//////////////////////////////////////// 이미지 수정 없이 텍스트만 업로드 ////////////////////////////////////////	
				// 이미지 수정 없이 텍스트만 업로드
				log.debug("이미지 수정 없이 텍스트만 업로드");
				log.debug("imageEditYn : " + false);
			}
		} catch (DataAccessException e) {
			exceptionHandler.printDBErrorLog(e); // 에러출력
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터 저장중 오류가 발생했습니다");
			map_result.put("API_CODE", map_code);
			return map_result.toString();
		}
		if (rtCnt == 1) {
			// 수정 성공
			Map<String, Object> map_code = mapCodePut.MapCode("200", "OK", "성공");
			map_result.put("API_CODE", map_code);
			return mapper.writeValueAsString(map_result);
		} else {
			// 수정 실패
			Map<String, Object> map_code = mapCodePut.MapCode("500", "Internal Server Error", "내부 서버 오류");
			map_result.put("API_CODE", map_code);
			return mapper.writeValueAsString(map_result);
		}
	}

	/*
	 * 게시글 삭제
	 */
	@RequestMapping(value = "/member/delete.do", method = RequestMethod.POST)
	public String deleteData(HttpServletRequest request, @RequestParam("memberSeq") Integer memberSeq, @RequestParam("imageDeleteYn") boolean imageDeleteYn)
			throws Exception {
		// memberSeq를 받아서 해당 이미지가 있는걸 확인하고 이걸 삭제하도록 할 것. -> 이미지삭제+글삭제 연달아 이어지도록 처리함
		// 삭제 후 메인화면으로 이동 (내부 서버 오류 발생하여 메인 화면으로 이동 못하는 중)

		/* result map 선언 */
		Map<String, Object> map_result = new HashMap<String, Object>();
		// MapCode 함수 호출
		MapCodePut mapCodePut = new MapCodePut();
		// JSON
		ObjectMapper mapper = new ObjectMapper();
		//
		MemberVO memberVO = new MemberVO();
		//
		memberVO.setMemberSeq(memberSeq);
		log.debug("imageDeleteYn : " + imageDeleteYn);

		ExceptionHandler exceptionHandler = new ExceptionHandler();
		
		if (imageDeleteYn) {
			try {
				MemberService.deleteImageOnly(memberSeq);
			} catch (DataAccessException e) {
				exceptionHandler.printDBErrorLog(e); // 에러출력
				Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 이미지 삭제중 오류가 발생했습니다");
				map_result.put("API_CODE", map_code);
				return map_result.toString();
			}
		}

		int rtCnt = 0;
		// 삭제중 오류시 예외처리
		try {
			rtCnt = MemberService.deleteMember(memberVO);
		} catch (DataAccessException e) {
			exceptionHandler.printDBErrorLog(e); // 에러출력
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터 삭제중 오류가 발생했습니다");
			map_result.put("API_CODE", map_code);
			return map_result.toString();
		}
		if (rtCnt == 1) {
			// 삭제 성공
			Map<String, Object> map_code = mapCodePut.MapCode("200", "OK", "성공");
			map_result.put("API_CODE", map_code);
			return mapper.writeValueAsString(map_result);
		} else {
			// 삭제 실패
			Map<String, Object> map_code = mapCodePut.MapCode("500", "Internal Server Error", "내부 서버 오류");
			map_result.put("API_CODE", map_code);
			return mapper.writeValueAsString(map_result);
		}

	}

}
