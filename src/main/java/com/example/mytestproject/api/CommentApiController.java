package com.example.mytestproject.api;

import com.example.mytestproject.dto.CommentDto;
import com.example.mytestproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ✅ 댓글(Comment) 관련 REST API 컨트롤러
 * - CRUD 기반으로 설계된 엔드포인트 제공
 * - 클라이언트와의 JSON 기반 비동기 통신 처리
 */
@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    // ─────────────────────────────────────────────
    // [1] READ: 특정 게시글의 모든 댓글 조회
    // GET /articles/{articleId}/comments
    // ─────────────────────────────────────────────
    @GetMapping("/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {
        // ① 서비스에 위임하여 댓글 목록 조회
        List<CommentDto> dtos = commentService.comments(articleId);

        // ② 조회된 댓글 목록 반환
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // ─────────────────────────────────────────────
    // [2] CREATE: 댓글 등록
    // POST /articles/{articleId}/comments
    // ─────────────────────────────────────────────
    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId,
                                             @RequestBody CommentDto dto) {
        // ① 서비스에 위임하여 댓글 생성
        CommentDto createdDto = commentService.create(articleId, dto);

        // ② 생성된 댓글 반환
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    // ─────────────────────────────────────────────
    // [3] UPDATE: 댓글 수정
    // PATCH /comments/{id}
    // ─────────────────────────────────────────────
    @PatchMapping("/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id,
                                             @RequestBody CommentDto dto) {
        // ① 서비스에 수정 요청 전달
        CommentDto updatedDto = commentService.update(id, dto);

        // ② 수정된 댓글 반환
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    // ─────────────────────────────────────────────
    // [4] DELETE: 댓글 삭제
    // DELETE /comments/{id}
    // ─────────────────────────────────────────────
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id) {
        // ① 서비스에 삭제 요청 전달
        CommentDto deletedDto = commentService.delete(id);

        // ② 삭제된 댓글 정보 반환 (null로 반환해도 무방)
        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }
}
