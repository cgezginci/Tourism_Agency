package tourismManagement.Model;

import tourismManagement.Helper.DBConnecter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RoomFeatures {//Oda özellikleri
    private int id;
    private String features;

    public RoomFeatures(int id, String features) {
        this.id = id;
        this.features = features;
    }

    public RoomFeatures() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }
    public static ArrayList<RoomFeatures> getList(){
        ArrayList<RoomFeatures> roomFeatures = new ArrayList<>();
        String sql = "SELECT * FROM room_features";
        RoomFeatures obj;
        try {
            Statement st = DBConnecter.getInstance().createStatement();
            ResultSet data = st.executeQuery(sql);
            while (data.next()){
                obj = new RoomFeatures();
                obj.setId(data.getInt("id"));
                obj.setFeatures(data.getString("features"));
                roomFeatures.add(obj);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roomFeatures;
    }
}
