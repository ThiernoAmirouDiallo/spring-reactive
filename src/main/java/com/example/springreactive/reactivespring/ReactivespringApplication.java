package com.example.springreactive.reactivespring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import org.springframework.data.mongodb.core.mapping.Document;

@SpringBootApplication
public class ReactivespringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactivespringApplication.class, args);
    }
}


@Component
@RequiredArgsConstructor
class SampleDataInitializer {

    private final ReservationRepository reservationRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        var savedReservations = Flux
                .just("thierno", "alpha", "alex", "shophie", "caroline", "ahmad", "ibrahim",
                        "mohamed")
                .map(name -> new Reservation(null, name))
                .flatMap(reservationRepository::save);

        reservationRepository
                .deleteAll()
                .thenMany(savedReservations)
                .thenMany(reservationRepository.findAll())
                .subscribe(System.out::println);
    }
}

interface ReservationRepository extends ReactiveCrudRepository<Reservation, String> {

}

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
class Reservation {

    @Id
    private String id;
    private String name;

}