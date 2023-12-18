package tixeaseadmin;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.print.PrinterException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;




class TicketBookingSystemGUI {
    private static final String URL = "jdbc:mysql://localhost:3306/tixease_admin";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static JFrame frame;
    private static JPanel adminPanel;
    private static List<Event> events = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowLoginGUI();
            loadEventsFromDatabase();
        });
    }

    private static void createAndShowLoginGUI() {
        frame = new JFrame("Ticket Booking System Admin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 650);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(0, 74, 173));
        ImageIcon logoIcon = new ImageIcon("C:/Users/ym/Downloads/TixEase.png");
        JLabel logoLabel = new JLabel(logoIcon);
        frame.add(logoLabel, BorderLayout.NORTH);

        // Pesan Selamat Datang
        JLabel welcomeLabel = new JLabel("Selamat Datang di Tixease");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setForeground(Color.WHITE);
        frame.add(welcomeLabel, BorderLayout.CENTER);

        JButton loginButton = createStyledButton("Login as Admin");
        loginButton.addActionListener(e -> loginAdmin());

        JButton loginloketButton = createStyledButton("Login as Loket");
        loginloketButton.addActionListener(e -> loginLoket());

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 80));
        loginPanel.setBackground(new Color(0, 74, 173));
        loginPanel.add(loginButton);
        loginPanel.add(loginloketButton);

        frame.add(loginPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(10, 80, 10, 80)
        ));

        return button;
    }


    private static void loginAdmin() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] fields = {"Username:", usernameField, "Password:", passwordField};

        int result = JOptionPane.showConfirmDialog(null, fields, "Admin Login", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);

            if (isValidAdmin(username, password)) {
                JOptionPane.showMessageDialog(null, "Login successful as admin.");
                createAdminPanel();
            } else {
                JOptionPane.showMessageDialog(null, "Login failed. Invalid username or password.");
            }
            usernameField.setText("");
            passwordField.setText("");
        }
    }

    private static boolean isValidAdmin(String username, String password) {
        String query = "SELECT * FROM admins WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    private static void loginLoket() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] fields = {"Username:", usernameField, "Password:", passwordField};

        int result = JOptionPane.showConfirmDialog(null, fields, "Loket Login", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);

            if (isValidLoket(username, password)) {
                JOptionPane.showMessageDialog(null, "Login successful as Loket.");
                createloketpanel();
            } else {
                JOptionPane.showMessageDialog(null, "Login failed. Invalid username or password.");
            }
            usernameField.setText("");
            passwordField.setText("");
        }
    }

    private static boolean isValidLoket(String username, String password) {
        String query = "SELECT * FROM loket WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static void createAdminPanel() {
        adminPanel = new JPanel();
        adminPanel.setBackground(new Color(0, 74, 173));

        ImageIcon logoIcon = new ImageIcon("C:/Users/ym/Downloads/TixEase.png");
        JLabel logoLabel = new JLabel(logoIcon);
        adminPanel.add(logoLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        buttonPanel.setBackground(new Color(0, 74, 173));

        JButton viewEventsButton = createStyledButton("View Events");
        viewEventsButton.addActionListener(e -> showEventList());

        JButton addEventButton = createStyledButton("Add Event");
        addEventButton.addActionListener(e -> addEvent());

        JButton editEventButton = createStyledButton("Edit Event");
        editEventButton.addActionListener(e -> editEvent());

        JButton deleteEventButton = createStyledButton("Delete Event");
        deleteEventButton.addActionListener(e -> deleteEvent());

        JButton createOrderButton = createStyledButton("Create Order");
        createOrderButton.addActionListener(e -> createOrder());

        JButton showLoketUserButton = createStyledButton("View Loket User");
        showLoketUserButton.addActionListener(e -> showLoketUsers());

        JButton viewTransactionReportButton = createStyledButton("View Transaction Report");
        viewTransactionReportButton.addActionListener(e -> showTransactionReport());

        buttonPanel.add(viewEventsButton);
        buttonPanel.add(addEventButton);
        buttonPanel.add(editEventButton);
        buttonPanel.add(deleteEventButton);
        buttonPanel.add(createOrderButton);
        buttonPanel.add(showLoketUserButton);
        buttonPanel.add(viewTransactionReportButton);

        adminPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(adminPanel);
        frame.revalidate();
        frame.repaint();
    }

    private static void createloketpanel() {
        adminPanel = new JPanel();
        adminPanel.setBackground(new Color(0, 74, 173));

        ImageIcon logoIcon = new ImageIcon("C:/Users/ym/Downloads/TixEase.png");
        JLabel logoLabel = new JLabel(logoIcon);
        adminPanel.add(logoLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        buttonPanel.setBackground(new Color(0, 74, 173));

        JButton viewEventsButton = createStyledButton("View Events");
        viewEventsButton.addActionListener(e -> showEventList());

        JButton createOrderButton = createStyledButton("Create Order");
        createOrderButton.addActionListener(e -> createOrder());
        JButton viewTransactionReportButton = createStyledButton("View Transaction Report");
        viewTransactionReportButton.addActionListener(e -> showTransactionReport());

        buttonPanel.add(viewEventsButton);
        buttonPanel.add(createOrderButton);
        buttonPanel.add(viewTransactionReportButton);

        adminPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(adminPanel);
        frame.revalidate();
        frame.repaint();
    }
    private static void showEventList() {
        List<Event> events = getEventsFromDatabase();

        JFrame frame = new JFrame("Daftar Event Tixease");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 650);
        frame.setLayout(new BorderLayout());

        frame.getContentPane().setBackground(new Color(0, 74, 173));

        ImageIcon logoIcon = new ImageIcon("C:/Users/ym/Downloads/TixEase.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon smallLogoIcon = new ImageIcon(logoImage);
        JLabel logoLabel = new JLabel(smallLogoIcon);
        frame.getContentPane().add(logoLabel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Daftar Event Tixease");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        frame.getContentPane().add(titleLabel, BorderLayout.CENTER);

        JPanel eventPanel = new JPanel();
        eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
        eventPanel.setBackground(new Color(0, 74, 173));
        eventPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Event event : events) {
            JPanel panel = createRoundedPanel();
            panel.setLayout(new GridLayout(0, 2, 10, 10));

            GridBagConstraints gbc = new GridBagConstraints();
            addLabelAndField(panel, gbc, "ID:", String.valueOf(event.getId()));
            addLabelAndField(panel, gbc, "Nama:", event.getName());
            addLabelAndField(panel, gbc, "Tanggal:", event.getTanggal().toString());
            addLabelAndField(panel, gbc, "Tempat:", event.getTempat());
            addLabelAndField(panel, gbc, "Harga:", String.valueOf(event.getHarga()));
            addLabelAndField(panel, gbc, "Pukul:", event.getJam().toString());
            addLabelAndField(panel, gbc, "Kuota:", String.valueOf(event.getKuotaTiket()));

            JButton editEventButton = createStyledButton("Edit");
            editEventButton.addActionListener(e -> editEvent());
            panel.add(editEventButton);

            JButton deleteEventButton = createStyledButton("Delete");
            deleteEventButton.addActionListener(e -> deleteEvent());
            panel.add((deleteEventButton));

            eventPanel.add(panel);
        }


        JScrollPane scrollPane = new JScrollPane(eventPanel);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private static JPanel createRoundedPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int width = getWidth();
                int height = getHeight();
                g.setColor(Color.WHITE);
                g.fillRoundRect(10, 10, width - 20, height - 20, 20, 20);
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panel;
    }


    private static void addLabelAndField(JPanel panel, GridBagConstraints gbc, String label, String text) {
        JLabel labelComponent = new JLabel(label);
        JTextField textField = new JTextField(text);
        textField.setEditable(false);
        panel.add(labelComponent);
        panel.add(textField);
    }

    private static List<Event> getEventsFromDatabase() {
        List<Event> events = new ArrayList<>();

        String query = "SELECT * FROM events";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Event event = new Event(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("tanggal"),
                        resultSet.getString("tempat"),
                        resultSet.getDouble("harga"),
                        resultSet.getTime("jam"),
                        resultSet.getInt("kuota")
                );
                events.add(event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }


    private static void addEvent() {
        JTextField nameField = new JTextField();
        JTextField tanggalField = new JTextField();
        JTextField tempatField = new JTextField();
        JTextField hargaField = new JTextField();
        JTextField jamField = new JTextField();
        JTextField kuotaField = new JTextField();

        Object[] fields = {
                "Nama Event:", nameField,
                "Tanggal (yyyy-MM-dd):", tanggalField,
                "Tempat:", tempatField,
                "Harga:", hargaField,
                "Jam (HH:mm):", jamField,
                "Kuota Tiket:", kuotaField
        };

        int result = JOptionPane.showConfirmDialog(null, fields, "Tambah Event", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                Date tanggal = Date.valueOf(tanggalField.getText());
                String tempat = tempatField.getText();
                double harga = Double.parseDouble(hargaField.getText());
                Time jam = Time.valueOf(jamField.getText() + ":00");
                int kuota = Integer.parseInt(kuotaField.getText());
                String insertQuery = "INSERT INTO events (name, tanggal, tempat, harga, jam, kuota) VALUES (?, ?, ?, ?, ?, ?)";
                try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                     PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                    statement.setString(1, name);
                    statement.setDate(2, tanggal);
                    statement.setString(3, tempat);
                    statement.setDouble(4, harga);
                    statement.setTime(5, jam);
                    statement.setInt(6, kuota);

                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Event berhasil ditambahkan ke database.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Gagal menambahkan event ke database.");
                    }
                }
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan. Pastikan input sesuai.");
            }
        }
    }

    private static void editEvent() {
        List<Event> events = getEventsFromDatabase();
        if (events.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tidak ada event untuk diedit.");
            return;
        }

        String[] eventNames = events.stream().map(Event::getName).toArray(String[]::new);
        String selectedEvent = (String) JOptionPane.showInputDialog(null, "Pilih event untuk diedit:", "Edit Event",
                JOptionPane.QUESTION_MESSAGE, null, eventNames, eventNames[0]);

        if (selectedEvent != null) {
            Event selectedEventObj = events.stream().filter(e -> e.getName().equals(selectedEvent)).findFirst().orElse(null);
            if (selectedEventObj != null) {
                JTextField nameField = new JTextField(selectedEventObj.getName());
                JTextField tanggalField = new JTextField(selectedEventObj.getTanggal().toString());
                JTextField tempatField = new JTextField(selectedEventObj.getTempat());
                JTextField hargaField = new JTextField(String.valueOf(selectedEventObj.getHarga()));
                JTextField jamField = new JTextField(selectedEventObj.getJam().toString());
                JTextField kuotaField = new JTextField(String.valueOf(selectedEventObj.getKuotaTiket()));

                Object[] fields = {
                        "Nama Event:", nameField,
                        "Tanggal (yyyy-MM-dd):", tanggalField,
                        "Tempat:", tempatField,
                        "Harga:", hargaField,
                        "Jam (HH:mm):", jamField,
                        "Kuota Tiket:", kuotaField
                };

                int result = JOptionPane.showConfirmDialog(null, fields, "Edit Event", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        String name = nameField.getText();
                        Date tanggal = Date.valueOf(tanggalField.getText());
                        String tempat = tempatField.getText();
                        double harga = Double.parseDouble(hargaField.getText());
                        Time jam = Time.valueOf(jamField.getText());
                        int kuota = Integer.parseInt(kuotaField.getText());
                        String updateQuery = "UPDATE events SET name=?, tanggal=?, tempat=?, harga=?, jam=?, kuota=? WHERE id=?";
                        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                             PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                            statement.setString(1, name);
                            statement.setDate(2, tanggal);
                            statement.setString(3, tempat);
                            statement.setDouble(4, harga);
                            statement.setTime(5, jam);
                            statement.setInt(6, kuota);
                            statement.setInt(7, selectedEventObj.getId());

                            int rowsAffected = statement.executeUpdate();
                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(null, "Event berhasil diubah.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Gagal mengubah event.");
                            }
                        }
                    } catch (SQLException | NumberFormatException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Terjadi kesalahan. Pastikan input sesuai.");
                    }
                }
            }
        }
    }

    private static void deleteEvent() {
        List<Event> events = getEventsFromDatabase();
        if (events.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tidak ada event untuk dihapus.");
            return;
        }

        String[] eventNames = events.stream().map(Event::getName).toArray(String[]::new);
        String selectedEvent = (String) JOptionPane.showInputDialog(null, "Pilih event untuk dihapus:", "Hapus Event",
                JOptionPane.QUESTION_MESSAGE, null, eventNames, eventNames[0]);

        if (selectedEvent != null) {
            Event selectedEventObj = events.stream().filter(e -> e.getName().equals(selectedEvent)).findFirst().orElse(null);
            if (selectedEventObj != null) {
                int confirmResult = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus event ini?",
                        "Konfirmasi Hapus Event", JOptionPane.YES_NO_OPTION);

                if (confirmResult == JOptionPane.YES_OPTION) {
                    String deleteQuery = "DELETE FROM events WHERE id=?";
                    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                         PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                        statement.setInt(1, selectedEventObj.getId());

                        int rowsAffected = statement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Event berhasil dihapus dari database.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Gagal menghapus event dari database.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Terjadi kesalahan dalam menghapus event.");
                    }
                }
            }
        }
    }

    private static void loadEventsFromDatabase() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM events")) {
            while (resultSet.next()) {
                Event event = new Event(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("tanggal"),
                        resultSet.getString("tempat"),
                        resultSet.getDouble("harga"),
                        resultSet.getTime("jam"),
                        resultSet.getInt("kuota")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static List<String> getEventNamesList() {
        return events.stream().map(Event::getName).collect(Collectors.toList());
    }

    private static Event getEventByName(String eventName) {
        return events.stream().filter(event -> event.getName().equals(eventName)).findFirst().orElse(null);
    }

    private static void createOrder() {
        JTextField idPengunjungField = new JTextField();
        JTextField namaPengunjungField = new JTextField();
        JTextField noTlpField = new JTextField();

        List<String> eventNamesList = getEventNamesList();
        String[] eventNamesArray = eventNamesList.toArray(new String[0]);
        JComboBox<String> eventComboBox = new JComboBox<>(eventNamesArray);

        JLabel kuotaLabel = new JLabel("Kuota Tersedia: ");
        eventComboBox.addActionListener(e -> {
            String selectedEvent = (String) eventComboBox.getSelectedItem();
            Event selectedEventObj = getEventByName(selectedEvent);
            if (selectedEventObj != null) {
                kuotaLabel.setText("Kuota Tersedia: " + selectedEventObj.getKuotaTiket());
            }
        });

        JTextField jumlahTiketField = new JTextField();
        JTextField pembayaranField = new JTextField();

        Object[] fields = {
                "ID Pengunjung:", idPengunjungField,
                "Nama Pengunjung:", namaPengunjungField,
                "No. Telepon:", noTlpField,
                "Event:", eventComboBox, kuotaLabel,
                "Jumlah Tiket:", jumlahTiketField,
                "Pembayaran:", pembayaranField
        };

        int result = JOptionPane.showConfirmDialog(null, fields, "Buat Pesanan", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int idPengunjung = Integer.parseInt(idPengunjungField.getText());
                String namaPengunjung = namaPengunjungField.getText();
                String noTlp = noTlpField.getText();
                String selectedEvent = (String) eventComboBox.getSelectedItem();
                int jumlahTiket = Integer.parseInt(jumlahTiketField.getText());
                double pembayaran = Double.parseDouble(pembayaranField.getText());

                Event selectedEventObj = getEventByName(selectedEvent);

                if (selectedEventObj.getKuotaTiket() < jumlahTiket) {
                    JOptionPane.showMessageDialog(null, "Maaf, kuota tiket untuk event ini tidak mencukupi.");
                    return;
                }

                double totalAmount = selectedEventObj.getHarga() * jumlahTiket;
                double changeAmount = pembayaran - totalAmount;
                showReceipt(idPengunjung, namaPengunjung, noTlp, selectedEventObj, jumlahTiket, totalAmount, pembayaran, changeAmount);
                logTransaction(idPengunjung, selectedEventObj.getId(), jumlahTiket, totalAmount, pembayaran, changeAmount, namaPengunjung, noTlp);
                updateTicketQuota(selectedEventObj.getId(), jumlahTiket);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan. Pastikan input sesuai.");
            }
        }
    }


    private static void showReceipt(int idPengunjung, String namaPengunjung, String noTlp, Event event, int jumlahTiket, double totalAmount, double pembayaran, double changeAmount) {
        JFrame receiptFrame = new JFrame("Struk Pembayaran");
        receiptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        receiptFrame.setSize(200, 650);
        receiptFrame.setLayout(new BorderLayout());
        receiptFrame.getContentPane().setBackground(Color.WHITE);

        ImageIcon logoIcon = new ImageIcon("C:/Users/ym/Downloads/TixEase1.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoIcon = new ImageIcon(logoImage);
        JLabel logoLabel = new JLabel(scaledLogoIcon);
        receiptFrame.add(logoLabel, BorderLayout.NORTH);

        JTextArea receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        String receiptText = String.format(
                "-------------------------\n" +
                        "      TIXEASE RECEIPT \n" +
                        "-------------------------\n\n" +
                        "ID Pengunjung: %d\n" +
                        "Nama: %s\n" +
                        "No. Telepon: %s\n\n" +
                        "Event: %s\n" +
                        "Tanggal: %s\n" +
                        "Tempat: %s\n" +
                        "Harga: %.2f\n" +
                        "Jumlah Tiket: %d\n\n" +
                        "Total: %.2f\n" +
                        "Pembayaran: %.2f\n" +
                        "Kembalian: %.2f\n\n" +
                        "-------------------------\n" +
                        "      TERIMA KASIH\n" +
                        "-------------------------",
                idPengunjung, namaPengunjung, noTlp, event.getName(), event.getTanggal(), event.getTempat(),
                event.getHarga(), jumlahTiket, totalAmount, pembayaran, changeAmount);

        receiptArea.setText(receiptText);
        receiptFrame.add(new JScrollPane(receiptArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton printButton = new JButton("Print");
        JButton saveButton = new JButton("Simpan");

        printButton.addActionListener(e -> {
            try {
                receiptArea.print();
            } catch (PrinterException pe) {
                pe.printStackTrace();
                JOptionPane.showMessageDialog(null, "Gagal mencetak struk.");
            }
        });

        saveButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Struk disimpan.");
        });

        buttonPanel.add(printButton);
        buttonPanel.add(saveButton);

        receiptFrame.add(buttonPanel, BorderLayout.SOUTH);

        receiptFrame.setLocationRelativeTo(null);
        receiptFrame.setVisible(true);
    }
    private static List<LoketUser> getLoketUsersFromDatabase() {
        List<LoketUser> loketUsers = new ArrayList<>();

        String query = "SELECT * FROM loket";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                LoketUser loketUser = new LoketUser(
                        resultSet.getInt("id"),
                        resultSet.getString("username")
                );
                loketUsers.add(loketUser);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loketUsers;
    }
    private static void showLoketUsers() {
        List<LoketUser> loketUsers = getLoketUsersFromDatabase();

        JFrame frame = new JFrame("Daftar Loket Users");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 650);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(0, 0, 0));

        ImageIcon logoIcon = new ImageIcon("C:/Users/ym/Downloads/TixEase.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon smallLogoIcon = new ImageIcon(logoImage);
        JLabel logoLabel = new JLabel(smallLogoIcon);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(0, 74, 173));
        headerPanel.add(logoLabel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Daftar Loket Users");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);

        JPanel loketUsersPanel = new JPanel();
        loketUsersPanel.setLayout(new BoxLayout(loketUsersPanel, BoxLayout.Y_AXIS));
        loketUsersPanel.setBackground(new Color(0, 74, 173));
        loketUsersPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (LoketUser loketUser : loketUsers) {
            JPanel panel = createRoundedPanel();
            panel.setLayout(new GridLayout(0, 2, 10, 10));

            GridBagConstraints gbc = new GridBagConstraints();
            addLabelAndField(panel, gbc, "ID:", String.valueOf(loketUser.getId()));
            addLabelAndField(panel, gbc, "Username:", loketUser.getUsername());

            JButton deleteLoketUserButton = createStyledButton("Delete Loket User");
            deleteLoketUserButton.addActionListener(e -> deleteLoketUser(loketUser.getId()));
            panel.add(deleteLoketUserButton);

            loketUsersPanel.add(panel);
        }

        JButton addLoketUserButton = createStyledButton("Add Loket User");
        addLoketUserButton.addActionListener(e -> addLoketUser());
        frame.getContentPane().add(addLoketUserButton, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(loketUsersPanel);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void deleteLoketUser(int loketUserId) {
        int confirmDelete = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete this Loket User?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirmDelete == JOptionPane.YES_OPTION) {
            if (removeLoketUserFromDatabase(loketUserId)) {
                JOptionPane.showMessageDialog(null, "Loket User deleted successfully.");
                showLoketUsers(); // Refresh the Loket Users list after deletion
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete Loket User.");
            }
        }
    }

    private static boolean removeLoketUserFromDatabase(int loketUserId) {
        String query = "DELETE FROM loket WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, loketUserId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    private static void addLoketUser() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] fields = {"Username:", usernameField, "Password:", passwordField};

        int result = JOptionPane.showConfirmDialog(null, fields, "Add Loket User", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);

            if (isValidAdmin(username, password)) {
                JOptionPane.showMessageDialog(null, "Cannot add a Loket user with admin credentials.");
            } else {
                if (insertLoketUserIntoDatabase(username, password)) {
                    JOptionPane.showMessageDialog(null, "Loket user added successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add Loket user.");
                }
            }

            usernameField.setText("");
            passwordField.setText("");
        }
    }

    private static boolean insertLoketUserIntoDatabase(String username, String password) {
        String query = "INSERT INTO loket (username, password) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    private static void showTransactionReport() {
        List<Transaction> transactions = getTransactionsFromDatabase();

        JFrame frame = new JFrame("Daftar Transaksi Tixease");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 650);
        frame.setLayout(new BorderLayout());

        frame.getContentPane().setBackground(new Color(0, 74, 173));
        ImageIcon logoIcon = new ImageIcon("C:/Users/ym/Downloads/TixEase.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon smallLogoIcon = new ImageIcon(logoImage);
        JLabel logoLabel = new JLabel(smallLogoIcon);
        frame.getContentPane().add(logoLabel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Daftar Transaksi Tixease");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        frame.getContentPane().add(titleLabel, BorderLayout.CENTER);

        JPanel transactionPanel = new JPanel();
        transactionPanel.setLayout(new BoxLayout(transactionPanel, BoxLayout.Y_AXIS));
        transactionPanel.setBackground(new Color(0, 74, 173));
        transactionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (Transaction transaction : transactions) {
            JPanel panel = createRoundedPanel();
            panel.setLayout(new GridLayout(0, 2, 10, 10));

            GridBagConstraints gbc = new GridBagConstraints();
            addLabelAndField(panel, gbc, "ID:", String.valueOf(transaction.getId()));
            addLabelAndField(panel, gbc, "ID Pengunjung:", String.valueOf(transaction.getIdPengunjung()));
            addLabelAndField(panel, gbc, "ID Event:", String.valueOf(transaction.getEventId()));
            addLabelAndField(panel, gbc, "Jumlah Tiket:", String.valueOf(transaction.getJumlahTiket()));
            addLabelAndField(panel, gbc, "Total Amount:", String.valueOf(transaction.getTotalAmount()));
            addLabelAndField(panel, gbc, "Payment:", String.valueOf(transaction.getPayment()));
            addLabelAndField(panel, gbc, "Change Amount:", String.valueOf(transaction.getChangeAmount()));
            addLabelAndField(panel, gbc, "Nama Pengunjung:", transaction.getNamaPengunjung());
            addLabelAndField(panel, gbc, "No. Telepon:", transaction.getNoTlp());

            transactionPanel.add(panel);
        }

        String[] columnNames = {"ID", "ID Pengunjung", "ID Event", "Jumlah Tiket", "Total Amount", "Payment", "Change Amount", "Nama Pengunjung", "No. Telepon"};

        Object[][] data = new Object[transactions.size()][columnNames.length];
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            data[i][0] = transaction.getId();
            data[i][1] = transaction.getIdPengunjung();
            data[i][2] = transaction.getEventId();
            data[i][3] = transaction.getJumlahTiket();
            data[i][4] = transaction.getTotalAmount();
            data[i][5] = transaction.getPayment();
            data[i][6] = transaction.getChangeAmount();
            data[i][7] = transaction.getNamaPengunjung();
            data[i][8] = transaction.getNoTlp();
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        double totalTotalAmount = 0.0;
        int totalJumlahTiket = 0;

        for (Transaction transaction : transactions) {
            totalTotalAmount += transaction.getTotalAmount();
            totalJumlahTiket += transaction.getJumlahTiket();
        }

        Object[] totalRow = {"Total", "", "", totalJumlahTiket, totalTotalAmount, "", "", "", ""};
        tableModel.addRow(totalRow);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (row == tableModel.getRowCount() - 1) {
                    comp.setBackground(new Color(0, 74, 173));
                    comp.setForeground(Color.WHITE);
                } else {
                    comp.setBackground(table.getBackground());
                    comp.setForeground(table.getForeground());
                }

                return comp;
            }
        });
    }


    private static List<Transaction> getTransactionsFromDatabase() {
        List<Transaction> transactions = new ArrayList<>();

        String query = "SELECT * FROM transactions";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Transaction transaction = new Transaction(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_pengunjung"),
                        resultSet.getInt("event_id"),
                        resultSet.getInt("jumlah_tiket"),
                        resultSet.getDouble("total_amount"),
                        resultSet.getDouble("payment"),
                        resultSet.getDouble("change_amount"),
                        resultSet.getString("nama_pengunjung"),
                        resultSet.getString("no_tlp")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving transactions from database: " + e.getMessage());
        }

        return transactions;
    }


    private static void updateTicketQuota(int eventId, int jumlahTiket) {
        String updateQuotaQuery = "UPDATE events SET kuota = kuota - ? WHERE id = ?";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(updateQuotaQuery)) {
                statement.setInt(1, jumlahTiket);
                statement.setInt(2, eventId);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    connection.commit();
                    System.out.println("Ticket quota updated successfully.");
                } else {
                    System.out.println("Failed to update ticket quota.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating ticket quota: " + e.getMessage());
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
    }



    private static void logTransaction(int idPengunjung, int eventId, int jumlahTiket,
                                       double totalAmount, double payment, double changeAmount,
                                       String namaPengunjung, String noTlp) {
        String logTransactionQuery = "INSERT INTO transactions (id_pengunjung, event_id, jumlah_tiket, total_amount, " +
                "payment, change_amount, nama_pengunjung, no_tlp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(logTransactionQuery)) {
            statement.setInt(1, idPengunjung);
            statement.setInt(2, eventId);
            statement.setInt(3, jumlahTiket);
            statement.setDouble(4, totalAmount);
            statement.setDouble(5, payment);
            statement.setDouble(6, changeAmount);
            statement.setString(7, namaPengunjung);
            statement.setString(8, noTlp);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Transaction logged successfully.");
            } else {
                System.out.println("Failed to log transaction.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error logging transaction.");
        }
    }
    static class LoketUser {
        private int id;
        private String username;

        public LoketUser(int id, String username) {
            this.id = id;
            this.username = username;
        }

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }
    }

    static class Event {
        private int id;
        private String name;
        private Date tanggal;
        private String tempat;
        private double harga;
        private Time jam;
        private int kuotaTiket;

        // Tambahkan konstruktor
        public Event(int id, String name, Date tanggal, String tempat, double harga, Time jam, int kuotaTiket) {
            this.id = id;
            this.name = name;
            this.tanggal = tanggal;
            this.tempat = tempat;
            this.harga = harga;
            this.jam = jam;
            this.kuotaTiket = kuotaTiket;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Date getTanggal() {
            return tanggal;
        }

        public String getTempat() {
            return tempat;
        }

        public double getHarga() {
            return harga;
        }

        public Time getJam() {
            return jam;
        }

        public int getKuotaTiket() {
            return kuotaTiket;
        }
    }
}