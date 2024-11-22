package DTO;

import Entity.CurrencyEntity;

import java.math.BigDecimal;

public class ExchangeCurrenciesRateDTO {
    private CurrencyEntity baseCurrency;
    private CurrencyEntity targetCurrency;
    private BigDecimal rate;
    private BigDecimal amount;
    private BigDecimal convertAmount;

    public ExchangeCurrenciesRateDTO(CurrencyEntity baseCurrency, CurrencyEntity targetCurrency, BigDecimal rate, BigDecimal amount, BigDecimal convertAmount) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
        this.amount = amount;
        this.convertAmount = convertAmount;
    }

    public ExchangeCurrenciesRateDTO(CurrencyEntity baseCurrency, CurrencyEntity targetCurrency, BigDecimal amount) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
    }

    public ExchangeCurrenciesRateDTO() {
    }

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

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getConvertAmount() {
        return convertAmount;
    }

    public void setConvertAmount(BigDecimal convertAmount) {
        this.convertAmount = convertAmount;
    }
}
