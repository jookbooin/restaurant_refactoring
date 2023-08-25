package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.Category;
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

        Category currentCategory = null;
//                Category.createCategory(categoryDto);

        if (StringUtils.hasText(categoryDto.getParent())) {
            log.info("부모가 존재합니다");

            //  level 0 : (음식, 'root' ) -> level 1 : ( 음식 , '음식' ) 불가능
            if (categoryDto.getBranch().equals( categoryDto.getName())) {
                throw new RuntimeException("branch와 name이 같을 수 없습니다. ");
            }

            /** 부모 영속화 */
            Category parentCategory = categoryRepository.findByBranchAndName(categoryDto.getBranch(), categoryDto.getParent())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리 없음 예외"));

            /** 영속성 전이? */
            currentCategory = Category.createCategory(categoryDto);
            currentCategory.updateLevel(parentCategory.getLevel()+1);
            currentCategory.connectParent(parentCategory);

        } else { // parent == null
            log.info("부모가 존재하지 않습니다");

            /** branch의 root 있는지 확인 */
            if (!categoryRepository.existsByBranchAndName(categoryDto.getBranch(), "root")) {
                log.info(categoryDto.getBranch()+ " root 를 생성합니다.");

                /** root 생성 */
                currentCategory = Category.root(categoryDto.getBranch());
            }
        }

        return categoryRepository.save(currentCategory);
    }

    @Transactional
    public void deleteCategory(Long categoryId){

        Category currentCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("카테고리가 존재하지 않습니다."));

        // c
        if(!currentCategory.getParent().getName().equals("root")){
            Category parentCategory = categoryRepository.findById(currentCategory.getParent().getId()).orElseThrow(() -> new RuntimeException("카테고리가 존재하지 않습니다."));
            parentCategory.getChildren().remove(currentCategory);
        }
        
    }

/** 오류 있을 시 보류 */
    @Transactional
    public Category saveCategory2(CategoryDto categoryDto) {

        Category childCategory = Category.createCategory(categoryDto);

        if (StringUtils.hasText(categoryDto.getParent())) {
            log.info("부모가 존재합니다");

            Category parentCategory = categoryRepository.findByBranchAndName(categoryDto.getBranch(), categoryDto.getParent())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리 없음 예외"));

            childCategory.updateLevel(parentCategory.getLevel()+1);
            childCategory.connectParent(parentCategory);


        } else { // parent == null
            log.info("부모가 존재하지 않습니다");
            if (categoryRepository.existsByBranchAndName(categoryDto.getBranch(), categoryDto.getName())) {
                throw new RuntimeException("branch와 name이 같을 수 없습니다. ");
            }

            /** branch의 root 있는지 확인 */
            if (!categoryRepository.existsByBranchAndName(categoryDto.getBranch(), "root")) {
                log.info(categoryDto.getBranch()+ " root 를 생성합니다.");

                /** root 생성 */
                Category rootBranch = Category.root(categoryDto.getBranch());
                categoryRepository.save(rootBranch);

                childCategory.connectParent(rootBranch);
                childCategory.updateLevel(1);
            }
        }

        return categoryRepository.save(childCategory);
    }

}
