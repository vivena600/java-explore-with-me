package ru.practicum.ewmservice.admin.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.base.dto.comment.CommentDto;
import ru.practicum.ewmservice.base.exception.NotFoundException;
import ru.practicum.ewmservice.base.mapper.CommentMapper;
import ru.practicum.ewmservice.base.model.Comment;
import ru.practicum.ewmservice.base.model.User;
import ru.practicum.ewmservice.base.repository.CommentRepository;
import ru.practicum.ewmservice.base.repository.UserRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCommentServiceImpl implements AdminCommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    /***
     * Получение списка комментариев пользователя
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByUserId(Long userId) {
        log.info("getCommentsByUserId, userId:{}",userId);
        checkUserById(userId);
        return commentRepository.findCommentsByUserId(userId).stream()
                .map(commentMapper::mapCommentToCommentDto)
                .toList();
    }

    /**
     * Удаление комментария пользователя администратором
     */
    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        log.info("deleteComment, commentId:{}",commentId);
        Comment comment = checkCommentById(commentId);
        commentRepository.delete(comment);
        log.info("successes delete comment {}", comment.toString());
    }

    private Comment checkCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id=" + commentId + " was not found"));
    }

    private User checkUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id= " + userId + " was not found"));
    }
}
