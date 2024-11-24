package HTTPTest;

import DAO.CurrenciesDAO;
import Entity.CurrencyEntity;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        File file = new File("identifier.sqlite");

        // Get absolute path
        String absolutePath = file.getAbsolutePath();
        System.out.println(  absolutePath + "src/main/resources/db/identifier.sqlite");

    }
}
