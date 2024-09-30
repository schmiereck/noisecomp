/*
 * www.schmiereck.de (c) 2010
 */
package demo1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleRole;
import javax.accessibility.AccessibleState;
import javax.accessibility.AccessibleStateSet;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/**
 * <p>
 * 	http://www.java2s.com/Code/Java/Swing-JFC/AccessibleScrollDemo.htm
 * </p>
 * 
 * @author smk
 * @version <p>02.11.2010:	created, smk</p>
 */
public class AccessibleScrollDemo 
extends JPanel 
{
  private Rule columnView;

  private Rule rowView;

  private JToggleButton isMetric;

  private ScrollablePicture picture;

  public AccessibleScrollDemo() {
    //Load the photograph into an image icon.
    ImageIcon david = new ImageIcon("demo1/AccessibleScrollDemo.PNG");
    david.setDescription("Photograph of David McNabb in his youth.");

    //Create the row and column headers
    columnView = new Rule(Rule.HORIZONTAL, true);
    columnView.setPreferredWidth(david.getIconWidth());
    columnView.getAccessibleContext().setAccessibleName("Column Header");
    columnView.getAccessibleContext().setAccessibleDescription(
        "Displays horizontal ruler for "
            + "measuring scroll pane client.");
    rowView = new Rule(Rule.VERTICAL, true);
    rowView.setPreferredHeight(david.getIconHeight());
    rowView.getAccessibleContext().setAccessibleName("Row Header");
    rowView.getAccessibleContext().setAccessibleDescription(
        "Displays vertical ruler for "
            + "measuring scroll pane client.");

    //Create the corners
    JPanel buttonCorner = new JPanel();
    isMetric = new JToggleButton("cm", true);
    isMetric.setFont(new Font("SansSerif", Font.PLAIN, 11));
    isMetric.setMargin(new Insets(2, 2, 2, 2));
    isMetric.addItemListener(new UnitsListener());
    isMetric.setToolTipText("Toggles rulers' unit of measure "
        + "between inches and centimeters.");
    buttonCorner.add(isMetric); //Use the default FlowLayout
    buttonCorner.getAccessibleContext().setAccessibleName(
        "Upper Left Corner");

    String desc = "Fills the corner of a scroll pane "
        + "with color for aesthetic reasons.";
    Corner lowerLeft = new Corner();
    lowerLeft.getAccessibleContext().setAccessibleName("Lower Left Corner");
    lowerLeft.getAccessibleContext().setAccessibleDescription(desc);

    Corner upperRight = new Corner();
    upperRight.getAccessibleContext().setAccessibleName(
        "Upper Right Corner");
    upperRight.getAccessibleContext().setAccessibleDescription(desc);

    //Set up the scroll pane
    picture = new ScrollablePicture(david,//"XXXXXXXXXXXXXXXXXXX", 
                                    columnView.getIncrement());
    //picture.setToolTipText(david.getDescription());
    picture.getAccessibleContext().setAccessibleName("Scroll pane client");

    JScrollPane pictureScrollPane = new JScrollPane(picture);
    pictureScrollPane.setPreferredSize(new Dimension(300, 250));
    pictureScrollPane.setViewportBorder(BorderFactory
        .createLineBorder(Color.black));

    pictureScrollPane.setColumnHeaderView(columnView);
    pictureScrollPane.setRowHeaderView(rowView);

    pictureScrollPane
        .setCorner(JScrollPane.UPPER_LEFT_CORNER, buttonCorner);
    pictureScrollPane.setCorner(JScrollPane.LOWER_LEFT_CORNER, lowerLeft);
    pictureScrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, upperRight);

    //Put it in this panel
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    add(pictureScrollPane);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
  }

  class UnitsListener implements ItemListener {
    public void itemStateChanged(ItemEvent e) {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        //turn it to metric
        rowView.setIsMetric(true);
        columnView.setIsMetric(true);
      } else {
        //turn it to inches
        rowView.setIsMetric(false);
        columnView.setIsMetric(false);
      }
      picture.setMaxUnitIncrement(rowView.getIncrement());
    }
  }

  public static void main(String s[]) {
    JFrame frame = new JFrame("AccessibleScrollDemo");
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });

    frame.setContentPane(new AccessibleScrollDemo());
    frame.pack();
    frame.setVisible(true);
  }
}

