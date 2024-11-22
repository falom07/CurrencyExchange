package Service;

import DAO.ExchangeRatesDAO;
import DTO.ExchangeCurrenciesRateDTO;
import DTO.ExchangeRatesDTO;
import Entity.CurrencyEntity;
import Entity.ExchangeRateEntity;
import Exceptions.DataDoesNotExistException;
import Exceptions.EmptyFieldException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesService {
    private final ExchangeRatesDAO exchangeRatesDAO  = ExchangeRatesDAO.getInstance();

    public ExchangeRatesDTO addExchangeRates(String baseCurrency,String targetCurrency, BigDecimal rate) {
        ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity(
                rate,
                new CurrencyEntity(baseCurrency),
                new CurrencyEntity(targetCurrency)
        );
        ExchangeRateEntity exchangeRate = exchangeRatesDAO.add(exchangeRateEntity);
        return new ExchangeRatesDTO(
                exchangeRate.getBaseCurrency(),
                exchangeRate.getTargetCurrency(),
                exchangeRate.getRate()
        );

    }
    public ExchangeRatesDTO updateExchangeRates(String currencies, BigDecimal rate) {
        String [] currenciesArray = decryptionExchangeCode(currencies);
        ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity(
                rate,
                new CurrencyEntity(currenciesArray[0]),
                new CurrencyEntity(currenciesArray[1])
        );
        ExchangeRateEntity exchangeRate = exchangeRatesDAO.update(exchangeRateEntity);
        return new ExchangeRatesDTO(
                exchangeRate.getBaseCurrency(),
                exchangeRate.getTargetCurrency(),
                exchangeRate.getRate()
        );


    }


    public List<ExchangeRatesDTO> getAllExchangeRates() {
        List<ExchangeRateEntity> exchangeRateEntities = exchangeRatesDAO.readAll();
        List<ExchangeRatesDTO> exchangeRates = new ArrayList<>();
        for (ExchangeRateEntity exchangeRateEntity : exchangeRateEntities) {//todo maybe create separately method
            ExchangeRatesDTO exchangeRatesDTO = new ExchangeRatesDTO(
                    exchangeRateEntity.getBaseCurrency(),
                    exchangeRateEntity.getTargetCurrency(),
                    exchangeRateEntity.getRate());
            exchangeRates.add(exchangeRatesDTO);
        }
        return exchangeRates;
    }
    public ExchangeRatesDTO getExchangeRate(String exchangeCode) {
        String [] decryptedCodes = decryptionExchangeCode(exchangeCode);
        ExchangeRateEntity exchangeRateEntity = exchangeRatesDAO.readOne(decryptedCodes[0], decryptedCodes[1]);
        ExchangeRatesDTO exchangeRatesDTO = new ExchangeRatesDTO(
                exchangeRateEntity.getBaseCurrency(),
                exchangeRateEntity.getTargetCurrency(),
                exchangeRateEntity.getRate());

        return exchangeRatesDTO;
    }
    private String[] decryptionExchangeCode(String exchangeCode) {
        String [] codes = new String[2];
        codes[0] = exchangeCode.substring(0, 3);
        codes[1] = exchangeCode.substring(3, 6);
        return codes;

    }

    public ExchangeCurrenciesRateDTO exchangeCurrencies(ExchangeCurrenciesRateDTO exchangeRatesDTO) {

        ExchangeRateEntity exchangeRateEntity = exchangeRatesDAO.exchangeCurrencyRate(
                new ExchangeRateEntity(
                        exchangeRatesDTO.getBaseCurrency(),
                        exchangeRatesDTO.getTargetCurrency()
                )
        );
        ExchangeCurrenciesRateDTO exchangeCurrenciesRateDTO = new ExchangeCurrenciesRateDTO(
                exchangeRateEntity.getBaseCurrency(),
                exchangeRateEntity.getTargetCurrency(),
                exchangeRateEntity.getRate(),
                exchangeRatesDTO.getAmount(),
                countExchangeRate(exchangeRatesDTO.getAmount(),exchangeRateEntity.getRate())
        );
        return exchangeCurrenciesRateDTO;
    }
    private static BigDecimal countExchangeRate(BigDecimal mount,BigDecimal rate) {
        rate = rate.multiply(mount);
        return rate.setScale(2, RoundingMode.DOWN);
    }
}
