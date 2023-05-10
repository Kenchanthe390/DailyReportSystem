package com.techacademy.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.techacademy.service.EmployeeDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                   //getter・setter等のメソッドを生成するアノテーション
@AllArgsConstructor     //全てのフィールドを引数に持つコンストラクタを生成するアノテーション
@NoArgsConstructor      //引数を持たないコンストラクタを生成するアノテーション
@Entity
@Table(name="report")
public class Report {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate report_date;
    @Column(length = 255, nullable = false)
    private String title;
    @Column(nullable = false)
    @Type(type="text")
    private String content;
    @Column(nullable = true)
    private LocalDateTime created_at;
    @Column(nullable = true)
    private LocalDateTime updated_at;
    @ManyToOne
    @JoinColumn(name="employee_id", referencedColumnName="id")
    private Employee employee;
    public void getEmployee(EmployeeDetail userDetail) {
        // TODO 自動生成されたメソッド・スタブ

    }
    }