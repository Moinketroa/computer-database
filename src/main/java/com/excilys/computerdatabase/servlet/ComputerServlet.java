package com.excilys.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.service.ComputerService;

/**
 * Servlet implementation class ComputerServlet
 */
@WebServlet("/computer")
public class ComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private ComputerService computerService = ComputerService.INSTANCE;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public ComputerServlet() {
    super();
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

    this.getServletContext().getRequestDispatcher("/WEB-INF/computer.jsp").forward(request, response);
  }

}
