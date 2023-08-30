package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.Category;
import com.restaurant.reservation.repository.CategoryMenuRepository;
import com.restaurant.reservation.repository.CategoryRepository;
import com.restaurant.reservation.repository.MenuRepository;
import com.restaurant.reservation.repository.dto.CategoryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest

class CategoryServiceTest {

    @Autowired
    MenuRepository menuRepository;
    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryMenuRepository categoryMenuRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    public void categoryInit(){
        /** root 카테고리 생성 */
        CategoryDto rootDto1 = CategoryDto.builder().name("음식").code("A1").build();
        CategoryDto rootDto2 = CategoryDto.builder().name("책").code("A2").build();
        CategoryDto rootDto3 = CategoryDto.builder().name("영화").code("A3").build();

        Category root1 = categoryService.saveCategory(rootDto1);
        Category root2 = categoryService.saveCategory(rootDto2);
        Category root3 = categoryService.saveCategory(rootDto3);

        /** 하위 카테고리 생성 */
        CategoryDto rootDto1l1_1 = CategoryDto.builder().parent("음식").name("메인").code("B1").build();
        CategoryDto rootDto1l1_2 = CategoryDto.builder().parent("음식").name("사이드").code("B2").build();
        CategoryDto rootDto1l1_3 = CategoryDto.builder().parent("음식").name("주류&음료").code("B3").build();
        CategoryDto rootDto1l2_1 = CategoryDto.builder().parent("주류&음료").name("주류").code("C1").build();
        CategoryDto rootDto1l2_2 = CategoryDto.builder().parent("주류&음료").name("음료").code("C2").build();
        CategoryDto rootDto1l2_3 = CategoryDto.builder().parent("메인").name("스페셜").code("C3").build();
        CategoryDto rootDto1l2_4 = CategoryDto.builder().parent("메인").name("스테이크").code("C4").build();
        CategoryDto rootDto1l2_5 = CategoryDto.builder().parent("메인").name("파스타").code("C5").build();

        Category root1l1_1 = categoryService.saveCategory(rootDto1l1_1);   // 쿼리 2 + 1 + 3
        Category root1l1_2 = categoryService.saveCategory(rootDto1l1_2);
        Category root1l1_3 = categoryService.saveCategory(rootDto1l1_3);
        Category root1l2_1 = categoryService.saveCategory(rootDto1l2_1);
        Category root1l2_2 = categoryService.saveCategory(rootDto1l2_2);
        Category root1l2_3 = categoryService.saveCategory(rootDto1l2_3);
        Category root1l2_4 = categoryService.saveCategory(rootDto1l2_4);
        Category root1l2_5 = categoryService.saveCategory(rootDto1l2_5);


    }



