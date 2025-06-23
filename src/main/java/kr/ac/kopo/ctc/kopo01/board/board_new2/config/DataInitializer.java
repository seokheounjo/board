package kr.ac.kopo.ctc.kopo01.board.board_new2.config;

import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.Post;
import kr.ac.kopo.ctc.kopo01.board.board_new2.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PostRepository postRepository;

    public DataInitializer(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 애플리케이션 시작 시 null 값 처리
        initializeNullValues();
    }

    private void initializeNullValues() {
        List<Post> posts = postRepository.findAll();
        boolean needsUpdate = false;

        for (Post post : posts) {
            if (post.getViewCount() == null) {
                post.setViewCount(0L);
                needsUpdate = true;
            }
            if (post.getLikeCount() == null) {
                post.setLikeCount(0L);
                needsUpdate = true;
            }
        }

        if (needsUpdate) {
            postRepository.saveAll(posts);
            System.out.println("✅ 초기화 완료: null 값들을 0으로 설정했습니다.");
        }
    }
}