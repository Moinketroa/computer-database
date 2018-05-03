package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.exceptions.WrongPageParameterException;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.page.Page;
import com.excilys.computerdatabase.service.ComputerService;

/**
 * Servlet implementation class DashboardServlet.
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private ComputerService computerService = ComputerService.INSTANCE;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DashboardServlet() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String offsetParameter = request.getParameter("offset");
      String entitiesPerPageParameter = request.getParameter("entitiesPerPage");

      int offset = 0, entitiesPerPage = 10;
      if (offsetParameter != null) {
        try {
          offset = Integer.parseInt(offsetParameter);

          if (offset < 0) {
            request.setAttribute("error", "Offset cannot be negative");
            this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
          }
        } catch (NumberFormatException e) {
          request.setAttribute("error", "Offset must be numeric");
          this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
        }
      }

      if (entitiesPerPageParameter != null) {
        try {
          entitiesPerPage = Integer.parseInt(entitiesPerPageParameter);

          if (entitiesPerPage < 0) {
            request.setAttribute("error", "The number of entities per page cannot be negative");
            this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
          } else if (entitiesPerPage != 10 && entitiesPerPage != 50 && entitiesPerPage != 100) {
            entitiesPerPage = 10;
          }
        } catch (NumberFormatException e) {
          request.setAttribute("error", "The number of entities per page must be numeric");
          this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
        }
      }

      Page<Computer> page = computerService.getAll(offset, entitiesPerPage);
      List<ComputerDto> computers = new ArrayList<>();

      for (Computer computer : page.result()) {
        computers.add(new ComputerDto(computer));
      }

      request.setAttribute("totalNumberOfComputers", page.getTotalNumberOfElements());
      request.setAttribute("isPreviousPageAvailable", page.isPreviousPageAvailable());
      request.setAttribute("previousPageOffset", page.getPreviousPageOffset());
      request.setAttribute("isNextPageAvailable", page.isNextPageAvailable());
      request.setAttribute("nextPageOffset", page.getNextPageOffset());

      request.setAttribute("offset", offset);
      request.setAttribute("entitiesPerPage", entitiesPerPage);
      request.setAttribute("page", page);

      request.setAttribute("computers", computers);
    } catch (WrongPageParameterException e) {
      request.setAttribute("error", e.getMessage());
      this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
    }

    this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
  }

}
