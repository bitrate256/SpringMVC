package springMVC.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import springMVC.utils.MapCodePut;

@RestController
public class ErrorController {

	// 로깅
	private Logger log = LogManager.getLogger(getClass());
	

	@RequestMapping(value = "/member/error", method = RequestMethod.POST)
	public void error() throws Exception {
		// 더미 데이터
		String i = "1";
		try {
			log.info("조회시 INFO 메세지 확인 : ", i);
			throw new Exception("예외 메시지"); // 예외 객체 던짐.
		} catch (Exception e) {
			log.error("테스트 에러 강제 발생 메세지 : ", e);
		}
	}
}
