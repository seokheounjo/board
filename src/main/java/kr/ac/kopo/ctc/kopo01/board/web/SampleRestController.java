package kr.ac.kopo.ctc.kopo01.board.web;

import kr.ac.kopo.ctc.kopo01.board.domain.Sample;
import kr.ac.kopo.ctc.kopo01.board.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sample")
public class SampleRestController {

    @Autowired
    private SampleService sampleService;

    @GetMapping("/sampleOne")
    public Sample sampleOne() {
        Sample sample = new Sample();
        sample.setId(1L);
        sample.setTitle("title1");
        return sample;
    }

    @GetMapping("/sampleAll")
    public List<Sample> sampleAll() {
        Sample sample1  = new Sample();
        sample1.setId(1L);
        sample1.setTitle("title1");

        Sample sample2  = new Sample();
        sample1.setId(2L);
        sample1.setTitle("title2");

        List<Sample> ret = new ArrayList<>();
        ret.add(sample1);
        ret.add(sample2);
        return ret;
    }

    @GetMapping("/entityOne")
    public ResponseEntity<Sample> entityOne() {
        Sample sample = new Sample();
        sample.setId(1L);
        sample.setTitle("title1");
        return ResponseEntity.ok(sample);
    }

    @GetMapping("/entityAll")
    public ResponseEntity<List<Sample>> entityAll() {
        Sample sample1 = new Sample();
        sample1.setId(1L);
        sample1.setTitle("title1");

        Sample sample2 = new Sample();
        sample2.setId(2L);
        sample2.setTitle("title2");

        List<Sample> ret = new ArrayList<>();
        ret.add(sample1);
        ret.add(sample2);
        return ResponseEntity.ok(ret);
    }

    @GetMapping("/entityError")
    public ResponseEntity<Sample> entityError() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }


}
