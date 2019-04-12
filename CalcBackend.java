/******
* Pset4 for CSCI E-10b Spring 2019
* Jennifer Vildzius
*
* A class that provides the mathematical logic for a  calculator GUI
*
*/
import java.io.*;
import java.lang.*;
import java.util.*;

public class CalcBackend {
    private int state;
        // 0 = ready for first operand
        // 1 = ready for second operand
        // 2 = constructing operand (left of decimal point)
        // 3 = constructing operand (right of decimal point) 
    private int category;
        // 0 Digit
        // 1 Decimal Point
        // 2 Equals Sign
        // 3 Binary Operator (+ - ÷ ×)
        // 4 Unary Operator (√)
    private int operator;
        // 0 Not yet provided
        // 1 Add
        // 2 Subtract
        // 3 Multiply
        // 4 Divide
    private ArrayList<Character> digits = new ArrayList<Character>( Arrays.asList('0','1','2','3','4','5','6','7','8','9') );
    private ArrayList<Character> binaryOps = new ArrayList<Character>( Arrays.asList('+','-','*','/') );
    private int digit;
    private double displayVal;
    private String displayString;
    private double leftOp;
    private double rightOp;
    private int counter;
    private boolean rightOpExists; // whether or not user has started to declare the next operand

    //constructor
    public CalcBackend() 
    {
        state = 0;
        operator = 0;
        displayVal = 0;
        leftOp = 0;
        rightOpExists = false;
    }

    // Tell the backend that a button has been pushed
    public void feedChar(char c)
    {
        if ( c == 'c')
        {
            state = 0;
            displayVal = 0;
            leftOp = 0;
            rightOp = 0;
            rightOpExists = false;
        }
        else if ( digits.contains(c) )
        {
            category = 0;
            digit = (c - '0');
            if (state == 0) s0c0(digit);
            else if (state == 1) s1c0(digit);
            else if (state == 2) s2c0(digit);
            else if (state == 3) s3c0(digit);
            
        }
        else if ( c == '.' )
        {
            category = 1;
            if (state == 2) s2c1();
        }
        else if ( c == '=' )
        {
            s2c2();
        }
        else if ( binaryOps.contains(c) )
        {
            switch (c)
            {
                case '+':
                    operator = 1;
                    break;
                case '-':
                    operator = 2;
                    break;
                case '*':
                    operator = 3;
                    break;
                case '/':
                    operator = 4;
                    break;
                default:
                    break;
            }
            category = 3;
            if (state == 2 || state == 3) s2c3();
        }
        else if (c == '\u221A')
        {
            category = 4;
            // if state is 1 because another operator was pressed, replace it with square root
            if (state == 1 || state == 2 || state == 3) s2c4();
        }
    }

    // Ask the backend what number should be displayed
    public double getDisplayVal()
    {
        return displayVal;
    }


    /* 
    * Various methods per combination of state and category
    */
    
    // state: ready for 1st operand
    // category: digit
    private void s0c0(int x)
    {
        leftOp = x;
        displayVal = leftOp;
        state = 2;
    }

    // state: ready for 2nd operand
    // category: digit
    private void s1c0(int x)
    {
        rightOp = x;
        rightOpExists = true;
        displayVal = rightOp;
        state = 2;
    }

    // state: constructing operand (left of decimal point)
    // category: digit
    private void s2c0(int x)
    {
        if (!rightOpExists)
        {
            leftOp = (leftOp * 10) + (double) x;
            displayVal = leftOp;
        }
        else
        {
            rightOp = (rightOp * 10) + (double) x;
            displayVal = rightOp;
        }
    }

    // state: constructing operand (right of decimal point)
    // category: digit
    private void s3c0(int x)
    {
        if (!rightOpExists)
        {
            leftOp += ( x / Math.pow(10,counter) );
            counter++;
            displayVal = leftOp; // NOTE testing as of Thur AM
        }
        else
        {
           rightOp += ( x / Math.pow(10,counter) );
            counter++;
        } 
    }

    // constructing operand (left of decimal point)
    // category: decimal
    private void s2c1()
    {
        //left of decimal is finished; now on to the right
        state = 3;
        counter = 1;
    }
    
    // category: binary
    private void s2c3()
    {
        // this method serves the same purpose that s3c3 would

        // ready for second operand
        state = 1;

        // calculates the ongoing "answer"        
        if (rightOpExists)
        {
            switch (operator)
            {
                case 1:
                    leftOp += rightOp;
                    break;
                case 2:
                    leftOp -= rightOp;
                    break;
                case 3:
                    leftOp *= rightOp;
                    break;
                case 4:
                    leftOp /= rightOp;
                    break;
                default:
                    break;
            }
            // NOTE this living inside or outside the if loop has consequences
            // clear out the right side of the equation for potentially more
            rightOp = 0;
            rightOpExists = false;
            displayVal = leftOp;
        }
        
    }

    // category: unary; square root
    private void s2c4()
    {
        // this method serves the same purpose that s3c4 would
        leftOp = Math.sqrt(leftOp);
        // NOTE testing as of Thur AM
        displayVal = leftOp;
        rightOp = 0;
        rightOpExists = false;
    }

    // category: equals is pressed
    private void s2c2()
    {
        // this method serves the same purpose as s3c2 would

        // copied: if equals sign is hit, only show "answer" if operator(s) provided
        // NOTE if ( operator != 0 ) displayVal = leftOp;
        if (rightOpExists)
        {
            switch (operator)
            {
                case 1:
                    leftOp += rightOp;
                    break;
                case 2:
                    leftOp -= rightOp;
                    break;
                case 3:
                    leftOp *= rightOp;
                    break;
                case 4:
                    leftOp /= rightOp;
                    break;
                default:
                    break;
            }
            displayVal = leftOp;
            // allow user to continue calculating off of this answer
            state = 1;
            rightOp = 0;
            rightOpExists = false;
        }
    }
}
