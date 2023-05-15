package com.techacademy.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.techacademy.service.ReportService;
import com.techacademy.service.EmployeeDetail;

@Controller     //このクラスがHTTPリクエストを受け付けるクラスであること示すアノテーション
public class TopController {
    private final ReportService reportService;

    public TopController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/")
    public String getTop(@AuthenticationPrincipal EmployeeDetail userDetail, Model model) {

        //ログインしているユーザーの権限情報を確認し、roleFlagの値をセットする処理（管理者の場合は"1"、一般の場合は"0"をセットする）
        int roleFlag = 0;
        String role = userDetail.getEmployee().getAuthentication().getRole().name();

        if(role == "管理者") {
            roleFlag = 1;
        }

        //ログインしているユーザーと権限の情報をmodelに格納し、top.htmlに画面遷移する
        model.addAttribute("loggedinuser", reportService.getLoginuserReportList(userDetail.getEmployee()));
        model.addAttribute("roleflag", roleFlag);

        return "top";
    }
}
