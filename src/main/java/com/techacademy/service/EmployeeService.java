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

    @Autowired  //パスワードを暗号化するために定義する
    private PasswordEncoder passwordEncoder;

    //コンポーネントを利用するために”private final”で変数を定義する
    private final EmployeeRepository employeeRepository;

    //引数付きコンストラクタを用意し、DIコンテナーを経由してインスタンスを取得できるようにする
    public EmployeeService(EmployeeRepository repository) {
        this.employeeRepository = repository;
    }

    /** 全件を検索して返す */
    public List<Employee> getEmployeeList(){

        //リポジトリクラスのfindAllメソッドを呼び出す
        return employeeRepository.findAll();
    }

    /** 従業員を1件検索して返す */
    public Employee getEmployee(int id) {
        return employeeRepository.findById(id).get();
    }

    /** 従業員を登録する */
    @Transactional      //saveEmployeeメソッドはデータベース更新用のメソッドであるため、@Transactionalアノテーションを指定する
    public Employee saveEmployee(Employee employee) {

        //タイムスタンプを取得し、「登録日時」にセットする
        LocalDateTime timeOfCreated = LocalDateTime.now();
        employee.setCreated_at(timeOfCreated);

        //タイムスタンプを取得し、「更新日時」にセットする
        LocalDateTime timeOfUpdated = LocalDateTime.now();
        employee.setUpdated_at(timeOfUpdated);

        //Delete_flgaに0をセットする
        employee.setDelete_flag(0);

        //入力されたパスワードを暗号化し、再度セットする
        String password = employee.getAuthentication().getPassword();
        employee.getAuthentication().setPassword(passwordEncoder.encode(password));

        //Authenticationテーブルのemployeeフィールドを再度セットする
        employee.getAuthentication().setEmployee(employee);

        //リポジトリクラスの更新メソッドを呼び出す(saveメソッドは、引数で渡したエンティティインスタンスのデータをテーブルに保存する)
        return employeeRepository.save(employee);
    }

    /** 従業員を更新する */
    @Transactional      //saveEmployeeメソッドはデータベース更新用のメソッドであるため、@Transactionalアノテーションを指定する
    public Employee updateEmployee(Employee employee) {

        //タイムスタンプを取得し、「更新日時」にセットする
        LocalDateTime timeOfUpdated = LocalDateTime.now();
        employee.setUpdated_at(timeOfUpdated);

        //Delete_flgaに0をセットする
        employee.setDelete_flag(0);

        //入力されたパスワードを暗号化し、再度セットする
        String password = employee.getAuthentication().getPassword();
        employee.getAuthentication().setPassword(passwordEncoder.encode(password));

        //Authenticationテーブルのemployeeフィールドを再度セットする
        employee.getAuthentication().setEmployee(employee);

        //リポジトリクラスの更新メソッドを呼び出す(saveメソッドは、引数で渡したエンティティインスタンスのデータをテーブルに保存する)
        return employeeRepository.save(employee);
    }

    /** 従業員を社員番号で検索する */
    public Employee findByEmployeecode(String employeecode) {

        //リポジトリクラスの検索メソッドを呼び出し、検索結果を返却する
        Employee result = employeeRepository.findByAuthenticationCode(employeecode);
        return result;
    }

    /** パスワードを更新する */
    @Transactional      //データベース更新用のメソッドであるため、@Transactionalアノテーションを指定する
    public Employee resetPassword(Employee newInfo) {

        //タイムスタンプを取得し、「更新日時」にセットする
        LocalDateTime timeOfUpdated = LocalDateTime.now();
        newInfo.setUpdated_at(timeOfUpdated);

        //入力されたパスワードを暗号化し、再度セットする
        String password = newInfo.getAuthentication().getPassword();
        newInfo.getAuthentication().setPassword(passwordEncoder.encode(password));

        //リポジトリクラスの更新メソッドを呼び出す(saveメソッドは、引数で渡したエンティティインスタンスのデータをテーブルに保存する)
        return employeeRepository.save(newInfo);
        }

    /** 従業員を削除する */
    @Transactional
    public void deleteEmployee(Employee employee) {

        //Delete_flgaに1をセットし、リポジトリクラスの更新メソッドを呼び出す(saveメソッドは、引数で渡したエンティティインスタンスのデータをテーブルに保存する)
        employee.setDelete_flag(1);
        employeeRepository.save(employee);
        }
}