package kr.pe.mayo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

// SpringBoot의 첨부파일 관련 구성을 따르지 않고 WebMvcConfig 클래스에 정의한 multipartResolver 관련 설정을 따르겠다
@SpringBootApplication(exclude={MultipartAutoConfiguration.class})
public class MayoApplication {

	public static void main(String[] args) {

		SpringApplication.run(MayoApplication.class, args);
	}

}
