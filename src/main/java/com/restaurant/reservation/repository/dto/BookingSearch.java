package com.restaurant.reservation.repository.dto;

import com.restaurant.reservation.api.request.search.BookingSearchRequest;
import com.restaurant.reservation.web.webDto.BookingSearchWeb;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class BookingSearch {

    private LocalDate startDate; // 1.
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String searchType;  // 이름 : name
    private String keyword; //  %고객%

    public BookingSearch() { }

    public static BookingSearch TransformWeb(BookingSearchWeb bookingSearchWeb){
        BookingSearch bookingSearch = new BookingSearch();
        bookingSearch.setStartDate(bookingSearchWeb.getStartDate());
        bookingSearch.setEndDate(bookingSearchWeb.getEndDate());
        bookingSearch.setSearchType(bookingSearchWeb.getSearchType());
        bookingSearch.setKeyword(bookingSearchWeb.getKeyword());
        return bookingSearch;
    }
    public static BookingSearch TransformApi(BookingSearchRequest bookingSearchRequest){
        BookingSearch bookingSearch = new BookingSearch();

        if(bookingSearchRequest.getStartDate() != null)
            bookingSearch.setStartDate(LocalDate.parse(bookingSearchRequest.getStartDate().trim()));

        if(bookingSearchRequest.getEndDate()!=null)
            bookingSearch.setEndDate(LocalDate.parse(bookingSearchRequest.getEndDate().trim()));
        bookingSearch.setSearchType(bookingSearchRequest.getSearchType());
        bookingSearch.setKeyword(bookingSearchRequest.getKeyword());

        return bookingSearch;
    }

}
