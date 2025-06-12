package kr.ac.kopo.ctc.kopo01.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.kopo.ctc.kopo01.board.domain.Child;
import org.springframework.stereotype.Repository;

import java.util.List;
import static kr.ac.kopo.ctc.kopo01.board.domain.QParent.parent;
import static kr.ac.kopo.ctc.kopo01.board.domain.QChild.child;


@Repository
public class ChildRepositoryCustomImpl implements ChildRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ChildRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Child> selectAll() {
        return queryFactory
                .selectFrom(child)
                .leftJoin(child.parent, parent)
                .fetchJoin()
                .fetch();
    }
}

