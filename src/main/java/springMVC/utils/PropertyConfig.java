package springMVC.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PropertyConfig {

	private Logger log = LogManager.getLogger(getClass());

	// 설정파일 확인 및 전달
	@Bean(name = "config")
	public PropertiesFactoryBean propertiesFactoryBean() throws Exception {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		ClassPathResource classPathResource = new ClassPathResource("config.properties");
		ExceptionHandler exceptionHandler = new ExceptionHandler();
		
		// 설정파일 내역 리스트 출력
		try {
			Path path = Paths.get(classPathResource.getURI());
			List<String> content = Files.readAllLines(path);
			content.forEach(System.out::println);
			log.info("설정파일 로드 성공");
		} catch (IOException e) {
			String errorMsg = "설정파일 로드 실패 {}";
			exceptionHandler.printErrorLog(e, errorMsg);
		}
		propertiesFactoryBean.setLocation(classPathResource);
		return propertiesFactoryBean;
	}
}
