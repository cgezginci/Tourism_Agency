package tourismManagement.View;

import tourismManagement.Helper.Config;
import tourismManagement.Helper.Helper;
import tourismManagement.Model.Admin;
import tourismManagement.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminGUI extends JFrame{//Admin Ekranı
    private JPanel wrapper;
    private JTable tbl_user;
    private JLabel fld_welcome;
    private JButton btn_logout;
    private JTextField fld_user_name;
    private JTextField fld_user_uname;
    private JTextField fld_user_pass;
    private JComboBox cmb_user_type;
    private JButton btn_add;
    private JScrollPane scrl_user;
    private JTextField fld_update_name;
    private JTextField fld_update_uname;
    private JTextField fld_update_pass;
    private JComboBox cmb_update_type;
    private JButton btn_update;
    private JTextField fld_update_id;
    private DefaultTableModel mdl_user;
    private Object[] row_user;
    private JPopupMenu userMenu;
    private final Admin admin;

    public AdminGUI(Admin admin){
        this.admin = admin;
        add(wrapper);
        setSize(900,500);
        int x = Helper.screenCenterPoint("x", getSize());
        int y = Helper.screenCenterPoint("y", getSize());
        setLocation(x,y);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        fld_welcome.setText("Hoşgeldin : " +admin.getName());

        userMenu = new JPopupMenu();
        JMenuItem deleteUser = new JMenuItem("Sil");
        userMenu.add(deleteUser);

        deleteUser.addActionListener(e -> {
            if (Helper.confirm("sure")){
                int select_id = Integer.parseInt(tbl_user.getValueAt(tbl_user.getSelectedRow() , 0).toString());
                if (User.delete(select_id)){
                    Helper.showMsg("done");
                    loadUserModel();
                }else {
                    Helper.showMsg("error");
                }
            }

        });

        mdl_user = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1 || column == 2 || column == 3 || column == 4)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_user = {"ID" , "İsim" , "Kullanıcı Adı" , "Şifre" , "Üyelik Tipi"};
        mdl_user.setColumnIdentifiers(col_user);
        row_user = new Object[col_user.length];
        loadUserModel();
        tbl_user.setModel(mdl_user);
        tbl_user.setComponentPopupMenu(userMenu);
        tbl_user.getTableHeader().setReorderingAllowed(false);
        tbl_user.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_user_id = tbl_user.getValueAt(tbl_user.getSelectedRow() , 0).toString();
                fld_update_id.setText(select_user_id);
                String select_user_name= tbl_user.getValueAt(tbl_user.getSelectedRow() , 1).toString();
                fld_update_name.setText(select_user_name);
                String select_user_uname= tbl_user.getValueAt(tbl_user.getSelectedRow() , 2).toString();
                fld_update_uname.setText(select_user_uname);
                String select_user_pass= tbl_user.getValueAt(tbl_user.getSelectedRow() , 3).toString();
                fld_update_pass.setText(select_user_pass);
            }catch (Exception exception){


            }

        });

        btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginGUI loginGUI = new LoginGUI();
            }
        });
        btn_add.addActionListener(e -> { //Kullanıcı Ekleme Butonu
            if (Helper.isFieldEmpty(fld_user_name)|| Helper.isFieldEmpty(fld_user_uname)|| Helper.isFieldEmpty(fld_user_pass)){
                Helper.showMsg("fill");

            }else {
                String name = fld_user_name.getText();
                String uname = fld_user_uname.getText();
                String pass = fld_user_pass.getText();
                String type = cmb_user_type.getSelectedItem().toString();
                if (User.add(name,uname,pass,type)){
                    Helper.showMsg("done");
                    loadUserModel();
                    fld_user_name.setText(null);
                    fld_user_uname.setText(null);
                    fld_user_pass.setText(null);


                }
            }

        });

        btn_update.addActionListener(e -> { //Kullanıcıyı Güncelleme Butonu

                if (Helper.confirm("sure")){
                    int id = Integer.parseInt(fld_update_id.getText());
                    String name = fld_update_name.getText();
                    String uname = fld_update_uname.getText();
                    String pass = fld_update_pass.getText();
                    String type = cmb_update_type.getSelectedItem().toString();
                    if (User.update(id,name,uname,pass,type)){
                        Helper.showMsg("done");
                        loadUserModel();
                    }else {
                        Helper.showMsg("error");
                    }
                }


        });
    }


    public  void loadUserModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user.getModel();
        clearModel.setRowCount(0);
        for (User obj : User.getList() ){

            int i = 0;
            row_user[i++] = obj.getId();
            row_user[i++] = obj.getName();
            row_user[i++] = obj.getUname();
            row_user[i++] = obj.getPass();
            row_user[i++] = obj.getType();
            mdl_user.addRow(row_user);
        }
    }

    public static void main(String[] args) {
        Helper.setLayout();
        Admin admin1 = new Admin();
        AdminGUI adminGUI = new AdminGUI(admin1);
    }


}
