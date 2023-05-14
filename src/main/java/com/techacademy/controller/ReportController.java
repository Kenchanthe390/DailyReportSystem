package com.techacademy.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;
import com.techacademy.service.EmployeeDetail;

@Controller
@RequestMapping("report")         //URLのプレフィックス(アプリ名の前に付ける言葉)として利用される
public class ReportController {
    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    /** 日報の新規登録画面を表示 */
    @GetMapping("/create")
    public String getCreateReport(@ModelAttribute Report report, Model model) {
//        model.addAttribute("loginUser", userDetail.getEmployee());
        return "report/create";
    }

    /** 日報の新規登録 */
    @PostMapping("/create")
    public String postCreateReport(Report report, @AuthenticationPrincipal EmployeeDetail userDetail) {
        report.setEmployee(userDetail.getEmployee());
        service.saveReport(report);
        return "redirect:/report/list";
    }

    /** 日報の一覧画面を表示 */
    @GetMapping("/list")
    public String getReportList(Model model){
        model.addAttribute("reportlist", service.getReportList());      //全件検索結果をModelに登録する
        return "report/list";                                           //report/list.htmlに画面遷移する
    }

    /** 日報の詳細画面を表示 */
    @GetMapping("/detail/{id}/")
    public String getReportDetail(@PathVariable("id") int id, Model model) {
        model.addAttribute("report", service.getReport(id));
        return "report/detail";
    }

    /** 日報の更新画面を表示 */
    @GetMapping("/update/{id}/")
    public String getReportUpdate(@PathVariable("id") int id, Model model) {
        model.addAttribute("report", service.getReport(id));
        return "report/update";
    }

    /** 日報の更新処理 */
    @PostMapping("/update/{id}/")
    public String postReportUpdate(Report report, @AuthenticationPrincipal EmployeeDetail userDetail) {
        report.setEmployee(userDetail.getEmployee());
        service.updateReport(report);
        return "redirect:/report/list";
    }
}
