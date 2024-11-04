Run main.py in **src/main/resources** to launch local server to integrate with Java.

Then compile Java code in **src/main/java/com/example/russianwordle** or use pre-compiled version (**src/main/resources/russianWordle-1.0-SNAPSHOT.jar**). Make sure JavaFX is installed.

Then run it with **java --module-path /path/to/javafx-sdk-23.0.1/lib --add-modules javafx.controls,javafx.fxml com.example.russianwordle.Main** or with **java --module-path /path/to/javafx-sdk-23.0.1/lib --add-modules javafx.controls,javafx.fxml -jar russianWordle-1.0-SNAPSHOT.jar** if you're using pre-compiled version (fat jar).

![image](https://github.com/user-attachments/assets/6f37d7b7-6372-4360-8a56-69e6b41e97a0)

