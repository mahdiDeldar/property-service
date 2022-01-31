package com.chubock.propertyservice.rest;

import com.chubock.propertyservice.entity.PropertyType;
import com.chubock.propertyservice.model.PropertyModel;
import com.chubock.propertyservice.service.PropertyService;
import com.chubock.propertyservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.*;


import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
public class PropertyRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyService propertyService;

    @MockBean
    private UserService userService;

    private static final String PROPERTY_MODEL_JSON = "{" +
            "\"sharedBathroom\": true," +
            "\"type\": \"PRIVATE_ROOM\"," +
            "\"description\": \"Awesome property\"," +
            "\"mainImage\": \"imageURL\"," +
            "\"postalCode\": \"postal-code\"," +
            "\"province\": \"Ontario\"," +
            "\"city\": \"Toronto\"," +
            "\"optionalAddress\": \"optional-address\"," +
            "\"streetAddress\": \"street-address\"," +
            "\"longitude\": \"1.1\"," +
            "\"latitude\": \"1.2\"," +
            "\"monthlyRent\": \"1000\"," +
            "\"leaseTerm\": \"SHORT_TERM\"," +
            "\"squareFootage\": \"100m\"," +
            "\"country\": \"Canada\"," +
            "\"availabilityDate\": \"2021-05-25\"," +
            "\"bathroomsCount\": \"2\"," +
            "\"bedroomsCount\": \"1\"," +
            "\"furnishing\": true," +
            "\"livingLandlord\": true," +
            "\"pets\": true," +
            "\"sharedCookingFacilities\": true," +
            "\"smoking\": true," +
            "\"amenities\": [\"amenity1\", \"amenity2\"]," +
            "\"images\": [\"image1\", \"image2\"]," +
            "\"videos\": [\"video1\"]" +
            "}";

    private static final PropertyModel PROPERTY_MODEL = PropertyModel.builder()
            .sharedBathroom(true)
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
            .squareFootage("100m")
            .country("Canada")
            .availabilityDate(LocalDate.parse("2021-05-25"))
            .bathroomsCount(2)
            .bedroomsCount(3)
            .furnishing(true)
            .pets(true)
            .livingLandlord(true)
            .sharedCookingFacilities(true)
            .smoking(true)
            .images(Arrays.asList("image1", "image2"))
            .videos(Collections.singletonList("video1"))
            .build();

    @Test
    @WithMockUser("foo")
    public void addShouldReturnOK() throws Exception {

        when(propertyService.save(any()))
                .thenReturn(PROPERTY_MODEL.toBuilder().id(UUID.randomUUID().toString()).build());

        ResultActions perform = mockMvc.perform(post("/api/v1/properties")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(PROPERTY_MODEL_JSON)
        );


        perform.andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.livingLandlord", is(PROPERTY_MODEL.getLivingLandlord())))
                .andExpect(jsonPath("$.squareFootage", is(PROPERTY_MODEL.getSquareFootage())))
                .andExpect(jsonPath("$.bedroomsCount", is(PROPERTY_MODEL.getBedroomsCount())))
                .andExpect(jsonPath("$.bathroomsCount", is(PROPERTY_MODEL.getBathroomsCount())))
                .andExpect(jsonPath("$.sharedBathroom", is(PROPERTY_MODEL.getSharedBathroom())))
                .andExpect(jsonPath("$.sharedCookingFacilities", is(PROPERTY_MODEL.getSharedCookingFacilities())))
                .andExpect(jsonPath("$.furnishing", is(PROPERTY_MODEL.getFurnishing())))
                .andExpect(jsonPath("$.smoking", is(PROPERTY_MODEL.getSmoking())))
                .andExpect(jsonPath("$.pets", is(PROPERTY_MODEL.getPets())))
                .andExpect(jsonPath("$.leaseTerm", is(PROPERTY_MODEL.getLeaseTerm())))
                .andExpect(jsonPath("$.availabilityDate", is("2021-05-25")))
                .andExpect(jsonPath("$.monthlyRent", is(PROPERTY_MODEL.getMonthlyRent().intValue())))
                .andExpect(jsonPath("$.country", is(PROPERTY_MODEL.getCountry())))
                .andExpect(jsonPath("$.streetAddress", is(PROPERTY_MODEL.getStreetAddress())))
                .andExpect(jsonPath("$.optionalAddress", is(PROPERTY_MODEL.getOptionalAddress())))
                .andExpect(jsonPath("$.city", is(PROPERTY_MODEL.getCity())))
                .andExpect(jsonPath("$.province", is(PROPERTY_MODEL.getProvince())))
                .andExpect(jsonPath("$.mainImage", is(PROPERTY_MODEL.getMainImage())))
                .andExpect(jsonPath("$.description", is(PROPERTY_MODEL.getDescription())))
                .andExpect(jsonPath("$.type", is(PROPERTY_MODEL.getType().name())))
                .andExpect(jsonPath("$.images").isArray())
                .andExpect(jsonPath("$.images", hasSize(2)))
                .andExpect(jsonPath("$.images", hasItem("image1")))
                .andExpect(jsonPath("$.images", hasItem("image2")))
                .andExpect(jsonPath("$.videos").isArray())
                .andExpect(jsonPath("$.videos", hasSize(1)))
                .andExpect(jsonPath("$.videos", hasItem("video1")))
                .andExpect(jsonPath("$.amenities").isArray())
                .andExpect(jsonPath("$.amenities", hasSize(2)))
                .andExpect(jsonPath("$.amenities", hasItem("amenity1")))
                .andExpect(jsonPath("$.amenities", hasItem("amenity2")));

    }

    @Test
    public void addShouldReturnForbiddenWhenNoCredentials() throws Exception {

        mockMvc.perform(post("/api/v1/properties")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().is(HttpStatus.FORBIDDEN.value()));

    }

    @Test
    @WithMockUser("foo")
    public void addShouldReturnBadRequestWhenPropertyTypeIsNull() throws Exception {

        mockMvc.perform(post("/api/v1/properties")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(PROPERTY_MODEL_JSON.replace("\"type\": \"PRIVATE_ROOM\",", ""))
        ).andExpect(status().is(HttpStatus.NOT_ACCEPTABLE.value()));

    }

    @Test
    @WithMockUser("foo")
    public void addShouldReturnBadRequestWhenAvailabilityDateIsNull() throws Exception {

        mockMvc.perform(post("/api/v1/properties")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(PROPERTY_MODEL_JSON.replace("\"availabilityDate\": \"2021-05-25\",", ""))
        ).andExpect(status().is(HttpStatus.NOT_ACCEPTABLE.value()));

    }

}
