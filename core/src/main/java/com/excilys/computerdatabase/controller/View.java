package com.excilys.computerdatabase.controller;

public enum View {
  ADD_COMPUTER("addComputer"), COMPUTER("computer"), DASHBOARD("dashboard"), EDIT_COMPUTER("editComputer"), NO_CONTENT(
      "204"), BAD_REQUEST("400"), FORBIDDEN("403"), NOT_FOUND("404"), INTERNAL_SERVER_ERROR("500"), LOGIN("login"), LOGOUT("logout");

  private final String value;

  private View(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}
