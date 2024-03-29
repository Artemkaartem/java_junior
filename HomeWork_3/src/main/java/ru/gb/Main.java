package ru.gb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try (Connection connection = getH2Connection()) {
         DatabaseTester tester = new SimpleDatabaseTester(connection, getStudents());
            tester.test();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getH2Connection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:test");
    }

    public static List<Student> getStudents() {
        List<Student> list = new ArrayList<>();
        list.add(new Student(1, "Ivan", "Ivanov", 19));
        list.add(new Student(2, "Petr", "Petrov", 31));
        list.add(new Student(3, "Tatyana", "Ivanova", 20));
        list.add(new Student(4, "Alla", "Petrova", 28));
        return list;
    }
}