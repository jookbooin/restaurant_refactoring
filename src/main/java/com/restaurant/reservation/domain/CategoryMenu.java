package com.restaurant.reservation.domain;

import javax.persistence.*;

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

//    @Column(name = "root_id")
//    private Long rootId;
    @Column(name ="category_branch")
    private String branch;          // root : 음식
    @Column(name ="category_name")
    private String categoryName;    // 주류
    @Column(name ="level")
    private Integer level;          // 2
    @Column(name ="menu_name")
    private String menuName;

    public CategoryMenu(Menu menu) {
        this.menu = menu;
    }

    public CategoryMenu(Category category) {
        this.category = category;
        this.branch = category.getBranch();
        this.categoryName = category.getName();
        this.level = category.getLevel();

    }

    public CategoryMenu() {

    }

    public static CategoryMenu menuName(Menu menu){
        return new CategoryMenu(menu);
    }
}
