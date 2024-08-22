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
@Table(name = "ENQUIRIES_TABLE")
public class Enquiry {

    @Id
    @Column(name = "ENQUIRY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer enquiryId;

    @Column(name = "STUDENT_  NAME")
    private String studentName;

    @Column(name = "PHONE_NUMBER")
    private Long phoneNumber;

    @Column(name = "COURSE_NAME")
    private String courseName;

    @Column(name = "CLASS_MODE")
    private String classMode;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_DATE", updatable = false)
    @CreationTimestamp
    private LocalDate createdDate;

    @Column(name = "UPDATED_DATE")
    @UpdateTimestamp
    private LocalDate updatedDate;

    @ManyToOne
    @JoinColumn(name = "COUNSELOR_ID")
    private Counselor counselor;

}
