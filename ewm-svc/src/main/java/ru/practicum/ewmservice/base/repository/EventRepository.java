package ru.practicum.ewmservice.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewmservice.base.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {

    @Query("SELECT e FROM Event e "
            + "WHERE e.id = :eventId AND e.userId.id = :userId")
    Optional<Event> findByIdAndUserId(@Param("userId") Long userId,
                                      @Param("eventId") Long eventId);

    @Query("SELECT e FROM Event e "
            + "WHERE e.categoryId.id = :id")
    List<Event> findAllByCategoryId(@Param("id") Long categoryId);

    List<Event> findAllByIdIn(List<Long> ids);

}
