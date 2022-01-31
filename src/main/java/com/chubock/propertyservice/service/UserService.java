package com.chubock.propertyservice.service;

import com.chubock.propertyservice.component.HibernateManager;
import com.chubock.propertyservice.endpoint.UserEndpoint;
import com.chubock.propertyservice.endpoint.dto.UserDTO;
import com.chubock.propertyservice.entity.Property;
import com.chubock.propertyservice.entity.User;
import com.chubock.propertyservice.model.ModelFactory;
import com.chubock.propertyservice.model.UserModel;
import com.chubock.propertyservice.repository.PropertyRepository;
import com.chubock.propertyservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    private final UserEndpoint userEndpoint;

    private final HibernateManager hibernateManager;

    public UserService(UserRepository userRepository, PropertyRepository propertyRepository, UserEndpoint userEndpoint, HibernateManager hibernateManager) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.userEndpoint = userEndpoint;
        this.hibernateManager = hibernateManager;
    }

    @Transactional
    public UserModel getProfile(String id) {
        return getModel(get(id));
    }

    @Transactional
    public UserModel updateProfile(UserModel profile) {

        User user = get(profile.getId())
                .toBuilder()
                .name(profile.getName())
                .allergy(profile.getAllergy())
                .applicable(profile.getApplicable())
                .languages(profile.getLanguages())
                .birthday(profile.getBirthday())
                .budget(profile.getBudget())
                .description(profile.getDescription())
                .descriptor(profile.getDescriptor())
                .drinks(profile.getDrinks())
                .gender(profile.getGender())
                .location(profile.getLocation())
                .locationId(profile.getLocationId())
                .moveInDate(profile.getMoveInDate())
                .imageUrl(profile.getImageUrl())
                .photos(profile.getPhotos())
                .smokes(profile.getSmokes())
                .lastModifiedDate(LocalDateTime.now())
                .hidden(profile.isHidden())
                .build();

        if (profile.getImageUrl() == null && !profile.getPhotos().isEmpty())
            user.setImageUrl(profile.getPhotos().get(0));

        userRepository.save(user);

        hibernateManager.afterCommit(() -> updateUserServiceInfo(user));

        return getModel(user);

    }

    User get(String id) {

        return userRepository.findById(id)
                .orElseGet(() -> {

                    UserDTO userDetails = userEndpoint.getUserDetails(id);

                    User user = User.builder()
                            .id(userDetails.getId())
                            .username(userDetails.getUsername())
                            .name(userDetails.getName())
                            .imageUrl(userDetails.getImageUrl())
                            .build();

                    return userRepository.save(user);

                });
    }

    private void updateUserServiceInfo(User user) {

        UserDTO userInfo = UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .imageUrl(user.getImageUrl())
                .build();

        userEndpoint.updateUserDetails(userInfo);

    }
    private UserModel getModel(User user) {

        UserModel profile = ModelFactory.of(user, UserModel.class);
        profile.setLanguages(new HashSet<>(user.getLanguages()));

        profile.setRenterProfileCompletionRate(getRenterCompletionRate(user));

        propertyRepository.findTopByOwnerAndLivingLandlordIsTrue(user)
                .map(this::getLandlordCompletionRate)
                .ifPresent(profile::setLandlordProfileCompletionRate);

        return profile;
    }


    private int getRenterCompletionRate(User user) {

        int rate = 10;
        if (user.getUsername() != null)
            rate += 5;
        if (user.getName() != null)
            rate += 5;
        if (user.getImageUrl() != null)
            rate += 5;
        if (user.getEmail() != null)
            rate += 5;
        if (user.getBirthday() != null)
            rate += 5;
        if (user.getGender() != null)
            rate += 5;
        if (user.getAllergy() != null)
            rate += 5;
        if (user.getDescriptor() != null)
            rate += 5;
        if (user.getDrinks() != null)
            rate += 5;
        if (user.getSmokes() != null)
            rate += 5;
        if (user.getLanguages() != null && !user.getLanguages().isEmpty())
            rate += 5;
        if (user.getDescription() != null)
            rate += 5;
        if (user.getApplicable() != null && !user.getApplicable().isEmpty())
            rate += 5;
        if (user.getPhotos() != null && !user.getPhotos().isEmpty())
            rate += 5;
        if (user.getLocation() != null)
            rate += 5;
        if (user.getMoveInDate() != null)
            rate += 5;

        return rate;

    }

    private int getLandlordCompletionRate(Property property) {
        int rate = 0;

        if (property.getSquareFootage() != null)
            rate += 5;

        if (property.getBedroomsCount() != null || property.getSharedCookingFacilities())
            rate += 5;

        if (property.getBathroomsCount() != null || property.getSharedBathroom())
            rate += 5;

        if (property.getSmoking() != null)
            rate += 5;

        if (property.getPets() != null)
            rate += 5;

        if (property.getFurnishing() != null)
            rate += 5;

        if (property.getAmenities() != null && !property.getAmenities().isEmpty())
            rate += 5;

        if (property.getSquareFootage() != null)
            rate += 5;

        if (property.getLeaseTerm() != null)
            rate += 5;

        if (property.getMonthlyRent() != null && property.getMonthlyRent() > 0)
            rate += 5;

        if (property.getRentCurrency() != null)
            rate += 5;

        if (property.getAvailabilityDate() != null)
            rate += 5;

        if (property.getSquareFootage() != null)
            rate += 5;

        if (property.getStreetAddress() != null)
            rate += 5;

        if (property.getOptionalAddress() != null)
            rate += 5;

        if (property.getPostalCode() != null)
            rate += 5;

        if (property.getMainImage() != null)
            rate += 5;

        if (property.getImages() != null && !property.getImages().isEmpty())
            rate += 5;

        if (property.getVideos() != null)
            rate += 5;

        if (property.getDescription() != null)
            rate += 5;

        return rate;

    }
}
