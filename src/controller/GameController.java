package controller;

import java.util.Optional;



import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Pair;
import message.Consumer;
import message.MoveMessage;
import message.Producer;
import model.Player;
import model.TicTacToeModel;

public class GameController extends Application {

	private TicTacToeModel model = new TicTacToeModel();
	private Player player = new Player();
	private boolean readyToPlay = false;
	
	private Producer producer = new Producer();
	private Consumer consumer = new Consumer();

	@FXML
	Text turnText;
	@FXML
	GridPane playfield;

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
		 System.out.println(source);
		 System.out.println(isReadyToPlay());
		// System.out.println(GridPane.getRowIndex(source));
		// System.out.println(GridPane.getColumnIndex(source));

		if (!readyToPlay) {
			waitForOpponent();
			return;
		}
		System.out.println("Gramy");
		if (model.isMarked(GridPane.getRowIndex(source), GridPane
				.getColumnIndex(source)) == TicTacToeModel.State.Blank) {

			if (model.getTurn() == TicTacToeModel.State.X) {
				drawX(src);

				turnText.setText("Turn: O");
				model.setMarked(GridPane.getRowIndex(source), GridPane
						.getColumnIndex(source), TicTacToeModel.State.X);
				model.setTurn(TicTacToeModel.State.O);
				checkResult(model.checkGame(GridPane.getRowIndex(source),
						GridPane.getColumnIndex(source),
						TicTacToeModel.State.X));

			} else {
				drawO(src);

				turnText.setText("Turn: X");
				model.setMarked(GridPane.getRowIndex(source), GridPane
						.getColumnIndex(source), TicTacToeModel.State.O);

				model.setTurn(TicTacToeModel.State.X);
				checkResult(model.checkGame(GridPane.getRowIndex(source),
						GridPane.getColumnIndex(source),
						TicTacToeModel.State.O));

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
				// alert.setContentText("I have a great message for you!");
				alert.showAndWait();

				restart();

			} else {
				System.out.println("draw");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Wynik");
				alert.setHeaderText("Remis!");

				// alert.setContentText("I have a great message for you!");

				alert.showAndWait();

				restart();
			}
		}
	}

	private void getUsername() {
		TextInputDialog dialog = new TextInputDialog("gracz");
		dialog.setTitle("Logowanie");
		dialog.setHeaderText("Powiedz innym jak sie nazywasz");
		dialog.setContentText("Wprowadz swoj nick: ");
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(name -> player.setUsername(name));
		System.out.println(player.getUsername());
	}

	private void waitForOpponentMessage() {
		
		consumer.receiveQueueMessagesAsynch();
		while (!consumer.isMessageReceived()) {

		}
		System.out.println("Message received" + consumer.getMoveMessage().toString());
		
		if(consumer.getMoveMessage().getPlayer() == player)
			waitForOpponentMessage();
		
		setReadyToPlay(true);
	}
	
	private void waitForOpponent() {
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Info");
		alert.setHeaderText("Aby zagrac potrzeba dwoch graczy!");
		alert.setContentText("Poczekaj aż przciwnik dołączy.");
		alert.showAndWait();
		
		waitForOpponentMessage();
	/*	Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {

			
				return null;
			}
		};

		
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
		*/
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			getUsername();
			
			MoveMessage welcomeMessage = new MoveMessage(null, player);
			producer.sendQueueMessages(welcomeMessage);

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
					"GameWindow.fxml"));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root, 600, 350);

			primaryStage.setScene(scene);
			primaryStage.setTitle("TicTacToe Game");
			primaryStage.show();
			waitForOpponent();
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

	
	public boolean isReadyToPlay() {
		return readyToPlay;
	}

	public void setReadyToPlay(boolean readyToPlay) {
		this.readyToPlay = readyToPlay;
	}

	
}
