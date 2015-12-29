package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.joxxe.borsen.Main;

public class MarketCrawler {

	private static String URL = "https://query.yahooapis.com/v1/public/yql?q=FRÅGA&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
	private String urlForHistoricalData;
	private String queryForHistoricalData;
	private ArrayList<String> companies;
	private ArrayList<Quote> quotes = new ArrayList<Quote>();
	private String queryForQuoteData;
	private String urlForQuoteData;

	public MarketCrawler(ArrayList<String> c) {
		this.companies = c;
		this.queryForQuoteData = "select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20("
				+ getStringForCompanies(companies) + ")";
		urlForQuoteData = URL.replace("FRÅGA", queryForQuoteData);
	}

	public Quote getQuote(int index) {
		if (quotes != null && quotes.size() > index) {
			return quotes.get(index);
		}
		return null;
	}

	public Quote getQuoteAsString(String quote) {
		if (quote == null) {
			return null;
		}
		if (quotes.isEmpty()) {
			return null;
		}
		for (Quote q : quotes) {
			if (q.getSymbol().equalsIgnoreCase(quote)) {
				return q;
			}
		}
		return null;
	}

	private Quote getQuote(String quote) {
		for (Quote q : quotes) {
			if (q.getSymbol().equalsIgnoreCase(quote)) {
				return q;
			}
		}
		Quote q = new Quote(quote);
		quotes.add(q);
		return q;
	}

