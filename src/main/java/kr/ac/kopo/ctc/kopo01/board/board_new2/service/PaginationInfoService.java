package kr.ac.kopo.ctc.kopo01.board.board_new2.service;

import kr.ac.kopo.ctc.kopo01.board.board_new2.dto.PaginationInfo;
import org.springframework.stereotype.Service;

/**
 * 간소화된 페이지네이션 서비스
 * 핵심 기능: 블록 단위 페이지 표시 + 이전/다음 블록 이동
 */
@Service
public class PaginationInfoService {

    /**
     * 페이지네이션 정보 계산
     *
     * @param totalItems 전체 아이템 개수
     * @param itemsPerPage 페이지당 아이템 개수
     * @param pagesPerBlock 블록당 페이지 개수 (한 번에 보여줄 페이지 번호 개수)
     * @param currentPage 현재 페이지 번호
     * @return 계산된 페이지네이션 정보
     */
    public PaginationInfo getPaginationInfo(int totalItems, int itemsPerPage, int pagesPerBlock, int currentPage) {
        PaginationInfo paginationInfo = new PaginationInfo();

        /* ========== 기본 계산 ========== */

        // 총 페이지 수: 전체 아이템을 페이지당 아이템 수로 나눈 값 (올림)
        int totalPageCount = (int) Math.ceil((double) totalItems / itemsPerPage);
        if (totalPageCount == 0) totalPageCount = 1; // 최소 1페이지 보장

        // 현재 페이지 범위 검증 (1 ~ 총페이지수)
        if (currentPage < 1) currentPage = 1;
        if (currentPage > totalPageCount) currentPage = totalPageCount;

        /* ========== 블록 계산 ========== */

        // 현재 페이지가 속한 블록 번호 (0부터 시작)
        int currentBlock = (currentPage - 1) / pagesPerBlock;

        // 현재 블록의 시작 페이지
        int startPage = currentBlock * pagesPerBlock + 1;

        // 현재 블록의 끝 페이지 (총 페이지 수를 넘지 않도록)
        int blockEndPage = Math.min(startPage + pagesPerBlock - 1, totalPageCount);

        // endPage: 블록에 페이지가 1개면 -1 (범위 표시 불필요), 여러 개면 끝페이지
        int endPage = (startPage == blockEndPage) ? -1 : blockEndPage;

        /* ========== 네비게이션 계산 ========== */

        // 이전 블록으로 이동 (이전 블록이 있을 때만)
        int prevPage = -1;
        if (currentBlock > 0) {  // 첫 번째 블록이 아닌 경우
            int prevBlockStart = (currentBlock - 1) * pagesPerBlock + 1;
            int prevBlockEnd = Math.min(prevBlockStart + pagesPerBlock - 1, totalPageCount);
            prevPage = prevBlockEnd;  // 이전 블록의 마지막 페이지로 이동
        }

        // 현재 블록의 실제 끝 페이지
        int actualBlockEnd = (endPage == -1) ? startPage : endPage;

        // 다음 블록으로 이동 (다음 블록이 있을 때만)
        int nextPage = (actualBlockEnd < totalPageCount) ? actualBlockEnd + 1 : -1;

        // 전체 마지막 페이지로 이동 (다음 블록이 있을 때만 표시)
        int lastPage = (actualBlockEnd < totalPageCount) ? totalPageCount : -1;

        /* ========== 결과 설정 ========== */
        paginationInfo.setFirstPage(currentBlock > 0 ? 1 : -1);
        paginationInfo.setPrevPage(prevPage);
        paginationInfo.setStartPage(startPage);
        paginationInfo.setCurrentPage(currentPage);
        paginationInfo.setEndPage(endPage);
        paginationInfo.setNextPage(nextPage);
        paginationInfo.setLastPage(lastPage);
        paginationInfo.setTotalPageCount(totalPageCount);

        return paginationInfo;
    }
}