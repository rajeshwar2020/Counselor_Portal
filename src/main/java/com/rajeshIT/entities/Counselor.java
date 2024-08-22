package com.rajeshIT.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "COUNSELORS_TABLE")
public class Counselor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COUNSELOR_ID")
    private Integer counselorId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "CREATED_DATE")
    @CreationTimestamp
    private LocalDate createdDate;

    @Column(name = "UPDATED_DATE")
    @UpdateTimestamp
    private LocalDate updatedDate;

}