    @Test
    @Transactional
    @Rollback(false)
    public void saveCategory_영속성전이_Test() throws Exception{
        CategoryDto rootDto = new CategoryDto();
        rootDto.setName("음식");

        CategoryDto l1_1 = CategoryDto.builder().parent("음식").name("메인").build();

        CategoryDto l1_2 = CategoryDto.builder().parent("음식").name("주류&음료").build();

        CategoryDto l1_3 = CategoryDto.builder().parent("음식").name("사이드").build();

        CategoryDto l2_1 = CategoryDto.builder().parent("주류&음료").name("주류").build();

        CategoryDto l2_2 = CategoryDto.builder().parent("주류&음료").name("음료").build();

        Category a = categoryService.saveCategory(rootDto);

        Category b = categoryService.saveCategory(l1_1);   // 쿼리 2 + 1 + 3
        Category c = categoryService.saveCategory(l1_2);
        Category e = categoryService.saveCategory(l2_1);
        Category f = categoryService.saveCategory(l2_2);

        assertThat(a.getChildren().size()).isEqualTo(2);
        assertThat(b.getChildren().size()).isEqualTo(0);
        assertThat(c.getChildren().size()).isEqualTo(2);
        assertThat(e.getChildren().size()).isEqualTo(0);
        assertThat(f.getChildren().size()).isEqualTo(0);


        System.out.println(" | a.getName() = "+a.getName()+" | a.getLevel = "+a.getLevel() +" | a 는 root 입니다!!");
        System.out.println(" | b.getName() = "+b.getName()+" | b.getLevel = "+b.getLevel()+ " | b.getParent.getName = "+b.getParent().getName());
        System.out.println(" | c.getName() = "+c.getName()+" | c.getLevel = "+c.getLevel()+ " | c.getParent.getName = "+c.getParent().getName());
        System.out.println(" | e.getName() = "+e.getName()+" | e.getLevel = "+e.getLevel()+ " | e.getParent.getName = "+e.getParent().getName());
        System.out.println(" | f.getName() = "+f.getName()+" | f.getLevel = "+f.getLevel()+ " | f.getParent.getName = "+f.getParent().getName());

        System.out.println("\n == a ==");
        for (Category child : a.getChildren()) {
            System.out.println("a 자식 카테고리 - 이름 :  " + child.getName() +" id : " +child.getId());
        }

        System.out.println("\n == b ==");
        for (Category child : b.getChildren()) {
            System.out.println("b 자식 카테고리 - 이름 :  " + child.getName() +" id : " +child.getId());
        }

        System.out.println("\n == c ==");
        for (Category child : c.getChildren()) {
            System.out.println("c 자식 카테고리 - 이름 :  " + child.getName() +" id : " +child.getId());
        }

    }



//    @Test
//    @Transactional
//    @Rollback(false)
//    public void 카테고리_삭제_Test() throws Exception{
//        CategoryDto rootDto = new CategoryDto();
//        rootDto.setBranch("음식");
//
//        CategoryDto l1_1 = new CategoryDto();
//        l1_1.setBranch("음식");
//        l1_1.setParent("root");
//        l1_1.setName("메인");
//
//        CategoryDto l1_2 = new CategoryDto();
//        l1_2.setBranch("음식");
//        l1_2.setParent("root");
//        l1_2.setName("주류&음료");
//
//        CategoryDto l2_1 = new CategoryDto();
//        l2_1.setBranch("음식");
//        l2_1.setParent("주류&음료");
//        l2_1.setName("주류");
//
//        CategoryDto l2_2 = new CategoryDto();
//        l2_2.setBranch("음식");
//        l2_2.setParent("주류&음료");
//        l2_2.setName("음료");
//
//        Category a = categoryService.saveCategory(rootDto);
//        Category b = categoryService.saveCategory(l1_1);   // 쿼리 2 + 1 + 3
//        Category c = categoryService.saveCategory(l1_2);
//        Category e = categoryService.saveCategory(l2_1);
//        Category f = categoryService.saveCategory(l2_2);
//
//        assertThat(a.getChildren().size()).isEqualTo(2);
//        assertThat(b.getChildren().size()).isEqualTo(0);
//        assertThat(c.getChildren().size()).isEqualTo(2);
//        assertThat(e.getChildren().size()).isEqualTo(0);
//        assertThat(f.getChildren().size()).isEqualTo(0);
//
//        System.out.println("\n == 입력 ==");
//        System.out.println("a.getBranch() = " + a.getBranch() +" | a.getName() = "+a.getName()+" | a.getLevel = "+a.getLevel() +" | a 는 root 입니다!!");
//        System.out.println("b.getBranch() = " + b.getBranch() +" | b.getName() = "+b.getName()+" | b.getLevel = "+b.getLevel()+ " | b.getParent.getName = "+b.getParent().getName());
//        System.out.println("c.getBranch() = " + c.getBranch() +" | c.getName() = "+c.getName()+" | c.getLevel = "+c.getLevel()+ " | c.getParent.getName = "+c.getParent().getName());
//        System.out.println("e.getBranch() = " + e.getBranch() +" | e.getName() = "+e.getName()+" | e.getLevel = "+e.getLevel()+ " | e.getParent.getName = "+e.getParent().getName());
//        System.out.println("f.getBranch() = " + f.getBranch() +" | f.getName() = "+f.getName()+" | f.getLevel = "+f.getLevel()+ " | f.getParent.getName = "+f.getParent().getName());
//
//
////        Category findCategory = categoryRepository.findById(c.getId()).get();
////        assertThat(findCategory.getChildren().size()).isEqualTo(2);
//
//        System.out.println("\n == 삭제 ==");
//        categoryService.deleteCategory(a.getBranch(),a.getId());
//
////        em.flush();
////        em.clear();
////        System.out.println(" flush 이후");
////
////        findCategory = categoryRepository.findById(c.getId()).get();
////        assertThat(findCategory.getChildren().size()).isEqualTo(1);
//
//    }
//
//    @Test
//    @Transactional
//    @Rollback(false)
//    public void 카테고리_삭제_orphanremove_Test() throws Exception{
//        CategoryDto rootDto = new CategoryDto();
//        rootDto.setBranch("음식");
//
//        CategoryDto l1_1 = new CategoryDto();
//        l1_1.setBranch("음식");
//        l1_1.setParent("root");
//        l1_1.setName("메인");
//
//        CategoryDto l1_2 = new CategoryDto();
//        l1_2.setBranch("음식");
//        l1_2.setParent("root");
//        l1_2.setName("주류&음료");
//
//        CategoryDto l2_1 = new CategoryDto();
//        l2_1.setBranch("음식");
//        l2_1.setParent("주류&음료");
//        l2_1.setName("주류");
//
//        CategoryDto l2_2 = new CategoryDto();
//        l2_2.setBranch("음식");
//        l2_2.setParent("주류&음료");
//        l2_2.setName("음료");
//
//        Category a = categoryService.saveCategory(rootDto);
//        Category b = categoryService.saveCategory(l1_1);   // 쿼리 2 + 1 + 3
//        Category c = categoryService.saveCategory(l1_2);
//        Category e = categoryService.saveCategory(l2_1);
//        Category f = categoryService.saveCategory(l2_2);
//
//        assertThat(a.getChildren().size()).isEqualTo(2);
//        assertThat(b.getChildren().size()).isEqualTo(0);
//        assertThat(c.getChildren().size()).isEqualTo(2);
//        assertThat(e.getChildren().size()).isEqualTo(0);
//        assertThat(f.getChildren().size()).isEqualTo(0);
//
//        System.out.println("\n == 입력 ==");
//        System.out.println("a.getBranch() = " + a.getBranch() +" | a.getName() = "+a.getName()+" | a.getLevel = "+a.getLevel() +" | a 는 root 입니다!!");
//        System.out.println("b.getBranch() = " + b.getBranch() +" | b.getName() = "+b.getName()+" | b.getLevel = "+b.getLevel()+ " | b.getParent.getName = "+b.getParent().getName());
//        System.out.println("c.getBranch() = " + c.getBranch() +" | c.getName() = "+c.getName()+" | c.getLevel = "+c.getLevel()+ " | c.getParent.getName = "+c.getParent().getName());
//        System.out.println("e.getBranch() = " + e.getBranch() +" | e.getName() = "+e.getName()+" | e.getLevel = "+e.getLevel()+ " | e.getParent.getName = "+e.getParent().getName());
//        System.out.println("f.getBranch() = " + f.getBranch() +" | f.getName() = "+f.getName()+" | f.getLevel = "+f.getLevel()+ " | f.getParent.getName = "+f.getParent().getName());
//
//        em.flush();
//        em.clear();
//        System.out.println("flush 이후");
//
//        Category findCategory = categoryRepository.findById(c.getId()).get();
//        assertThat(findCategory.getChildren().size()).isEqualTo(2);
//
//        System.out.println("\n == 삭제 ==");
//        categoryService.deleteCategory(c.getBranch(),c.getId());
//        em.flush();
//        em.clear();
//        System.out.println(" flush 이후");
//        findCategory = categoryRepository.findById(a.getId()).get();
//        assertThat(findCategory.getChildren().size()).isEqualTo(1);
//
//    }
}