package com.chubock.propertyservice.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "PROPERTIES")
@SuperBuilder(toBuilder = true)
public class Property extends AbstractEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User owner;

    @Column
    private Boolean livingLandlord;

    private String squareFootage;

    @PositiveOrZero
    private Integer bedroomsCount;

    @PositiveOrZero
    private Integer bathroomsCount;

    private Boolean sharedBathroom;
    private Boolean sharedCookingFacilities;

    private Boolean furnishing;
    private Boolean smoking;
    private Boolean pets;

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> amenities = new HashSet<>();

    private String leaseTerm;

    @PositiveOrZero
    private Long monthlyRent;

    private String rentCurrency;

    @Column(columnDefinition = "date")
    private LocalDate availabilityDate;


    private Double latitude;
    private Double longitude;

    private String country;
    private String province;
    private String city;

    private String streetAddress;
    private String optionalAddress;
    private String postalCode;

    private String countryId;
    private String provinceId;
    private String cityId;
    private String streetAddressId;

    private String mainImage;

    @OrderColumn
    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images = new ArrayList<>();

    @OrderColumn
    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> videos = new ArrayList<>();

    @Size(max = 300)
    @Column(length = 300)
    private String description;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PropertyType type;

    @Column(nullable = false)
    private boolean finalized;

    @Column(nullable = false)
    private boolean verified;

    @Column(nullable = false)
    private boolean deleted;


}
