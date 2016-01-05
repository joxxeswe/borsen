package com.joxxe.borsen.model.stock;

import javafx.beans.property.SimpleStringProperty;

public class StockResultSet {

	private SimpleStringProperty name;
	private SimpleStringProperty symbol;
	private SimpleStringProperty exch;
	private SimpleStringProperty exchDisp;
	
	public StockResultSet(String name,String symbol,String exch,String exchDisp){
		this.name = new SimpleStringProperty(name);
		this.symbol = new SimpleStringProperty(symbol);
		this.exch = new SimpleStringProperty(exch);
		this.exchDisp =  new SimpleStringProperty(exchDisp);
	}

	public String getName() {
		return name.get();
	}

	public String getSymbol() {
		return symbol.get();
	}

	public String getExch() {
		return exch.get();
	}

	public String getExchDisp() {
		return exchDisp.get();
	}
}