	public void crawlForQuoteData() {
		if (quotes.isEmpty()) {
			Main.output("Läs in historisk data först!");
		} else {
			JSONObject obj;
			try {
				Main.output("Fråga:\n" + urlForQuoteData);
				Main.output("Läser in data, vänligen vänta");
				obj = new JSONObject(IOUtils.toString(new URL(urlForQuoteData), Charset.forName("UTF-8")));
				JSONObject q = obj.getJSONObject("query");
				if (!q.isNull("results")) {
					JSONObject results = q.getJSONObject("results");
					JSONArray quote = results.getJSONArray("quote");
					for (Object d : quote) {
						JSONObject jsonLineItem = (JSONObject) d;
						String symbol = getString("Symbol",jsonLineItem);
						double averageDailyVolume = getDouble("AverageDailyVolume",jsonLineItem);
						double bookValue = getDouble("BookValue",jsonLineItem);
						String currency = getString("Currency",jsonLineItem);
						String lastTradeDate = getString("LastTradeDate",jsonLineItem);
						double earningsShare = getDouble("EarningsShare",jsonLineItem);
						double ePSEstimateCurrentYear = getDouble("EPSEstimateCurrentYear",jsonLineItem);
						double ePSEstimateNextYear = getDouble("EPSEstimateNextYear",jsonLineItem);
						double ePSEstimateNextQuarter = getDouble("EPSEstimateNextQuarter",jsonLineItem);
						double yearLow = getDouble("YearLow",jsonLineItem);
						double yearHigh = getDouble("YearHigh",jsonLineItem);
						String marketCapitalization = getString("MarketCapitalization",jsonLineItem);
						String ebitda = getString("EBITDA",jsonLineItem);
						double changeFromYearLow = getDouble("ChangeFromYearLow",jsonLineItem);
						String percentChangeFromYearLow = getString("PercentChangeFromYearLow",jsonLineItem);
						double changeFromYearHigh = getDouble("ChangeFromYearHigh",jsonLineItem);
						String percebtChangeFromYearHigh =getString("PercebtChangeFromYearHigh",jsonLineItem);
						double fiftydayMovingAverage =  getDouble("FiftydayMovingAverage",jsonLineItem);
						double twoHundreddayMovingAverage = getDouble("TwoHundreddayMovingAverage",jsonLineItem);
						double changeFromTwoHundreddayMovingAverage = getDouble("ChangeFromTwoHundreddayMovingAverage",jsonLineItem);
						String percentChangeFromTwoHundreddayMovingAverage = getString("PercentChangeFromTwoHundreddayMovingAverage",jsonLineItem);
						double changeFromFiftydayMovingAverage = getDouble("ChangeFromFiftydayMovingAverage",jsonLineItem);
						String percentChangeFromFiftydayMovingAverage = getString("PercentChangeFromFiftydayMovingAverage",jsonLineItem);
						String name = getString("Name",jsonLineItem);
						double priceSales = getDouble("PriceSales",jsonLineItem);
						double priceBook = getDouble("PriceBook",jsonLineItem);
						String exDividendDate =getString("ExDividendDate",jsonLineItem);
						double pegRatio = getDouble("PEGRatio",jsonLineItem);
						double peRatio = getDouble("PERatio",jsonLineItem);
						double volume = getDouble("Volume",jsonLineItem);
						String yearRange = getString("YearRange",jsonLineItem);
						String stockExchange = getString("StockExchange",jsonLineItem);
						String percentChange = getString("PercentChange",jsonLineItem);
						// get quote, do not create a new one if not found!
						Quote actualQuote = getQuoteAsString(symbol);
						if (actualQuote != null) {
							QuoteData quoteData = new QuoteData(averageDailyVolume, bookValue, currency, lastTradeDate,
									earningsShare, ePSEstimateCurrentYear, ePSEstimateNextQuarter, ePSEstimateNextYear,
									yearLow, yearHigh, marketCapitalization, ebitda, changeFromYearLow,
									percentChangeFromYearLow, changeFromYearHigh, percebtChangeFromYearHigh,
									fiftydayMovingAverage, twoHundreddayMovingAverage,
									changeFromTwoHundreddayMovingAverage, percentChangeFromTwoHundreddayMovingAverage,
									changeFromFiftydayMovingAverage, percentChangeFromFiftydayMovingAverage, name,
									priceSales, priceBook, exDividendDate, pegRatio, peRatio, volume, yearRange,
									stockExchange, percentChange);
							actualQuote.addQuoteData(quoteData);
						} else {
							Main.output("Hittade inte aktien");
						}
					}
					Main.output("Data inläst!");
				} else {
					Main.output("Inget resultat, pröva att läsa in en kortare period");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String getString(String string, JSONObject jsonLineItem) {
		if(jsonLineItem.has(string) && !jsonLineItem.isNull(string)){
			return jsonLineItem.getString(string);
		}
		return null;
	}
	
	private double getDouble(String string, JSONObject jsonLineItem) {
		if(jsonLineItem.has(string) && !jsonLineItem.isNull(string)){
			return jsonLineItem.getDouble(string);
		}
		return 0;
	}

	public void crawlForHistoricalData() {
		if (queryForHistoricalData != null) {
			JSONObject obj;
			try {
				Main.output("Fråga:\n" + urlForHistoricalData);
				Main.output("Läser in data, vänligen vänta");
				obj = new JSONObject(IOUtils.toString(new URL(urlForHistoricalData), Charset.forName("UTF-8")));
				JSONObject q = obj.getJSONObject("query");
				if (!q.isNull("results")) {
					JSONObject results = q.getJSONObject("results");
					JSONArray days = results.getJSONArray("quote");
					for (Object d : days) {
						JSONObject jsonLineItem = (JSONObject) d;
						String symbol = getString("Symbol", jsonLineItem);
						String dateString = getString("Date", jsonLineItem);
						Date date = getDateFromString(dateString);
						if (date != null) {
							double volume = getDouble("Volume", jsonLineItem);
							double open =  getDouble("Open", jsonLineItem);
							double close = getDouble("Close", jsonLineItem);
							double adjClose = getDouble("Adj_Close", jsonLineItem);
							double low = getDouble("Low", jsonLineItem);
							double high = getDouble("High", jsonLineItem);
							// TODO check for invalid date
							Quote quote = getQuote(symbol);
							QuoteDay qd = new QuoteDay(date, adjClose, close, high, low, open, volume);
							quote.addQouteDay(qd);
						}
					}
					Main.output("Data inläst!");
				} else {
					Main.output("Inget resultat, pröva att läsa in en kortare period");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Main.output("Sätt en tidsperiod innan du laddar datan!");
		}
	}

	@Override
	public String toString() {
		String s = "RESULTS\n";
		for (Quote q : quotes) {
			s += q.toString();
		}
		return s;
	}

	private Date getDateFromString(String dateString) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse(dateString);
		} catch (Exception ex) {
			System.err.print(ex);
		}
		return date;

	}

	private static String getStringForCompanies(ArrayList<String> companies2) {
		String ret = "";
		for (int i = 0; i < companies2.size(); i++) {
			ret += "%22" + companies2.get(i) + "%22,";
		}
		// remove last ,
		return ret.substring(0, ret.length() - 1);
	}

	private String getStringFromInt(int nr) {
		if (nr < 10) {
			return "0" + nr;
		} else {
			return "" + nr;
		}
	}

	public void setTimeSpan(int syear, int smonth, int sday, int eyear, int emonth, int eday) {
		// rebuild query
		String stringStartMonth = getStringFromInt(smonth);
		String stringStartDay = getStringFromInt(sday);
		String stringEndMonth = getStringFromInt(emonth);
		String stringEndDay = getStringFromInt(eday);
		rebuildQuery(syear + "-" + stringStartMonth + "-" + stringStartDay,
				eyear + "-" + stringEndMonth + "-" + stringEndDay);
	}

	private void rebuildQuery(String stringStartDate, String stringEndDate) {
		queryForHistoricalData = "select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20in%20("
				+ getStringForCompanies(companies) + ")%20and%20startDate%20%3D%20%22" + stringStartDate
				+ "%22%20and%20endDate%20%3D%20%22" + stringEndDate + "%22";
		urlForHistoricalData = URL.replace("FRÅGA", queryForHistoricalData);
	}

	public ArrayList<String> getCompanies() {
		return companies;
	}

	public void setTimeSpanAsLocalDate(LocalDate startDate, LocalDate endDate) {
		setTimeSpan(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth(), endDate.getYear(),
				endDate.getMonthValue(), endDate.getDayOfMonth());

	}

	public void save() {
		try {
			Main.output("Sparar data");
			OutputStream file = new FileOutputStream("marketdata.ser");
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			output.writeObject(quotes);
			output.close();
			file.close();
			Main.output("Data sparad");
		} catch (FileNotFoundException e) {
			Main.output("Fel när data skulle sparas:" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Main.output("Fel när data skulle sparas:" + e.getMessage());
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public void open() {
		try {
			Main.output("Läser in sparad data");
			FileInputStream file = new FileInputStream("marketdata.ser");
			BufferedInputStream buffer = new BufferedInputStream(file);
			ObjectInputStream input = new ObjectInputStream(buffer);
			quotes = (ArrayList<Quote>) input.readObject();
			input.close();
			file.close();
			Main.output("Sparad data inläst");
		} catch (FileNotFoundException e) {
			Main.output("Hittade ingen sprad data");
		} catch (IOException e) {
			Main.output("Fel när data skulle laddas:" + e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			Main.output("Fel när data skulle laddas:" + e.getMessage());
			e.printStackTrace();
		}

	}

	public void clear() {
		Main.output("Rensar data (även sparad)");
		quotes.clear();
		File file = new File("marketdata.ser");
		if (file.delete()) {
			Main.output(file.getName() + " borttagen!");
		} else {
			Main.output("Kunde inte tabort data som sparats på disk, däremot är datan i programmet rensad.");
		}
	}
}
