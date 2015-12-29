package com.joxxe.borsen.gui.tabs;

import com.joxxe.borsen.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.MarketCrawler;
import model.Quote;
import model.QuoteDay;

public class Tab1 extends Tab {

	private MarketCrawler marketCrawler;
	private TableView<QuoteDay> table;
	private ListView<String> list;

	@SuppressWarnings("unchecked")
	public Tab1(Main m){
		this.list = m.list;
		this.marketCrawler = m.marketCrawler;
		setText("Historisk data");
		HBox box = new HBox();

		//table
		table = new TableView<QuoteDay>();
		TableColumn<QuoteDay, String> dateCol = new TableColumn<QuoteDay, String>("Datum");
		dateCol.setCellValueFactory(new PropertyValueFactory<QuoteDay, String>("date"));
		TableColumn<QuoteDay, Double> openCol = new TableColumn<QuoteDay, Double>("Öppning");
		openCol.setCellValueFactory(new PropertyValueFactory<QuoteDay, Double>("open"));
		TableColumn<QuoteDay, Double> closeCol = new TableColumn<QuoteDay, Double>("Stägning");
		closeCol.setCellValueFactory(new PropertyValueFactory<QuoteDay, Double>("close"));
		TableColumn<QuoteDay, Double> highCol = new TableColumn<QuoteDay, Double>("Högsta");
		highCol.setCellValueFactory(new PropertyValueFactory<QuoteDay, Double>("high"));
		TableColumn<QuoteDay, Double> lowCol = new TableColumn<QuoteDay, Double>("Lägsta");
		lowCol.setCellValueFactory(new PropertyValueFactory<QuoteDay, Double>("low"));
		TableColumn<QuoteDay, Double> volCol = new TableColumn<QuoteDay, Double>("Volym");
		volCol.setCellValueFactory(new PropertyValueFactory<QuoteDay, Double>("volume"));
		table.getColumns().addAll(dateCol, openCol, closeCol, highCol, lowCol, volCol);
		updateTableData();
		table.setPrefWidth(m.root.getWidth() - Main.LIST_WIDTH);
		//
		box.getChildren().add(table);
		setContent(box);
	}
	
	public void updateTableData() {
		String selected = list.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Quote qd = marketCrawler.getQuoteAsString(selected);
			if (qd != null) {
				ObservableList<QuoteDay> qds = FXCollections.observableArrayList(qd.getQuoteDays());
				table.setItems(qds);
				table.getColumns().get(0).setVisible(false);
				table.getColumns().get(0).setVisible(true);
			}
		}
	}
	
	public TableView<QuoteDay> getTable(){
		return table;
	}
}
