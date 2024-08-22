package com.rajeshIT.repositories;

import com.rajeshIT.entities.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnquiryRepository extends JpaRepository<Enquiry, Integer> {

    @Query(value = "select * from ENQUIRIES_TABLE where COUNSELOR_ID = :counselorId", nativeQuery = true)
    public List<Enquiry> getEnquiriesByCounselorId(Integer counselorId);


}
