package com.joxxe.borsen;

import java.util.ArrayList;

import com.joxxe.borsen.gui.MenuBorsen;
import com.joxxe.borsen.gui.tabs.Tab0;
import com.joxxe.borsen.gui.tabs.Tab1;
import com.joxxe.borsen.gui.tabs.Tab2;
import com.joxxe.borsen.model.MarketCrawler;
import com.joxxe.borsen.threadExecuter.NotifyingThread;
import com.joxxe.borsen.threadExecuter.ThreadExecuter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Main class for application.
 * @author joakim hagberg
 *
 */
public class Main extends Application {
	public static final int LIST_WIDTH = 150;
	private static TextArea output;
	public static final double OUTPUT_HEIGHT = 150;
	public static final double DATA_PANE_HEIGHT = 150;
	public static void main(String[] args) {
		launch(args);
	}
	/**
	 * Method for printing text in output field.
	 * @param text Text to print.
	 */
	public static void output(String text) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				output.setText(text + "\n" + output.getText());
			}
		});
	}

	public MarketCrawler marketCrawler;
	public BorderPane root;

	private Tab0 tab0;
	private Tab1 tab1;
	private Tab2 tab2;

	public TabPane tabbedPane;

	public ListView<String> list;
	private ThreadExecuter threadExecuter;

	/**
	 * Method to be called when exiting the application.
	 */
	public void exit() {
		threadExecuter.exit();
		Platform.exit();
		System.exit(1);

	}


	/**
	 * Method that inits the gui.
	 */
	private void initGUI() {
		MenuBorsen menuBorsen = new MenuBorsen(this);
		VBox v = new VBox();
		HBox h = new HBox();
		//list
		list = new ListView<String>(FXCollections.observableArrayList(marketCrawler.getCompanies()));
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				switch (tabbedPane.getSelectionModel().getSelectedIndex()) {
				case 0:
					break;
				case 1:
					tab1.updateTableData();
					Main.output("Visa historisk data för:" + newValue);
					break;
				case 2:
					tab2.update();
					//tab2.updateData();
					Main.output("Visa diagram för " + newValue);
					break;
				default:
					break;
				}

			}
		});
		list.setPrefWidth(LIST_WIDTH);
		//tabs!
		tabbedPane = new TabPane();
		tabbedPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tab0 = new Tab0(this);
		tab1 = new Tab1(this);
		tab2 = new Tab2(this);
		tabbedPane.getTabs().addAll(tab0, tab1, tab2);
		tabbedPane.setPrefWidth(root.getWidth()-LIST_WIDTH);
		tabbedPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				switch (newValue.intValue()) {
				case 0:
					break;
				case 1:
					tab1.updateTableData();
					break;
				case 2:
					tab2.update();
					break;
				default:
					break;
				}

			}
		});
		//
		output = new TextArea();
		output.getStyleClass().add("output");
		h.getChildren().addAll(list, tabbedPane);
		v.getChildren().addAll(menuBorsen.getMenuBar(),h,output);
		tabbedPane.setPrefHeight(root.getHeight() - OUTPUT_HEIGHT);
		output.setWrapText(true);
		output.setPrefHeight(OUTPUT_HEIGHT);
		root.setCenter(v);
	}

	/**
	 * Method for init listeners when chaning window size.
	 * @param primaryStage Primary stage
	 * @param scene Scene 
	 */
	private void initListeners(Stage primaryStage, Scene scene) {
		// new width?
		scene.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
					Number newSceneWidth) {
				tab1.getTable().setPrefWidth(scene.getWidth() - LIST_WIDTH);
				list.setPrefWidth(LIST_WIDTH);
				// height
				tabbedPane.setPrefHeight(root.getHeight() - OUTPUT_HEIGHT);
				output.setPrefHeight(OUTPUT_HEIGHT);
				// height and width
				tabbedPane.setPrefSize(root.getWidth() - LIST_WIDTH, root.getHeight() - OUTPUT_HEIGHT);
				tab2.getLineChart().setPrefSize(root.getWidth() - LIST_WIDTH, root.getHeight() - OUTPUT_HEIGHT-DATA_PANE_HEIGHT);
			}
		});
		//be sure exit method is used when closing app.
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {
				exit();
			}
		});
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			threadExecuter = new ThreadExecuter();
			// init manager
			marketCrawler = new MarketCrawler(FXCollections.observableArrayList(getCompanies()));
			// init gui
			root = new BorderPane();
			Scene scene = new Scene(root, 800, 640);
			scene.getStylesheets().add(getClass().getResource("/styles/Borsen.css").toExternalForm());
			initGUI();
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/borsen.png")));
			// redirectOutput();
			initListeners(primaryStage, scene);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			// e.printStackTrace();
		}
	}
	private ArrayList<String> getCompanies() {
		String[] s = { "ABBN.VX", "ALFA.ST", "ATCO-A.ST", "ATCO-B.ST", "AZN.L", "BOL.ST", "ELUX-B.ST",
				"ERIC-B.ST", "GETI-B.ST", "HM-B.ST", "INVE-B.ST", "KINV-B.ST", "LUPE.ST", "MTG-B.ST", "NDA-SEK.ST",
				"NOKIA.HE", "SAND.ST", "SCA-B.ST", "SEB-A.ST", "SECU-B.ST", "SHB-A.ST", "SKA-B.ST", "SKF-B.ST",
				"SSAB-A.ST", "SWED-A.ST", "SWMA.ST", "TEL2-B.ST", "TLSN.ST", "VOLV-B.ST" };
		ArrayList<String> c = new ArrayList<String>();
		for (int i = 0; i < s.length; i++) {
			c.add(s[i]);
		}
		return c;
	}
	
	/**
	 * Clear data from marketcrawler.
	 */
	public void clear() {
		marketCrawler.clear();
	}
	
	/**
	 * Method to execute another thread
	 * @param t Thread to execute.
	 */
	public void executeRunnable(NotifyingThread t){
		threadExecuter.execute(t);
	}

}
