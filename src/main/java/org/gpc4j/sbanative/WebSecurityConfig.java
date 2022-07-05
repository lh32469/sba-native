package org.gpc4j.sbanative;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

//  public WebSecurityConfig() {
//    System.out.println("---------------------------");
//    log.info("WebSecurityConfig.WebSecurityConfig");
//  }

  private final AdminServerProperties adminServer;

  public WebSecurityConfig(AdminServerProperties adminServer) {
    this.adminServer = adminServer;
    log.info("adminServer.getContextPath() = {}", adminServer.getContextPath());
  }

//  @Bean
//  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    log.info("http = {}", http);
//    return http.build();
//  }
//
//  @Bean
//  public WebSecurityCustomizer webSecurityCustomizer() {
//    return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
//  }

    @Bean
  public SecurityFilterChain filterChddain(HttpSecurity http) throws Exception {
    SavedRequestAwareAuthenticationSuccessHandler successHandler =
        new SavedRequestAwareAuthenticationSuccessHandler();
    successHandler.setTargetUrlParameter("redirectTo");
    successHandler.setDefaultTargetUrl(this.adminServer.getContextPath() + "/");

    http
        .authorizeRequests()
        .antMatchers(this.adminServer.getContextPath() + "/assets/**").permitAll()
        .antMatchers(this.adminServer.getContextPath() + "/login").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage(this.adminServer.getContextPath() + "/login")
        .successHandler(successHandler)
        .and()
        .logout()
        .logoutUrl(this.adminServer.getContextPath() + "/logout")
        .and()
        .httpBasic()
        .and()
        .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .ignoringRequestMatchers(
            new AntPathRequestMatcher(this.adminServer.getContextPath() +
                "/instances", HttpMethod.POST.toString()),
            new AntPathRequestMatcher(this.adminServer.getContextPath() +
                "/instances/*", HttpMethod.DELETE.toString()),
            new AntPathRequestMatcher(this.adminServer.getContextPath() + "/actuator/**"))
        .and()
        .rememberMe()
        .key("84766f68-1698-4e76-a9e9-47ff03a7693d")
        .tokenValiditySeconds(1209600);
    return http.build();
  }

}
