package com.borzacchiello.advent_of_code.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
}
