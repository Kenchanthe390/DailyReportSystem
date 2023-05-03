package com.techacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller                     //このクラスがHTTPリクエストを受け付けるクラスであること示すアノテーション
public class TopController {

    @GetMapping("/")            //URLに対する「GETメソッド」を受け取る関するであることを示すアノテーション
    public String getTop() {
        return "top";          //list.htmlに画面遷移
    }
}
