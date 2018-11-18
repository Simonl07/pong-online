package examples;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
class Mouse extends Frame implements MouseMotionListener { 
  
    // Jlabels to display the actions of events of MouseMotionListener 
    static JLabel label1, label2; 
  
    // default constructor 
    Mouse() 
    { 
    } 
  
    // main class 
    public static void main(String[] args) 
    { 
        // create a frame 
        JFrame f = new JFrame("MouseMotionListener"); 
  
        // set the size of the frame 
        f.setSize(600, 400); 
  
        // close the frame when close button is pressed 
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
  
        // create anew panel 
        JPanel p = new JPanel(); 
  
        // set the layout of the panel 
        p.setLayout(new FlowLayout()); 
  
        // initialize the labels 
        label1 = new JLabel("no event  "); 
  
        label2 = new JLabel("no event  "); 
  
        // create an object of mouse class 
        Mouse m = new Mouse(); 
  
        // add mouseListener to the frame 
        f.addMouseMotionListener(m); 
  
        // add labels to the panel 
        p.add(label1); 
        p.add(label2); 
  
        // add panel to the frame 
        f.add(p); 
  
        f.show(); 
    } 
  
    // getX() and getY() functions return the 
    // x and y coordinates of the current 
    // mouse position 
  
    // invoked when mouse button is pressed 
    // and dragged from one point to another 
    // in a  component 
    public void mouseDragged(MouseEvent e) 
    { 
        // update the label to show the point 
        // through which point mouse is dragged 
        label1.setText("mouse is dragged through point "
                       + e.getX() + " " + e.getY()); 
    } 
  
    // invoked when the cursor is moved from 
    // one point to another within the component 
    public void mouseMoved(MouseEvent e) 
    { 
        // update the label to show the point to which the cursor moved 
        label2.setText("mouse is moved to point "
                       + e.getX() + " " + e.getY()); 
    } 
} 
