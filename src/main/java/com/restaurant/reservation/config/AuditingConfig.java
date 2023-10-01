package com.restaurant.reservation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/** @WebMvcTest
 *  진행했을때  JPA metamodel must not be empty! 라는 문구가 떴었다.
 *  WebMvcTest <-> Jpa
 *
 *  해결방법으로는
 *  1. @WebMvcTest  + @MockBean(JpaMetamodelMappingContext.class) 함꼐 써주는 방법
 *  2. ReservationApplication 에 있던 @EnableJpaAuditing 을 분리하여
 *     AuditingConfig 파일을 새로 만들어주는 방법이 있다.
 *
 * */
@Configuration
@EnableJpaAuditing
public class AuditingConfig {
}
