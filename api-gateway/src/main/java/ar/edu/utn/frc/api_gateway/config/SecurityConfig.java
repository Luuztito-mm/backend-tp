package ar.edu.utn.frc.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        // Adaptador reactivo para WebFlux
        var jwtConverter = new ReactiveJwtAuthenticationConverterAdapter(
                new KeycloakRealmRoleConverter()
        );

        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(auth -> auth

                // Público
                .pathMatchers("/public/**").permitAll()

                // Roles aplicados en el GATEWAY
                .pathMatchers("/cliente/**").hasRole("cliente")
                .pathMatchers("/admin/**").hasRole("operador")
                .pathMatchers("/transportista/**").hasRole("transportista")

                // Todo lo demás requiere token
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter))
            );

        return http.build();
    }
}
