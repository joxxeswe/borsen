package com.joxxe.borsen.model.stock;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Comparator;

import javafx.beans.property.SimpleStringProperty;

public class Stock implements Externalizable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4769334868506811919L;
	private SimpleStringProperty symbol;
	private ArrayList<StockDayValue> qouteDays = new ArrayList<StockDayValue>();
	private StockData quoteData;

	public Stock(String symbol) {
		this.setSymbol(new SimpleStringProperty(symbol));
	}

	public Stock() {

	}

	public Stock(SimpleStringProperty symbol) {
		this.setSymbol(symbol);
	}

	public String getSymbol() {
		return symbol.get();
	}

	public void setSymbol(SimpleStringProperty symbol) {
		this.symbol = symbol;
	}

	public boolean addQouteDay(StockDayValue q) {
		// check so no day is added two times.
		for (StockDayValue quoteDay : qouteDays) {
			if (quoteDay.equals(q)) {
				return false;
			}
		}
		qouteDays.add(q);
		return true;
	}
	
	public void sortQuoteDays(boolean acending){
		qouteDays.sort(new Comparator<StockDayValue>() {

			@Override
			public int compare(StockDayValue o1, StockDayValue o2) {
				if(o1.getDateAsDate().equals(o2.getDateAsDate())){
					//equal
					return 0;
				}else if(o1.getDateAsDate().before(o2.getDateAsDate())){
					if(acending){
						return 1;
					}else{
						return -1;
					}
				}else{
					if(acending){
						return -1;
					}else{
						return 1;
					}
				}
			}
		});
	}

	public ArrayList<StockDayValue> getQuoteDays() {
		return qouteDays;
	}

	@Override
	public String toString() {
		return "<-- " + symbol.getValue() + "-->\n" + qouteDays.toString() + "\n\n";
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(getSymbol());
		out.writeObject(quoteData);
		out.writeObject(qouteDays);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		setSymbol(new SimpleStringProperty((String) in.readObject()));
		quoteData = (StockData) in.readObject();
		qouteDays = (ArrayList<StockDayValue>) in.readObject();

	}

	public StockData getQuoteData(){
		return quoteData;
	}
	public void addQuoteData(StockData quoteData) {
		this.quoteData = quoteData;
	}


}
