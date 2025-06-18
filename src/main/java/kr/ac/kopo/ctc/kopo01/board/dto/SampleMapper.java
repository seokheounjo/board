package kr.ac.kopo.ctc.kopo01.board.dto;

import kr.ac.kopo.ctc.kopo01.board.domain.Sample;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SampleMapper {
    SampleMapper INSTANCE = Mappers.getMapper(SampleMapper.class);

    SampleDto toDto(Sample sample);

    @Mapping(target = "id", ignore = true)
    Sample toEntity(SampleDto sampleDto);

}
