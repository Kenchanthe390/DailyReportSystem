package com.techacademy.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("employeelist", service.getEmployeeList());      //全件検索結果をModelに登録する
        return "employee/list";                                             //employee/list.htmlに画面遷移する
    }

    /** 従業員の詳細画面を表示 */
    @GetMapping("/detail/{id}/")
    public String getEmployeeDetail(@PathVariable("id") int id, Model model) {
        model.addAttribute("employee", service.getEmployee(id));
        return "employee/detail";
    }

    /** 新規従業員の登録画面を表示 */
    @GetMapping("/register")
    public String getRegister(@ModelAttribute Employee employee) {      //@ModelAttributeアノテーションを付与すると、自動的に（テンプレートにデータを受け渡す）Modelにインスタンスが登録される(≒@ModelAttributeアノテーションを付与せずに model.addAttribute("employee", employee); と記述することと同一）
        return "employee/register";                                     //テンプレート側では employee を使い <input type="text" th:field="${employee.name}> のように記述できる
        }

    /** 従業員の登録処理 */
    @PostMapping("/register")
    public String postRegister(Employee employee) {
        employee.setName("yamada");
        service.saveEmployee(employee);     // 従業員の登録する
        return "redirect:/employee/list";       // 従業員の一覧画面にリダイレクトする
    }

    /** 従業員の更新画面を表示 */
    @GetMapping("/update/{id}/")
    public String getEmployee(@PathVariable("id") int id, Model model) {
        model.addAttribute("employee", service.getEmployee(id));
        return "employee/update";
    }

    /**  従業員の更新処理 */
    @PostMapping("/update/{id}/")
    public String postEmployee(Employee employee) {
        service.saveEmployee(employee);
        return "redirect:/employee/list";
    }

    /** 従業員の削除処理（deleteフラグに"1"を記入する） */
    @GetMapping("/delete/{id}/")
    public String deleteEmployee(@PathVariable("id") int id, Employee employee) {
        employee = service.getEmployee(id);
        service.deleteEmployee(employee);
        return "redirect:/employee/list";
    }
}
