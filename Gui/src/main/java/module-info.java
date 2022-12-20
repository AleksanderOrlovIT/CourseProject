module khai.edu.com.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens khai.edu.com.gui to javafx.fxml;
    exports khai.edu.com.gui;
}