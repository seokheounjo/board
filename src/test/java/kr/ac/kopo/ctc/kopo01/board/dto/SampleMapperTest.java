package kr.ac.kopo.ctc.kopo01.board.dto;


import kr.ac.kopo.ctc.kopo01.board.domain.Sample;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SampleMapperTest {

    @Test
    void toDto() {
        Sample sample = new Sample(1L,"홍길동");
        SampleDto sampleDto = SampleMapper.INSTANCE.toDto(sample);
        assertEquals("홍길동", sampleDto.getTitle());
    }
}
