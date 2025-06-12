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
class SampleDao1Test {

    @Autowired
    SampleDao1 sampleDao1;
    @Test
    void findAll() {
        List<Sample> samples = sampleDao1.findAll();
        for (Sample sample : samples) {
            System.out.println(sample.getTitle());
        }
    }

    @Test
    void findById() {
        Sample sample = sampleDao1.findById(1L);
        System.out.println(sample.getTitle());
        assertEquals(1, sample.getId());
    }
}