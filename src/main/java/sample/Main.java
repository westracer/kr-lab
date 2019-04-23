package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import sample.view_controller.ObslClientController;

public class Main extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../obsl_client.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();

        ObslClientController controller = loader.getController();
//        controller.table.prefWidthProperty().bind(controller.flowPane.widthProperty().subtract(20));
//        controller.table.prefHeightProperty().bind(controller.flowPane.heightProperty().subtract(20));
        controller.initAdresTable();
    }

}