package DAO;


import Entity.CurrencyEntity;
import Entity.ExchangeRateEntity;
import Exceptions.DataAlreadyExistException;
import Exceptions.DataDoesNotExistException;
import Exceptions.EmptyFieldException;
import Exceptions.SomeThingWrongWithBDException;
import Utils.ConnectionManager;

import javax.management.relation.RoleInfoNotFoundException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesDAO implements CrudDAO<ExchangeRateEntity> {
    private ExchangeRatesDAO(){}
    private static final ExchangeRatesDAO instance;
    static{
        instance = new ExchangeRatesDAO();
    }
    public static ExchangeRatesDAO getInstance(){
        return instance;
    }
    @Override
    public ExchangeRateEntity add(ExchangeRateEntity exchangeRateEntity) {
        String sql = """
                insert into ExchangeRates (baseCurrencyId, targetCurrencyId, rate) 
                values ((select id from Currencies where code = ?),
                (select id from Currencies where code = ?),?)
                """;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
             preparedStatement.setString(1,exchangeRateEntity.getBaseCurrency().getCode());
             preparedStatement.setString(2,exchangeRateEntity.getTargetCurrency().getCode());
             preparedStatement.setBigDecimal(3,exchangeRateEntity.getRate());
            System.out.println(preparedStatement.executeUpdate());

        } catch (SQLException e) {
            e.printStackTrace();
            if(e.getMessage().startsWith("SQLITE_CONSTRAINT_UNIQUE", 1)){
                throw new DataAlreadyExistException(exchangeRateEntity.getBaseCurrency().getCode() + exchangeRateEntity.getTargetCurrency().getCode());
            }else if(e.getMessage().startsWith("SQLITE_CONSTRAINT_NOTNULL", 1)){
                throw new DataDoesNotExistException();
            }else if(e.getErrorCode() == 1){
                throw new SomeThingWrongWithBDException();
            }else {
                throw new RuntimeException();
            }
        }
        return readOne(exchangeRateEntity.getBaseCurrency().getCode(),exchangeRateEntity.getTargetCurrency().getCode());

    }
    @Override
    public List<ExchangeRateEntity> readAll() {
        String sql = """
                select ExchangeRates.id as id,baseCur.fullName as bName,baseCur.Sign as bSign,baseCur.code as bCode,tarCur.id as tId,baseCur.id as bId,tarCur.fullName as tName,tarCur.Sign as tSign,tarCur.code as tCode,rate from ExchangeRates
                            join Currencies as baseCur on baseCurrencyId = baseCur.id
                            join Currencies as tarCur on targetCurrencyId = tarCur.id
                """;
        List<ExchangeRateEntity> exchangeRateEntities = new ArrayList<>();
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity(
                        resultSet.getInt("id"),
                        resultSet.getBigDecimal("rate"),
                        new CurrencyEntity(
                                resultSet.getInt("tID"),
                                resultSet.getString("tCode"),
                                resultSet.getString("tName"),
                                resultSet.getString("tSign")
                        ),new CurrencyEntity(
                                resultSet.getInt("bId"),
                                resultSet.getString("bCode"),
                                resultSet.getString("bName"),
                                resultSet.getString("bSign")
                        ));
                exchangeRateEntities.add(exchangeRateEntity);
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1) {
                throw new SomeThingWrongWithBDException();
            }else {
                throw new RuntimeException(e);
            }
        }

        return exchangeRateEntities;
    }

    public ExchangeRateEntity update(ExchangeRateEntity exchangeRateEntity) {
        String sql = """
                UPDATE ExchangeRates
                set rate = ?
                where baseCurrencyId = (select id from Currencies where code = ?) and targetCurrencyId = (select id from Currencies where code = ?);
                """;
        int result;
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setBigDecimal(1,exchangeRateEntity.getRate());
            preparedStatement.setString(2,exchangeRateEntity.getBaseCurrency().getCode());
            preparedStatement.setString(3, exchangeRateEntity.getTargetCurrency().getCode());
            result = preparedStatement.executeUpdate();


        } catch (SQLException e) {
           if (e.getErrorCode() == 1) {
                throw new SomeThingWrongWithBDException();
            }else {
                throw new RuntimeException(e);
            }
        }
        if (result == 0){
            throw new DataDoesNotExistException();
        }
        return readOne(exchangeRateEntity.getBaseCurrency().getCode(),exchangeRateEntity.getTargetCurrency().getCode());
    }

    public ExchangeRateEntity readOne(String baseCurrency, String targetCurrency) {
      String sql = """
              select ExchangeRates.id as id,baseCur.fullName as bName,baseCur.Sign as bSign,baseCur.code as bCode,baseCur.id as bId,tarCur.fullName as tName,tarCur.Sign as tSign,tarCur.id as tId,tarCur.code as tCode,rate from ExchangeRates
              join Currencies as baseCur on baseCurrencyId = baseCur.id
              join Currencies as tarCur on targetCurrencyId = tarCur.id
              where baseCur.code = ? and tarCur.code = ?;
              """;
      ExchangeRateEntity exchangeRateEntity = null;
      try(Connection connection = ConnectionManager.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
          preparedStatement.setString(1, baseCurrency);
          preparedStatement.setString(2, targetCurrency);
          ResultSet resultSet = preparedStatement.executeQuery();
          if (resultSet.next()) {
              exchangeRateEntity = new ExchangeRateEntity(
                      resultSet.getInt("id"),
                      resultSet.getBigDecimal("rate"),
                      new CurrencyEntity(
                              resultSet.getInt("tID"),
                              resultSet.getString("tCode"),
                              resultSet.getString("tName"),
                              resultSet.getString("tSign")
                      ), new CurrencyEntity(
                      resultSet.getInt("bId"),
                      resultSet.getString("bCode"),
                      resultSet.getString("bName"),
                      resultSet.getString("bSign")));
          }

          if (exchangeRateEntity == null) {
              throw new DataDoesNotExistException();
          }
      }catch (SQLException e){
          if (e.getErrorCode() == 1) {
              throw new SomeThingWrongWithBDException();
          }else {
              throw new RuntimeException(e);
          }
      }
      return exchangeRateEntity;

    }

    public ExchangeRateEntity exchangeCurrencyRate (ExchangeRateEntity exchangeRateEntity) {
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
            preparedStatement.setString(1, exchangeRateEntity.getBaseCurrency().getCode());
            preparedStatement.setString(2, exchangeRateEntity.getTargetCurrency().getCode());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                exchangeRateEntity = new ExchangeRateEntity(
                        resultSet.getBigDecimal("rate"),
                        new CurrencyEntity(
                                resultSet.getInt("tID"),
                                resultSet.getString("tCode"),
                                resultSet.getString("tName"),
                                resultSet.getString("tSign")
                        ), new CurrencyEntity(
                        resultSet.getInt("bId"),
                        resultSet.getString("bCode"),
                        resultSet.getString("bName"),
                        resultSet.getString("bSign")
                )
                );
            }
        } catch (SQLException e) {
            if(e.getErrorCode() == 1){
                throw new SomeThingWrongWithBDException();
            }else {
                throw new RuntimeException(e);
            }
        }
        if (exchangeRateEntity.getRate() == null){
            throw new DataDoesNotExistException();
        }
        return exchangeRateEntity;

    }
}
