package Service;

import DAO.ExchangeRatesDAO;
import DTO.ExchangeCurrenciesRateDTO;
import DTO.ExchangeRatesDTO;
import Entity.Currency;
import Entity.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesService {
    private final ExchangeRatesDAO exchangeRatesDAO  = ExchangeRatesDAO.getInstance();

    public ExchangeRatesDTO addExchangeRates(String baseCurrency,String targetCurrency, BigDecimal rate) {
        ExchangeRate exchangeRateEntity = new ExchangeRate(
                rate,
                new Currency(baseCurrency),
                new Currency(targetCurrency)
        );

        ExchangeRate exchangeRate = exchangeRatesDAO.add(exchangeRateEntity);

        return new ExchangeRatesDTO(
                exchangeRate.getBaseCurrency(),
                exchangeRate.getTargetCurrency(),
                exchangeRate.getRate()
        );

    }


    public ExchangeRatesDTO updateExchangeRates(String currencies, BigDecimal rate) {
        String currenciesBase = currencies.substring(0, 3);    //take two codes for query
        String currenciesTarget = currencies.substring(3, 6);
        ExchangeRate exchangeRateEntity = new ExchangeRate(
                rate,
                new Currency(currenciesBase),
                new Currency(currenciesTarget)
        );

        ExchangeRate exchangeRate = exchangeRatesDAO.update(exchangeRateEntity);

        return new ExchangeRatesDTO(
                exchangeRate.getBaseCurrency(),
                exchangeRate.getTargetCurrency(),
                exchangeRate.getRate()
        );


    }


    public List<ExchangeRatesDTO> getAllExchangeRates() {
        List<ExchangeRate> exchangeRateEntities = exchangeRatesDAO.readAll();
        List<ExchangeRatesDTO> exchangeRates = new ArrayList<>();

        for (ExchangeRate exchangeRate : exchangeRateEntities) {
            ExchangeRatesDTO exchangeRatesDTO = new ExchangeRatesDTO(
                    exchangeRate.getBaseCurrency(),
                    exchangeRate.getTargetCurrency(),
                    exchangeRate.getRate());
            exchangeRates.add(exchangeRatesDTO);
        }

        return exchangeRates;
    }


    public ExchangeRatesDTO getExchangeRate(String exchangeCode) {
        ExchangeRate exchangeRate = exchangeRatesDAO.readOne(exchangeCode);

        ExchangeRatesDTO exchangeRatesDTO = new ExchangeRatesDTO(
                exchangeRate.getBaseCurrency(),
                exchangeRate.getTargetCurrency(),
                exchangeRate.getRate());

        return exchangeRatesDTO;
    }



    public ExchangeCurrenciesRateDTO exchangeCurrencies(String from, String to, BigDecimal amount) {
        ExchangeCurrenciesRateDTO exchangeRatesDTO = new ExchangeCurrenciesRateDTO(
                new Currency(from),
                new Currency(to),
                amount
        );

        ExchangeRate exchangeRate = exchangeRatesDAO.exchangeCurrencyRate(
                new ExchangeRate(
                        exchangeRatesDTO.getBaseCurrency(),
                        exchangeRatesDTO.getTargetCurrency()
                )
        );

        return new ExchangeCurrenciesRateDTO(
                exchangeRate.getBaseCurrency(),
                exchangeRate.getTargetCurrency(),
                exchangeRate.getRate(),
                exchangeRatesDTO.getAmount(),
                countExchangeRate(exchangeRatesDTO.getAmount(), exchangeRate.getRate())
        );
    }


    private static BigDecimal countExchangeRate(BigDecimal mount,BigDecimal rate) {
        rate = rate.multiply(mount);
        return rate.setScale(2, RoundingMode.DOWN);
    }
}
