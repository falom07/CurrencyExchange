package Service;

import DAO.CurrenciesDAO;
import DTO.CurrenciesDTO;
import Entity.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrenciesService {
    private final CurrenciesDAO currenciesDAO = CurrenciesDAO.getInstance();


    public List<CurrenciesDTO> getAllCurrencies()  {
        List<CurrenciesDTO> currenciesDTOList = new ArrayList<>();
        List<Currency> currenciesDAOList = currenciesDAO.readAll();

        for(Currency currency : currenciesDAOList){
            currenciesDTOList.add(new CurrenciesDTO(
                    currency.getCode(),
                    currency.getFullName(),
                    currency.getSign()
            ));
        }
        return currenciesDTOList;
    }

    public CurrenciesDTO add (CurrenciesDTO currenciesDTO){
        Currency currency = new Currency(
                null,
                currenciesDTO.getCode(),
                currenciesDTO.getFullName(),
                currenciesDTO.getSign()
        );

        Currency currencyEntity = currenciesDAO.add(currency);

        return new CurrenciesDTO(
                currencyEntity.getCode(),
                currencyEntity.getFullName(),
                currencyEntity.getSign()
        );
    }


    public CurrenciesDTO getCurrenciesByCode(String code){
        Currency currency =  currenciesDAO.readOne(code);

        return new CurrenciesDTO(
                currency.getCode(),
                currency.getFullName(),
                currency.getSign()
        );
    }
}
