module com.example.naloga2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.scripting;
    requires exp4j;


    opens com.example.naloga2 to javafx.fxml;
    exports com.example.naloga2;
}