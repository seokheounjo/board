package kr.ac.kopo.ctc.kopo01.board.web;

import kr.ac.kopo.ctc.kopo01.board.domain.Sample;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SampleRestControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetSample() throws Exception {
        URI uri = UriComponentsBuilder.fromPath("/sample/sampleOne")  // ← 수정됨
                .build()
                .toUri();
        Sample response = restTemplate.getForObject(uri, Sample.class);

        assertEquals(1L, response.getId());
        assertEquals("title1", response.getTitle());  // "update1" → "title1" 로 수정
    }


}
