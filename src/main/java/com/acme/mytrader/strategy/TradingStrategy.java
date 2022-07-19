package com.acme.mytrader.strategy;

import java.util.Arrays;
import java.util.List;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.execution.TradeExecutionService;
import com.acme.mytrader.price.BuyPriceListener;
import com.acme.mytrader.price.PriceSourceImpl;
import com.acme.mytrader.price.RunnablePrice;
import com.db.SecurityDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */
@AllArgsConstructor
@Getter
public class TradingStrategy{

	  private ExecutionService tradeExecutionService;
	  private RunnablePrice runnablePrice;
	  private int id;

	public TradingStrategy(ExecutionService tradeExecutionService2, PriceSourceImpl priceSource) {
		this.tradeExecutionService= tradeExecutionService2;
		this.runnablePrice= priceSource;
	}

	public void automaticStockBuy(List<SecurityDTO> request) throws InterruptedException {

	    request.stream().map(
	        r -> new BuyPriceListener(r.getSecurity(),r.getThresholdPrice(), r.getVolume(),
	            tradeExecutionService, false)).forEach(runnablePrice::addPriceListener);
	    Thread thread = new Thread(runnablePrice);
	    thread.start();
	    thread.join();
	    request.stream().map(
	        r -> new BuyPriceListener(r.getSecurity(),r.getThresholdPrice(), r.getVolume(),
	            tradeExecutionService, false)).forEach(runnablePrice::removePriceListener);
	  }

	  //Main program to execute automatic order generation
	  public static void main(String[] args) throws InterruptedException {
	    TradingStrategy tradingStrategy = new TradingStrategy(new TradeExecutionService(1),
	        new PriceSourceImpl());
	    final SecurityDTO indiabulls = SecurityDTO.Builder.newInstance().setSecurity("Indiabulls").setThresholdPrice(100.00).setVolume(12)
	        .build();
	    final SecurityDTO lse = SecurityDTO.Builder.newInstance().setSecurity("LSE").setThresholdPrice(100.00).setVolume(24)
		        .build();
	    tradingStrategy.automaticStockBuy(Arrays.asList(indiabulls, lse));
	  }

}

