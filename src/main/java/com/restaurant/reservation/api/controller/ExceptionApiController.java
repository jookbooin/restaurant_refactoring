package com.restaurant.reservation.api.controller;

import com.restaurant.reservation.exception.domain.CategoryException;
import com.restaurant.reservation.exception.domain.DomainException;
import com.restaurant.reservation.exception.domain.MenuException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExceptionApiController {

    @GetMapping("/api/Exception/IllegalArgumentException")
    public ResponseEntity<?> IllegalArgumentException() {
        throw new IllegalArgumentException("IllegalArgumentException 발생 ");
    }

    @GetMapping("/api/Exception/RuntimeException")
    public ResponseEntity<?> RuntimeException() {
        throw new RuntimeException("RuntimeException  error 발생 ");
    }
    @GetMapping("/api/Exception/Exception")
    public ResponseEntity<?> Exception() throws Exception {
        throw new Exception("Exception  발생 ");
    }

    @GetMapping("/api/Exception/domainException")
    public ResponseEntity<?> domainException()  {
        throw new DomainException("DomainException  발생 ");
    }

    @GetMapping("/api/Exception/MenuException")
    public ResponseEntity<?> MenuException()  {
        throw new MenuException("MenuException  발생 ");
    }

    @GetMapping("/api/Exception/CategoryException")
    public ResponseEntity<?> CategoryException()  {
        throw new CategoryException("CategoryException  발생 ");
    }


}
