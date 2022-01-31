package com.chubock.propertyservice.rest;

import com.chubock.propertyservice.model.PropertyModel;
import com.chubock.propertyservice.model.UserModel;
import com.chubock.propertyservice.service.PropertyService;
import com.chubock.propertyservice.service.UserService;
import com.chubock.propertyservice.util.JacksonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
public class UserRestControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PropertyService propertyService;

    @Test
    @WithMockUser(username = "foo")
    public void getProfileShouldReturnProfile() throws Exception {

        UserModel userModel = UserModel.builder()
                .id("foo")
                .name("bar")
                .build();

        when(userService.getProfile("foo"))
                .thenReturn(userModel);

        mockMvc.perform(get("/api/v1/users/me"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.id", is("foo")))
                .andExpect(jsonPath("$.name", is("bar")));

    }

    @Test
    public void getProfileShouldReturnForbiddenWhenNoCredentials() throws Exception {

        mockMvc.perform(get("/api/v1/users/me"))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));

    }

    @Test
    @WithMockUser(username = "foo")
    public void updateProfileShouldReturnOk() throws Exception {

        UserModel userModel = UserModel.builder()
                .id("foo")
                .name("bar")
                .build();

        when(userService.updateProfile(userModel))
                .thenReturn(userModel);

        mockMvc.perform(put("/api/v1/users/me").contentType(MediaType.APPLICATION_JSON_VALUE).content(JacksonUtil.serialize(userModel)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.id", is("foo")))
                .andExpect(jsonPath("$.name", is("bar")));

    }

    @Test
    public void updateProfileShouldReturnForbiddenWhenNoCredentials() throws Exception {

        UserModel userModel = UserModel.builder()
                .id("foo")
                .name("bar")
                .build();

        when(userService.updateProfile(userModel))
                .thenReturn(userModel);

        mockMvc.perform(put("/api/v1/users/me").contentType(MediaType.APPLICATION_JSON_VALUE).content(JacksonUtil.serialize(userModel)))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));

    }

    @Test
    @WithMockUser("foo")
    public void addToFavoritesShouldReturnOk() throws Exception {

        mockMvc.perform(put("/api/v1/users/me/favorites/bar"))
                .andExpect(status().is(HttpStatus.OK.value()));

    }

    @Test
    public void addToFavoritesShouldReturnForbiddenWhenNoCredentials() throws Exception {

        mockMvc.perform(put("/api/v1/users/me/favorites/bar"))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));

    }

    @Test
    @WithMockUser("foo")
    public void removeFavoritesShouldReturnOk() throws Exception {

        mockMvc.perform(delete("/api/v1/users/me/favorites/bar"))
                .andExpect(status().is(HttpStatus.OK.value()));

    }

    @Test
    public void removeFavoritesShouldReturnForbiddenWhenNoCredentials() throws Exception {

        mockMvc.perform(delete("/api/v1/users/me/favorites/bar"))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));

    }

    @Test
    @WithMockUser("foo")
    public void getFavouritePropertiesShouldReturnOk() throws Exception {

        List<PropertyModel> favorites = new ArrayList<>();

        PropertyModel fav1 = PropertyModel.builder()
                .id("fav1")
                .build();

        PropertyModel fav2 = PropertyModel.builder()
                .id("fav2")
                .build();

        favorites.add(fav1);
        favorites.add(fav2);

        when(propertyService.getUserFavoriteProperties("foo"))
                .thenReturn(favorites);

        mockMvc.perform(get("/api/v1/users/me/favorites"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("fav1")))
                .andExpect(jsonPath("$[1].id", is("fav2")));

    }

    @Test
    @WithMockUser("foo")
    public void getFavouritePropertiesShouldReturnEmptyArrayWhenUserHasNoFavorites() throws Exception {

        when(propertyService.getUserFavoriteProperties("foo"))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/users/me/favorites"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$", empty()));

    }

    @Test
    public void getFavouritePropertiesShouldReturnOkWhenNoCredentials() throws Exception {

        mockMvc.perform(delete("/api/v1/users/me/favorites"))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));

    }

}
