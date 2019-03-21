//GIANNIS ENOMA, 2o, 3160247
//GIANNIS KALOPETRIS, 2o, 3160049
//DIONYSIS KOULOURIS, 2o, 3160069




import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import java.io.*;
import java.util.logging.LoggingPermission;


public class mainApp extends JFrame implements ActionListener, MouseListener {
    private JButton select_txt;
    private JButton new_contract;
    private JButton list_by_type;
    private JButton back_to_contracts;
    private JButton refresh_contract_txt;
    private JButton back_button;
	private JButton new_stats;
	private JButton get_charge;


    private JList<Integer> conJlist;
    private JList serJlist;
    private JTabbedPane tabs = new JTabbedPane();
    private JLabel services_title = new JLabel("Service Type");
    private JLabel contracts_title = new JLabel("Contract ID");
    private JLabel menu = new JLabel("Menu");
    private static DefaultListModel<Integer> listModelcon;
    private static DefaultListModel listModelser;
    private static File selectedFile;
    private static Servicelist services = new Servicelist();
    private static Contractlist contracts = new Contractlist();
    private static long phone_n;
    private static int contract_id;
    private static final String inputinfo = "Welcome. Press one of the buttons on the left." +
            "\nPress \"Load database\" to select the TXT files of the services and the contracts." +
            "\nPress \"Create new contract\" to create a new contract with the selected service." +
            "\nPress \"List contracts\" by type to show all contracts with the given type." +
            "\nPress \"Back to service\" type to return to service types." +
            "\nPress \"Back to all contracts\" to restore the \"Contracts\" list to show all contracts." +
            "\nPress \"Refresh contract stats\" to refresh the stats of the selected contract." +
            "\n Press \"Get final charge\" to get the final charge or the rest (if Card Contract)" +
            "\n------------------------------------------------------------------------------------------------------------------------\n";
    private JTextArea resultArea = new JTextArea(inputinfo,10,50);
    private static boolean phase2entered = false;

	
    public mainApp() {
        setTitle("Mobile Carrier Control Panel");
        drawFrame();
        select_txt.addActionListener(this);
        new_contract.addActionListener(this);
        list_by_type.addActionListener(this);
        back_button.addActionListener(this);
        back_to_contracts.addActionListener(this);
        refresh_contract_txt.addActionListener(this);
        new_stats.addActionListener(this);
        get_charge.addActionListener(this);
        conJlist.addMouseListener(this);
        serJlist.addMouseListener(this);
        setVisible(true);
    }

