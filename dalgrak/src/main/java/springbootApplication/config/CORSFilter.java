package springbootApplication.config;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;


public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletResponse res = (HttpServletResponse) response;
        
        // CORS 설정 추가
        res.setHeader("Access-Control-Allow-Origin", "*"); // 모든 도메인 허용
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT"); // 허용할 HTTP 메서드
        res.setHeader("Access-Control-Max-Age", "3600"); // 캐시 시간 설정
        res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, x-requested-with, origin, accept");

        chain.doFilter(request, response); // 요청 처리 계속 진행
    }

    @Override
    public void destroy() {
        // 필터 종료 시 처리할 내용 (필요 없으면 생략 가능)
    }
}