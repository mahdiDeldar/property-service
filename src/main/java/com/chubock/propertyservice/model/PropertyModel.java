package com.chubock.propertyservice.model;

import com.chubock.propertyservice.entity.Property;
import com.chubock.propertyservice.entity.PropertyType;
import com.chubock.propertyservice.enumeration.Currency;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class PropertyModel extends AbstractModel<Property> {

    private UserModel owner;

    private Boolean livingLandlord;
    private String squareFootage;

    @PositiveOrZero(message = "bedrooms count must be greater equal than 0")
    private Integer bedroomsCount;

    @PositiveOrZero(message = "bathrooms count must be greater equal than 0")
    private Integer bathroomsCount;

    private Boolean sharedBathroom;
    private Boolean sharedCookingFacilities;

    private Boolean furnishing;
    private Boolean smoking;
    private Boolean pets;

    @Builder.Default
    private Set<String> amenities = new HashSet<>();

    private String leaseTerm;

    @PositiveOrZero(message = "monthly rent must be greater equal than 0")
    private Long monthlyRent;

    private String rentCurrency;

    private LocalDate availabilityDate;

    private Double latitude;
    private Double longitude;

    private String country;
    private String streetAddress;
    private String optionalAddress;
    private String city;
    private String province;
    private String postalCode;

    private String countryId;
    private String provinceId;
    private String cityId;
    private String streetAddressId;

    private String mainImage;

    @Builder.Default
    private List<String> images = new ArrayList<>();

    @Builder.Default
    private List<String> videos = new ArrayList<>();

    @Size(max = 300, message = "description must be less than 300 characters")
    private String description;

    @NotNull(message = "type can't be empty")
    private PropertyType type;

    private Boolean finalized;
    private Boolean verified;

    private Boolean favourite;

    public String getUniversalLink() {
        return "https://beta.housemate.space/properties/" + getId();
    }

    @Override
    public void fill(Property entity) {
        super.fill(entity);
        setLivingLandlord(entity.getLivingLandlord());
        setSquareFootage(entity.getSquareFootage());
        setAvailabilityDate(entity.getAvailabilityDate());
        setType(entity.getType());
        setBathroomsCount(entity.getBathroomsCount());
        setBedroomsCount(entity.getBedroomsCount());
        setCountry(entity.getCountry());
        setProvince(entity.getProvince());
        setCity(entity.getCity());
        setOptionalAddress(entity.getOptionalAddress());
        setStreetAddress(entity.getStreetAddress());
        setPostalCode(entity.getPostalCode());
        setLatitude(entity.getLatitude());
        setLongitude(entity.getLongitude());
        setCountryId(entity.getCountryId());
        setProvinceId(entity.getProvinceId());
        setCityId(entity.getCityId());
        setStreetAddressId(entity.getStreetAddressId());
        setDescription(entity.getDescription());
        setFurnishing(entity.getFurnishing());
        setSmoking(entity.getSmoking());
        setPets(entity.getPets());
        setLeaseTerm(entity.getLeaseTerm());
        setRentCurrency(entity.getRentCurrency().toString());
        setMonthlyRent(entity.getMonthlyRent());
        setMainImage(entity.getMainImage());
        setSharedBathroom(entity.getSharedBathroom());
        setSharedCookingFacilities(entity.getSharedCookingFacilities());
        setFinalized(entity.isFinalized());
        setVerified(entity.isVerified());
        setAmenities(new HashSet<>(entity.getAmenities()));
        setImages(new ArrayList<>(entity.getImages()));
    }

}
