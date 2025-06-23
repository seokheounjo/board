package kr.ac.kopo.ctc.kopo01.board.board_new2.dto;

public class PaginationInfo {
    private int firstPage;        // p  - 첫 페이지로 이동
    private int prevPage;         // pp - 이전 페이지/블록으로 이동
    private int startPage;        // s  - 현재 블록의 시작 페이지
    private int currentPage;      // c  - 현재 페이지
    private int endPage;          // e  - 현재 블록의 끝 페이지
    private int nextPage;         // n  - 다음 페이지/블록으로 이동
    private int lastPage;         // nn - 마지막 페이지로 이동
    private int totalPageCount;   // 총 페이지 수

    // 기본 생성자
    public PaginationInfo() {}

    // Getters and Setters
    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    @Override
    public String toString() {
        return String.format("PaginationInfo(pp=%d, p=%d, s=%d, c=%d, e=%d, n=%d, nn=%d)",
                prevPage, firstPage, startPage, currentPage,
                endPage, nextPage, lastPage);
    }
}