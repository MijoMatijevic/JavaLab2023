package com.example.demo1.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="record")
@Table(name="record")
public class Record {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="moon", nullable = false)
    private int month;

    @Column(name="sun", nullable = false)
    private int year;

    @Column(name="recordValue")
    private int value;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id")
    private Device device;
}
