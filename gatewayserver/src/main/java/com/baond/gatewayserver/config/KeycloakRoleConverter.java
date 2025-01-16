package com.baond.gatewayserver.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is going to help me to extract the roles that I'm getting from they keycloak into the format
 * that spring security framework can understand.
 *
 * GrantedAuthority is an interface available inside the Spring Security framework.
 * Whenever we want to convey the role's information or privileged information, we need to make sure
 * we are sending those details with these interface formats only.
 */
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> realAccess = jwt.getClaim("realm_access");
//        Map<String, Object> realAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

        if (realAccess == null || realAccess.isEmpty()){
            return new ArrayList<>();
        }
        Collection<GrantedAuthority> returnValue = ((List<String>) realAccess.get("roles"))
//                we have to append the ROLE_ prefix to each role because whenever we use the hasRole() method.
//                internally it is going to use a prefix which is ROLE_...
                .stream().map(roleName -> "ROLE_"+roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return returnValue;
    }


}
