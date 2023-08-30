package com.restaurant.reservation.domain;

import lombok.*;

import javax.persistence.*;
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CategoryMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_menu_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    Menu menu;
    @Column(name ="category_code")
    private String categoryCode;

    @Column(name ="level")
    private Integer level;          // 2
//    @Column(name ="menu_name")
//    private String menuName;
//
//    @Column(name ="category_name")
//    private String categoryName;    // 주류




    public CategoryMenu(Category category, Menu menu) {
        //카테고리
        this.category = category;
        this.menu = menu;
    }

    @Builder
    public CategoryMenu(Long id, Category category, Menu menu, String categoryCode, Integer level) {
        this.id = id;
        this.category = category;
        this.menu = menu;
        this.categoryCode = categoryCode;
        this.level = level;
    }
    //    @Builder
//    public CategoryMenu(Long id, Category category, Menu menu, String categoryCode, String categoryName, Integer level, String menuName) {
//        this.id = id;
//        this.category = category;
//        this.menu = menu;
//        this.categoryCode = categoryCode;
//        this.categoryName = categoryName;
//        this.level = level;
//        this.menuName = menuName;
//    }




    public static CategoryMenu createCategoryMenu(Menu menu , Category category) {

        return CategoryMenu.builder()
                .menu(menu)
                .category(category)
                .categoryCode(category.getCode())
                .level(category.getLevel()).build();
    }

    /** 추가되거나 삭제될 수도있어 메서드로 만듬 */
    public void updateCategoryLevel(Category category){
        this.level=category.getLevel();
    }





}
