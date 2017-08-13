package org.adammohammed;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import javax.swing.*;

public class ComputerGUI extends JFrame {
  private static final long serialVersionUID = 1L;
  private BufferedImage graphicsContext;
  private JLabel contextRender;
  private int width = 300;
  private JButton submitButton;
  private Color bg;

  public ComputerGUI(){
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Flight computer");
    //this.setSize(500,500);
    this.setLocationRelativeTo(null);

    graphicsContext = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);
    contextRender = new JLabel(new ImageIcon(graphicsContext));
    contextRender.setOpaque(false);
    contextRender.setAlignmentX(Component.CENTER_ALIGNMENT);
    final JPanel comboPanel = new JPanel();

    FlightDataPanel inputPanel = new FlightDataPanel();

    submitButton = new JButton("Calculate");
    submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    submitButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          final double airspeed = inputPanel.getField(FlightDataPanel.AIRSPEED);
          final double course = inputPanel.getField(FlightDataPanel.COURSE);
          final double windSpeed = inputPanel.getField(FlightDataPanel.WINDSPEED);
          final double windDirection = inputPanel.getField(FlightDataPanel.WINDDIRECTION);
          updateVisual(airspeed, course, windSpeed, windDirection);
          calculateTrack(airspeed, course, windSpeed, windDirection);
          repaint();
        }
      });

    comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.PAGE_AXIS));
    comboPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

    comboPanel.add(inputPanel);
    comboPanel.add(Box.createRigidArea(new Dimension(0,5)));
    comboPanel.add(submitButton);
    comboPanel.add(Box.createRigidArea(new Dimension(0,10)));
    comboPanel.add(contextRender);
    add(comboPanel, BorderLayout.NORTH);
    // comboPanel.setSize(width+100, width+100);
    // this.setContentPane(comboPanel);
    this.pack();
    bg = comboPanel.getBackground();
    paint();
    setVisible(true);
  }

  public void updateVisual (final double airspeed, final double course, final double windSpeed, final double windDirection) {
    Graphics2D g2d = graphicsContext.createGraphics();
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setColor(bg);
    g2d.fillRect(0, 0, width+100, width+100);
    g2d.setStroke(new BasicStroke(4));

    // Draw Course Line
    g2d.setColor(Color.DARK_GRAY);
    double speed = Math.min(airspeed, 145);
    int xlen = 150 + (int) (speed * Math.sin(course));
    int ylen = 150 - (int) (speed * Math.cos(course));
    g2d.drawLine(150, 150, xlen, ylen);

    // Draw Wind from tip of the course line
    g2d.setColor(Color.CYAN);
    speed = Math.min(windSpeed, 145);
    int xWind = xlen - (int) (speed * Math.sin(windDirection));
    int yWind = ylen + (int) (speed * Math.cos(windDirection));
    g2d.drawLine(xlen, ylen, xWind, yWind);

    // Draw Circle
    g2d.setColor(Color.BLUE);
    g2d.drawOval(4, 4, 292, 292);
  }

  public void calculateTrack (final double airspeed, final double course, final double windSpeed, final double windDirection) {
    double courseDeg = Math.toDegrees(course);
    double airX = airspeed * Math.sin(course);
    double airY = airspeed * Math.cos(course);

    double windX = windSpeed * Math.sin(windDirection);
    double windY = windSpeed * Math.cos(windDirection);

    double trackXCor = airX + windX;
    double trackYCor = airY + windY;
    double trackHeadingCor = Math.toDegrees(Math.atan2(trackXCor, trackYCor));
    if (trackHeadingCor < 0)
      trackHeadingCor += 360;
    int windCorrection = (int)((trackHeadingCor - courseDeg) + 0.5);

    System.out.printf("Wind Correction angle is %+d\n",  windCorrection);

  }

  public void paint() {
    Graphics2D g2d = graphicsContext.createGraphics();
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setColor(bg);
    g2d.fillRect(0, 0, width+100, width+100);
    g2d.setStroke(new BasicStroke(4));
    g2d.setColor(Color.BLUE);
    g2d.drawOval(4, 4, 292, 292);
  }

}
