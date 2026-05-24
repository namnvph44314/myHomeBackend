package com.example.myschool.config.config;//package com.example.backend.config.config;
//
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.util.Optional;
//
//import static org.apache.commons.lang3.math.NumberUtils.createLong;
//
//public class ApplicationAuditAware implements AuditorAware<Long> {
//    @Override
//    public Optional<Long> getCurrentAuditor() {
//        Authentication authentication =
//                SecurityContextHolder
//                        .getContext()
//                        .getAuthentication();
//        if (authentication == null ||
//                !authentication.isAuthenticated() ||
//                authentication instanceof AnonymousAuthenticationToken
//        ) {
//            return Optional.empty();
//        }
//        return Optional.ofNullable( createLong(authentication.getPrincipal().toString()));
//    }
//}
