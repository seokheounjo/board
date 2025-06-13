package kr.ac.kopo.ctc.kopo01.board;

import kr.ac.kopo.ctc.kopo01.board.domain.board.Post;
import kr.ac.kopo.ctc.kopo01.board.repository.board.PostRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Transactional
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @BeforeEach    // 각각의 테스트 메서드가 실행되기 전에 실행되는 메서드
    void setUp() {
        // 테스트의 독립성을 보장하기 위해 기존의 모든 데이터를 삭제
        // 이전 테스트의 데이터가 현재 테스트에 영향을 주지 않도록 함
        postRepository.deleteAll();

        // 테스트를 위한 샘플 데이터 5개 생성
        for (int i = 1; i <= 20; i++) {
            // 새로운 게시글 객체 생성
            Post p = new Post();

            // 테스트용 데이터 설정
            // 각 게시글은 'title1' ~ 'title5'의 제목을 가짐
            p.setTitle("title" + i);
            // 각 게시글은 'content1' ~ 'content5'의 내용을 가짐
            p.setContent("content" + i);
            // 모든 게시글의 작성자 ID를 1로 설정
            p.setUserId(1L);
            // 모든 게시글의 게시판 ID를 1로 설정
            p.setBoardId(1L);

            // 설정된 게시글을 데이터베이스에 저장
            // 저장 시 @GeneratedValue에 의해 id가 자동으로 생성됨
            postRepository.save(p);
        }
    }

    @Test
    void testPagination() {
        postRepository.deleteAll();

        for (int i = 1; i <= 20; i++) {
            Post p = new Post();
            p.setTitle("title" + i);
            p.setContent("content" + i);
            p.setUserId(1L);
            p.setBoardId(1L);
            postRepository.save(p);
        }

        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("id").descending());
        Page<Post> postPage = postRepository.findAll(pageRequest);

        // 첫 번째 페이지 정보 출력
        System.out.println("\n=== 첫 번째 페이지 정보 ===");
        System.out.println("현재 페이지 번호: " + postPage.getNumber());
        System.out.println("페이지 크기: " + postPage.getSize());
        System.out.println("전체 페이지 수: " + postPage.getTotalPages());
        System.out.println("전체 게시글 수: " + postPage.getTotalElements());
        System.out.println("현재 페이지 게시글 수: " + postPage.getNumberOfElements());
        System.out.println("다음 페이지 존재 여부: " + postPage.hasNext());
        System.out.println("이전 페이지 존재 여부: " + postPage.hasPrevious());

        System.out.println("\n=== 첫 번째 페이지 게시글 목록 ===");
        postPage.getContent().forEach(post ->
                System.out.println("ID: " + post.getId() + ", 제목: " + post.getTitle()));

        // 두 번째 페이지 조회
        PageRequest nextPageRequest = PageRequest.of(1, 5, Sort.by("id").descending());
        Page<Post> nextPostPage = postRepository.findAll(nextPageRequest);

        // 두 번째 페이지 정보 출력
        System.out.println("\n=== 두 번째 페이지 정보 ===");
        System.out.println("현재 페이지 번호: " + nextPostPage.getNumber());
        System.out.println("페이지 크기: " + nextPostPage.getSize());
        System.out.println("전체 페이지 수: " + nextPostPage.getTotalPages());
        System.out.println("전체 게시글 수: " + nextPostPage.getTotalElements());
        System.out.println("현재 페이지 게시글 수: " + nextPostPage.getNumberOfElements());
        System.out.println("다음 페이지 존재 여부: " + nextPostPage.hasNext());
        System.out.println("이전 페이지 존재 여부: " + nextPostPage.hasPrevious());

        System.out.println("\n=== 두 번째 페이지 게시글 목록 ===");
        nextPostPage.getContent().forEach(post ->
                System.out.println("ID: " + post.getId() + ", 제목: " + post.getTitle()));

        // 검증
        Assertions.assertTrue(postPage.hasContent());                  // 컨텐츠 존재 여부
        Assertions.assertEquals(5, postPage.getContent().size());     // 페이지 크기
        Assertions.assertEquals(20, postPage.getTotalElements());      // 전체 요소 수
        Assertions.assertEquals(4, postPage.getTotalPages());          // 전체 페이지 수 (20/5 = 4)
        Assertions.assertTrue(postPage.hasNext());                     // 다음 페이지 존재 여부
        Assertions.assertFalse(postPage.hasPrevious());               // 이전 페이지 존재 여부


        Assertions.assertEquals(5, nextPostPage.getContent().size()); // 두 번째 페이지 크기
        Assertions.assertTrue(nextPostPage.hasPrevious());            // 이전 페이지 존재
        Assertions.assertTrue(nextPostPage.hasNext());               // 다음 페이지 존재 (아직 2페이지가 더 남음)
    }



    @Test       // JUnit 테스트 메서드임을 나타냄
    void findAll() {
        // 모든 게시글을 조회
        List<Post> posts = postRepository.findAll();

        // setUp()에서 생성한 게시글이 5개인지 검증
        Assertions.assertEquals(20, posts.size());

        // 조회된 모든 게시글의 제목을 콘솔에 출력
        // 테스트 결과를 육안으로 확인하기 위한 용도
        for (Post p : posts) {
            System.out.println(p.getTitle());
        }
    }

    @Test
    void updatePost() {
        // 첫 번째 게시글을 가져옴
        Post post = postRepository.findAll().get(0);

        // 게시글의 제목을 "updated"로 변경
        post.setTitle("updated");
        // 변경된 게시글을 저장
        postRepository.save(post);

        // 게시글을 다시 조회
        // orElseThrow()를 사용하여 게시글이 없으면 예외 발생
        Post found = postRepository.findById(post.getId()).orElseThrow();

        // 제목이 정상적으로 "updated"로 변경되었는지 검증
        Assertions.assertEquals("updated", found.getTitle());
    }

    @Test
    void deletePost() {
        // 첫 번째 게시글을 가져옴
        Post post = postRepository.findAll().get(0);

        // 해당 게시글을 ID를 사용하여 삭제
        postRepository.deleteById(post.getId());

        // 삭제된 게시글이 더 이상 존재하지 않는지 확인
        // findById()의 결과가 비어있어야 함 (Optional.empty())
        Assertions.assertFalse(postRepository.findById(post.getId()).isPresent());
    }
}
