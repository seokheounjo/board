package kr.ac.kopo.ctc.kopo01.board.repository;

import kr.ac.kopo.ctc.kopo01.board.domain.Sample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface SampleMapper {
    List<Sample> findAll();
    Sample findById(Long id);
    List<Sample> findAllByTitle(String title, RowBounds rowBounds);
}
