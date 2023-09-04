package com.restaurant.reservation.repository.custom;

import com.restaurant.reservation.domain.CategoryMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryMenuRepositoryCustom {

    boolean existsByNames(String categoryName , String menuName);

    Page<CategoryMenu> findAllContainCode(String code, Pageable pageable);

}
