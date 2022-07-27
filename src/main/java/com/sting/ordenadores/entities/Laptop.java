package com.sting.ordenadores.entities;

import javax.persistence.*;

@Entity
@Table(name = "laptops")
public class Laptop {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String model;
  private Double price;
  private String specs;

  public Laptop() {
  }

  /**
   *
   * @param Long:id
   * @param String:model
   * @param Double:price
   * @param String:specs
   */
  public Laptop(Long id, String model, Double price, String specs) {
    this.id = id;
    this.model = model;
    this.price = price;
    this.specs = specs;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getSpecs() {
    return specs;
  }

  public void setSpecs(String specs) {
    this.specs = specs;
  }
}
