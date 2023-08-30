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

class CategoryMenuServiceTest {

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

        /** 메뉴 생성 */
        MenuDto menuDto10 = MenuDto.builder().name("처음처럼").price(5000).build();
        MenuDto menuDto11 = MenuDto.builder().name("새로").price(4000).build();
        MenuDto menuDto12 = MenuDto.builder().name("콜라").price(3000).build();
        MenuDto menuDto20 = MenuDto.builder().name("토마호크").price(30000).build();
        MenuDto menuDto30 = MenuDto.builder().name("알프레도").price(30000).build();
        MenuDto menuDto31 = MenuDto.builder().name("로제").price(31000).build();
        MenuDto menuDto32 = MenuDto.builder().name("라자냐").price(32000).build();
        MenuDto menuDto33 = MenuDto.builder().name("까르보나라").price(33000).build();
        MenuDto menuDto34 = MenuDto.builder().name("뽀모도로").price(34000).build();
        MenuDto menuDto35 = MenuDto.builder().name("볼로네즈").price(35000).build();
        MenuDto menuDto36 = MenuDto.builder().name("나폴리탄").price(36000).build();
        MenuDto menuDto37 = MenuDto.builder().name("푸타네스카").price(37000).build();
        MenuDto menuDto38 = MenuDto.builder().name("봉골레").price(38000).build();
        MenuDto menuDto39 = MenuDto.builder().name("알리오올리오").price(39000).build();
        MenuDto menuDto50 = MenuDto.builder().name("감자튀김").price(6000).build();
        MenuDto menuDto51 = MenuDto.builder().name("콘치즈").price(4000).build();
        MenuDto menuDto52 = MenuDto.builder().name("볶음밥").price(7000).build();

        Menu menu10 = menuRepository.save(Menu.createMenu(menuDto10)); // 처음처럼
        Menu menu11 = menuRepository.save(Menu.createMenu(menuDto11)); // 새로
        Menu menu12 = menuRepository.save(Menu.createMenu(menuDto12)); // 콜라
        Menu menu20 = menuRepository.save(Menu.createMenu(menuDto20)); //
        Menu menu30 = menuRepository.save(Menu.createMenu(menuDto30)); //
        Menu menu31 = menuRepository.save(Menu.createMenu(menuDto31)); //
        Menu menu32 = menuRepository.save(Menu.createMenu(menuDto32)); //
        Menu menu33 = menuRepository.save(Menu.createMenu(menuDto33)); //
        Menu menu34 = menuRepository.save(Menu.createMenu(menuDto34)); //
        Menu menu35 = menuRepository.save(Menu.createMenu(menuDto35)); //
        Menu menu36 = menuRepository.save(Menu.createMenu(menuDto36)); //
        Menu menu37 = menuRepository.save(Menu.createMenu(menuDto37)); //
        Menu menu38 = menuRepository.save(Menu.createMenu(menuDto38)); //
        Menu menu39 = menuRepository.save(Menu.createMenu(menuDto39)); //
        Menu menu50 = menuRepository.save(Menu.createMenu(menuDto50)); //
        Menu menu51 = menuRepository.save(Menu.createMenu(menuDto51)); //
        Menu menu52 = menuRepository.save(Menu.createMenu(menuDto52)); //

        /***/
        Category category1 = categoryRepository.findByName("주류").orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다"));
        Category category2 = categoryRepository.findByName("음료").orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다"));
        Category category3 = categoryRepository.findByName("파스타").orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다"));
        Category category4 = categoryRepository.findByName("스테이크").orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다"));
        Category category5 = categoryRepository.findByName("사이드").orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다"));
        Category category6 = categoryRepository.findByName("스페셜").orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다"));

        /** 카테고리 메뉴 */

        CategoryMenu cm10 = CategoryMenu.createCategoryMenu(menu10, category1);
        CategoryMenu cm11 = CategoryMenu.createCategoryMenu(menu11, category1);
        CategoryMenu cm12 = CategoryMenu.createCategoryMenu(menu12, category2);
        CategoryMenu cm20 = CategoryMenu.createCategoryMenu(menu20, category4);
        CategoryMenu cm30 = CategoryMenu.createCategoryMenu(menu30, category3);
        CategoryMenu cm31 = CategoryMenu.createCategoryMenu(menu31, category3);
        CategoryMenu cm32 = CategoryMenu.createCategoryMenu(menu32, category3);
        CategoryMenu cm33 = CategoryMenu.createCategoryMenu(menu33, category3);
        CategoryMenu cm34 = CategoryMenu.createCategoryMenu(menu34, category3);
        CategoryMenu cm35 = CategoryMenu.createCategoryMenu(menu35, category3);
        CategoryMenu cm36 = CategoryMenu.createCategoryMenu(menu36, category3);
        CategoryMenu cm37 = CategoryMenu.createCategoryMenu(menu37, category3);
        CategoryMenu cm38 = CategoryMenu.createCategoryMenu(menu38, category3);
        CategoryMenu cm39 = CategoryMenu.createCategoryMenu(menu39, category3);
        CategoryMenu cm50 = CategoryMenu.createCategoryMenu(menu50, category5);
        CategoryMenu cm51 = CategoryMenu.createCategoryMenu(menu51, category5);
        CategoryMenu cm52 = CategoryMenu.createCategoryMenu(menu52, category5);

        categoryMenuRepository.save(cm10);
        categoryMenuRepository.save(cm11);
        categoryMenuRepository.save(cm12);
        categoryMenuRepository.save(cm20);
        categoryMenuRepository.save(cm30);
        categoryMenuRepository.save(cm31);
        categoryMenuRepository.save(cm32);
        categoryMenuRepository.save(cm33);
        categoryMenuRepository.save(cm34);
        categoryMenuRepository.save(cm35);
        categoryMenuRepository.save(cm36);
        categoryMenuRepository.save(cm37);
        categoryMenuRepository.save(cm38);
        categoryMenuRepository.save(cm39);
        categoryMenuRepository.save(cm50);
        categoryMenuRepository.save(cm51);
        categoryMenuRepository.save(cm52);

    }


    @Test
    @Transactional
    @Rollback(false)
    public void categoryMenu_넣기() throws Exception {

        List<CategoryMenu> categoryMenuList = categoryMenuRepository.findAll();
        assertThat(categoryMenuList.size()).isEqualTo(17);
    }

    @Test
    @Transactional
    public void categoryMenu_Code조회() throws Exception {
        String categoryCode = categoryRepository.findCodeByName("주류").orElseThrow(() -> new IllegalArgumentException("잘못된 카테고리 명"));
        List<CategoryMenu> result = categoryMenuRepository.findAllContainCode(categoryCode);
        assertThat(result.size()).isEqualTo(2);
    }

}