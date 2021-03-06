package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainProdaj extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../prodaj.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Сотрудник отдела продаж электроэнергии");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

}