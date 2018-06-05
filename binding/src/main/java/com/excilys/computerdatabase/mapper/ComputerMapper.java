package com.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.dto.CompanyDto;
import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.exceptions.notfound.CompanyNotFoundException;
import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.service.CompanyService;

/**
 * The class helps building a {@link Computer} from specific parameters or helps
 * building other objects from a {@link Computer}.
 *
 * @author jmdebicki
 *
 */
@Component
public class ComputerMapper implements RowMapper<Computer> {

  @Autowired
  private CompanyService companyService;

  @Override
  public Computer mapRow(ResultSet result, int rowNum) throws SQLException {
    int id = result.getInt("id");
    String name = result.getString("name");

    LocalDate introduced = null;
    if (result.getDate("introduced") != null) {
      introduced = result.getDate("introduced").toLocalDate();
    }

    LocalDate discontinued = null;
    if (result.getDate("discontinued") != null) {
      discontinued = result.getDate("discontinued").toLocalDate();
    }

    Computer computer = new Computer(name);
    computer.setId(id);
    computer.setIntroduced(introduced);
    computer.setDiscontinued(discontinued);

    int companyId = result.getInt("company_id");

    if (companyId != 0) {
      String companyName = result.getString("company_name");

      Company company = new Company(companyName);
      company.setId(companyId);

      computer.setCompany(company);
    }

    return computer;
  }

  /**
   * Builds a new {@link Computer} from parameters describing each fields of the
   * computer except its id.
   *
   * @param name
   *          the name of the computer
   * @param introduced
   *          the date when the computer was introduced to the public (null if
   *          none)
   * @param discontinued
   *          the date when the computer was withdrawn from sale (null if none)
   * @param companyId
   *          the id of the computer's company (null if none)
   * @return a new {@link Computer} without any id
   * @throws CompanyNotFoundException
   *           if the company to add to the computer doesn't exist
   */
  public Computer fromParameters(String name, LocalDate introduced, LocalDate discontinued, Integer companyId)
      throws CompanyNotFoundException {
    Computer computer = new Computer(name);
    computer.setIntroduced(introduced);
    computer.setDiscontinued(discontinued);

    if (companyId != null) {
      Company company = companyService.getById(companyId);

      if (company != null) {
        computer.setCompany(company);
      } else {
        throw new CompanyNotFoundException(companyId);
      }
    }

    return computer;
  }

  /**
   * Builds a {@link Computer} from parameters describing each fields of the
   * computer except its id.
   *
   * @param computerId
   *          the id of the computer
   * @param name
   *          the name of the computer
   * @param introduced
   *          the date when the computer was introduced to the public (null if
   *          none)
   * @param discontinued
   *          the date when the computer was withdrawn from sale (null if none)
   * @param companyId
   *          the id of the computer's company (null if none)
   * @return a {@link Computer}
   * @throws CompanyNotFoundException
   *           if the company to add to the computer doesn't exist
   */
  public Computer fromParameters(int computerId, String name, LocalDate introduced, LocalDate discontinued,
      Integer companyId) throws CompanyNotFoundException {
    Computer computer = new Computer(name);
    computer.setId(computerId);
    computer.setIntroduced(introduced);
    computer.setDiscontinued(discontinued);

    if (companyId != null) {
      Company company = companyService.getById(companyId);

      if (company != null) {
        computer.setCompany(company);
      } else {
        throw new CompanyNotFoundException(companyId);
      }
    }

    return computer;
  }

  public Computer fromComputerDto(ComputerDto computerDto) throws CompanyNotFoundException {
    Computer computer = new Computer(computerDto.getName());

    computer.setId(computerDto.getId());
    computer.setIntroduced(computerDto.getIntroduced());
    computer.setDiscontinued(computerDto.getDiscontinued());

    if (computerDto.getCompany() != null) {
      if (computerDto.getCompany().getId() != 0) {
        Company company = companyService.getById(computerDto.getCompany().getId());
  
        if (company != null) {
          computer.setCompany(company);
        } else {
          throw new CompanyNotFoundException(computerDto.getCompany().getId());
        }
      }
    }

    return computer;
  }
}
