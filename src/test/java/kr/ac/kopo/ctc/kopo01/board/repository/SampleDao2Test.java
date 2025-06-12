package kr.ac.kopo.ctc.kopo01.board.repository;

import kr.ac.kopo.ctc.kopo01.board.domain.Sample;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@SpringBootTest
class SampleDao2Test {

    @Autowired
    SampleDao2 sampleDao2;


    @Test
    void findAll() {
        List<Sample> list = sampleDao2.findAll();
        assertEquals(2, list.size());
    }

    @Test
    void findById() {
        Sample sample = sampleDao2.findById(1L);
        assertEquals(1L, sample.getId());
    }
}