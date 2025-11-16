package com.internship.iam.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Mentor H
 */
public class ResponseHandler {

    /**
     * @author Mentor H
     */
    public static ResponseEntity<?> success() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * @author Mentor H
     */
    public static <T> ResponseEntity<T> success(T data) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(data);
    }

    /**
     * @author Mentor H
     */
    public static <T> ResponseEntity<T> badRequest(T data) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(data);
    }

    /**
     * @author Mentor H
     */
    public static <T> ResponseEntity<T> unauthorized() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .build();
    }

    /**
     * @author Mentor H
     */
    public static <T> ResponseEntity<T> unauthorized(T data) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(data);
    }

    /**
     * @author Mentor H
     */
    public static ResponseEntity<?> notFound() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * @author Mentor H
     */
    public static <T> ResponseEntity<T> notFound(T data) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(data);
    }
}
