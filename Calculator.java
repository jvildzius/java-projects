/******
* Pset4 for CSCI E-10b Spring 2019
* Jennifer Vildzius
*
* A calculator GUI
*
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;


public class Calculator {
    public static void main( String [] args ) {
        MyWindow myWindow = new MyWindow();
        myWindow.setVisible( true );
        myWindow.setResizable( false );
    }
}

class MyWindow extends JFrame {
    // set up a backend
    private CalcBackend backend = new CalcBackend();

    // Widgets
    private JTextField screen = new JTextField("0"); //make this uneditable
    private JButton b0 = new JButton("0");
    private JButton b1 = new JButton("1");
    private JButton b2 = new JButton("2");
    private JButton b3 = new JButton("3");
    private JButton b4 = new JButton("4");
    private JButton b5 = new JButton("5");
    private JButton b6 = new JButton("6");
    private JButton b7 = new JButton("7");
    private JButton b8 = new JButton("8");
    private JButton b9 = new JButton("9");
    private JButton bDecimal = new JButton(".");
    private JButton bClear = new JButton("c");
    private JButton bSquareRoot = new JButton("\u221A");
    private JButton bDiv = new JButton("/");
    private JButton bMult = new JButton("*");
    private JButton bSub = new JButton("-");
    private JButton bAdd = new JButton("+");
    private JButton bEquals = new JButton("=");

    private JButton[] buttons = {b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, bDecimal,
        bClear, bSquareRoot, bDiv, bMult, bAdd, bSub, bEquals};

    //variables
    private double displayVal;
    private DecimalFormat df = new DecimalFormat("0.########");
    private String displayString;
    
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
        this.setTitle( "Calculator" );
        this.setLocationRelativeTo( null );
        this.setSize (300, 400);

        // set the middle of the frame in the middle of user's screen
        Point p = new Point();
        p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        double x = p.getX();
        double y = p.getY();
        this.setLocation ( (int)(x - 150), (int)(y - 200) );

        // format widgets
        Font f1 = new Font ("SansSerif", Font.PLAIN, 26);
        Font f2 = new Font ("SansSerif", Font.BOLD, 40);

        // set styling for all buttons
        for (JButton button : buttons) 
        { 
            button.setBackground( Color.LIGHT_GRAY ); 
            button.setFont( f1 );
            button.setSize(25, 25);
        }

        // override styling for non-numeric buttons
        for ( int i = 12; i < 18; i++ )
        {
            buttons[i].setForeground( Color.BLUE );
        }

        screen.setHorizontalAlignment( JTextField.RIGHT );
        screen.setFont( f2 );
        screen.setEditable(false);
        screen.setBackground( Color.YELLOW );
        bClear.setForeground( Color.RED );

        // create panels
        JPanel center = new JPanel( new BorderLayout() );

        JPanel grid = new JPanel();
        grid.setLayout (new GridLayout(4,3, 2, 2) );
        grid.add(bClear); grid.add(bSquareRoot); grid.add(bDiv);
        grid.add(b7); grid.add(b8); grid.add(b9);
        grid.add(b4); grid.add(b5); grid.add(b6);
        grid.add(b1); grid.add(b2); grid.add(b3);

        JPanel center2 = new JPanel( new BorderLayout(2,2) );
        center2.add(b0, BorderLayout.CENTER);
        center2.add(bDecimal, BorderLayout.EAST);

        JPanel east1 = new JPanel( new BorderLayout(2,2) );
        east1.add(bEquals, BorderLayout.EAST);

        JPanel east2 = new JPanel();
        east2.setLayout( new GridLayout(3, 1, 2, 2) );
        east2.add(bMult);
        east2.add(bSub);
        east2.add(bAdd);

        // add (nested) panels to frame
        this.add(screen, BorderLayout.NORTH);
        this.add(east1, BorderLayout.EAST);
        east1.add(east2,BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
        center.add(grid, BorderLayout.CENTER);
        center.add(center2, BorderLayout.SOUTH);
    }

    /* 
    * This section sets up the action event listeners
    */
    class ButtonListener implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
            buttonClicked( e );
        }
    }

    // add a ButtonListener to every button
    private void addListeners() {
        for (JButton button : buttons) 
        { 
            button.addActionListener( new ButtonListener() );
        }
    }


    /* 
    * This section handles the logic
    */
    public void buttonClicked( ActionEvent e ) {
        // pass the single character value of which button was pushed to the backend
        JButton buttonSource = (JButton) e.getSource();
        backend.feedChar( buttonSource.getText().charAt(0) );

        // update the calculator's display as necessary
        displayVal = backend.getDisplayVal();
        displayString = "" + df.format(displayVal);
        screen.setText( displayString );
    }

}