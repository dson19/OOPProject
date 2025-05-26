module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    
    opens com.example.UI to javafx.fxml;
    exports com.example.UI;
}
