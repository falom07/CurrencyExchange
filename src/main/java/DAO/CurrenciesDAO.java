package DAO;

import Entity.CurrencyEntity;
import Exceptions.DataAlreadyExistException;
import Exceptions.DataDoesNotExistException;
import Exceptions.EmptyFieldException;
import Exceptions.SomeThingWrongWithBDException;
import Utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrenciesDAO implements CrudDAO<CurrencyEntity>{
    private static final CurrenciesDAO currenciesDAO;
    private CurrenciesDAO(){}
    static{
        currenciesDAO = new CurrenciesDAO();
    }
    public static CurrenciesDAO getInstance(){
        return currenciesDAO;
    }



    @Override
    public CurrencyEntity add(CurrencyEntity currencyEntity){
        String sql = "INSERT INTO Currencies(code, fullName, Sign) VALUES (?,?,?)";
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, currencyEntity.getCode());
            preparedStatement.setString(2, currencyEntity.getFullName());
            preparedStatement.setString(3, currencyEntity.getSign());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                throw new DataAlreadyExistException(currencyEntity.getCode());
            }else{
                throw new RuntimeException(e);
            }
        }
        return currencyEntity;
    }

    @Override
    public List<CurrencyEntity> readAll() {
        String sql = "select id,code,fullName,sign from Currencies";
        List<CurrencyEntity> list = new ArrayList<>();
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                CurrencyEntity currencyEntity = new CurrencyEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("code"),
                        resultSet.getString("fullName"),
                        resultSet.getString("sign")
                );
                list.add(currencyEntity);

            }


        } catch (SQLException e) {
            if(e.getErrorCode() == 1) {
                throw new SomeThingWrongWithBDException();
            }else {
                throw new RuntimeException(e);
            }
        }
        return list;
    }



    public CurrencyEntity readOne(String code) {
        String sql = "select code,fullName,Sign from Currencies where code=?";
        CurrencyEntity currency = new CurrencyEntity();
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


        if(currency.getCode() == null) {
            throw new SQLException();
        }

        }catch (SQLException e) {
            if(currency.getCode() == null) {
                throw new DataDoesNotExistException();
            }else if(e.getErrorCode() == 1){
                throw new SomeThingWrongWithBDException();
            }else{
                throw new RuntimeException(e);
            }
        }

        return currency;
    }
}
