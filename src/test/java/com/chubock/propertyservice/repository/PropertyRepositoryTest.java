package com.chubock.propertyservice.repository;

import com.chubock.propertyservice.entity.Property;
import com.chubock.propertyservice.entity.PropertyType;
import com.chubock.propertyservice.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PropertyRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PropertyRepository propertyRepository;

    @Test
    public void shouldReturnPropertyWhenPropertySaved() {

        User user = User.builder()
                .build();

        entityManager.persist(user);
        entityManager.flush();


        Property property = Property.builder()
                .sharedBathroom(true)
                .owner(user)
                .type(PropertyType.PRIVATE_ROOM)
                .description("Awesome property")
                .mainImage("imageURL")
                .postalCode("postal-code")
                .province("Ontario")
                .city("Toronto")
                .optionalAddress("optional-address")
                .streetAddress("street-address")
                .longitude(1.1)
                .latitude(1.2)
                .monthlyRent(1000L)
                .leaseTerm("SHORT_TERM")
                .amenities(new HashSet<>(Arrays.asList("amenity1", "amenity2")))
                .lastModifiedDate(LocalDateTime.now())
                .squareFootage("100m")
                .country("Canada")
                .availabilityDate(LocalDate.now().plusDays(2))
                .bathroomsCount(2)
                .bedroomsCount(3)
                .furnishing(true)
                .pets(true)
                .livingLandlord(true)
                .sharedCookingFacilities(true)
                .smoking(true)
                .images(Arrays.asList("image1", "image2"))
                .videos(Collections.singletonList("video1"))
                .verified(true)
                .build();

        propertyRepository.save(property);

        Optional<Property> read = propertyRepository.findById(property.getId());

        assertThat(read).isPresent();

        Property saved = read.get();

        assertThat(saved.getSquareFootage()).isEqualTo(property.getSquareFootage());
        assertThat(saved.getSharedCookingFacilities()).isEqualTo(property.getSharedCookingFacilities());
        assertThat(saved.getSharedBathroom()).isEqualTo(property.getSharedBathroom());
        assertThat(saved.getPostalCode()).isEqualTo(property.getPostalCode());
        assertThat(saved.getStreetAddress()).isEqualTo(property.getStreetAddress());
        assertThat(saved.getOptionalAddress()).isEqualTo(property.getOptionalAddress());
        assertThat(saved.getMonthlyRent()).isEqualTo(property.getMonthlyRent());
        assertThat(saved.getMainImage()).isEqualTo(property.getMainImage());
        assertThat(saved.getLivingLandlord()).isEqualTo(property.getLivingLandlord());
        assertThat(saved.getLeaseTerm()).isEqualTo(property.getLeaseTerm());
        assertThat(saved.getLongitude()).isEqualTo(property.getLongitude());
        assertThat(saved.getLatitude()).isEqualTo(property.getLatitude());
        assertThat(saved.getPets()).isEqualTo(property.getPets());
        assertThat(saved.getSmoking()).isEqualTo(property.getSmoking());
        assertThat(saved.getFurnishing()).isEqualTo(property.getFurnishing());
        assertThat(saved.getDescription()).isEqualTo(property.getDescription());
        assertThat(saved.getCountry()).isEqualTo(property.getCountry());
        assertThat(saved.getProvince()).isEqualTo(property.getProvince());
        assertThat(saved.getCity()).isEqualTo(property.getCity());
        assertThat(saved.getBedroomsCount()).isEqualTo(property.getBedroomsCount());
        assertThat(saved.getBathroomsCount()).isEqualTo(property.getBathroomsCount());
        assertThat(saved.getAvailabilityDate()).isEqualTo(property.getAvailabilityDate());
        assertThat(saved.getImages()).containsExactlyElementsOf(property.getImages());
        assertThat(saved.getVideos()).containsExactlyElementsOf(property.getVideos());
        assertThat(saved.getVideos()).containsExactlyElementsOf(property.getVideos());

    }


}
