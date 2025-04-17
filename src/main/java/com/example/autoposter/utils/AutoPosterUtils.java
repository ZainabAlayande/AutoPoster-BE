package com.example.autoposter.utils;

import com.example.autoposter.service.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;

public class AutoPosterUtils {

    UserService userService;

    public static String getLoggedInUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    public String getLinkedinUser_AccessToken() {
        return userService.getUserLinkedin_AccessToken();
    }

}
