package kr.ac.kopo.ctc.kopo01.board;

import kr.ac.kopo.ctc.kopo01.board.service.SampleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SampleServiceTest {

    @Autowired
    private SampleService sampleService;
    @Test
    void testNoCache1() {
        System.out.println("testNoCache1 Start");
        String ret = sampleService.testNoCache(3L);
        System.out.println("testNoCache1 End, " + ret);
    }
    @Test
    void testNoCache2() {
        System.out.println("testNoCache2 Start");
        String ret = sampleService.testNoCache(3L);
        System.out.println("testNoCache2 End, " + ret);
    }
    @Test
    void testCache1() {
        System.out.println("testNoCache1 Start");
        String ret = sampleService.testCache(3L);
        System.out.println("testNoCache1 End, " + ret);
    }
    @Test
    void testCache2() {
        System.out.println("testNoCache2 Start");
        String ret = sampleService.testCache(3L);
        System.out.println("testNoCache2 End, " + ret);
    }
}
