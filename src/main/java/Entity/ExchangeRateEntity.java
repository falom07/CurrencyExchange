package Entity;

import java.math.BigDecimal;

public class ExchangeRateEntity {
    private Integer id;
    private BigDecimal rate;
    private CurrencyEntity baseCurrency;
    private CurrencyEntity targetCurrency;

    public ExchangeRateEntity(Integer id, BigDecimal rate, CurrencyEntity baseCurrency, CurrencyEntity targetCurrency) {
        this.id = id;
        this.rate = rate;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
    }

    public ExchangeRateEntity(BigDecimal rate, CurrencyEntity baseCurrency, CurrencyEntity targetCurrency) {
        this.rate = rate;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
    }

    public ExchangeRateEntity(CurrencyEntity baseCurrency, CurrencyEntity targetCurrency) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
    }

    public ExchangeRateEntity() {}

    public CurrencyEntity getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(CurrencyEntity baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public CurrencyEntity getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(CurrencyEntity targetCurrency) {
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
