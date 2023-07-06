package com.restaurant.reservation.Repository;

import com.restaurant.reservation.domain.members.Admins;
import com.restaurant.reservation.domain.members.Customer;
import com.restaurant.reservation.domain.members.MemberInfo;
import com.restaurant.reservation.Repository.entityManagerRepo.MemberRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
class MemberRepoTest {

    @Autowired
    private MemberRepo memberRepo;
    @Test
    @Rollback(false)
    public void memberTest() throws Exception{
//
        Customer customer = new Customer();
        customer.setEmail("11111");
        customer.setMemberInfo(new MemberInfo("이승헌","하이헤로우"));
        customer.setCreatedDate(LocalDateTime.now());
        Long cid =memberRepo.save(customer);
        Assertions.assertEquals(cid,1);

        Admins admin = new Admins();
        admin.setEmail("2222");
        admin.setCreatedDate(LocalDateTime.now());
        Long aid = memberRepo.save(admin);
        Assertions.assertEquals(aid,2);
    }

}