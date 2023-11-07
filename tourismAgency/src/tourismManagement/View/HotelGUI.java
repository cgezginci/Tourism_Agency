package tourismManagement.View;

import tourismManagement.Helper.Config;
import tourismManagement.Helper.DBConnecter;
import tourismManagement.Helper.Helper;
import tourismManagement.Helper.Item;
import tourismManagement.Model.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Statement;
import java.util.*;

public class HotelGUI extends JFrame {//Acenta çalışanı için her şeyin yönetimi burada
    private JPanel wrapper;
    private JTabbedPane tab_hotel;
    private JScrollPane scrl_hotel;
    private JTable tbl_hotel;
    private JLabel fld_welcome;
    private JButton btn_logout;
    private JScrollPane scrl_period;
    private JTable tbl_period;
    private JScrollPane scrl_room;
    private JTable tbl_room;
    private JTextField fld_add_hotel_name;
    private JTextField fld_add_city;
    private JTextField fld_add_loc;
    private JTextField fld_add_address;
    private JTextField fld_add_email;
    private JTextField fld_add_number;
    private JTextField fld_add_star;
    private JTextField fld_add_facilities;
    private JTextField fld_add_hostel;
    private JButton btn_add;
    private JTextField fld_add_room_hotel;
    private JTextField fld_add_room_name;
    private JTextField fld_add_room_stock;
    private JTextField fld_add_room_bed;
    private JTextField fld_add_room_squaremeter;
    private JTextField fld_add_room_features;
    private JButton btn_add_room;
    private JComboBox cmb_add_room_hotel;
    private JComboBox cmb_add_room_name;
    private JComboBox cmb_add_room_periodname;
    private JComboBox cmb_add_room_hostel;
    private JComboBox cmb_add_room_persontype;
    private JTextField fld_add_room_price;
    private JTable tbl_srch;
    private JTextField fld_srch_hotelname;
    private JTextField fld_srch_start_date;
    private JTextField fld_srch_persontype;
    private JButton btn_srch;
    private JTextField fld_srch_end_date;
    private JScrollPane scrl_reservation;
    private JTable tbl_reservation;
    private JTextField fld_update_facilities;
    private JTextField fld_update_hostel;
    private JButton btn_update;
    private JTextField fld_update_features;
    private JComboBox cmb_update_hostel;
    private JButton btn_update_room;
    private JComboBox cmb_update_period;
    private JComboBox cmb_update_person_type;
    private JComboBox cmb_update_room_type;
    private JTextField fld_srch_city;
    private JComboBox cmb_add_hostel_name_room;
    private DefaultTableModel mdl_hotel;
    private Object[] row_hotel;
    private Map<Integer, String> facilityNamesMap;
    private Map<Integer, String> hostelNamesMap;
    private Acente acente;
    private DefaultTableModel mdl_period;
    private Object[] row_period;
    private DefaultTableModel mdl_room;
    private Object[] row_room;
    private Map<Integer, String> roomFeaturesMap;
    private boolean shown = false;
    private JPopupMenu hotelMenu;
    private JPopupMenu roomMenu;
    private DefaultTableModel mdl_reservation;
    private Object[] row_reservation;
    private JPopupMenu reservationMenu;


    public HotelGUI() {


    }


    public HotelGUI(Acente acente) {
        this.acente = acente;
        add(wrapper);
        setSize(1100, 800);
        int x = Helper.screenCenterPoint("x", getSize());
        int y = Helper.screenCenterPoint("y", getSize());
        setLocation(x, y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        facilityNamesMap = new HashMap<>();
        hostelNamesMap = new HashMap<>();
        roomFeaturesMap = new HashMap<>();
        loadFacilityTypes();
        loadHostelTypes();
        loadRoomFeaturesMap();

        // Bazı fieldların işlevlerini yerine getirebilmek için tab ile gezinmeyi kapatmak zorundayım.
        KeyboardFocusManager.getCurrentKeyboardFocusManager().setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());


        fld_welcome.setText("Hoşgeldin : " + acente.getName());
        hotelMenu = new JPopupMenu();
        JMenuItem deleteHotel = new JMenuItem("Sil");
        hotelMenu.add(deleteHotel);

        deleteHotel.addActionListener(e -> {//Otel silme
            if (Helper.confirm("sure")) {
                int select_id = Integer.parseInt(tbl_hotel.getValueAt(tbl_hotel.getSelectedRow(), 0).toString());
                if (Hotel.delete(select_id)) {
                    Helper.showMsg("done");
                    loadHotelModel();
                    loadHotelCombo();
                    loadRoomModel();
                    loadReservationModel();
                } else {
                    Helper.showMsg("error");
                }
            }

        });


        //Hotel List (Otel Yönetimi Kısmı)---------------------------------------------------------------------------
        mdl_hotel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 8 || column == 9)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_hotel = {"ID", "Otel Adı", "Şehir", "Bölge", "Adres", "Eposta", "Telefon Numarası", "Yıldız Sayısı", "Tesis Özellikleri", "Pansiyon Tipi"};

