package com.techacademy.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;

@Service        //サービスクラスであることを示す(Springを起動した際にDIコンテナーへ登録される)。処理をサービスクラスに記述する。リポジトリクラスを呼び出してデータ操作を完了させる役割を担う
public class ReportService {

    private final ReportRepository reportRepository;        //コンポーネントを利用するために”private final”で変数を定義する

    public ReportService(ReportRepository repository) {     //引数付きコンストラクタを用意し、DIコンテナーを経由してインスタンスを取得できるようにする
        this.reportRepository = repository;
    }

    /** 日報を登録する */
    @Transactional      //saveReportメソッドはデータベース更新用のメソッドであるため、@Transactionalアノテーションを指定する
    public Report saveReport(Report report) {
        LocalDateTime timeOfCreated = LocalDateTime.now();              //タイムスタンプを取得し、「登録日時」にセットする
        LocalDateTime timeOfUpdated = LocalDateTime.now();              //タイムスタンプを取得し、「更新日時」にセットする
        report.setCreated_at(timeOfCreated);
        report.setUpdated_at(timeOfUpdated);
        return reportRepository.save(report);
    }

    /** 日報を更新する */
    @Transactional      //saveReportメソッドはデータベース更新用のメソッドであるため、@Transactionalアノテーションを指定する
    public Report updateReport(Report report) {
        LocalDateTime timeOfUpdated = LocalDateTime.now();              //タイムスタンプを取得し、「更新日時」にセットする
        report.setUpdated_at(timeOfUpdated);
        return reportRepository.save(report);
    }

    /** 日報を一覧検索する */
    public List<Report> getReportList(){
        return reportRepository.findAll();        //リポジトリクラスのfindAllメソッドを呼び出す
    }

    /** ログインユーザーの日報を一覧検索する */
    public List<Report> getLoginuserReportList(Employee employee){
        return reportRepository.findByEmployee(employee);
    }

    /** 日報を1件検索する */
    public Report getReport(int id) {
        return reportRepository.findById(id);
    }

/**    public List<Report> getLoginuserReportList(EmployeeDetail userDetail) {
        int loginuserId = userDetail.getEmployee().getId();
        return reportRepository.find
        // reportRepository.findByEmployee(userDetail);
*/
    }