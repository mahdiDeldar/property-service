package com.chubock.propertyservice.service;

import com.chubock.propertyservice.entity.Property;
import com.chubock.propertyservice.entity.User;
import com.chubock.propertyservice.exception.PropertyNotFoundException;
import com.chubock.propertyservice.exception.UserNotFoundException;
import com.chubock.propertyservice.model.ModelFactory;
import com.chubock.propertyservice.model.PropertyModel;
import com.chubock.propertyservice.model.PropertySearchModel;
import com.chubock.propertyservice.model.UserModel;
import com.chubock.propertyservice.repository.PropertyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    private final UserService userService;

    public PropertyService(PropertyRepository propertyRepository, UserService userService) {
        this.propertyRepository = propertyRepository;
        this.userService = userService;
    }

    @Transactional
    public Page<PropertyModel> getProperties(String userId, PropertySearchModel model, Pageable pageable) {

        User user = userService.get(userId);

        return propertyRepository.findAll(model, pageable)
                .map(property -> {
                    PropertyModel propertyModel = ModelFactory.of(property, PropertyModel.class);

                    propertyModel.setOwner(ModelFactory.of(property.getOwner(), UserModel.class));

                    propertyModel.setFavourite(user.getFavorites().contains(property));
                    return propertyModel;

                });

    }

    @Transactional(readOnly = true)
    public UserModel getPropertyOwner(String id) {

        Property property = getExistingProperty(id);

        return ModelFactory.of(property.getOwner(), UserModel.class);

    }

    @Transactional(readOnly = true)
    public PropertyModel getProperty(String id) {
        return getProperty(null, id);
    }

    @Transactional
    public PropertyModel getProperty(String userId, String id) {

        User user = userId == null ? null : userService.get(userId);

        Property property = propertyRepository.findByIdWithDetails(id)
                .filter(Property::isFinalized)
                .filter(entity -> !entity.isDeleted())
                .orElseThrow(PropertyNotFoundException::new);

        PropertyModel model = ModelFactory.of(property, PropertyModel.class);

        model.setOwner(ModelFactory.of(property.getOwner(), UserModel.class));

        model.setVideos(new ArrayList<>(property.getVideos()));

        if (user != null)
            model.setFavourite(user.getFavorites().contains(property));

        return model;

    }

    @Transactional
    public void addToUserFavorites(String userId, String propertyId) {

        User user = userService.get(userId);
        Property property = getExistingProperty(propertyId);

        user.getFavorites().add(property);

    }

    @Transactional
    public void removeFromUserFavorites(String userId, String propertyId) {

        User user = userService.get(userId);
        Property property = getExistingProperty(propertyId);

        user.getFavorites().remove(property);

    }

    @Transactional
    public List<PropertyModel> getUserFavoriteProperties(String userId) {
        User owner = getUser(userId);
        return owner.getFavorites()
                .stream()
                .filter(property -> !property.isDeleted())
                .map(property -> ModelFactory.of(property, PropertyModel.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PropertyModel> getUserProperties(String userId) {
        User owner = getUser(userId);
        return propertyRepository.findByOwnerAndDeletedIsFalseOrderByCreateDateDesc(owner)
                .stream()
                .map(this::getOwnerPropertyModel)
                .collect(Collectors.toList());
    }

    @Transactional
    public PropertyModel save(PropertyModel model) {

        User owner = getUser(model.getOwner());

        Property property = Optional.of(model)
                .map(PropertyModel::getId)
                .flatMap(propertyRepository::findById)
                .filter(entity -> !entity.isDeleted())
                .map(Property::toBuilder)
                .orElseGet(Property::builder)
                .owner(owner)
                .livingLandlord(model.getLivingLandlord())
                .squareFootage(model.getSquareFootage())
                .bedroomsCount(model.getBedroomsCount())
                .bathroomsCount(model.getBathroomsCount())
                .sharedBathroom(model.getSharedBathroom())
                .sharedCookingFacilities(model.getSharedCookingFacilities())
                .furnishing(model.getFurnishing())
                .smoking(model.getSmoking())
                .pets(model.getPets())
                .leaseTerm(model.getLeaseTerm())
                .monthlyRent(model.getMonthlyRent())
                .rentCurrency(model.getRentCurrency())
                .availabilityDate(model.getAvailabilityDate())
                .latitude(model.getLatitude())
                .longitude(model.getLongitude())
                .country(model.getCountry())
                .countryId(model.getCountryId())
                .streetAddress(model.getStreetAddress())
                .streetAddressId(model.getStreetAddressId())
                .optionalAddress(model.getOptionalAddress())
                .city(model.getCity())
                .cityId(model.getCityId())
                .province(model.getProvince())
                .provinceId(model.getProvinceId())
                .postalCode(model.getPostalCode())
                .mainImage(model.getMainImage())
                .description(model.getDescription())
                .type(model.getType())
                .finalized(model.getFinalized() == null ? false : model.getFinalized())
                .build();

        if (property.isFinalized() && (property.getLatitude() == null || property.getLongitude() == null))
            throw new IllegalArgumentException("latitude and longitude must be specified");

        property.getAmenities().clear();
        property.getAmenities().addAll(model.getAmenities());

        property.getImages().clear();
        property.getImages().addAll(model.getImages());

        property.getVideos().clear();
        property.getVideos().addAll(model.getVideos());

        propertyRepository.save(property);

        PropertyModel ret = ModelFactory.of(property, PropertyModel.class);
        ret.setOwner(ModelFactory.of(owner, UserModel.class));
        ret.setVideos(property.getVideos());

        return ret;

    }

    @Transactional
    public void delete(String id) {

        Property property = getExistingProperty(id);

        property.setDeleted(true);
    }

    private Property getExistingProperty(String id) {
        return propertyRepository.findById(id)
                .filter(property -> !property.isDeleted())
                .orElseThrow(PropertyNotFoundException::new);
    }

    private User getUser(UserModel model) {

        if (model == null)
            throw new UserNotFoundException();

        return getUser(model.getId());

    }

    private User getUser(String id) {

        return Optional.ofNullable(id)
                .map(userService::get)
                .orElseThrow(UserNotFoundException::new);

    }

    private PropertyModel getOwnerPropertyModel(Property property) {
        PropertyModel model = ModelFactory.of(property, PropertyModel.class);

        User user = property.getOwner();
        UserModel owner = ModelFactory.of(user, UserModel.class);
        owner.setLanguages(new HashSet<>(user.getLanguages()));

        model.setOwner(owner);

        return model;
    }

}
