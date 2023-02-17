package togethers.togethers.config;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.swagger.models.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.httpBasic().disable().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )
                .and()
                .authorizeRequests()
                .antMatchers("/sign-api/sign-in", "/sign-api/sign-up",
                        "/sign-api/exception","/introduction","/write","/detailPost/Reply"
                        ,"/post/modify","/post/delete","/post/detailPost","/post/postList").permitAll()
                .anyRequest().hasRole("ADMIN")
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) ->web.ignoring().antMatchers("/css/**","/js/**","/img/**","script/**","/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**","/v2/api-docs");
    }
}
