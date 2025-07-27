package ru.practicum.ewmservice.privateApi.service;

import ru.practicum.ewmservice.base.dto.comment.AddCommentDto;
import ru.practicum.ewmservice.base.dto.comment.CommentDto;
import ru.practicum.ewmservice.base.dto.comment.UpdateCommentDto;

public interface PrivateCommentService {

    CommentDto createComment(Long userId, Long eventId, AddCommentDto commentDto);

    CommentDto updateComment(Long userId, Long eventId, Long commentId, UpdateCommentDto commentDto);

    void deleteComment(Long userId, Long eventId, Long commentId);
}
