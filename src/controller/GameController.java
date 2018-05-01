package controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.TicTacToeModel;

public class GameController extends Application {

	private TicTacToeModel model = new TicTacToeModel();
	@FXML
	Text turnText;
	@FXML
	GridPane playfield;

	@FXML
	private void setTxt() {
		System.out.println("kliknieto Txt");
	}

	private void drawX(TextFlow src) {
		Text text = new Text("X");
		text.setFont(Font.font(30));
		src.getChildren().add(text);
	}

	private void drawO(TextFlow src) {
		Text text = new Text("O");
		text.setFont(Font.font(30));
		src.getChildren().add(text);
	}

	@FXML
	private void draw(MouseEvent e) {

		TextFlow src = (TextFlow) e.getSource();
		Node source = (Node) e.getSource();
		// System.out.println(source);

		// System.out.println(GridPane.getRowIndex(source));
		// System.out.println(GridPane.getColumnIndex(source));

		if (model.isMarked(GridPane.getRowIndex(source), GridPane
				.getColumnIndex(source)) == TicTacToeModel.State.Blank) {

			
			if (model.getTurn() == TicTacToeModel.State.X) {
				drawX(src);
				
				turnText.setText("Turn: O");
				model.setMarked(GridPane.getRowIndex(source), GridPane
						.getColumnIndex(source), TicTacToeModel.State.X);
				model.setTurn(TicTacToeModel.State.O);
				checkResult(model.checkGame(GridPane.getRowIndex(source), GridPane
						.getColumnIndex(source), TicTacToeModel.State.X));

			} else {
				drawO(src);
				
				turnText.setText("Turn: X");
				model.setMarked(GridPane.getRowIndex(source), GridPane
						.getColumnIndex(source), TicTacToeModel.State.O);
				
				model.setTurn(TicTacToeModel.State.X);
				checkResult(model.checkGame(GridPane.getRowIndex(source), GridPane
						.getColumnIndex(source), TicTacToeModel.State.O));

			}

		}

	}

	private void checkResult(Pair<Boolean, TicTacToeModel.State> result) {
		if (result.getKey()) {
			if (result.getValue() != TicTacToeModel.State.Blank) {
				System.out.println(result.getValue());
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Wynik");
				alert.setHeaderText("Wygrał " + result.getValue());
			//	alert.setContentText("I have a great message for you!");
				alert.showAndWait();
				
				restart();
				
			} else {
				System.out.println("draw");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Wynik");
				alert.setHeaderText("Remis!");
				
				//	alert.setContentText("I have a great message for you!");

				alert.showAndWait();
				
				restart();
			}
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
					"GameWindow.fxml"));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root, 600, 350);

			primaryStage.setScene(scene);
			primaryStage.setTitle("TicTacToe Game");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

	@FXML
	private void restart() {
		turnText.setText("Turn: X");
		playfield.getChildren().forEach(e -> {
			// System.out.print(e.getClass());

			if (e.getClass().toString().equals(
					"class javafx.scene.text.TextFlow")) {
				TextFlow src = (TextFlow) e;
				src.getChildren().clear();
			}

		});

		model = new TicTacToeModel();
	}

}
