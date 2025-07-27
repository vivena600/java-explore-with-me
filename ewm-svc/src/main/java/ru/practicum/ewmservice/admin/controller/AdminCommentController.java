package ru.practicum.ewmservice.admin.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.admin.service.AdminCommentService;

@RestController
@RequestMapping("/admin/comments")
@Slf4j
@RequiredArgsConstructor
public class AdminCommentController {
    private final AdminCommentService adminCommentService;

    /**
     * DEL /admin/comments/{commentId}
     * Удаление комментария
    */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable @Positive Long commentId) {
        log.info("delete comment id:{}", commentId);
        adminCommentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
