package com.excilys.computerdatabase.ui;

import java.util.Optional;

public enum MenuOption {

  DISPLAY_COMPUTERS(1, "Display all computers"), DISPLAY_COMPANIES(2, "Display all companies"), COMPUTER_DETAILS(3,
      "Show computer details"), ADD_COMPUTER(4, "Add a computer"), UPDATE_COMPUTER(5,
          "Update a computer"), REMOVE_COMPUTER(6,
              "Remove a computer"), REMOVE_COMPANY(7, "Remove a company"), EXIT(8, "Exit");

  private int optionNumber;
  private String optionDescription;

  private MenuOption(int number, String description) {
    this.optionNumber = number;
    this.optionDescription = number + " : " + description;
  }

  public int getOptionNumber() {
    return this.optionNumber;
  }

  public static Optional<MenuOption> valueOf(int choice) {
    for (MenuOption option : MenuOption.values()) {
      if (choice == option.optionNumber) {
        return Optional.of(option);
      }
    }

    return Optional.empty();
  }

  @Override
  public String toString() {
    return this.optionDescription;
  }
}
