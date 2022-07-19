package com.acme.mytrader.execution;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TradeExecutionService implements ExecutionService {
	private int id;

	  public TradeExecutionService(int id) {
		this.id= id;
	}

	@Override
	  public void buy(String security, double price, int volume) {
	    System.out.printf("\n BUY Trade executed for %d number of securities", security,
	        price, volume);
	  }

	  @Override
	  public void sell(String security, double price, int volume) {
	    throw new UnsupportedOperationException("Exception Occurred");
	  }
}
