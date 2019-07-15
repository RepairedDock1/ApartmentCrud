package jdb.apartment.service;

import jdb.apartment.dto.ApartmentDTO;
import jdb.apartment.repository.ApartmentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApartmentService{
    private ApartmentDAO apartmentDAO;

    @Autowired
    public ApartmentService(ApartmentDAO apartmentDAO){
        this.apartmentDAO = apartmentDAO;
    }

    public ApartmentDTO getApartment(Integer apartmentId){
        return apartmentDAO.getApartment(apartmentId);
    }

    public ApartmentDTO insertApartment(ApartmentDTO apartmentDTO){
        return apartmentDAO.insertApartment(apartmentDTO);
    }

    public boolean updateApartment(ApartmentDTO apartmentDTO){
        return apartmentDAO.updateApartment(apartmentDTO);
    }

    public boolean deleteApartment(Integer apartmentId){
        return apartmentDAO.deleteApartment(apartmentId);
    }
}