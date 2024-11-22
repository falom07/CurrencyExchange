package Service;

import DAO.CurrenciesDAO;
import DTO.CurrenciesDTO;
import Entity.CurrencyEntity;
import Exceptions.EmptyFieldException;
import Exceptions.SomeThingWrongWithBDException;

import java.util.ArrayList;
import java.util.List;

public class CurrenciesService {
    public List<CurrenciesDTO> getAllCurrencies()  {
        List<CurrenciesDTO> currenciesDTOList = new ArrayList<>();
        List<CurrencyEntity> currenciesDAO = CurrenciesDAO.getInstance().readAll();
        for(CurrencyEntity currencyEntity : currenciesDAO){
            currenciesDTOList.add(new CurrenciesDTO(
                    currencyEntity.getCode(),
                    currencyEntity.getFullName(),
                    currencyEntity.getSign()
            ));
        }
        return currenciesDTOList;
    }

    public CurrenciesDTO add (CurrenciesDTO currenciesDTO){
        CurrencyEntity currency = new CurrencyEntity(
                null,
                currenciesDTO.getCode(),
                currenciesDTO.getFullName(),
                currenciesDTO.getSign()
        );
        CurrencyEntity currencyEntity = CurrenciesDAO.getInstance().add(currency);
        return new CurrenciesDTO(
                currencyEntity.getCode(),
                currencyEntity.getFullName(),
                currencyEntity.getSign()
        );
    }


    public CurrenciesDTO getCurrenciesByCode(String code){
        CurrenciesDAO currenciesDAO = CurrenciesDAO.getInstance();
        CurrencyEntity currencyEntity =  currenciesDAO.readOne(code);
        CurrenciesDTO currenciesDTO = new CurrenciesDTO(
                currencyEntity.getCode(),
                currencyEntity.getFullName(),
                currencyEntity.getSign()
        );
        return currenciesDTO;
    }
}
