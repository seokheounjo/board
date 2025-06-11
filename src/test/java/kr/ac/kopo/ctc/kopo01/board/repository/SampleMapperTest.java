package kr.ac.kopo.ctc.kopo01.board.repository;


import kr.ac.kopo.ctc.kopo01.board.domain.Sample;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SampleMapperTest {
    @Autowired
    SampleMapper sampleMapper;

    @Test
    void findAll() {
        List<Sample> samples = sampleMapper.findAll();
        for (Sample sample : samples) {            System.out.println(sample.getTitle());
        }
    }
    @Test
    void findByTitle() {
        RowBounds rowBounds = new RowBounds(0, 10);
        List<Sample> samples = sampleMapper.findAllByTitle("t1", rowBounds);
        for (Sample sample : samples) {
            System.out.println(sample.getTitle());
        }
    }
}
