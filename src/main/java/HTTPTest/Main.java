package HTTPTest;

import DAO.CurrenciesDAO;
import Exceptions.SomeThingWrongWithBDException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CurrenciesDAO currenciesDAO = CurrenciesDAO.getInstance();
        System.out.println(currenciesDAO.readAll().get(1));

    }
}
