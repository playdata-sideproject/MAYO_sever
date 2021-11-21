package kr.pe.mayo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(new LoggerInterceptor());
//    }

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        // 인코딩 설정
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        // 파일 업로든 용량제한 설정
        // 5mb max & size (1024, 1024)
        commonsMultipartResolver.setMaxUploadSizePerFile(5*1024*1024);
        return commonsMultipartResolver;
    }
}
