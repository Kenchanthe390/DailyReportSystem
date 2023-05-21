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
    public String getCreateReport(@ModelAttribute Report report, @AuthenticationPrincipal EmployeeDetail userDetail, Model model) {

        //ログインしているユーザーの権限情報を確認し、roleFlagの値をセットする（管理者の場合は"1"、一般の場合は"0"をセットする）
        int roleFlag = 0;
        String role = userDetail.getEmployee().getAuthentication().getRole().name();

        if(role == "管理者") {
            roleFlag = 1;
        }

        //ログインユーザーと日報の情報をmodelに格納し、report/create.htmlに画面遷移する
        model.addAttribute("reportlist", service.getReportList());
        model.addAttribute("roleflag", roleFlag);
        return "report/create";
    }

    /** 日報の新規登録 */
    @PostMapping("/create")
    public String postCreateReport(Report report, @AuthenticationPrincipal EmployeeDetail userDetail) {

        //新規登録ページで入力されなかった内容をセットする
        report.setEmployee(userDetail.getEmployee());

        //日報を登録し、report/list.htmlにリダイレクトする
        service.saveReport(report);
        return "redirect:/report/list";
    }

    /** 日報の一覧画面を表示 */
    @GetMapping("/list")
    public String getReportList(@AuthenticationPrincipal EmployeeDetail userDetail, Model model){

        //ログインしているユーザーの権限情報を確認し、roleFlagの値をセットする（管理者の場合は"1"、一般の場合は"0"をセットする）
        int roleFlag = 0;
        String role = userDetail.getEmployee().getAuthentication().getRole().name();

        if(role == "管理者") {
            roleFlag = 1;
        }

        //ログインユーザーと日報の情報をmodelに格納し、report/list.htmlに画面遷移する
        model.addAttribute("reportlist", service.getReportList());
        model.addAttribute("roleflag", roleFlag);
        return "report/list";
    }

    /** 日報の詳細画面を表示 */
    @GetMapping("/detail/{id}/")
    public String getReportDetail(@PathVariable("id") int id, @AuthenticationPrincipal EmployeeDetail userDetail, Model model) {

        //ログインしているユーザーの権限情報を確認し、roleFlagの値をセットする（管理者の場合は"1"、一般の場合は"0"をセットする）
        int roleFlag = 0;
        String role = userDetail.getEmployee().getAuthentication().getRole().name();

        if(role == "管理者") {
            roleFlag = 1;
        }

        //ログインユーザーと日報の情報をmodelに格納し、report/detail.htmlに画面遷移する
        model.addAttribute("report", service.getReport(id));
        model.addAttribute("roleflag", roleFlag);
        return "report/detail";
    }

    /** 日報の更新画面を表示 */
    @GetMapping("/update/{id}/")
    public String getReportUpdate(@PathVariable("id") int id, @AuthenticationPrincipal EmployeeDetail userDetail, Model model) {

        //ログインしているユーザーの権限情報を確認し、roleFlagの値をセットする（管理者の場合は"1"、一般の場合は"0"をセットする）
        int roleFlag = 0;
        String role = userDetail.getEmployee().getAuthentication().getRole().name();

        if(role == "管理者") {
            roleFlag = 1;
        }

        //ログインユーザーと日報の情報をmodelに格納し、report/update.htmlに画面遷移する
        model.addAttribute("report", service.getReport(id));
        model.addAttribute("roleflag", roleFlag);
        return "report/update";
    }

    /** 日報の更新処理 */
    @PostMapping("/update/{id}/")
    public String postReportUpdate(Report report, @AuthenticationPrincipal EmployeeDetail userDetail) {

        //更新ページで入力されなかった内容をセットする
        report.setEmployee(userDetail.getEmployee());

        //日報を更新し、report/list.htmlにリダイレクトする
        service.updateReport(report);
        return "redirect:/report/list";
    }
}