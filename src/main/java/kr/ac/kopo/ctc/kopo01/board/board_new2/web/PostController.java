package kr.ac.kopo.ctc.kopo01.board.board_new2.web;

import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.Comment;
import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.Post;
import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.User;
import kr.ac.kopo.ctc.kopo01.board.board_new2.dto.PostDTO;
import kr.ac.kopo.ctc.kopo01.board.board_new2.repository.CommentRepository;
import kr.ac.kopo.ctc.kopo01.board.board_new2.repository.PostRepository;
import kr.ac.kopo.ctc.kopo01.board.board_new2.repository.LikeRepository;
import kr.ac.kopo.ctc.kopo01.board.board_new2.repository.UserRepository;
import kr.ac.kopo.ctc.kopo01.board.board_new2.dto.PaginationInfo;
import kr.ac.kopo.ctc.kopo01.board.board_new2.service.PaginationInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PaginationInfoService paginationInfoService;

    public PostController(PostRepository postRepository,
                          CommentRepository commentRepository,
                          LikeRepository likeRepository,
                          UserRepository userRepository,
                          PaginationInfoService paginationInfoService) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.paginationInfoService = paginationInfoService;
    }

    // 페이지 크기 옵션들
    private static final int[] PAGE_SIZE_OPTIONS = {3, 5, 10, 20};
    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final int BLOCK_SIZE = 5;

    @GetMapping
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "id") String sort,
                       @RequestParam(defaultValue = "desc") String order,
                       @RequestParam(defaultValue = "5") int pageSize,
                       @RequestParam(required = false) String keyword,
                       Model model) {

        try {
            // 페이지 크기 검증
            if (!isValidPageSize(pageSize)) {
                pageSize = DEFAULT_PAGE_SIZE;
            }

            List<Post> allPosts = postRepository.findAll();

            // 검색어 안전 처리
            final String cleanKeyword = (keyword != null && !keyword.trim().isEmpty())
                    ? keyword.trim()
                    : null;

            // 검색 필터링
            if (cleanKeyword != null) {
                // username으로 검색할 사용자들 찾기
                List<User> matchingUsers = userRepository.findAll().stream()
                        .filter(user -> {
                            if (user == null) return false;

                            boolean usernameMatch = user.getUsername() != null &&
                                    user.getUsername().toLowerCase().contains(cleanKeyword.toLowerCase());
                            boolean nameMatch = user.getName() != null &&
                                    user.getName().toLowerCase().contains(cleanKeyword.toLowerCase());

                            return usernameMatch || nameMatch;
                        })
                        .toList();

                Set<Long> matchingUserIds = matchingUsers.stream()
                        .map(User::getId)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());

                allPosts = allPosts.stream()
                        .filter(post -> {
                            if (post == null) return false;

                            boolean titleMatch = post.getTitle() != null &&
                                    post.getTitle().toLowerCase().contains(cleanKeyword.toLowerCase());
                            boolean contentMatch = post.getContent() != null &&
                                    post.getContent().toLowerCase().contains(cleanKeyword.toLowerCase());
                            boolean usernameMatch = post.getUserId() != null &&
                                    matchingUserIds.contains(post.getUserId());
                            return titleMatch || contentMatch || usernameMatch;
                        })
                        .toList();
            }

            int totalPosts = allPosts.size();

            // 페이지네이션
            PaginationInfo pageInfo = paginationInfoService.getPaginationInfo(
                    totalPosts, pageSize, BLOCK_SIZE, page);

            // 페이지 범위 체크
            if (page > pageInfo.getTotalPageCount() && pageInfo.getTotalPageCount() > 0) {
                String redirectUrl = "redirect:/posts?page=" + pageInfo.getTotalPageCount() +
                        "&sort=" + sort + "&order=" + order + "&pageSize=" + pageSize;
                if (cleanKeyword != null) {
                    redirectUrl += "&keyword=" + cleanKeyword;
                }
                return redirectUrl;
            }

            // 정렬 및 페이징
            List<Post> pagePosts = applySortAndPaging(allPosts, sort, order, page, pageSize);

            // ⭐ Post를 PostDTO로 변환 - 성능 최적화된 방식
            // 1단계: 필요한 userId들 수집
            Set<Long> userIds = pagePosts.stream()
                    .map(Post::getUserId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            // 2단계: 한 번의 쿼리로 모든 사용자 정보 조회
            Map<Long, User> userMap = new HashMap<>();
            if (!userIds.isEmpty()) {
                userMap = userRepository.findAllById(userIds).stream()
                        .filter(user -> user != null && user.getId() != null)
                        .collect(Collectors.toMap(User::getId, user -> user));
            }

            // 3단계: PostDTO 리스트 생성
            final Map<Long, User> finalUserMap = userMap;
            List<PostDTO> postDTOs = pagePosts.stream()
                    .map(post -> {
                        User user = post.getUserId() != null ? finalUserMap.get(post.getUserId()) : null;
                        return PostDTO.from(post, user);
                    })
                    .collect(Collectors.toList());

            // 모델에 데이터 추가
            model.addAttribute("posts", postDTOs);  // ⭐ PostDTO 리스트 전달
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("sort", sort);
            model.addAttribute("order", order);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pageSizeOptions", PAGE_SIZE_OPTIONS);
            model.addAttribute("keyword", cleanKeyword != null ? cleanKeyword : "");
            model.addAttribute("hasKeyword", cleanKeyword != null);
            model.addAttribute("totalPosts", totalPosts);

            return "list";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "목록 조회 중 오류가 발생했습니다.");
            return "redirect:/posts";
        }
    }

    @GetMapping("/{id}")
    public String postpage(@PathVariable Long id, Model model, HttpSession session) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + id));

        // 조회수 증가
        post.incrementViewCount();
        postRepository.save(post);

        model.addAttribute("postpage", post);

        List<Comment> comments = commentRepository.findByPostId(id);
        model.addAttribute("comments", comments);

        // 게시글 작성자 정보 조회
        if (post.getUserId() != null) {
            User postAuthor = userRepository.findById(post.getUserId()).orElse(null);
            model.addAttribute("postAuthor", postAuthor != null ? postAuthor.getUsername() : "알 수 없음");
        } else {
            model.addAttribute("postAuthor", "알 수 없음");
        }

        // 댓글 작성자 정보 조회
        Map<Long, String> commentUserMap = new HashMap<>();
        Set<Long> commentUserIds = comments.stream()
                .map(Comment::getUserId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (!commentUserIds.isEmpty()) {
            List<User> commentUsers = userRepository.findAllById(commentUserIds);
            commentUsers.forEach(user -> {
                if (user != null && user.getId() != null && user.getUsername() != null) {
                    commentUserMap.put(user.getId(), user.getUsername());
                }
            });
        }
        model.addAttribute("commentUserMap", commentUserMap);

        // 현재 사용자의 좋아요 상태 확인
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null) {
            boolean liked = likeRepository.existsByUserIdAndPostId(currentUser.getId(), id);
            model.addAttribute("liked", liked);
        } else {
            model.addAttribute("liked", false);
        }

        return "postpage";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + id));

        // 권한 확인: 작성자 또는 ADMIN만 수정 가능
        if (!post.getUserId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRoles())) {
            throw new IllegalArgumentException("게시글을 수정할 권한이 없습니다.");
        }

        model.addAttribute("post", post);
        return "editPost";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Post postData, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + id));

        // 권한 확인
        if (!post.getUserId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRoles())) {
            throw new IllegalArgumentException("게시글을 수정할 권한이 없습니다.");
        }

        post.setTitle(postData.getTitle());
        post.setContent(postData.getContent());
        post.setBoardId(postData.getBoardId());

        postRepository.save(post);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + id));

        // 권한 확인
        if (!post.getUserId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRoles())) {
            throw new IllegalArgumentException("게시글을 삭제할 권한이 없습니다.");
        }

        postRepository.deleteById(id);
        return "redirect:/posts";
    }

    @GetMapping("/new")
    public String createForm(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }

        model.addAttribute("post", new Post());
        return "editPost";
    }

    @PostMapping("/new")
    public String createPost(@ModelAttribute Post postData, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }

        postData.setUserId(currentUser.getId());
        postRepository.save(postData);
        return "redirect:/posts";
    }

    // 정렬 및 페이징 적용 메서드
    private List<Post> applySortAndPaging(List<Post> posts, String sort, String order, int page, int pageSize) {
        return posts.stream()
                .sorted((a, b) -> {
                    int result = 0;
                    switch (sort) {
                        case "title":
                            String titleA = a.getTitle() != null ? a.getTitle() : "";
                            String titleB = b.getTitle() != null ? b.getTitle() : "";
                            result = titleA.compareToIgnoreCase(titleB);
                            break;
                        case "userId":
                            Long userIdA = a.getUserId() != null ? a.getUserId() : 0L;
                            Long userIdB = b.getUserId() != null ? b.getUserId() : 0L;
                            result = Long.compare(userIdA, userIdB);
                            break;
                        case "viewCount":
                            Long viewCountA = a.getViewCount() != null ? a.getViewCount() : 0L;
                            Long viewCountB = b.getViewCount() != null ? b.getViewCount() : 0L;
                            result = Long.compare(viewCountA, viewCountB);
                            break;
                        case "likeCount":
                            Long likeCountA = a.getLikeCount() != null ? a.getLikeCount() : 0L;
                            Long likeCountB = b.getLikeCount() != null ? b.getLikeCount() : 0L;
                            result = Long.compare(likeCountA, likeCountB);
                            break;
                        case "createdAt":
                            if (a.getCreatedAt() == null && b.getCreatedAt() == null) return 0;
                            if (a.getCreatedAt() == null) return 1;
                            if (b.getCreatedAt() == null) return -1;
                            result = a.getCreatedAt().compareTo(b.getCreatedAt());
                            break;
                        default: // id
                            result = Long.compare(a.getId(), b.getId());
                            break;
                    }

                    // 정렬 방향 적용
                    return "asc".equals(order) ? result : -result;
                })
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .toList();
    }

    // 페이지 크기 검증
    private boolean isValidPageSize(int pageSize) {
        for (int validSize : PAGE_SIZE_OPTIONS) {
            if (validSize == pageSize) {
                return true;
            }
        }
        return false;
    }
}