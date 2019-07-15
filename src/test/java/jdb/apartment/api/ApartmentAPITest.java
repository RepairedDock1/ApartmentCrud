package jdb.apartment.api;

import static jdb.apartment.constants.ResponseConstants.SUCCESSFUL_DELETE;
import static jdb.apartment.constants.ResponseConstants.SUCCESSFUL_INSERT;
import static jdb.apartment.constants.SqlQueryConstants.GET_APARTMENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdb.apartment.dto.ApartmentDTO;
import jdb.apartment.dto.GenericResponseDTO;
import jdb.apartment.repository.rowmappings.ApartmentMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


/*
•	@RunWith annotation defines how your test class is run
•	Note that it is possible to create your own custom Runner to change this behavior
•	Profiles are a core feature of the spring framework and allow us to map our beans to different profiles.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("apitests")
@SpringBootTest
public class ApartmentAPITest {

  @Autowired
  WebApplicationContext webApplicationContext;

  @Autowired
  NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  MockMvc mockMvc;
  ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void testInsertApartment() throws Exception {
    ApartmentDTO expectedApartment = new ApartmentDTO(2, "SkyHouse Midtown", 80, 5000000);

    mockMvc.perform(post("/apartment").contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(expectedApartment)))
        .andExpect(status().isCreated()).andExpect(content()
        .json(getExpectedResponse(CREATED, SUCCESSFUL_INSERT, expectedApartment)));

    ApartmentDTO actualApartment = namedParameterJdbcTemplate.getJdbcOperations()
        .queryForObject(GET_APARTMENT, new ApartmentMapper(), 2);

    assertThat(actualApartment).isEqualToComparingFieldByFieldRecursively(expectedApartment);
  }

  @Test
  public void testGetApartment() throws Exception {
    ApartmentDTO actualApartment = namedParameterJdbcTemplate.getJdbcOperations()
        .queryForObject(GET_APARTMENT, new ApartmentMapper(), 1);

    mockMvc.perform(get("/apartment/{apartmentId}", 1)).andExpect(status()
        .isOk()).andExpect(content().json(getExpectedResponse(OK, null, actualApartment)));
  }

  @Test
  public void testPutApartment() throws Exception {
    ApartmentDTO expectedApartment = new ApartmentDTO(1, "UPDATED", 111, 222222);

    mockMvc.perform(put("/apartment").contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(expectedApartment))).andExpect(status().isOk());

    ApartmentDTO actualApartment = namedParameterJdbcTemplate.getJdbcOperations()
        .queryForObject(GET_APARTMENT, new ApartmentMapper(), 1);

    assertThat(actualApartment).isEqualToComparingFieldByFieldRecursively(expectedApartment);
  }

  @Test(expected = EmptyResultDataAccessException.class)
  public void testDeleteApartment() throws Exception {
    mockMvc.perform(delete("/apartment/{apartmentId}", 1)).andExpect(status()
        .isOk()).andExpect(content().json(getExpectedResponse(OK, SUCCESSFUL_DELETE, null)));

    namedParameterJdbcTemplate.getJdbcOperations()
        .queryForObject(GET_APARTMENT, new ApartmentMapper(), 1);
  }

  private String getExpectedResponse(HttpStatus status, String message, ApartmentDTO apartmentDTO)
      throws JsonProcessingException {
    GenericResponseDTO expectedResponseDTO = new GenericResponseDTO();
    expectedResponseDTO.setStatus(status);
    expectedResponseDTO.setMessage(message);
    expectedResponseDTO.setData(apartmentDTO);

    return objectMapper.writeValueAsString(expectedResponseDTO);
  }
}