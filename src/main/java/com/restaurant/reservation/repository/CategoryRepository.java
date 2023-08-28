package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName (String name);
//    Optional<Category> findByBranchAndName(String branch , String name);
//    Optional<Category> findByBranchAndId(String branch , Long id);
//    boolean existsByBranchAndName(String branch , String name);

    
    List<Category> findByParentIsNull(); // root 카테고리 찾기
    boolean existsByName(String name);




}
