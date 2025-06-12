package kr.ac.kopo.ctc.kopo01.board.repository;

import kr.ac.kopo.ctc.kopo01.board.domain.Child;

import java.util.List;

public interface ChildRepositoryCustom {
    List<Child> selectAll();
}
