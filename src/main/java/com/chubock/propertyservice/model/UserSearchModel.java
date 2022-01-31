package com.chubock.propertyservice.model;

import com.chubock.propertyservice.entity.Answer;
import com.chubock.propertyservice.entity.Gender;
import com.chubock.propertyservice.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserSearchModel implements Specification<User> {

    private static final Predicate[] ARRAY_TYPE = {};

    private String ignoreUserId;
    private Boolean landlord;
    private Integer minAge;
    private Integer maxAge;
    private Gender gender;
    private Boolean drinks;
    private Boolean smokes;
    private Boolean allergy;
    private boolean verified;

    @NotEmpty(message = "location can't be black")
    private String locationId;

    private Set<String> applicable = new HashSet<>();

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.isFalse(root.get("deleted")));
        predicates.add(criteriaBuilder.isFalse(root.get("hidden")));

        predicates.add(criteriaBuilder.equal(root.get("locationId"), locationId));

        if (ignoreUserId != null)
            predicates.add(criteriaBuilder.notEqual(root.get("id"), ignoreUserId));

        if (landlord != null)
            predicates.add(criteriaBuilder.equal(root.get("landlord"), landlord));

        if (minAge != null)
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), LocalDate.now().minusYears(minAge)));

        if (maxAge != null)
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), LocalDate.now().minusYears(maxAge)));

        if (gender != null)
            predicates.add(criteriaBuilder.equal(root.get("gender"), gender));

        if (drinks != null) {
            if (drinks)
                predicates.add(criteriaBuilder.notEqual(root.get("drinks"), Answer.NO));
            else
                predicates.add(criteriaBuilder.equal(root.get("drinks"), Answer.NO));
        }

        if (smokes != null) {
            if (smokes)
                predicates.add(criteriaBuilder.notEqual(root.get("smokes"), Answer.NO));
            else
                predicates.add(criteriaBuilder.equal(root.get("smokes"), Answer.NO));
        }

        if (allergy != null) {
            if (allergy)
                predicates.add(criteriaBuilder.isNotNull(root.get("allergy")));
            else
                predicates.add(criteriaBuilder.isNull(root.get("allergy")));
        }

        if (applicable != null)
            applicable.forEach(a -> predicates.add(criteriaBuilder.isMember(a, root.get("applicable"))));

        return criteriaBuilder.and(predicates.toArray(ARRAY_TYPE));
    }
}
