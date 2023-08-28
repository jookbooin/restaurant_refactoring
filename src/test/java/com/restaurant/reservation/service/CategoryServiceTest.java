package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.Category;
import com.restaurant.reservation.domain.CategoryMenu;
import com.restaurant.reservation.domain.Menu;
import com.restaurant.reservation.repository.CategoryMenuRepository;
import com.restaurant.reservation.repository.CategoryRepository;
import com.restaurant.reservation.repository.MenuRepository;
import com.restaurant.reservation.repository.dto.CategoryDto;
import com.restaurant.reservation.repository.dto.MenuDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

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
        CategoryDto rootDto = new CategoryDto();
        rootDto.setName("음식");

        CategoryDto l1_1 = new CategoryDto();
        l1_1.setParent("음식");
        l1_1.setName("메인");

        CategoryDto l1_2 = new CategoryDto();
        l1_2.setParent("음식");
        l1_2.setName("주류&음료");

        CategoryDto l2_1 = new CategoryDto();
        l2_1.setParent("주류&음료");
        l2_1.setName("주류");

        CategoryDto l2_2 = new CategoryDto();
        l2_2.setParent("주류&음료");
        l2_2.setName("음료");

        Category a = categoryService.saveCategory(rootDto);
        Category b = categoryService.saveCategory(l1_1);   // 쿼리 2 + 1 + 3
        Category c = categoryService.saveCategory(l1_2);
        Category e = categoryService.saveCategory(l2_1);
        Category f = categoryService.saveCategory(l2_2);


        CategoryDto rootDto2 = new CategoryDto();
        rootDto2.setName("책");
        CategoryDto rootDto3 = new CategoryDto();
        rootDto3.setName("영화");

        Category book = categoryService.saveCategory(rootDto2);
        Category movie = categoryService.saveCategory(rootDto3);
    }



    @Test
    @Transactional
    @Rollback(false)
    public void saveCategory_영속성전이_Test() throws Exception{
        CategoryDto rootDto = new CategoryDto();
        rootDto.setName("음식");

        CategoryDto l1_1 = new CategoryDto();
        l1_1.setParent("음식");
        l1_1.setName("메인");

        CategoryDto l1_2 = new CategoryDto();
        l1_2.setParent("음식");
        l1_2.setName("주류&음료");

        CategoryDto l2_1 = new CategoryDto();
        l2_1.setParent("주류&음료");
        l2_1.setName("주류");

        CategoryDto l2_2 = new CategoryDto();
        l2_2.setParent("주류&음료");
        l2_2.setName("음료");

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

    @Test
    @Transactional
    @Rollback(false)
    public void categoryMenu_넣기() throws Exception {
        MenuDto menuDto1 = MenuDto.builder().name("처음처럼").price(5000).build();
        MenuDto menuDto2 = MenuDto.builder().name("새로").price(4000).build();
        MenuDto menuDto3 = MenuDto.builder().name("콜라").price(3000).build();

        Menu menu1 = menuRepository.save(Menu.createMenu(menuDto1)); // 처음처럼
        Menu menu2 = menuRepository.save(Menu.createMenu(menuDto2)); // 새로
        Menu menu3 = menuRepository.save(Menu.createMenu(menuDto3)); // 콜라

        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList.size()).isEqualTo(3);

        Category category1 = categoryRepository.findByName("주류").orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다"));
        Category category2 = categoryRepository.findByName("음료").orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다"));

        assertThat(category1.getLevel()).isEqualTo(2);
        assertThat(category2.getLevel()).isEqualTo(2);

        CategoryMenu alchol1 = CategoryMenu.createCategoryMenu(menu1, category1);
        CategoryMenu alchol2 = CategoryMenu.createCategoryMenu(menu2, category1);
        CategoryMenu drink1 = CategoryMenu.createCategoryMenu(menu3, category2);

        categoryMenuRepository.save(alchol1);
        categoryMenuRepository.save(alchol2);
        categoryMenuRepository.save(drink1);

        List<CategoryMenu> categoryMenuList = categoryMenuRepository.findAll();

        assertThat(categoryMenuList.size()).isEqualTo(3);


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