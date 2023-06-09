package com.techacademy.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Employee;
import com.techacademy.service.EmployeeService;

@Controller
@RequestMapping("employee")         //URLのプレフィックス(アプリ名の前に付ける言葉)として利用される
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    /** 従業員の一覧画面を表示 */
    @GetMapping("/list")
    public String getEmployeeList(Model model) {

        //従業員情報の全件検索結果をModelに登録し、employee/list.htmlに画面遷移する
        model.addAttribute("employeelist", service.getEmployeeList());
        return "employee/list";
    }

    /** 従業員の詳細画面を表示 */
    @GetMapping("/detail/{id}/")
    public String getEmployeeDetail(@PathVariable("id") int id, Model model) {

        //idで検索した従業員情報をModelに登録し、employee/detail.htmlに画面遷移する
        model.addAttribute("employee", service.getEmployee(id));
        return "employee/detail";
    }

    /** 従業員の新規登録画面を表示 */
    @GetMapping("/register")
    public String getRegister(@ModelAttribute Employee employee) {
        //@ModelAttributeアノテーションを付与すると、自動的にModelにインスタンスが登録される(≒@ModelAttributeアノテーションを付与せずに model.addAttribute("employee", employee); と記述することと同一）
        //テンプレート側では employee を使い <input type="text" th:field="${employee.name}> のように記述できる

        //employee/register.htmlに画面遷移する
        return "employee/register";
        }

    /** 従業員の新規登録処理 */
    @PostMapping("/register")
    public String postRegister(@Validated Employee employee, BindingResult res) {
        if(res.hasErrors()) {
            //エラーあり
            return getRegister(employee);
        }

        //従業員情報を新規登録し、一覧画面にリダイレクトする
        service.saveEmployee(employee);
        return "redirect:/employee/list";
    }

    /** 従業員の更新画面を表示 */
    @GetMapping("/update/{id}/")
    public String getEmployee(@PathVariable("id") int id, Model model) {

        //idで検索した従業員情報をModelに登録し、employee/update.htmlに画面遷移する
        model.addAttribute("employee", service.getEmployee(id));
        return "employee/update";
    }

    /** 従業員の更新処理 */
    @PostMapping("/update/{id}/")
    public String postEmployee(Employee employee) {

        //従業員情報を更新し、一覧画面にリダイレクトする
        service.updateEmployee(employee);
        return "redirect:/employee/list";
    }

    /** 従業員の削除処理（deleteフラグに"1"を記入する） */
    @GetMapping("/delete/{id}/")
    public String deleteEmployee(@PathVariable("id") int id, Employee employee) {

        //授業員の削除処理をし、一覧画面にリダイレクトする
        service.deleteEmployee(service.getEmployee(id));
        return "redirect:/employee/list";
    }
}