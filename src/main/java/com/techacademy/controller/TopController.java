package com.techacademy.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.techacademy.entity.Employee;
import com.techacademy.service.ReportService;
import com.techacademy.service.EmployeeDetail;

@Controller                     //このクラスがHTTPリクエストを受け付けるクラスであること示すアノテーション
public class TopController {
    private final ReportService service;

    public TopController(ReportService service) {
        this.service = service;
    }

    @GetMapping("/")            //URLに対する「GETメソッド」を受け取る関するであることを示すアノテーション
    public String getTop(@AuthenticationPrincipal EmployeeDetail userDetail, Model model) {
        model.addAttribute("lu", service.getLoginuserReportList(userDetail.getEmployee()));
        return "/top";          //top.htmlに画面遷移
    }
}
