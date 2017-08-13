package org.adammohammed;



public class Computer {

  public static void main (String[] args) {
    new ComputerGUI();
  }

  static int calculateGroundSpeed(int ch, int ias, int dir, int windSpeed) {
    double headwindComponent = windSpeed * Math.cos(Math.toRadians(dir - ch));
    return ias - (int) headwindComponent;
  }
}
