package com.rajeshIT.controllers;

import com.rajeshIT.dto.DashboardResponse;
import com.rajeshIT.entities.Counselor;
import com.rajeshIT.service.CounselorService;
import com.rajeshIT.utility.Helper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CounselorController {

    private CounselorService counselorService;

    public CounselorController(CounselorService counselorService) {
        this.counselorService = counselorService;
    }

    @GetMapping("/")
    public String index(Model model) {
        Counselor counselorObj = new Counselor();

        // sending data from controller to UI
        model.addAttribute("counselor", counselorObj);

        return "index";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute Counselor counselor, HttpServletRequest request, Model model) {
        Counselor c = counselorService.login(counselor.getEmail(), counselor.getPassword());
        if (c == null) {
            model.addAttribute("errorMsg", "Invalid credentials");
            return "index";
        } else {
            // valid login, store counselorId in session for future purpose
            HttpSession session = request.getSession(true);
            session.setAttribute("counselorId", c.getCounselorId());

            DashboardResponse dashboardObj = counselorService.getDashboardInfo(c.getCounselorId());
            model.addAttribute("dashboardInfo", dashboardObj);
            return "dashboard";
        }
    }

    @GetMapping("/dashboardFromNavBar")
    public String showDashboard(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return Helper.returnToLoginPage(model);
        }
        Integer counselorId = (Integer) session.getAttribute("counselorId");
        DashboardResponse dashboardObj = counselorService.getDashboardInfo(counselorId);
        model.addAttribute("dashboardInfo", dashboardObj);
        return "dashboard";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {

        Counselor counselorObj = new Counselor();
        model.addAttribute("counselor", counselorObj);
        return "registrationForm";
    }

    @PostMapping("/register")
    public String handleRegistration(Counselor counselor, Model model) {

        // check for duplicate email
        Counselor byEmail = counselorService.findByEmail(counselor.getEmail());
        if (byEmail != null) {
            model.addAttribute("errorMsg", "Duplicate email");
            return "registrationForm";
        }

        boolean isRegistered = counselorService.register(counselor);

        if (isRegistered) {
            model.addAttribute("successMsg", "Registration success");
        } else {
            model.addAttribute("errorMsg", "Registration failed");
        }
        return "registrationForm";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        // get existing session and invalidate it
        HttpSession session = request.getSession(false);
        session.invalidate();

        // redirect to login page
        return "redirect:/";
    }

}
