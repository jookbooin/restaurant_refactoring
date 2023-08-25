package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.Menu;
import com.restaurant.reservation.domain.OrderMenu;
import com.restaurant.reservation.domain.booking.Reservation;
import com.restaurant.reservation.repository.dto.OrderMenuDto;
import com.restaurant.reservation.repository.dto.ReservationDto;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.MemberRepository;
import com.restaurant.reservation.repository.MenuRepository;
import com.restaurant.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;
    private final ReservationRepository reservationRepository;

    private final Long limit = 10L;


    @Transactional
    public Reservation addReservation(Long memberId , ReservationDto reservationDto){

        // 엔티티 조회
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("can't find Member"));

        // 주문 상품 리스트 생성
        // 1. 주문 상품 생성 -> OrderMenuDto - MenuId, count 담김
        // 2. 리스트로 만들어야 함
        List<OrderMenu> orderMenuList = new ArrayList<>();

        if(reservationDto.getOrderMenuList() != null)
            for (OrderMenuDto orderMenuDto : reservationDto.getOrderMenuList()) {
                Menu findMenu = menuRepository.findById(orderMenuDto.getMenuId()).orElseThrow(() -> new RuntimeException("can't find Menu"));
                OrderMenu createOrderMenu = OrderMenu.createOrderMenu(findMenu, orderMenuDto.getCount());
                orderMenuList.add(createOrderMenu);
            }

        Reservation createReservation = Reservation.createReservation(member,reservationDto,orderMenuList);
        Reservation reservation = reservationRepository.save(createReservation);
        return reservation;

    }

    @Transactional
    public int deleteReservation(Long rid , Long memberId ) {
        int rowCount = 0;
        Optional<Reservation> reservationOpt = reservationRepository.findReservationByIdAndMember_Id(rid, memberId);

        if(reservationOpt.isPresent()){
            reservationRepository.delete(reservationOpt.get());
            rowCount++;
        }
        return rowCount;
    }

    public List<OrderMenu> findOrderMenuList(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(()->new RuntimeException("not found reservation"));

        if(reservation.getOrderMenus().size()>0)
            return reservation.getOrderMenus();
        return null;
    }

    public List<LocalTime> findPossibleTime(LocalDate date){
        List<Object[]> objectList = reservationRepository.findPossibleTimeByDate(date, limit);
        log.info("objectList : {}",objectList);
        List<LocalTime> collect = null;
        try {
            collect = objectList.stream().map(objects -> (LocalTime) objects[0]).collect(Collectors.toList());
        } catch (ClassCastException e) {
            throw new RuntimeException(e);
        }
        return collect;
    }

    /**
     *  기존 주문메뉴 list  : findReservation.getOrderMenus()
     *  modify:  modifyMenuDtoList
     *
     * 리스트 2개 비교해서  두가지 리스트 추출
     * 1. 삭제될 id를 가진 리스트
     * 2. id가 변하지 않은 리스트
     *    추가될 id를 가진 리스트  추출
     * */

    /**
     * 동작방식
     * A. 1번 리스트 존재하면 -> before 리스트에서 1번 리스트 delete -> modify에 존재하는 id만 남게됨.
     * B. modify 리스트로 -> A 번 이후 리스트 비교
     *                      id 존재할 경우 -> count 변경감지
     *                      id 존재 x -> list에 추가
     * */
    @Transactional
    public int updateReservation (ReservationDto reservationDto , Long sid) {
        log.info("updateReservation");
        Optional<Reservation> reservationOpt = reservationRepository.findReservationByIdAndMember_Id(reservationDto.getRid(),sid);  // join 1번
//        Optional<Reservation> reservationOpt = reservationRepository.fetchReservationByIdMember_Id(reservationDto.getRid(),sid);  // join 1번
        int cnt = 0 ;
        if (reservationOpt.isPresent()) {
            Reservation findReservation = reservationOpt.get();
            log.info("findReservation : {}",findReservation);
            // 1. 컬렉션 이외의 요소 변경감지
            findReservation.updateReservation(reservationDto);

            // 2. collection 수정
            // 2-(1) findReservation.getOrderMenus 리스트에 [ modify리스트안에 존재하지 않는 ] id들로 이루어진 list 추출
            List<OrderMenu> removeList = collectRemoveFromReservation(findReservation,reservationDto.getOrderMenuList());
            for (OrderMenu orderMenu : removeList) {
                log.info("removeOrderMenu : {}",orderMenu);
            }
            // 2-(2) 리스트 삭제
            if(!removeList.isEmpty()){
                deleteRemoveList(findReservation, removeList);
            }
            // 2-(3) modifyList 추가 및 변경감지
            // 기존에 존재하는 것들 변경감지
            List<OrderMenuDto> insertList = collectInsertFromModify(findReservation,reservationDto.getOrderMenuList());
            for (OrderMenuDto orderMenuDto : insertList) {
                log.info("orderMenuDto : {}",orderMenuDto);
            }
            // 2-(4) 메뉴 추가
            insertOrderMenu(findReservation, insertList);
            cnt ++;
        }
        return cnt;
    }

    /** 수정된 orderMenu를 받아서 reservation에 추가 */
    private void insertOrderMenu(Reservation findReservation, List<OrderMenuDto> insertList) {
        insertList.forEach(orderMenuDto -> {
            Optional<Menu> findMenu = menuRepository.findById(orderMenuDto.getMenuId());
            findMenu.ifPresent(menu -> {
                OrderMenu orderMenu = OrderMenu.createOrderMenu(menu, orderMenuDto.getCount());
                findReservation.addOrderMenu(orderMenu);
            });
        });
    }

    /** 수정된 orderMenu 리스트로 부터 reservation에 추가될 리스트 추출
     *  동일한 id가 있을 경우 필드 변경감지도  */
    private List<OrderMenuDto> collectInsertFromModify(Reservation findReservation,List<OrderMenuDto> modifyMenuDtoList) {
        return modifyMenuDtoList.stream()
                .filter(orderMenuDto -> {
                    for (OrderMenu orderMenu : findReservation.getOrderMenus())
                        if (orderMenu.getMenu().getId() == orderMenuDto.getMenuId()) {
                            orderMenu.changeCount(orderMenuDto.getCount());
                            return false;
                        }
                    return true;
                }).collect(Collectors.toList());
    }

    /** */

    
    /** 기존 orderMenus 에서 removeList 제거하는 메소드 */
    private  void deleteRemoveList(Reservation findReservation, List<OrderMenu> removeList) {
        /** stream.anyMatch : 2중 for문 일치하는 것이 있으면 true*/
        findReservation.getOrderMenus()
                .removeIf(orderMenu -> removeList.stream().anyMatch(removeMenu -> removeMenu.getMenu().getId() == orderMenu.getMenu().getId())
                );

//        .removeIf(orderMenu -> {
//            for (OrderMenu removeMenu : removeList) {
//                if (removeMenu.getMenu().getId() == orderMenu.getMenu().getId())
//                    return true;
//            }
//            return false;
//        });
    }

    /** 제거될 리스트 뽑아내는 메서드 */
    private  List<OrderMenu> collectRemoveFromReservation(Reservation findReservation,List<OrderMenuDto> modifyMenuDtoList ) {

        /** stream.nonMatch(): 2중 for문 일치하는 것이 없으면 true*/
        return findReservation.getOrderMenus().stream()
                .filter(orderMenu -> modifyMenuDtoList.stream().noneMatch(orderMenuDto -> orderMenuDto.getMenuId() == orderMenu.getMenu().getId()))
                .collect(Collectors.toList());

//        .filter(orderMenu ->{
//            for (OrderMenuDto orderMenuDto : modifyMenuDtoList) {
//                if(orderMenuDto.getMenuId() == orderMenu.getMenu().getId())
//                    return false;
//            }
//            return true;})
//                .collect(Collectors.toList());
    }







    /**==============================================================================================================*/
    /** 1. 전체 리스트 삭제
     *  이전 orderMenuList : N 만큼 무조건 삭제
     *  modifyList : M      M 만큼 menu  select
     *                      M 만큼 orderMenu insert
     *
     *                      항상 총 쿼리 개수 = N + 2M
     * --> 만약 리스트 변경사항이 없다면???? \(￣︶￣*\))
     *
     * */
    @Transactional
    public void updateReservation_allList(Long id , ReservationDto reservationDto ,List<OrderMenuDto> modifyMenuDtoList){
        /** 리스트는 어떻게 update 하는 것이지? */
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);

        if (reservationOpt.isPresent()){
            Reservation findReservation = reservationOpt.get();
            // 1. 컬렉션 이외의 요소 변경감지
            findReservation.updateReservation(reservationDto);

            // 2. 이전 기록 모두 지운다.
            findReservation.getOrderMenus().clear();
            // 3. 새롭게 추가
            for (OrderMenuDto orderMenuDto : modifyMenuDtoList) {
                Optional<Menu> menuOps = menuRepository.findById(orderMenuDto.getMenuId());
                if (menuOps.isPresent()) {
                    OrderMenu modifyOrderMenu = OrderMenu.createOrderMenu(menuOps.get(), orderMenuDto.getCount());
                    findReservation.addOrderMenu(modifyOrderMenu);
                }
            }
        }
    }


}
