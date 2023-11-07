package tourismManagement.Model;

import tourismManagement.Helper.DBConnecter;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Room {//Oda yönetimi
    private int id;
    private int hotel_id;
    private int room_type_id;
    private int stock;
    private int bed;
    private int square_meters;
    private String[] room_features;
    private int period_id;
    private int hostel_id;
    private String person_type;
    private int price;
    private RoomTypes roomTypes;
    private Hotel hotel;
    private Hostel hostel;
    private Period period;


    public Room(int id, int hotel_id, int room_type_id, int stock, int bed, int square_meters, String[] room_features,int period_id,int hostel_id, String person_type , int price) {
        this.id = id;
        this.hotel_id = hotel_id;
        this.room_type_id = getRoom_type_id();
        this.stock = stock;
        this.bed = bed;
        this.square_meters = square_meters;
        this.room_features = room_features;
        this.hotel=Hotel.getFetch(hotel_id);
        this.roomTypes=RoomTypes.getFetch(room_type_id);
        this.period_id = period_id;
        this.hostel_id=hostel_id;
        this.person_type = person_type;
        this.price = price;
        this.period = Period.getFetch(period_id);
        this.hostel = Hostel.getFetch(hostel_id);
    }

    public Room() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public int getRoom_type_id() {
        return room_type_id;
    }

    public void setRoom_type_id(int room_type_id) {
        this.room_type_id = room_type_id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSquare_meters() {
        return square_meters;
    }

    public void setSquare_meters(int square_meters) {
        this.square_meters = square_meters;
    }

    public String[] getRoom_features() {
        return room_features;
    }

    public void setRoom_features(String[] room_features) {
        this.room_features = room_features;
    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public RoomTypes getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(RoomTypes roomTypes) {
        this.roomTypes = roomTypes;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public int getPeriod_id() {
        return period_id;
    }

    public void setPeriod_id(int period_id) {
        this.period_id = period_id;
    }

    public int getHostel_id() {
        return hostel_id;
    }

    public void setHostel_id(int hostel_id) {
        this.hostel_id = hostel_id;
    }

    public String getPerson_type() {
        return person_type;
    }

    public void setPerson_type(String person_type) {
        this.person_type = person_type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }



    public static ArrayList<Room> getListBySearch(String query){
        ArrayList<Room> searchRooms = new ArrayList<>();

        try {
            Statement st = DBConnecter.getInstance().createStatement();
            ResultSet data = st.executeQuery(query);
            while ((data.next())){
                int id = data.getInt("id");
                int hotel_id = data.getInt("hotel_id");
                int room_type_id = data.getInt("room_type_id");
                int stock = data.getInt("stock");
                int bed = data.getInt("bed");
                int squareMeters = data.getInt("square_meters");
                String roomFeatures = data.getString("room_features");
                String[] room_feature = roomFeatures.split(",");
                int period_id = data.getInt("period_id");
                int hostel_id = data.getInt("hostel_id");
                String person_type = data.getString("person_type");
                int price = data.getInt("price");


                Room obj = new Room(id,hotel_id,room_type_id,stock,bed,squareMeters,room_feature,period_id,hostel_id,person_type,price);
                searchRooms.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return searchRooms;
    }

    public static ArrayList<Room> getList() {
        ArrayList<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM room";
        try {
            Statement st = DBConnecter.getInstance().createStatement();
            ResultSet data = st.executeQuery(sql);
            while (data.next()) {
                int id = data.getInt("id");
                int hotel_id = data.getInt("hotel_id");
                int room_type_id = data.getInt("room_type_id");
                int stock = data.getInt("stock");
                int bed = data.getInt("bed");
                int squareMeters = data.getInt("square_meters");
                String roomFeatures = data.getString("room_features");
                String[] room_feature = roomFeatures.split(",");
                int period_id = data.getInt("period_id");
                int hostel_id = data.getInt("hostel_id");
                String person_type = data.getString("person_type");
                int price = data.getInt("price");


                Room obj = new Room(id,hotel_id,room_type_id,stock,bed,squareMeters,room_feature,period_id,hostel_id,person_type,price);
                rooms.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rooms;
    }

    public static boolean add(int hotel_id, int room_type_id, int stock, int bed, int square_meters, String[] room_features, int period_id , int hostel_id , String person_type , int price) {
        String query = "INSERT INTO room (hotel_id, room_type_id, stock, bed, square_meters, room_features, period_id, hostel_id, person_type, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setInt(1, hotel_id);
            pr.setInt(2, room_type_id);
            pr.setInt(3, stock);
            pr.setInt(4, bed);
            pr.setInt(5, square_meters);
            String roomFeaturesString = "";
            for (String feature : room_features) {
                roomFeaturesString += feature + ",";
            }
            roomFeaturesString = roomFeaturesString.substring(0, roomFeaturesString.length() - 1); // Son virgülü kaldır
            pr.setString(6, roomFeaturesString);
            pr.setInt(7, period_id);
            pr.setInt(8, hostel_id);
            pr.setString(9, person_type);
            pr.setInt(10, price);

            int response = pr.executeUpdate();

            return response != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static boolean delete(int id){
        String dlt = "DELETE FROM room WHERE id = ?";
        ArrayList<Reservation> reservations = Reservation.getList();
        for (Reservation obj : reservations){
            if (obj.getRoom_id() == id){
                Reservation.deleteReservation(obj.getId());
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




    public static String searchRooms(String hotelName, String city ,String startDate, String endDate, String personType) {
        String query = "SELECT * FROM room WHERE stock > 0";//Stoğu 0 olan bir oda arama listesinde gözükemez.

        if (!hotelName.isEmpty()) {
            query += " AND hotel_id IN (SELECT id FROM hotel WHERE hotel_name LIKE '%" + hotelName + "%')";
        }
        if (!city.isEmpty()) {
            query += " AND hotel_id IN (SELECT id FROM hotel WHERE city LIKE '%" + city + "%')";
        }

        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date userStart = outputFormat.parse(startDate);
                Date userEnd = outputFormat.parse(endDate);

                String formattedUserStart = outputFormat.format(userStart);
                String formattedUserEnd = outputFormat.format(userEnd);



                query += " AND period_id IN (SELECT id FROM period WHERE " +
                        "start_date <= '" + formattedUserEnd + "' " +
                        "AND end_date >= '" + formattedUserStart + "')";

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (!personType.isEmpty()) {
            query += " AND person_type LIKE '%" + personType + "%'";
        }

        return query;
    }

    public static Room getFetch(int id) {
        Room obj = null;
        String query = "SELECT * FROM room WHERE id = ?";

        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet data = pr.executeQuery();
            if (data.next()) {
                obj = new Room();
                obj.setId(data.getInt("id"));
                obj.setHotel_id(data.getInt("hotel_id"));
                obj.setRoom_type_id(data.getInt("room_type_id"));
                obj.setStock(data.getInt("stock"));
                obj.setBed(data.getInt("bed"));
                obj.setSquare_meters(data.getInt("square_meters"));
                String featuresNamesString = data.getString("room_features");
                String[] featuresNames = featuresNamesString.split(",");
                obj.setRoom_features(featuresNames);
                obj.setPeriod_id(data.getInt("period_id"));
                obj.setHostel_id(data.getInt("hostel_id"));
                obj.setPerson_type(data.getString("person_type"));
                obj.setPrice(data.getInt("price"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public static boolean updateFeaturesAndHostel(int id, int room_type_id  ,String[] room_features, int hostel_id, int period_id,String person_type){
        String updt ="UPDATE room SET room_type_id=?, room_features = ?, hostel_id = ?, period_id=?, person_type=? WHERE id = ?";


        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(updt);
            pr.setInt(1,room_type_id);
            String roomFeatureString = "";
            for (String types : room_features) {
                roomFeatureString += types + ",";
            }
            roomFeatureString = roomFeatureString.substring(0, roomFeatureString.length() - 1); // Son virgülü kaldır
            pr.setString(2, roomFeatureString);

            pr.setInt(3,hostel_id);
            pr.setInt(4 ,period_id);
            pr.setString(5,person_type);
            pr.setInt(6, id);

            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean update(int id, int stock, int bed, int square_meters, int price ){
        String updt ="UPDATE room SET stock = ?, bed = ?, square_meters = ?, price = ? WHERE id = ?";

        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(updt);
            pr.setInt(1, stock);
            pr.setInt(2, bed);
            pr.setInt(3, square_meters);
            pr.setInt(4, price);
            pr.setInt(5, id);

            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }











}
