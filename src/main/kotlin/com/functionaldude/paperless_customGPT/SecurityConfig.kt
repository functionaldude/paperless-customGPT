package com.functionaldude.paperless_customGPT

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http
      .csrf(AbstractHttpConfigurer<*, *>::disable)
      .authorizeHttpRequests { auth ->
        auth
          .requestMatchers("/actuator/health").permitAll()
          .requestMatchers("/error").permitAll()
          .requestMatchers("/").permitAll()
          .requestMatchers("/api/openapi.json").permitAll()
          .requestMatchers("/v3/api-docs/**").permitAll()
          // everything else requires the user to be logged in
          .anyRequest().authenticated()
      }
      // enables the OIDC login flow
      .oauth2Login { oauth2 ->
        oauth2.loginPage("/oauth2/authorization/authentik") // optional
      }
      // Token-based auth for tools (IntelliJ, GPT, curl, …)
      .oauth2ResourceServer { rs ->
        rs.jwt { jwt -> }
      }
      .logout { logout ->
        logout.logoutSuccessUrl("/")
      }

    return http.build()
  }
}
