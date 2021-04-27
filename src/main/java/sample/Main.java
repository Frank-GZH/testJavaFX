package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.netty.HttpServer;

public class Main extends Application {
    private int httpPort = 8066;

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/test.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/webcam.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1280, 1024));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

        this.startHttpServer();
    }

    private void startHttpServer(){
        new Thread(()->{
            new HttpServer().bind(httpPort);
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
