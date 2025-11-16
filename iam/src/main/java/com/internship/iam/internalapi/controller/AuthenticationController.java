package com.internship.iam.internalapi.controller;

import com.internship.iam.internalapi.model.request.AuthenticationRequest;
import com.internship.iam.internalapi.model.response.AuthenticationResponse;
import com.internship.iam.internalapi.service.AuthenticationService;
import com.internship.iam.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

/**
 * @author Mentor H
 */
@Controller
@RequestMapping("api/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * @author Mentor H
     */
    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        if (!Objects.isNull(response.getError())) {
            return ResponseHandler.unauthorized(response.getError());
        }
        return ResponseHandler.success(response);
    }

}
