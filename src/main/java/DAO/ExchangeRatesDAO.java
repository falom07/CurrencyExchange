package DAO;

import Entity.ExchangeRatesEntity;

import java.util.List;

public class ExchangeRatesDAO implements CrudDAO<ExchangeRatesEntity> {
    @Override
    public ExchangeRatesEntity add(ExchangeRatesEntity exchangeRatesEntity) {
        return null;
    }

    @Override
    public List<ExchangeRatesEntity> readAll() {
        return List.of();
    }

    @Override
    public void update(ExchangeRatesEntity exchangeRatesEntity) {

    }

//    @Override
//    public void readOne(ExchangeRatesEntity exchangeRatesEntity) {
//
//    }
}
