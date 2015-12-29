package model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.joxxe.borsen.gui.HoveredThresholdNode;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class Quote implements Externalizable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4769334868506811919L;
	private SimpleStringProperty symbol;
	private ArrayList<QuoteDay> qouteDays = new ArrayList<QuoteDay>();
	private QuoteData quoteData;

	public Quote(String symbol) {
		this.setSymbol(new SimpleStringProperty(symbol));
	}

	public Quote() {

	}

	public Quote(SimpleStringProperty symbol) {
		this.setSymbol(symbol);
	}

	public String getSymbol() {
		return symbol.get();
	}

	public void setSymbol(SimpleStringProperty symbol) {
		this.symbol = symbol;
	}

	public boolean addQouteDay(QuoteDay q) {
		// check so no day is added two times.
		for (QuoteDay quoteDay : qouteDays) {
			if (quoteDay.equals(q)) {
				return false;
			}
		}
		qouteDays.add(q);
		return true;
	}

	public Series<String, Number> getDataSeries() {
		Series<String, Number> series = new Series<String, Number>();
		series.setName(getSymbol());
		// reverse list
		for (int i = qouteDays.size() - 1; i > 0; i--) {
			SimpleDateFormat f = new SimpleDateFormat("yy-MM-dd");
			QuoteDay q = qouteDays.get(i);
			String date = f.format(q.getDateAsDate());
			Data<String, Number> d = new XYChart.Data<>(date, q.getClose());
			d.setNode(new HoveredThresholdNode(q.getClose()));
			series.getData().add(d);
		}
		return series;
	}

	public ArrayList<QuoteDay> getQuoteDays() {
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
		quoteData = (QuoteData) in.readObject();
		qouteDays = (ArrayList<QuoteDay>) in.readObject();

	}

	public QuoteData getQuoteData(){
		return quoteData;
	}
	public void addQuoteData(QuoteData quoteData) {
		this.quoteData = quoteData;
	}


}
