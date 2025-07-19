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
            predicates.add(criteriaBuilder.equal(root.get("users").get("id").in(params.getUsers()), entityManager));
        }

        if (params.getCategories() != null && !params.getCategories().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("category").get("id")
                    .in(params.getCategories()), entityManager));
        }

        if (params.getStates() != null && !params.getStates().isEmpty()) {
            predicates.add(root.get("state").in(params.getStates()));
        }

        if (params.getRangeStart() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), params.getRangeStart()));
        }

        if (params.getRangeEnd() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), params.getRangeEnd()));
        }

        query.select(root).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query)
                .setFirstResult(params.getFrom())
                .setMaxResults(params.getFrom() + params.getSize())
                .getResultList();
    }
}
