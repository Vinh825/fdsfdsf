package com.internship.iam.publicapi.controller;

import com.internship.iam.publicapi.model.request.AuthenticationRequest;
import com.internship.iam.publicapi.model.response.AuthenticationResponse;
import com.internship.iam.publicapi.service.ExAuthenticationService;
import com.internship.iam.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("api/public/login")
public class ExAuthenticationController {

    @Autowired
    private ExAuthenticationService exAuthenticationService;

    /**
     * @author Mentor H
     */
    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = exAuthenticationService.authenticate(request);
        if (!Objects.isNull(response.getError())) {
            return ResponseHandler.unauthorized(response.getError());
        }
        return ResponseHandler.success(response);
    }

}
