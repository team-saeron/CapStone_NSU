package togethers.togethers.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenProvider jwtTokenProvider;

//    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
//        this.jwtTokenProvider = jwtTokenProvider;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(servletRequest);
        LOGGER.info("[doFilterInternal] token 값 추출 완료. token : {}", token);

        LOGGER.info("[doFilterInternal] token 값 유효성 체크 시작");
        if(token !=null && jwtTokenProvider.validateToken(token)){
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            LOGGER.info("[doFilterInternal] token 값 유효성 체크 완료");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request ); //request header에서 토큰 추출
//        LOGGER.info("[doFilterInternal] token 값 추출 완료. token : {}", token);
//
//        LOGGER.info("[doFilterInternal] token 값 유효성 체크 시작");
//        if(token !=null &&jwtTokenProvider.validateToken(token)){
//            Authentication authentication = jwtTokenProvider.getAuthentication(token); // 토큰이 유효할 경우 토큰에서 authentication 객체를 가지고 와서 securitycontext에 저장
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            LOGGER.info("[doFilterInternal] token 값 유효성 체크 완료");
//        }
//
//        chain.doFilter(request, response);
//    }
}
