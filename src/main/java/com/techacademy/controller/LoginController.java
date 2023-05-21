package com.techacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.techacademy.entity.Employee;
import com.techacademy.service.EmployeeService;

@Controller
public class LoginController {

    //コンポーネントを利用するために”private final”で変数を定義する
    private final EmployeeService service;

    //引数付きコンストラクタを用意し、DIコンテナーを経由してインスタンスを取得できるようにする
    public LoginController(EmployeeService service) {
        this.service = service;
    }

    /** ログイン画面を表示 */
    @GetMapping("/login")
    public String getLogin() {

        //login.htmlに画面遷移する
        return "login";
    }

    /** パスワード再設定画面を表示 */
    @GetMapping("/passwordreset")
    public String getPasswordreset(Employee employee, Model model) {

        //Modelを作成し、passwordreset.htmlに画面遷移する
        model.addAttribute("employee", employee);
        return "passwordreset";
    }

    /** パスワード再設定の更新処理 */
    @PostMapping("/passwordreset")
    public String postPasswordreset(Employee employee) {

        //パスワード再設定画面で入力された社員情報をもとに検索し、パスワードを上書きしたうえで更新メソッドを呼び出す
        Employee newInfo = service.findByEmployeecode(employee.getAuthentication().getCode());
        newInfo.getAuthentication().setPassword(employee.getAuthentication().getPassword());
        service.resetPassword(newInfo);

        //更新作業が完了後、ログイン画面へリダイレクトする
        return "redirect:/login";
    }
}