    public void drawFrame() {
        setBounds(500, 300, 780, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension d = new Dimension(180, 25);
        setResizable(false);


        //create buttons
        select_txt = new JButton("Load database");

        back_to_contracts = new JButton("Back to all contracts");
        back_to_contracts.setEnabled(false);

        new_contract = new JButton("Create new contract");
        new_contract.setEnabled(false);

        list_by_type = new JButton("List contracts by type");
        list_by_type.setEnabled(false);

        refresh_contract_txt = new JButton("Save contracts database");
        refresh_contract_txt.setEnabled(false);

        back_button = new JButton("Back to service types");
		back_button.setEnabled(false);

		new_stats = new JButton("Refresh contract stats");
		new_stats.setEnabled(false);

		get_charge = new JButton("Get final charge");
		get_charge.setEnabled(false);





        //set button size
        select_txt.setPreferredSize(d);
        select_txt.setMaximumSize(d);
        new_contract.setPreferredSize(d);
        new_contract.setMaximumSize(d);
        list_by_type.setPreferredSize(d);
        list_by_type.setMaximumSize(d);
        refresh_contract_txt.setPreferredSize(d);
        refresh_contract_txt.setMaximumSize(d);
		back_button.setPreferredSize(d);
		back_button.setMaximumSize(d);
		back_to_contracts.setPreferredSize(d);
        back_to_contracts.setMaximumSize(d);
        get_charge.setMaximumSize(d);
        get_charge.setPreferredSize(d);
        new_stats.setMaximumSize(d);
        new_stats.setPreferredSize(d);

		//list model creation
        listModelcon = new DefaultListModel<>();
        conJlist = new JList<Integer>(listModelcon);
        conJlist.setSelectedIndex(0);
        listModelser = new DefaultListModel();
        serJlist = new JList(listModelser);
        serJlist.setSelectedIndex(0);



		//container creation
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
		
        JPanel panel1 = new JPanel(); // panel1: buttons
        JPanel panel2 = new JPanel(); //panel2: resultarea
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setAlignmentX(Component.LEFT_ALIGNMENT);
        JScrollPane listScroller1 = new JScrollPane(conJlist);
        JScrollPane listScroller2 = new JScrollPane((serJlist));
        listScroller1.setPreferredSize(new Dimension(300, 200));
        listScroller2.setPreferredSize((new Dimension(300,200)));
        JPanel ServicesPanel = new JPanel();
        ServicesPanel.add(services_title);
        ServicesPanel.add(listScroller2);
        JPanel ContractsPanel = new JPanel();
        ContractsPanel.add(contracts_title);
        ContractsPanel.add(listScroller1);
        ContractsPanel.setLayout(new BoxLayout(ContractsPanel, BoxLayout.Y_AXIS));
        ServicesPanel.setLayout(new BoxLayout(ServicesPanel, BoxLayout.Y_AXIS));
        panel1.add(menu);

        panel1.add(select_txt);
        panel1.add(new_contract);
        panel1.add(list_by_type);
        panel1.add(refresh_contract_txt);
		panel1.add(back_button);
		panel1.add(back_to_contracts);
		panel1.add(get_charge);
		panel1.add(new_stats);
        resultArea.setFont(new Font("Serif", Font.ITALIC, 16));
        resultArea.setForeground(Color.BLUE);
        resultArea.setEditable(false);
        panel2.add(resultArea);
        getContentPane().add(tabs);
        tabs.addTab("Services" ,ServicesPanel);
        tabs.addTab("Contracts" ,ContractsPanel);

		//add panels to ccontainer
        cp.add(panel1, BorderLayout.LINE_START);
        cp.add (panel2, BorderLayout.AFTER_LAST_LINE);
        cp.add(tabs, BorderLayout.CENTER);
        pack();
    }

    public void actionPerformed(ActionEvent e) {

        //choose txt files
        if (e.getSource() == select_txt) {
            boolean file_selected = false, file_loaded = false, continue_input = true;
            int reply;
            int returnVal;


            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "TXT Files", "txt");
            chooser.setFileFilter(filter);


            //select the correct file with confirmation

            reply = JOptionPane.showConfirmDialog(null, "Do you want to load the Services file?", "Message", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.NO_OPTION) {
                continue_input = false;
            } else {
                continue_input = true;
            }

            if (continue_input) {
                JOptionPane.showMessageDialog(null, "Select the TXT file of the services.", "Message", JOptionPane.PLAIN_MESSAGE);
                while (!file_selected) {
                    //message to select the txt
                    returnVal = chooser.showOpenDialog(null);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        selectedFile = chooser.getSelectedFile();
                        //confirmation
                        reply = JOptionPane.showConfirmDialog(null, "Is the file: \"" + selectedFile.getName() + "\"\nthe right one for the services?", "Message", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            //load file and check if it has errors
                            file_loaded = ServiceListFiller(selectedFile);
                            if (file_loaded) {
                                file_selected = true;
                            } else {
                                reply = JOptionPane.showConfirmDialog(null, "File could not be loaded, syntax error. Retry", "Error", JOptionPane.YES_NO_OPTION);
                                if (reply == JOptionPane.NO_OPTION) {
                                    file_selected = true;
                                }
                            }
                        }
                    } else {
                        file_selected = true;
                        continue_input = false;
                    }
                }
            }

            reply = JOptionPane.showConfirmDialog(null, "Do you want to load the Contracts file?", "Message", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.NO_OPTION) {
                continue_input = false;
            } else {
                continue_input = true;
            }

            if (continue_input) {
                file_selected = false;
                file_loaded = false;
                JOptionPane.showMessageDialog(null, "Select the TXT file of the contracts.", "Message", JOptionPane.PLAIN_MESSAGE);
                while (!file_selected) {
                    returnVal = chooser.showOpenDialog(null);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        selectedFile = chooser.getSelectedFile();
                        reply = JOptionPane.showConfirmDialog(null, "Is the file: \"" + selectedFile.getName() + "\"\nthe right one for the contracts?", "Message", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            //load file and check if it has errors
                            file_loaded = ContractListFiller(selectedFile);
                            if (file_loaded) {
                                file_selected = true;
                                refresh_contract_txt.setEnabled(true);
                                list_by_type.setEnabled(true);

                                list_by_type.setEnabled(true);
                                get_charge.setEnabled(true);
                                new_stats.setEnabled(true);
                            }
                            else {
                                reply = JOptionPane.showConfirmDialog(null, "File could not be loaded, syntax error. Retry", "Error", JOptionPane.YES_NO_OPTION);
                                if (reply == JOptionPane.NO_OPTION) file_selected = true;
                            }
                        }
                    } else {
                        file_selected = true;
                    }
                }
            }
        }
        //refresh button
        if (e.getSource() == refresh_contract_txt) {

            contracts.savecontracts(selectedFile.getPath());
        }

        //refresh contract stats
        if (e.getSource() == new_stats) {
            int id = (int)conJlist.getSelectedValue();
            if (contracts.getType(id) == 1) {
                try {
                    double mb = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter MB consumed: "));
                    contracts.setMBConsumed(id, mb);
                }  catch (NullPointerException ignored) {}
            } else {
                try {
                    String[] options = { "Add Minutes To Homes", "Add Minutes To Mobile", "Add SMS"};
                    String  result = (String) JOptionPane.showInputDialog(null, "Select from the drop down below: ", "Consumption", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (result.equals(options[0])) {
                        double dur = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter minutes consumed: "));
                        contracts.setHomeCallDuration(id, dur);
                    }
                    if (result.equals(options[1])) {
                        double dur = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter minutes consumed: "));
                        contracts.setMobileCallDuration(id, dur);
                    }
                    if (result.equals(options[2])) {
                        int sms = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter minutes consumed: "));
                        contracts.setSMSConsumed(id, sms);
                    }
                } catch (NullPointerException ignored) {}
            }
        }

        if (e.getSource() == get_charge) {
            int id = (int)conJlist.getSelectedValue();
            int type = contracts.getType(id);
            String service_name = contracts.getServiceName(id);
            double cut = 1 - services.getCut(service_name);
            double final_charge = services.getFixed(service_name);
            //mobile internet
            if (type == 1) {
                double free_mb = services.getFreeMB(service_name);
                double charge_per_additional_mb = services.getChargePerMB(service_name);
                double mb_consumed = contracts.getMBConsumed(id);
                if (mb_consumed > free_mb) {
                    double dif = mb_consumed - free_mb;
                    final_charge = final_charge + dif*charge_per_additional_mb;
                }
                final_charge = final_charge*0.7;
                final_charge = final_charge*cut;
                JOptionPane.showMessageDialog(null, "The final charge is: " + final_charge, "Message", JOptionPane.PLAIN_MESSAGE);
            } else {
                //contract/card contract
                int free_sms = services.getFreeSMS(service_name);
                double charge_per_sms = services.getChargePerSMS(service_name);
                double free_min = services.getFreeMin(service_name);
                double charge_per_minute = services.getChargePerMin(service_name);
                int sms_consumed = contracts.getSMSConsumed(id);
                double minutes_consumed_mobile = contracts.getMobileCallDuration(id);
                double minutes_consumed_home = contracts.getHomeCallDuration(id);
                double minutes_consumed = minutes_consumed_home + minutes_consumed_mobile;
                double rest = services.getCardRest(service_name);
                if (minutes_consumed > free_min) {
                    double dif = minutes_consumed - free_min;
                    final_charge = final_charge + dif * charge_per_minute;
                }
                if (sms_consumed > free_sms) {
                    int dif = sms_consumed - free_sms;
                    final_charge = final_charge + dif * charge_per_sms;
                }
                if (type == 2) {
                    final_charge = final_charge * 0.8;

                    final_charge = final_charge * cut;
                    JOptionPane.showMessageDialog(null, "The final charge is: " + final_charge, "Message", JOptionPane.PLAIN_MESSAGE);


                } else {
                    final_charge = final_charge * 0.75;
                    final_charge = final_charge * cut;
                    //card contract paying system
                    if (final_charge < rest) {
                        final_charge = minutes_consumed * charge_per_minute + sms_consumed * charge_per_sms;


                        rest = rest - final_charge;
                        JOptionPane.showMessageDialog(null, "Remaining: " + rest, "Message", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        final_charge = final_charge - rest;
                        JOptionPane.showMessageDialog(null, "You have to pay additional: " + final_charge, "Message", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }








        }


        //back to contract list
        if (e.getSource() == back_to_contracts) {

            back_to_contracts.setEnabled(false);
            listModelcon.clear();
            listModelcon = contracts.RestoreList();
            conJlist.setModel(listModelcon);

        }

        //new contract button
        if (e.getSource() == new_contract) {
            //current date
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            String date_final = null;
            String new_service_name = (String)serJlist.getSelectedValue();
            String wop = null, name = null;
            contract_id = 0;
            try {
                contract_id = contracts.getLastID();
            } catch (ArrayIndexOutOfBoundsException e1) {
                contract_id = 14172;
            }
            contract_id++;
            try {
                phone_n = contracts.getLastNumber();
            } catch (ArrayIndexOutOfBoundsException e1) {
                phone_n = 6900456738l;
            }
            phone_n++;
            boolean continue_loop = true;
            int n = 0;

            while(continue_loop) {

                //enter name
                name = (String) JOptionPane.showInputDialog(this, "Enter customer's name:");
                if (name == null) {
                    continue_loop = false;
                }

                //show number
                if (continue_loop) {
                    Object[] yes_no = {"OK", "Cancel"};
                    n = JOptionPane.showOptionDialog(this, "Your number will be: \"" + phone_n + "\"", "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, yes_no, yes_no[0]);
                    if (n == 0) {
                        continue_loop = true;
                    } else {
                        continue_loop = false;
                    }
                }

                //read wop
                if (continue_loop) {
                    Object[] way_of_payment = {"Cash", "Credit Card", "Cancel"};
                    n = JOptionPane.showOptionDialog(this, "Select way of payment.", "Way of payment", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, way_of_payment, way_of_payment[0]);
                    if (n == 0) {
                        wop = "Cash";
                    } else if (n == 1) {
                        wop = "Credit";
                    } else {
                        continue_loop = false;
                    }
                }
                //read date
                if (continue_loop) {
                    date_final = (String) JOptionPane.showInputDialog(this, "Enter the date of registration", dateFormat.format(date));
                    if (date_final.equals(null)) {
                        continue_loop = false;
                    }
                }

                //service name

                if (continue_loop) {
                    Object[] confirmation = {"Confirm", "Type Again", "Cancel"};
                    n = JOptionPane.showOptionDialog(this, "New contract info:\nCustomer's Name: " + name + "\nContract name: " + new_service_name, "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, confirmation, confirmation[0]);
                    if (n == 0) {
                        continue_loop = false;
                        contracts.addContract(new Contract(services.ServiceToType(new_service_name), contract_id, new_service_name, name, Long.toString(phone_n), date_final, wop));
                        listModelcon.addElement(contract_id);
                        if (!list_by_type.isEnabled()) {
                            list_by_type.setEnabled(true);
                            get_charge.setEnabled(true);
                            new_stats.setEnabled(true);
                        }
                    } else if (n == 1){
                        continue_loop = true;
                    } else {
                        continue_loop = false;
                    }
                }
            }
        }
        //list by type button
        if (e.getSource() == list_by_type) {
            String[] options = { "Mobile Internet", "Contract", "Card Contract"};
            String result = (String)JOptionPane.showInputDialog(null, "Select a service type from the drop down below: ", "Select service type", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            int type = 0;
            if (result.equals("Mobile Internet")) {
                type = 1;
            }
            if (result.equals("Contract")) {
                type = 2;
            }
            if (result.equals("Card Contract")) {
                type = 3;
            }

            listModelcon.clear();
            listModelcon = contracts.AddToListByType(type);
            conJlist.setModel(listModelcon);
            back_to_contracts.setEnabled(true);
        }

        //back button
        if (e.getSource() == back_button) {
            listModelser.clear();
            listModelser.addElement("Mobile Internet");
            listModelser.addElement("Contract");
            listModelser.addElement("Card Contract");
            back_button.setEnabled(false);
            new_contract.setEnabled(false);
            phase2entered = false;
            services_title.setText("Service Type");
        }
    }

    public static void main(String[] args) {
        mainApp frame;
        frame = new mainApp();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        frame.setBounds(((width/2)-370), ((height/2)-300), 780, 600);
        }

    public static boolean ServiceListFiller(File file) {
        services.clear();
        listModelser.clear();

        BufferedReader br = null;
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
        String line = null;
        br = new BufferedReader(fileReader);
        int type = 0, free_sms = 0, counter = 0;
        double cut = -0.1;
		Double data_chrg = -0.1, fixed = -0.1, min_chrg = -0.1, sms_chrg = -0.1, free_min = -0.1, rest = -0.1, free_data = -0.1;
        String name = null;
		boolean selected_from_this_line = false;
        try {
            while ((line = br.readLine().trim()) != null) {
                if (line.trim().equals("SERVICE_LIST")){
                    line = br.readLine().trim();
                    while (line.trim().equals("")) {
                        line = br.readLine().trim();
                    }
                    if (line.trim().equals("{")){
                        line = br.readLine().trim();
                        while (line.trim().equals("")) {
                            line = br.readLine().trim();
                        }



                        while ((line.trim()).equals("SERVICE")) {
                            line = br.readLine().trim();
                            while (line.trim().equals("")) {
                                line = br.readLine().trim();
                            }
                            if ((line.trim()).equals("{")){
                                line = br.readLine().trim();
                                while (line.trim().equals("")) {
                                    line = br.readLine().trim();
                                }
                                type = 0;
                                while (!(line.trim()).equals("}")) {
									selected_from_this_line = false;
                                    if (line.toUpperCase().startsWith("TYPE ")) {
                                        type = Integer.parseInt(line.substring(4).trim());
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("CUT ")) {
                                        cut = Double.parseDouble(line.substring(3).trim());
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("NAME ")) {
                                        name = line.substring(4).trim();
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("FIXED ")) {
                                        fixed = Double.parseDouble(line.substring(5).trim());
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("FREE_DATA ")) {
                                        free_data = Double.parseDouble(line.substring(9).trim());
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("DATA_CHARGE ")) {
                                        data_chrg = Double.parseDouble(line.substring(11).trim());
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("FREE_SMS ")) {
                                        free_sms = Integer.parseInt(line.substring(8).trim());
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("SMS_CHARGE ")) {
                                        sms_chrg = Double.parseDouble(line.substring(10).trim());
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("FREE_MINUTES ")) {
                                        free_min = Double.parseDouble(line.substring(12).trim());
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("MINUTE_CHARGE ")) {
                                        min_chrg = Double.parseDouble(line.substring(13).trim());
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("REST ")) {
                                        rest = Double.parseDouble(line.substring(5).trim());
										selected_from_this_line = true;
                                    }
									if (selected_from_this_line == false && !line.isEmpty() ) {
										type = 0;
										
									}
									line = br.readLine().trim();
                                    while (line.trim().equals("")) {
                                        line = br.readLine().trim();
                                    }
                                }

                                if (type == 0) {
                                    JOptionPane.showMessageDialog(null, "A service could not be loaded due to syntax error.", "Message", JOptionPane.PLAIN_MESSAGE);
                                }

                                if (type == 1) {
                                    if (cut == -0.1 || name.equals(null) || fixed == -0.1 || free_data == -0.1 || data_chrg == -0.1) {
                                        JOptionPane.showMessageDialog(null, "A service could not be loaded due to syntax error.", "Message", JOptionPane.PLAIN_MESSAGE);
                                        type = 0;
                                    } else {
                                        services.addService(new MoInternet(type, cut, name, fixed, free_data, data_chrg));
                                    }
                                }
                                if (type == 2) {
                                    if (cut == -0.1 || name.equals(null) || fixed == -0.1 || free_sms == -1 || sms_chrg == -0.1 || free_min == -0.1 || min_chrg == -0.1) {
                                        JOptionPane.showMessageDialog(null, "A service could not be loaded due to syntax error.", "Message", JOptionPane.PLAIN_MESSAGE);
                                        type = 0;
                                    } else {
                                        services.addService(new MoPhone(type, cut, name, fixed, free_sms, sms_chrg, free_min, min_chrg));
                                    }
                                }
                                if (type == 3) {
                                    if (cut == -0.1 || name.equals(null) || fixed == -0.1 || free_sms == -1 || sms_chrg == -0.1 || free_min == -0.1 || min_chrg == -0.1 || rest == -0.1) {
                                        JOptionPane.showMessageDialog(null, "A service could not be loaded due to syntax error.", "Message", JOptionPane.PLAIN_MESSAGE);
                                        type = 0;
                                    } else {
                                        services.addService(new MoPhoneCC(type, cut, name, fixed, free_sms, sms_chrg, free_min, min_chrg, rest));
                                    }
                                }
                                if (type == 1 || type == 2 || type == 3) {
                                    boolean is_entered = false;
                                    for (int i = 0; i < listModelser.size(); i++) {
                                        if (services.TypeToName(type).equals(listModelser.getElementAt(i))) {
                                            is_entered = true;
                                        }
                                    }
                                    if (!is_entered) {
                                        listModelser.addElement(services.TypeToName(type));
                                    }
                                }
                            }
                            line = br.readLine().trim();
                            while (line.trim().equals("")) {
                                line = br.readLine().trim();
                            }
                        }
                    }
                }
            }

            fileReader.close();
            return true;
        } catch (IOException e) {
            return false;
        } catch (NullPointerException e) {
            return true;
        }
    }

    public static boolean ContractListFiller(File file) {

        contracts.clear();
        listModelcon.clear();

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
        String line = null;
        BufferedReader br = new BufferedReader(fileReader);
        int type = 0, id = -1, sms_consumed = 0, counter;
        String name = null, service_name = null, date = null, wop = null, number = null;
        Double data_consumed = -0.01, to_home = -0.01, to_mobile = -0.01;
		boolean selected_from_this_line = false;
        try {
            while ((line = br.readLine().trim()) != null) {
                if ((line).equals("CONTRACT_LIST")){
                    line = br.readLine().trim();
                    while (line.trim().equals("")) {
                        line = br.readLine().trim();
                    }

                    if (line.trim().equals("{")){
                        line = br.readLine().trim();
                        while (line.trim().equals("")) {
                            line = br.readLine().trim();
                        }
                        while ((line.trim()).equals("CONTRACT")) {
                            line = br.readLine().trim();
                            while (line.trim().equals("")) {
                                line = br.readLine().trim();
                            }
                            if ((line.trim()).equals("{")){
                                line = br.readLine().trim();
                                while (line.trim().equals("")) {
                                    line = br.readLine().trim();
                                }
                                counter = 0;
                                type = 0;
								name = null;
								id = -1;
								number = null;
                                while (!(line.trim()).equals("}")) {
									selected_from_this_line = false;
                                    if (line.startsWith("TYPE ")) {
                                        type = Integer.parseInt(line.substring(4).trim());
                                        counter++;
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("NAME ")) {
                                        name = line.substring(4).trim();
                                        counter++;
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("SERVICE_NAME ")) {
                                        service_name = line.substring(12).trim();
                                        counter++;
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("NUMBER ")) {
                                        number = line.substring(6).trim();
                                        counter++;
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("DATE ")) {
                                        date = line.substring(4).trim();
                                        counter++;
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("WOP ")) {
                                        wop = line.substring(3).trim();
                                        counter++;
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("DATA_CONSUMED ")) {
                                        try {
                                            data_consumed = Double.parseDouble(line.substring(13).trim());
                                        } catch (NumberFormatException e) {
                                            data_consumed = 0.0;
                                        }
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("SMS_CONSUMED ")) {
                                        try {
                                            sms_consumed = Integer.parseInt(line.substring(12).trim());
                                        } catch (NumberFormatException e) {
                                            sms_consumed = 0;
                                        }
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("TO_HOME_MINUTES_CONSUMED ")) {
                                        try {
                                            to_home = Double.parseDouble(line.substring(23).trim());
                                        } catch (NumberFormatException e) {
                                            to_home = 0.0;
                                        }
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("TO_MOBILE_MINUTES_CONSUMED ")) {
                                        try {
                                            to_mobile = Double.parseDouble(line.substring(26).trim());
                                        } catch (NumberFormatException e) {
                                            to_mobile = 0.0;
                                        }
										selected_from_this_line = true;
                                    }
                                    if (line.toUpperCase().startsWith("ID ")) {
                                        id = Integer.parseInt(line.substring(2).trim());
										selected_from_this_line = true;
										counter++;

                                    }
									if (selected_from_this_line == false && !line.isEmpty() ) {
										type = 0;
									}
									line = br.readLine().trim();
                                    while (line.trim().equals("")) {
                                        line = br.readLine().trim();
                                    }
                                }

                                if (counter == 7) {
                                    if (type == 0 || id == -1 || name.equals(null) || number.equals(null) || date.equals(null) || wop.equals(null) || service_name.equals(null)) {

                                        if (!name.equals(null)) {
                                            JOptionPane.showMessageDialog(null, "The contract that belongs to: \"" + name + "\" could not be loaded.", "Message", JOptionPane.PLAIN_MESSAGE);
                                        } else if (!number.equals(null)) {
                                            JOptionPane.showMessageDialog(null, "The contract with phone number: \"" + number + "\" could not be loaded.", "Message", JOptionPane.PLAIN_MESSAGE);
                                        } else if (!(id == -1)) {
                                            JOptionPane.showMessageDialog(null, "The contract with ID: \"" + id + "\" could not be loaded.", "Message", JOptionPane.PLAIN_MESSAGE);
                                        } else {
                                            JOptionPane.showMessageDialog(null, "A contract could not be loaded.", "Message", JOptionPane.PLAIN_MESSAGE);
                                        }
                                    } else {
                                        contracts.addContract(new Contract(type, id, service_name, name, number, date, wop));
                                        contracts.setMBConsumed(id, data_consumed);
                                        contracts.setMobileCallDuration(id, to_mobile);
                                        contracts.setSMSConsumed(id, sms_consumed);
                                        contracts.setHomeCallDuration(id, to_home);
                                        listModelcon.addElement(id);
                                    }
                                } else {
                                    if (!name.equals(null)) {
                                        JOptionPane.showMessageDialog(null, "The contract that belongs to: \"" + name + "\" could not be loaded.", "Message", JOptionPane.PLAIN_MESSAGE);
                                    } else if (!number.equals(null)) {
                                        JOptionPane.showMessageDialog(null, "The contract with phone number: \"" + number + "\" could not be loaded.", "Message", JOptionPane.PLAIN_MESSAGE);
                                    } else if (!(id == -1)) {
                                        JOptionPane.showMessageDialog(null, "The contract with ID: \"" + id + "\" could not be loaded.", "Message", JOptionPane.PLAIN_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "A contract could not be loaded.", "Message", JOptionPane.PLAIN_MESSAGE);
                                    }
                                }
                                line = br.readLine().trim();
                                while (line.trim().equals("")) {
                                    line = br.readLine().trim();
                                }
                            }
                        }
                    }
                }
            }
            fileReader.close();
            return true;

        } catch (IOException e) {
            return false;
        } catch (NullPointerException e){
            return true;
        }
    }

    public void mouseClicked(MouseEvent w) {
        Object source = w.getSource();

        if (source == conJlist) {
            int id = conJlist.getSelectedValue();
            if (w.getClickCount() == 2) {
                JOptionPane.showMessageDialog(null, contracts.IDtoString(id), "Contract Info", JOptionPane.PLAIN_MESSAGE);
            }
            if (w.getClickCount() == 1) {

                String AddToResultArea1 = null, AddToResultArea2 = "-";

                int type = contracts.getType(id);
                if (type == 1) {
                    double mb = contracts.getMBConsumed(id);
                    mb = services.getFreeMB(contracts.getServiceName(id)) - mb;
                    if (mb >= 0) {
                        AddToResultArea1 = "Remaining MB: " + mb;
                    } else {
                        AddToResultArea1 = "No remaining free MB!";
                    }

                } else if (type == 2) {
                    int sms = contracts.getSMSConsumed(id);
                    double min = contracts.getMobileCallDuration(id) + contracts.getHomeCallDuration(id);
                    sms = services.getFreeSMS(contracts.getServiceName(id)) - sms;
                    if (sms >= 0) {
                        AddToResultArea1 = ("Remaining SMS: " + sms);
                    } else {
                        AddToResultArea1 = ("No remaining free SMS!");
                    }
                    min = services.getFreeMin(contracts.getServiceName(id)) - min;
                    if (min >= 0) {
                        AddToResultArea2 = ("Remaining minutes: " + min);
                    } else {
                        AddToResultArea2 = ("No remaining minutes!");
                    }
                }
                if (!AddToResultArea2.equals("-")) {
                    resultArea.setText(inputinfo + contracts.IDtoName(id) + "'s Contract Remainings: \n" + AddToResultArea1 + "\n" + AddToResultArea2);
                } else {
                    resultArea.setText(inputinfo + contracts.IDtoName(id) + "'s Contract Remainings: \n" + AddToResultArea1);
                }
                if (type == 3) {
                    resultArea.setText(inputinfo + contracts.IDtoName(id) + "'s Contract is Card Contract and cant have remainings.");
                }






            }
        }
        if (source == serJlist) {
            String title = (String) serJlist.getSelectedValue();
            if (w.getClickCount() == 2) {
                if (!phase2entered) {
                    int list_type = 0;
                    if (title.equals("Mobile Internet")) {
                        list_type = 1;
                    } else if (title.equals("Contract")) {
                        list_type = 2;
                    } else if (title.equals("Card Contract")) {
                        list_type = 3;
                    }

                    listModelser.clear();
                    listModelser = services.ListByType(list_type);
                    serJlist.setModel(listModelser);
                    phase2entered = true;
                    services_title.setText("Service");
					back_button.setEnabled(true);
					new_contract.setEnabled(true);
                } else JOptionPane.showMessageDialog(null, services.NameToString(title), "Service Info", JOptionPane.PLAIN_MESSAGE);
            }



        }
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
