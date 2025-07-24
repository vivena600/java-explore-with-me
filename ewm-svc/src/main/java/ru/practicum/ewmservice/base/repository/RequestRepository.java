package ru.practicum.ewmservice.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewmservice.base.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("SELECT r FROM Request r "
            + "WHERE r.event.id = :eventId AND r.user.id = :userId")
    List<Request> findByEventIdAndUserId(@Param("eventId") Long eventId,
                                @Param("userId") Long userId);

    @Query("SELECT r FROM Request r "
            + "WHERE r.event.id = :eventId")
    List<Request> findByEventId(@Param("eventId") Long eventId);

    @Query("SELECT r FROM Request r "
            + "WHERE r.user.id = :userId ")
    List<Request> findByUserId(@Param("userId") Long userId);

    @Query("SELECT r FROM Request r "
            + "WHERE r.id IN (:requestId)")
    List<Request> findByRequestId(@Param("requestId") List<Long> requestId);
}
