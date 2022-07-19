package com.acme.mytrader.price;

import com.acme.mytrader.execution.ExecutionService;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class BuyPriceListener implements PriceListener{
	
	  private ExecutionService executionService;
	  private final String security="";
	  private final double triggerLevel=0.0;
	  private final int purchaseQty=0;
	  private boolean istradeExecuted;
	

	public BuyPriceListener() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BuyPriceListener(ExecutionService executionService, boolean istradeExecuted) {
		super();
		this.executionService = executionService;
		this.istradeExecuted = istradeExecuted;
	}
	
	

	public BuyPriceListener(String security2, double thresholdPrice, int volume, ExecutionService tradeExecutionService,
			boolean b) {
		// TODO Auto-generated constructor stub
	}

	@Override
	  public void priceUpdate(String security, double price) {
	    if (isBuyPossible(security, price)) {
	      executionService.buy(security, price, purchaseQty);
	      istradeExecuted = true;
	    }
	  }
	
	public boolean isBuyPossible(String security, double price) {
	    return (!istradeExecuted) && this.security.equals(security) && (price < this.triggerLevel);
	  }

}
