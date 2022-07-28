package com.application.utils;

import com.application.exceptions.ServiceException;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.context.FieldContext;
import net.sf.oval.context.OValContext;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CustomValidator {

    private Validator validator = new Validator();

    public void validate(Object object) throws ServiceException {

        List<ConstraintViolation> list = validator.validate(object);

        if (!list.isEmpty()) {

            Map<String, Object> errors = new TreeMap<String, Object>();

            for (ConstraintViolation tmp : list) {
                OValContext context = tmp.getContext();
                if (context instanceof FieldContext) {
                    errors.put(((FieldContext) context).getField().getName(), tmp.getMessage());
                }
            }
            throw new ServiceException(errors);
        }
    }

}
