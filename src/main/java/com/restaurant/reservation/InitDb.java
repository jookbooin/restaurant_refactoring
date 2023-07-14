package com.restaurant.reservation;

import com.restaurant.reservation.domain.dto.MemberDto;
import com.restaurant.reservation.domain.dto.MenuDto;
import com.restaurant.reservation.domain.Menu;
import com.restaurant.reservation.domain.Tables;
import com.restaurant.reservation.domain.enumType.MenuType;
import com.restaurant.reservation.domain.enumType.TableType;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.MemberRepository;
import com.restaurant.reservation.repository.MenuRepository;
import com.restaurant.reservation.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct // bean에 올라오면 spring이 불러오는 것 : 초기화
    public void init() {
        initService.MemberInit1();
        initService.TableInit();
        initService.MenuInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final MemberRepository memberRepository;
        private final TableRepository tableRepository;
        private final MenuRepository menuRepository;

        public void MemberInit1() {
            MemberDto memberDto1 =MemberDto.builder()
                    .email("3670lsh@naver.com")
                    .password("dltmdgjs4139!")
                    .name("고객3670")
                    .phoneNumber("01071974139")
                    .build();
            Member member1= Member.createCustomer(memberDto1);
            Member insertMember1 = memberRepository.save(member1);

            MemberDto memberDto2=MemberDto.builder()
                    .email("3670lsh@gmail.com")
                    .password("dltmdgjs4139!")
                    .name("관리자3670")
                    .phoneNumber("01041397197")
                    .build();
            Member member2= Member.createAdmin(memberDto2);
            Member insertMember2 = memberRepository.save(member2);
        }

        public void TableInit(){
            Tables table1 =Tables.createTables(1,4, TableType.BAR);
            Tables table2 =Tables.createTables(2,4, TableType.HOLE);
            Tables table3 =Tables.createTables(3,8, TableType.ROOM);
            Tables table4 =Tables.createTables(4,4, TableType.TERRACE);

            Tables bar = tableRepository.save(table1);
            Tables hole = tableRepository.save(table2);
            Tables room = tableRepository.save(table3);
            Tables terrace = tableRepository.save(table4);
        }

        public void MenuInit(){

            MenuDto menuDto1 = new MenuDto();
            menuDto1.setName("코스A");
            menuDto1.setPrice(80000);
            menuDto1.setIntro("스테이크2 + 샐러드1 + 사이드 1");
            menuDto1.setMenuType(MenuType.SPECIAL);
            Menu special1 = Menu.createMenu(menuDto1);

            MenuDto menuDto2 = new MenuDto();
            menuDto2.setName("코스B");
            menuDto2.setPrice(95000);
            menuDto2.setIntro("해산물2 + 사이드 2");
            menuDto2.setMenuType(MenuType.SPECIAL);
            Menu special2 = Menu.createMenu(menuDto2);

            MenuDto menuDto3 = new MenuDto();
            menuDto3.setName("코스C");
            menuDto3.setPrice(110000);
            menuDto3.setIntro("스테이크2 + 버거2 + 사이드 1 + 음료4");
            menuDto3.setMenuType(MenuType.SPECIAL);
            Menu special3 = Menu.createMenu(menuDto3);

            MenuDto menuDto4 = new MenuDto();
            menuDto4.setName("스테이크");
            menuDto4.setPrice(25000);
            menuDto4.setIntro("스테이크 입니다");
            menuDto4.setMenuType(MenuType.GENERAL);
            Menu general1 = Menu.createMenu(menuDto4);

            MenuDto menuDto5 = new MenuDto();
            menuDto5.setName("버거");
            menuDto5.setPrice(16000);
            menuDto5.setIntro("수제버거 입니다");
            menuDto5.setMenuType(MenuType.GENERAL);
            Menu general2 = Menu.createMenu(menuDto5);

            Menu specialA = menuRepository.save(special1);
            Menu specialB = menuRepository.save(special2);
            Menu specialC = menuRepository.save(special3);
            Menu generalA = menuRepository.save(general1);
            Menu generalB = menuRepository.save(general2);
        }
    }
}
