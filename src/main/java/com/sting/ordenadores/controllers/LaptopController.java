package com.sting.ordenadores.controllers;

import com.sting.ordenadores.entities.Laptop;
import com.sting.ordenadores.repositories.LaptopRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LaptopController {

  private final LaptopRepository laptopRepository;
  private final Logger log = LoggerFactory.getLogger(LaptopController.class);

  public LaptopController(LaptopRepository laptopRepository) {
    this.laptopRepository = laptopRepository;
  }

  /** get all Entities
   * 
   * @return 
   */
  @GetMapping("/laptops")
  public List<Laptop> findAll() {
    return laptopRepository.findAll();
  }

  
  /** Get Entity by id
   * 
   * @param id
   * @return 
   */
  @GetMapping("/laptops/{id}")
  public ResponseEntity<Laptop> findOneById(@PathVariable Long id) {
    Optional<Laptop> laptopOptional = laptopRepository.findById(id);

    if (laptopOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(laptopOptional.get());
  }

  /** Create Entity
   * 
   * @param laptop
   * @return 
   */
  @PostMapping("/laptops")
  public ResponseEntity<Laptop> create(@RequestBody Laptop laptop) {

    if (laptop.getId() != null) {
      log.warn("Trying Creating LaptopEntity width id on method POST");
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(laptopRepository.save(laptop));
  }
  
  /** Update Entity
   * 
   * @param laptop
   * @return
   */
  @PutMapping("/laptops")
  public ResponseEntity<Laptop> update(@RequestBody Laptop laptop) {

    if (laptop.getId() == null) {
      log.warn("Trying creating a LaptopEntity widthout id on method PUT");
      return ResponseEntity.badRequest().build();
    }

    if (!laptopRepository.existsById(laptop.getId())) {
      log.warn("Trying to find LaptopEntity, but not exists");
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(laptopRepository.save(laptop));
  }

  
  /** Delete Entity by id
   * 
   * @param id
   * @return
   */
  @DeleteMapping("/laptops/{id}")
  public ResponseEntity<Laptop> delete(@PathVariable Long id) {

    if (laptopRepository.existsById(id)) {
      laptopRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    }

    log.warn("Trying deleting a LaptopEntity what not exists");
    return ResponseEntity.notFound().build();
  }
  
  /** Delete all Entities
   * 
   * @return
   */
  @DeleteMapping("/laptops")
  public ResponseEntity<Laptop> deleteAll() {

    
    log.info("Deleting all LaptopsEntities");
    laptopRepository.deleteAll();
    return ResponseEntity.noContent().build();
  }

}
