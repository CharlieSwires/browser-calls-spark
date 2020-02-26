package com.twilio.browsercalls.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "person")
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "phone_number")
  private String phoneNumber;



  public Person() {}

  public Person(String name, String phoneNumber) {
    this.name = name;
    this.phoneNumber = phoneNumber;
  }

  Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }


}
