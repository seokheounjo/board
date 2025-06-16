package kr.ac.kopo.ctc.kopo01.board.service;

import kr.ac.kopo.ctc.kopo01.board.domain.Sample;

public interface SampleService {

    String testNoCache(Long id);
    String testCache(Long id);
    void testCacheClear(Long id);

    void testNoTransactional();
    void testTransactional();

    Sample selectOne(Long id);
}
