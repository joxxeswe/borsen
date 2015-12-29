package model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

public class QuoteDay implements Externalizable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1194336592470949744L;
	private SimpleObjectProperty<Date> date;
	private SimpleDoubleProperty volume;
	private SimpleDoubleProperty open;
	private SimpleDoubleProperty close;
	private SimpleDoubleProperty adjClose;
	private SimpleDoubleProperty low;
	private SimpleDoubleProperty high;
	
	public QuoteDay(Date date,SimpleDoubleProperty adjClose,SimpleDoubleProperty close, SimpleDoubleProperty high,SimpleDoubleProperty low,SimpleDoubleProperty open,SimpleDoubleProperty volume){
		setDate(date);
		setAdjClose(adjClose);
		setClose(close);
		setHigh(high);
		setLow(low);
		setOpen(open);
		setVolume(volume);
	}
	
	public QuoteDay(){
		
	}

   
	
	public QuoteDay(Date date2, double adjClose2, double close2, double high2, double low2, double open2,
			double volume2) {
		setDate(date2);
		setAdjClose(new SimpleDoubleProperty(adjClose2));
		setClose(new SimpleDoubleProperty(close2));
		setHigh(new SimpleDoubleProperty(high2));
		setLow(new SimpleDoubleProperty(low2));
		setOpen(new SimpleDoubleProperty(open2));
		setVolume(new SimpleDoubleProperty(volume2));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof QuoteDay) {
			return ((QuoteDay)obj).getDate().equals(this.getDate());
		}
		return false;
	}

	public double getAdjClose() {
		return adjClose.get();
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

	public void setAdjClose(SimpleDoubleProperty adjClose) {
		this.adjClose = adjClose;
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
		return "--> date=" + f.format(date.get()) + ", volume=" + volume + ", open=" + open + ", close=" + close + ", adjClose="
				+ adjClose + ", low=" + low + ", high=" + high + " \n";
	}




	 @Override
	    public void writeExternal(ObjectOutput out) throws IOException {
	        out.writeObject(getDateAsDate());
	        out.writeDouble(getVolume());
	        out.writeDouble(getOpen());
	        out.writeDouble(getClose());
	        out.writeDouble(getAdjClose());
	        out.writeDouble(getLow());
	        out.writeDouble(getHigh());
	    }

	    @Override
	    public void readExternal(ObjectInput in) throws IOException,
	            ClassNotFoundException {
	        setDate((Date)in.readObject());
	        setVolume(new SimpleDoubleProperty(in.readDouble()));
	        setOpen(new SimpleDoubleProperty(in.readDouble()));
	        setClose(new SimpleDoubleProperty(in.readDouble()));
	        setAdjClose(new SimpleDoubleProperty(in.readDouble()));
	        setLow(new SimpleDoubleProperty(in.readDouble()));
	        setHigh(new SimpleDoubleProperty(in.readDouble()));
	    }
}
