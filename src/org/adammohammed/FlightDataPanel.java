package org.adammohammed;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class FlightDataPanel extends JPanel {
  public static final long serialVersionUID = 1l;

  private JLabel[] labels;
  private JTextField[] inputFields;
  public static final int AIRSPEED = 0;
  public static final int COURSE = 1;
  public static final int WINDSPEED = 2;
  public static final int WINDDIRECTION = 3;

  public FlightDataPanel() {
    this.setLayout(new GridBagLayout());
    this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    GridBagConstraints c = new GridBagConstraints();


    this.setFont(Font.getFont("arial"));
    labels = new JLabel[4];

    labels[AIRSPEED] = new JLabel("Airspeed: ");
    labels[COURSE] = new JLabel("Course Heading: ");
    labels[WINDSPEED] = new JLabel("Wind Speed: ");
    labels[WINDDIRECTION] = new JLabel("Wind Direction: ");

    inputFields = new JTextField[4];
    inputFields[AIRSPEED] = new JTextField(3);
    inputFields[COURSE] = new JTextField(3);
    inputFields[WINDSPEED] = new JTextField(3);
    inputFields[WINDDIRECTION] = new JTextField(3);


    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridy = 0;
    for(int i = 0; i < 4; i++) {
      c.gridx = 0;
      c.weightx = 0;
      this.add(labels[i], c);

      c.gridx = 1;
      c.weightx = 0.5;
      this.add(inputFields[i], c);

      c.gridy += 1;
    }

  }

  public final double getField(int FIELD){
    String val = inputFields[FIELD].getText();

    if (FIELD == COURSE || FIELD == WINDDIRECTION){
      return Math.toRadians(textToInt(val));
    }

    return (double) textToInt(val);
  }

  private final int textToInt (String s) {
    if (s.isEmpty())
      return 0;

    try {
      final int v =  Integer.parseInt(s);
      return v;
    } catch (NumberFormatException e){
      return 0;
    }
  }
}
