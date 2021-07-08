package com.zxz.common.excel.util;

import com.zxz.common.excel.read.res.ValidResult;
import org.jetbrains.annotations.NotNull;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ValidUtil {


    public static <T> ValidResult valid(T t) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> validate = validator.validate(t);
        ValidResult<T> validResult = new ValidResult<>();
        validResult.setData(t);
        if (CollectionUtil.isEmpty(validate)) {
            validResult.setError(false);
            return validResult;
        }
        Map<String, String> errorMsg = new HashMap<>();
        for (ConstraintViolation<T> constraintViolation : validate) {
            validResult.setError(true);
            errorMsg.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }
        validResult.setErrorMsg(errorMsg);
        return validResult;
    }


    public static <T> ValidResult valid(T t, String fieldName) {
        ValidResult<T> validResult = new ValidResult<>();
        validResult.setData(t);
        return getValidResult(t, fieldName, validResult);
    }

    public static <T> ValidResult valid(T t, String fieldName, ValidResult<T> validResult) {
        return getValidResult(t, fieldName, validResult);
    }

    public static <T> ValidResult valid(T t, String fieldName, ValidResult<T> validResult, int sheetNum, int rowNUm, int cellNum) {
        return getValidResult(t, fieldName, validResult, sheetNum, rowNUm, cellNum);
    }

    @NotNull
    private static <T> ValidResult<T> getValidResult(T t, String fieldName, ValidResult<T> validResult) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> validate = validator.validateProperty(t, fieldName);
        if (CollectionUtil.isEmpty(validate)) {
            validResult.setError(false);
            return validResult;
        }
        Map<String, String> errorMsg = validResult.getErrorMsg();
        for (ConstraintViolation<T> constraintViolation : validate) {
            validResult.setError(true);
            errorMsg.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }
        return validResult;
    }

    @NotNull
    private static <T> ValidResult<T> getValidResult(T t, String fieldName, ValidResult<T> validResult, int sheetNum, int rowNum, int cellNum) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> validate = validator.validateProperty(t, fieldName);
        if (CollectionUtil.isEmpty(validate)) {
            return validResult;
        }
        Map<String, String> errorMsg = validResult.getErrorMsg();
        for (ConstraintViolation<T> constraintViolation : validate) {
            validResult.setError(true);
            StringBuilder msg = new StringBuilder();
            msg.append("第").append(sheetNum).append("页").append("第").append(rowNum).append("行")
                    .append("第").append(cellNum).append("列");
            errorMsg.put(msg.toString(), constraintViolation.getMessage());
        }
        return validResult;
    }
}
