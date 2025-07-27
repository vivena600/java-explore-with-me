package ru.practicum.ewmservice.admin.service;

import ru.practicum.ewmservice.base.dto.comment.CommentDto;

import java.util.List;

public interface AdminCommentService {

    List<CommentDto> getCommentsByUserId(Long userId);

    void deleteComment(Long commentId);
}
