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

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.service.ComputerService;

/**
 * Servlet implementation class ComputerServlet
 */
@WebServlet("/computer2")
public class ComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Autowired
  private ComputerService computerService;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public ComputerServlet() {
    super();
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }

  /**
   * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
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

    computerService.delete(computerId);

    request.setAttribute("msg", "Computer #" + computerId + " deleted !");
    this.getServletContext().getRequestDispatcher("/WEB-INF/204.jsp").forward(request, response);
  }
}