class Rule extends JComponent implements Accessible {
  public static final int INCH = Toolkit.getDefaultToolkit()
      .getScreenResolution();

  public static final int HORIZONTAL = 0;

  public static final int VERTICAL = 1;

  public static final int SIZE = 35;

  public int orientation;

  public boolean isMetric;

  private int increment;

  private int units;

  public Rule(int o, boolean m) {
    orientation = o;
    isMetric = m;
    setIncrementAndUnits();
  }

  public void setIsMetric(boolean isMetric) {
    if (accessibleContext != null && this.isMetric != isMetric) {
      if (isMetric) {
        accessibleContext.firePropertyChange(
            AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
            AccessibleRulerState.INCHES,
            AccessibleRulerState.CENTIMETERS);
      } else {
        accessibleContext.firePropertyChange(
            AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
            AccessibleRulerState.CENTIMETERS,
            AccessibleRulerState.INCHES);
      }
    }
    this.isMetric = isMetric;
    setIncrementAndUnits();
    repaint();
  }

  private void setIncrementAndUnits() {
    if (isMetric) {
      units = (int) ((double) INCH / (double) 2.54); // dots per
      // centimeter
      increment = units;
    } else {
      units = INCH;
      increment = units / 2;
    }
  }

  public boolean isMetric() {
    return this.isMetric;
  }

  public int getIncrement() {
    return increment;
  }

  public void setPreferredHeight(int ph) {
    setPreferredSize(new Dimension(SIZE, ph));
  }

  public void setPreferredWidth(int pw) {
    setPreferredSize(new Dimension(pw, SIZE));
  }

  public void paintComponent(Graphics g) {
    Rectangle drawHere = g.getClipBounds();

    // Fill clipping area with dirty brown/orange.
    g.setColor(new Color(230, 163, 4));
    g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);

    // Do the ruler labels in a small font that's black.
    g.setFont(new Font("SansSerif", Font.PLAIN, 10));
    g.setColor(Color.black);

    // Some vars we need.
    int end = 0;
    int start = 0;
    int tickLength = 0;
    String text = null;

    // Use clipping bounds to calculate first tick
    // and last tick location.
    if (orientation == HORIZONTAL) {
      start = (drawHere.x / increment) * increment;
      end = (((drawHere.x + drawHere.width) / increment) + 1) * increment;
    } else {
      start = (drawHere.y / increment) * increment;
      end = (((drawHere.y + drawHere.height) / increment) + 1)
          * increment;
    }

    // Make a special case of 0 to display the number
    // within the rule and draw a units label.
    if (start == 0) {
      text = Integer.toString(0) + (isMetric ? " cm" : " in");
      tickLength = 10;
      if (orientation == HORIZONTAL) {
        g.drawLine(0, SIZE - 1, 0, SIZE - tickLength - 1);
        g.drawString(text, 2, 21);
      } else {
        g.drawLine(SIZE - 1, 0, SIZE - tickLength - 1, 0);
        g.drawString(text, 9, 10);
      }
      text = null;
      start = increment;
    }

