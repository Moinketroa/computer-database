package com.excilys.computerdatabase.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.excilys.computerdatabase.page.Page;

public class PaginationTag extends TagSupport {

  private Page page;

  @Override
  public int doStartTag() throws JspException {

    StringBuilder stringBuilder = new StringBuilder("<ul class=\"pagination\">");

    if (page.isPreviousPageAvailable()) {
      stringBuilder.append("<li><a href=\"./dashboard?offset=");
      stringBuilder.append(page.getPreviousPageOffset());
      stringBuilder.append("&entitiesPerPage=");
      stringBuilder.append(page.getNumberOfElementsPerPage());
      stringBuilder.append("\" aria-label=\"Previous\">");
      stringBuilder.append("<span aria-hidden=\"true\">&laquo; Previous Page</span>");
      stringBuilder.append("</a></li>");
    }

    if (page.isNextPageAvailable()) {
      stringBuilder.append("<li><a href=\"./dashboard?offset=");
      stringBuilder.append(page.getNextPageOffset());
      stringBuilder.append("&entitiesPerPage=");
      stringBuilder.append(page.getNumberOfElementsPerPage());
      stringBuilder.append("\" aria-label=\"Next\">");
      stringBuilder.append("<span aria-hidden=\"true\">Next Page &raquo;</span>");
      stringBuilder.append("</a></li>");
    }

    stringBuilder.append("</ul>");

    try {
      pageContext.getOut().println(stringBuilder.toString());
    } catch (IOException e) {
      throw new JspException("I/O Error", e);
    }

    return SKIP_BODY;
  }

  public void setPage(Page page) {
    this.page = page;
  }
}
