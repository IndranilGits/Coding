package com.acme.mytrader.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.BuyPriceListener;
import com.db.SecurityDTO;

public class BuyPriceListenerTest {

	  //@Test
	  public void testBuy_whenThresholdIsMet() {
	    ExecutionService executionService = Mockito.mock(ExecutionService.class);
	    ArgumentCaptor<String> acString = ArgumentCaptor.forClass(String.class);
	    ArgumentCaptor<Double> acDouble = ArgumentCaptor.forClass(Double.class);
	    ArgumentCaptor<Integer> acInteger = ArgumentCaptor.forClass(Integer.class);

	    BuyPriceListener buyPriceListener = new BuyPriceListener("LSE", 50.00, 100, executionService,
	        false);
	    buyPriceListener.priceUpdate("LSE", 25.00);

	    verify(executionService, times(1))
	        .buy(acString.capture(), acDouble.capture(), acInteger.capture());
	    assertThat(acString.getValue()).as("Should be LSE ")
	        .isEqualTo("LSE");
	    assertThat(acDouble.getValue()).as("Should be the value less than 50.00, that is 25.00")
	        .isEqualTo(25.00);
	    assertThat(acInteger.getValue()).as("Should be the volume purchased").isEqualTo(100);
	    assertThat(buyPriceListener.isBuyPossible("LSE", 25.00))
	        .as("Should be the trade is successfully executed").isTrue();
	  }

	  @Test
	  public void testShouldNotBuy_whenThresholdIsNotMet() {
	    ExecutionService executionService = Mockito.mock(ExecutionService.class);
	    ArgumentCaptor<String> acString = ArgumentCaptor.forClass(String.class);
	    ArgumentCaptor<Double> acDouble = ArgumentCaptor.forClass(Double.class);
	    ArgumentCaptor<Integer> acInteger = ArgumentCaptor.forClass(Integer.class);

	    BuyPriceListener buyPriceListener = new BuyPriceListener("Indiabulls", 50.00, 100, executionService,
	        false);
	    buyPriceListener.priceUpdate("Indiabulls", 55.00);

	    verify(executionService, times(0))
	        .buy(acString.capture(), acDouble.capture(), acInteger.capture());
	    assertThat(buyPriceListener.isBuyPossible("Indiabulls", 55.00))
	        .as("Should be the trade is not successfully executed").isFalse();
	  }

	  @Test
	  public void testShouldNotBuy_whenSecurityIsDifferent() {
	    ExecutionService executionService = Mockito.mock(ExecutionService.class);
	    ArgumentCaptor<String> acString = ArgumentCaptor.forClass(String.class);
	    ArgumentCaptor<Double> acDouble = ArgumentCaptor.forClass(Double.class);
	    ArgumentCaptor<Integer> acInteger = ArgumentCaptor.forClass(Integer.class);

	    BuyPriceListener buyPriceListener = new BuyPriceListener("LSE", 50.00, 100, executionService,
	        false);
	    buyPriceListener.priceUpdate("LSE", 55.00);

	    verify(executionService, times(0))
	        .buy(acString.capture(), acDouble.capture(), acInteger.capture());
	    assertThat(buyPriceListener.isBuyPossible("LSE", 55.00))
	        .as("Should be the trade is not successfully executed").isFalse();
	  }

	  //@Test
	  public void testGivenSeveralPriceUpdates_whenTradeIsAlreadyExecucted_shouldBuyOnlyOnce() {
	    ExecutionService executionService = Mockito.mock(ExecutionService.class);
	    ArgumentCaptor<String> acString = ArgumentCaptor.forClass(String.class);
	    ArgumentCaptor<Double> acDouble = ArgumentCaptor.forClass(Double.class);
	    ArgumentCaptor<Integer> acInteger = ArgumentCaptor.forClass(Integer.class);

	    BuyPriceListener buyPriceListener = new BuyPriceListener("Indiabulls", 50.00, 100, executionService,
	        false);
	    buyPriceListener.priceUpdate("Indiabulls", 25.00);
	    buyPriceListener.priceUpdate("Indiabulls", 10.00);
	    buyPriceListener.priceUpdate("Indiabulls", 35.00);

	    verify(executionService, times(1))
	        .buy(acString.capture(), acDouble.capture(), acInteger.capture());
	    assertThat(acString.getValue()).as("Should be Indiabulls ")
	        .isEqualTo("Indiabulls");
	    assertThat(acDouble.getValue()).as("Should be the value less than 50.00, that is 25.00")
	        .isEqualTo(25.00);
	    assertThat(acInteger.getValue()).as("Should be the volume purchased").isEqualTo(100);
	    assertThat(buyPriceListener.isBuyPossible("Indiabulls", 35.00))
	        .as("Should be the trade is successfully executed").isTrue();
	  }

}
