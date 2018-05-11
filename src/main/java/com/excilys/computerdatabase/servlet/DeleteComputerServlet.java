package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.service.ComputerService;

/**
 * Servlet implementation class DeleteComputer
 */
@WebServlet("/deleteComputer")
public class DeleteComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private ComputerService computerService = ComputerService.INSTANCE;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DeleteComputerServlet() {
    super();
    // TODO Auto-generated constructor stub
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
    
  }
}
