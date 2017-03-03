package com.joxxe.borsen.model.stock;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

public class StockDayValue implements Externalizable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1194336592470949744L;
	private SimpleObjectProperty<Date> date;
	private SimpleDoubleProperty volume;
	private SimpleDoubleProperty open;
	private SimpleDoubleProperty close;
	private SimpleDoubleProperty low;
	private SimpleDoubleProperty high;
	
	public StockDayValue(Date date,SimpleDoubleProperty close, SimpleDoubleProperty high,SimpleDoubleProperty low,SimpleDoubleProperty open,SimpleDoubleProperty volume){
		setDate(date);
		setClose(close);
		setHigh(high);
		setLow(low);
		setOpen(open);
		setVolume(volume);
	}
	
	public StockDayValue(){
		
	}

   
	
	public StockDayValue(Date date, double close, double high, double low, double open,
			double volume) {
		setDate(date);
		setClose(new SimpleDoubleProperty(close));
		setHigh(new SimpleDoubleProperty(high));
		setLow(new SimpleDoubleProperty(low));
		setOpen(new SimpleDoubleProperty(open));
		setVolume(new SimpleDoubleProperty(volume));
	}

	public StockDayValue(Date date, double close, double high, double low, double open) {
		setDate(date);
		setClose(new SimpleDoubleProperty(close));
		setHigh(new SimpleDoubleProperty(high));
		setLow(new SimpleDoubleProperty(low));
		setOpen(new SimpleDoubleProperty(open));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StockDayValue) {
			return ((StockDayValue)obj).getDate().equals(this.getDate());
		}
		return false;
	}

	public double getClose() {
		return close.get();
	}

	public Date getDateAsDate() {
		return date.get();
	}
	
	public String getDate(){
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.format(date.get());
	}

	public double getHigh() {
		return high.get();
	}

	public double getLow() {
		return low.get();
	}

	public double getOpen() {
		return open.get();
	}

	public double getVolume() {
		return volume.get();
	}

	public void setClose(SimpleDoubleProperty close) {
		this.close = close;
	}

	public void setDate(Date date) {
		this.date = new SimpleObjectProperty<Date>(date);
	}

	public void setHigh(SimpleDoubleProperty high) {
		this.high = high;
	}

	public void setLow(SimpleDoubleProperty low) {
		this.low = low;
	}

	public void setOpen(SimpleDoubleProperty open) {
		this.open = open;
	}

	public void setVolume(SimpleDoubleProperty volume) {
		this.volume = volume;
	}
	


	@Override
	public String toString() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return "--> date=" + f.format(date.get()) + ", volume=" + volume + ", open=" + open + ", close=" + close + ", low=" + low + ", high=" + high + " \n";
	}




	 @Override
	    public void writeExternal(ObjectOutput out) throws IOException {
	        out.writeObject(getDateAsDate());
	        
	        out.writeDouble(getOpen());
	        out.writeDouble(getClose());
	        out.writeDouble(getLow());
	        out.writeDouble(getHigh());
	        out.writeDouble(getVolume());
	    }

	    @Override
	    public void readExternal(ObjectInput in) throws IOException,
	            ClassNotFoundException {
	        setDate((Date)in.readObject());
	        
	        setOpen(new SimpleDoubleProperty(in.readDouble()));
	        setClose(new SimpleDoubleProperty(in.readDouble()));
	        setLow(new SimpleDoubleProperty(in.readDouble()));
	        setHigh(new SimpleDoubleProperty(in.readDouble()));
	        setVolume(new SimpleDoubleProperty(in.readDouble())); //TODO opptional
	    }
}
