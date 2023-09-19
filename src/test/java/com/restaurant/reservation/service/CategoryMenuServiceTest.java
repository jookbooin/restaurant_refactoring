package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.Category;
import com.restaurant.reservation.domain.CategoryMenu;
import com.restaurant.reservation.domain.Menu;
import com.restaurant.reservation.exception.domain.MenuException;
import com.restaurant.reservation.repository.CategoryMenuRepository;
import com.restaurant.reservation.repository.CategoryRepository;
import com.restaurant.reservation.repository.MenuRepository;
import com.restaurant.reservation.repository.dto.CategoryDto;
import com.restaurant.reservation.repository.dto.MenuDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest

class CategoryMenuServiceTest {

    @Autowired
    MenuRepository menuRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryMenuRepository categoryMenuRepository;

    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryMenuService categoryMenuService;

    @Autowired
    EntityManager em;

    @Profile("test")
    @BeforeEach
    public void categoryInit(){
        /** root 카테고리 생성 */
        CategoryDto rootDto1 = CategoryDto.builder().name("음식").code("A1").build();
        CategoryDto rootDto2 = CategoryDto.builder().name("책").code("A2").build();
        CategoryDto rootDto3 = CategoryDto.builder().name("영화").code("A3").build();

        Category A1 = categoryService.saveCategory(rootDto1);
        Category A2 = categoryService.saveCategory(rootDto2);
        Category A3 = categoryService.saveCategory(rootDto3);

        /** 하위 카테고리 생성 */
        CategoryDto rootDto1l1_1 = CategoryDto.builder().parent("음식").name("메인").code("B1").build();      // 메인
        CategoryDto rootDto1l1_2 = CategoryDto.builder().parent("음식").name("사이드").code("B2").build();    // 사이드
        CategoryDto rootDto1l1_3 = CategoryDto.builder().parent("음식").name("주류&음료").code("B3").build(); // 주류&음료
        CategoryDto rootDto1l2_1 = CategoryDto.builder().parent("주류&음료").name("주류").code("C1").build(); // 주류
        CategoryDto rootDto1l2_2 = CategoryDto.builder().parent("주류&음료").name("음료").code("C2").build(); // 음료
        CategoryDto rootDto1l2_3 = CategoryDto.builder().parent("메인").name("스페셜").code("C3").build();    // 스페셜
        CategoryDto rootDto1l2_4 = CategoryDto.builder().parent("메인").name("스테이크").code("C4").build();  // 스테이크
        CategoryDto rootDto1l2_5 = CategoryDto.builder().parent("메인").name("파스타").code("C5").build();    // 파스타

        Category A1B1 = categoryService.saveCategory(rootDto1l1_1);   // 메인
        Category A1B2 = categoryService.saveCategory(rootDto1l1_2);   // 사이드
        Category A1B3 = categoryService.saveCategory(rootDto1l1_3);   // 주류&음료
        Category A1B3C1 = categoryService.saveCategory(rootDto1l2_1);   // 주류
        Category A1B3C2 = categoryService.saveCategory(rootDto1l2_2);   // 음료
        Category A1B1C3 = categoryService.saveCategory(rootDto1l2_3);   // 스페셜
        Category A1B1C4 = categoryService.saveCategory(rootDto1l2_4);   // 스테이크
        Category A1B1C5 = categoryService.saveCategory(rootDto1l2_5);   // 파스타

        /** category menu*/
        MenuDto menuDto10 = MenuDto.builder().name("처음처럼").price(5000).description("주류 - A1B3C1").build();
        MenuDto menuDto11 = MenuDto.builder().name("새로").price(4000).description("주류 - A1B3C1").build();
        MenuDto menuDto12 = MenuDto.builder().name("콜라").price(3000).description("음료 - A1B3C2").build();
        MenuDto menuDto20 = MenuDto.builder().name("토마호크").price(30000).description("스테이크 - A1B1C4").build();
        MenuDto menuDto30 = MenuDto.builder().name("알프레도").price(30000).description("알프레도 파스타 - A1B1C5").build();
        MenuDto menuDto31 = MenuDto.builder().name("로제").price(31000).description("로제 파스타 - A1B1C5").build();
        MenuDto menuDto32 = MenuDto.builder().name("라자냐").price(32000).description("라자냐 파스타 - A1B1C5").build();
        MenuDto menuDto33 = MenuDto.builder().name("까르보나라").price(33000).description("까르보나라 파스타 - A1B1C5").build();
        MenuDto menuDto34 = MenuDto.builder().name("뽀모도로").price(34000).description("뽀모도로 파스타 - A1B1C5").build();
        MenuDto menuDto35 = MenuDto.builder().name("볼로네즈").price(35000).description("볼로네즈파스타 - A1B1C5").build();
        MenuDto menuDto36 = MenuDto.builder().name("나폴리탄").price(36000).description("나폴리탄 파스타 - A1B1C5").build();
        MenuDto menuDto37 = MenuDto.builder().name("푸타네스카").price(37000).description("푸타네스카 파스타 - A1B1C5").build();
        MenuDto menuDto38 = MenuDto.builder().name("봉골레").price(38000).description("봉골레 파스타 - A1B1C5").build();
        MenuDto menuDto39 = MenuDto.builder().name("알리오올리오").price(39000).description("알리오올리오 파스타 - A1B1C5").build();
        MenuDto menuDto50 = MenuDto.builder().name("감자튀김").price(6000).description("감자튀김 사이드 - A1B2").build();
        MenuDto menuDto51 = MenuDto.builder().name("콘치즈").price(4000).description("콘치즈 사이드 - A1B2").build();
        MenuDto menuDto52 = MenuDto.builder().name("볶음밥").price(7000).description("볶음밥 사이드 - A1B2").build();

        MenuDto menuDto60 = MenuDto.builder().name("코스 A").price(70000).description("코스 A - A1B1C3").build();
        MenuDto menuDto61 = MenuDto.builder().name("코스 B").price(80000).description("코스 B - A1B1C3").build();
        MenuDto menuDto62 = MenuDto.builder().name("코스 C").price(90000).description("코스 C - A1B1C3").build();

        CategoryMenu alcohol1 = categoryMenuService.save("주류", menuDto10);
        CategoryMenu alcohol2 = categoryMenuService.save("주류", menuDto11);
        CategoryMenu beverage1 = categoryMenuService.save("음료", menuDto12);
        CategoryMenu stake1 = categoryMenuService.save("스테이크", menuDto20);
        CategoryMenu pasta1 = categoryMenuService.save("파스타", menuDto30);
        CategoryMenu pasta2 = categoryMenuService.save("파스타", menuDto31);
        CategoryMenu pasta3 = categoryMenuService.save("파스타", menuDto32);
        CategoryMenu pasta4 = categoryMenuService.save("파스타", menuDto33);
        CategoryMenu pasta5 = categoryMenuService.save("파스타", menuDto34);
        CategoryMenu pasta6 = categoryMenuService.save("파스타", menuDto35);
        CategoryMenu pasta7 = categoryMenuService.save("파스타", menuDto36);
        CategoryMenu pasta8 = categoryMenuService.save("파스타", menuDto37);
        CategoryMenu pasta9 = categoryMenuService.save("파스타", menuDto38);
        CategoryMenu pasta10 = categoryMenuService.save("파스타", menuDto39);
        CategoryMenu side1 = categoryMenuService.save("사이드", menuDto50);
        CategoryMenu side2 = categoryMenuService.save("사이드", menuDto51);
        CategoryMenu side3 = categoryMenuService.save("사이드", menuDto52);
        CategoryMenu special1 = categoryMenuService.save("스페셜", menuDto60);
        CategoryMenu special2 = categoryMenuService.save("스페셜", menuDto61);
        CategoryMenu special3 = categoryMenuService.save("스페셜", menuDto62);

    }


