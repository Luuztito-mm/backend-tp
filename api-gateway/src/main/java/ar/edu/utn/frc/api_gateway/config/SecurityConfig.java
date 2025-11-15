package ar.edu.utn.frc.api_gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    /**
     * ✅ BEAN CRÍTICO: ReactiveJwtDecoder
     * Sin esto, Spring no puede validar los tokens JWT
     */
    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        // Converter clásico de Spring para extraer roles
        JwtAuthenticationConverter jwtAuthConverter = new JwtAuthenticationConverter();
        jwtAuthConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());

        // Adaptador REACTIVO para WebFlux
        var reactiveJwtConverter = new ReactiveJwtAuthenticationConverterAdapter(jwtAuthConverter);

        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(auth -> auth
                // ✅ Rutas públicas sin autenticación
                .pathMatchers("/ping").permitAll()
                .pathMatchers("/gateway/logs/**").permitAll()
                .pathMatchers("/public/**").permitAll()

                // ✅ Rutas de CLIENTES
                .pathMatchers("/cliente/**").hasRole("cliente")
                .pathMatchers("/clientes/**").hasRole("cliente")
                .pathMatchers("/solicitudes/**").hasRole("cliente")
                .pathMatchers("/eventos-seguimiento/**").hasRole("cliente")

                // ✅ Rutas de TRANSPORTISTAS
                .pathMatchers("/camiones/**").hasRole("transportista")
                .pathMatchers("/transportista/**").hasRole("transportista")

                // ✅ Rutas de ADMINISTRADORES/OPERADORES
                .pathMatchers("/admin/**").hasRole("operador")
                .pathMatchers("/adepositos/**").hasRole("operador")

                // ✅ Rutas públicas o con lógica especial
                .pathMatchers("/tarifas/**").permitAll()

                // ✅ Cualquier otra ruta requiere autenticación
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(reactiveJwtConverter))
            );

        return http.build();
    }
}
