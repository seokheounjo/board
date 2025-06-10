package kr.ac.kopo.ctc.kopo01.board.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping("/list")
    public  String list(Model model){
        return "list";
    }
}
