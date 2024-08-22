package com.rajeshIT.service;

import com.rajeshIT.dto.ViewEnquiryFilterRequest;
import com.rajeshIT.entities.Counselor;
import com.rajeshIT.entities.Enquiry;
import com.rajeshIT.repositories.CounselorRepository;
import com.rajeshIT.repositories.EnquiryRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnquiryServiceImpl implements EnquiryService {

    private CounselorRepository counselorRepo;

    private EnquiryRepository enquiryRepo;


    public EnquiryServiceImpl(CounselorRepository counselorRepo, EnquiryRepository enquiryRepo) {
        this.counselorRepo = counselorRepo;
        this.enquiryRepo = enquiryRepo;
    }

    @Override
    public boolean addEnquiry(Enquiry enquiry, Integer counselorId) throws Exception {
        Counselor counselor = counselorRepo.findById(counselorId).orElse(null);

        if (counselor == null) {
            throw new Exception("No counselor found");
        }

        enquiry.setCounselor(counselor);
        enquiryRepo.save(enquiry);
        return true;
    }

    @Override
    public List<Enquiry> getAllEnquiries(Integer counselorId) {
        return enquiryRepo.getEnquiriesByCounselorId(counselorId);
    }

    @Override
    public List<Enquiry> getEnquiriesWithFilter(ViewEnquiryFilterRequest filterRequest, Integer counselorId) {
        Enquiry enquiry = new Enquiry();

        if(StringUtils.isNotEmpty(filterRequest.getClassMode())) {
            enquiry.setClassMode(filterRequest.getClassMode());
        }
        if(StringUtils.isNotEmpty(filterRequest.getCourseName())) {
            enquiry.setCourseName(filterRequest.getCourseName());
        }
        if(StringUtils.isNotEmpty(filterRequest.getStatus())) {
            enquiry.setStatus(filterRequest.getStatus());
        }

        Counselor counselor = counselorRepo.findById(counselorId).orElse(null);
        enquiry.setCounselor(counselor);

        Example example = Example.of(enquiry);

        return enquiryRepo.findAll(example);
    }

    @Override
    public Enquiry getEnquiryById(Integer enquiryId) {
        return enquiryRepo.findById(enquiryId).orElse(null);
    }
}
