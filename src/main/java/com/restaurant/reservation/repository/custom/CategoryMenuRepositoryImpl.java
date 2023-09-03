package com.restaurant.reservation.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import static com.restaurant.reservation.domain.QCategory.category;
import static com.restaurant.reservation.domain.QCategoryMenu.categoryMenu;
import static com.restaurant.reservation.domain.QMenu.menu;

public class CategoryMenuRepositoryImpl  implements CategoryMenuRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public CategoryMenuRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public boolean existsByNames(String categoryName, String menuName) {

        /** cross join 피하기위해서 명시적 조인을 사용해야한다. */
        Integer result = queryFactory
                .selectOne()
                .from(categoryMenu)
                .innerJoin(categoryMenu.category, category)
                .innerJoin(categoryMenu.menu, menu)
                .where(categoryMenu.menu.name.eq(menuName)
                        .and(categoryMenu.category.name.eq(categoryName)))
                .fetchFirst();
        return result !=null;
    }
}
