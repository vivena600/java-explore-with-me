package ru.practicum.ewmservice.base.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.admin.dto.RequestGetEventsDto;
import ru.practicum.ewmservice.base.exception.ValidationRequestException;
import ru.practicum.ewmservice.base.model.Event;
import ru.practicum.ewmservice.publicApi.dto.RequestEventDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventRepositoryCustomImpl implements EventRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public List<Event> searchEventBuAdminParams(RequestGetEventsDto params) {
        checkDateInRequest(params.getRangeStart(), params.getRangeEnd());
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = criteriaBuilder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);

        List<Predicate> predicates = new ArrayList<>();

        if (params.getUsers() != null && !params.getUsers().isEmpty()) {
            predicates.add(root.get("userId").get("id").in(params.getUsers()));
        }

        if (params.getCategories() != null && !params.getCategories().isEmpty()) {
            predicates.add(root.get("categoryId").get("id").in(params.getCategories()));
        }

        if (params.getStates() != null && !params.getStates().isEmpty()) {
            predicates.add(root.get("state").in(params.getStates()));
        }

        if (params.getRangeStart() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), params.getRangeStart()));
        }

        if (params.getRangeEnd() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), params.getRangeEnd()));
        }

        query.select(root).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query)
                .setFirstResult(params.getFrom())
                .setMaxResults(params.getSize())
                .getResultList();
    }

    @Override
    public List<Event> searchEventByPublicParam(RequestEventDto params) {
        checkDateInRequest(params.getRangeStart(), params.getRangeEnd());
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = criteriaBuilder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);

        List<Predicate> predicates = new ArrayList<>();

        if (params.getCategories() != null && !params.getCategories().isEmpty()) {
            predicates.add(root.get("categoryId").get("id").in(params.getCategories()));
        }

        if (params.getText() != null && !params.getText().isEmpty()) {
            String pattern = "%" + params.getText().toLowerCase() + "%";
            predicates.add(
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("annotation")), pattern),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), pattern)
                    )
            );
        }

        if (params.getPaid() != null) {
            predicates.add(criteriaBuilder.equal(root.get("paid"), params.getPaid()));
        }

        if (params.getRangeStart() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), params.getRangeStart()));
        }

        if (params.getRangeEnd() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), params.getRangeEnd()));
        }

        if (params.getOnlyAvailable() != null && params.getOnlyAvailable()) {
            predicates.add(criteriaBuilder.lt(root.get("confirmedRequests"), root.get("participantLimit")));
        }

        query.select(root).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        //Сортировка
        if ("EVENT_DATE".equalsIgnoreCase(params.getSort())) {
            query.orderBy(criteriaBuilder.asc(root.get("date")));
        } else if ("VIEWS".equalsIgnoreCase(params.getSort())) {
            query.orderBy(criteriaBuilder.desc(root.get("views")));
        }

        return entityManager.createQuery(query)
                .setFirstResult(params.getFrom())
                .setMaxResults(params.getSize())
                .getResultList();
    }

    private void checkDateInRequest(LocalDateTime start, LocalDateTime end) {
        if (start != null && end != null && start.isAfter(end)) {
            throw new ValidationRequestException("start date cannot be after end date");
        }
    }
}
