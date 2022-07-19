package com.acme.mytrader.price;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PriceSourceImpl implements RunnablePrice{
	
	//For Multi threading we are using ConcurrentArrayList- CopyOnWriteArrayList
	// which takes the PriceListener as type
	private final List<PriceListener> priceListeners = new CopyOnWriteArrayList<>();

	  private static final List<String> SECURITIES = Arrays
	      .asList("Indiabulls", "Tata Motors", "LSE", "BSE", "Microsoft", "Google", "Natwest");

	  private static final double MINIMUM_RANGE = 1.00;
	  private static final double MAXIMUM_RANGE = 300.00;

	  @Override
	  public void addPriceListener(PriceListener listener) {
	    this.priceListeners.add(listener);
	  }

	  @Override
	  public void removePriceListener(PriceListener listener) {
	    this.priceListeners.remove(listener);
	  }

	  
	  /**
	   * Implementing  run() of Runnable interface, to generate random numbers for the securities
	  * Calculating the price by getting the difference between Max and min values
	  and then updating the stock price with the price for each events those are being listened by price listener
	  */
	  @Override
	  public void run() {
	    Random rand = new Random();
	    for (int i = 0; i < 15; i++) {
	      String security = SECURITIES.get(rand.nextInt(SECURITIES.size()));
	      double price = MAXIMUM_RANGE + (MINIMUM_RANGE - MAXIMUM_RANGE) * rand.nextDouble();
	      priceListeners.forEach(priceListener -> priceListener.priceUpdate(security, price));
	    }
	  }

}
