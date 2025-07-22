package ru.practicum.ewmservice.base.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.admin.dto.RequestGetEventsDto;
import ru.practicum.ewmservice.base.model.Event;
import ru.practicum.ewmservice.publicApi.dto.RequestEventDto;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventRepositoryCustomImpl implements EventRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public List<Event> searchEventBuAdminParams(RequestGetEventsDto params) {
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
                .setMaxResults(params.getFrom() + params.getSize())
                .getResultList();
    }

    @Override
    public List<Event> searchEventByPublicParam(RequestEventDto params) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = criteriaBuilder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);

        List<Predicate> predicates = new ArrayList<>();

        if (params.getCategories() != null && !params.getCategories().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("categoryId").get("id")
                    .in(params.getCategories()), entityManager));
        }

        if (params.getText() != null && !params.getText().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("text"), params.getText()));
        }

        if (params.getPaid() != null) {
            predicates.add(criteriaBuilder.equal(root.get("paid"), params.getPaid()));
        }

        if (params.getOnlyAvailable() != null) {
            predicates.add(criteriaBuilder.equal(root.get("onlyAvailable").get("id"), params.getOnlyAvailable()));
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
                .setMaxResults(params.getFrom() + params.getSize())
                .getResultList();
    }
}
