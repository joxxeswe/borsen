package com.joxxe.borsen.gui;

import com.joxxe.borsen.Main;
import com.joxxe.borsen.model.MarketCrawler;
import com.joxxe.borsen.model.stock.StockResultSet;
import com.joxxe.borsen.threadExecuter.NotifyingThread;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.Callback;
/**
 * Class showing the menu
 * @author joakim hagberg
 *
 */
public class MenuBorsen {

	private MenuBar menuBar;
	private MarketCrawler marketCrawler;

	/**
	 * Constructor
	 * @param m Main class for reference
	 */
	public MenuBorsen(Main m) {
		marketCrawler = m.marketCrawler;
		menuBar = new MenuBar();
		String os = System.getProperty("os.name");
		if (os != null && os.startsWith("Mac")) {
			menuBar.useSystemMenuBarProperty().set(true);
		}
		Menu menuFile = new Menu("File");
		MenuItem open = new MenuItem("Hämta sparad data");
		MenuItem save = new MenuItem("Spara inläst data");
		MenuItem clear = new MenuItem("Ta bort data (även sparad)");
		MenuItem exit = new MenuItem("Avsluta");
		exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				m.exit();
			}
		});
		clear.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				m.clear();
			}
		});
		save.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				m.marketCrawler.save();
			}
		});
		open.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				m.marketCrawler.open();
				m.list.setItems(FXCollections.observableArrayList(m.marketCrawler.getCompanies()));
			}
		});
		menuFile.getItems().addAll(open, save, new SeparatorMenuItem(), clear, new SeparatorMenuItem(), exit);
		// aktier
		Menu menuQuotes = new Menu("Aktier");
		MenuItem menuStockAdd = new MenuItem("Lägg till i listan");
		MenuItem menuStockRemove = new MenuItem("Ta bort markerad");
		menuStockRemove.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				removeSelected(m);
			}

			
		});
		menuStockAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				showDialog(m);
			}

		});
		menuQuotes.getItems().addAll(menuStockAdd,menuStockRemove);
		menuBar.getMenus().addAll(menuFile, menuQuotes);
	}
	/**
	 * Removes seleced stock from the list.
	 * @param m Reference to Main class.
	 */
	private void removeSelected(Main m) {
		String company = m.list.getSelectionModel().getSelectedItem();
		if(company!=null){
			m.marketCrawler.removeCompany(company);
			m.list.setItems(FXCollections.observableArrayList(m.marketCrawler.getCompanies()));
		}
	}
	/**
	 * Method that creates and shows a dialog where user can search for stocks.
	 * @param m Reference to Main class
	 */
	private void showDialog(Main m){
		//
		Dialog<String> dialog = new Dialog<>();
		VBox box = new VBox();
		ListView<StockResultSet> resultList = new ListView<>();
		resultList.setPrefWidth(400);
		resultList.setCellFactory(new Callback<ListView<StockResultSet>, ListCell<StockResultSet>>(){
            @Override
            public ListCell<StockResultSet> call(ListView<StockResultSet> p) {
                 
                ListCell<StockResultSet> cell = new ListCell<StockResultSet>(){
 
                    @Override
                    protected void updateItem(StockResultSet t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getName() + " (" + t.getExch() + ")");
                            setTooltip(new Tooltip("Market:" + t.getExchDisp() + " (" + t.getExch() + ")"));
                        }
                    }
 
                };
                 
                return cell;
            }
        });
		//
		dialog.setTitle("Lägg till aktie");
		dialog.initModality(Modality.WINDOW_MODAL);
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10,10,10,10));
		TextField quote = new TextField();
		quote.setPromptText("Aktie");
		Button addButton = new Button("Lägg till aktie");
		ButtonType buttonCloseButton = new ButtonType("Stäng", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().add(buttonCloseButton);
		addButton.setOnAction(event -> {
			//add
			StockResultSet selected = resultList.getSelectionModel().getSelectedItem();
			marketCrawler.addCompany(selected.getSymbol());
			m.list.setItems(FXCollections.observableArrayList(m.marketCrawler.getCompanies()));
		});
		Button searchButton = new Button("Sök");
		searchButton.setOnAction(event -> {
				resultList.getItems().clear();
				NotifyingThread t = new NotifyingThread() {
					@Override
					public void doRun() {
						ObservableList<StockResultSet> res = FXCollections.observableArrayList(marketCrawler.searchForQuote(quote.getText()));
						Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								resultList.setItems((res));
							}
						});
					}
				};
				m.executeRunnable(t);
		});
		
		grid.add(new Label("Aktie:"), 0, 0);
		grid.add(quote, 1, 0);
		grid.add(searchButton, 0, 1);
		box.getChildren().addAll(grid,resultList,addButton);
		dialog.getDialogPane().setContent(box);
		dialog.show();

	}
	
	/**
	 * Method that returns the menu
	 * @return Menu returned
	 */
	public MenuBar getMenuBar() {
		return menuBar;
	}
}
