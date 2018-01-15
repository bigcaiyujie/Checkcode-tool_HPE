package com.microfocus.checkcodetool;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InterruptedIOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CheckCode {
    String version;
    String resultFile;
    JFileChooser chooser;
    JButton submit;
    JButton upload;
    JPanel currentPanel;
    JLabel udfLabel;
    JLabel udfValue;
    JLabel udiLabel;
    JLabel udiValue;
    JPanel summaryPanel;
    JLabel typeTitle;
    JLabel points;
    JLabel serverFullLabel;
    JLabel serverFullValue;
    JLabel serverFullPointValue;
    JLabel serverBasicLabel;
    JLabel serverBasicValue;
    JLabel serverBasicPointValue;
    JLabel allWorkstationLabel;
    JLabel allWorkstationValue;
    JLabel storageLabel;
    JLabel storageValue;
    JLabel storagePointValue;
    JLabel allWorkstationPointValue;
    JLabel networkLabel;
    JLabel networkValue;
    JLabel networkPointValue;
    JLabel dockerLabel;
    JLabel dockerValue;
    JLabel dockerPointValue;
    JPanel allPanel;
    JLabel migrationLabel;
    JLabel totalunitsLabel;
    JLabel numbers;
    JPanel outputPanel;
    JLabel mdrLabel;
    JLabel mdrValue;
    JLabel totalLabel;
    JLabel totalValue;
    JLabel totalPointValue;
    JLabel migrationValue;
    JLabel totalunitsValue;
    JLabel vmLabel;
    JLabel vmValue;
    JLabel vmPointValue;
    JLabel compliancyLabel;
    JLabel compliancyValue;
    JFrame jFrame;
    String vailcode;
    JLabel opsbWorkstationLabel;
    JLabel opsbWorkstationValue;
    JLabel opsbWorkstationPointValue;
    JLabel fbWorkstationLabel;
    JLabel fbWorkstationValue;
    JLabel fbWorkstationPointValue;
    JLabel skuValue_Migration;
    JLabel skuValue_Compliancy ;
    int height = 30;
    int width = 200;
    int borderX = 120;
    int padding = 380;
    int borderY = 80;
    int initX = 20;
    int initY = 20;
    String data[][] = {
            {"", "", "", "", "", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", "", "", "", "", ""},
    };

    public CheckCode() {
        jFrame = new JFrame();
        jFrame.setSize(685, 100);
        jFrame.setTitle("License Report Validation ToolKit");
        jFrame.getContentPane().setLayout(null);
        jFrame.setResizable(false);
        JPanel inputPanel = createJPanel(0, 0, 680, 80, Color.white, null);

        JLabel checkLabel = createJLabel("Import CSV File :", initX, initY, width, height);
        inputPanel.add(checkLabel);

        upload = createJButton("Import .CSV", initX + borderX, initY, width, height, true);
        inputPanel.add(upload);


        JButton submit = createJButton("Validate", initX + padding, initY, width, height, true);
        inputPanel.add(submit);

         /*
        onClickListener
         */
        upload.addActionListener(new ImportListener());
        submit.addActionListener(new SubmitListener());

        jFrame.add(inputPanel);

        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        CheckCode checkCode = new CheckCode();
    }

    private JButton createJButton(String title, int x, int y, int width, int height, boolean enable) {
        JButton button = new JButton(title);
        button.setBounds(x, y, width, height);
        button.setEnabled(enable);
        return button;
    }


    private JLabel createJLabel(String title, int x, int y, int width, int height) {
        JLabel label = new JLabel(title);
        label.setBounds(x, y, width, height);
        return label;
    }

    private JPanel createJPanel(int x, int y, int width, int height, Color color, LayoutManager layout) {
        JPanel jPanel = new JPanel();
        jPanel.setBounds(x, y, width, height);
        jPanel.setBackground(color);
        jPanel.setLayout(layout);
        return jPanel;
    }


    class ImportListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "csv", "csv");
            chooser = new JFileChooser();
            chooser.setFileFilter(filter);
            chooser.setCurrentDirectory(new File("."));
            chooser.setDialogTitle("select file");
            chooser.setAcceptAllFileFilterUsed(true);
            chooser.setFileSelectionMode(0);
            int state = chooser.showOpenDialog(null);
            File selectedFile;
            if (state == 1) {
                return;
            } else {
                selectedFile = chooser.getSelectedFile();
                upload.setText(selectedFile.getName());
            }
            BufferedReader br = null;
            StringBuffer result = new StringBuffer();
            vailcode = new String();
            try {
                br = new BufferedReader(new FileReader(selectedFile));
                String line = "";
                for (int i = 0; i < 7; i++) {
                    line = br.readLine();
                    data[i] = line.split(",");
                    if (i == 0 || i == 6) {
                        if (i == 6) {
                            vailcode = (line.split(","))[1];
                        }
                        if (i == 0) {
                            version = line.split(",")[0];
                            version = version.substring(6, version.length());
                        }
                    } else {
                        result.append(line);
                        result.append("\r\n");
                    }
                }
            } catch (Exception x) {
                x.printStackTrace();
            }finally {
                try{
                    br.close();
                }catch (Exception o){
                    o.printStackTrace();
                }

            }
            resultFile = result.toString();
            resultFile = new GetMD5().getMD5(resultFile);
            System.out.println(resultFile);
        }
    }

    class SubmitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            jFrame.setSize(685, 100);
            if(outputPanel!=null){
                outputPanel.setVisible(false);
            }
            if (resultFile.compareTo(vailcode) == 0) {
                String versionCompare = "";
                if(version.indexOf("C")!=-1){
                    versionCompare = version.substring(0,version.indexOf("C")-1);
                }else{
                    versionCompare = version;
                }
                if (Double.valueOf(versionCompare).compareTo(Double.valueOf("10.40")) >= 0) {
                    moreThan();
                    jFrame.add(outputPanel, null);
                    serverFullValue.setText(data[2][5]);
                    serverFullPointValue.setText(data[2][5]);
                    serverBasicValue.setText(data[2][6]);
                    serverBasicPointValue.setText(getPointMore(Integer.valueOf(data[2][6]), 0.2) + "");
                    fbWorkstationValue.setText(data[2][7]);
                    fbWorkstationPointValue.setText(getPointMore(Integer.valueOf(data[2][7]), 0.1) + "");
                    opsbWorkstationValue.setText(data[2][8]);
                    opsbWorkstationPointValue.setText(getPointMore(Integer.valueOf(data[2][8]), 0.2) + "");
                    networkValue.setText(data[2][9]);
                    networkPointValue.setText(getPointMore(Integer.valueOf(data[2][9]), 0.1) + "");
                    storageValue.setText(data[2][10]);
                    storagePointValue.setText(getPointMore(Integer.valueOf(data[2][10]), 0.1) + "");
                    dockerValue.setText(data[2][11]);
                    dockerPointValue.setText(getPointMore(Integer.valueOf(data[2][11]), 0.1) + "");
                    totalValue.setText(Integer.valueOf(data[2][5]) + Integer.valueOf(data[2][6]) + Integer.valueOf(data[2][7]) + Integer.valueOf(data[2][8]) + Integer.valueOf(data[2][9]) + Integer.valueOf(data[2][10]) + Integer.valueOf(data[2][11]) + "");
                    totalPointValue.setText(data[5][1]);
                    migrationValue.setText(data[1][1]);
                    compliancyValue.setText(data[5][1]);
                    totalunitsValue.setText((Double.valueOf(data[1][1])-Double.valueOf(data[5][1]))+"");
                    outputPanel.setBorder(BorderFactory.createTitledBorder("Current Version UCMDB " + version));
                } else {
                    lessThan();
                    jFrame.add(outputPanel, null);
                    udfValue.setText(data[1][2]);
                    udiValue.setText(data[1][3]);
                    mdrValue.setText(data[1][4]);
                    serverFullValue.setText(data[2][5]);
                    serverBasicValue.setText(data[2][6]);
                    serverBasicPointValue.setText(getPoint(Integer.valueOf(data[2][6]), 0.1) + "");
                    allWorkstationValue.setText(data[2][8]);
                    vmValue.setText(data[2][7]);
                    vmPointValue.setText(getPoint(Integer.valueOf(data[2][7]), 0.9) + "");
                    networkValue.setText(data[2][9]);
                    networkPointValue.setText(getPoint(Integer.valueOf(data[2][9]), 0.1) + "");
                    storageValue.setText(data[2][10]);
                    storagePointValue.setText(getPoint(Integer.valueOf(data[2][10]), 0.1) + "");
                    dockerValue.setText(data[2][11]);
                    dockerPointValue.setText(getPoint(Integer.valueOf(data[2][11]), 0.1) + "");
                    totalValue.setText(Integer.valueOf(data[2][5]) + Integer.valueOf(data[2][6]) + Integer.valueOf(data[2][7]) + Integer.valueOf(data[2][8]) + Integer.valueOf(data[2][9]) + Integer.valueOf(data[2][10]) + Integer.valueOf(data[2][11]) + "");
                    totalPointValue.setText(data[4][1]);
                    migrationValue.setText(data[3][1]);
                    compliancyValue.setText(data[4][1]);
                    totalunitsValue.setText(data[5][1]);
                    if(Integer.valueOf(data[1][2])==0&&Integer.valueOf(data[1][3])==0){
                        serverBasicPointValue.setText("0");
                        vmPointValue.setText("0");
                        networkPointValue.setText("0");
                        storagePointValue.setText("0");
                        dockerPointValue.setText("0");
                        totalPointValue.setText("0");
                        compliancyValue.setText("0");
                    }
                    outputPanel.setBorder(BorderFactory.createTitledBorder("Current Version UCMDB " + version));
                }
                JOptionPane.showMessageDialog(null, "Vaild Code !");

            } else {
                JOptionPane.showMessageDialog(null, "Invaild Code !");
            }
        }
    }


    public String getResultFile() {
        return resultFile;
    }

    public void setResultFile(String resultFile) {
        this.resultFile = resultFile;
    }

    public void lessThan() {
        if (outputPanel != null) {
            jFrame.remove(outputPanel);
        }
        jFrame.setSize(680, 590);
        outputPanel = createJPanel(0, 80, 680, 490, Color.white, null);

        initX = 20;
        initY = 20;
        borderX = 150;
        borderY = 60;
        padding = 320;
        int padding1 = 260;
        height = 30;
        width = 200;
        int borderX1 = 260;

        /*
        Current license status
         */

        currentPanel = createJPanel(20, initY, 640, 60, Color.white, null);
        currentPanel.setBorder(BorderFactory.createTitledBorder("Current License Capacity :"));

        udfLabel = createJLabel("UDF  Capacity :", initX, initY, width, height);
        currentPanel.add(udfLabel);

        udfValue = createJLabel("0", initX + 100, initY, width, height);
        currentPanel.add(udfValue);

        udiLabel = createJLabel("UDI Capacity :", initX + borderX1, initY, width, height);
        currentPanel.add(udiLabel);

        udiValue = createJLabel("0", initX + padding1 + 100, initY, width, height);
        currentPanel.add(udiValue);

        mdrLabel = createJLabel("MDR  Capacity :", initX + borderX + padding, initY, width, height);
        currentPanel.add(mdrLabel);

        mdrValue = createJLabel("0", initX + borderX + padding + 100, initY, width, height);
        currentPanel.add(mdrValue);

        outputPanel.add(currentPanel);

        /*
        After upgrade
         */

        summaryPanel = createJPanel(20, initY + borderY, 640, 380, Color.white, null);
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Migration Unit Conversion :"));


        int widthF = 250;
        int borderY1 = 30;
        initY = 50;

        typeTitle = createJLabel("License Types", initX, 20, widthF, height);
        summaryPanel.add(typeTitle);

        numbers = createJLabel("CI Counts", initX + borderX1, 20, widthF, height);
        summaryPanel.add(numbers);

        points = createJLabel("Compliancy Units ", initX + borderX + padding, 20, widthF, height);
        summaryPanel.add(points);

        /*
        serverFull
         */

        serverFullLabel = createJLabel("Servers with Advanced License :", initX, initY, widthF, height);
        summaryPanel.add(serverFullLabel);

        serverFullValue = createJLabel("0", initX + borderX1, initY, width, height);
        summaryPanel.add(serverFullValue);

        serverFullPointValue = createJLabel("0", initX + padding + borderX, initY, width, height);
        summaryPanel.add(serverFullPointValue);

        /*
        server Basic
         */

        serverBasicLabel = createJLabel("Servers with Basic License :", initX, initY + borderY1, widthF, height);
        summaryPanel.add(serverBasicLabel);

        serverBasicValue = createJLabel("0", initX + borderX1, initY + borderY1, width, height);
        summaryPanel.add(serverBasicValue);

        serverBasicPointValue = createJLabel("0", initX + padding + borderX, initY + borderY1, width, height);
        summaryPanel.add(serverBasicPointValue);

        /*
        Allworkstation
         */

        allWorkstationLabel = createJLabel("Workstations :", initX, initY + borderY1 * 2, widthF, height);
        summaryPanel.add(allWorkstationLabel);

        allWorkstationValue = createJLabel("0", initX + borderX1, initY + borderY1 * 2, width, height);
        summaryPanel.add(allWorkstationValue);


        allWorkstationPointValue = createJLabel("0", initX + padding + borderX, initY + borderY1 * 2, width, height);
        summaryPanel.add(allWorkstationPointValue);

         /*
        VM
         */

        vmLabel = createJLabel("VM Hosts :", initX, initY + borderY1 * 3, widthF, height);
        summaryPanel.add(vmLabel);

        vmValue = createJLabel("0", initX + borderX1, initY + borderY1 * 3, width, height);
        summaryPanel.add(vmValue);


        vmPointValue = createJLabel("0", initX + padding + borderX, initY + borderY1 * 3, width, height);
        summaryPanel.add(vmPointValue);

        /*
        Network
         */

        networkLabel = createJLabel("Network Devices :", initX, initY + borderY1 * 4, widthF, height);
        summaryPanel.add(networkLabel);

        networkValue = createJLabel("0", initX + borderX1, initY + borderY1 * 4, width, height);
        summaryPanel.add(networkValue);


        networkPointValue = createJLabel("0", initX + padding + borderX, initY + borderY1 * 4, width, height);
        summaryPanel.add(networkPointValue);

        /*
        Storage
         */

        storageLabel = createJLabel("Storage Devices :", initX, initY + borderY1 * 5, widthF, height);
        summaryPanel.add(storageLabel);

        storageValue = createJLabel("0", initX + borderX1, initY + borderY1 * 5, width, height);
        summaryPanel.add(storageValue);

        storagePointValue = createJLabel("0", initX + padding + borderX, initY + borderY1 * 5, width, height);
        summaryPanel.add(storagePointValue);

        /*
        Docker
         */

        dockerLabel = createJLabel("Containers :", initX, initY + borderY1 * 6, widthF, height);
        summaryPanel.add(dockerLabel);

        dockerValue = createJLabel("0", initX + borderX1, initY + borderY1 * 6, width, height);
        summaryPanel.add(dockerValue);

        dockerPointValue = createJLabel("0", initX + padding + borderX, initY + borderY1 * 6, width, height);
        summaryPanel.add(dockerPointValue);

         /*
        Docker
         */

        totalLabel = createJLabel("Total CIs :", initX, initY + borderY1 * 7, widthF, height);
        summaryPanel.add(totalLabel);

        totalValue = createJLabel("0", initX + borderX1, initY + borderY1 * 7, width, height);
        summaryPanel.add(totalValue);

        totalPointValue = createJLabel("0", initX + padding + borderX, initY + borderY1 * 7, width, height);
        summaryPanel.add(totalPointValue);

        /*
        ALL
         */

        allPanel = new JPanel();
        allPanel.setBorder(BorderFactory.createTitledBorder("ALL New License Units :"));
        allPanel.setBackground(Color.white);
        allPanel.setBounds(5, initY + borderY1 * 8 + 10, 630, 70);
        allPanel.setLayout(null);

        migrationLabel = createJLabel("Migration Units :", initX, 20, width, height);
        allPanel.add(migrationLabel);

        migrationValue = createJLabel("0", initX + 120, 20, width, height);
        allPanel.add(migrationValue);

        skuValue_Migration = createJLabel("(SKU:SWAA029P9)", initX, 40, width, height);
        allPanel.add(skuValue_Migration);

        compliancyLabel = createJLabel("Compliancy Units :", initX + borderX1 - 10, 20, width, height);
        allPanel.add(compliancyLabel);

        compliancyValue = createJLabel("0", initX + borderX1 + 120, 20, width, height);
        allPanel.add(compliancyValue);

        skuValue_Compliancy = createJLabel("(SKU:SWAA030P9)", initX + borderX1 - 10, 40, width, height);
        allPanel.add(skuValue_Compliancy);

        totalunitsLabel = createJLabel("Total Units :", initX + borderX + padding - 10, 20, width, height);
        allPanel.add(totalunitsLabel);

        totalunitsValue = createJLabel("0", initX + padding + borderX + 80, 20, width, height);
        allPanel.add(totalunitsValue);


        summaryPanel.add(allPanel);

        outputPanel.add(summaryPanel);
    }

    public void moreThan() {
        if (outputPanel != null) {
            jFrame.remove(outputPanel);
        }
        jFrame.setSize(680, 520);
        outputPanel = createJPanel(0, 80, 680, 435, Color.white, null);
        initX = 20;
        initY = 20;
        int borderX1 = 260;
        padding = 320;

        height = 30;
        width = 200;

        outputPanel.setBorder(BorderFactory.createTitledBorder("Current Version UCMDB ----"));

        /*
        After upgrade
         */

        summaryPanel = createJPanel(20, initY, 640, 380, Color.white, null);
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Migration Unit Conversion :"));


        int widthF = 250;
        int borderY1 = 30;
        initY = 50;

        typeTitle = createJLabel("License Types", initX, 20, widthF, height);
        summaryPanel.add(typeTitle);

        numbers = createJLabel("CI Counts", initX + borderX1, 20, widthF, height);
        summaryPanel.add(numbers);

        points = createJLabel("Compliancy Units ", initX + borderX + padding, 20, widthF, height);
        summaryPanel.add(points);

        /*
        serverFull
         */

        serverFullLabel = createJLabel("Servers with Advanced License :", initX, initY, widthF, height);
        summaryPanel.add(serverFullLabel);

        serverFullValue = createJLabel("0", initX + borderX1, initY, width, height);
        summaryPanel.add(serverFullValue);

        serverFullPointValue = createJLabel("0.0", initX + padding + borderX, initY, width, height);
        serverFullPointValue.setToolTipText("10/10  unit for every full Server discovered");
        summaryPanel.add(serverFullPointValue);

        /*
        server Basic
         */

        serverBasicLabel = createJLabel("Servers with Basic&opsb License :", initX, initY + borderY1, widthF, height);
        summaryPanel.add(serverBasicLabel);

        serverBasicValue = createJLabel("0", initX + borderX1, initY + borderY1, width, height);
        summaryPanel.add(serverBasicValue);

        serverBasicPointValue = createJLabel("0.0", initX + padding + borderX, initY + borderY1, width, height);
        serverBasicPointValue.setToolTipText("2/10  unit for every Basic&Opsb Server discovered");
        summaryPanel.add(serverBasicPointValue);

        /*
       workstationOpsb
         */

        opsbWorkstationLabel = createJLabel("Workstations with Opsb License :", initX, initY + borderY1 * 2, widthF, height);
        summaryPanel.add(opsbWorkstationLabel);

        opsbWorkstationValue = createJLabel("0", initX + borderX1, initY + borderY1 * 2, width, height);
        summaryPanel.add(opsbWorkstationValue);


        opsbWorkstationPointValue = createJLabel("0.0", initX + padding + borderX, initY + borderY1 * 2, width, height);
        opsbWorkstationPointValue.setToolTipText("2/10  unit for every Opsb Workstation discovered");
        summaryPanel.add(opsbWorkstationPointValue);

         /*
        workstationOther
         */

        fbWorkstationLabel = createJLabel("Workstations with Full&basic License :", initX, initY + borderY1 * 3, widthF, height);
        summaryPanel.add(fbWorkstationLabel);

        fbWorkstationValue = createJLabel("0", initX + borderX1, initY + borderY1 * 3, width, height);
        summaryPanel.add(fbWorkstationValue);

        fbWorkstationPointValue = createJLabel("0.0", initX + padding + borderX, initY + borderY1 * 3, width, height);
        fbWorkstationPointValue.setToolTipText("1/10  unit  for every full&basic Workstation discovered");
        summaryPanel.add(fbWorkstationPointValue);

          /*
        Network
         */
        networkLabel = createJLabel("Network Devices :", initX, initY + borderY1 * 4, widthF, height);
        summaryPanel.add(networkLabel);

        networkValue = createJLabel("0", initX + borderX1, initY + borderY1 * 4, width, height);
        summaryPanel.add(networkValue);


        networkPointValue = createJLabel("0.0", initX + padding + borderX, initY + borderY1 * 4, width, height);
        networkPointValue.setToolTipText("1/10  unit for every network device discovered");
        summaryPanel.add(networkPointValue);

        /*
        Storage
         */

        storageLabel = createJLabel("Storage Devices :", initX, initY + borderY1 * 5, widthF, height);
        summaryPanel.add(storageLabel);

        storageValue = createJLabel("0", initX + borderX1, initY + borderY1 * 5, width, height);
        summaryPanel.add(storageValue);

        storagePointValue = createJLabel("0.0", initX + padding + borderX, initY + borderY1 * 5, width, height);
        storagePointValue.setToolTipText("1/10  unit for every storage device discovered");
        summaryPanel.add(storagePointValue);

        /*
        Docker
         */

        dockerLabel = createJLabel("Containers :", initX, initY + borderY1 * 6, widthF, height);
        summaryPanel.add(dockerLabel);

        dockerValue = createJLabel("0", initX + borderX1, initY + borderY1 * 6, width, height);
        summaryPanel.add(dockerValue);

        dockerPointValue = createJLabel("0.0", initX + padding + borderX, initY + borderY1 * 6, width, height);
        dockerPointValue.setToolTipText("1/10  unit for every container device discovered");
        summaryPanel.add(dockerPointValue);

         /*
        Docker
         */

        totalLabel = createJLabel("Total CIs :", initX, initY + borderY1 * 7, widthF, height);
        summaryPanel.add(totalLabel);

        totalValue = createJLabel("0", initX + borderX1, initY + borderY1 * 7, width, height);
        summaryPanel.add(totalValue);

        totalPointValue = createJLabel("0", initX + padding + borderX, initY + borderY1 * 7, width, height);
        summaryPanel.add(totalPointValue);

        /*
        ALL
         */

        allPanel = new JPanel();
        allPanel.setBorder(BorderFactory.createTitledBorder("ALL New License Units :"));
        allPanel.setBackground(Color.white);
        allPanel.setBounds(5, initY + borderY1 * 8 + 10, 630, 70);
        allPanel.setLayout(null);

        migrationLabel = createJLabel("Unit Capacity :", initX, 20, width, height);
        allPanel.add(migrationLabel);

        migrationValue = createJLabel("0", initX + 120, 20, width, height);
        allPanel.add(migrationValue);

        compliancyLabel = createJLabel("Used Units :", initX + borderX1 - 10, 20, width, height);
        allPanel.add(compliancyLabel);

        compliancyValue = createJLabel("0", initX + borderX1 + 120, 20, width, height);
        allPanel.add(compliancyValue);

        totalunitsLabel = createJLabel("Remain Units :", initX + borderX + padding - 10, 20, width, height);
        allPanel.add(totalunitsLabel);

        totalunitsValue = createJLabel("0", initX + padding + borderX + 80, 20, width, height);
        allPanel.add(totalunitsValue);

        summaryPanel.add(allPanel);

        outputPanel.add(summaryPanel);
    }

    public int getPoint(int number, double rate) {
        BigDecimal tempDecimal = new BigDecimal(number);
        double basicServerPoint = tempDecimal.multiply(new BigDecimal(rate)).setScale(1, RoundingMode.HALF_UP).doubleValue();
        return (int) Math.ceil(basicServerPoint);
    }

    public double getPointMore(int number, double rate) {
        BigDecimal tempDecimal = new BigDecimal(number);
        double basicServerPoint = tempDecimal.multiply(new BigDecimal(rate)).setScale(1, RoundingMode.HALF_UP).doubleValue();
        return basicServerPoint;
    }
}