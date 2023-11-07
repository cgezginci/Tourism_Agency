package tourismManagement.Model;

import tourismManagement.Helper.Config;
import tourismManagement.Helper.DBConnecter;

import java.sql.*;
import java.util.ArrayList;

public class Facility {//Tesis Özellikleri
    private int id;
    private String facility_name;

    public Facility(int id, String facility_name) {
        this.id = id;
        this.facility_name = facility_name;
    }

    public Facility() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFacility_name() {
        return facility_name;
    }

    public void setFacility_name(String facility_name) {
        this.facility_name = facility_name;
    }

     public static ArrayList<Facility> getList(){
        ArrayList<Facility> facilities = new ArrayList<>();
        String sql = "SELECT * FROM facility";
        Facility obj;
        try {
            Statement st = DBConnecter.getInstance().createStatement();
            ResultSet data = st.executeQuery(sql);
            while (data.next()){
                obj = new Facility();
                obj.setId(data.getInt("id"));
                obj.setFacility_name(data.getString("facility_name"));
                facilities.add(obj);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return facilities;
    }



}
