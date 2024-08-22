package com.rajeshIT.controllers;

import com.rajeshIT.dto.ViewEnquiryFilterRequest;
import com.rajeshIT.entities.Counselor;
import com.rajeshIT.entities.Enquiry;
import com.rajeshIT.service.EnquiryService;
import com.rajeshIT.utility.Helper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EnquiryController {

    private EnquiryService enquiryService;

    public EnquiryController(EnquiryService enquiryService) {
        this.enquiryService = enquiryService;
    }

    @GetMapping("/add-enquiry")
    public String addEnquiry(Model model) {
        model.addAttribute(new Enquiry());
        return "add-enquiry";
    }

    @PostMapping("/createEnquiry")
    public String createEnquiry(@ModelAttribute Enquiry enquiry, HttpServletRequest request, Model model) throws Exception {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return Helper.returnToLoginPage(model);
        }
        //for UPDATE - adding enquiryId to enquiry object (because from UI enquiryID is coming as null)
        Integer enquiryId = (Integer) session.getAttribute("enquiryId");
        if (enquiryId != null) {
            enquiry.setEnquiryId(enquiryId);
        }

        Integer counselorId = (Integer) session.getAttribute("counselorId");
        boolean isAdded = enquiryService.addEnquiry(enquiry, counselorId);
        if (isAdded) {
            model.addAttribute("successMsg", "Enquiry added successfully");
            return "add-enquiry";
        }
        return "add-enquiry";


    }

    @GetMapping("/allEnquiries")
    public String listAllEnquiries(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return Helper.returnToLoginPage(model);
        }
        Integer counselorId = (Integer) session.getAttribute("counselorId");
        if (counselorId != null) {
            List<Enquiry> allEnquiries = enquiryService.getAllEnquiries(counselorId);
            model.addAttribute("enquiries", allEnquiries);
        } else {
            model.addAttribute("errorMsg", "No counselor found");
        }
        ViewEnquiryFilterRequest filterRequest = new ViewEnquiryFilterRequest();
        model.addAttribute("filter", filterRequest);
        return "view-enquiries";


    }

    @PostMapping("/filterSearch")
    public String filterSearch(@ModelAttribute ViewEnquiryFilterRequest filterRequest, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return Helper.returnToLoginPage(model);
        }
        Integer counselorId = (Integer) session.getAttribute("counselorId");
        List<Enquiry> enquiriesWithFilter = enquiryService.getEnquiriesWithFilter(filterRequest, counselorId);
        model.addAttribute("enquiries", enquiriesWithFilter);
        ViewEnquiryFilterRequest obj = new ViewEnquiryFilterRequest();
        model.addAttribute("filter", obj);
        return "view-enquiries";
    }


    @GetMapping("/editEnquiry/{enquiryId}")
    public String editEnquiry(@PathVariable("enquiryId") Integer enquiryId, HttpServletRequest request, Model model) {
        Enquiry enquiry = enquiryService.getEnquiryById(enquiryId);
        HttpSession session = request.getSession(false);
        session.setAttribute("enquiryId", enquiry.getEnquiryId());
        model.addAttribute(enquiry);
        return "add-enquiry";
    }

}
