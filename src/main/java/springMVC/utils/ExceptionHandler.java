package springMVC.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.util.Objects;

public class ExceptionHandler {

    // 로깅
    private Logger log = LogManager.getLogger(getClass());

    // properties 설정값 읽기
    @Value("#{config['program.mode']}")
    private String programMode;

    public void printDBErrorLog (Exception e){
        // 프로그램 모드별 로그출력방식 변경
        if (Objects.equals(programMode, "DEV")) {
            // DEV 모드일시 스택트레이스 출력
            log.error("DB 오류 ==> ");
            e.getStackTrace();
        } else if (Objects.equals(programMode, "PROD")) {
            // PROD 모드일시 log 내역 저장
            log.error("DB 오류 ==> ", e);
        }
    }

    public void printErrorLog (Exception e, String errorMsg){
        // 프로그램 모드별 로그출력방식 변경
        if (Objects.equals(programMode, "DEV")) {
            // DEV 모드일시 스택트레이스 출력
            log.error(errorMsg);
            e.getStackTrace();
        } else if (Objects.equals(programMode, "PROD")) {
            // PROD 모드일시 log 내역 저장
            log.error(errorMsg, e);
        }
    }

}
