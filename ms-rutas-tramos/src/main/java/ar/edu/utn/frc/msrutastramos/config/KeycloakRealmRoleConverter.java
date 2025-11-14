package ar.edu.utn.frc.msrutastramos.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;

public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        Object realmAccess = jwt.getClaim("realm_access");

        if (realmAccess instanceof Map<?, ?> map) {
            Object roles = map.get("roles");

            if (roles instanceof Collection<?> list) {
                return list.stream()
                        .map(Object::toString)
                        .map(r -> "ROLE_" + r)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());
            }
        }

        return Set.of();
    }
}
