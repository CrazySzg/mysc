package com.sensetime.kafka_action;

import java.io.Serializable;

public class StockQuotationInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3144243267444068484L;
	private String stockName;
	private String stockCode;
	private long tradeTime;
	private float preClosePrice;
	private float openPrice;
	private float currentPrice;
	private float highPrice;
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public long getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(long tradeTime) {
		this.tradeTime = tradeTime;
	}
	public float getPreClosePrice() {
		return preClosePrice;
	}
	public void setPreClosePrice(float preClosePrice) {
		this.preClosePrice = preClosePrice;
	}
	public float getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(float openPrice) {
		this.openPrice = openPrice;
	}
	public float getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(float currentPrice) {
		this.currentPrice = currentPrice;
	}
	public float getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(float highPrice) {
		this.highPrice = highPrice;
	}
	@Override
	public String toString() {
		return "StockQuotationInfo [stockName=" + stockName + ", stockCode=" + stockCode + ", tradeTime=" + tradeTime
				+ ", preClosePrice=" + preClosePrice + ", openPrice=" + openPrice + ", currentPrice=" + currentPrice
				+ ", highPrice=" + highPrice + "]";
	}
	
}
