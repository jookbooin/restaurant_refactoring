package com.restaurant.reservation.repository.custom;

public interface CategoryMenuRepositoryCustom {

    boolean existsByNames(String categoryName , String menuName);
}
