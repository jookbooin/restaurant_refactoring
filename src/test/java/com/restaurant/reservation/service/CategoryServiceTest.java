package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.Category;
import com.restaurant.reservation.repository.dto.CategoryDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest

class CategoryServiceTest {


    @Autowired
    CategoryService categoryService;

    @Test
    @Transactional
    public void saveCategory2_영속성전이_Test() throws Exception{
        CategoryDto rootDto = new CategoryDto();
        rootDto.setBranch("음식");
        rootDto.setName("메인");

        CategoryDto foodDto1 = new CategoryDto();
        foodDto1.setBranch("음식");
        foodDto1.setParent("메인");
        foodDto1.setName("세트 메뉴");

        CategoryDto foodDto2 = new CategoryDto();
        foodDto2.setBranch("음식");
        foodDto2.setParent("메인");
        foodDto2.setName("단품");

        Category a = categoryService.saveCategory(rootDto);
        Category b = categoryService.saveCategory(foodDto1);   // 쿼리 2 + 1 + 3
        Category c = categoryService.saveCategory(foodDto2);

        Assertions.assertThat(a.getChildren().size()).isEqualTo(2);
        Assertions.assertThat(b.getChildren().size()).isEqualTo(0);
        Assertions.assertThat(c.getChildren().size()).isEqualTo(0);

        System.out.println("a.getBranch() = " + a.getBranch() +" a.getName() = "+a.getName()+" a.getLevel = "+a.getLevel() +" | a.getParent.getName = "+a.getParent().getName());
        System.out.println("b.getBranch() = " + b.getBranch() +" b.getName() = "+b.getName()+" b.getLevel = "+b.getLevel()+ " | b.getParent.getName = "+b.getParent().getName());
        System.out.println("c.getBranch() = " + c.getBranch() +" c.getName() = "+c.getName()+" c.getLevel = "+c.getLevel()+ " | c.getParent.getName = "+c.getParent().getName());

    }


    @Test
    @Transactional
    public void saveCategory_영속성전이_Test() throws Exception{
        CategoryDto rootDto = new CategoryDto();
        rootDto.setBranch("음식");

        CategoryDto l1_1 = new CategoryDto();
        l1_1.setBranch("음식");
        l1_1.setParent("root");
        l1_1.setName("메인");

        CategoryDto l1_2 = new CategoryDto();
        l1_2.setBranch("음식");
        l1_2.setParent("root");
        l1_2.setName("주류&음료");

        CategoryDto l2_1 = new CategoryDto();
        l2_1.setBranch("음식");
        l2_1.setParent("주류&음료");
        l2_1.setName("주류");

        CategoryDto l2_2 = new CategoryDto();
        l2_2.setBranch("음식");
        l2_2.setParent("주류&음료");
        l2_2.setName("음료");

        Category a = categoryService.saveCategory(rootDto);
        Category b = categoryService.saveCategory(l1_1);   // 쿼리 2 + 1 + 3
        Category c = categoryService.saveCategory(l1_2);
        Category e = categoryService.saveCategory(l2_1);
        Category f = categoryService.saveCategory(l2_2);

        Assertions.assertThat(a.getChildren().size()).isEqualTo(2);
        Assertions.assertThat(b.getChildren().size()).isEqualTo(0);
        Assertions.assertThat(c.getChildren().size()).isEqualTo(2);
        Assertions.assertThat(e.getChildren().size()).isEqualTo(0);
        Assertions.assertThat(f.getChildren().size()).isEqualTo(0);


        System.out.println("a.getBranch() = " + a.getBranch() +" | a.getName() = "+a.getName()+" | a.getLevel = "+a.getLevel() +" | a 는 root 입니다!!");
        System.out.println("b.getBranch() = " + b.getBranch() +" | b.getName() = "+b.getName()+" | b.getLevel = "+b.getLevel()+ " | b.getParent.getName = "+b.getParent().getName());
        System.out.println("c.getBranch() = " + c.getBranch() +" | c.getName() = "+c.getName()+" | c.getLevel = "+c.getLevel()+ " | c.getParent.getName = "+c.getParent().getName());
        System.out.println("e.getBranch() = " + e.getBranch() +" | e.getName() = "+e.getName()+" | e.getLevel = "+e.getLevel()+ " | e.getParent.getName = "+e.getParent().getName());
        System.out.println("f.getBranch() = " + f.getBranch() +" | f.getName() = "+f.getName()+" | f.getLevel = "+f.getLevel()+ " | f.getParent.getName = "+f.getParent().getName());

    }
}