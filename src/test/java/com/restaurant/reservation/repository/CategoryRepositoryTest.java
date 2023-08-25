package com.restaurant.reservation.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CategoryRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    public void before(){
        for (int i = 0; i < 1000; i++) {
//            Category category = new Category();
//            category.setName("음식");
//            categoryRepository.save(category);
        }
        em.flush();
        em.clear();
    }

    @Test
    public void existsByTest() throws Exception {

        System.out.println("== After ==");

        boolean flag =categoryRepository.existsByName("음식");

        assertThat(flag).isTrue();
    }

}