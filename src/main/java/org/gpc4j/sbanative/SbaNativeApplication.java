package org.gpc4j.sbanative;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Slf4j
//@SpringBootApplication
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })

//@Configuration
@EnableAdminServer
public class SbaNativeApplication {

  public static void main(String[] args) {
    log.info("SbaNativeApplication.main");
    SpringApplication.run(SbaNativeApplication.class, args);
  }


//  @Bean
//  public SecurityFilterChain filterChain(@Autowired  HttpSecurity http) throws Exception {
//    log.info("http = {}", http);
//    return http.build();
//  }
}
