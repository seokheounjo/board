package kr.ac.kopo.ctc.kopo01.board.service;


import kr.ac.kopo.ctc.kopo01.board.domain.board.Post;
import kr.ac.kopo.ctc.kopo01.board.repository.board.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository repository;

    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public Post create(Post post) {
        return repository.save(post);
    }

    @Override
    public Optional<Post> read(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Post> readAll() {
        return repository.findAll();
    }

    @Override
    public Post update(Long id, Post updatedPost) {
        // repository.findById(id)로 데이터베이스에서 게시글을 찾음
        // Optional<Post>를 반환받아 map() 메서드로 처리
        return repository.findById(id).map(post -> {
            // 찾은 게시글(post)의 제목과 내용을
            // 매개변수로 받은 updatedPost의 값으로 변경
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());

            // 변경된 엔티티를 데이터베이스에 저장하고 반환
            // JPA의 영속성 컨텍스트로 인해 실제로는
            // 별도의 update 쿼리 없이 트랜잭션 종료 시점에 자동 반영됨
            return repository.save(post);
        }).orElseThrow(() -> new RuntimeException("Post not found"));
        // 게시글을 찾지 못한 경우 예외 발생
    }


    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
