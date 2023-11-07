package tourismManagement.Model;

import tourismManagement.Helper.DBConnecter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RoomTypes {//Oda tipleri
    private int id;
    private String name;

    public RoomTypes(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public RoomTypes() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public static RoomTypes getFetch(int id){
        RoomTypes obj = null;
        String query = "SELECT * FROM room_types WHERE id = ?";
        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setInt(1 , id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj= new RoomTypes();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static ArrayList<RoomTypes> getList(){
        ArrayList<RoomTypes> roomTypes = new ArrayList<>();
        String sql = "SELECT * FROM room_types";
        RoomTypes obj;
        try {
            Statement st = DBConnecter.getInstance().createStatement();
            ResultSet data = st.executeQuery(sql);
            while (data.next()){
                obj = new RoomTypes();
                obj.setId(data.getInt("id"));
                obj.setName(data.getString("name"));
                roomTypes.add(obj);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roomTypes;
    }

}
