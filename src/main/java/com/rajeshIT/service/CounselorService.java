package com.rajeshIT.service;

import com.rajeshIT.dto.DashboardResponse;
import com.rajeshIT.entities.Counselor;
import org.springframework.stereotype.Service;

public interface CounselorService {

    public Counselor findByEmail(String email);

    public boolean register(Counselor counselor);

    public Counselor login(String email, String password);

    public DashboardResponse getDashboardInfo(Integer counselorId);
}
