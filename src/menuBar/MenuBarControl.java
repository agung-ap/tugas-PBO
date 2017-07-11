package menuBar;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by Agungaprian
 * on 16/04/2017.
 */
public class MenuBarControl {

    public void close(){
        Platform.exit();
        System.exit(0);
    }

    public void about() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/menuBar/About.fxml"));
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(new Scene(p));
        stage.show();
    }
}
