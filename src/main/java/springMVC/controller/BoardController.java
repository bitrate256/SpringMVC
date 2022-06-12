package springMVC.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import springMVC.service.BoardService;
import springMVC.utils.Criteria;
import springMVC.utils.MapCodePut;
import springMVC.utils.PageMaker;
import springMVC.vo.BaseVO;

@Controller
public class BoardController {

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private BoardService BoardService;

	@RequestMapping(value = "/board/list", method = RequestMethod.GET)
	public ModelAndView list(ModelMap modelMap, ModelAndView mav, Criteria cri, BaseVO baseVO)
			throws IOException, SQLException, ParseException, Exception {
		//
		modelMap = mav.getModelMap();
		// MapCode 함수 호출
		MapCodePut mapCodePut = new MapCodePut();

		System.out.println(">>>>>>>>>>>> searchType : " + baseVO.getSearchType());
		System.out.println(">>>>>>>>>>>>>>> keyword : " + baseVO.getKeyword());
		System.out.println(">>>>>>>>>>>>> startDate : " + baseVO.getStartDate());
		System.out.println(">>>>>>>>>>>>>>> endDate : " + baseVO.getEndDate());

		// 조회
		final List<Map<String, Object>> DataListPageSearch;
		try {
			DataListPageSearch = BoardService.selectDataListPage(baseVO, cri);
		} catch (Exception e) {
			// 조회중 오류시 예외처리
			log.error("DB 커넥션 오류 ==> " + e);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터 조회중 오류가 발생했습니다");
			//
			modelMap.addAttribute("map_code", map_code); // data 전달
			return new ModelAndView("", modelMap); // ERROR VIEW PAGE 설정
		}

		// PageMaker 객체 생성
		PageMaker pageMaker = new PageMaker(cri);
		// 토탈카운트 (전체 게시물 수를 조회)
		int totalCount = BoardService.getTotalCount(cri);
		// pageMaker로 전달 -> pageMaker는 startPage, endPage, prev, next를 계산함
		pageMaker.setTotalCount(totalCount);
		// pageMaker의 계산결과를 모델에 추가
		modelMap.addAttribute("pageMaker", pageMaker);
		//
		modelMap.addAttribute("Criteria", cri);
		//
		modelMap.addAttribute("DataListPageSearchResult", DataListPageSearch); // data 전달
		return new ModelAndView("board/list", modelMap); // VIEW PAGE 설정
	}

	/* detail */
	@RequestMapping(value = "/board/detail", method = RequestMethod.GET)
	public ModelAndView detail(Criteria cri, ModelMap modelMap, ModelAndView mav, @RequestParam("bno") Integer bno) {
		//
		modelMap = mav.getModelMap();
		// MapCode 함수 호출
		MapCodePut mapCodePut = new MapCodePut();
		//
		BaseVO baseVO = new BaseVO();
		BaseVO DataResult = new BaseVO();
		//
		baseVO.setBno(bno);

		// 조회
		try {
			DataResult = BoardService.boardDetail(baseVO);
		} catch (Exception e) {
			// 조회중 오류시 예외처리
			log.error("DB 커넥션 오류 ==> " + e);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터 조회중 오류가 발생했습니다");
			//
			modelMap.addAttribute("map_code", map_code); // data 전달
			return new ModelAndView("", modelMap); // ERROR VIEW PAGE 설정
		}
		modelMap.addAttribute("Criteria", cri);
		modelMap.addAttribute("DataResult", DataResult); // data 전달
		return new ModelAndView("board/detail", modelMap); // VIEW PAGE 설정
	}

	/* modify */
	@RequestMapping(value = "/board/modify", method = RequestMethod.GET)
	public ModelAndView modify(Criteria cri, ModelMap modelMap, ModelAndView mav, @RequestParam("bno") Integer bno) {
		//
		modelMap = mav.getModelMap();
		// MapCode 함수 호출
		MapCodePut mapCodePut = new MapCodePut();
		//
		BaseVO baseVO = new BaseVO();
		BaseVO DataResult = new BaseVO();
		//
		baseVO.setBno(bno);

		// 조회
		try {
			DataResult = BoardService.boardDetail(baseVO);
		} catch (Exception e) {
			// 조회중 오류시 예외처리
			log.error("DB 커넥션 오류 ==> " + e);
			Map<String, Object> map_code = mapCodePut.MapCode("400", "BAD REQUEST", "잘못된 요청 / 데이터 조회중 오류가 발생했습니다");
			//
			modelMap.addAttribute("map_code", map_code); // data 전달
			return new ModelAndView("", modelMap); // ERROR VIEW PAGE 설정
		}
		modelMap.addAttribute("DataResult", DataResult); // data 전달
		return new ModelAndView("board/modify", modelMap); // VIEW PAGE 설정
	}

	/* write */
	@RequestMapping(value = "/board/write", method = RequestMethod.GET)
	public String write() {
		return "board/write";
	}

}