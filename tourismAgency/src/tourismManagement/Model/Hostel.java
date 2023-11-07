package tourismManagement.Model;

import tourismManagement.Helper.DBConnecter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Hostel {//Pansiyon Tipleri
    private int id;
    private String hostel_name;
    private String[] hostel_types;

    public Hostel(int id, String hostel_name) {
        this.id = id;
        this.hostel_name = hostel_name;
    }

    public Hostel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHostel_name() {
        return hostel_name;
    }

    public void setHostel_name(String hostel_name) {
        this.hostel_name = hostel_name;
    }

    public String[] getHostel_types() {
        return hostel_types;
    }

    public void setHostel_types(String[] hostel_types) {
        this.hostel_types = hostel_types;
    }

    public static ArrayList<Hostel> getList(){
        ArrayList<Hostel> hostels = new ArrayList<>();
        String sql = "SELECT * FROM hostel";
        Hostel obj;
        try {
            Statement st = DBConnecter.getInstance().createStatement();
            ResultSet data = st.executeQuery(sql);
            while (data.next()){
                obj = new Hostel();
                obj.setId(data.getInt("id"));
                obj.setHostel_name(data.getString("hostel_name"));
                hostels.add(obj);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hostels;
    }
    public static Hostel getFetch(int id) {
        Hostel obj = null;
        String query = "SELECT * FROM hostel WHERE id = ?";

        try {

            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet data = pr.executeQuery();
            if (data.next()) {
                obj = new Hostel();
                obj.setId(data.getInt("id"));
                obj.setHostel_name(data.getString("hostel_name"));




            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }
    public static void main(String[] args) {

    }

}
