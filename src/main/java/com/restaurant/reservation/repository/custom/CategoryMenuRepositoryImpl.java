package com.restaurant.reservation.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.restaurant.reservation.domain.CategoryMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.util.List;

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

    @Override
    public Page<CategoryMenu> findAllContainCode(String code, Pageable pageable) {
        List<CategoryMenu> content = queryFactory.selectFrom(categoryMenu)
                .join(categoryMenu.category, category).fetchJoin()
                .join(categoryMenu.menu, menu).fetchJoin()
                .where(containCode(code))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(categoryMenu.categoryCode.asc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(categoryMenu.count())
                .from(categoryMenu)
                .where(containCode(code));

        return PageableExecutionUtils.getPage(content,pageable,countQuery::fetchOne);

    }

    private BooleanExpression containCode(String code) {
      return StringUtils.hasText(code) ? categoryMenu.categoryCode.contains(code) : null;
    }
}
