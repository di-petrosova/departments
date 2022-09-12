package com.application.service.impl;

import com.application.dao.DepartmentsDAO;
import com.application.data.EmployeeData;
import com.application.exceptions.ServiceException;
import com.application.model.DepartmentModel;


import com.application.utils.CustomValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.isNaN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class DefaultDepartmentServiceTest {

    @InjectMocks
    private DefaultDepartmentService departmentService;

    @Mock
    private DepartmentsDAO departmentsDAO;

    @Mock
    private CustomValidator validator;

    private List<DepartmentModel> departmentModels = new ArrayList<>();
    private DepartmentModel department;
    private List<EmployeeData> employees = new ArrayList<>();


    @Before
    public void setUp() {
        DepartmentModel departmentModel = new DepartmentModel();
        departmentModel.setId(255);
        departmentModel.setName("Department1");
        departmentModel.setPk(566);
        departmentModel.setAddress(560006);
        departmentModels.add(departmentModel);

        department = new DepartmentModel();
        department.setId(1);
        department.setPk(5);
        department.setAddress(555444);
        department.setName("New dep");

        EmployeeData employee = new EmployeeData();
        employee.setEmpEmail("ng@gmail.com");
        employee.setEmpFirstName("Ki");
        employee.setEmpLastName("Ra");
        employees.add(employee);
    }

    @After
    public void destroy() {

    }

    @Test
    public void getAllDepartmentsTest() {
        List<DepartmentModel> departmentModelsExpected = new ArrayList<>();
        DepartmentModel departmentModel = new DepartmentModel();
        departmentModel.setId(255);
        departmentModel.setName("Department1");
        departmentModel.setPk(566);
        departmentModel.setAddress(560006);
        departmentModelsExpected.add(departmentModel);

        Mockito.when(departmentsDAO.getAllDepartments()).thenReturn(departmentModels);

        List<DepartmentModel> allDepartments = departmentService.getAllDepartments();

        assertNotNull(departmentModel);
        assertEquals("DepartmentModel does not match expected result", departmentModelsExpected.get(0).getId(), allDepartments.get(0).getId());
    }

    @Test
    public void getRandomIdTest() {
        int randomId = departmentService.getRandomId();

        assertNotNull(randomId);
        assertTrue("Result is not a number", !isNaN(randomId));
    }

    @Test
    public void checkExistingDepartmentIdTest() {
        int id = department.getId();
        Mockito.when(departmentsDAO.getDepartmentForId(id)).thenReturn(department);

        boolean ifDepartmentExists = departmentService.checkExistingDepartmentId(id);

        assertNotNull(ifDepartmentExists);
        assertTrue("Department doesn't exist", ifDepartmentExists);
    }

    @Test
    public void createEditDepartmentTest() throws ServiceException {
        departmentService.createEditDepartment(department);
        Mockito.verify(validator).validate(department);
        Mockito.verify(departmentsDAO).createEditDepartment(department);
    }

    @Test
    public void getDepartmentForIdTest() {
        int id = 1;
        Mockito.when(departmentsDAO.getDepartmentForId(1)).thenReturn(department);

        DepartmentModel departmentForId = departmentService.getDepartmentForId(id);

        assertNotNull(departmentForId);
        assertEquals("Departments Model does not match expected result", id, departmentForId.getId());
    }

    @Test
    public void removeDepartmentTest() {
        int id = 1;
        String strI = Integer.toString(id);
        Mockito.when(departmentsDAO.getDepartmentForId(1)).thenReturn(department);

        DepartmentModel departmentForId = departmentService.getDepartmentForId(id);
        List<DepartmentModel> departmentModels = new ArrayList<>();
        departmentModels.add(departmentForId);

        departmentModels = departmentService.removeDepartment(strI);

        Boolean result = true;
        for (int i = 0; i < departmentModels.size(); i++) {
            if (departmentModels.get(i).getId() == id) {
                result = false;
                break;
            }
        }
        assertTrue(result);
    }

/*    @Test
    public void getEmployeesForDepartmentIdTest() {
        int id = 1;
//        doReturn(employees).when(departmentService).getEmployeesForDepartmentId(id);
        Mockito.when(departmentsDAO.getDepartmentForId(id)).thenReturn(department);

        List<EmployeeData> employeesForDepartmentId = departmentService.getEmployeesForDepartmentId(id);
        Assert.assertEquals("Employees list is not the same", employeesForDepartmentId, employees);
    }*/
}
