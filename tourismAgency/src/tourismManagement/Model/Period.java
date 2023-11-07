package tourismManagement.Model;

import tourismManagement.Helper.DBConnecter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Period {//Dönem işlemleri
    private int id;

    private String period_name;
    private String start_date;
    private String end_date;
    private Hotel hotel;

    public Period(int id,  String period_name, String start_date, String end_date) {
        this.id = id;
        this.period_name = period_name;
        this.start_date = start_date;
        this.end_date = end_date;

    }

    public Period() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getPeriod_name() {
        return period_name;
    }

    public void setPeriod_name(String period_name) {
        this.period_name = period_name;
    }

    public static ArrayList<Period> getList() {
        ArrayList<Period> periodList = new ArrayList<>();
        String sql = "SELECT * FROM period";
        try {
            Statement st = DBConnecter.getInstance().createStatement();
            ResultSet data = st.executeQuery(sql);
            while (data.next()) {
                int id = data.getInt("id");
                String period_name = data.getString("period_name");
                String start_date = data.getString("start_date");
                String end_date = data.getString("end_date");

                Period obj = new Period(id,  period_name, start_date, end_date);
                periodList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return periodList;
    }
    public static Period getFetch(int id) {
        Period obj = null;
        String query = "SELECT * FROM period WHERE id = ?";

        try {

            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet data = pr.executeQuery();
            if (data.next()) {
                obj = new Period();
                obj.setId(data.getInt("id"));
                obj.setPeriod_name(data.getString("period_name"));
                obj.setStart_date(data.getString("start_date"));
                obj.setEnd_date(data.getString("end_date"));



            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }




}
