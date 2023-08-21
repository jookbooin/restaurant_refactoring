package com.restaurant.reservation.staticClass;

import com.restaurant.reservation.domain.enumType.MemberGrade;
import com.restaurant.reservation.domain.enumType.TimeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@Transactional
class methodTest {

   @Test
   public void 날짜_테스트() throws Exception{

       LocalTime time = LocalTime.of(13, 30, 45);

       DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
       String formattedTime1 = time.format(formatter1);
       System.out.println(formattedTime1); // 출력: 13:30:45

       DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("a h:mm");
       String formattedTime2 = time.format(formatter2);
       System.out.println(formattedTime2); // 출력: 1:30 PM

       DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("HH:mm");
       String formattedTime3 = time.format(formatter3);
       System.out.println(formattedTime3); // 출력: 13:30

       System.out.println();

       LocalTime time1 = LocalTime.of(9, 30, 45);

       DateTimeFormatter amFormatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
       String amformattedTime1 = time1.format(amFormatter1);
       System.out.println(amformattedTime1); // 출력: 09:30:45

       DateTimeFormatter amFormatter2 = DateTimeFormatter.ofPattern("a h:mm");
       String amformattedTime2 = time1.format(amFormatter2);
       System.out.println(amformattedTime2); // 출력: 오전 9:30


       DateTimeFormatter amFormatter3 = DateTimeFormatter.ofPattern("HH:mm");
       String amformattedTime3 = time1.format(amFormatter3);
       System.out.println(amformattedTime3); // 출력: 09:30


       String timeString = "오전 9:30";
       DateTimeFormatter aToTime = DateTimeFormatter.ofPattern("a h:mm");
       LocalTime atime = LocalTime.parse(timeString, aToTime);
       System.out.println(atime);  // 09:30


       System.out.println();
       /** enum static method */
       String timeString2 = "오후 9:30";
       LocalTime localTime1 = TimeEnum.transferStringToTime(timeString2);
       System.out.println("localTime1 = " + localTime1);
       String localTime1ToString = TimeEnum.transferTimeToString(localTime1);
       System.out.println("localTime1ToString = " + localTime1ToString);

       System.out.println();

       LocalTime localTime = LocalTime.of(17, 30, 45);
       String stringTime1 = TimeEnum.transferTimeToString(localTime);
       System.out.println("stringTime1 = " + stringTime1);
       LocalTime stringTime1ToLocalTime = TimeEnum.transferStringToTime(stringTime1);
       System.out.println("stringTime1ToLocalTime = " + stringTime1ToLocalTime);


   }

   @Test
   public void enum_Test() throws Exception{
       MemberGrade gold = MemberGrade.GOLD;
       System.out.println("gold = " + gold);
       System.out.println("gold.getClass() = " + gold.getClass());
       System.out.println("gold.getName() = " + gold.getName());
       System.out.println("gold.getName().getClass() = " + gold.getName().getClass());

       
       try {
           MemberGrade gold1 = MemberGrade.valueOf("GOLD");
           System.out.println("gold1 = " + gold1);
           System.out.println("gold1.getClass() = " + gold1.getClass());
           System.out.println("   gold1 통과");
           MemberGrade gold2 = MemberGrade.valueOf("골드");
           System.out.println("   gold2 통과");
       } catch (IllegalArgumentException e) {
           System.out.println("IllegalArgumentException 발생");
       }

   }
}