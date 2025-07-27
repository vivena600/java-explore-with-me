package ru.practicum.ewmservice.privateApi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.base.dto.comment.AddCommentDto;
import ru.practicum.ewmservice.base.dto.comment.CommentDto;
import ru.practicum.ewmservice.base.dto.comment.UpdateCommentDto;
import ru.practicum.ewmservice.base.exception.ConflictException;
import ru.practicum.ewmservice.base.exception.NotFoundException;
import ru.practicum.ewmservice.base.mapper.CommentMapper;
import ru.practicum.ewmservice.base.model.Comment;
import ru.practicum.ewmservice.base.model.Event;
import ru.practicum.ewmservice.base.model.User;
import ru.practicum.ewmservice.base.repository.CommentRepository;
import ru.practicum.ewmservice.base.repository.EventRepository;
import ru.practicum.ewmservice.base.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivateCommentServiceImpl implements PrivateCommentService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    /**
     * Создание нового комментария под событием
     */
    @Override
    @Transactional
    public CommentDto createComment(Long userId, Long eventId, AddCommentDto commentDto) {
        log.info("createComment, userId:{}, eventId:{}", userId, eventId);
        User user = checkUserById(userId);
        Event event = checkEventById(eventId);
        Comment comment = commentMapper.mapAddDtoToComment(commentDto, user, event);
        comment.setCreated(LocalDateTime.now());
        log.info("createComment, userId:{}, eventId:{} - {}", userId, eventId, comment.toString());
        return commentMapper.mapCommentToCommentDto(commentRepository.save(comment));
    }

    /**
     * Редактирование комментария пользователя
     * Учитывает, что комментарий может редактировать только его автор
     */
    @Override
    @Transactional
    public CommentDto updateComment(Long userId, Long eventId, Long commentId, UpdateCommentDto commentDto) {
        log.info("updateComment, userId:{}, eventId:{}", userId, eventId);
        User user = checkUserById(userId);
        Event event = checkEventById(eventId);
        Comment oldComment = checkCommentById(commentId);

        //Редактировать комментарий может только автор
        if (!Objects.equals(oldComment.getUser().getId(), user.getId())) {
            throw new ConflictException("User with id= " + user.getId() + " is not allowed to update comment");
        }

        //редактировать можно только тот комментарий, который относится к event
        if (!Objects.equals(oldComment.getEvent().getId(), event.getId())) {
            throw new ConflictException("Event with id= " + event.getId() + " is not allowed to update comment");
        }

        oldComment.setText(commentDto.getText());
        log.info("successes update comment {}", oldComment.toString());
        return commentMapper.mapCommentToCommentDto(commentRepository.save(oldComment));
    }

    /**
     * Удаление комментария пользователя
     * Комментарий могут удалять следующие пользователи:
     *  автор комментария,
     *  автор события,
     *  администратор (логика прописана в сервисе AdminCommentService)
     */
    @Override
    @Transactional
    public void deleteComment(Long userId, Long eventId, Long commentId) {
        log.info("deleteComment, userId:{}, eventId:{}", userId, eventId);
        checkUserById(userId);
        Event event = checkEventById(eventId);
        Comment comment = checkCommentById(commentId);

        boolean isCommentAuthor = comment.getUser().getId().equals(userId);
        boolean isEventAuthor = event.getUserId().getId().equals(userId);

        if (isCommentAuthor || isEventAuthor) {
            commentRepository.delete(comment);
            log.info("successes delete comment {}", comment.toString());
        } else {
            throw new ConflictException("User with id= " + userId + " is not allowed to delete comment");
        }
    }

    private Event checkEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " was not found"));
    }

    private User checkUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id= " + userId + " was not found"));
    }

    private Comment checkCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id=" + commentId + " was not found"));
    }
}
