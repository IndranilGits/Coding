package com.acme.mytrader.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSourceImpl;
import com.db.SecurityDTO;

import lombok.SneakyThrows;

public class TradingStrategyTest {
	@SneakyThrows
	  @Test
	  public void testAutoBuyForSuccessfulBuy() throws InterruptedException {
	    ExecutionService tradeExecutionService = Mockito.mock(ExecutionService.class);
	    PriceSourceImpl priceSource = new MockPriceSource("Indiabulls", 25.00);
	    TradingStrategy tradingStrategy = new TradingStrategy(tradeExecutionService, priceSource);
	    List<SecurityDTO> input = Arrays.asList(SecurityDTO.Builder.newInstance().setSecurity("Indiabulls").setThresholdPrice(25.00).setVolume(10)
	    .build());
	    tradingStrategy.automaticStockBuy(input);
	    assertThat(tradingStrategy);

	  }

	  @SneakyThrows
	  @Test
	  public void testAutoBuyForNotSuccessfulBuy() throws InterruptedException {
	    ExecutionService tradeExecutionService = Mockito.mock(ExecutionService.class);
	    PriceSourceImpl priceSource = new MockPriceSource("Indisbulls", 25.00);

	    TradingStrategy tradingStrategy = new TradingStrategy(tradeExecutionService, priceSource);
	    List<SecurityDTO> input = Arrays.asList(SecurityDTO.Builder.newInstance().setSecurity("BSE").setThresholdPrice(50.00).setVolume(10)
	    		.build());
	    tradingStrategy.automaticStockBuy(input);
	    verifyZeroInteractions(tradeExecutionService);
	  }

	  private class MockPriceSource extends PriceSourceImpl {

	    String security;
	    double price;

	    MockPriceSource(String security, double price) {
	      this.security = security;
	      this.price = price;
	    }

	    private final List<PriceListener> priceListeners = new CopyOnWriteArrayList<>();

	    @Override
	    public void addPriceListener(PriceListener listener) {
	      priceListeners.add(listener);
	    }

	    @Override
	    public void removePriceListener(PriceListener listener) {
	      priceListeners.remove(listener);
	    }

	    @Override
	    public void run() {
	      priceListeners.forEach(priceListener -> priceListener.priceUpdate(security, price));
	    }
	  }
}
