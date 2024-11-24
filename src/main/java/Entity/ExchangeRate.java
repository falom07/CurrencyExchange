package Entity;

import java.math.BigDecimal;

public class ExchangeRate {
    private Integer id;
    private BigDecimal rate;
    private Currency baseCurrency;
    private Currency targetCurrency;

    public ExchangeRate(Integer id, BigDecimal rate, Currency baseCurrency, Currency targetCurrency) {
        this.id = id;
        this.rate = rate;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
    }

    public ExchangeRate(BigDecimal rate, Currency baseCurrency, Currency targetCurrency) {
        this.rate = rate;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
    }

    public ExchangeRate(Currency baseCurrency, Currency targetCurrency) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
    }

    public ExchangeRate() {}

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

}
