package com.example.springreactive.reactivespring.config;

import com.example.springreactive.reactivespring.Profile;
import org.springframework.context.ApplicationEvent;

public class ProfileCreatedEvent extends ApplicationEvent {

    public ProfileCreatedEvent(Profile source) {
        super(source);
    }
}