package com.restaurant.reservation.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Getter
@Setter // test 용
@Entity
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

//    @Column(name = "root_id")
//    private Long rootId;
//    @Column(name = "branch")
//    private String branch; // 카테고리 분류 : 음식 , 게시글
    private String code;  // 카테고리 분류 코드
    @Column(name = "name")
    private String name;  // 카테고리 명
    @Column(name = "level")
    private Integer level;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent" , cascade = CascadeType.REMOVE)
    private List<Category> children = new ArrayList<>();

    // 1. cascade = CascadeType.ALL , orphanRemoval = true 써야할까?
    // 2.
//    @OneToMany(mappedBy = "reservation")
//    List<CategoryMenu> categoryMenuList = new ArrayList<>();

    @Builder
    public Category(Long id, String code, String name, Integer level, Category parent, List<Category> children) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.level = level;
        this.parent = parent;
        if(children != null)
            this.children = children;
    }

    public void updateLevel(Integer level){
        this.level=level;
    }
    public void connectParent(Category parent){
        this.parent =parent;
        parent.getChildren().add(this);
    }
    
    // 생성 메서드
    // branch 타입의 최상단 카테고리
    public static Category root(String name,String code){
        return Category.builder()
                .level(0)
                .code(code)
                .name(name)
                .build();
    }

    public static Category createCategory(String name,String code){
        return Category.builder()
                .name(name)
                .code(code)
                .build();
    }
}
