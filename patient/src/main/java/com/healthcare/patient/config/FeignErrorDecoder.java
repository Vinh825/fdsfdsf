package com.healthcare.patient.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

// Bộ giải mã lỗi
public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());

        // Nếu IAM service trả về 401 (Token sai, hết hạn) hoặc 403 (Không có quyền)
        if (status.equals(HttpStatus.UNAUTHORIZED) || status.equals(HttpStatus.FORBIDDEN)) {
            // Ném exception này để GlobalExceptionHandler bắt được
            return new RuntimeException("Authentication failed: " + status.getReasonPhrase());
        }

        // Các lỗi khác (500, 404...) thì trả về lỗi mặc định
        return defaultDecoder.decode(methodKey, response);
    }
}
