package com.example.blibli.bootcamp.projectreactor.controller;

import com.example.blibli.bootcamp.projectreactor.model.User;
import com.example.blibli.bootcamp.projectreactor.model.request.UserRequest;
import com.example.blibli.bootcamp.projectreactor.model.response.UserResponse;
import com.example.blibli.bootcamp.projectreactor.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    public Mono<ServerResponse> findByName(ServerRequest serverRequest){
        String name = serverRequest.pathVariable("name");
        Mono<User> user = this.userRepository.findFirstByName(name);

        return ServerResponse.ok().body(user.map(this::toUserResponse), UserResponse.class);
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest){
        Mono<UserRequest> request = serverRequest.bodyToMono(UserRequest.class);
        Mono<User> user = request.map(this::toUser)
                //o -> User
                //Map -> Mono<Mono<User>>
                //flatMap -> Mono<User>
                .flatMap(this.userRepository::save);

        return ServerResponse.ok().body(user.map(this::toUserResponse), UserResponse.class);
    }

    private User toUser(UserRequest user){
        return User.builder()
                .name(user.getName())
                .score(user.getScore())
                .build();
    }

    private UserResponse toUserResponse(User user){
        return UserResponse.builder()
                .name(user.getName())
                .score(user.getScore())
                .build();
    }
}
