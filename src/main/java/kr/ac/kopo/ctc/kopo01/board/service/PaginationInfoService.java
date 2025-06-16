package kr.ac.kopo.ctc.kopo01.board.service;

import kr.ac.kopo.ctc.kopo01.board.dto.PaginationInfo;
import org.springframework.stereotype.Service;

/**
 * 간소화된 페이지네이션 서비스
 * 핵심 기능: 블록 단위 페이지 표시 + 이전/다음 블록 이동
 *
 * 페이지네이션 구조 예시:
 * [이전] [1] [2] [3] [4] [5] [다음] [마지막]
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
        // 예: 95개 아이템 ÷ 10개/페이지 = 9.5 → 10페이지
        int totalPageCount = (int) Math.ceil((double) totalItems / itemsPerPage);
        if (totalPageCount == 0) totalPageCount = 1; // 최소 1페이지 보장

        // 현재 페이지 범위 검증 (1 ~ 총페이지수)
        if (currentPage < 1) currentPage = 1;
        if (currentPage > totalPageCount) currentPage = totalPageCount;

        /* ========== 블록 계산 ========== */

        // 현재 페이지가 속한 블록 번호 (0부터 시작)
        // 예: 6페이지, 5개/블록 → (6-1) ÷ 5 = 1블록 (두 번째 블록)
        int currentBlock = (currentPage - 1) / pagesPerBlock;

        // 현재 블록의 시작 페이지
        // 블록 0: 0×5+1 = 1페이지 시작 (1,2,3,4,5)
        // 블록 1: 1×5+1 = 6페이지 시작 (6,7,8,9,10)
        // 블록 2: 2×5+1 = 11페이지 시작 (11,12,13,14,15)
        int startPage = currentBlock * pagesPerBlock + 1;

        // 현재 블록의 끝 페이지 (총 페이지 수를 넘지 않도록)
        // 예: 6~10페이지 블록이지만 총 8페이지면 8페이지까지만
        int blockEndPage = Math.min(startPage + pagesPerBlock - 1, totalPageCount);

        // endPage: 블록에 페이지가 1개면 -1 (범위 표시 불필요), 여러 개면 끝페이지
        // UI에서 "6-10" 형태 표시할 때 단일 페이지면 범위 표시 안 함
        int endPage = (startPage == blockEndPage) ? -1 : blockEndPage;

        /* ========== 네비게이션 계산 ========== */

        // 이전 블록으로 이동 (이전 블록이 있을 때만)
        int prevPage = -1;
        if (currentBlock > 0) {  // 첫 번째 블록이 아닌 경우
            // 이전 블록의 시작과 끝 계산
            int prevBlockStart = (currentBlock - 1) * pagesPerBlock + 1;
            int prevBlockEnd = Math.min(prevBlockStart + pagesPerBlock - 1, totalPageCount);
            prevPage = prevBlockEnd;  // 이전 블록의 마지막 페이지로 이동
        }

        // 현재 블록의 실제 끝 페이지 (endPage가 -1이면 startPage 사용)
        int actualBlockEnd = (endPage == -1) ? startPage : endPage;

        // 다음 블록으로 이동 (다음 블록이 있을 때만)
        int nextPage = (actualBlockEnd < totalPageCount) ? actualBlockEnd + 1 : -1;

        // 전체 마지막 페이지로 이동 (다음 블록이 있을 때만 표시)
        // 더 많은 페이지가 있음을 사용자에게 알려주는 역할
        int lastPage = (actualBlockEnd < totalPageCount) ? totalPageCount : -1;

        /* ========== 결과 설정 ========== */
        if (true) {
            throw new RuntimeException("예외 발생");
        }
        // 계산된 값들을 PaginationInfo 객체에 설정
        paginationInfo.setFirstPage(currentBlock > 0 ? 1 : -1);              // 첫페이지 이동: 사용 안 함 (불필요)
        paginationInfo.setPrevPage(prevPage);         // 이전 블록 이동 (-1: 없음)
        paginationInfo.setStartPage(startPage);       // 현재 블록 시작 페이지
        paginationInfo.setCurrentPage(currentPage);   // 현재 페이지 (강조 표시용)
        paginationInfo.setEndPage(endPage);           // 현재 블록 끝 페이지 (-1: 단일 페이지)
        paginationInfo.setNextPage(nextPage);         // 다음 블록 이동 (-1: 없음)
        paginationInfo.setLastPage(lastPage);         // 마지막 페이지 이동 (-1: 불필요)
        paginationInfo.setTotalPageCount(totalPageCount); // 총 페이지 수

        return paginationInfo;
    }
}

/*
 * 블록 계산 원리 상세 설명:
 *
 * 예: 총 15페이지, 5개/블록인 경우
 *
 * 블록 0 (첫 번째): [1] [2] [3] [4] [5]    ← 0×5+1 = 1부터 시작
 * 블록 1 (두 번째): [6] [7] [8] [9] [10]   ← 1×5+1 = 6부터 시작
 * 블록 2 (세 번째): [11] [12] [13] [14] [15] ← 2×5+1 = 11부터 시작
 *
 * 공식: 블록번호 × 블록크기 + 1 = 시작페이지
 *
 * 페이지 → 블록 번호 계산:
 * - 3페이지: (3-1) ÷ 5 = 0.4 → 0블록
 * - 7페이지: (7-1) ÷ 5 = 1.2 → 1블록
 * - 12페이지: (12-1) ÷ 5 = 2.2 → 2블록
 *
 * 블록 번호 → 시작 페이지 계산:
 * - 0블록: 0 × 5 + 1 = 1페이지
 * - 1블록: 1 × 5 + 1 = 6페이지
 * - 2블록: 2 × 5 + 1 = 11페이지
 *
 * 실제 사용 예시:
 *
 * 예시 1: getPaginationInfo(50, 10, 5, 3)
 * - 총 50개, 10개/페이지 → 5페이지, 현재 3페이지
 * - 결과: [1] [2] [3] [4] [5] (모든 페이지가 한 블록)
 * - prevPage=-1, startPage=1, endPage=5, nextPage=-1, lastPage=-1
 *
 * 예시 2: getPaginationInfo(100, 10, 5, 6)
 * - 총 100개, 10개/페이지 → 10페이지, 현재 6페이지 (두 번째 블록)
 * - 결과: [이전:5] [6] [7] [8] [9] [10] [마지막:10]
 * - prevPage=5, startPage=6, endPage=10, nextPage=-1, lastPage=-1
 *
 * 예시 3: getPaginationInfo(100, 10, 5, 3)
 * - 총 100개, 10개/페이지 → 10페이지, 현재 3페이지 (첫 번째 블록)
 * - 결과: [1] [2] [3] [4] [5] [다음:6] [마지막:10]
 * - prevPage=-1, startPage=1, endPage=5, nextPage=6, lastPage=10
 *
 * UI 표시 규칙:
 * - prevPage != -1 이면 "이전" 버튼 표시
 * - startPage ~ endPage 범위의 페이지 번호들 표시
 * - currentPage는 강조 표시 (현재 페이지)
 * - nextPage != -1 이면 "다음" 버튼 표시
 * - lastPage != -1 이면 "마지막" 버튼 표시
 * - -1 값들은 해당 버튼을 숨김 처리
 */