package tourismManagement.Model;

import tourismManagement.Helper.DBConnecter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Reservation {//Rezervasyon yönetimi
    private int id;
    private String clientName;
    private String clientTC;
    private String clientNumber;
    private String startDate;
    private String endDate;
    private Hotel hotel;
    private Room room;
    private RoomTypes roomTypes;
    private int otel_id;
    private int room_id;
    private int room_type_id;
    private int number_of_person;


    public Reservation(int id, String clientName, String clientTC, String clientNumber , String startDate, String endDate, int otel_id, int room_id , int room_type_id , int number_of_person) {
        this.id = id;
        this.clientName = clientName;
        this.clientTC = clientTC;
        this.clientNumber = clientNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.otel_id = otel_id;
        this.room_id = room_id;
        this.room_type_id=room_type_id;
        this.hotel = Hotel.getFetch(otel_id);
        this.room = Room.getFetch(room_id);
        this.roomTypes = RoomTypes.getFetch(room_type_id);
        this.number_of_person = number_of_person;



    }

    public Reservation() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientTC() {
        return clientTC;
    }

    public void setClientTC(String clientTC) {
        this.clientTC = clientTC;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }



    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getOtel_id() {
        return otel_id;
    }

    public void setOtel_id(int otel_id) {
        this.otel_id = otel_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public RoomTypes getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(RoomTypes roomTypes) {
        this.roomTypes = roomTypes;
    }

    public int getRoom_type_id() {
        return room_type_id;
    }

    public void setRoom_type_id(int room_type_id) {
        this.room_type_id = room_type_id;
    }

    public int getNumber_of_person() {
        return number_of_person;
    }

    public void setNumber_of_person(int number_of_person) {
        this.number_of_person = number_of_person;
    }

    public static ArrayList<Reservation> getList() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation";
        try {
            Statement st = DBConnecter.getInstance().createStatement();
            ResultSet data = st.executeQuery(sql);
            while (data.next()) {
                int id = data.getInt("id");
                int otel_id = data.getInt("hotel_id");
                int room_id = data.getInt("room_id");
                int room_type_id = data.getInt("room_type_id");
                String clientName = data.getString("client_name");
                String clientTC = data.getString("client_tc");
                String clientNumber = data.getString("client_number");
                String startDate = data.getString("start_date");
                String endDate = data.getString("end_date");
                int person_number = data.getInt("number_of_person");



                Reservation obj = new Reservation(id, clientName, clientTC, clientNumber, startDate, endDate,otel_id ,room_id , room_type_id , person_number);
                reservations.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservations;
    }

    public static boolean updateReservation(int hotel_id, int room_id, int room_type_id, String clientName, String clientTC , String clientNumber , String startDate, String endDate , int number_of_person ,int id) {
        String query = "UPDATE reservation SET hotel_id=? , room_id=? , room_type_id=? ,client_name=? , client_tc=? , client_number=?, start_date=?, end_date=?, number_of_person=? WHERE id=?";
        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setInt(1 ,hotel_id);
            pr.setInt(2 , room_id);
            pr.setInt(3 , room_type_id);
            pr.setString(4,clientName);
            pr.setString(5,clientTC);
            pr.setString(6,clientNumber);
            pr.setString(7,startDate);
            pr.setString(8,endDate);
            pr.setInt(9,number_of_person);
            pr.setInt(10,id);
            return pr.executeUpdate() != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void addRs() {//Reservation GUI açıldığında bütün değerlerin null olduğu bir satır databaseye eklenir.
        String sql = "INSERT INTO reservation () " +
                "VALUES ()";

        try {
            PreparedStatement ps = DBConnecter.getInstance().prepareStatement(sql);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Reservation getFetch(int room_id) {
        Reservation obj = null;
        String query = "SELECT * FROM reservation WHERE id = ?";

        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setInt(1, room_id);
            ResultSet data = pr.executeQuery();
            if (data.next()) {
                obj = new Reservation();
                obj.setId(data.getInt("id"));
                obj.setClientName(data.getString("client_name"));
                obj.setClientTC(data.getString("client_tc"));
                obj.setClientNumber(data.getString("client_number"));
                obj.setStartDate(data.getString("start_date"));
                obj.setEndDate(data.getString("end_date"));
                obj.setNumber_of_person(data.getInt("number_of_person"));
                obj.setOtel_id(data.getInt("hotel_id"));
                obj.setRoom_id(data.getInt("room_id"));
                obj.setRoom_type_id(data.getInt("room_type_id"));

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public static boolean delete(int id, String bed){
        String dlt = "DELETE FROM reservation WHERE id = ?";

        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(dlt);
            pr.setInt(1, id);

            int affectedRows = pr.executeUpdate();


            if (affectedRows !=-1){
                updateStock(bed);
            }

            return pr.executeUpdate() != -1;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public static boolean deleteReservation(int id) {
        String dlt = "DELETE FROM reservation WHERE id = ?";

        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(dlt);
            pr.setInt(1, id);


            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static void updateStock(String bed) {
        try {
            Statement st = DBConnecter.getInstance().createStatement();
            String sql = "UPDATE room SET stock = stock + 1 WHERE bed = '" + bed + "'";
            int affectedRows = st.executeUpdate(sql);
           // System.out.println("Güncellenen satır sayısı: " + affectedRows); // Satırların güncellenip güncellenmediğinin kontrolü
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean update(int id, String clientName, String clientTC, String clientNumber , String startDate, String endDate, int number_of_person ){
        String updt ="UPDATE reservation SET client_name = ?, client_tc = ?, client_number = ?, start_Date = ?, end_date = ? , number_of_person=? WHERE id = ?";

        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(updt);
            pr.setString(1, clientName);
            pr.setString(2, clientTC);
            pr.setString(3, clientNumber);
            pr.setString(4, startDate);
            pr.setString(5, endDate);
            pr.setInt(6,number_of_person);
            pr.setInt(7 , id);

            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
}
