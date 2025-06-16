package kr.ac.kopo.ctc.kopo01.board.web;

import kr.ac.kopo.ctc.kopo01.board.domain.Sample;
import kr.ac.kopo.ctc.kopo01.board.repository.SampleRepository;
import kr.ac.kopo.ctc.kopo01.board.service.SampleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@WebMvcTest(SampleController.class)
class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SampleRepository sampleRepository;

    @MockitoBean
    private SampleService sampleService;

    @Test
    void getSample() throws Exception {
        Sample sample = new Sample(1L, "title1");
        Mockito.when(sampleService.selectOne(1L)).thenReturn(sample);

        mockMvc.perform(get("/sample/selectOne")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("title1"))
                .andDo(print());
    }

}
