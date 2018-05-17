package controller;

import java.util.Optional;
import javafx.scene.control.TextInputDialog;


public class WindowAlert {

	public String askForUsername() {

		TextInputDialog dialog = new TextInputDialog("gracz");
		dialog.setTitle("Logowanie");
		dialog.setHeaderText("Powiedz innym jak sie nazywasz");
		dialog.setContentText("Wprowadz swoj nick: ");
		Optional<String> result = dialog.showAndWait();
		return result.get();
	}

}
