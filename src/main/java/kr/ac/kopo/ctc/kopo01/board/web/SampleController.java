package kr.ac.kopo.ctc.kopo01.board.web;


import kr.ac.kopo.ctc.kopo01.board.domain.Sample;
import kr.ac.kopo.ctc.kopo01.board.repository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/sample")
public class SampleController {

    @Autowired
    private SampleRepository sampleRepository;

    @GetMapping("/list")
    @ResponseBody
    public List<Sample> list(Model model) {
        return  sampleRepository.findAll();
    }
}
