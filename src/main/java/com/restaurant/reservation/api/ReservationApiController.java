package com.restaurant.reservation.api;

import com.restaurant.reservation.domain.enumType.TimeEnum;
import com.restaurant.reservation.service.ReservationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReservationApiController {

    private final ReservationService reservationService;
    @GetMapping("/api/reservation/{date}/time")
    public ResponseEntity<OneListResult> findPossibleTime(@PathVariable("date") String date){
        log.info("date : {}",date);
        List<LocalTime> possibleTimeList = null;
        LocalDate localDate = LocalDate.parse(date);

        try {
            possibleTimeList = reservationService.findPossibleTime(localDate);
        } catch (Exception e) {
            List<String> original = Arrays.stream(TimeEnum.values()).map(o -> o.getTime()).collect(Collectors.toList());
            return new ResponseEntity<>(new OneListResult(original),HttpStatus.BAD_REQUEST);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a h:mm");
        List<String> timeToString = possibleTimeList.stream().map(time -> time.format(formatter))
                .collect(Collectors.toList());

        List<PossibleTime> result = Arrays.stream(TimeEnum.values())
                .map(timeEnum -> {
                    String time = timeEnum.getTime();
                    int check = timeToString.contains(time) ? 1 : 0;
                    return new PossibleTime(time, check);
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(new OneListResult(result), HttpStatus.OK);
    }
    @Data
    @AllArgsConstructor
    class PossibleTime{
        String time;
        Integer check;

    }


    @Data
    @AllArgsConstructor
    static class OneListResult<T> {
        private T data;

    }


}
