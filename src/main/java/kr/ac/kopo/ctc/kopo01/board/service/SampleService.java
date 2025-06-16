package kr.ac.kopo.ctc.kopo01.board.service;

public interface SampleService {

    String testNoCache(Long id);
    String testCache(Long id);
    void testCacheClear(Long id);

    void testNoTransactional();
    void testTransactional();
}
