package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.Category;
import com.restaurant.reservation.exception.domain.CategoryException;
import com.restaurant.reservation.repository.CategoryRepository;
import com.restaurant.reservation.repository.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category saveCategory(CategoryDto categoryDto) {

        StringBuilder sb = new StringBuilder();

        Category currentCategory = null;
//                Category.createCategory(categoryDto);

        if (StringUtils.hasText(categoryDto.getParent())) { // null 체크
            log.info("부모가 존재합니다");

//            if (categoryRepository.existsByName(categoryDto.getName())) {
//                log.info("error - 이미 root가 존재합니다.");
//                throw new IllegalArgumentException("root가 이미 존재합니다");
//            }else if (categoryDto.getName().equals(categoryDto.getParent())){
//                throw new IllegalArgumentException("root 가 아닐때 name 과 parent는 같을 수 없습니다.");
//            }

            /** 부모 영속화 */
            Category parentCategory = categoryRepository.findByName(categoryDto.getParent())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리 없음 예외"));

            log.info("parentCategory name : {} code : {}",parentCategory.getName(),parentCategory.getCode());
            sb.append(parentCategory.getCode()).append(categoryDto.getCode()); // 부모 Category

            /** 영속성 전이? */
            currentCategory = Category.createCategory(categoryDto.getName(),sb.toString());
            currentCategory.updateLevel(parentCategory.getLevel()+1);
            log.info("currentCategory name : {} code : {} level : {}", currentCategory.getName() ,currentCategory.getCode(),currentCategory.getLevel());
            currentCategory.connectParent(parentCategory);

        } else { // parent == null
            log.info("부모가 존재하지 않습니다");

            /** branch의 root 있는지 확인
             *  name 이 존재하면 error
             * */
            if (categoryRepository.existsByName(categoryDto.getName())) {
                log.info("잘못된 형식입니다");
                throw new IllegalArgumentException("이미 존재하는 카테고리 입니다");

            }else {
                log.info(" root 를 생성합니다.");
                /** root 생성 */
                currentCategory = Category.root(categoryDto.getName(),categoryDto.getCode());
            }
        }

        return categoryRepository.save(currentCategory);
    }

    public String findCode(String categoryName){
        return categoryRepository.findCodeByName(categoryName).orElseThrow(() -> new CategoryException("존재하지 않는 카테고리 명입니다"));
    }




//
//    @Transactional
//    public void deleteCategory(String branch ,Long categoryId){
//        log.info("delete - currentCategory ");
//        Category currentCategory = categoryRepository.findByBranchAndId(branch , categoryId).orElseThrow(() -> new RuntimeException("삭제 - 카테고리가 존재하지 않습니다."));
//
//        Category parentCategory = null ;
//        if (currentCategory.getParent() == null) {
//            log.info("delete - level 0 root 입니다");
//            //        categoryRepository.delete(currentCategory);
//        }else if(currentCategory.getParent().getName().equals("root")){
//            log.info("delte - level 1 입니다.");
//            parentCategory = categoryRepository.findByBranchAndName(branch, "root").orElseThrow(() -> new RuntimeException("삭제 - 루트 카테고리 존재하지 않습니다."));
//        }else{
//            log.info("delte - level 2 이상 입니다.");
//            parentCategory = categoryRepository.findByBranchAndId(branch , currentCategory.getParent().getId()).orElseThrow(() -> new RuntimeException("카테고리가 존재하지 않습니다."));
////
////            parentCategory.getChildren().remove(currentCategory);
//        }
//
//        /** 부모 - cur 연관 관계 끊고 , cur 삭제 */
//
////        categoryRepository.delete(currentCategory);
//    }
//
//
//    @Transactional
//    public void deleteCategory2(String branch ,Long categoryId){
//        log.info("delete - currentCategory 찾기");
//        Category currentCategory = categoryRepository.findByBranchAndId(branch , categoryId).orElseThrow(() -> new RuntimeException("삭제 - 카테고리가 존재하지 않습니다."));
//
//        Category parentCategory = null ;
//
//        if(!currentCategory.getParent().getName().equals("root")){
//            log.info("delte - root가 아닙니다.");
////            parentCategory = categoryRepository.findById(currentCategory.getParent().getId()).orElseThrow(() -> new RuntimeException("카테고리가 존재하지 않습니다."));
//            parentCategory = categoryRepository.findByBranchAndId(branch , currentCategory.getParent().getId()).orElseThrow(() -> new RuntimeException("카테고리가 존재하지 않습니다."));
//
//            parentCategory.getChildren().remove(currentCategory);
//        }else{
//            log.info("delte - root입니다.");
//            parentCategory = categoryRepository.findByBranchAndName(branch, "root").orElseThrow(() -> new RuntimeException("삭제 - 루트 카테고리 존재하지 않습니다."));
//        }
//
//        /** 부모 - cur 연관 관계 끊고 , cur 삭제 */
//
//        categoryRepository.delete(currentCategory);
//    }
//
///** 오류 있을 시 보류 */
//    @Transactional
//    public Category saveCategory2(CategoryDto categoryDto) {
//
//        Category childCategory = Category.createCategory(categoryDto);
//
//        if (StringUtils.hasText(categoryDto.getParent())) {
//            log.info("부모가 존재합니다");
//
//            Category parentCategory = categoryRepository.findByBranchAndName(categoryDto.getBranch(), categoryDto.getParent())
//                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리 없음 예외"));
//
//            childCategory.updateLevel(parentCategory.getLevel()+1);
//            childCategory.connectParent(parentCategory);
//
//
//        } else { // parent == null
//            log.info("부모가 존재하지 않습니다");
//            if (categoryRepository.existsByBranchAndName(categoryDto.getBranch(), categoryDto.getName())) {
//                throw new RuntimeException("branch와 name이 같을 수 없습니다. ");
//            }
//
//            /** branch의 root 있는지 확인 */
//            if (!categoryRepository.existsByBranchAndName(categoryDto.getBranch(), "root")) {
//                log.info(categoryDto.getBranch()+ " root 를 생성합니다.");
//
//                /** root 생성 */
//                Category rootBranch = Category.root(categoryDto.getBranch());
//                categoryRepository.save(rootBranch);
//
//                childCategory.connectParent(rootBranch);
//                childCategory.updateLevel(1);
//            }
//        }
//
//        return categoryRepository.save(childCategory);
//    }

}
