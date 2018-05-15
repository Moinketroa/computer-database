package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.dao.OrderByComputer;
import com.excilys.computerdatabase.dao.OrderByMode;
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
      String keywordParameter = request.getParameter("keyword");
      String orderParameter = request.getParameter("order");
      String modeParameter = request.getParameter("mode");

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
      
      OrderByComputer order = decideOrderByParameter(orderParameter);
      OrderByMode mode = decideOrderModeParameter(modeParameter);

      Page<Computer> pageResult = null;

      if (keywordParameter != null && !keywordParameter.equals("")) {
        System.out.println(keywordParameter);
        pageResult = computerService.search(keywordParameter, order, mode, offset, entitiesPerPage);
      } else {
        pageResult = computerService.getAll(order, mode, offset, entitiesPerPage);
      }

      List<ComputerDto> computers = new ArrayList<>();

      for (Computer computer : pageResult.result()) {
        computers.add(new ComputerDto(computer));
      }

      request.setAttribute("totalNumberOfComputers", pageResult.getTotalNumberOfElements());
      request.setAttribute("isPreviousPageAvailable", pageResult.isPreviousPageAvailable());
      request.setAttribute("previousPageOffset", pageResult.getPreviousPageOffset());
      request.setAttribute("isNextPageAvailable", pageResult.isNextPageAvailable());
      request.setAttribute("nextPageOffset", pageResult.getNextPageOffset());

      request.setAttribute("offset", offset);
      request.setAttribute("entitiesPerPage", entitiesPerPage);
      request.setAttribute("page", pageResult);

      request.setAttribute("keyword", keywordParameter);
      
      if (modeParameter == null || !modeParameter.equals("desc")) {
        request.setAttribute("mode", "asc");
      } else {
        request.setAttribute("mode", "desc");
      }

      request.setAttribute("computers", computers);
    } catch (WrongPageParameterException e) {
      request.setAttribute("error", e.getMessage());
      this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
    }

    this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
  }

  private OrderByComputer decideOrderByParameter(String order) {
    if (order == null) {
      return OrderByComputer.ID;
    }
  
    switch (order) {
    case "name" :
      return OrderByComputer.NAME;
    case "introduced" :
      return OrderByComputer.INTRODUCED;
    case "discontinued" :
      return OrderByComputer.DISCONTINUED;
    case "company" :
      return OrderByComputer.COMPANY;
    default:
      return OrderByComputer.ID;
    }
  }

  private OrderByMode decideOrderModeParameter(String mode) {
    if (mode == null) {
      return OrderByMode.ASCENDING;
    }
  
    switch (mode) {
    case "asc" :
      return OrderByMode.ASCENDING;
    case "desc" :
      return OrderByMode.DESCENDING;
    default:
      return OrderByMode.ASCENDING;
    }
  }
}
