package com.chubock.propertyservice.repository;

import com.chubock.propertyservice.entity.Answer;
import com.chubock.propertyservice.entity.Gender;
import com.chubock.propertyservice.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturnUserWhenUserSaved() {

        User user = User.builder()
                .name("foo")
                .lastModifiedDate(LocalDateTime.now())
                .smokes(Answer.NO)
                .photos(Arrays.asList("pic1", "pic2"))
                .moveInDate(LocalDate.now().plusDays(1))
                .location("Toronto")
                .gender(Gender.MALE)
                .drinks(Answer.YES)
                .allergy("Jam")
                .description("I'm awesome")
                .budget("300$-400$")
                .birthday(LocalDate.now().minusYears(30))
                .languages(new HashSet<>(Arrays.asList("english", "persian")))
                .descriptor("student")
                .imageUrl("imageURL1")
                .verified(true)
                .build();

        userRepository.save(user);

        Optional<User> read = userRepository.findById(user.getId());

        assertThat(read).isPresent();

        User saved = read.get();

        assertThat(saved.getName()).isEqualTo(user.getName());
        assertThat(saved.getLastModifiedDate()).isEqualTo(user.getLastModifiedDate());
        assertThat(saved.getSmokes()).isEqualTo(user.getSmokes());
        assertThat(saved.getPhotos()).containsExactlyElementsOf(user.getPhotos());
        assertThat(saved.getMoveInDate()).isEqualTo(user.getMoveInDate());
        assertThat(saved.getLocation()).isEqualTo(user.getLocation());
        assertThat(saved.getGender()).isEqualTo(user.getGender());
        assertThat(saved.getDrinks()).isEqualTo(user.getDrinks());
        assertThat(saved.getAllergy()).isEqualTo(user.getAllergy());
        assertThat(saved.getDescriptor()).isEqualTo(user.getDescriptor());
        assertThat(saved.getDescription()).isEqualTo(user.getDescription());
        assertThat(saved.getBudget()).isEqualTo(user.getBudget());
        assertThat(saved.getBirthday()).isEqualTo(user.getBirthday());
        assertThat(saved.getLanguages()).containsExactlyElementsOf(user.getLanguages());
        assertThat(saved.getImageUrl()).isEqualTo(user.getImageUrl());
        assertThat(saved.isVerified()).isEqualTo(user.isVerified());


    }

}
