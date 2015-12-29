package com.joxxe.borsen.gui.tabs;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.joxxe.borsen.Main;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class Tab0 extends Tab {

	public Tab0(Main m){
		setText("Inställningar");
		VBox box0 = new VBox();
		VBox box1 = getBox1(m);
		box0.getChildren().addAll(box1);
		setContent(box0);
	}
	
	private VBox getBox1(Main m) {
		VBox box = new VBox();
		box.setPadding(new Insets(10));
		Label lblStart = new Label("Startdatum");
		Label lblEnd = new Label("Slutdatum");
		DatePicker startDate = new DatePicker(LocalDate.now());
		startDate.setConverter(getStringConverter());
		DatePicker endDate = new DatePicker(LocalDate.now());
		endDate.setConverter(getStringConverter());
		Button crwlBtn = new Button("Läs in historisk börsdata för vald period");
		crwlBtn.setOnAction(event -> {
			crawlForHistoricalData(m, startDate, endDate);

		});
		box.getChildren().addAll(lblStart, startDate, lblEnd, endDate, crwlBtn);
		return box;
	}

	private void crawlForHistoricalData(Main m, DatePicker startDate, DatePicker endDate) {
		Thread t = new Thread() {
			@Override
			public void run() {
				m.getMarketCrawler().setTimeSpanAsLocalDate(startDate.getValue(), endDate.getValue());
				m.getMarketCrawler().crawlForHistoricalData();
				// change tab and show data
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						m.tabbedPane.getSelectionModel().select(1);
						crawlForData(m);
					}
				});
				m.threads.remove(this);
			}
		};
		m.threads.add(t);
		t.start();
	}

	private void crawlForData(Main m) {
		Thread t = new Thread() {
			@Override
			public void run() {
				m.marketCrawler.crawlForQuoteData();
				// change tab and show data
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						m.tabbedPane.getSelectionModel().select(3);
					}

				});
				m.threads.remove(this);
			}
		};
		m.threads.add(t);
		t.start();
	}

	private StringConverter<LocalDate> getStringConverter() {
		return new javafx.util.StringConverter<LocalDate>() {
			private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			@Override
			public String toString(LocalDate localDate) {
				if (localDate == null)
					return "";
				return dateTimeFormatter.format(localDate);
			}

			@Override
			public LocalDate fromString(String dateString) {
				if (dateString == null || dateString.trim().isEmpty()) {
					return null;
				}
				return LocalDate.parse(dateString, dateTimeFormatter);
			}
		};
	}
}