        mdl_hotel.setColumnIdentifiers(col_hotel);
        row_hotel = new Object[col_hotel.length];
        loadHotelModel();
        tbl_hotel.setModel(mdl_hotel);
        tbl_hotel.setComponentPopupMenu(hotelMenu);
        tbl_hotel.getTableHeader().setReorderingAllowed(false);


        tbl_hotel.getColumnModel().getColumn(0).setPreferredWidth(1);   //Tablonun sütun genişliğini ayarladım.
        tbl_hotel.getColumnModel().getColumn(1).setPreferredWidth(50);
        tbl_hotel.getColumnModel().getColumn(2).setPreferredWidth(50);
        tbl_hotel.getColumnModel().getColumn(3).setPreferredWidth(50);
        tbl_hotel.getColumnModel().getColumn(4).setPreferredWidth(90);
        tbl_hotel.getColumnModel().getColumn(6).setPreferredWidth(60);
        tbl_hotel.getColumnModel().getColumn(7).setPreferredWidth(40);
        tbl_hotel.getColumnModel().getColumn(8).setPreferredWidth(200);
        tbl_hotel.getColumnModel().getColumn(9).setPreferredWidth(200);

        tbl_hotel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tbl_hotel.getSelectedRow();
                if (selectedRow >= 0) {
                    String facility = tbl_hotel.getValueAt(selectedRow, 8).toString();
                    String hostel = tbl_hotel.getValueAt(selectedRow, 9).toString();
                    fld_update_facilities.setText(facility);
                    fld_update_hostel.setText(hostel);
                }
            }
        });
        tbl_hotel.getModel().addTableModelListener(e -> {//Güncelleme
            if (e.getType() == TableModelEvent.UPDATE) {
                int hotel_id = Integer.parseInt(tbl_hotel.getValueAt(tbl_hotel.getSelectedRow(), 0).toString());
                String hotel_name = tbl_hotel.getValueAt(tbl_hotel.getSelectedRow(), 1).toString();
                String hotel_city = tbl_hotel.getValueAt(tbl_hotel.getSelectedRow(), 2).toString();
                String hotel_loc = tbl_hotel.getValueAt(tbl_hotel.getSelectedRow(), 3).toString();
                String hotel_address = tbl_hotel.getValueAt(tbl_hotel.getSelectedRow(), 4).toString();
                String hotel_email = tbl_hotel.getValueAt(tbl_hotel.getSelectedRow(), 5).toString();
                String hotel_number = tbl_hotel.getValueAt(tbl_hotel.getSelectedRow(), 6).toString();
                int hotel_star = Integer.parseInt(tbl_hotel.getValueAt(tbl_hotel.getSelectedRow(), 7).toString());


                if (Hotel.update(hotel_id, hotel_name, hotel_city, hotel_loc, hotel_address, hotel_email, hotel_number, hotel_star)) {
                    Helper.showMsg("done");
                }
                loadHotelModel();
                loadRoomModel();
                loadReservationModel();
            }
        });


        //Hotel List End --------------------------------------------------------------------------------------------


        //Room List (Oda Yönetimi Kısmı) Fiyat yönetimi - Arama Yönetimi de bu kısım içerisinde-----------------------------------------------------------------------------

        roomMenu = new JPopupMenu();
        JMenuItem reservationRoom = new JMenuItem("Rezervasyon Yap");
        JMenuItem deleteRoom = new JMenuItem("Sil");
        roomMenu.add(reservationRoom);
        roomMenu.add(deleteRoom);


        deleteRoom.addActionListener(e -> {//Silme işlemi
            if (Helper.confirm("sure")) {
                int select_id = Integer.parseInt(tbl_room.getValueAt(tbl_room.getSelectedRow(), 0).toString());
                if (Room.delete(select_id)) {
                    Helper.showMsg("done");
                    loadRoomModel();
                    loadReservationModel();
                } else {
                    Helper.showMsg("error");
                }
            }

        });

        reservationRoom.addActionListener(e -> {//Rezervasyon yapma kısmı stok 0 ve 0 dan küçükse rezervasyon yapmaya izin verilmez
            int selected_row = tbl_room.getSelectedRow();
            int stock = 0;
            if (selected_row >= 0) {
                stock = Integer.parseInt(tbl_room.getValueAt(selected_row, 3).toString());

                if (stock <= 0) {
                    Helper.showMsg("Stoğu 0 olan oda için rezervasyon yapamazsınız.");
                    return;
                }
            }
            Reservation.addRs();
            int select_last_id = Reservation.getList().get(Reservation.getList().size() - 1).getId();
            int selectedRoomId = Integer.parseInt(tbl_room.getValueAt(tbl_room.getSelectedRow(), 0).toString());
            ReservationGUI reservationGUI = new ReservationGUI(Reservation.getFetch(select_last_id), selectedRoomId);

            reservationGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadReservationModel();
                    loadRoomModel();


                }
            });


        });


        mdl_room = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1 || column == 2 || column == 6 || column == 7 || column == 8 || column == 9)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_room = {"ID", "Otel Adı", "Oda Adı ", "Stok Sayısı", "Yatak Sayısı", "Metrekare", "Oda Özellikleri", "Dönem", "Pansiyon Tipi", "Kişi Tipi", "Fiyat"};
        mdl_room.setColumnIdentifiers(col_room);
        row_room = new Object[col_room.length];
        loadRoomModel();
        tbl_room.setModel(mdl_room);
        tbl_room.setComponentPopupMenu(roomMenu);
        tbl_room.getTableHeader().setReorderingAllowed(false);

        loadRoomCombo();
        loadPeriodCombo();
        loadHotelCombo();
        loadHostelCombo();
        loadUpdatePeriodCombo();
        loadUpdateRoomTypeCombo();

        tbl_room.getColumnModel().getColumn(0).setPreferredWidth(60);   //Tablonun sütun genişliğini ayarladım.
        tbl_room.getColumnModel().getColumn(1).setPreferredWidth(150);
        tbl_room.getColumnModel().getColumn(2).setPreferredWidth(50);
        tbl_room.getColumnModel().getColumn(3).setPreferredWidth(50);
        tbl_room.getColumnModel().getColumn(4).setPreferredWidth(50);
        tbl_room.getColumnModel().getColumn(6).setPreferredWidth(200);
        tbl_room.getColumnModel().getColumn(7).setPreferredWidth(100);
        tbl_room.getColumnModel().getColumn(8).setPreferredWidth(100);
        tbl_room.getColumnModel().getColumn(9).setPreferredWidth(100);
        tbl_room.getColumnModel().getColumn(10).setPreferredWidth(70);

        tbl_room.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int room_id = Integer.parseInt(tbl_room.getValueAt(tbl_room.getSelectedRow(), 0).toString());
                int stock = Integer.parseInt(tbl_room.getValueAt(tbl_room.getSelectedRow(), 3).toString());
                int bed = Integer.parseInt(tbl_room.getValueAt(tbl_room.getSelectedRow(), 4).toString());
                int squareMeters = Integer.parseInt(tbl_room.getValueAt(tbl_room.getSelectedRow(), 5).toString());
                int price = Integer.parseInt(tbl_room.getValueAt(tbl_room.getSelectedRow(), 10).toString());


                if (Room.update(room_id, stock, bed, squareMeters, price)) {
                    Helper.showMsg("done");
                } else {
                    Helper.showMsg("Hata");
                }

                loadRoomModel();
                loadReservationModel();
            }
        });


        //Room List End -----------------------------------------------------------------------------------------------


        //Period List (Dönem Yönetimi Kısmı)-------------------------------------------------------------------------
        mdl_period = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_period = {"ID", "Dönem Adı", "Dönem Başlangıç Tarihi", "Dönem Bitiş Tarihi"};
        mdl_period.setColumnIdentifiers(col_period);
        row_period = new Object[col_period.length];
        loadPeriodModel();
        tbl_period.setModel(mdl_period);

        tbl_period.getTableHeader().setReorderingAllowed(false);
        //Period List End---------------------------------------------------------------------------------------------

        //Reservation List (Rezervasyon yönetimi)--------------------------

        reservationMenu = new JPopupMenu();
        JMenuItem deleteReservation = new JMenuItem("Rezervasyonu Sil");
        reservationMenu.add(deleteReservation);

        deleteReservation.addActionListener(e -> {//Silme kısmı
            if (Helper.confirm("sure")) {
                int select_reservation_id = Integer.parseInt(tbl_reservation.getValueAt(tbl_reservation.getSelectedRow(), 0).toString());
                String bed = tbl_reservation.getValueAt(tbl_reservation.getSelectedRow(), 16).toString(); // Bed değerini al
                if (Reservation.delete(select_reservation_id, bed)) {
                    Helper.showMsg("done");
                    loadRoomModel();
                    loadReservationModel();
                } else {
                    Helper.showMsg("error");
                }
            }
        });


        mdl_reservation = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 4 || column == 5 || column == 6 || column == 7 || column == 8 ||
                        column == 9 || column == 10 || column == 14 || column == 15 ||
                        column == 16 || column == 17) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_reservation = {"ID", "Müşteri Adı ", "Müşteri TC", "Müşteri Numarası", "Otel Adı", "Şehir", "Bölge", "Otel No", "Otel Tesis Özellikleri", "Otel Pansiyon Tipleri", "Oda Adı", "Giriş Tarihi", "Çıkış Tarihi", "Kişi Sayısı", "Kişi Tipi", "Oda Özellikleri ", "Yatak Sayısı", "Fiyat"};
        mdl_reservation.setColumnIdentifiers(col_reservation);
        row_reservation = new Object[col_reservation.length];

        loadReservationModel();
        tbl_reservation.setModel(mdl_reservation);
        tbl_reservation.setComponentPopupMenu(reservationMenu);
        tbl_reservation.getTableHeader().setReorderingAllowed(false);


        btn_logout.addActionListener(e -> {//Çıkış Butonu
            dispose();
            LoginGUI loginGUI = new LoginGUI();

        });

        tbl_reservation.getModel().addTableModelListener(e -> {//Güncelleme
            if (e.getType() == TableModelEvent.UPDATE) {
                int reservation_id = Integer.parseInt(tbl_reservation.getValueAt(tbl_reservation.getSelectedRow(), 0).toString());
                String client_name = (tbl_reservation.getValueAt(tbl_reservation.getSelectedRow(), 1).toString());
                String client_tc = (tbl_reservation.getValueAt(tbl_reservation.getSelectedRow(), 2).toString());
                String client_number = (tbl_reservation.getValueAt(tbl_reservation.getSelectedRow(), 3).toString());
                String start_date = (tbl_reservation.getValueAt(tbl_reservation.getSelectedRow(), 11).toString());
                String end_date = (tbl_reservation.getValueAt(tbl_reservation.getSelectedRow(), 12).toString());
                int number_of_person = Integer.parseInt(tbl_reservation.getValueAt(tbl_reservation.getSelectedRow(), 13).toString());


                if (Reservation.update(reservation_id, client_name, client_tc, client_number, start_date, end_date, number_of_person)) {
                    Helper.showMsg("done");
                } else {
                    Helper.showMsg("Hata");
                }

                loadReservationModel();
            }
        });

        tbl_reservation.getColumnModel().getColumn(0).setPreferredWidth(60);   //Tablonun sütun genişliğini ayarladım.
        tbl_reservation.getColumnModel().getColumn(1).setPreferredWidth(100);
        tbl_reservation.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbl_reservation.getColumnModel().getColumn(3).setPreferredWidth(100);
        tbl_reservation.getColumnModel().getColumn(4).setPreferredWidth(100);
        tbl_reservation.getColumnModel().getColumn(5).setPreferredWidth(70);
        tbl_reservation.getColumnModel().getColumn(6).setPreferredWidth(80);
        tbl_reservation.getColumnModel().getColumn(7).setPreferredWidth(100);
        tbl_reservation.getColumnModel().getColumn(8).setPreferredWidth(150);
        tbl_reservation.getColumnModel().getColumn(9).setPreferredWidth(150);
        tbl_reservation.getColumnModel().getColumn(10).setPreferredWidth(100);
        tbl_reservation.getColumnModel().getColumn(11).setPreferredWidth(100);
        tbl_reservation.getColumnModel().getColumn(12).setPreferredWidth(80);
        tbl_reservation.getColumnModel().getColumn(13).setPreferredWidth(80);
        tbl_reservation.getColumnModel().getColumn(14).setPreferredWidth(80);
        tbl_reservation.getColumnModel().getColumn(15).setPreferredWidth(80);
        tbl_reservation.getColumnModel().getColumn(16).setPreferredWidth(80);
        tbl_reservation.getColumnModel().getColumn(17).setPreferredWidth(70);

        //Reservation List End----------------------------------------------------------------------------------------
        //Fieldlere tıklanıldığında kullanıcıya ne yapması gerektiğini söyleyen mesajlar ekrana verilir.
        fld_add_facilities.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Helper.showMsg("1. Ücretsiz Otopark \n2. Ücretsiz WiFi\n3. Yüzme Havuzu\n4. Fitness Center\n5. Hotel Concierge\n6. SPA\n7. 7/24 Oda Servisi\nLütfen eklemek istediğiniz tesis özelliğinin ID sini yazın ve araya virgül koymayı unutmayın!");
                shown = true;
            }
        });
        fld_update_facilities.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Helper.showMsg("Güncelleme yapmak için lütfen önce tüm satırı silin sonrasında:\n1. Ücretsiz Otopark \n2. Ücretsiz WiFi\n3. Yüzme Havuzu\n4. Fitness Center\n5. Hotel Concierge\n6. SPA\n7. 7/24 Oda Servisi\nLütfen eklemek istediğiniz tesis özelliğinin ID sini yazın ve araya virgül koymayı unutmayın!");
                shown = true;
            }
        });
        fld_add_hostel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Helper.showMsg("1. Ultra Herşey Dahil\n2. Herşey Dahil\n3. Oda Kahvaltı\n4. Tam Pansiyon\n5. Yarım Pansiyon\n6. Sadece Yatak\n7. Alkol Hariç Full credit\nLütfen eklemek istediğiniz pansiyon tipinin ID sini yazın ve araya virgül koymayı unutmayın!");
                shown = true;
            }
        });
        fld_update_hostel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Helper.showMsg("Güncelleme yapmak için lütfen önce tüm satırı silin sonrasında:\n1. Ultra Herşey Dahil\n2. Herşey Dahil\n3. Oda Kahvaltı\n4. Tam Pansiyon\n5. Yarım Pansiyon\n6. Sadece Yatak\n7. Alkol Hariç Full credit\nLütfen eklemek istediğiniz pansiyon tipinin ID sini yazın ve araya virgül koymayı unutmayın!");
                shown = true;
            }
        });
        fld_add_room_features.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Helper.showMsg("1. Televizyon\n2. Minibar\n3. Oyun Konsolu\n4. Kasa\n5. Projeksiyon\nLütfen odada var olan özelliklerin ID sini yazın ve virgül koymayı unutmayın!");
                shown = true;
            }
        });
        fld_update_features.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Helper.showMsg("Güncelleme yapmak için lütfen önce tüm satırı silin sonrasında:\n1. Televizyon\n2. Minibar\n3. Oyun Konsolu\n4. Kasa\n5. Projeksiyon\nLütfen odada var olan özelliklerin ID sini yazın ve virgül koymayı unutmayın!");
                shown = true;
            }
        });
        fld_srch_start_date.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Helper.showMsg("Giriş tarihinizi yıl ay gün olarak yazınız ve unutmayın ki giriş tarihi çıkış tarihinden sonraki bir tarih olamaz.");
                shown = true;
            }
        });
        fld_srch_end_date.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Helper.showMsg("Çıkış tarihinizi yıl ay gün olarak yazınız ve unutmayın ki çıkış tarihi giriş tarihinden önceki bir tarih olamaz.");
                shown = true;
            }
        });

        //-------------------------------------------------------------------------------------------------


        btn_update.setEnabled(false);  // btn_update başlangıçta devre dışı bırakılır
        tbl_hotel.getSelectionModel().addListSelectionListener(e -> {
            if (!tbl_hotel.getSelectionModel().isSelectionEmpty()) {
                btn_update.setEnabled(true); // Satır seçiliyse butonu aktifleştir
            } else {
                btn_update.setEnabled(false); // Satır seçili değilse butonu devre dışı bırak
            }
        });
        btn_update_room.setEnabled(false);  // btn_update başlangıçta devre dışı bırakılır
        tbl_room.getSelectionModel().addListSelectionListener(e -> {
            if (!tbl_room.getSelectionModel().isSelectionEmpty()) {
                btn_update_room.setEnabled(true); // Satır seçiliyse butonu aktifleştir
            } else {
                btn_update_room.setEnabled(false); // Satır seçili değilse butonu devre dışı bırak
            }
        });


        btn_update.addActionListener(e -> {//Otel için tesis ve pansiyon tipi güncelleme kısmı
            String facilitiesText = fld_update_facilities.getText();
            String hostelsText = fld_update_hostel.getText();

            if (areAllNumeric(facilitiesText) && areAllNumeric(hostelsText)) {
                int selectedRow = tbl_hotel.getSelectedRow();
                int id = Integer.parseInt(tbl_hotel.getValueAt(selectedRow, 0).toString());
                String[] facilities = facilitiesText.split(",");
                String[] hostels = hostelsText.split(",");

                if (Hotel.updateHostelAndFacility(id, facilities, hostels)) {
                    Helper.showMsg("Güncelleme başarılı");
                } else {
                    Helper.showMsg("Güncelleme sırasında bir hata oluştu");
                }
            } else {
                Helper.showMsg("Lütfen sadece sayısal değerler giriniz");
            }

            loadHotelModel();
            loadRoomModel();
            loadReservationModel();
        });
        btn_update_room.addActionListener(e -> {
            String featuresText = fld_update_features.getText();
            // Seçili oda bilgilerini al
            if (areAllNumeric(featuresText)) {
                int selectedRow = tbl_room.getSelectedRow();
                int id = Integer.parseInt(tbl_room.getValueAt(selectedRow, 0).toString());
                String[] room_features = fld_update_features.getText().split(",");
                Item selectedItem = (Item) cmb_update_hostel.getSelectedItem();
                Item periodItem = (Item) cmb_update_period.getSelectedItem();
                String personTypeItem = (String) cmb_update_person_type.getSelectedItem();
                Item roomTypeItem = (Item) cmb_update_room_type.getSelectedItem();

                int hostel_id = selectedItem.getKey();
                int period_id = periodItem.getKey();
                int room_type_id = roomTypeItem.getKey();


                // Oda bilgilerini güncelle
                boolean updateSuccess = Room.updateFeaturesAndHostel(id, room_type_id, room_features, hostel_id, period_id, personTypeItem);

                if (updateSuccess) {
                    Helper.showMsg("Güncelleme başarılı");
                } else {
                    Helper.showMsg("Güncelleme sırasında bir hata oluştu");
                }
            } else {
                Helper.showMsg("Lütfen sadece sayısal değerler girin.");
            }


            loadRoomModel();
            loadReservationModel();

        });

        tbl_room.getSelectionModel().addListSelectionListener(e -> {
            if (!tbl_room.getSelectionModel().isSelectionEmpty()) {
                int selectedRow = tbl_room.getSelectedRow();
                int room_id = Integer.parseInt(tbl_room.getValueAt(selectedRow, 0).toString());
                loadHostelTypeByRoom(room_id);
            }
        });
        tbl_room.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tbl_room.getSelectedRow();
                if (selectedRow >= 0) {
                    String facility = tbl_room.getValueAt(selectedRow, 6).toString();
                    fld_update_features.setText(facility);
                }
            }
        });


        btn_add.addActionListener(e -> {//Otel Ekleme
            if (Helper.isFieldEmpty(fld_add_hotel_name) || Helper.isFieldEmpty(fld_add_city) || Helper.isFieldEmpty(fld_add_loc) || Helper.isFieldEmpty(fld_add_address) || Helper.isFieldEmpty(fld_add_email) || Helper.isFieldEmpty(fld_add_number) || Helper.isFieldEmpty(fld_add_facilities) || Helper.isFieldEmpty(fld_add_hostel)) {
                Helper.showMsg("fill");
            } else {
                String hotel_name = fld_add_hotel_name.getText();
                String city = fld_add_city.getText();
                String region = fld_add_loc.getText();
                String address = fld_add_address.getText();
                String email = fld_add_email.getText();
                String number = fld_add_number.getText();
                int star = Integer.parseInt(fld_add_star.getText().toString());
                String[] facility = {fld_add_facilities.getText()};
                String[] hostel = {fld_add_hostel.getText()};

                if (Hotel.add(hotel_name, city, region, address, email, number, star, facility, hostel)) {
                    Helper.showMsg("done");
                    loadHotelModel();
                    loadHotelCombo();

                    fld_add_hotel_name.setText(null);
                    fld_add_city.setText(null);
                    fld_add_loc.setText(null);
                    fld_add_address.setText(null);
                    fld_add_email.setText(null);
                    fld_add_number.setText(null);
                    fld_add_star.setText(null);
                    fld_add_facilities.setText(null);
                    fld_add_hostel.setText(null);

                } else {
                    Helper.showMsg("error");
                }
            }
        });
        cmb_add_room_hotel.addItemListener(e -> {//Eğer ki oda ekleme kısmında otel adı comboboxu değişirse o otele ait pansiyon tipleri comboboxu güncellenir.
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Item selectedHotel = (Item) cmb_add_room_hotel.getSelectedItem();
                int hotelId = selectedHotel.getKey();

                cmb_add_hostel_name_room.removeAllItems();
                for (Hotel obj : Hotel.getList()) {
                    if (obj.getId() == hotelId) {
                        String[] hostelIds = obj.getHostels();
                        for (String hostelId : hostelIds) {
                            int id = Integer.parseInt(hostelId);
                            Hostel hostel = Hostel.getFetch(id);
                            if (hostel != null) {
                                cmb_add_hostel_name_room.addItem(new Item(id, hostel.getHostel_name()));
                            }
                        }
                        break;
                    }
                }
            }
        });

        btn_add_room.addActionListener(e -> {//Otele oda ekleme
            Item hotelItem = (Item) cmb_add_room_hotel.getSelectedItem();
            Item roomItem = (Item) cmb_add_room_name.getSelectedItem();
            Item periodItem = (Item) cmb_add_room_periodname.getSelectedItem();
            Item hostelItem = (Item) cmb_add_hostel_name_room.getSelectedItem();
            String personTypeItem = (String) cmb_add_room_persontype.getSelectedItem();
            if (Helper.isFieldEmpty(fld_add_room_stock) || Helper.isFieldEmpty(fld_add_room_bed) || Helper.isFieldEmpty(fld_add_room_squaremeter) || Helper.isFieldEmpty(fld_add_room_features) || Helper.isFieldEmpty(fld_add_room_price)) {
                Helper.showMsg("fill");
            } else {

                int stock = Integer.parseInt(fld_add_room_stock.getText().toString());
                int bed = Integer.parseInt(fld_add_room_bed.getText().toString());
                int squareMeter = Integer.parseInt(fld_add_room_squaremeter.getText().toString());
                String[] features = {fld_add_room_features.getText()};
                int price = Integer.parseInt(fld_add_room_price.getText().toString());


                if (Room.add(hotelItem.getKey(), roomItem.getKey(), stock, bed, squareMeter, features, periodItem.getKey(), hostelItem.getKey(), personTypeItem, price)) {
                    Helper.showMsg("done");
                    loadRoomModel();
                    fld_add_room_stock.setText(null);
                    fld_add_room_bed.setText(null);
                    fld_add_room_squaremeter.setText(null);
                    fld_add_room_features.setText(null);
                    fld_add_room_price.setText(null);
                } else {
                    Helper.showMsg("error");
                }
            }


        });

        btn_srch.addActionListener(e -> {

            String hotelName = fld_srch_hotelname.getText();
            String city = fld_srch_city.getText();
            String startDate = fld_srch_start_date.getText();
            String endDate = fld_srch_end_date.getText();
            String personType = fld_srch_persontype.getText();

            String srch = Room.searchRooms(hotelName, city, startDate, endDate, personType);
            loadSearchModel(Room.getListBySearch(srch));

        });
    }

    // Yardımcı bir metot sadece sayısal değerlerin girilmesini sağlar bazı bölümler için
    public boolean areAllNumeric(String str) {
        String[] values = str.split(",");
        for (String value : values) {
            if (!value.matches("-?\\d+(\\.\\d+)?")) {
                return false;
            }
        }
        return true;
    }

    public void loadFacilityTypes() {
        for (Facility obj : Facility.getList()) {
            facilityNamesMap.put(obj.getId(), obj.getFacility_name());
        }
    }

    public void loadHostelTypes() {
        for (Hostel obj : Hostel.getList()) {
            hostelNamesMap.put(obj.getId(), obj.getHostel_name());
        }
    }

    public void loadRoomFeaturesMap() {
        for (RoomFeatures obj : RoomFeatures.getList()) {
            roomFeaturesMap.put(obj.getId(), obj.getFeatures());
        }
    }

    public void loadHotelModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hotel.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Hotel obj : Hotel.getList()) {
            i = 0;
            row_hotel[i++] = obj.getId();
            row_hotel[i++] = obj.getHotel_name();
            row_hotel[i++] = obj.getCity();
            row_hotel[i++] = obj.getLoc();
            row_hotel[i++] = obj.getAddress();
            row_hotel[i++] = obj.getEmail();
            row_hotel[i++] = obj.getNumber();
            row_hotel[i++] = obj.getStar();
            // Tesis Özellikleri için döngü
            String[] facilities = obj.getFacilities();
            String facilityName = "";
            for (String id : facilities) {
                int facilityId = Integer.parseInt(id);
                String tesisAdi = facilityNamesMap.get(facilityId);
                if (tesisAdi != null) {
                    facilityName += tesisAdi + ", ";
                }
            }
            if (!facilityName.isEmpty()) {
                facilityName = facilityName.substring(0, facilityName.length() - 2); // Eğer hostelNames boş değilse ve en az iki karakter içeriyorsa, sondaki virgül ve boşluğu temizle

            }
            row_hotel[i++] = facilityName;
            // Pansiyon Tipleri için döngü
            String[] hostels = obj.getHostels();
            String hostelNames = "";
            for (String id : hostels) {
                int pansiyonId = Integer.parseInt(id);
                String hostelName = hostelNamesMap.get(pansiyonId);
                if (hostelName != null) {
                    hostelNames += hostelName + ", ";
                }
            }
            if (!hostelNames.isEmpty()) {
                hostelNames = hostelNames.substring(0, hostelNames.length() - 2);
            }
            row_hotel[i++] = hostelNames;
            mdl_hotel.addRow(row_hotel);
        }
    }

    public void loadPeriodModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_period.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Period obj : Period.getList()) {
            i = 0;
            row_period[i++] = obj.getId();
            row_period[i++] = obj.getPeriod_name();
            row_period[i++] = obj.getStart_date();
            row_period[i++] = obj.getEnd_date();
            mdl_period.addRow(row_period);
        }
    }

    public void loadRoomModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_room.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Room obj : Room.getList()) {
            i = 0;
            row_room[i++] = obj.getId();
            row_room[i++] = obj.getHotel().getHotel_name();
            row_room[i++] = obj.getRoomTypes().getName();
            row_room[i++] = obj.getStock();
            row_room[i++] = obj.getBed();
            row_room[i++] = obj.getSquare_meters();
            //Oda Tipleri İçin Döngü
            String[] roomFeatures = obj.getRoom_features();
            String roomFeatureName = "";
            for (String id : roomFeatures) {
                int roomFeatureId = Integer.parseInt(id);
                String tesisAdi = roomFeaturesMap.get(roomFeatureId);
                if (tesisAdi != null) {
                    roomFeatureName += tesisAdi + ", ";
                }
            }
            if (!roomFeatureName.isEmpty()) {
                roomFeatureName = roomFeatureName.substring(0, roomFeatureName.length() - 2);

            }
            row_room[i++] = roomFeatureName;
            row_room[i++] = obj.getPeriod().getPeriod_name();
            row_room[i++] = obj.getHostel().getHostel_name();
            row_room[i++] = obj.getPerson_type();
            row_room[i++] = obj.getPrice();

            mdl_room.addRow(row_room);
        }

    }

    public void loadSearchModel(ArrayList<Room> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_room.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Room obj : list) {

            i = 0;
            row_room[i++] = obj.getId();
            row_room[i++] = obj.getHotel().getHotel_name();
            row_room[i++] = obj.getRoomTypes().getName();
            row_room[i++] = obj.getStock();
            row_room[i++] = obj.getBed();
            row_room[i++] = obj.getSquare_meters();
            //Oda Tipleri İçin Döngü
            String[] roomFeatures = obj.getRoom_features();
            String roomFeatureName = "";
            for (String id : roomFeatures) {
                int roomFeatureId = Integer.parseInt(id);
                String tesisAdi = roomFeaturesMap.get(roomFeatureId);
                if (tesisAdi != null) {
                    roomFeatureName += tesisAdi + ", ";
                }
            }
            if (!roomFeatureName.isEmpty()) {
                roomFeatureName = roomFeatureName.substring(0, roomFeatureName.length() - 2);

            }
            row_room[i++] = roomFeatureName;
            row_room[i++] = obj.getPeriod().getPeriod_name();
            row_room[i++] = obj.getHostel().getHostel_name();
            row_room[i++] = obj.getPerson_type();
            row_room[i++] = obj.getPrice();

            mdl_room.addRow(row_room);
        }

    }

    public void loadReservationModel() {
        try {
            Statement st = DBConnecter.getInstance().createStatement();
            String sql = "DELETE FROM reservation WHERE room_id IS NULL";//Tabloya null değer gelirse basma
            int affectedRows = st.executeUpdate(sql);
            //System.out.println("Silinen satır sayısı: " + affectedRows); //Kontrol
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        DefaultTableModel clearModel = (DefaultTableModel) tbl_reservation.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Reservation obj : Reservation.getList()) {
            i = 0;
            row_reservation[i++] = obj.getId();
            row_reservation[i++] = obj.getClientName();
            row_reservation[i++] = obj.getClientTC();
            row_reservation[i++] = obj.getClientNumber();
            row_reservation[i++] = obj.getHotel().getHotel_name();
            row_reservation[i++] = obj.getHotel().getCity();
            row_reservation[i++] = obj.getHotel().getLoc();
            row_reservation[i++] = obj.getHotel().getNumber();
            String[] facilities = obj.getHotel().getFacilities();
            String facilityName = "";
            for (String id : facilities) {
                int facilityId = Integer.parseInt(id);
                String tesisAdi = facilityNamesMap.get(facilityId);
                if (tesisAdi != null) {
                    facilityName += tesisAdi + ", ";
                }
            }
            if (!facilityName.isEmpty()) {
                facilityName = facilityName.substring(0, facilityName.length() - 2);
            }
            row_reservation[i++] = facilityName;
            String[] hostels = obj.getHotel().getHostels();
            String hostelNames = "";
            for (String id : hostels) {
                int pansiyonId = Integer.parseInt(id);
                String hostelName = hostelNamesMap.get(pansiyonId);
                if (hostelName != null) {
                    hostelNames += hostelName + ", ";
                }
            }
            if (!hostelNames.isEmpty()) {
                hostelNames = hostelNames.substring(0, hostelNames.length() - 2);
            }
            row_reservation[i++] = hostelNames;
            row_reservation[i++] = obj.getRoomTypes().getName();
            row_reservation[i++] = obj.getStartDate();
            row_reservation[i++] = obj.getEndDate();
            row_reservation[i++] = obj.getNumber_of_person();
            int personNumber = obj.getNumber_of_person();
            int roomPrice = obj.getRoom().getPrice();
            int totalPrice = personNumber * roomPrice;
            row_reservation[i++] = obj.getRoom().getPerson_type();
            String[] roomFeatures = obj.getRoom().getRoom_features();
            String roomFeatureName = "";
            for (String id : roomFeatures) {
                int roomFeatureId = Integer.parseInt(id);
                String tesisAdi = roomFeaturesMap.get(roomFeatureId);
                if (tesisAdi != null) {
                    roomFeatureName += tesisAdi + ", ";
                }
            }
            if (!roomFeatureName.isEmpty()) {
                roomFeatureName = roomFeatureName.substring(0, roomFeatureName.length() - 2);

            }
            row_reservation[i++] = roomFeatureName;
            row_reservation[i++] = obj.getRoom().getBed();
            row_reservation[i++] = totalPrice; //Seçilen kişi tipi sayısından fiyatı hesaplama
            mdl_reservation.addRow(row_reservation);

        }
    }


    public void loadRoomCombo() {
        cmb_add_room_name.removeAllItems();
        for (RoomTypes obj : RoomTypes.getList()) {
            cmb_add_room_name.addItem(new Item(obj.getId(), obj.getName()));
        }
    }

    public void loadPeriodCombo() {
        cmb_add_room_periodname.removeAllItems();
        for (Period obj : Period.getList()) {
            cmb_add_room_periodname.addItem(new Item(obj.getId(), obj.getPeriod_name()));
        }
    }

    public void loadUpdatePeriodCombo() {
        cmb_update_period.removeAllItems();
        for (Period obj : Period.getList()) {
            cmb_update_period.addItem(new Item(obj.getId(), obj.getPeriod_name()));
        }
    }

    public void loadUpdateRoomTypeCombo() {
        cmb_update_room_type.removeAllItems();
        for (RoomTypes obj : RoomTypes.getList()) {
            cmb_update_room_type.addItem(new Item(obj.getId(), obj.getName()));
        }
    }


    public void loadHostelTypeByRoom(int room_id) {
        cmb_update_hostel.removeAllItems(); // ComboBox'ı temizle

        // Seçili oda bilgisini al
        Room room = Room.getFetch(room_id);
        if (room != null) {
            // Odaya ait otel ID'sini al
            int hotel_id = room.getHotel_id();

            // Otelden ait olan hostelleri getir
            Hotel hotel = Hotel.getFetch(hotel_id);
            if (hotel != null) {
                String[] hostelIds = hotel.getHostels();
                for (String hostelId : hostelIds) {
                    int id = Integer.parseInt(hostelId);
                    Hostel hostel = Hostel.getFetch(id);
                    if (hostel != null) {
                        cmb_update_hostel.addItem(new Item(id, hostel.getHostel_name()));
                    }
                }
            }
        }


    }

    public void loadHostelCombo() {//Otellere göre pansiyon tiplerini bastırır.
        cmb_add_hostel_name_room.removeAllItems();
        for (Hotel obj : Hotel.getList()) {
            String[] hostelIds = obj.getHostels();
            for (String hostelId : hostelIds) {
                int id = Integer.parseInt(hostelId);
                Hostel hostel = Hostel.getFetch(id);
                if (hostel != null) {
                    cmb_add_hostel_name_room.addItem(new Item(obj.getId(), hostel.getHostel_name()));
                }
            }
        }
    }


    public void loadHotelCombo() {
        cmb_add_room_hotel.removeAllItems();
        for (Hotel obj : Hotel.getList()) {
            cmb_add_room_hotel.addItem(new Item(obj.getId(), obj.getHotel_name()));
        }
    }


    public static void main(String[] args) {

        Helper.setLayout();
        Acente acente1 = new Acente();
        HotelGUI hotelGUI = new HotelGUI(acente1);
    }
}
