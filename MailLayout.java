/******
* Pset4 for CSCI E-10b Spring 2019
* Jennifer Vildzius
*
* A simple email layout that writes an "email" to a file outbox.txt
*
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class MailLayout {
    public static void main( String [] args ) {
        MyWindow myWindow = new MyWindow();
        myWindow.setVisible( true );
    }
}

class MyWindow extends JFrame {
    // Widgets
    private JLabel labelTo = new JLabel("To: ");
    private JLabel labelCc = new JLabel("Cc: ");
    private JLabel labelBcc = new JLabel("Bcc: ");
    private JLabel labelSubject = new JLabel("Subject: ");
    private JLabel labelFrom = new JLabel("From: ");

    private JTextField txtTo = new JTextField();
    private JTextField txtCc = new JTextField();
    private JTextField txtBcc = new JTextField();
    private JTextField txtSubject = new JTextField();
    private String [] emails = new String[]{"myfirstaddress@gmail.com", "anotheraddress@aol.com", "adifferentaddress@hotmail.com"};
    private JComboBox boxFrom = new JComboBox(emails);
    private JTextArea boxMessage = new JTextArea();
    private JButton btnSend = new JButton( "Send" );

    // Constructor
    public MyWindow () {
        layoutComponents();
        addListeners();
    }

    /* 
    * This section handles the entire layout of the frame
    */
    private void layoutComponents() {
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setLocationRelativeTo( null ); // Centers the window
        this.setTitle( "New Message" );
        this.setSize (450, 350);

        // create nested panels

        // top level panel for the north
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setVisible(true);

        //subpanels for top level north
        JPanel northSubPanelNorth = new JPanel(new BorderLayout());
        northSubPanelNorth.setVisible(true);
        northSubPanelNorth.add(btnSend, BorderLayout.WEST);

        JPanel northSubPanelWest = new JPanel ();
        northSubPanelWest.setLayout(new BoxLayout(northSubPanelWest, BoxLayout.Y_AXIS)); 
        northSubPanelWest.setVisible(true);
        northSubPanelWest.add (labelTo);
        northSubPanelWest.add (labelCc);
        northSubPanelWest.add (labelBcc);
        northSubPanelWest.add (labelSubject);
        northSubPanelWest.add (labelFrom);

        JPanel northSubPanelCenter = new JPanel();
        northSubPanelCenter.setLayout(new BoxLayout(northSubPanelCenter, BoxLayout.Y_AXIS));
        northSubPanelCenter.setVisible(true);
        northSubPanelCenter.add (txtTo);
        northSubPanelCenter.add (txtCc);
        northSubPanelCenter.add (txtBcc);
        northSubPanelCenter.add (txtSubject);
        northSubPanelCenter.add (boxFrom);

        // add subpanels to top lavel north panel
        northPanel.add(northSubPanelNorth, BorderLayout.NORTH);
        northPanel.add(northSubPanelWest, BorderLayout.WEST);
        northPanel.add(northSubPanelCenter, BorderLayout.CENTER);

        // add top level panels to frame
        this.add(northPanel, BorderLayout.NORTH); //was NORTH
        this.add(boxMessage, BorderLayout.CENTER); //was CENTER

        txtTo.setHorizontalAlignment( JTextField.LEFT );
        txtCc.setHorizontalAlignment( JTextField.LEFT );
        txtBcc.setHorizontalAlignment( JTextField.LEFT );
        txtSubject.setHorizontalAlignment( JTextField.LEFT );

        Font f = new Font ("SansSerif", Font.BOLD, 18);
        labelTo.setFont(f); labelCc.setFont(f); labelBcc.setFont(f);
        labelSubject.setFont(f); labelFrom.setFont(f);

        Font f2 = new Font ("SansSerif", Font.PLAIN, 15);
        txtTo.setFont(f2); txtCc.setFont(f2); txtBcc.setFont(f2);
        txtSubject.setFont(f2);

        //txtTo.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
    }

    /* 
    * This section sets up the program's listeners
    */
    private void addListeners() {
        btnSend.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                btnSendClicked( e );
            }
        } );

        // change to a focus listener
        txtSubject.addFocusListener( new FocusListener() {
            public void focusGained( FocusEvent e ) {
                txtSubjectEntered( e );
            }

            public void focusLost( FocusEvent ev) {
                txtSubjectFocusLost( ev );
            }
        } );
    }

    /* 
    * This section handles the action event logic
    */
    public void btnSendClicked( ActionEvent e ) {
        // write message body to outbox file
        try 
        {         
            PrintWriter outbox = new PrintWriter("outbox.txt");
            outbox.println(boxMessage.getText());
            outbox.close(); 
        }
        catch (FileNotFoundException ex) 
	    { 
            System.out.println("Sorry, there was an error 'sending' your e-mail to outbox.");
        }

        // re-set the mail window
        this.setTitle( "New Message" ); 
        txtTo.setText("");
        txtCc.setText("");
        txtBcc.setText("");
        txtSubject.setText("");
        boxMessage.setText("");  
    }

    public void txtSubjectEntered( FocusEvent e ) {
        
        /* TO DO - fix this
        Scanner sc = new Scanner(txtSubject.getText());

        do
        {
            this.setTitle(txtSubject.getText());
        }
        while (sc.hasNext());
        */
    }

    public void txtSubjectFocusLost( FocusEvent ev) {
        // set the title once user is done entering Subject
        if (!(txtSubject.getText()).equals(""))
        {
            this.setTitle(txtSubject.getText());
        }   
    }

}