package com.excilys.computerdatabase.servlet;

import java.io.IOException;
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

import com.excilys.computerdatabase.service.ComputerService;

/**
 * Servlet implementation class DeleteComputer
 */
@WebServlet("/deleteComputer")
public class DeleteComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Autowired
  private ComputerService computerService;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DeleteComputerServlet() {
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

    if (computerId == 0) {
      request.setAttribute("error", "Please enter a valid ID");
      this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
    }

    computerService.delete(computerId);

    request.setAttribute("msg", "Computer #" + computerId + " deleted !");
    this.getServletContext().getRequestDispatcher("/WEB-INF/204.jsp").forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String computerSelection = request.getParameter("selection");

    if (computerSelection.isEmpty() || computerSelection == null) {
      request.setAttribute("error", "Cannot delete an empty set of computers, please select at least one computer");
      this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
    }

    String[] computerIdArray = computerSelection.split(",");

    List<Integer> computerIdList = new ArrayList<>();

    for (String idString : computerIdArray) {
      try {
        int computerId = Integer.parseInt(idString);

        if (computerId < 0) {
          request.setAttribute("error", "The ID of a computer cannot be negative");
          this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
        }

        computerIdList.add(computerId);
      } catch (NumberFormatException e) {
        request.setAttribute("error", "The ID of a computer must be numeric");
        this.getServletContext().getRequestDispatcher("/WEB-INF/400.jsp").forward(request, response);
      }
    }

    computerService.deteleSeveral(computerIdList.toArray(new Integer[computerIdList.size()]));

    request.setAttribute("msg", "Computers deleted !");
    this.getServletContext().getRequestDispatcher("/WEB-INF/204.jsp").forward(request, response);

  }
}
