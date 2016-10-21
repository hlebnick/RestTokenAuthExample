package com.resttest.controllers;

import com.resttest.beans.rest.StringResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @RequestMapping(value = "/api/user/details", method = RequestMethod.GET)
    @ResponseBody
    @Secured({"ROLE_USER"})
    public ResponseEntity getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((User) authentication.getDetails()).getUsername();
        return ResponseEntity.ok().body(new StringResponse(username));
    }
}
