package ru.practicum.ewmservice.base.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewmservice.base.dto.comment.AddCommentDto;
import ru.practicum.ewmservice.base.dto.comment.CommentDto;
import ru.practicum.ewmservice.base.model.Comment;
import ru.practicum.ewmservice.base.model.Event;
import ru.practicum.ewmservice.base.model.User;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    default Comment toComment(CommentDto comment, User user, Event event) {
        return Comment.builder()
                .id(comment.getId())
                .text(comment.getText())
                .user(user)
                .created(comment.getCreated())
                .event(event)
                .build();
    }

    default Comment mapAddDtoToComment(AddCommentDto commentDto, User user, Event event) {
        return Comment.builder()
                .text(commentDto.getText())
                .user(user)
                .event(event)
                .created(LocalDateTime.now())
                .build();
    }

    default CommentDto mapCommentToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .author(comment.getUser().getName())
                .created(comment.getCreated())
                .build();
    }
}
