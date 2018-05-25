package com.excilys.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.computerdatabase.dao.OrderByComputer;
import com.excilys.computerdatabase.dao.OrderByMode;
import com.excilys.computerdatabase.dto.PageComputerDto;
import com.excilys.computerdatabase.exceptions.BadRequestException;
import com.excilys.computerdatabase.mapper.IntegerMapper;
import com.excilys.computerdatabase.mapper.OrderByComputerMapper;
import com.excilys.computerdatabase.mapper.OrderByModeMapper;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.page.Page;
import com.excilys.computerdatabase.service.ComputerService;

/**
 * Servlet implementation class DashboardServlet.
 */
@WebServlet("/dashboard2")
public class DashboardServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Autowired
  private ComputerService computerService;

  @Autowired
  private IntegerMapper integerMapper;
  @Autowired
  private OrderByComputerMapper orderByComputerMapper;
  @Autowired
  private OrderByModeMapper orderByModeMapper;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DashboardServlet() {
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
    try {
      String offsetParameter = request.getParameter("offset");
      String entitiesPerPageParameter = request.getParameter("entitiesPerPage");
      String keywordParameter = request.getParameter("keyword");
      String orderParameter = request.getParameter("order");
      String modeParameter = request.getParameter("mode");

      int offset = integerMapper.fromStringOnlyPositive(offsetParameter, "Offset");
      int entitiesPerPage = integerMapper.fromStringOnlyPositive(entitiesPerPageParameter,
          "Number of entities per page");

      OrderByComputer order = orderByComputerMapper.fromString(orderParameter);
      OrderByMode mode = orderByModeMapper.fromString(modeParameter);

      Page<Computer> pageResult = null;

      if (keywordParameter != null && !keywordParameter.equals("")) {
        pageResult = computerService.search(keywordParameter, order, mode, offset, entitiesPerPage);
      } else {
        pageResult = computerService.getAll(order, mode, offset, entitiesPerPage);
      }

      request.setAttribute("offset", offset);
      request.setAttribute("entitiesPerPage", entitiesPerPage);
      request.setAttribute("page", new PageComputerDto(pageResult));

      request.setAttribute("keyword", keywordParameter);

      if (modeParameter == null || !modeParameter.equals("desc")) {
        request.setAttribute("mode", "asc");
      } else {
        request.setAttribute("mode", "desc");
      }

    } catch (BadRequestException e) {
      request.setAttribute("error", e.getMessage());
      this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
    }

    this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
  }
}
