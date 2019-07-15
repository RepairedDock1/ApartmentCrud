package jdb.apartment.constants;

public class SqlQueryConstants {
    public static String GET_MAX_ID = "SELECT MAX(APT_ID) FROM Apartment";
    public static String GET_APARTMENT = "SELECT * FROM Apartment WHERE APT_ID = ?";
    public static String INSERT_APARTMENT = "INSERT INTO Apartment "
        + "(APT_ID, APT_NM, UNITS, PRICE) values (:APT_ID, :APT_NM, :UNITS, :PRICE)";
    public static String UPDATE_APARTMENT = "UPDATE APARTMENT SET APT_NM = :APT_NM, "
        + " UNITS = :UNITS, PRICE = :PRICE "
        + "WHERE APT_ID = :APT_ID";
    public static String DELETE_APARTMENT = "DELETE FROM Apartment WHERE APT_ID = ?";

    private SqlQueryConstants(){
    }
}