    // ticks and labels
    for (int i = start; i < end; i += increment) {
      if (i % units == 0) {
        tickLength = 10;
        text = Integer.toString(i / units);
      } else {
        tickLength = 7;
        text = null;
      }

      if (tickLength != 0) {
        if (orientation == HORIZONTAL) {
          g.drawLine(i, SIZE - 1, i, SIZE - tickLength - 1);
          if (text != null)
            g.drawString(text, i - 3, 21);
        } else {
          g.drawLine(SIZE - 1, i, SIZE - tickLength - 1, i);
          if (text != null)
            g.drawString(text, 9, i + 3);
        }
      }
    }
  }

  public AccessibleContext getAccessibleContext() {
    if (accessibleContext == null) {
      accessibleContext = new AccessibleRuler();
    }
    return accessibleContext;
  }

  protected class AccessibleRuler extends AccessibleJComponent {
    public AccessibleRole getAccessibleRole() {
      return AccessibleRuleRole.RULER;
    }

    public AccessibleStateSet getAccessibleStateSet() {
      AccessibleStateSet states = super.getAccessibleStateSet();
      if (orientation == VERTICAL) {
        states.add(AccessibleState.VERTICAL);
      } else {
        states.add(AccessibleState.HORIZONTAL);
      }
      if (isMetric) {
        states.add(AccessibleRulerState.CENTIMETERS);
      } else {
        states.add(AccessibleRulerState.INCHES);
      }
      return states;
    }
  }
}

class AccessibleRuleRole extends AccessibleRole {
  public static final AccessibleRuleRole RULER = new AccessibleRuleRole(
      "ruler");

  protected AccessibleRuleRole(String key) {
    super(key);
  }

  //Should really provide localizable versions of these names.
  public String toDisplayString(String resourceBundleName, Locale locale) {
    return key;
  }
}

class AccessibleRulerState extends AccessibleState {
  public static final AccessibleRulerState INCHES = new AccessibleRulerState(
      "inches");

  public static final AccessibleRulerState CENTIMETERS = new AccessibleRulerState(
      "centimeters");

  protected AccessibleRulerState(String key) {
    super(key);
  }

  //Should really provide localizable versions of these names.
  public String toDisplayString(String resourceBundleName, Locale locale) {
    return key;
  }
}

class ScrollablePicture extends JLabel implements Scrollable {

  private int maxUnitIncrement = 1;

  public ScrollablePicture(Icon i,
                           //String text, 
                           int m) 
  {
//    super(text);
    super(i);
    maxUnitIncrement = m;
  }

  public Dimension getPreferredScrollableViewportSize() {
    return getPreferredSize();
  }

  public int getScrollableUnitIncrement(Rectangle visibleRect,
      int orientation, int direction) {

    int currentPosition = 0;
    if (orientation == SwingConstants.HORIZONTAL)
      currentPosition = visibleRect.x;
    else
      currentPosition = visibleRect.y;

    if (direction < 0) {
      int newPosition = currentPosition
          - (currentPosition / maxUnitIncrement) * maxUnitIncrement;
      return (newPosition == 0) ? maxUnitIncrement : newPosition;
    } else {
      return ((currentPosition / maxUnitIncrement) + 1)
          * maxUnitIncrement - currentPosition;
    }
  }

  public int getScrollableBlockIncrement(Rectangle visibleRect,
      int orientation, int direction) {
    if (orientation == SwingConstants.HORIZONTAL)
      return visibleRect.width - maxUnitIncrement;
    else
      return visibleRect.height - maxUnitIncrement;
  }

  public boolean getScrollableTracksViewportWidth() {
    return false;
  }

  public boolean getScrollableTracksViewportHeight() {
    return false;
  }

  public void setMaxUnitIncrement(int pixels) {
    maxUnitIncrement = pixels;
  }
}

class Corner extends JComponent implements Accessible {

  public void paintComponent(Graphics g) {
    // Fill me with dirty brown/orange.
    g.setColor(new Color(230, 163, 4));
    g.fillRect(0, 0, getWidth(), getHeight());
  }

  public AccessibleContext getAccessibleContext() {
    if (accessibleContext == null) {
      accessibleContext = new AccessibleCorner();
    }
    return accessibleContext;
  }

  protected class AccessibleCorner extends AccessibleJComponent {
    //Inherit everything, override nothing.
  }
}