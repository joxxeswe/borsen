package com.joxxe.borsen.gui.tabs;

import com.joxxe.borsen.Main;
import com.joxxe.borsen.gui.DataPane;
import com.joxxe.borsen.gui.candlechart.CandleStickChart;
import com.joxxe.borsen.gui.candlechart.DecimalAxisFormatter;
import com.joxxe.borsen.model.MarketCrawler;
import com.joxxe.borsen.model.stock.Stock;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class Tab2 extends Tab {

	private CandleStickChart chart;
	private MarketCrawler marketCrawler;
	private ListView<String> list;
	private DataPane dataPane;
	

	public Tab2(Main m) {
		this.marketCrawler = m.marketCrawler;
		this.list = m.list;
		setText("Diagram");
		VBox box2 = new VBox();
		dataPane = new DataPane();
		// creating the chart
		String title = list.getSelectionModel().getSelectedItem();
		chart = new CandleStickChart(title);
		chart.setYAxisFormatter(new DecimalAxisFormatter("#000.00"));
		chart.setPrefSize(m.root.getWidth() - Main.LIST_WIDTH, m.root.getHeight() - Main.OUTPUT_HEIGHT-Main.DATA_PANE_HEIGHT);
		dataPane.setPrefHeight(Main.DATA_PANE_HEIGHT);
		box2.getChildren().addAll(chart,dataPane);
		//update();
		setContent(box2);
	}

	public void update() {
		Stock s = marketCrawler.getQuoteAsString(list.getSelectionModel().getSelectedItem());
		dataPane.clearData();
		if (s != null) {
			Main.output("Visa diagram för " + s.getSymbol());
			s.sortQuoteDays(false);
			chart.setData(s.getQuoteDays());
			dataPane.updateData(s.getQuoteData());
		}

	}
	

	public CandleStickChart getLineChart() {
		return chart;
	}

}
