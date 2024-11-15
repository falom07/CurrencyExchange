package Service;

import DAO.CurrenciesDAO;
import DTO.CurrenciesDTO;
import Entity.CurrenciesEntity;

import java.util.ArrayList;
import java.util.List;

public class CurrenciesService {
    public List<CurrenciesDTO> getAllCurrencies(){
        List<CurrenciesDTO> currenciesDTOList = new ArrayList<>();
        List<CurrenciesEntity> currenciesDAO = CurrenciesDAO.getInstance().readAll();
        for(CurrenciesEntity currenciesEntity : currenciesDAO){
            currenciesDTOList.add(new CurrenciesDTO(
                    currenciesEntity.getCode(),
                    currenciesEntity.getFullName(),
                    currenciesEntity.getSign()
            ));
        }
        return currenciesDTOList;
    }

    public void add (CurrenciesDTO currenciesDTO){
        CurrenciesEntity currencie = new CurrenciesEntity(
                null,
                currenciesDTO.getCode(),
                currenciesDTO.getFullName(),
                currenciesDTO.getSign()
        );
        CurrenciesDAO.getInstance().add(currencie);
    }
    public CurrenciesDTO getCurrenciesById(int id){
        CurrenciesDAO currenciesDAO = CurrenciesDAO.getInstance();
        CurrenciesEntity currencyEntity =  currenciesDAO.readOne(id);
        CurrenciesDTO currenciesDTO = new CurrenciesDTO(
                currencyEntity.getCode(),
                currencyEntity.getFullName(),
                currencyEntity.getSign()
        );

        return currenciesDTO;
    }
}
