package kr.ac.kopo.ctc.kopo01.board.service;

import kr.ac.kopo.ctc.kopo01.board.domain.Sample;
import kr.ac.kopo.ctc.kopo01.board.repository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class SampleServicImpl implements SampleService{
    @Autowired
    SampleRepository sampleRepository;

    @Override
    public void testNoTransactional() {
        Sample sample = sampleRepository.findById(1L).get();
        sample.setTitle("update1");
        sampleRepository.save(sample);

        throw new RuntimeException("No Transactional Test");
    }

    @Transactional
    @Override
    public  void testTransactional(){
        Sample sample = sampleRepository.findById(1L).get();
        sample.setTitle("transaction");
        sampleRepository.save(sample);

        throw new RuntimeException("Transactional Test");
    }
}
