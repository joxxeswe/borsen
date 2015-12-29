package model;

import java.io.Serializable;

public class QuoteData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5906590119022943321L;
	private double averageDailyVolume;
	private double bookValue;
	private String currency;
	private String lastTradeDate;
	private double earningsShare;
	private double ePSEstimateCurrentYear;
	private double ePSEstimateNextQuarter;
	private double ePSEstimateNextYear;
	private double yearLow;
	private double yearHigh;
	private String marketCapitalization;
	private String ebitda;
	private double changeFromYearHigh;
	private String percentChangeFromYearLow;
	private double fiftydayMovingAverage;
	private String percebtChangeFromYearHigh;
	private double twoHundreddayMovingAverage;
	private double changeFromTwoHundreddayMovingAverage;
	private String percentChangeFromTwoHundreddayMovingAverage;
	private double changeFromFiftydayMovingAverage;
	private String percentChangeFromFiftydayMovingAverage;
	private String name;
	private double priceSales;
	private double priceBook;
	private String exDividendDate;
	private double pegRatio;
	private double peRatio;
	private double volume;
	private String percentChange;
	private String stockExchange;

	public QuoteData(){
		
	}

	public QuoteData(double averageDailyVolume, double bookValue, String currency, String lastTradeDate,
			double earningsShare, double ePSEstimateCurrentYear, double ePSEstimateNextQuarter,
			double ePSEstimateNextYear, double yearLow, double yearHigh, String marketCapitalization, String ebitda,
			double changeFromYearLow, String percentChangeFromYearLow, double changeFromYearHigh,
			String percebtChangeFromYearHigh, double fiftydayMovingAverage, double twoHundreddayMovingAverage,
			double changeFromTwoHundreddayMovingAverage, String percentChangeFromTwoHundreddayMovingAverage,
			double changeFromFiftydayMovingAverage, String percentChangeFromFiftydayMovingAverage, String name,
			double priceSales, double priceBook, String exDividendDate, double pegRatio, double peRatio, double volume,
			String yearRange, String stockExchange, String percentChange) {
		this.averageDailyVolume = averageDailyVolume;
		this.bookValue = bookValue;
		this.stockExchange = stockExchange;
		this.currency = currency;
		this.lastTradeDate = lastTradeDate;
		this.earningsShare = earningsShare;
		this.ePSEstimateCurrentYear = ePSEstimateCurrentYear;
		this.ePSEstimateNextQuarter = ePSEstimateNextQuarter;
		this.ePSEstimateNextYear = ePSEstimateNextYear;
		this.yearLow = yearLow;
		this.yearHigh = yearHigh;
		this.marketCapitalization = marketCapitalization;
		this.ebitda = ebitda;
		this.changeFromYearHigh = changeFromYearLow;
		this.percentChangeFromYearLow = percentChangeFromYearLow;
		this.changeFromYearHigh = changeFromYearHigh;
		this.percebtChangeFromYearHigh = percebtChangeFromYearHigh;
		this.fiftydayMovingAverage = fiftydayMovingAverage;
		this.twoHundreddayMovingAverage = twoHundreddayMovingAverage;
		this.changeFromTwoHundreddayMovingAverage = changeFromTwoHundreddayMovingAverage;
		this.percentChangeFromTwoHundreddayMovingAverage = percentChangeFromTwoHundreddayMovingAverage;
		this.changeFromFiftydayMovingAverage = changeFromFiftydayMovingAverage;
		this.percentChangeFromFiftydayMovingAverage = percentChangeFromFiftydayMovingAverage;
		this.name = name;
		this.priceSales = priceSales;
		this.priceBook = priceBook;
		this.exDividendDate = exDividendDate;
		this.pegRatio = pegRatio;
		this.peRatio = peRatio;
		this.volume = volume;
		this.percentChange = percentChange;
	}

	public double getAverageDailyVolume() {
		return averageDailyVolume;
	}

	public double getBookValue() {
		return bookValue;
	}

	public String getCurrency() {
		return currency;
	}

	public String getLastTradeDate() {
		return lastTradeDate;
	}

	public double getEarningsShare() {
		return earningsShare;
	}

	public double getePSEstimateCurrentYear() {
		return ePSEstimateCurrentYear;
	}

	public double getePSEstimateNextQuarter() {
		return ePSEstimateNextQuarter;
	}

	public double getePSEstimateNextYear() {
		return ePSEstimateNextYear;
	}

	public double getYearLow() {
		return yearLow;
	}

	public double getYearHigh() {
		return yearHigh;
	}

	public String getMarketCapitalization() {
		return marketCapitalization;
	}

	public String getEbitda() {
		return ebitda;
	}

	public double getChangeFromYearHigh() {
		return changeFromYearHigh;
	}

	public String getPercentChangeFromYearLow() {
		return percentChangeFromYearLow;
	}

	public double getFiftydayMovingAverage() {
		return fiftydayMovingAverage;
	}

	public String getPercebtChangeFromYearHigh() {
		return percebtChangeFromYearHigh;
	}

	public double getTwoHundreddayMovingAverage() {
		return twoHundreddayMovingAverage;
	}

	public double getChangeFromTwoHundreddayMovingAverage() {
		return changeFromTwoHundreddayMovingAverage;
	}

	public String getPercentChangeFromTwoHundreddayMovingAverage() {
		return percentChangeFromTwoHundreddayMovingAverage;
	}

	public double getChangeFromFiftydayMovingAverage() {
		return changeFromFiftydayMovingAverage;
	}

	public String getPercentChangeFromFiftydayMovingAverage() {
		return percentChangeFromFiftydayMovingAverage;
	}

	public String getName() {
		return name;
	}

	public double getPriceSales() {
		return priceSales;
	}

	public double getPriceBook() {
		return priceBook;
	}

	public String getExDividendDate() {
		return exDividendDate;
	}

	public double getPegRatio() {
		return pegRatio;
	}

	public double getPeRatio() {
		return peRatio;
	}

	public double getVolume() {
		return volume;
	}

	public String getPercentChange() {
		return percentChange;
	}

	public String getStockExchange() {
		return stockExchange;
	}

}
