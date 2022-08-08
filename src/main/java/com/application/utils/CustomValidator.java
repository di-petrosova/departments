package com.application.utils;

import com.application.exceptions.ServiceException;
import com.application.model.EmployeeModel;
import com.application.service.EmployeeService;
import com.application.service.impl.DefaultEmployeeService;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.context.FieldContext;
import net.sf.oval.context.OValContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class CustomValidator {

    @Autowired
    private EmployeeService employeeService;

    private Validator validator = new Validator();

    public void validate(Object object) throws ServiceException {

        List<ConstraintViolation> list = validator.validate(object);
        Map<String, Object> errors = new TreeMap<String, Object>();

        if (object instanceof EmployeeModel) {
            EmployeeModel employeeModel = (EmployeeModel) object;
            boolean exists = employeeService.checkExistingEmployeeEmail((employeeModel).getEmail());
            EmployeeModel existingEmployee = employeeService.getEmployeeForId(employeeModel.getId());

            if ((existingEmployee == null || !employeeModel.getEmail().equals(existingEmployee.getEmail())) && exists) {
                errors.put("email", "This email is busy. Choose another one");
            }
        }

        if (!list.isEmpty() || !errors.isEmpty()) {

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
