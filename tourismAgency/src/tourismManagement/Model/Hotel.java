package tourismManagement.Model;

import tourismManagement.Helper.DBConnecter;
import tourismManagement.Helper.Helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Hotel {//Otel işlemleri
    private int id;
    private String hotel_name;
    private String city;
    private String loc;
    private String address;
    private String email;
    private String number;
    private int star;
    private String[] facilities;
    private String[] hostels;

    public Hotel(int id, String hotel_name, String city, String loc, String addres, String email, String number, int star, String[] facilities, String[] hostels) {
        this.id = id;
        this.hotel_name = hotel_name;
        this.city = city;
        this.loc = loc;
        this.address = addres;
        this.email = email;
        this.number = number;
        this.star = star;
        this.facilities = facilities;
        this.hostels = hostels;
    }

    public Hotel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String name) {
        this.hotel_name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String[] getFacilities() {
        return facilities;
    }

    public void setFacilities(String[] facilities) {
        this.facilities = facilities;
    }

    public String[] getHostels() {
        return hostels;
    }

    public void setHostels(String[] hostels) {
        this.hostels = hostels;
    }

    public static ArrayList<Hotel> getList(){
        ArrayList<Hotel> hotelList = new ArrayList<>();
        String sql = "SELECT * FROM hotel";
        Hotel obj;
        try {
            Statement st = DBConnecter.getInstance().createStatement();
            ResultSet data = st.executeQuery(sql);
            while (data.next()){
                obj = new Hotel();
                obj.setId(data.getInt("id"));
                obj.setHotel_name(data.getString("hotel_name"));
                obj.setCity(data.getString("city"));
                obj.setLoc(data.getString("loc"));
                obj.setAddress(data.getString("address"));
                obj.setEmail(data.getString("email"));
                obj.setNumber(data.getString("number"));
                obj.setStar(data.getInt("star"));
                String facilityNamesString = data.getString("facilities");
                String[] facilityNames = facilityNamesString.split(",");
                obj.setFacilities(facilityNames);
                String hostelNamesString = data.getString("hostels");
                String[] hostelNames = hostelNamesString.split(",");
                obj.setHostels(hostelNames);
                hotelList.add(obj);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotelList;
    }

    public static Hotel getFetch(int id) {
        Hotel obj = null;
        String query = "SELECT * FROM hotel WHERE id = ?";

        try {

            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet data = pr.executeQuery();
            if (data.next()) {
                obj = new Hotel();
                obj.setId(data.getInt("id"));
                obj.setHotel_name(data.getString("hotel_name"));
                obj.setCity(data.getString("city"));
                obj.setLoc(data.getString("loc"));
                obj.setAddress(data.getString("address"));
                obj.setEmail(data.getString("email"));
                obj.setNumber(data.getString("number"));
                obj.setStar(data.getInt("star"));
                String facilityNamesString = data.getString("facilities");
                String[] facilityNames = facilityNamesString.split(",");
                obj.setFacilities(facilityNames);
                String hostelNamesString = data.getString("hostels");
                String[] hostelNames = hostelNamesString.split(",");
                obj.setHostels(hostelNames);


            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }
    public static boolean add(String hotel_name, String city, String loc, String address, String email, String number, int star, String[] facilities, String[] hostels) {
        String query = "INSERT INTO hotel (hotel_name, city, loc, address, email, number, star, facilities, hostels) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setString(1, hotel_name);
            pr.setString(2, city);
            pr.setString(3, loc);
            pr.setString(4, address);
            pr.setString(5, email);
            pr.setString(6, number);
            pr.setInt(7, star);
            String facilityString = "";
            for (String types : facilities) {
                facilityString += types + ",";
            }
            facilityString = facilityString.substring(0, facilityString.length() - 1); // Son virgülü kaldır
            pr.setString(8, facilityString);
            String hostelString = "";
            for (String types : hostels) {
                hostelString += types + ",";
            }
            hostelString = hostelString.substring(0, hostelString.length() - 1); // Son virgülü kaldır
            pr.setString(9, hostelString);




            int response = pr.executeUpdate();

            return response != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static boolean delete(int id){
        String dlt = "DELETE FROM hotel WHERE id = ?";
        ArrayList<Room> rooms = Room.getList();
        for (Room obj : rooms){
            if (obj.getHotel().getId() == id){
                Room.delete(obj.getId());
            }
        }

        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(dlt);
            pr.setInt(1, id);

            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Hotel.getFetch(1);

    }
    public static boolean update(int id, String hotel_name, String city, String loc, String address , String email , String number , int star ){
        String updt ="UPDATE hotel SET hotel_name = ?, city = ?, loc = ?, address = ?, email=?,number=?,star=? WHERE id = ?";

        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(updt);
            pr.setString(1, hotel_name);
            pr.setString(2, city);
            pr.setString(3, loc);
            pr.setString(4, address);
            pr.setString(5, email);
            pr.setString(6, number);
            pr.setInt(7, star);
            pr.setInt(8, id);

            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean updateHostelAndFacility(int id, String[] facilities, String[] hostels){
        String updt ="UPDATE hotel SET facilities = ?, hostels = ? WHERE id = ?";


        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(updt);
            String facilityString = "";
            for (String types : facilities) {
                facilityString += types + ",";
            }
            facilityString = facilityString.substring(0, facilityString.length() - 1); // Son virgülü kaldır
            pr.setString(1, facilityString);
            String hostelString = "";
            for (String types : hostels) {
                hostelString += types + ",";
            }
            hostelString = hostelString.substring(0, hostelString.length() - 1); // Son virgülü kaldır
            pr.setString(2, hostelString);
            pr.setInt(3, id);

            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }




}
