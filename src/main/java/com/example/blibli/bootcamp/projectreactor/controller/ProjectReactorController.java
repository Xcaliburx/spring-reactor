package com.example.blibli.bootcamp.projectreactor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/project-reactor")
public class ProjectReactorController {

    @GetMapping
    public Mono<String> helloWorld() {
        return Mono.just("Hello World!!");
    }
}
