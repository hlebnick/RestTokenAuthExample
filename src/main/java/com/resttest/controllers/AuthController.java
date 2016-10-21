package com.resttest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resttest.beans.rest.AuthRequest;
import com.resttest.beans.rest.ErrorResponse;
import com.resttest.beans.rest.StringResponse;
import com.resttest.dao.UserDao;
import com.resttest.dto.User;
import com.resttest.service.TokenService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class AuthController {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/api/auth", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity auth(@Valid AuthRequest authRequest, BindingResult bindingResult) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("errors");
        }

        User userFromDb = userDao.getUser(authRequest.getUsername());

        String response;
        if (userFromDb != null && DigestUtils.sha1Hex(authRequest.getPassword()).equals(userFromDb.getPassword())) {
            String token = tokenService.registerUser(userFromDb.getEmail());
            response = mapper.writeValueAsString(new StringResponse(token));
            return ResponseEntity.ok(response);
        }

        response = mapper.writeValueAsString(new ErrorResponse("Wrong email or password"));
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/api/logout", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity logout(@CookieValue("x-auth-token") String token) {
        tokenService.removeUser(token);
        return ResponseEntity.ok().body(null);
    }
}
