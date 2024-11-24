package Validation;

import Exceptions.EmptyFieldException;
import Exceptions.IncorrectDataExceptions;

import java.math.BigDecimal;

public final class ValidationServlets { //class for validation
    private ValidationServlets() {}
    public static void checkCodeLengthCurrency(String code) { //check empty and isEmpty
        if(code.isEmpty()) {
            throw new EmptyFieldException();
        }else if(code.length() != 3) {
            throw new IncorrectDataExceptions();
        }
    }
    public static void checkCodeLengthExchangeRates(String code) {
        if(code.isEmpty()) {
            throw new EmptyFieldException();
        }else if(code.length() != 6) {
            throw new IncorrectDataExceptions();
        }
    }

    public static void checkParametersForCorrect(String code,String name,String sign) {
        if(code == null || name == null || sign == null || code.isEmpty() || name.isEmpty() || sign.isEmpty()  ) {
            throw new EmptyFieldException();
        }else if(code.length() != 3) {
            throw new IncorrectDataExceptions();
        }
    }

    public static void checkParametersExchangeForCorrect(String bCode, String tCode, String rate) {
        if(bCode == null || tCode == null || rate == null || bCode.isEmpty() || tCode.isEmpty()) {
            throw new EmptyFieldException();
        }
    }

    public static void checkUpdateParameters(String rate, String currencies) {
        if(rate == null || currencies == null || currencies.isEmpty()) {
            throw new EmptyFieldException();
        }else if(currencies.length() != 6) {
            throw new IncorrectDataExceptions();
        }
    }

    public static void checkParametersForExchange(String from, String to, String amount) {
        if(from == null || to == null || amount == null || from.isEmpty() || to.isEmpty() || amount.isEmpty()) {
            throw new EmptyFieldException();
        }if(from.length() != 3 || to.length() != 3) {
            throw new IncorrectDataExceptions();
        }
        try{
            Integer.parseInt(amount);
        }catch(Exception e){
            throw new IncorrectDataExceptions();
        }
    }
}
