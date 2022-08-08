package com.application.controller;

import com.application.dao.EmployeesDAO;
import com.application.dao.MediaDAO;
import com.application.exceptions.ServiceException;
import com.application.form.DepartmentForm;
import com.application.form.EmployeeForm;
import com.application.form.MediaModelForm;
import com.application.model.DepartmentModel;
import com.application.model.EmployeeModel;
import com.application.model.MediaModel;
import com.application.service.DepartmentService;
import com.application.service.EmployeeService;
import com.application.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Part;

import static com.application.controller.CoreConstants.EMPLOYEES;

@Controller
@RequestMapping(value = "/employees")
public class EmpController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeesDAO employeesDAO;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private MediaDAO mediaModelDAO;

    @Autowired
    private MediaService mediaService;

    private static final String SAVE_DIR = "uploadFiles";

    @RequestMapping(method = RequestMethod.GET)
    public String getEmployees(Model model) {
        List<EmployeeModel> allEmployees = employeeService.getAllEmployees();
        List<DepartmentModel> allDepartments = departmentService.getAllDepartments();
        model.addAttribute("employees", allEmployees);
        model.addAttribute("departments", allDepartments);
        return "/WEB-INF/jsp/employee-list.jsp";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView getCreationPage(Model model,
                                        @ModelAttribute("employeeForm") EmployeeForm employeeForm) {
        List<DepartmentModel> allDepartments = departmentService.getAllDepartments();
        model.addAttribute("departments", allDepartments);
        return new ModelAndView("/WEB-INF/jsp/create-edit-employee.jsp", "employeeForm", employeeForm);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView getEditionPage(@RequestParam(name = "idToEdit") final String id,
                                       @ModelAttribute("employeeForm") EmployeeForm employeeForm,
                                       @ModelAttribute("mediaModelForm") MediaModelForm mediaModelForm,
                                       Model model) {
        List<DepartmentModel> allDepartments = departmentService.getAllDepartments();
        int idToEdit = Integer.parseInt(id);
        EmployeeModel currentEmployee = employeesDAO.getEmployeeForId(idToEdit);
        if (employeeForm.getId() == 0) {
            employeeForm.setId(currentEmployee.getId());
            employeeForm.setFirstName(currentEmployee.getFirstName());
            employeeForm.setLastName(currentEmployee.getLastName());
            employeeForm.setDateBirth(currentEmployee.getDateBirth());
            employeeForm.setCreatedDate(currentEmployee.getCreatedDate());
            employeeForm.setEmail(currentEmployee.getEmail());
            employeeForm.setPhoto(currentEmployee.getPhoto());
            employeeForm.setExperience(currentEmployee.isExperience());
            employeeForm.setDepartmentId(currentEmployee.getDepartmentId());
            mediaModelForm.setIdEmp(currentEmployee.getId());
        }
        model.addAttribute("departments", allDepartments);
        model.addAttribute("mediaModelForm", mediaModelForm);
        model.addAttribute("edit", Boolean.TRUE);
        return new ModelAndView("/WEB-INF/jsp/create-edit-employee.jsp", "employeeForm", employeeForm);
    }

/*    @RequestMapping(value = "/photo/{name:.*}", method = RequestMethod.GET)
    public String getPhoto(@PathVariable("name") final String idStr,
                           HttpServletResponse resp,
                           HttpServletRequest req) throws IOException {
        int photoName = Integer.parseInt(idStr.substring(0, idStr.lastIndexOf('.')));

        ServletOutputStream outputStream = resp.getOutputStream();
        EmployeeModel employee = employeeService.getEmployeeForId(photoName);
        if (employee != null && employee.getPhoto() != null && employee.getPhoto().getPhoto() != null) {
            MediaModel photo = employee.getPhoto();
            outputStream.write(photo.getPhoto());
            return req.getServletPath() + "/photo/" + photoName;
        } else {
            return req.getServletPath() + "/photo/" + photoName;
        }
        //outputStream.flush();
    }*/

    @RequestMapping(value = "/photo/{name:.*}", method = RequestMethod.GET)
    public void getPhoto(@PathVariable("name") final String idStr,
                         HttpServletResponse resp) {
        int photoName = Integer.parseInt(idStr.substring(0, idStr.lastIndexOf('.')));

        ServletOutputStream outputStream = null;
        try {
            outputStream = resp.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EmployeeModel employee = employeeService.getEmployeeForId(photoName);
        if (employee != null && employee.getPhoto() != null && employee.getPhoto().getPhoto() != null) {
            MediaModel photo = employee.getPhoto();
            try {
                assert outputStream != null;
                outputStream.write(photo.getPhoto());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                assert outputStream != null;
                outputStream.write(mediaService.getDefaultImage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //outputStream.flush();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String deleteEmployee(Model model,
                                 @RequestParam(name = "idToRemove") String id) {
        int idToRemove = Integer.parseInt(id);
        List<EmployeeModel> leftEmployees = employeeService.removeEmployee(idToRemove);
        model.addAttribute("employees", leftEmployees);
        return "/WEB-INF/jsp/employee-list.jsp";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createEmployee(@ModelAttribute("employeeForm") EmployeeForm employeeForm,
                                 HttpServletRequest req,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        List<DepartmentModel> allDepartments = departmentService.getAllDepartments();
        EmployeeModel employeeModel = new EmployeeModel();
        DepartmentModel departmentModel = new DepartmentModel();

        int id = employeeService.getRandomId();
        model.addAttribute("employeeId", id);
        departmentModel.setId(employeeForm.getId());
        employeeModel.setId(id);
        employeeModel.setFirstName(employeeForm.getFirstName());
        employeeModel.setLastName(employeeForm.getLastName());
        employeeModel.setDateBirth(employeeForm.getDateBirth());
        employeeModel.setEmail(employeeForm.getEmail());
        employeeModel.setCreatedDate(new Date());
        employeeModel.setModifiedDate(new Date());
        employeeModel.setExperience(employeeForm.isExperience());
        employeeModel.setDepartmentId(employeeForm.getDepartmentId());
        try {
            employeeService.createEditEmployee(employeeModel);
            return "redirect:" + EMPLOYEES;
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("message", e.getErrors());
            redirectAttributes.addFlashAttribute("departments", allDepartments);
            redirectAttributes.addFlashAttribute("employeeForm", employeeForm);
            return "redirect:" + req.getServletPath();
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editEmployee(@RequestParam(name = "id") final String id,
                               @ModelAttribute("employeeForm") EmployeeForm employeeForm,
                               @ModelAttribute("mediaModelForm") MediaModelForm mediaModelForm,
                               HttpServletRequest req,
                               RedirectAttributes redirectAttributes) {
        EmployeeModel employeeModel = new EmployeeModel();
        int idToEdit = Integer.parseInt(id);
        DepartmentModel departmentModel = departmentService.getDepartmentForId(employeeForm.getDepartmentId().getId());
        List<MediaModel> allMediaModels = mediaModelDAO.getAllMedias();
        redirectAttributes.addFlashAttribute("employees", allMediaModels);

        MediaModel mediaModel = mediaModelDAO.getMediaForIdEmp(idToEdit);
        MediaModel currentMedia = mediaModelDAO.getMediaForIdEmp(idToEdit);

        EmployeeModel currentEmployee = employeesDAO.getEmployeeForId(idToEdit);
        String email = employeeForm.getEmail();

        redirectAttributes.addFlashAttribute("currentMedia", currentMedia);
        List<DepartmentModel> allDepartments = departmentService.getAllDepartments();

        try {
            setEmployeeModel(employeeModel, departmentModel, mediaModel, idToEdit, req, employeeForm);
            employeeService.createEditEmployee(employeeModel);
            return "redirect:" + EMPLOYEES;
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("departments", allDepartments);
            redirectAttributes.addFlashAttribute("wrongEmail", email);
            redirectAttributes.addFlashAttribute("currentEmployee", currentEmployee);
            redirectAttributes.addFlashAttribute("mediaModelForm", mediaModelForm);
            redirectAttributes.addFlashAttribute("employeeForm", employeeForm);
            return "redirect:" + req.getServletPath();
        }
    }

    @RequestMapping(value = "/edit-photo", method = RequestMethod.POST)
     public String savePhoto(@ModelAttribute("mediaModelForm") MediaModelForm mediaModelForm,
                             @ModelAttribute("employeeForm") EmployeeForm employeeForm,
                             RedirectAttributes redirectAttributes,
                             HttpServletRequest req) {
        int idToEdit = employeeForm.getId();

        int photoId;
        if (mediaModelDAO.getMediaForIdEmp(idToEdit) != null) {
            photoId = mediaModelDAO.getMediaForIdEmp(idToEdit).getId();
        } else {
            photoId = 0;
        }
        Part filePart = null;
        try {
            filePart = req.getPart("photo");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        String fileName = filePart.getSubmittedFileName();
        List<MediaModel> allMediaModels = mediaModelDAO.getAllMedias();
        req.setAttribute("employees", allMediaModels);

        String appPath = req.getServletContext().getRealPath("");
        String savePath = appPath + SAVE_DIR;
        File fileSaveDir = new File(savePath);

        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        String filePath = "/home/diana/Downloads/" + fileName;
        try {
            for (Part part : req.getParts()) {
                part.write(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

//            ==========================================

        File savedFile = new File(filePath);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(savedFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        employeeService.saveUpdatePhoto(fileInputStream, idToEdit, photoId);
        mediaModelForm.setPhoto(employeeService.saveUpdatePhoto(fileInputStream, idToEdit, photoId).getPhoto());
//        employeeForm.setPhoto(mediaModelForm.getPhoto());
        redirectAttributes.addFlashAttribute("mediaModelForm",mediaModelForm);
        return "redirect:" + "/employees/edit?idToEdit=" + idToEdit;

    }

    private void setEmployeeModel(EmployeeModel employeeModel, DepartmentModel departmentModel, MediaModel mediaModel, int idToEdit, HttpServletRequest req, EmployeeForm employeeForm) {
        employeeModel.setId(idToEdit);
        employeeModel.setFirstName(req.getParameter("firstName"));
        employeeModel.setLastName(req.getParameter("lastName"));
        employeeModel.setDateBirth(req.getParameter("dateBirth"));
        employeeModel.setEmail(req.getParameter("email"));
        employeeModel.setCreatedDate(employeeForm.getCreatedDate());
        employeeModel.setModifiedDate(new Date());
        employeeModel.setPhoto(mediaModel);
        employeeModel.setExperience(Boolean.parseBoolean(req.getParameter("experience")));
        employeeModel.setDepartmentId(departmentModel);
    }

   /* @Override
    public void processGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getServletPath().substring(req.getServletPath().lastIndexOf('/') + 1);

        if (EMPLOYEES.equals(req.getServletPath())) {
            List<EmployeeModel> allEmployees = employeeService.getAllEmployees();
            List<DepartmentModel> allDepartments = departmentService.getAllDepartments();
            req.setAttribute("employees", allEmployees);
            req.setAttribute("departments", allDepartments);
            req.getRequestDispatcher("/WEB-INF/jsp/employee-list.jsp").forward(req, resp);
        }
        if ("/employee/edit".equals(req.getServletPath())) {
            List<DepartmentModel> allDepartments = departmentService.getAllDepartments();
            int idToEdit = Integer.valueOf(req.getParameter("idToEdit"));
            EmployeeModel currentEmployee = employeesDAO.getEmployeeForId(idToEdit);
            req.setAttribute("departments", allDepartments);
            req.setAttribute("currentEmployee", currentEmployee);
            req.getRequestDispatcher("/WEB-INF/jsp/create-edit-employee.jsp").forward(req, resp);
        }
        if ("/employee/create".equals(req.getServletPath())) {
            List<DepartmentModel> allDepartments = departmentService.getAllDepartments();
            req.setAttribute("departments", allDepartments);
//            req.setAttribute("currentEmployee", currentEmployee);
            req.getRequestDispatcher("/WEB-INF/jsp/create-edit-employee.jsp").forward(req, resp);
        }
        if (("/employee/photo/" + idStr).equals(req.getServletPath())) {
            int photoName = Integer.parseInt(idStr.substring(0, idStr.lastIndexOf('.')));

            ServletOutputStream outputStream = resp.getOutputStream();
            EmployeeModel employee = employeeService.getEmployeeForId(photoName);
            if (employee != null && employee.getPhoto() != null && employee.getPhoto().getPhoto() != null) {
                MediaModel photo = employee.getPhoto();
                outputStream.write(photo.getPhoto());
            } else {
                outputStream.write(mediaService.getDefaultImage());
            }
            //outputStream.flush();
        }
    }

    @Override
    public void processPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {

        if (EMPLOYEES.equals(req.getServletPath())) {
            int idToRemove = Integer.parseInt(req.getParameter("idToRemove"));
            List<EmployeeModel> leftEmployees = employeeService.removeEmployee(idToRemove);
            req.setAttribute("employees", leftEmployees);
            req.getRequestDispatcher("/WEB-INF/jsp/employee-list.jsp").forward(req, resp);
        }

        if ("/employee/create".equals(req.getServletPath())) {
            List<DepartmentModel> allDepartments = departmentService.getAllDepartments();
            String email = req.getParameter("email");
            EmployeeModel employeeModel = new EmployeeModel();
            DepartmentModel departmentModel = new DepartmentModel();
            if (employeeService.checkExistingEmployeeEmail(email)) {
                req.setAttribute("departments", allDepartments);
                req.setAttribute("wrongEmail", email);
                req.setAttribute("createdEmployee", employeeService.convertRequestToEmployee(req));
                req.getRequestDispatcher("/WEB-INF/jsp/create-edit-employee.jsp").forward(req, resp);
            } else {
                int id = employeeService.getRandomId();
                req.setAttribute("employeeId", id);
                departmentModel.setId(Integer.parseInt(req.getParameter("departmentId")));
                employeeModel.setId(id);
                employeeModel.setFirstName(req.getParameter("firstName"));
                employeeModel.setLastName(req.getParameter("lastName"));
                employeeModel.setDateBirth(req.getParameter("dateBirth"));
                employeeModel.setEmail(req.getParameter("email"));
                employeeModel.setCreatedDate(new Date());
                employeeModel.setModifiedDate(new Date());
                employeeModel.setExperience(Boolean.parseBoolean(req.getParameter("empExperience")));
                employeeModel.setDepartmentId(departmentModel);
                try {
                    employeeService.createEditEmployee(employeeModel);
                    resp.sendRedirect(req.getContextPath() + EMPLOYEES);
                } catch (ServiceException e) {
                    req.setAttribute("message", e.getErrors());
                    req.setAttribute("departments", allDepartments);
                    req.setAttribute("createdEmployee", employeeService.convertRequestToEmployee(req));
                    req.getRequestDispatcher("/WEB-INF/jsp/create-edit-employee.jsp").forward(req, resp);
                }

            }
        }

        if ("/employee/edit-photo".equals(req.getServletPath())) {
            int idToEdit = Integer.parseInt(req.getParameter("id"));

            int photoId;
            if (mediaModelDAO.getMediaForIdEmp(idToEdit) != null) {
                photoId = mediaModelDAO.getMediaForIdEmp(idToEdit).getId();
            } else {
                photoId = 0;
            }
            Part filePart = req.getPart("photo");
            String fileName = filePart.getSubmittedFileName();
            List<MediaModel> allMediaModels = mediaModelDAO.getAllMedias();
            req.setAttribute("employees", allMediaModels);

            String appPath = req.getServletContext().getRealPath("");
            String savePath = appPath + SAVE_DIR;
            File fileSaveDir = new File(savePath);

            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }
            String filePath = "/home/diana/Downloads/" + fileName;
            for (Part part : req.getParts()) {
                part.write(filePath);
            }

//            ==========================================

            File savedFile = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(savedFile);

            employeeService.saveUpdatePhoto(fileInputStream, idToEdit, photoId);
            resp.sendRedirect(req.getContextPath() + "/employee/edit?idToEdit=" + idToEdit);

        }

        if ("/employee/edit".equals(req.getServletPath())) {
            EmployeeModel employeeModel = new EmployeeModel();
            int idToEdit = Integer.parseInt(req.getParameter("id"));
            DepartmentModel departmentModel = departmentService.getDepartmentForId(Integer.parseInt(req.getParameter("departmentId")));
            List<MediaModel> allMediaModels = mediaModelDAO.getAllMedias();
            req.setAttribute("employees", allMediaModels);

            MediaModel mediaModel = mediaModelDAO.getMediaForIdEmp(idToEdit);

            MediaModel currentMedia = mediaModelDAO.getMediaForIdEmp(idToEdit);

            EmployeeModel currentEmployee = employeesDAO.getEmployeeForId(idToEdit);
            String email = req.getParameter("empEmail");

            req.setAttribute("currentMedia", currentMedia);

            EmployeeModel employeeById = employeesDAO.getEmployeeForId(idToEdit);
            List<DepartmentModel> allDepartments = departmentService.getAllDepartments();
            if (employeeService.checkExistingEmployeeEmail(email)) {
                req.setAttribute("departments", allDepartments);
                req.setAttribute("wrongEmail", email);
                req.setAttribute("currentEmployee", currentEmployee);
                if (email.equals(employeeById.getEmail())) {
                    setEmployeeModel(employeeModel, departmentModel, mediaModel, idToEdit, req);
                    employeeService.createEditEmployee(employeeModel);
                    resp.sendRedirect(req.getContextPath() + EMPLOYEES);
                } else {
                    req.getRequestDispatcher("/WEB-INF/jsp/create-edit-employee.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("employeeId", employeeService.getRandomId());
                setEmployeeModel(employeeModel, departmentModel, mediaModel, idToEdit, req);
                employeeService.createEditEmployee(employeeModel);
                resp.sendRedirect(req.getContextPath() + EMPLOYEES);
            }
        }
    }
*/

}
