// Fig. 11.43: GridLayoutFrame.java
// Demonstrating GridLayout.
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JButton;

public class GridLayoutFrame extends JFrame implements ActionListener 
{
   private JButton buttons[]; // array of buttons
   private final String names[] = 
      { "one", "two", "three" };
   private boolean toggle = true; // toggle between two layouts
   private Container container; // frame container
   private GridLayout gridLayout1; // first gridlayout
   private GridLayout gridLayout2; // second gridlayout
   private String s = "hii";

   // no-argument constructor
   public GridLayoutFrame()
   {
      super( "GridLayout Demo" );
      gridLayout1 = new GridLayout( 2, 3, 5, 5 ); // 2 by 3; gaps of 5
      gridLayout2 = new GridLayout( 3, 2 ); // 3 by 2; no gaps
      container = getContentPane(); // get content pane
      setLayout( gridLayout1 ); // set JFrame layout
      buttons = new JButton[ names.length ]; // create array of JButtons

      for ( int count = 0; count < names.length; count++ )
      {
         buttons[ count ] = new JButton( names[ count ] );
         buttons[ count ].addActionListener( this ); // register listener
         add( buttons[ count ] ); // add button to JFrame
      } // end for
   } // end GridLayoutFrame constructor

   // handle button events by toggling between layouts
   public void actionPerformed( ActionEvent event )
   { 
      if ( toggle )
      {
         container.setLayout( gridLayout2 ); // set layout to second
         s="1";
         buttons[0] = new JButton(s);
         add( buttons[ 0 ] );
      }
      else
         container.setLayout( gridLayout1 ); // set layout to first

      toggle = !toggle; // set toggle to opposite value
      container.validate(); // re-lay out container
   } // end method actionPerformed
 // end class GridLayoutFrame

    public String getS()
    {
        return s;
    }

    public void setS(String s2)
    {
        s=s2;
    }

}
/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/