package com.example.springreactive.reactivespring.service;

import com.example.springreactive.reactivespring.Profile;
import com.example.springreactive.reactivespring.ProfileRepository;
import java.util.UUID;
import java.util.function.Predicate;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Log4j2
@DataMongoTest // <1>
@Import(ProfileService.class) // <2>
class ProfileServiceTest {

    private final ProfileService service;
    private final ProfileRepository repository;

    public ProfileServiceTest(@Autowired ProfileService service, // <3>
            @Autowired ProfileRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @Test // <4>
    void getAll() {
        Flux<Profile> saved = repository.saveAll(Flux.just(new Profile(null, "Josh"), new Profile(null, "Matt"), new Profile(null, "Jane")));

        Flux<Profile> composite = service.all().thenMany(saved);

        Predicate<Profile> match = profile -> saved.any(saveItem -> saveItem.equals(profile)).block();

        StepVerifier
                .create(composite) // <5>
                .expectNextMatches(match)  // <6>
                .expectNextMatches(match)
                .expectNextMatches(match)
                .verifyComplete(); // <7>
    }

    @Test
    void save() {
        Mono<Profile> profileMono = this.service.create("email@email.com");
        StepVerifier
                .create(profileMono)
                .expectNextMatches(saved -> StringUtils.hasText(saved.getId()))
                .verifyComplete();
    }

    @Test
    void delete() {
        String test = "test";
        Mono<Profile> deleted = this.service
                .create(test)
                .flatMap(saved -> this.service.delete(saved.getId()));
        StepVerifier
                .create(deleted)
                .expectNextMatches(profile -> profile.getEmail().equalsIgnoreCase(test))
                .verifyComplete();
    }

    @Test
    void update() throws Exception {
        Mono<Profile> saved = this.service
                .create("test")
                .flatMap(p -> this.service.update(p.getId(), "test1"));
        StepVerifier
                .create(saved)
                .expectNextMatches(p -> p.getEmail().equalsIgnoreCase("test1"))
                .verifyComplete();
    }

    @Test
    void getById() {
        String test = UUID.randomUUID().toString();
        Mono<Profile> deleted = this.service
                .create(test)
                .flatMap(saved -> this.service.get(saved.getId()));
        StepVerifier
                .create(deleted)
                .expectNextMatches(profile -> StringUtils.hasText(profile.getId()) && test.equalsIgnoreCase(profile.getEmail()))
                .verifyComplete();
    }
}