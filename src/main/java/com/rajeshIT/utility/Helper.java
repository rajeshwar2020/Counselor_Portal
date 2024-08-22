package com.rajeshIT.utility;

import com.rajeshIT.entities.Counselor;
import org.springframework.ui.Model;

public class Helper {

    public static String returnToLoginPage(Model model) {
        Counselor counselorObj = new Counselor();
        model.addAttribute("counselor", counselorObj);

        model.addAttribute("errorMsg", "Error occurred. Please login again..");
        return "index";
    }
}
