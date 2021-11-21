package kr.pe.mayo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

// multipartResolver을 등록했기 때문에
// 자동으로 구성된 요소들 중, 첨부파일관련 구성을 사용하지 않도록 설정
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
public class MayoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MayoApplication.class, args);
	}

}
