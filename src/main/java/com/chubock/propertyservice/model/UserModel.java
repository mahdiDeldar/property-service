package com.chubock.propertyservice.model;

import com.chubock.propertyservice.entity.Answer;
import com.chubock.propertyservice.entity.Gender;
import com.chubock.propertyservice.entity.Property;
import com.chubock.propertyservice.entity.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class UserModel extends AbstractModel<User> {

    private String username;
    private String name;
    private String imageUrl;

    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "email address is not valid")
    private String email;
    private LocalDate birthday;
    private Gender gender;

    private String allergy;

    private String descriptor;

    private Answer drinks;

    private Answer smokes;

    @Builder.Default
    private Set<String> languages = new HashSet<>();

    private String description;

    @Builder.Default
    private Set<String> applicable = new HashSet<>();

    @Builder.Default
    private List<String> photos = new ArrayList<>();

    @Builder.Default
    private Set<Property> favorites = new HashSet<>();

    private String location;
    private String locationId;

    private String budget;

    private LocalDate moveInDate;

    private boolean hidden;

    private boolean verified;

    private int renterProfileCompletionRate;
    private int landlordProfileCompletionRate;

    public UserModel(String id) {
        super(id);
    }

    public String getUniversalLink() {
        return "https://test.elegant-designs.net/housemates/" + getId();
    }

    public void fill(User entity) {
        super.fill(entity);
        setUsername(entity.getUsername());
        setName(entity.getName());
        setImageUrl(entity.getImageUrl());
        setEmail(entity.getEmail());
        setBirthday(entity.getBirthday());
        setGender(entity.getGender());
        setAllergy(entity.getAllergy());
        setDescriptor(entity.getDescriptor());
        setDescription(entity.getDescription());
        setDrinks(entity.getDrinks());
        setSmokes(entity.getSmokes());
        setLocation(entity.getLocation());
        setBudget(entity.getBudget());
        setMoveInDate(entity.getMoveInDate());
        setLocationId(entity.getLocationId());
        setHidden(entity.isHidden());
        setVerified(entity.isVerified());
        setPhotos(new ArrayList<>(entity.getPhotos()));
        setApplicable(new HashSet<>(entity.getApplicable()));
    }

    public static UserModel of (User user) {
        UserModel model = new UserModel();
        model.fill(user);
        return model;
    }
}
