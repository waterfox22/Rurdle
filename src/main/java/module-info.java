module com.example.russianwordle {
    requires javafx.controls;
    requires org.apache.commons.text;

    opens com.example.russianwordle to javafx.fxml;
    exports com.example.russianwordle;
}