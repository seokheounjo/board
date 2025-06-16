package kr.ac.kopo.ctc.kopo01.board.service;


import jakarta.transaction.Transactional;
import kr.ac.kopo.ctc.kopo01.board.domain.Sample;
import kr.ac.kopo.ctc.kopo01.board.repository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;



@Service
public class SampleServicImpl implements SampleService {

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

    @Override
    public String testNoCache(Long id){
        sleep(3);
        return "NoCache";
    }

    @Override
    @Cacheable(value="sample", key="#id")
    public String testCache(Long id) {
        sleep(3);
        return "Cache";
    }

    @Override
    @CacheEvict(value="sample", key="#id")
    public  void testCacheClear(Long id) {
    }

    private void  sleep(int second) {
        try {
            Thread.sleep(second * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
