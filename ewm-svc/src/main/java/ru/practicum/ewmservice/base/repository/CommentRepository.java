package ru.practicum.ewmservice.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.base.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByEventId(Long eventId);

    List<Comment> findCommentsByUserId(Long userId);
}
