package tourismManagement.View;

import tourismManagement.Helper.Config;
import tourismManagement.Helper.DBConnecter;
import tourismManagement.Helper.Helper;
import tourismManagement.Model.Reservation;
import tourismManagement.Model.Room;

import javax.swing.*;
import java.sql.Statement;

public class ReservationGUI extends JFrame{//Rezervasyon yaparken ekrana açılan misafir bilgilerini alan GUI
    private JPanel wrapper;
    private JTextField fld_client_name;
    private JTextField fld_client_tc;
    private JTextField fld_client_number;
    private JButton btn_reservation;
    private JTextField fld_res_start_date;
    private JTextField fld_res_end_date;
    private JTextField fld_res_person_number;
    private Reservation reservation;
    private Room room;
    private int room_idd;
    public ReservationGUI(Reservation reservation, int room_idd) {
        this.reservation= reservation;
        this.room_idd=room_idd;
        this.room = Room.getFetch(room_idd);
        add(wrapper);
        setSize(400, 500);
        int x = Helper.screenCenterPoint("x", getSize());
        int y = Helper.screenCenterPoint("y", getSize());
        setLocation(x, y);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);





        btn_reservation.addActionListener(e -> {
                if (Helper.isFieldEmpty(fld_client_name) || Helper.isFieldEmpty(fld_client_tc) || Helper.isFieldEmpty(fld_client_number) || Helper.isFieldEmpty(fld_res_start_date) || Helper.isFieldEmpty(fld_res_end_date) || Helper.isFieldEmpty(fld_res_person_number)){
                    Helper.showMsg("fill");
                }else {
                    int id = reservation.getId();
                    int hotel_id = room.getHotel_id();
                    int room_id = room.getId();
                    int room_type_id = room.getRoom_type_id();
                    String clientName = fld_client_name.getText();
                    String clientTc = fld_client_tc.getText();
                    String clientNumber = fld_client_number.getText();
                    String start_date = fld_res_start_date.getText();
                    String end_Date = fld_res_end_date.getText();
                    int person_number = Integer.parseInt(fld_res_person_number.getText());
                    Reservation.updateReservation(hotel_id,room_id, room_type_id, clientName ,clientTc,clientNumber,start_date,end_Date,person_number,id);
                    updateStock(room_id);
                    dispose();
                }


        });
    }

    private void updateStock(int roomId) {//Rezervasyon gerçekleşirse stok 1 azalır
        try {
            Statement st = DBConnecter.getInstance().createStatement();
            String sql = "UPDATE room SET stock = stock - 1 WHERE id = " + roomId;
            int affectedRows = st.executeUpdate(sql);
            //System.out.println("Güncellenen satır sayısı: " + affectedRows);
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
