package com.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode

public class SecurityDTO {
	  private String security;
	  private double thresholdPrice;
	  private int volume;
	public String getSecurity() {
		return security;
	}
	public double getThresholdPrice() {
		return thresholdPrice;
	}
	public int getVolume() {
		return volume;
	}
	
	public SecurityDTO(Builder builder)
    {
        this.security = builder.security;
        this.thresholdPrice = builder.thresholdPrice;
        this.volume = builder.volume;
    }
	

//	public SecurityDTO(String security, double thresholdPrice, int volume) {
//		this.security = security;
//        this.thresholdPrice = thresholdPrice;
//        this.volume = volume;
//	}


	// Static class Builder
    public static class Builder {
  
        /// instance fields
        private String security;
        private double thresholdPrice;
        private int volume;
  
        public static Builder newInstance()
        {
            return new Builder();
        }
  
        private Builder() {}
  
        // Setter methods
        public Builder setSecurity(String security)
        {
            this.security = security;
            return this;
        }
        public Builder setThresholdPrice(double thresholdPrice)
        {
            this.thresholdPrice = thresholdPrice;
            return this;
        }
        public Builder setVolume(int volume)
        {
            this.volume = volume;
            return this;
        }
  
        // build method to deal with outer class
        // to return outer instance
        public SecurityDTO build()
        {
            return new SecurityDTO(this);
        }
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((security == null) ? 0 : security.hashCode());
		long temp;
		temp = Double.doubleToLongBits(thresholdPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + volume;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SecurityDTO other = (SecurityDTO) obj;
		if (security == null) {
			if (other.security != null)
				return false;
		} else if (!security.equals(other.security))
			return false;
		if (Double.doubleToLongBits(thresholdPrice) != Double.doubleToLongBits(other.thresholdPrice))
			return false;
		if (volume != other.volume)
			return false;
		return true;
	}
    
	  
}
