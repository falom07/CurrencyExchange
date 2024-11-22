package Utils;

import jakarta.servlet.http.HttpServletResponse;

public final class ExceptionsHandler {
//    private static ExceptionsHandler exceptionsHandler;
//    private ExceptionsHandler() {
//    }
//    static {
//        exceptionsHandler = new ExceptionsHandler();
//    }
//    static ExceptionsHandler getInstance() {
//        return exceptionsHandler;
//    }
    public static String getErrorMessage(String nameException, HttpServletResponse resp) {
        String errorResponseMessage = "\"message\" : \"";
        switch (nameException) {
            case ("SomeThingWrongWithBDException"): //error 500
                errorResponseMessage += "Something went wrong with DB,i need fix it.Sorry for inconveniences";
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                break;
            case ("DataDoesNotExistException"): // error 404
                errorResponseMessage += "This data does not exist";
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                break;
            case ("DataAlreadyExistException")://error 409
                errorResponseMessage += "This data already exists";
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                break;
            case ("EmptyFieldException"): //error 400
                errorResponseMessage += "You did not indicate all required fields or stay it empty";
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                break;
            case ("IncorrectDataExceptions"): //error 400
                errorResponseMessage += "You have entered an invalid data";
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                break;
            default:
                errorResponseMessage += "I did not add message for this error,but it will added soon...";
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return errorResponseMessage + "\"";
    }

}
