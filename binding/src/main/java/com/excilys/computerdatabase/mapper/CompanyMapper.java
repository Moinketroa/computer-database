package com.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.model.pojo.Company;

/**
 * The class helps building a {@link Company} from specific parameters or helps
 * building other objects from a {@link Company}.
 *
 * @author jmdebicki
 *
 */
@Component
public class CompanyMapper implements RowMapper<Company> {

  @Override
  public Company mapRow(ResultSet result, int arg1) throws SQLException {
    int id = result.getInt("id");
    String name = result.getString("name");

    Company company = new Company(name);
    company.setId(id);

    return company;
  }

}
