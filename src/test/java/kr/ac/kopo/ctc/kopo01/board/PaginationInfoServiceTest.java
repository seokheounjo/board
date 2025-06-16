package kr.ac.kopo.ctc.kopo01.board;

import kr.ac.kopo.ctc.kopo01.board.dto.PaginationInfo;
import kr.ac.kopo.ctc.kopo01.board.service.PaginationInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class PaginationInfoServiceTest {

    @Autowired
    PaginationInfoService paginationInfoService;

    void check(PaginationInfo paginationInfo, int firstPage, int prevPage, int startPage, int currentPage, int endPage, int nextPage, int lastPage, int totalPageCount){
        assertEquals(paginationInfo.getFirstPage(), firstPage);
        assertEquals(paginationInfo.getPrevPage(), prevPage);
        assertEquals(paginationInfo.getStartPage(), startPage);
        assertEquals(paginationInfo.getCurrentPage(), currentPage);
        assertEquals(paginationInfo.getEndPage(), endPage);
        assertEquals(paginationInfo.getNextPage(), nextPage);
        assertEquals(paginationInfo.getLastPage(), lastPage);
        assertEquals(paginationInfo.getTotalPageCount(), totalPageCount);
    }



    @Test
    void getPaginationInfo01() {
        PaginationInfo paginationInfo = paginationInfoService.getPaginationInfo(1, 5, 1, 1);
        check(paginationInfo, -1, -1, 1, 1, -1, -1, -1, 1);
        System.out.println(paginationInfo);
    }

    @Test
    void getPaginationInfo02() {
        PaginationInfo paginationInfo = paginationInfoService.getPaginationInfo(1, 5, 10, 5);
        check(paginationInfo, -1, -1, 1, 1, -1, -1, -1, 1);
    }

    @Test
    void getPaginationInfo03() {
        // 총 50개, 10개/페이지 (5페이지), 5개/블록 - 3페이지
        PaginationInfo paginationInfo = paginationInfoService.getPaginationInfo(50, 10, 5, 3);
        check(paginationInfo, -1, -1, 1, 3, 5, -1, -1, 5);
        System.out.println(paginationInfo);
    }

    @Test
    void getPaginationInfo04() {
        // 총 50개, 10개/페이지 (5페이지), 5개/블록 - 5페이지
        PaginationInfo paginationInfo = paginationInfoService.getPaginationInfo(50, 10, 5, 5);
        check(paginationInfo, -1, -1, 1, 5, 5, -1, -1, 5);
        System.out.println(paginationInfo);
    }

    @Test
    void getPaginationInfo05() {
        // 총 100개, 10개/페이지 (10페이지), 5개/블록 - 5페이지
        PaginationInfo paginationInfo = paginationInfoService.getPaginationInfo(100, 10, 5, 5);
        check(paginationInfo, -1, -1, 1, 5, 5, 6, 10, 10);
        System.out.println(paginationInfo);
    }

    @Test
    void getPaginationInfo06() {
        // 총 100개, 10개/페이지 (10페이지), 5개/블록 - 6페이지
        PaginationInfo paginationInfo = paginationInfoService.getPaginationInfo(100, 10, 5, 6);
        check(paginationInfo, 1, 5, 6, 6, 10, -1, -1, 10);
        System.out.println(paginationInfo);
    }

    @Test
    void getPaginationInfo07() {
        // 총 95개, 10개/페이지 (10페이지), 5개/블록 - 9페이지
        PaginationInfo paginationInfo = paginationInfoService.getPaginationInfo(95, 10, 5, 9);
        check(paginationInfo, 1, 5, 6, 9, 10, -1, -1, 10);
        System.out.println(paginationInfo);
    }

    @Test
    void getPaginationInfo08() {
        // 총 95개, 10개/페이지 (10페이지), 5개/블록 - 10페이지
        PaginationInfo paginationInfo = paginationInfoService.getPaginationInfo(95, 10, 5, 10);
        check(paginationInfo, 1, 5, 6, 10, 10, -1, -1, 10);
        System.out.println(paginationInfo);
    }

    @Test
    void getPaginationInfo09() {
        // 총 10개, 1개/페이지 (10페이지), 3개/블록 - 10페이지
        PaginationInfo paginationInfo = paginationInfoService.getPaginationInfo(10, 1, 3, 10);
        check(paginationInfo, 1, 9, 10, 10, -1, -1, -1, 10);
        System.out.println(paginationInfo);
    }

    @Test
    void getPaginationInfo10() {
        // 총 7개, 2개/페이지 (4페이지), 2개/블록 - 1페이지
        PaginationInfo paginationInfo = paginationInfoService.getPaginationInfo(7, 2, 2, 1);
        check(paginationInfo, -1, -1, 1, 1, 2, 3, 4, 4);
        System.out.println(paginationInfo);
    }

    @Test
    void getPaginationInfo11() {
        // 총 7개, 2개/페이지 (4페이지), 2개/블록 - 2페이지
        PaginationInfo paginationInfo = paginationInfoService.getPaginationInfo(7, 2, 2, 2);
        check(paginationInfo, -1, -1, 1, 2, 2, 3, 4, 4);
        System.out.println(paginationInfo);
    }

    @Test
    void getPaginationInfo12() {
        // 총 7개, 2개/페이지 (4페이지), 2개/블록 - 3페이지
        PaginationInfo paginationInfo = paginationInfoService.getPaginationInfo(7, 2, 2, 3);
        check(paginationInfo, 1, 2, 3, 3, 4, -1, -1, 4);
        System.out.println(paginationInfo);
    }

    @Test
    void getPaginationInfo13() {

        // 총 7개, 2개/페이지 (4페이지), 2개/블록 - 4페이지
        PaginationInfo paginationInfo = paginationInfoService.getPaginationInfo(7, 2, 2, 4);
        check(paginationInfo, 1, 2, 3, 4, 4, -1, -1, 4);
        System.out.println(paginationInfo);
    }
    @Test
    void getPaginationInfo14() {


        // 총 1,025개, 10개/페이지(103페이지), 10개/블록 – 현재 25페이지
        PaginationInfo p = paginationInfoService.getPaginationInfo(1025, 10, 10, 25);
        check(p,
                1,    // firstPage  : 처음으로 (세 번째 블록이므로 필요)
                20,   // prevPage   : 이전 블록 마지막 (11-20 블록의 마지막)
                21,   // startPage  : 현 블록 시작 (21-30 블록)
                25,   // currentPage: 현재 페이지
                30,   // endPage    : 현 블록 끝
                31,   // nextPage   : 다음 블록 첫 페이지
                103,  // lastPage   : 전체 마지막 (더 많은 블록이 있으므로)
                103); // totalPagecount: 총 페이지 수
        System.out.println(p);
    }

}
