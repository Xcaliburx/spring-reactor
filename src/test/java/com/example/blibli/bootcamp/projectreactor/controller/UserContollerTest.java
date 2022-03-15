package com.example.blibli.bootcamp.projectreactor.controller;

import com.example.blibli.bootcamp.projectreactor.model.User;
import com.example.blibli.bootcamp.projectreactor.model.request.UserRequest;
import com.example.blibli.bootcamp.projectreactor.model.response.UserResponse;
import com.example.blibli.bootcamp.projectreactor.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserContollerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown(){
        this.userRepository.deleteAll().subscribe();
    }

    @Test
    public void findByName_test(){
        User user = User.builder()
                .name("Adi")
                .score(10)
                .build();

        this.userRepository.save(user).subscribe();

        UserResponse response = webTestClient.get().uri("/user/Adi")
                .exchange()
                .expectBody(UserResponse.class)
                .returnResult()
                .getResponseBody();
        assertEquals("Adi", response.getName());
        assertEquals(10, response.getScore());
    }

    @Test
    public void save_test(){
        UserRequest request = UserRequest.builder()
                .name("Adi")
                .score(10)
                .build();

        UserResponse response = webTestClient.post().uri("/user")
                .body(Mono.just(request), UserRequest.class)
                .exchange()
                .expectBody(UserResponse.class)
                .returnResult()
                .getResponseBody();

        assertEquals("Adi", response.getName());
        assertEquals(10, response.getScore());

        User user = userRepository.findFirstByName("Adi").block();
        assertEquals("Adi", user.getName());
        assertEquals(10, user.getScore());
    }
}
