package com.restaurant.reservation.repository.dto;

import com.restaurant.reservation.api.dto.searchApi.BookingSearchApi;
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
    public static BookingSearch TransformApi(BookingSearchApi bookingSearchApi){
        BookingSearch bookingSearch = new BookingSearch();

        if(bookingSearchApi.getStartDate() != null)
            bookingSearch.setStartDate(LocalDate.parse(bookingSearchApi.getStartDate().trim()));

        if(bookingSearchApi.getEndDate()!=null)
            bookingSearch.setEndDate(LocalDate.parse(bookingSearchApi.getEndDate().trim()));
        bookingSearch.setSearchType(bookingSearchApi.getSearchType());
        bookingSearch.setKeyword(bookingSearchApi.getKeyword());

        return bookingSearch;
    }

}
