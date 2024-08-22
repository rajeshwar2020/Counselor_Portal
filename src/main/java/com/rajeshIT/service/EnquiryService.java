package com.rajeshIT.service;

import com.rajeshIT.dto.ViewEnquiryFilterRequest;
import com.rajeshIT.entities.Enquiry;
import com.rajeshIT.repositories.CounselorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface EnquiryService {

    public boolean addEnquiry(Enquiry enquiry, Integer counselorId) throws Exception;

    public List<Enquiry> getAllEnquiries(Integer counselorId);

    public List<Enquiry> getEnquiriesWithFilter(ViewEnquiryFilterRequest filterRequest, Integer CounselorId);

    public Enquiry getEnquiryById(Integer enquiryId);
}
