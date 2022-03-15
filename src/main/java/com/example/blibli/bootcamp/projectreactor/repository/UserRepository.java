package com.example.blibli.bootcamp.projectreactor.repository;

import com.example.blibli.bootcamp.projectreactor.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findFirstByName(String name);
}
