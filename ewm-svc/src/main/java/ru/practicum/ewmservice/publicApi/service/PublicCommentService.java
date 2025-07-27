package ru.practicum.ewmservice.publicApi.service;

import ru.practicum.ewmservice.base.dto.comment.CommentDto;

import java.util.List;

public interface PublicCommentService {

    List<CommentDto> getComments(Long eventId);

    CommentDto getCommentById(Long commentId);
}
