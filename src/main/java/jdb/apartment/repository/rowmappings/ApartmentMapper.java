package jdb.apartment.repository.rowmappings;

import java.sql.ResultSet;
import java.sql.SQLException;
import jdb.apartment.dto.ApartmentDTO;
import org.springframework.jdbc.core.RowMapper;

public class ApartmentMapper implements RowMapper<ApartmentDTO> {
  public ApartmentDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    ApartmentDTO apartmentDTO = new ApartmentDTO();
    apartmentDTO.setApartmentId(resultSet.getInt("APT_ID"));
    apartmentDTO.setApartmentName(resultSet.getString("APT_NM"));
    apartmentDTO.setUnits(resultSet.getInt("UNITS"));
    apartmentDTO.setPrice(resultSet.getInt("PRICE"));
    return apartmentDTO;
  }
}
