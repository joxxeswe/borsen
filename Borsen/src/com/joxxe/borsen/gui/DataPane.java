package com.joxxe.borsen.gui;


import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import com.joxxe.borsen.model.QuoteData;

import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class DataPane extends Pane {
	private Label valueName;
	private Label valuePE;
	private Label valueEbitda;
	private Label valueEarningsshare;
	private Label valueExDividendDate;
	private Label valuePEGRatio;
	private Label valueYearLow;
	private Label valueYearHigh;
	private Label valueStockExchange;

	public DataPane() {
		getChildren().add(getPane());
	}

	
	private GridPane getPane(){
		
		GridPane pane = new GridPane();
		pane.setVgap(5);
		pane.setHgap(5);
		pane.setPadding(new Insets(10, 10, 10, 10));
		
		Label lblName = new Label("Name");
		lblName.setPrefWidth(150);
		Label lblStockExchange = new Label("Marknad");
		lblStockExchange.setPrefWidth(150);
		Label lblPE = new Label("PE");
		Label lblEarningsShare = new Label("Earningsshare");
		Label lblEbitda = new Label("EBITDA");
		Label lblexDividendDate = new Label("Senast utdelning");
		Label lblPEGRatio = new Label("PEGRatio");
		Label lblYearLow = new Label("Årslägsta");
		Label lblYearHigh = new Label("Årshögsta");
		valueName = new Label();
		valueName.setPrefWidth(150);
		valueStockExchange = new Label();
		valueStockExchange.setPrefWidth(150);
		valuePE = new Label();
		valueEbitda = new Label();
		valueEarningsshare = new Label();
		valueExDividendDate = new Label();
		valuePEGRatio = new Label();
		valueYearLow = new Label();
		valueYearHigh = new Label();
		
		pane.add(lblName,0,0);
		pane.add(valueName,1,0);
		
		pane.add(lblStockExchange,2,0);
		pane.add(valueStockExchange,3,0);

		pane.add(lblPE,0,1);
		pane.add(valuePE,1,1);
		
		pane.add(lblPEGRatio,2,1);
		pane.add(valuePEGRatio,3,1);
		
		pane.add(lblEbitda, 0, 2);
		pane.add(valueEbitda, 1, 2);
		
		pane.add(lblEarningsShare, 2, 2);
		pane.add(valueEarningsshare, 3, 2);
		
		pane.add(lblexDividendDate, 0, 3);
		pane.add(valueExDividendDate, 1,3);
		
		pane.add(lblYearLow, 0, 4);
		pane.add(valueYearLow, 1, 4);
		
		pane.add(lblYearHigh, 2, 4);
		pane.add(valueYearHigh, 3,4);
		
		return pane;
	}

	public void updateData(QuoteData quoteData){
			valueName.setText(quoteData.getName());
			valuePE.setText(""+quoteData.getPeRatio());
			valueEarningsshare.setText(""+ quoteData.getEarningsShare());
			valueEbitda.setText("" + quoteData.getEbitda());
			valueExDividendDate.setText(quoteData.getExDividendDate());
			valuePEGRatio.setText("" + quoteData.getPegRatio());
			valueYearHigh.setText(""+ quoteData.getYearHigh());
			valueYearLow.setText(""+ quoteData.getYearLow());
			valueStockExchange.setText(quoteData.getStockExchange());
		
	}

}
