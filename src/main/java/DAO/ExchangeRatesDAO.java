package DAO;


import Entity.Currency;
import Entity.ExchangeRate;
import Exceptions.DataAlreadyExistException;
import Exceptions.DataDoesNotExistException;

import Exceptions.SomeThingWrongWithBDException;
import Utils.ConnectionManager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesDAO implements CrudDAO<ExchangeRate> {
    private ExchangeRatesDAO(){}
    private static ExchangeRatesDAO instance;

    public static ExchangeRatesDAO getInstance(){
        if(instance == null){
            instance = new ExchangeRatesDAO();
        }
        return instance;
    }

    @Override
    public ExchangeRate add(ExchangeRate exchangeRate) {
        String sql = """
                insert into ExchangeRates (baseCurrencyId, targetCurrencyId, rate) 
                values ((select id from Currencies where code = ?),
                (select id from Currencies where code = ?),?)
                """;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

             preparedStatement.setString(1, exchangeRate.getBaseCurrency().getCode());
             preparedStatement.setString(2, exchangeRate.getTargetCurrency().getCode());
             preparedStatement.setBigDecimal(3, exchangeRate.getRate());

            System.out.println(preparedStatement.executeUpdate());

        } catch (SQLException e) {

            if(e.getMessage().startsWith("SQLITE_CONSTRAINT_UNIQUE", 1)){ //already exist
                throw new DataAlreadyExistException();
            }else if(e.getMessage().startsWith("SQLITE_CONSTRAINT_NOTNULL", 1)){ //some of data does not exist
                throw new DataDoesNotExistException();
            }else if(e.getErrorCode() == 1){
                throw new SomeThingWrongWithBDException();
            }else {
                throw new RuntimeException();
            }
        }

        return readOne(exchangeRate.getBaseCurrency().getCode() + exchangeRate.getTargetCurrency().getCode());
    }


    @Override
    public List<ExchangeRate> readAll() {
        String sql = """
                select ExchangeRates.id as id,baseCur.fullName as bName,baseCur.Sign as bSign,baseCur.code as bCode,tarCur.id as tId,baseCur.id as bId,tarCur.fullName as tName,tarCur.Sign as tSign,tarCur.code as tCode,rate from ExchangeRates
                            join Currencies as baseCur on baseCurrencyId = baseCur.id
                            join Currencies as tarCur on targetCurrencyId = tarCur.id
                """;
        List<ExchangeRate> exchangeRateEntities = new ArrayList<>();

        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ExchangeRate exchangeRate = new ExchangeRate(
                        resultSet.getInt("id"),
                        resultSet.getBigDecimal("rate"),
                        new Currency(
                                resultSet.getInt("tID"),
                                resultSet.getString("tCode"),
                                resultSet.getString("tName"),
                                resultSet.getString("tSign")
                        ),new Currency(
                                resultSet.getInt("bId"),
                                resultSet.getString("bCode"),
                                resultSet.getString("bName"),
                                resultSet.getString("bSign")
                        ));

                exchangeRateEntities.add(exchangeRate);
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1) { //unknown exception
                throw new SomeThingWrongWithBDException();
            }else {
                throw new RuntimeException(e);
            }
        }

        return exchangeRateEntities;
    }


    public ExchangeRate update(ExchangeRate exchangeRate) {
        String sql = """
                UPDATE ExchangeRates
                set rate = ?
                where baseCurrencyId = (select id from Currencies where code = ?) and targetCurrencyId = (select id from Currencies where code = ?);
                """;
        int result; // for info about update

        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setBigDecimal(1, exchangeRate.getRate());
            preparedStatement.setString(2, exchangeRate.getBaseCurrency().getCode());
            preparedStatement.setString(3, exchangeRate.getTargetCurrency().getCode());

            result = preparedStatement.executeUpdate();


        } catch (SQLException e) {
           if (e.getErrorCode() == 1) { //unknown exception
                throw new SomeThingWrongWithBDException();
            }else {
                throw new RuntimeException(e);
            }
        }

        if (result == 0){ //if 0,then we know that data does not exist
            throw new DataDoesNotExistException();
        }

        return readOne(exchangeRate.getBaseCurrency().getCode() + exchangeRate.getTargetCurrency().getCode());
    }

    @Override
    public ExchangeRate readOne(String exchangeCode) {
      String sql = """
              select ExchangeRates.id as id,baseCur.fullName as bName,baseCur.Sign as bSign,baseCur.code as bCode,baseCur.id as bId,tarCur.fullName as tName,tarCur.Sign as tSign,tarCur.id as tId,tarCur.code as tCode,rate from ExchangeRates
              join Currencies as baseCur on baseCurrencyId = baseCur.id
              join Currencies as tarCur on targetCurrencyId = tarCur.id
              where baseCur.code = ? and tarCur.code = ?;
              """;
      ExchangeRate exchangeRate = null;

      try(Connection connection = ConnectionManager.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

          preparedStatement.setString(1, exchangeCode.substring(0, 3));
          preparedStatement.setString(2, exchangeCode.substring(3, 6));

          ResultSet resultSet = preparedStatement.executeQuery();

          if (resultSet.next()) {
              exchangeRate = new ExchangeRate(
                      resultSet.getInt("id"),
                      resultSet.getBigDecimal("rate"),
                      new Currency(
                              resultSet.getInt("tID"),
                              resultSet.getString("tCode"),
                              resultSet.getString("tName"),
                              resultSet.getString("tSign")
                      ), new Currency(
                      resultSet.getInt("bId"),
                      resultSet.getString("bCode"),
                      resultSet.getString("bName"),
                      resultSet.getString("bSign")));
          }

          if (exchangeRate == null) { //for check if data was found
              throw new DataDoesNotExistException();
          }

      }catch (SQLException e){
          if (e.getErrorCode() == 1) { //unknown exception
              throw new SomeThingWrongWithBDException();
          }else {
              throw new RuntimeException(e);
          }
      }

      return exchangeRate;
    }


    public ExchangeRate exchangeCurrencyRate (ExchangeRate exchangeRate) {
        //all logic in sql query,here also possible use much join instead of sub query,but I am stupid
        String sql = """
                
                WITH const AS (SELECT\s
                                   (select id
                                    from Currencies
                                    where code = ?) AS base,
                                   (select id
                                    from Currencies
                                    where code = ?)  AS target)
                select
                       case
                           when exists(
                                select rate
                                from ExchangeRates
                                where baseCurrencyId = base
                                and targetCurrencyId = target
                           ) then (
                                select rate
                                from ExchangeRates
                                where baseCurrencyId = base
                                and targetCurrencyId = target)
                           when exists(select rate
                                       from ExchangeRates
                                       where baseCurrencyId = target
                                         and targetCurrencyId = base
                           ) then (
                                    1.0 / (
                                    select rate
                                    from ExchangeRates
                                    where baseCurrencyId = target
                                    and targetCurrencyId = base)
                           ) when exists(
                                select rate
                                from ExchangeRates
                                where baseCurrencyId = 1
                                and targetCurrencyId = target)
                                and (
                                select rate
                                from ExchangeRates
                                where baseCurrencyId = 1
                                and targetCurrencyId =  base
                           ) then (
                                select rate
                                from ExchangeRates
                                where baseCurrencyId = 1
                                  and targetCurrencyId = target
                           ) / (
                                select rate
                                from ExchangeRates
                                where baseCurrencyId = 1
                                  and targetCurrencyId = base)
                           end as rate,
                       baseCur.fullName as bName,baseCur.Sign as bSign,baseCur.code as bCode,tarCur.id as tId,baseCur.id as bId,tarCur.fullName as tName,tarCur.Sign as tSign,tarCur.code as tCode
                from const
                join Currencies as baseCur on const.base = baseCur.id
                join Currencies as tarCur on const.target = tarCur.id
                
                """;

        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, exchangeRate.getBaseCurrency().getCode());
            preparedStatement.setString(2, exchangeRate.getTargetCurrency().getCode());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                exchangeRate = new ExchangeRate(
                        resultSet.getBigDecimal("rate"),
                        new Currency(
                                resultSet.getInt("tID"),
                                resultSet.getString("tCode"),
                                resultSet.getString("tName"),
                                resultSet.getString("tSign")
                        ), new Currency(
                        resultSet.getInt("bId"),
                        resultSet.getString("bCode"),
                        resultSet.getString("bName"),
                        resultSet.getString("bSign")
                )
                );
            }

        } catch (SQLException e) {
            if(e.getErrorCode() == 1){ //unknown query
                throw new SomeThingWrongWithBDException();
            }else {
                throw new RuntimeException(e);
            }
        }
        if (exchangeRate.getRate() == null){ // check if did not find data
            throw new DataDoesNotExistException();
        }
        return exchangeRate;
    }
}
