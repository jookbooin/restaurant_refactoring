package com.restaurant.reservation.service;

import com.restaurant.reservation.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryApiService {
    private final CategoryRepository categoryRepository;


}
