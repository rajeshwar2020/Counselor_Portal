package com.rajeshIT.service;

import com.rajeshIT.dto.DashboardResponse;
import com.rajeshIT.entities.Counselor;
import com.rajeshIT.entities.Enquiry;
import com.rajeshIT.repositories.CounselorRepository;
import com.rajeshIT.repositories.EnquiryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CounselorServiceImpl implements CounselorService {

    private CounselorRepository counselorRepo;

    private EnquiryRepository enquiryRepo;

    public CounselorServiceImpl(CounselorRepository counselorRepo, EnquiryRepository enquiryRepo) {
        this.counselorRepo = counselorRepo;
        this.enquiryRepo = enquiryRepo;
    }

    @Override
    public Counselor findByEmail(String email) {
        Counselor counselor = counselorRepo.findByEmail(email).orElse(null);
        return counselor;
    }

    @Override
    public boolean register(Counselor counselor) {
        Counselor dbCounselor = counselorRepo.findByEmail(counselor.getEmail()).orElse(null);
        if (dbCounselor == null) {
            counselorRepo.save(counselor);
            return true;
        }
        return false;
    }

    @Override
    public Counselor login(String email, String password) {
        return counselorRepo.findByEmailAndPassword(email, password);
    }

    @Override
    public DashboardResponse getDashboardInfo(Integer counselorId) {

        DashboardResponse dashboardResponse = new DashboardResponse();

        List<Enquiry> enquiries = enquiryRepo.getEnquiriesByCounselorId(counselorId);
        int totalEnquiries = enquiries.size();

        int openEnquiries = enquiries.stream()
                .filter(enquiry -> enquiry.getStatus().equals("OPEN"))
                .collect(Collectors.toList())
                .size();

        int enrolledEnquiries = enquiries.stream()
                .filter(enquiry -> enquiry.getStatus().equals("ENROLLED"))
                .collect(Collectors.toList())
                .size();

        int lostEnquiries = enquiries.stream()
                .filter(enquiry -> enquiry.getStatus().equals("LOST"))
                .collect(Collectors.toList())
                .size();

        dashboardResponse.setTotalEnquiries(totalEnquiries);
        dashboardResponse.setOpenEnquiries(openEnquiries);
        dashboardResponse.setEnrolledEnquiries(enrolledEnquiries);
        dashboardResponse.setLostEnquiries(lostEnquiries);

        return dashboardResponse;
    }
}



























