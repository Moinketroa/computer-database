package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.computerdatabase.dto.CompanyDto;
import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.exceptions.CompanyNotFoundException;
import com.excilys.computerdatabase.exceptions.DiscontinuationPriorToIntroductionExpection;
import com.excilys.computerdatabase.exceptions.WrongPageParameterException;
import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.mapper.LocalDateMapper;
import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.page.Page;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

/**
 * Servlet implementation class EditComputerServlet.
 */
@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Autowired
  private CompanyService companyService;
  @Autowired
  private ComputerService computerService;
  
  @Autowired
  private LocalDateMapper localDateMapper;
  @Autowired
  private ComputerMapper computerMapper;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public EditComputerServlet() {
    super();
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String computerIdParameter = request.getParameter("computerId");

    int computerId = 0;
    if (computerIdParameter != null) {
      try {
        computerId = Integer.parseInt(computerIdParameter);

        if (computerId < 0) {
          request.setAttribute("error", "The ID of the computer cannot be negative");
          this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
        }
      } catch (NumberFormatException e) {
        request.setAttribute("error", "The ID of the computer must be numeric");
        this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
      }
    }

    request.setAttribute("computerId", computerId);

    Computer computer = computerService.getById(computerId);

    if (computer != null) {
      ComputerDto computerDto = new ComputerDto(computer);

      request.setAttribute("computer", computerDto);
    }

    try {
      Page<Company> page = companyService.getAll(0, 100);
      List<CompanyDto> companies = new ArrayList<>();

      for (Company company : page.result()) {
        companies.add(new CompanyDto(company));
      }

      request.setAttribute("companies", companies);
    } catch (WrongPageParameterException e) {
      request.setAttribute("error", e.getMessage());
      this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
    }

    this.getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String computerIdParameter = request.getParameter("computerId");

    int computerId = 0;
    if (computerIdParameter != null) {
      try {
        computerId = Integer.parseInt(computerIdParameter);

        if (computerId < 0) {
          request.setAttribute("error", "The ID of the computer cannot be negative");
          this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
        }
      } catch (NumberFormatException e) {
        request.setAttribute("error", "The ID of the computer must be numeric");
        this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
      }
    }

    String nameParameter = request.getParameter("name");
    String introducedParameter = request.getParameter("introduced");
    String discontinuedParameter = request.getParameter("discontinued");
    String companyIdParameter = request.getParameter("companyId");

    LocalDate introduced = null;
    LocalDate discontinued = null;
    Integer companyId = null;

    if (nameParameter == null || nameParameter.trim().equals("")) {
      request.setAttribute("error", "Computer name is mandatory when adding a new computer");
      this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
    }

    if (introducedParameter != null && !(introducedParameter.trim().equals(""))) {
      introduced = localDateMapper.fromUSFormatString(introducedParameter);
    }

    if (discontinuedParameter != null && !(discontinuedParameter.trim().equals(""))) {
      discontinued = localDateMapper.fromUSFormatString(discontinuedParameter);
    }

    if (companyIdParameter != null && !(companyIdParameter.trim().equals(""))) {
      try {
        companyId = Integer.parseInt(companyIdParameter);
      } catch (NumberFormatException e) {
        request.setAttribute("error", "Company ID must be numeric");
        this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
      }
    }

    Computer computerToUpdate = null;

    try {
      computerToUpdate = computerMapper.fromParameters(computerId, nameParameter, introduced, discontinued, companyId);
    } catch (CompanyNotFoundException e) {
      request.setAttribute("error", "Requested company wasn't found");
      this.getServletContext().getRequestDispatcher("/WEB-INF/404.jsp").forward(request, response);
    }

    try {
      computerService.update(computerToUpdate);

      request.setAttribute("computerId", computerToUpdate.getId());
      request.setAttribute("computer", new ComputerDto(computerToUpdate));

      this.getServletContext().getRequestDispatcher("/WEB-INF/computer.jsp").forward(request, response);
    } catch (DiscontinuationPriorToIntroductionExpection e) {
      request.setAttribute("error", e.getMessage());
      this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
    }
  }

}
