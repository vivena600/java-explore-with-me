package ru.practicum.ewmservice.publicApi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.base.dto.comment.CommentDto;
import ru.practicum.ewmservice.base.exception.NotFoundException;
import ru.practicum.ewmservice.base.mapper.CommentMapper;
import ru.practicum.ewmservice.base.model.Comment;
import ru.practicum.ewmservice.base.model.Event;
import ru.practicum.ewmservice.base.repository.CommentRepository;
import ru.practicum.ewmservice.base.repository.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PublicCommentServiceImpl implements PublicCommentService {
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    /**
     * Получение всех комментариев под событием
     */
    @Override
    public List<CommentDto> getComments(Long eventId) {
        log.info("getComments, eventId:{}", eventId);
        checkEventById(eventId);
        return commentRepository.findCommentsByEventId(eventId).stream()
                .map(commentMapper::mapCommentToCommentDto).toList();
    }

    /**
     * Получение комментария по его Id
     */
    @Override
    public CommentDto getCommentById(Long commentId) {
        log.info("getCommentById, commentId:{}", commentId);
        return commentMapper.mapCommentToCommentDto(checkCommentById(commentId));
    }

    private Event checkEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " was not found"));
    }

    private Comment checkCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id=" + commentId + " was not found"));
    }
}
