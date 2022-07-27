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

  /** Allows to send JSON
   *
   */
  private static HttpEntity<String> request(String json){
    // Headers
    HttpHeaders header = new HttpHeaders();
    header.setContentType(MediaType.APPLICATION_JSON);
    header.setAccept(List.of(MediaType.APPLICATION_JSON));

    return new HttpEntity<>(json, header);
  }

  /** Send Request
   *
   */
    private void sendRequest(){
      String json = """
            {
              "model": "Lap Test",
              "price": 40.00,
              "specs": "4 GB RAM"
            }
            """;
      ResponseEntity<Laptop> res = testRestTemplate.exchange(url, HttpMethod.POST, request(json), Laptop.class);
    }


  /** Setting environment to test
   *
   */
  @BeforeEach
  void setUp(){
    restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
    testRestTemplate = new TestRestTemplate(restTemplateBuilder);
  }


  /** Testing OK
   *
   */
  @Test
  void findAll() {

    ResponseEntity<Laptop[]> res = testRestTemplate.getForEntity(url, Laptop[].class);


    List<Laptop> lap = Arrays.asList(Objects.requireNonNull(res.getBody()));
    assertEquals(HttpStatus.OK, res.getStatusCode());
  }


  /** Testing NOT_FOUND GET
   *
   */
  @Test
  void findOneById_NOT_FOUND() {
    ResponseEntity<Laptop> res = testRestTemplate.getForEntity(url + "/1", Laptop.class);
    assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    assertNull(res.getBody());
  }

  @Test
  void findOneById_OK() {
    sendRequest();
    ResponseEntity<Laptop> res = testRestTemplate.getForEntity(url + "/1", Laptop.class);
    assertEquals(HttpStatus.OK, res.getStatusCode());
  }

  /** Testing OK POST
   *
   */
  @Test
  void create_OK() {
    String json = """
            {
              "model": "Lap Test",
              "price": 40.00,
              "specs": "4 GB RAM"
            }
            """;
    ResponseEntity<Laptop> res = testRestTemplate.exchange(url, HttpMethod.POST, request(json), Laptop.class);
    Laptop result = res.getBody();
    assertEquals(HttpStatus.OK, res.getStatusCode());
  }

  /** Testing NO_CONTENT PUT
   *
   */
  @Test
  void update_NO_CONTENT() {
    sendRequest();
    // Update The Entity
    String json = """
            {
              "id": 1,
              "model": "Lap Test Put",
              "price": 70.00,
              "specs": "4 GB RAM, 1T storage"
            }
            """;
    ResponseEntity<Laptop> res = testRestTemplate.exchange(url, HttpMethod.PUT, request(json), Laptop.class);
    assertEquals(HttpStatus.OK, res.getStatusCode());
  }


  /** Testing NO_CONTENT DELETE
   *
   */
  @Test
  void delete_NO_CONTENT() {
    sendRequest();
    HttpEntity<String> entity = new HttpEntity<>("");
    ResponseEntity<Laptop> res = testRestTemplate.exchange(url+ "/1", HttpMethod.DELETE, entity, Laptop.class);
    assertEquals(HttpStatus.NO_CONTENT, res.getStatusCode());
  }

  /** Testing NOT_FOUND DELETE
   *
   */
  @Test
  void delete_NOT_FOUND(){
    HttpEntity<String> entity = new HttpEntity<>("");
    ResponseEntity<Laptop> res = testRestTemplate.exchange(url+ "/1", HttpMethod.DELETE, entity, Laptop.class);
    assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
  }

  /** Testing NO_CONTENT DELETE
   *
   */
  @Test
  void deleteAll() {
    HttpEntity<String> entity = new HttpEntity<>("");
    ResponseEntity<Laptop> res = testRestTemplate.exchange(url, HttpMethod.DELETE, entity, Laptop.class);
    assertEquals(HttpStatus.NO_CONTENT, res.getStatusCode());
  }
}