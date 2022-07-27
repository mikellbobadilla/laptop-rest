package com.sting.ordenadores.controllers;

import com.sting.ordenadores.entities.Laptop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

  // Attributes
  private TestRestTemplate testRestTemplate;

  @Autowired
  private RestTemplateBuilder restTemplateBuilder;

  @LocalServerPort
  private int port;

  private final String url = "/api/laptops";

  /** Setting environment to test
   *
   */
  @BeforeEach
  void setUp(){
    restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
    testRestTemplate = new TestRestTemplate(restTemplateBuilder);
  }


  @Test
  void findAll() {
    ResponseEntity<Laptop[]> res = testRestTemplate.getForEntity(url, Laptop[].class);

    List<Laptop> lap = Arrays.asList(Objects.requireNonNull(res.getBody()));
    assertEquals(HttpStatus.OK, res.getStatusCode());
  }

  @Test
  void findOneById() {
    ResponseEntity<Laptop> res = testRestTemplate.getForEntity(url + "/1", Laptop.class);
    assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    assertNull(res.getBody());
  }

  @Test
  void create() {
    // Headers
    HttpHeaders header = new HttpHeaders();
    header.setContentType(MediaType.APPLICATION_JSON);
    header.setAccept(List.of(MediaType.APPLICATION_JSON));

    String json = """
            {
              "model": "Lap Test",
              "price": 40.00,
              "specs": "4 GB RAM"
            }
            """;
    HttpEntity<String> request = new HttpEntity<>(json, header);
    ResponseEntity<Laptop> res = testRestTemplate.exchange(url, HttpMethod.POST, request, Laptop.class);

    Laptop result = res.getBody();
    assert result != null;
    assertEquals(1L, result.getId());
    assertEquals("Lap Test", result.getModel());
  }

  @Test
  void update() {



  }

  @Test
  void delete() {
  }

  @Test
  void deleteAll() {
  }
}