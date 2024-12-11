/*
 * Created by JFormDesigner on Tue Dec 10 19:39:03 IST 2024
 */

package com.axians.forms;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.axians.dtos.PortScannerInput;
import com.axians.system.ScanPort;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import net.miginfocom.swing.*;

/**
 * @author mocoitlabs
 */
public class PortScanner {
    private Validator validator;
    public PortScanner() {

        initComponents();
        // System.out.println(getClass().getResource("/logo-axians.png"));
        contentPane.pack();
        contentPane.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane.setVisible(true);
        ipErrorLabel.setForeground(Color.red);
        String[] columnNames = {"Port", "Status"};
        Object[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTableHeader header = resultTable.getTableHeader();
        header.setFont(header.getFont().deriveFont(Font.BOLD));

        resultTable.setModel(model);

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                while (model.getRowCount() > 0) {
                    model.removeRow(0);
                }

                ipTextField.setText("");
                fromTextField.setText("");
                toTextField.setText("");
                ipErrorLabel.setText("");
                fromPortErrorLabel.setText("");
                toPortErrorLabel.setText("");
                ipErrorLabel.setVisible(false);
                fromPortErrorLabel.setVisible(false);
                toPortErrorLabel.setVisible(false);

            }
        });

        scanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (model.getRowCount() > 0) {
                    model.removeRow(0);
                }
                ipErrorLabel.setVisible(false);
                ipErrorLabel.setText("");
                fromPortErrorLabel.setText("");
                fromPortErrorLabel.setVisible(false);
                toPortErrorLabel.setVisible(false);
                toPortErrorLabel.setText("");
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                validator = factory.getValidator();
                PortScannerInput portScannerInput = new PortScannerInput();
                portScannerInput.setIp(ipTextField.getText());
                try {
                    portScannerInput.setFromPort(Integer.parseInt(fromTextField.getText()));
                    portScannerInput.setToPort(Integer.parseInt(toTextField.getText()));
                } catch (NumberFormatException numberFormatException) {
                  JOptionPane.showMessageDialog(contentPane, "Invalid input format, Only numbers allowed for ports field!","Error",JOptionPane.ERROR_MESSAGE);
                }


                Set<ConstraintViolation<PortScannerInput>> violations = validator.validate(portScannerInput);

              if(!violations.isEmpty()) {
                  for (ConstraintViolation<PortScannerInput> violation : violations) {
                      // System.out.println(violation.getMessage());
                      String propertyPath = violation.getPropertyPath().toString();
                      if(propertyPath.equals("ip")){
                          ipErrorLabel.setVisible(true);
                          ipErrorLabel.setText(violation.getMessage());
                      } else if (propertyPath.equals("fromPort")) {
                          fromPortErrorLabel.setVisible(true);
                          fromPortErrorLabel.setText(violation.getMessage());
                      } else if (propertyPath.equals("toPort")) {
                          toPortErrorLabel.setVisible(true);
                          toPortErrorLabel.setText(violation.getMessage());
                      }
                  }
              } else {
                  ScanPort scanPort =new ScanPort();
                 if(portScannerInput.getFromPort() == 0) {
                     JOptionPane.showMessageDialog(contentPane, "Field from port is required!","Error",JOptionPane.ERROR_MESSAGE);
                 } else if (portScannerInput.getToPort() == 0 || portScannerInput.getFromPort() > portScannerInput.getToPort()) {
                     JOptionPane.showMessageDialog(contentPane, "Field to port is required and must be greater than from port!","Error",JOptionPane.ERROR_MESSAGE);
                 } else if (portScannerInput.getFromPort() >= portScannerInput.getToPort()) {
                     JOptionPane.showMessageDialog(contentPane, "From port and to port can't be equal!","Error",JOptionPane.ERROR_MESSAGE);
                 } else {
                   List<String[]> results =   scanPort.scanPorts(portScannerInput.getIp(), portScannerInput.getFromPort(), portScannerInput.getToPort());
                     for (String[] rowData : results) {
                         model.addRow(rowData);
                     }
                 }
              }
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - vijeesh
        contentPane = new JFrame();
        IpLabel = new JLabel();
        ipTextField = new JTextField();
        ipErrorLabel = new JLabel();
        fromLabel = new JLabel();
        fromTextField = new JTextField();
        fromPortErrorLabel = new JLabel();
        toLabel = new JLabel();
        toTextField = new JTextField();
        toPortErrorLabel = new JLabel();
        scrollPane1 = new JScrollPane();
        resultTable = new JTable();
        clearButton = new JButton();
        scanButton = new JButton();

        //======== contentPane ========
        {
            contentPane.setMinimumSize(new Dimension(600, 450));
            contentPane.setPreferredSize(new Dimension(600, 450));
            contentPane.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            contentPane.setTitle("AXIANS PORT SCANNER");
            contentPane.setIconImage(null);
            var contentPaneContentPane = contentPane.getContentPane();
            contentPaneContentPane.setLayout(new MigLayout(
                "fillx,hidemode 3",
                // columns
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]"));

            //---- IpLabel ----
            IpLabel.setText("IP Address");
            contentPaneContentPane.add(IpLabel, "cell 1 1");
            contentPaneContentPane.add(ipTextField, "cell 2 1 6 1");

            //---- ipErrorLabel ----
            ipErrorLabel.setVisible(false);
            ipErrorLabel.setForeground(Color.red);
            contentPaneContentPane.add(ipErrorLabel, "cell 2 2,wmax 200");

            //---- fromLabel ----
            fromLabel.setText("From");
            contentPaneContentPane.add(fromLabel, "cell 1 3");
            contentPaneContentPane.add(fromTextField, "cell 2 3 6 1");

            //---- fromPortErrorLabel ----
            fromPortErrorLabel.setVisible(false);
            fromPortErrorLabel.setForeground(Color.red);
            contentPaneContentPane.add(fromPortErrorLabel, "cell 2 4");

            //---- toLabel ----
            toLabel.setText("To");
            contentPaneContentPane.add(toLabel, "cell 1 5");
            contentPaneContentPane.add(toTextField, "cell 2 5 6 1");

            //---- toPortErrorLabel ----
            toPortErrorLabel.setVisible(false);
            toPortErrorLabel.setForeground(Color.red);
            contentPaneContentPane.add(toPortErrorLabel, "cell 2 6");

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(resultTable);
            }
            contentPaneContentPane.add(scrollPane1, "cell 1 7 7 1");

            //---- clearButton ----
            clearButton.setText("Clear");
            contentPaneContentPane.add(clearButton, "cell 7 8");

            //---- scanButton ----
            scanButton.setText("Scan");
            contentPaneContentPane.add(scanButton, "cell 7 8");
            contentPane.pack();
            contentPane.setLocationRelativeTo(contentPane.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - vijeesh
    private JFrame contentPane;
    private JLabel IpLabel;
    private JTextField ipTextField;
    private JLabel ipErrorLabel;
    private JLabel fromLabel;
    private JTextField fromTextField;
    private JLabel fromPortErrorLabel;
    private JLabel toLabel;
    private JTextField toTextField;
    private JLabel toPortErrorLabel;
    private JScrollPane scrollPane1;
    private JTable resultTable;
    private JButton clearButton;
    private JButton scanButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
