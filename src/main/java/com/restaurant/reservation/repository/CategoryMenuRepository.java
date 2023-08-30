package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.CategoryMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryMenuRepository extends JpaRepository<CategoryMenu, Long> {


//    @Query("Select cm from CategoryMenu cm" +
//            " where cm.categoryCode like :code%" +
//            " order by cm.categoryCode asc")
//    List<CategoryMenu> findAllContainCode (@Param("code") String code);


    @Query("Select cm from CategoryMenu cm" +
            " join fetch cm.menu" +
            " where cm.categoryCode like :code%" +
            " order by cm.categoryCode asc")
    List<CategoryMenu> findAllContainCode (@Param("code") String code);

}
