package com.restaurant.reservation.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
public class Pagination<T> {

    private int totalElements; // 전체 요소 개수
    private int totalPages;   // 전체 페이지
    private int pageSize; // pageSize
    private int naviSize; //
    private int page; // 현재 page
    private int startPage; // 네비게이션 시작 페이지
    private int endPage; // 네비게이션 마지막 페이지
    private boolean first; // 이전 페이지로 이동하는 링크 보여줄 것인지
    private boolean last;  // 다음 페이지로 이동하는 링크 보여줄 것인지


    public Pagination(int totalElements , int page , int pageSize){
        this.naviSize = 10;
        this.totalElements = totalElements;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = (int)Math.ceil(totalElements/(double)pageSize);
        this.startPage = (page/naviSize) * naviSize +1;
        this.endPage = Math.min(startPage +naviSize -1,totalPages);
        this.first = startPage != 1;
        this.last =  endPage != totalPages;
    }

    public Pagination(Page<T> page){
        this.naviSize=10;
        this.totalElements = (int) page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.pageSize = page.getSize();
        this.naviSize = naviSize;
        this.page = page.getNumber();
        this.startPage = (this.page/naviSize) * naviSize +1;
        this.endPage = Math.min(startPage +naviSize -1,totalPages);
        this.first = page.isFirst(); // startPage != 1
        this.last = page.isLast(); // endPage != totalPages
    }
}
