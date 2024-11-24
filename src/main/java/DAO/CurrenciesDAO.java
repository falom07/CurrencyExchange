package DAO;

import Entity.Currency;
import Exceptions.DataAlreadyExistException;
import Exceptions.DataDoesNotExistException;
import Exceptions.SomeThingWrongWithBDException;
import Utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CurrenciesDAO implements CrudDAO<Currency>{
    private static CurrenciesDAO currenciesDAO;
    private CurrenciesDAO(){}

    public static CurrenciesDAO getInstance(){
        if(currenciesDAO == null){
            currenciesDAO =  new CurrenciesDAO();
        }
        return currenciesDAO;
    }



    @Override
    public Currency add(Currency currency)  {
        String sql = "INSERT INTO Currencies(code, fullName, Sign) VALUES (?,?,?)";

        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, currency.getCode());
            preparedStatement.setString(2, currency.getFullName());
            preparedStatement.setString(3, currency.getSign());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            if (e.getErrorCode() == 19) { // exception unique
                throw new DataAlreadyExistException();
            }else{
                throw new RuntimeException(e);
            }
        }
        return currency;
    }

    @Override
    public List<Currency> readAll() {
        String sql = "select id,code,fullName,sign from Currencies";
        List<Currency> list = new ArrayList<>();

        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Currency currency = new Currency(
                        resultSet.getInt("id"),
                        resultSet.getString("code"),
                        resultSet.getString("fullName"),
                        resultSet.getString("sign")
                );
                list.add(currency);

            }

        } catch (SQLException e) {
            if(e.getErrorCode() == 1) {  //unknown exception
                throw new SomeThingWrongWithBDException();
            }else {
                throw new RuntimeException(e);
            }
        }

        return list;
    }


    @Override
    public Currency readOne(String code) {
        String sql = "select code,fullName,Sign from Currencies where code=?";
        Currency currency = new Currency();

        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, code);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            if(resultSet.next()){
                currency.setCode(resultSet.getString("code"));
                currency.setFullName(resultSet.getString("fullName"));
                currency.setSign(resultSet.getString("Sign"));
            }


        if(currency.getCode() == null) { // if data does not exist
            throw new SQLException();
        }

        }catch (SQLException e) {
            if(currency.getCode() == null) {
                throw new DataDoesNotExistException();
            }else if(e.getErrorCode() == 1){ //unknown exception
                throw new SomeThingWrongWithBDException();
            }else{
                throw new RuntimeException(e);
            }
        }

        return currency;
    }
}
