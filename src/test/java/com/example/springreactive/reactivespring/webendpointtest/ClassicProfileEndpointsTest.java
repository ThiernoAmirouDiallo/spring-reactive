package com.example.springreactive.reactivespring.webendpointtest;

import com.example.springreactive.reactivespring.service.ProfileService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

//@Log4j2
@Import({ProfileRestController.class, ProfileService.class})
@ActiveProfiles("classic")
public class ClassicProfileEndpointsTest extends AbstractBaseProfileEndpoints {
    private static final Logger log = LoggerFactory.getLogger(ClassicProfileEndpointsTest.class);

    @BeforeAll
    static void before() {
        log.info("running non-classic tests");
    }

    ClassicProfileEndpointsTest(@Autowired WebTestClient client) {
        super(client);
    }
}