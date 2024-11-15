package HTTPTest;

import DAO.CurrenciesDAO;
import DTO.CurrenciesDTO;
import Entity.CurrenciesEntity;

public class Main {
    public static void main(String[] args) {
        CurrenciesDAO currenciesDAO = CurrenciesDAO.getInstance();
        System.out.println(currenciesDAO.add(new CurrenciesEntity(1,"name","full name","77")));
    }
}
