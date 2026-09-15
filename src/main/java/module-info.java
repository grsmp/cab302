module com.example.assignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires de.mkammerer.argon2.nolibs;
    requires java.net.http;
    requires org.xerial.sqlitejdbc;
    requires org.junit.jupiter.api;


    opens com.geraj.assignment to javafx.fxml;
    exports com.geraj.assignment;
    exports com.geraj.assignment.controller;
    opens com.geraj.assignment.controller to javafx.fxml;
    exports com.geraj.assignment.model;
    opens com.geraj.assignment.model to javafx.fxml;
    exports com.geraj.assignment.dao;
    opens com.geraj.assignment.dao to javafx.fxml;
}