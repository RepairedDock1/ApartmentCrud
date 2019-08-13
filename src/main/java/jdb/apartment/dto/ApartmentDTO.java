package jdb.apartment.dto;

public class ApartmentDTO {
    private Integer apartmentId;
    private String  apartmentName;
    private Integer units;
    private Integer price;

    public ApartmentDTO() {
    }

    public ApartmentDTO(String apartmentName, Integer units, Integer price){
        this.apartmentName = apartmentName;
        this.units = units;
        this.price = price;
    }

    public ApartmentDTO(Integer apartmentId, String apartmentName, Integer units,
        Integer price) {
        this.apartmentId = apartmentId;
        this.apartmentName = apartmentName;
        this.units = units;
        this.price = price;
    }

    public Integer getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Integer apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ApartmentDTO{" +
            "apartmentId=" + apartmentId +
            ", apartmentName='" + apartmentName + '\'' +
            ", units=" + units +
            ", price=" + price +
            '}';
    }
}
