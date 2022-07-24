package com.chubock.propertyservice.service;

import com.chubock.propertyservice.component.HibernateManager;
import com.chubock.propertyservice.endpoint.UserEndpoint;
import com.chubock.propertyservice.endpoint.dto.UserDTO;
import com.chubock.propertyservice.entity.Property;
import com.chubock.propertyservice.entity.Report;
import com.chubock.propertyservice.entity.User;
import com.chubock.propertyservice.exception.PropertyNotFoundException;
import com.chubock.propertyservice.exception.UserNotFoundException;
import com.chubock.propertyservice.model.ModelFactory;
import com.chubock.propertyservice.model.UserModel;
import com.chubock.propertyservice.repository.PropertyRepository;
import com.chubock.propertyservice.repository.ReportRepository;
import com.chubock.propertyservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final ReportRepository reportRepository;

    private final UserEndpoint userEndpoint;

    private final HibernateManager hibernateManager;

    public UserService(ReportRepository reportRepository, UserRepository userRepository, PropertyRepository propertyRepository, UserEndpoint userEndpoint, HibernateManager hibernateManager) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.userEndpoint = userEndpoint;
        this.hibernateManager = hibernateManager;
        this.reportRepository = reportRepository;
    }

    @Transactional
    public UserModel getProfile(String id) {
        return getModel(get(id));
    }

    @Transactional()
    public ResponseEntity<Boolean> deleteUser(String id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.OK);
        }
        List<Property> propertyList = propertyRepository.findAllByOwner(user.get());
        if (!propertyList.isEmpty()) {
            propertyList.forEach(p -> {
                propertyRepository.delete(p);
            });
        }
        List<Report> reportList = reportRepository.findReportsByReporter(user.get());
        if (!reportList.isEmpty()) {
            reportList.forEach(r -> {
                reportRepository.delete(r);
            });
        }
        userRepository.delete(user.get());
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

    @Transactional
    public UserModel updateProfile(UserModel profile) {

        User user = get(profile.getId());
        if (profile.getName() != null)
            user.setName(profile.getName());
        if (profile.getAllergy() != null)
            user.setAllergy(profile.getAllergy());
        if (profile.getApplicable() != null)
            user.setApplicable(profile.getApplicable());
        if (profile.getLanguages() != null)
            user.setLanguages(profile.getLanguages());
        if (profile.getBirthday() != null)
            user.setBirthday(profile.getBirthday());
        if (profile.getBudget() != null)
            user.setBudget(profile.getBudget());
        if (profile.getDescription() != null)
            user.setDescription(profile.getDescription());
        if (profile.getDescriptor() != null)
            user.setDescriptor(profile.getDescriptor());
        if (profile.getDrinks() != null)
            user.setDrinks(profile.getDrinks());
        if (profile.getGender() != null)
            user.setGender(profile.getGender());
        if (profile.getLocation() != null)
            user.setLocation(profile.getLocation());
        if (profile.getLocationId() != null)
            user.setLocationId(profile.getLocationId());
        if (profile.getMoveInDate() != null)
            user.setMoveInDate(profile.getMoveInDate());
        if (profile.getImageUrl() != null)
            user.setImageUrl(profile.getImageUrl());
        if (profile.getPhotos() != null)
            user.setPhotos(profile.getPhotos());
        if (profile.getSmokes() != null)
            user.setSmokes(profile.getSmokes());
        if (profile.isHidden() != user.isHidden())
            user.setHidden(profile.isHidden());

        user.setLastModifiedDate(LocalDateTime.now());

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
