package jdb.apartment.repository;

import static jdb.apartment.constants.SqlQueryConstants.DELETE_APARTMENT;
import static jdb.apartment.constants.SqlQueryConstants.GET_APARTMENT;
import static jdb.apartment.constants.SqlQueryConstants.GET_MAX_ID;
import static jdb.apartment.constants.SqlQueryConstants.INSERT_APARTMENT;
import static jdb.apartment.constants.SqlQueryConstants.UPDATE_APARTMENT;

import java.util.HashMap;
import jdb.apartment.dto.ApartmentDTO;
import jdb.apartment.repository.rowmappings.ApartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ApartmentDAO {

  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  public ApartmentDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  // Note: you can expose classic jdbc operations from your NamedParameterJdbcTemplate with
  // getJdbcOperations()
  public ApartmentDTO getApartment(Integer apartmentId) {
    try {
      return namedParameterJdbcTemplate.getJdbcOperations()
          .queryForObject(GET_APARTMENT, new ApartmentMapper(), apartmentId);
    } catch(EmptyResultDataAccessException exception){
      return null;
    }
  }


  public ApartmentDTO insertApartment(ApartmentDTO apartmentDTO) {
    apartmentDTO.setApartmentId(getNewApartmentId());
    namedParameterJdbcTemplate.update(INSERT_APARTMENT, getInsertParameterMap(apartmentDTO));

    return apartmentDTO;
  }

  public boolean updateApartment(ApartmentDTO apartmentDTO) {
    int updateSuccess = namedParameterJdbcTemplate
        .update(UPDATE_APARTMENT, getInsertParameterMap(apartmentDTO));

    return updateSuccess == 1;
  }

  public boolean deleteApartment(Integer apartmentId) {
    int deleteSuccess = namedParameterJdbcTemplate.getJdbcOperations()
        .update(DELETE_APARTMENT, apartmentId);

    return deleteSuccess == 1;
  }


  private Integer getNewApartmentId() {
    Integer max = namedParameterJdbcTemplate.getJdbcOperations()
        .queryForObject(GET_MAX_ID, Integer.class);
    if (null == max) {
      return 1;
    }

    return ++max;
  }

  private HashMap<String, Object> getInsertParameterMap(ApartmentDTO apartmentDTO) {
    HashMap<String, Object> parameterMap = new HashMap<>();
    parameterMap.put("APT_ID", apartmentDTO.getApartmentId());
    parameterMap.put("APT_NM", apartmentDTO.getApartmentName());
    parameterMap.put("UNITS", apartmentDTO.getUnits());
    parameterMap.put("PRICE", apartmentDTO.getPrice());

    return parameterMap;
  }
}
