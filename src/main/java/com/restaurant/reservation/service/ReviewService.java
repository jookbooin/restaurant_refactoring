package com.restaurant.reservation.service;

import com.restaurant.reservation.common.Pagination;
import com.restaurant.reservation.common.TwoTypeData;
import com.restaurant.reservation.common.exception.domain.MemberException;
import com.restaurant.reservation.common.exception.domain.RestaurantException;
import com.restaurant.reservation.common.exception.domain.ReviewException;
import com.restaurant.reservation.domain.Restaurant;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.domain.review.Review;
import com.restaurant.reservation.domain.review.UploadFile;
import com.restaurant.reservation.repository.MemberRepository;
import com.restaurant.reservation.repository.RestaurantRepository;
import com.restaurant.reservation.repository.ReviewRepository;
import com.restaurant.reservation.repository.UploadFileRepository;
import com.restaurant.reservation.repository.dto.ReviewDto;
import com.restaurant.reservation.repository.dto.ReviewSearch;
import com.restaurant.reservation.repository.dto.ReviewSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;

    private final UploadFileRepository uploadFileRepository;
    private final FileHandler fileHandler;


    /**
     * 23_10_02
     * 1. jpa의 Page객체를 Service쪽에 숨기는게 더 나을지도 모르겠다고 생각이들었다.
     * 2. content 와 Pagination을 감싸 Controller로 반환한다.
     * */
    public TwoTypeData<List<ReviewSearchDto>, Pagination<ReviewSearchDto>> reviewSearch(Long rid, ReviewSearch reviewSearch , Pageable pageable){

        Page<ReviewSearchDto> reviewSearchPage = reviewRepository.findAllRestaurantReview(rid,reviewSearch, pageable);

        Pagination<ReviewSearchDto> pagination = new Pagination<>(reviewSearchPage);

        return new TwoTypeData<>(reviewSearchPage.getContent(),pagination);
    }
    @Transactional
    public Long save(ReviewDto reviewDto) throws IOException {
        log.info("리뷰 등록");
        Restaurant restaurant = restaurantRepository.findById(reviewDto.getRestaurantId()).orElseThrow(() -> new RestaurantException("레스토랑이 존재하지 않습니다"));
        Member member = memberRepository.findById(reviewDto.getMemberId()).orElseThrow(() -> new MemberException("해당 회원이 존재하지않습니다"));

        Review review = Review.saveOf(restaurant, member, reviewDto);

        /** multipart -> UploadFile (image) 로 변환
         *  review.fileList 에 저장
         * */
        List<UploadFile> uploadFileList = fileHandler.storeFiles(reviewDto.getMultipartFileList());
        review.storeFiles(uploadFileList);

        Review saveReview = reviewRepository.save(review);
        uploadFileList.forEach(uploadFileRepository::save);  // cascade

        return saveReview.getId();
    }

    @Transactional
    public void delete(Long mid ,Long rtid,Long rwid){

        Review findReview = reviewRepository.findFechMember_Restaurant(rwid).orElseThrow(() -> new ReviewException("해당 id의 리뷰가 존재하지 않습니다"));

        /** 1. review - 작성자 확인 */
        if(findReview.getMember().getId().equals(mid)){
            log.info("해당 리뷰 - 작성자가 맞음");

            /** 2. review(N) - restaurant(1) 확인 */
            if(findReview.getRestaurant().getId().equals(rtid)){
                log.info("해당 리뷰 - 레스토랑 맞음");
                findReview.getRestaurant().deleteReview(findReview);
                reviewRepository.delete(findReview);
            }else{
                throw new RestaurantException("해당 레스토랑의 리뷰가 아닙니다.");
            }

        }else{
            throw new ReviewException("해당 작성자가 쓴 리뷰가 아닙니다");
        }
    }

    @Transactional
    public void update(ReviewDto reviewDto){

        Review findReview = reviewRepository.findFechMember_Restaurant(reviewDto.getId()).orElseThrow(() -> new ReviewException("해당 id의 리뷰가 존재하지 않습니다"));

        if(findReview.getMember().getId().equals(reviewDto.getMemberId())) {
            log.info("해당 리뷰 - 작성자가 맞음");
            if(findReview.getRestaurant().getId().equals(reviewDto.getRestaurantId())){
                log.info("해당 리뷰 - 레스토랑 맞음");
                findReview.updateReview(reviewDto);
            }else{
                throw new RestaurantException("해당 레스토랑의 리뷰가 아닙니다.");
            }
        }else{
            throw new ReviewException("해당 작성자가 쓴 리뷰가 아닙니다");
        }
    }

}
