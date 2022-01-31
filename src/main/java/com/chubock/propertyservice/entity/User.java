package com.chubock.propertyservice.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity {

    private String username;
    private String name;
    private String imageUrl;

    private String email;
    @Column(columnDefinition = "date")
    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String descriptor;

    private String allergy;

    @Enumerated(EnumType.STRING)
    private Answer drinks;

    @Enumerated(EnumType.STRING)
    private Answer smokes;

    @Builder.Default
    @ElementCollection
    @Column(name = "language")
    private Set<String> languages = new HashSet<>();

    @Column(columnDefinition = "text")
    private String description;

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> applicable = new HashSet<>();

    @Builder.Default
    @ElementCollection
    @Column(name = "photo")
    private List<String> photos = new ArrayList<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Property> favorites = new HashSet<>();

    private String location;
    private String locationId;

    private String budget;

    @Column(columnDefinition = "date")
    private LocalDate moveInDate;

    @Column(nullable = false)
    private boolean verified;

    @Column(nullable = false)
    private boolean deleted;

    @Column(nullable = false)
    private boolean hidden;


}
