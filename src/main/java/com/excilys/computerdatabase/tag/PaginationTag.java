package com.excilys.computerdatabase.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.excilys.computerdatabase.page.Page;

public class PaginationTag extends TagSupport {

  private String action;
  private Page page;

  public int doStartTag() throws JspException {

    try {
      switch (action.toUpperCase()) {
      case "NEXT":

        break;
      case "PREVIOUS":
        printLinkToPreviousPage();
        break;
      }
    } catch (IOException e) {
      throw new JspException("I/O Error", e);
    }

    return SKIP_BODY;
  }

  private void printLinkToPreviousPage() throws IOException {
    pageContext.getOut()
        .println("<a href=\"./dashboard?offset=" + page.getPreviousPageOffset()
            + "&entitiesPerPage=${ entitiesPerPage }\" aria-label=\"Previous\"> "
            + "<span aria-hidden=\"true\">&laquo; Previous Page</span>" + "</a>");
  }

  public void setAction(String action) {
    this.action = action;
  }

  public void setPage(Page page) {
    this.page = page;
  }
}
