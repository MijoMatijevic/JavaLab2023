package com.example.demo1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    private String city;
    @Column(name = "county")
    private String county;
    @Column(name = "street_number")
    private Integer streetNumber;

    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private Client client;
}