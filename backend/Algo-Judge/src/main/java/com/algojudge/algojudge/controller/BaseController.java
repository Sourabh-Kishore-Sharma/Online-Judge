package com.algojudge.algojudge.controller;

import com.algojudge.algojudge.service.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {
    protected String gerUsernameFromJwt(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl){
            return ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
        }
        throw new IllegalStateException("User not authenticated or user details not available.");
    }
}
