package com.excilys.computerdatabase.mapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.exceptions.NegativeNumberException;
import com.excilys.computerdatabase.exceptions.NotANumberException;

@Component
public class IntegerMapper {

  public Integer fromString(String parameter, int defaultValue) {
    try {
      return Integer.parseInt(parameter);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public Integer fromStringOnlyNumber(String parameter, String paramName) throws NotANumberException {
    try {
      return Integer.parseInt(parameter);
    } catch (NumberFormatException e) {
      throw new NotANumberException(paramName);
    }
  }

  public Integer fromStringOnlyPositive(String parameter, String paramName)
      throws NegativeNumberException, NotANumberException {
    Integer result = fromStringOnlyNumber(parameter, paramName);

    if (result < 0) {
      throw new NegativeNumberException(paramName);
    }

    return result;
  }

  public List<Integer> listFromSelection(String selection) {
    String[] selectionArray = selection.split(",");
    return Arrays.stream(selectionArray).map(selectionString -> Integer.parseInt(selectionString))
        .collect(Collectors.toList());
  }

  public String listToInString(List<Integer> list) {
    StringBuilder result = new StringBuilder("(");
    
    for (Integer id : list) {
      result.append(id + ",");
    }
    
    result.replace(result.length() - 1, result.length(), ")");
    
    return result.toString();
  }
}
