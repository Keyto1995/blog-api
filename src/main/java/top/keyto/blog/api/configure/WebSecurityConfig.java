package top.keyto.blog.api.configure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.keyto.blog.api.dto.ResultDTO;
import top.keyto.blog.api.filter.CustomAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Keyto
 * Created on 2019/10/28
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;

    @Autowired
    public WebSecurityConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/r/**").authenticated()
            .anyRequest().permitAll()
            .and()
            .formLogin()
            .and()
            .httpBasic()
            .and()
            .csrf().disable()
            .exceptionHandling()
            // AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
            .authenticationEntryPoint((request, response, e) -> {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=utf-8");
                ResultDTO resultDTO = ResultDTO.FAILE("-1", "未登录，请先登录", null);
                response.getWriter().write(this.objectMapper.writeValueAsString(resultDTO));
            })
            // AccessDeineHandler 用来解决认证过的用户访问无权限资源时的异常
            .accessDeniedHandler((request, response, e) -> {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=utf-8");
                ResultDTO resultDTO = ResultDTO.FAILE("-1", "权限不足，请联系管理员", null);
                response.getWriter().write(this.objectMapper.writeValueAsString(resultDTO));
            });
        http.cors();
        // 用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
        http.addFilterAt(customAuthenticationFilter(),
            UsernamePasswordAuthenticationFilter.class);
    }

    // 注册自定义的UsernamePasswordAuthenticationFilter
    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(this.objectMapper.writeValueAsString(authentication));
        });
        filter.setAuthenticationFailureHandler((request, response, e) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=utf-8");
            ResultDTO resultDTO;
            if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                resultDTO = ResultDTO.FAILE("-1", "用户名或密码输入错误，登录失败", null);
            } else if (e instanceof DisabledException) {
                resultDTO = ResultDTO.FAILE("-1", "账户被禁用，登录失败，请联系管理员", null);
            } else {
                resultDTO = ResultDTO.FAILE("-1", "登录失败", null);
            }
            response.getWriter().write(this.objectMapper.writeValueAsString(resultDTO));
        });
        filter.setFilterProcessesUrl("/login/ajax");

        // 这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
}
