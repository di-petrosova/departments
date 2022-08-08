package com.application.controller;

import com.application.dao.DepartmentsDAO;
import com.application.exceptions.ServiceException;
import com.application.form.DepartmentForm;
import com.application.model.DepartmentModel;
import com.application.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;

import static com.application.controller.CoreConstants.DEPARTMENTS;

@Controller
@RequestMapping(value = "/departments")
public class DepController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentsDAO departmentsDAO;

    DepartmentModel departmentModel = new DepartmentModel();

    @RequestMapping(method = RequestMethod.GET)
    public String getDepartments(final Model model) {
        List<DepartmentModel> allDepartments = departmentService.getAllDepartments();
        model.addAttribute("departments", allDepartments);
        return "/WEB-INF/jsp/departments-list.jsp";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView getCreationPage(@ModelAttribute("departmentForm") DepartmentForm departmentForm) {
        return new ModelAndView("/WEB-INF/jsp/create-edit-department.jsp", "departmentForm", departmentForm);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView getEditPage(@RequestParam(name = "idToEdit") final String id,
                                    @ModelAttribute("departmentForm") DepartmentForm departmentForm,
                                    final Model model) {
        int idToEdit = Integer.parseInt(id);
        DepartmentModel departmentById = departmentService.getDepartmentForId(idToEdit);
        if (departmentForm.getId() == 0) {
            if (Objects.nonNull(departmentById)) {
                departmentForm.setId(departmentById.getId());
                departmentForm.setName(departmentById.getName());
                departmentForm.setAddress(departmentById.getAddress());
            }
        }
        model.addAttribute("edit", Boolean.TRUE);
        return new ModelAndView("/WEB-INF/jsp/create-edit-department.jsp", "departmentForm", departmentForm);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String deleteDepartment(HttpServletRequest request) {
        String idToRemove = request.getParameter("idToRemove");
        List<DepartmentModel> leftDepartments = departmentService.removeDepartment(idToRemove);
        request.setAttribute("departments", leftDepartments);
        return "/WEB-INF/jsp/departments-list.jsp";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createdDepartment(@ModelAttribute("departmentForm") DepartmentForm departmentForm,
                                    HttpServletRequest request,
                                    RedirectAttributes redirectAttributes) {
        departmentModel.setName(departmentForm.getName());
        if (departmentForm.getAddress() != null) {
            departmentModel.setAddress(departmentForm.getAddress());
        }
        try {
            departmentService.createEditDepartment(departmentModel);
            return "redirect:" + DEPARTMENTS;
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("message", e.getErrors());
            redirectAttributes.addFlashAttribute("departmentForm", departmentForm);
            return "redirect:" + request.getServletPath();
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editDepartment(@RequestParam(name = "id") final String id,
                                 @ModelAttribute("departmentForm") DepartmentForm departmentForm,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) {
        int idToEdit = Integer.parseInt(id);
        DepartmentModel departmentForId = departmentsDAO.getDepartmentForId(idToEdit);
        if (!request.getParameter("address").equals("")) {
            departmentForId.setAddress(departmentForm.getAddress());
        }
        departmentForId.setName(departmentForm.getName());
        try {
            departmentService.createEditDepartment(departmentForId);
            return "redirect:" + DEPARTMENTS;
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("message", e.getErrors());
            redirectAttributes.addFlashAttribute("departmentForm", departmentForm);
            return "redirect:" + request.getServletPath() + "?idToEdit=" + idToEdit;
        }
    }
}
