package com.joxxe.borsen.gui.tabs;

import com.joxxe.borsen.Main;
import com.joxxe.borsen.model.MarketCrawler;
import com.joxxe.borsen.model.stock.Stock;
import com.joxxe.borsen.model.stock.StockDayValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class Tab1 extends Tab {

	private MarketCrawler marketCrawler;
	private TableView<StockDayValue> table;
	private ListView<String> list;

	@SuppressWarnings("unchecked")
	public Tab1(Main m){
		this.list = m.list;
		this.marketCrawler = m.marketCrawler;
		setText("Historisk data");
		HBox box = new HBox();

		//table
		table = new TableView<StockDayValue>();
		TableColumn<StockDayValue, String> dateCol = new TableColumn<StockDayValue, String>("Datum");
		dateCol.setCellValueFactory(new PropertyValueFactory<StockDayValue, String>("date"));
		TableColumn<StockDayValue, Double> openCol = new TableColumn<StockDayValue, Double>("Öppning");
		openCol.setCellValueFactory(new PropertyValueFactory<StockDayValue, Double>("open"));
		TableColumn<StockDayValue, Double> closeCol = new TableColumn<StockDayValue, Double>("Stägning");
		closeCol.setCellValueFactory(new PropertyValueFactory<StockDayValue, Double>("close"));
		TableColumn<StockDayValue, Double> highCol = new TableColumn<StockDayValue, Double>("Högsta");
		highCol.setCellValueFactory(new PropertyValueFactory<StockDayValue, Double>("high"));
		TableColumn<StockDayValue, Double> lowCol = new TableColumn<StockDayValue, Double>("Lägsta");
		lowCol.setCellValueFactory(new PropertyValueFactory<StockDayValue, Double>("low"));
		TableColumn<StockDayValue, Double> volCol = new TableColumn<StockDayValue, Double>("Volym");
		volCol.setCellValueFactory(new PropertyValueFactory<StockDayValue, Double>("volume"));
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
			Stock qd = marketCrawler.getQuoteAsString(selected);
			if (qd != null) {
				ObservableList<StockDayValue> qds = FXCollections.observableArrayList(qd.getQuoteDays());
				table.setItems(qds);
				table.getColumns().get(0).setVisible(false);
				table.getColumns().get(0).setVisible(true);
			}else{
				table.setItems(null);
			}
		}
	}
	
	public TableView<StockDayValue> getTable(){
		return table;
	}
}
