package com.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerdatabase.model.pojo.Company;

/**
 * The class helps building a {@link Company} from specific parameters or helps
 * building other objects from a {@link Company}.
 *
 * @author jmdebicki
 *
 */
public class CompanyMapper {

  /**
   * Builds a {@link Company} from a {@link ResultSet}.
   *
   * @param result
   *          the {@link ResultSet} describing the company entry in the database
   * @return A {@link Company} which represents the wanted company entry in the
   *         database
   * @throws SQLException
   *           if something went wrong while fetching the company's fields
   */
  public static Company fromResultSet(ResultSet result) throws SQLException {
    int id = result.getInt("id");
    String name = result.getString("name");

    Company company = new Company(name);
    company.setId(id);

    return company;
  }

}
