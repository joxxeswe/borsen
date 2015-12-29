package com.joxxe.borsen.gui.tabs;

import com.joxxe.borsen.Main;
import com.joxxe.borsen.gui.DataPane;
import com.joxxe.borsen.model.MarketCrawler;
import com.joxxe.borsen.model.Quote;

import javafx.event.EventHandler;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class Tab2 extends Tab {

	private LineChart<String, Number> lineChart;
	private MarketCrawler marketCrawler;
	private ListView<String> list;
	private DataPane dataPane;
	

	public Tab2(Main m) {
		this.marketCrawler = m.marketCrawler;
		this.list = m.list;
		setText("Diagram");
		VBox box2 = new VBox();
		dataPane = new DataPane();
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Värde");
		xAxis.setLabel("Datum");
		// creating the chart
		lineChart = new LineChart<String, Number>(xAxis, yAxis);
		lineChart.setPrefSize(m.root.getWidth() - Main.LIST_WIDTH, m.root.getHeight() - Main.OUTPUT_HEIGHT-Main.DATA_PANE_HEIGHT);
		lineChart.setCreateSymbols(false);
		dataPane.setPrefHeight(Main.DATA_PANE_HEIGHT);
		box2.getChildren().addAll(lineChart,dataPane);
		update();
		setContent(box2);
	}

	public void update() {
		lineChart.setTitle(list.getSelectionModel().getSelectedItem());
		Quote s = marketCrawler.getQuoteAsString(list.getSelectionModel().getSelectedItem());
		if (s != null) {
			lineChart.getData().clear();
			Series<String, Number> series = s.getDataSeries();
			lineChart.getData().add(series);
			for (final Data<String, Number> data : series.getData()) {
				data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						Main.output("Datum " + data.getXValue() + " värde " + data.getYValue());
					}
				});
			}
			dataPane.updateData(s.getQuoteData());
		}

	}
	

	public LineChart<String, Number> getLineChart() {
		return lineChart;
	}

}
