package com.techacademy.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.entity.Employee;
import com.techacademy.repository.EmployeeRepository;

@Service        //サービスクラスであることを示す(Springを起動した際にDIコンテナーへ登録される)。処理をサービスクラスに記述する。リポジトリクラスを呼び出してデータ操作を完了させる役割を担う
public class EmployeeService {

    @Autowired
    private PasswordEncoder passwordEncoder;                    //パスワードを暗号化するために定義する

    private final EmployeeRepository employeeRepository;        //コンポーネントを利用するために”private final”で変数を定義する

    public EmployeeService(EmployeeRepository repository) {     //引数付きコンストラクタを用意し、DIコンテナーを経由してインスタンスを取得できるようにする
        this.employeeRepository = repository;
    }

    /** 全件を検索して返す */
    public List<Employee> getEmployeeList(){
        return employeeRepository.findAll();        //リポジトリクラスのfindAllメソッドを呼び出す
    }

    /** 従業員を1件検索して返す */
    public Employee getEmployee(int id) {
        return employeeRepository.findById(id).get();
    }

    /** 従業員を登録する */
    @Transactional      //saveEmployeeメソッドはデータベース更新用のメソッドであるため、@Transactionalアノテーションを指定する
    public Employee saveEmployee(Employee employee) {
        LocalDateTime timeOfCreated = LocalDateTime.now();              //タイムスタンプを取得し、「登録日時」にセットする
        LocalDateTime timeOfUpdated = LocalDateTime.now();              //タイムスタンプを取得し、「更新日時」にセットする
        String password = employee.getAuthentication().getPassword();   //入力されたパスワードを暗号化し、再度セットする
        employee.setCreated_at(timeOfCreated);
        employee.setUpdated_at(timeOfUpdated);
        employee.setDelete_flag(0);
        employee.getAuthentication().setPassword(passwordEncoder.encode(password));
        employee.getAuthentication().setEmployee(employee);
        return employeeRepository.save(employee);       //saveメソッドは、引数で渡したエンティティインスタンスのデータをテーブルに保存する
    }

    /** 従業員を削除する */
    @Transactional
    public void deleteEmployee(Employee employee) {
        employee.setDelete_flag(1);
        employeeRepository.save(employee);
        }
    }