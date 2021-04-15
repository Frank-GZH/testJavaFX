package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    private WebView webview1;
    private WebEngine webEngine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webEngine = webview1.getEngine();
        System.out.println("执行加载浏览器");
        webEngine.load("http://www.google.com");
    }
}
