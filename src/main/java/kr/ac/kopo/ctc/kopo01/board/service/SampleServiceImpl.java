package kr.ac.kopo.ctc.kopo01.board.service;


import jakarta.transaction.Transactional;
import kr.ac.kopo.ctc.kopo01.board.domain.Sample;
import kr.ac.kopo.ctc.kopo01.board.repository.SampleRepository;
import kr.ac.kopo.ctc.kopo01.board.web.SampleController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


@Service
public class SampleServiceImpl implements SampleService {

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

    @Override
    public Sample selectOne(Long id) {
        // 주어진 id 값을 기준으로 Sample 엔티티를 데이터베이스에서 조회한다.
        // findById()는 Optional<Sample>을 반환하므로,
        // 값이 없을 경우 IllegalArgumentException 예외를 발생시킨다.
        Sample sample = sampleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예외"));  // ← 존재하지 않으면 400 Bad Request 유사 상황 처리

        // 조회된 Sample 객체를 반환한다.
        return sample;
    }
}
