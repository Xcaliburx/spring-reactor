package com.example.blibli.bootcamp.projectreactor.config;

import com.example.blibli.bootcamp.projectreactor.controller.ProjectReactorRouterController;
import com.example.blibli.bootcamp.projectreactor.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Autowired
    private ProjectReactorRouterController projectReactorRouterController;

    @Bean
    public RouterFunction<ServerResponse> appContext(){
        return route()
                .GET("/project-reactor-route", serverRequest -> ServerResponse.ok().body(Mono.just("project reactor route"), String.class))
                .GET("/project-reactor-route2", serverRequest -> projectReactorRouterController.helloWorldRouter())
                .build();
    }

    //prefix /user
    @Bean
    public RouterFunction userControllerRouter(UserController userController){
        return route().path("/user", builder -> builder
                .GET("/{name}", userController::findByName)
                .POST("", userController::save)
                .build())
                .build();
    }
}
