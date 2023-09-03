package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.CategoryMenu;
import com.restaurant.reservation.repository.custom.CategoryMenuRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryMenuRepository extends JpaRepository<CategoryMenu, Long> , CategoryMenuRepositoryCustom {


//    @Query("Select cm from CategoryMenu cm" +
//            " where cm.categoryCode like :code%" +
//            " order by cm.categoryCode asc")
//    List<CategoryMenu> findAllContainCode (@Param("code") String code);


    @Query("Select cm from CategoryMenu cm" +
            " join fetch cm.menu" +
            " join fetch cm.category" +
            " where cm.categoryCode like :code%" +
            " order by cm.categoryCode asc")
    List<CategoryMenu> findAllContainCode (@Param("code") String code);

    @Query("Select cm from CategoryMenu cm" +
            " join fetch cm.menu" +
            " join fetch cm.category" +
            " where cm.menu.id = :id")
    Optional<CategoryMenu> findByMenuId(@Param("id")Long id);

    boolean existsByCategory_NameAndMenu_Name(String categoryName,String menuName);
}
