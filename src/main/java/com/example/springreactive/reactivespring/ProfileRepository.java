package com.example.springreactive.reactivespring;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

interface ProfileRepository extends ReactiveMongoRepository<Profile, String> {
}
