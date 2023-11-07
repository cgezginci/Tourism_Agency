package tourismManagement.View;

import tourismManagement.Helper.Config;
import tourismManagement.Helper.Helper;
import tourismManagement.Model.Acente;
import tourismManagement.Model.Admin;
import tourismManagement.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame{//Login Ekranı admin veya acenta çalışanına göre farklı GUI lere yönlendirme yapar
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_user_uname;
    private JPasswordField fld_user_pass;
    private JButton btn_login;

    public LoginGUI(){
        add(wrapper);
        setSize(400,400);
        int x = Helper.screenCenterPoint("x", getSize());
        int y = Helper.screenCenterPoint("y", getSize());
        setLocation(x,y);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        btn_login.addActionListener(e -> {
        if (Helper.isFieldEmpty(fld_user_uname) || Helper.isFieldEmpty(fld_user_pass)){
            Helper.showMsg("fill");
        }else {
            User user = User.getFetch(fld_user_uname.getText(),fld_user_pass.getText());
            if (user == null){
                Helper.showMsg("Kullanıcı Bulunamadı");
            }else {
                switch (user.getType()){
                    case "admin" :
                        AdminGUI adminGUI = new AdminGUI((Admin) user);
                        break;
                    case "acente çalışanı" :
                        HotelGUI hotelGUI = new HotelGUI((Acente) user);
                        break;
                }
                dispose();
            }
        }

        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI log = new LoginGUI();
    }
}
