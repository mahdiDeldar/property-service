package com.chubock.propertyservice.model;

import com.chubock.propertyservice.entity.Property;
import com.chubock.propertyservice.entity.PropertyType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PropertySearchModel implements Specification<Property> {

    public static final double RADIUS = 1;

    private static final Predicate[] ARRAY_TYPE = {};

    private Boolean livingLandlord;

    private Long minMonthlyRent;
    private Long maxMonthlyRent;
    private PropertyType type;
    private String leaseTerm;
    private Integer minBedroomsCount;
    private Integer minBathroomsCount;

    private Boolean sharedBathroom;
    private Boolean sharedCookingFacilities;

    private Boolean pets;
    private Boolean furnishing;
    private Boolean smoking;
    private List<String> amenities;
    private boolean verified;

    @NotNull(message = "latitude can't be null")
    @Max(value = 90, message = "latitude can't be more that 90")
    @Min(value = -90, message = "latitude can't be less that -90")
    private Double latitude;

    @NotNull(message = "longitude can't be null")
    @Max(value = 180, message = "longitude can't be more that 180")
    @Min(value = -180, message = "longitude can't be less that -180")
    private Double longitude;

    @Override
    public Predicate toPredicate(Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

//        root.fetch("owner");
//        root.fetch("images", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.isTrue(root.get("finalized")));
        predicates.add(criteriaBuilder.isFalse(root.get("deleted")));

        //TODO chubock: we should consider 90 and -90 for latitude. It's rare but should be considered.
        if (latitude != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("latitude"), latitude + RADIUS));
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("latitude"), latitude - RADIUS));
        }

        //TODO chubock: we should consider 180 and -180 for latitude. It's rare but should be considered.
        if (longitude != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("longitude"), longitude + RADIUS));
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("longitude"), longitude - RADIUS));
        }


        if (livingLandlord != null)
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("livingLandlord"), minMonthlyRent));

        if (minMonthlyRent != null)
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("monthlyRent"), minMonthlyRent));

        if (maxMonthlyRent != null)
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("monthlyRent"), maxMonthlyRent));

        if (type != null)
            predicates.add(criteriaBuilder.equal(root.get("type"), type));

        if (leaseTerm != null)
            predicates.add(criteriaBuilder.equal(root.get("leaseTerm"), leaseTerm));

        if (minBedroomsCount != null)
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("bedroomsCount"), minBedroomsCount));

        if (minBathroomsCount != null)
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("bathroomsCount"), minBathroomsCount));

        if (sharedBathroom != null)
            predicates.add(criteriaBuilder.equal(root.get("sharedBathroom"), sharedBathroom));

        if (sharedCookingFacilities != null)
            predicates.add(criteriaBuilder.equal(root.get("sharedCookingFacilities"), sharedCookingFacilities));

        if (pets != null)
            predicates.add(criteriaBuilder.equal(root.get("pets"), pets));

        if (furnishing != null)
            predicates.add(criteriaBuilder.equal(root.get("furnishing"), furnishing));

        if (smoking != null)
            predicates.add(criteriaBuilder.equal(root.get("smoking"), smoking));

        if (amenities != null && !amenities.isEmpty())
            predicates.add(root.join("amenities").in(amenities));

        if (verified)
            predicates.add(criteriaBuilder.isTrue(root.get("verified")));

        return criteriaBuilder.and(predicates.toArray(ARRAY_TYPE));
    }
}
