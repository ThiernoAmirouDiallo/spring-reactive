package com.example.springreactive.reactivespring.webendpointtest;

import com.example.springreactive.reactivespring.config.ProfileEndpointConfiguration;
import com.example.springreactive.reactivespring.config.ProfileHandler;
import com.example.springreactive.reactivespring.service.ProfileService;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

//@Log4j2
@ActiveProfiles("default")
@Import({ProfileEndpointConfiguration.class,
        ProfileHandler.class, ProfileService.class})
public class FunctionalProfileEndpointsTest extends AbstractBaseProfileEndpoints {

    private static final Logger log = LoggerFactory.getLogger(FunctionalProfileEndpointsTest.class);

    @BeforeAll
    static void before() {
        log.info("running default " + ProfileRestController.class.getName() + " tests");
    }

    FunctionalProfileEndpointsTest(@Autowired WebTestClient client) {
        super(client);
    }
}
