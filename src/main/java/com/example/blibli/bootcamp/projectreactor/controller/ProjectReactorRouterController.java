package com.example.blibli.bootcamp.projectreactor.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProjectReactorRouterController {

    public Mono<ServerResponse> helloWorldRouter(){
        return ServerResponse.ok()
                .body(Mono.just("Hello world router!!"), String.class);
    }
}
