package com.healthcare.patient;

import com.healthcare.patient.config.FeignClientConfig;
import com.healthcare.patient.dto.request.AuthenticationRequest;
import com.healthcare.patient.dto.response.AuthenticationResponse;
import com.healthcare.patient.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "identity-service", url = "${iam.service.url}",configuration = FeignClientConfig.class)
public interface IamServiceClient {
    @PostMapping("/api/public/login")
    AuthenticationResponse login(@RequestBody AuthenticationRequest request);
}
