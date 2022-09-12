package com.application.service.impl;

import com.application.dao.EmployeesDAO;
import com.application.dao.MediaDAO;
import com.application.exceptions.ServiceException;
import com.application.model.EmployeeModel;
import com.application.utils.CustomValidator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Double.isNaN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class DefaultEmployeeServiceTest {

    @InjectMocks
    private DefaultEmployeeService employeeService;

    @Mock
    private EmployeesDAO employeesDAO;
    @Mock
    private CustomValidator validator;
    @Mock
    private MediaDAO mediaDAO;

    private EmployeeModel employeeForId;
    private EmployeeModel employee;
    private FileInputStream inputStream;


    private MockHttpServletRequest request;

    @Before
    public void setUp() {
        employeeForId = new EmployeeModel();
        employeeForId.setId(1);
        employeeForId.setFirstName("John");
        employeeForId.setLastName("Doe");

        employee = new EmployeeModel();
        employee.setId(5);
        employee.setEmail("depe@gmail.com");
        employee.setFirstName("De");
        employee.setLastName("Pe");
        employee.setDateBirth("1980-05-05");

        request = new MockHttpServletRequest();
        request.setParameter("departmentId", "20");
        request.setParameter("id", "25");
        request.setParameter("firstName", "De");
        request.setParameter("lastName", "Pe");
        request.setParameter("dateBirth", "1980-01-01");
        request.setParameter("email", "dedfsdpe@gmail.com");
    }

    @After
    public void destroy() {
        employeeForId = null;
    }

    @Test
    public void checkExistingEmployeeEmailTest() {
        String email = employee.getEmail();
        Mockito.when(employeesDAO.checkExistingEmployeeEmail(email)).thenReturn(employee);

        boolean ifEmailExists = employeeService.checkExistingEmployeeEmail(email);
        assertTrue(ifEmailExists);
    }

    @Test
    public void createEditEmployeeTest() throws ServiceException {
        employeeService.createEditEmployee(employee);
        Mockito.verify(validator).validate(employee);
        Mockito.verify(employeesDAO).createUpdateEmployee(employee);
    }

    @Test
    public void getRandomIdTest() {
        int randomId = employeeService.getRandomId();

        assertTrue("Result is not a number", !isNaN(randomId));
    }

    @Test
    public void checkExistingEmployeeIdTest() {
        int id = employee.getId();
        Mockito.when(employeesDAO.getEmployeeForId(id)).thenReturn(employee);

        boolean ifEmployeeExists = employeeService.checkExistingEmployeeId(id);

        assertNotNull(ifEmployeeExists);
        assertTrue("Employee doesn't exist", ifEmployeeExists);
    }


    @Test
    public void getEmployeeForIdTest() {
        int id = 1;
        Mockito.when(employeesDAO.getEmployeeForId(1)).thenReturn(employeeForId);

        EmployeeModel employeeForId = employeeService.getEmployeeForId(id);

        assertNotNull(employeeForId);
        assertEquals("EmployeeModel does not match expected result", id, employeeForId.getId());
    }

    @Test
    public void getEmployeeForIdTest2() {
        int id = 33;
        employeeForId.setId(id);
        Mockito.when(employeesDAO.getEmployeeForId(id)).thenReturn(employeeForId);

        EmployeeModel employeeForId = employeeService.getEmployeeForId(id);

        assertNotNull(employeeForId);
        assertEquals("EmployeeModel does not match expected result", id, employeeForId.getId());
    }

    @Test
    public void removeEmployeeTest() {
        int id = 1;
        Mockito.when(employeesDAO.getEmployeeForId(1)).thenReturn(employee);

        EmployeeModel employeeModel = employeeService.getEmployeeForId(id);
        List<EmployeeModel> employeeModels = new ArrayList<>();
        employeeModels.add(employeeModel);

        employeeModels = employeeService.removeEmployee(id);

        Boolean result = true;
        for (int i = 0; i < employeeModels.size(); i++) {
            if (employeeModels.get(i).getId() == id) {
                result = false;
                break;
            }
        }
        assertTrue(result);
    }

    @Test
    public void convertRequestToEmployee() {
        int id = Integer.parseInt(Objects.requireNonNull(request.getParameter("id")));
        System.out.println(request.getParameter("id"));
        EmployeeModel employeeModel = employeeService.convertRequestToEmployee(request);
        Assert.assertEquals("Request and Model are not the same", employeeModel.getId(),id);
    }

    @Test
    public void saveUpdatePhotoTest() throws FileNotFoundException {
        inputStream = new FileInputStream("/home/diana/Downloads/di.jpg");
        Mockito.when(employeesDAO.getEmployeeForId(1)).thenReturn(employee);

        int id = 1;
        int photoId = 5;
        employeeService.saveUpdatePhoto(inputStream, id, photoId);
    }
}