    @Test
    @Transactional
    public void categoryMenu_save() throws Exception {

        MenuDto menuDto10 = MenuDto.builder().name("처음처럼").price(5000).description("주류 - A1B3C1").build();
        assertThrows(MenuException.class, () -> {
            categoryMenuService.save( "주류",menuDto10);
        });

    }

    @Test
    @Transactional
    public void categoryMenu_Code조회() throws Exception {
        final String categoryName = "스페셜";
        String categoryCode = categoryService.findCode(categoryName);
        List<CategoryMenu> result = categoryMenuService.findCategoryMenu(categoryCode);

        assertThat(result.size()).isEqualTo(3);
    }
    @Test
    @Transactional
    @Rollback(false)
    public void menu_수정() throws Exception  {
        String categoryName = "음료";
        Long id = 1L;
        MenuDto menuDto10 = MenuDto.builder().id(id).name("파워에이드").price(1000).description("주류 -> 파워에이드 교체").build();

        Menu findMenu = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("테스트 실패"));
        System.out.println("findMenu = " + findMenu);

        em.flush();
        em.clear();

        categoryMenuService.update(categoryName,menuDto10);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void menu_삭제() throws Exception  {
        Long id = 1L;
        categoryMenuService.delete(id);
        List<CategoryMenu> all = categoryMenuRepository.findAll();
        assertThat(all.size()).isEqualTo(16);
    }


}