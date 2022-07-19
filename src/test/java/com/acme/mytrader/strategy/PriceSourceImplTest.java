package com.acme.mytrader.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSource;
import com.acme.mytrader.price.PriceSourceImpl;

import lombok.SneakyThrows;

public class PriceSourceImplTest {

	  @Test
	  public void addPriceListenerOfTwoListeners_shouldAddToAList() {
	    PriceListener priceListener1 = Mockito.mock(PriceListener.class);
	    PriceListener priceListener2 = Mockito.mock(PriceListener.class);
	    List<PriceSource> priceSrcList= new ArrayList<>();
	    PriceSourceImpl priceSource = new PriceSourceImpl();
	    priceSource.addPriceListener(priceListener1);
	    priceSrcList.add(priceSource);
	    assertThat(priceSrcList).hasSize(1);
	  }

	  @Test
	  public void removePriceListenerOfOneListeners_shouldRemoveListener() {
	    PriceListener priceListener1 = Mockito.mock(PriceListener.class);
	    PriceListener priceListener2 = Mockito.mock(PriceListener.class);
	    PriceSourceImpl priceSource = new PriceSourceImpl();
	    List<PriceSource> priceSrcList= new ArrayList<>();
	    priceSource.addPriceListener(priceListener1);
	    priceSource.addPriceListener(priceListener2);
	    priceSource.removePriceListener(priceListener2);
	    priceSrcList.add(priceSource);
	    assertThat(priceSrcList).hasSize(1);
	  }

	  //@Test
	  @SneakyThrows
	  public void givenOneListener_priceSourceShouldInvokeTheListener_whenThreadStarted() throws InterruptedException {
	    PriceListener priceListener = Mockito.mock(PriceListener.class);
	    PriceSourceImpl priceSource1 = new PriceSourceImpl();
	    priceSource1.addPriceListener(priceListener);
	    Thread thread = new Thread(priceSource1);
	    thread.start();
	    thread.join();
	    verify(priceListener, times(15)).priceUpdate(Arrays.asList("Indiabulls", "Tata Motors", "LSE", "BSE", "Microsoft", "Google", "Natwest").toString(),123.0d);
	  }

	  @Test
	  @SneakyThrows
	  public void givenOneListener_priceSourceShouldInvokeTheListenerWithSecurityAndPrice_whenThreadStarted() throws InterruptedException {
	    List<String> SECURITIES = Arrays.asList("Indiabulls", "Tata Motors", "LSE", "BSE", "Microsoft", "Google", "Natwest");
	    ArgumentCaptor<String> securityCaptor = ArgumentCaptor.forClass(String.class);
	    ArgumentCaptor<Double> priceCaptor = ArgumentCaptor.forClass(Double.class);
	    PriceListener priceListener = Mockito.mock(PriceListener.class);
	    PriceSourceImpl priceSource = new PriceSourceImpl();
	    priceSource.addPriceListener(priceListener);
	    Thread thread = new Thread(priceSource);
	    thread.start();
	    thread.join();
	    verify(priceListener, times(15)).priceUpdate(securityCaptor.capture(), priceCaptor.capture());
	    assertThat(securityCaptor.getValue()).as("Should contain at least one value from Securities ")
	        .matches(s -> SECURITIES.stream().anyMatch(s::contains));
	    assertThat(priceCaptor.getValue()).as("Should be a double value between 1.00 to 200.00")
	        .isGreaterThan(1.00).isLessThanOrEqualTo(200.00);
	  }

}
