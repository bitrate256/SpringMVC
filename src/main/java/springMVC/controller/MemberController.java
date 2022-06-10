package springMVC.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import springMVC.service.MemberService;
import springMVC.utils.Criteria;
import springMVC.utils.ExceptionHandler;
import springMVC.utils.MapCodePut;
import springMVC.utils.PageMaker;
import springMVC.vo.ImageVO;
import springMVC.vo.MemberVO;

@Controller
public class MemberController {

	// 로깅
	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private MemberService MemberService;

	// properties 설정값 읽기
	@Value("#{config['dbms.mode']}")
	private String dbmsMode;
	
	/* 게시글 list */
	@RequestMapping(value = "/member/list", method = RequestMethod.GET)
	public ModelAndView list(ModelMap modelMap, ModelAndView mav, Criteria criteria, MemberVO memberVO)
			throws Exception {
		// 설정값 읽기 (list 호출시 dbmsMode 따라서 페이징 쿼리가 달라지므로 해당 설정값은 꼭 필요함)
		log.info("dbmsMode 설정값 읽기 : " + dbmsMode);
		// dbms 설정값 세팅
		memberVO.setDbmsMode(dbmsMode);
		
		//
		modelMap = mav.getModelMap();
		// MapCode 함수 호출
		MapCodePut mapCodePut = new MapCodePut();

		ExceptionHandler exceptionHandler = new ExceptionHandler();

		// 조회
		final List<Map<String, Object>> DataListPageSearch;
		// 조회중 오류시 예외처리
		try {
			DataListPageSearch = MemberService.selectDataListPage(memberVO, criteria);
		} catch (DataAccessException e) {
			exceptionHandler.printDBErrorLog(e); // 에러출력
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 리스트 조회중 오류가 발생했습니다"); //
			modelMap.addAttribute("map_code", map_code); // data 전달
			return new ModelAndView("error", modelMap); // ERROR VIEW PAGE 설정
		}

		// PageMaker 객체 생성
		PageMaker pageMaker = new PageMaker(criteria);
		// 토탈카운트 (전체 게시물 수를 조회)
		int totalCount = 0;
		// 조회중 오류시 예외처리
		try {
			totalCount = MemberService.getTotalCount(criteria);
		} catch (DataAccessException e) {
			exceptionHandler.printDBErrorLog(e); // 에러출력
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 토탈카운트 조회중 오류가 발생했습니다"); //
			modelMap.addAttribute("map_code", map_code); // data 전달
			return new ModelAndView("error", modelMap); // ERROR VIEW PAGE 설정
		}
		// pageMaker로 전달 -> pageMaker는 startPage, endPage, prev, next를 계산함
		pageMaker.setTotalCount(totalCount);
		// pageMaker의 계산결과를 모델에 추가
		modelMap.addAttribute("pageMaker", pageMaker);
		modelMap.addAttribute("Criteria", criteria);
		modelMap.addAttribute("DataListPageSearchResult", DataListPageSearch); // data 전달
		return new ModelAndView("member/list", modelMap); // VIEW PAGE 설정
	}

	/* 게시글 detail */
	@CrossOrigin
	@RequestMapping(value = "/member/detail", method = RequestMethod.GET)
	public ModelAndView detail(Criteria criteria, ModelMap modelMap, ModelAndView mav,
			@RequestParam("memberSeq") Integer memberSeq) throws Exception {
		//
		modelMap = mav.getModelMap();
		// MapCode 함수 호출
		MapCodePut mapCodePut = new MapCodePut();
		//
		MemberVO memberVO = new MemberVO();
		MemberVO DataResult = new MemberVO();
		
		ImageVO ImageResult = new ImageVO();
		ExceptionHandler exceptionHandler = new ExceptionHandler();
		//
		memberVO.setMemberSeq(memberSeq);
		
		log.debug("ImageResult : "+ImageResult);

		// 조회
		// 수정중 오류시 예외처리
		try {
			DataResult = MemberService.memberDetail(memberVO);
			ImageResult = MemberService.getImage(memberSeq);
			log.debug("ImageResult : "+ImageResult);
		} catch (DataAccessException e) {
			exceptionHandler.printDBErrorLog(e); // 에러출력
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터 조회중 오류가 발생했습니다"); //
			modelMap.addAttribute("map_code", map_code); // data 전달
			return new ModelAndView("error", modelMap); // ERROR VIEW PAGE 설정
		}
		modelMap.addAttribute("memberSeq", memberVO.getMemberSeq());
		modelMap.addAttribute("Criteria", criteria);
		modelMap.addAttribute("DataResult", DataResult); // data 전달 DataResult
		modelMap.addAttribute("ImageResult", ImageResult); // data 전달 ImageResult
		return new ModelAndView("member/detail", modelMap); // VIEW PAGE 설정
	}

	/* 게시글 modify */
	@RequestMapping(value = "/member/modify", method = RequestMethod.GET)
	public ModelAndView modify(Criteria criteria, ModelMap modelMap, ModelAndView mav,
			@RequestParam("memberSeq") Integer memberSeq) throws Exception {
		//
		modelMap = mav.getModelMap();
		// MapCode 함수 호출
		MapCodePut mapCodePut = new MapCodePut();
		//
		MemberVO memberVO = new MemberVO();
		MemberVO DataResult = new MemberVO();
		
		ImageVO ImageResult = new ImageVO();
		ExceptionHandler exceptionHandler = new ExceptionHandler();
		//
		memberVO.setMemberSeq(memberSeq);
		
		log.debug("memberVO : "+memberVO);
		log.debug("DataResult : "+DataResult);
		
		// 조회
		// 삭제중 오류시 예외처리
		try {
			DataResult = MemberService.memberDetail(memberVO);
			ImageResult = MemberService.getImage(memberSeq);
			log.debug("ImageResult : "+ImageResult);
		} catch (DataAccessException e) {
			exceptionHandler.printDBErrorLog(e); // 에러출력
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터 수정중 오류가 발생했습니다"); //
			modelMap.addAttribute("map_code", map_code); // data 전달
			return new ModelAndView("error", modelMap); // ERROR VIEW PAGE 설정
		}
		modelMap.addAttribute("DataResult", DataResult); // data 전달 DataResult
		modelMap.addAttribute("ImageResult", ImageResult); // data 전달 ImageResult
		return new ModelAndView("member/modify", modelMap); // VIEW PAGE 설정
	}

	/* 게시글 write */
	@RequestMapping(value = "/member/write", method = RequestMethod.GET)
	public String write() {
		return "member/write";
	}

}