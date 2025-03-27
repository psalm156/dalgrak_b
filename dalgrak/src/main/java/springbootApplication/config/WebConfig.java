package springbootApplication.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // 설정 클래스로 등록
public class WebConfig {

    @Bean
    public FilterRegistrationBean<CORSFilter> corsFilter() {
        FilterRegistrationBean<CORSFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CORSFilter());  // 필터 등록
        registrationBean.addUrlPatterns("/api/*");  // 필터가 적용될 URL 패턴
        return registrationBean;
    }
}
