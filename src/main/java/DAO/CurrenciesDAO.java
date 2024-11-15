package DAO;

import Entity.CurrenciesEntity;
import Utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrenciesDAO implements CrudDAO<CurrenciesEntity>{
    private static final CurrenciesDAO currenciesDAO = new CurrenciesDAO();
    private CurrenciesDAO(){}
    public static CurrenciesDAO getInstance(){
        return currenciesDAO;
    }



    @Override
    public CurrenciesEntity add(CurrenciesEntity currencyEntity) {
        String sql = "INSERT INTO Currencies(code, fullName, Sign) VALUES (?,?,?)";
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, currencyEntity.getCode());
            preparedStatement.setString(2, currencyEntity.getFullName());
            preparedStatement.setString(3, currencyEntity.getSign());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<CurrenciesEntity> readAll() {
        String sql = "select id,code,fullName,sign from Currencies";
        List<CurrenciesEntity> list = new ArrayList<>();
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                CurrenciesEntity currenciesEntity = new CurrenciesEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("code"),
                        resultSet.getString("fullName"),
                        resultSet.getString("sign")
                );
                list.add(currenciesEntity);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void update(CurrenciesEntity currenciesEntity) {

    }


    public CurrenciesEntity readOne(int id) {
        String sql = "select code,fullName,Sign from Currencies where id=?";
        CurrenciesEntity currency = new CurrenciesEntity();
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                currency.setCode(resultSet.getString("code"));
                currency.setCode(resultSet.getString("fullName"));
                currency.setCode(resultSet.getString("Sign"));
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currency;
    }
}
