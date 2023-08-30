package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName (String name);
//    Optional<Category> findByBranchAndName(String branch , String name);
//    Optional<Category> findByBranchAndId(String branch , Long id);
//    boolean existsByBranchAndName(String branch , String name);

    List<Category> findByParentIsNullOrderByIdAsc(); // only_spring_data

    @Query("Select c.code from Category c where c.name = :name")
    Optional<String> findCodeByName(@Param("name") String name);
    @Query("Select c from Category c where c.parent = NULL order by c.id")  // 페이징 할 필요없을듯
    List<Category> findParentWithFetchJoin();

    boolean existsByName(String name);




}
