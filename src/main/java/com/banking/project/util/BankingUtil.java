package com.banking.project.util;

import com.banking.project.exception.OperationResultException;
import com.banking.project.model.dto.OperationResult;

public class BankingUtil {

    public static void throwException(OperationResult operationResult) {
        throw new OperationResultException(operationResult);
    }

    /*
    * "hello #p, sen #p yasındasın!"
    * */
    public static String replaceMessage(String message, String... args) {
        if (args != null) {
            for (String value : args) {
                message = message.replaceFirst("#p", value);
            }
        }
        return message;
    }

}
