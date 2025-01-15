package com.baond.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
// Because Spring Cloud Gateway built upon the Spring reactive module, we need to use EnableWebFluxSecurity annotation.
// If it is a normal spring boot web application, then you should se the annotation EnableWebSecurity
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {

        serverHttpSecurity.authorizeExchange(exchanges
//                All the requests that are coming towards the gateway have to be authenticated
                -> exchanges.pathMatchers(HttpMethod.GET).permitAll()
//       if someone is trying to invoke the get APIs inside these 3 patterns, it is going to be permitted all
//                even though I have mentioned authenticated. Because the first configuration in the top will have the first piority.
                .pathMatchers("/easybank/accounts/**").authenticated()
                .pathMatchers("/easybank/cards/**").authenticated()
                .pathMatchers("/easybank/loans/**").authenticated())
                .oauth2ResourceServer(oAuth2ResourceServerSpec
//                        use the default configurations related to the JWT tokens.
                        -> oAuth2ResourceServerSpec.jwt(Customizer.withDefaults()));//convert the gateway server as an OAuth2 resource server
//   Disable the csrf protection provided by the spring security framework
//        because csrf protection is only needed when browsers are involved.
//        Since in our scenario, there are no browsers involved, we can disable the csrf protection
        serverHttpSecurity.csrf( csrf -> csrf.disable());
        return serverHttpSecurity.build();
    }
}
