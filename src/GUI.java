	import java.awt.*;
	import java.awt.event.*;
	import java.io.*;
	import javax.swing.*;

public class GUI  extends JFrame {
  
  //Note: Typically the main method will be in a
  //separate class. As this is a simple one class
  //example it's all in the one class.

 	    public GUI() {
	    super("File Chooser Test Frame");
	    setSize(350, 200);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);

	    Container c = getContentPane();
	    c.setLayout(new FlowLayout());
	    
	    JButton openButton = new JButton("Open");
	    //JButton saveButton = new JButton("Save");
	    //JButton dirButton = new JButton("Pick Dir");
	    final JLabel statusbar = 
	    new JLabel("Output of your selection will go here");

	    // Create a file chooser that opens up as an Open dialog
	    openButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        JFileChooser chooser = new JFileChooser();
	        chooser.setMultiSelectionEnabled(true);
	        int option = chooser.showOpenDialog(GUI.this);
	        if (option == JFileChooser.APPROVE_OPTION) 
	        {
	          File[] sf = chooser.getSelectedFiles();
	          String filelist = "nothing";
	          if (sf.length > 0) filelist = sf[0].getName();
	          for (int i = 1; i < sf.length; i++) {
	            filelist += ", " + sf[i].getName();
	          }
	          statusbar.setText("You chose " + filelist);
	        }
	        else {
	          statusbar.setText("You canceled.");
	        }
	      }
	    });

	    c.add(openButton);
	    c.add(statusbar);
	  }

	  public static void main(String args[]) {
		  GUI sfc = new GUI();
	    sfc.setVisible(true);
	  }

  }
