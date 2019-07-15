package jdb.apartment.controller;

import static jdb.apartment.constants.ResponseConstants.NO_RECORD_FOUND;
import static jdb.apartment.constants.ResponseConstants.NULL_INPUT;
import static jdb.apartment.constants.ResponseConstants.SUCCESSFUL_DELETE;
import static jdb.apartment.constants.ResponseConstants.SUCCESSFUL_INSERT;
import static jdb.apartment.constants.ResponseConstants.SUCCESSFUL_UPDATE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import jdb.apartment.dto.ApartmentDTO;
import jdb.apartment.dto.GenericResponseDTO;
import jdb.apartment.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/apartment")
public class ApartmentController {

  @Autowired
  ApartmentService apartmentService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createNewApartment(@RequestBody ApartmentDTO apartmentDTO) {
    if (isInsertInputInvalid(apartmentDTO)) {
      return createResponseEntity(new GenericResponseDTO(BAD_REQUEST, NULL_INPUT, null));
    }

    ApartmentDTO insertedApartment = apartmentService.insertApartment(apartmentDTO);

    return createResponseEntity(new GenericResponseDTO(CREATED, SUCCESSFUL_INSERT, insertedApartment));
  }

  @GetMapping(path = "/{apartmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getApartmentDetails(@PathVariable Integer apartmentId) {
    ApartmentDTO apartmentDTO = apartmentService.getApartment(apartmentId);

    if (null == apartmentDTO) {
      return createResponseEntity(
          new GenericResponseDTO(OK, NO_RECORD_FOUND + apartmentId, null));
    }

    return createResponseEntity(
        new GenericResponseDTO(OK, null, apartmentDTO));
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateApartment(@RequestBody ApartmentDTO apartmentDTO) {
    if (isUpdateInputInvalid(apartmentDTO)) {
      return createResponseEntity(new GenericResponseDTO(BAD_REQUEST, NULL_INPUT, null));
    }

    boolean updateSuccess = apartmentService.updateApartment(apartmentDTO);

    if (!updateSuccess) {
      return createResponseEntity(new GenericResponseDTO(OK,
          NO_RECORD_FOUND + apartmentDTO.getApartmentId(), null));
    }

    return createResponseEntity(new GenericResponseDTO(OK, SUCCESSFUL_UPDATE, null));
  }

  @DeleteMapping(path = "/{apartmentId}", produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<?> deleteApartment(@PathVariable Integer apartmentId) {
    boolean deleteSuccess = apartmentService.deleteApartment(apartmentId);

    if (!deleteSuccess) {
      return createResponseEntity(new GenericResponseDTO(OK,
          NO_RECORD_FOUND + apartmentId, null));
    }

    return createResponseEntity(new GenericResponseDTO(OK, SUCCESSFUL_DELETE, null));
  }

  private boolean isInsertInputInvalid(ApartmentDTO apartmentDTO) {
    return null == apartmentDTO.getApartmentName() || null == apartmentDTO.getUnits()
        || null == apartmentDTO.getPrice();
  }

  private boolean isUpdateInputInvalid(ApartmentDTO apartmentDTO) {
    return null == apartmentDTO.getApartmentId() || null == apartmentDTO.getApartmentName()
        || null == apartmentDTO.getUnits() || null == apartmentDTO.getPrice();
  }

  private ResponseEntity<GenericResponseDTO> createResponseEntity(
      GenericResponseDTO genericResponseDTO) {
    return ResponseEntity.status(genericResponseDTO.getStatus())
        .contentType(MediaType.APPLICATION_JSON).body(genericResponseDTO);
  }
}
