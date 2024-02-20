package src.hw;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;
import javax.swing.JFrame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 *
 * @author thetom
 */
public class DrawHouse
    extends JPanel {

  int height = 500;
  int width = 500;

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    int[][] instructions = readPointsFromFile("/Users/tomascalle/Documents/GitHub/computer-graphics/src/hw/draw.txt");

    Graphics2D g2d = (Graphics2D) g;

    boolean drawPoints = false;
    boolean drawLines = false;

    List<int[]> pointsDrawn = new ArrayList<>();

    for (int[] point : instructions) {
      if (point.length == 1 && point[0] == 5) {
        drawPoints = true;
        drawLines = false;
      } else if (point.length == 1 && point[0] == 6) {
        drawLines = true;
        drawPoints = false;
      } else if (drawPoints || drawLines) {
        if (drawPoints) {
          pointsDrawn.add(point);
        } else {
          int[] point0 = pointsDrawn.get(point[0]);
          int[] point1 = pointsDrawn.get(point[1]);

          double[] newPoint0 = translatePoints(point0[0], point0[1]);
          double[] newPoint1 = translatePoints(point1[0], point1[1]);

          g2d.drawLine((int) newPoint0[0], (int) newPoint0[1], (int) newPoint1[0], (int) newPoint1[1]);
        }
      }
    }

    for (int[] point : pointsDrawn) {
      System.out.println(Arrays.toString(point));
    }

  }

  private double[] translatePoints(int x, int y) {
    double newX = width / 2 + x;
    double newY = height / 2 - y;

    return new double[] { newX, newY };
  }

  private int[][] readPointsFromFile(String filename) {
    List<int[]> pointsList = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] numbers = line.split(" ");
        int[] point = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
          point[i] = Integer.parseInt(numbers[i]);
        }
        pointsList.add(point);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    int[][] pointsArray = new int[pointsList.size()][];
    pointsArray = pointsList.toArray(pointsArray);
    return pointsArray;
  }

  public static void main(String[] args) {
    // Crear un nuevo Frame
    JFrame frame = new JFrame("Eventos del Mouse");
    // Al cerrar el frame, termina la ejecución de este programa
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Agregar un JPanel que se llama Points (esta clase)
    DrawHouse ev = new DrawHouse();
    frame.add(ev);
    // frame.addMouseListener(ev);
    // Asignarle tamaño
    frame.setSize(ev.width, ev.height);
    // Poner el frame en el centro de la pantalla
    frame.setLocationRelativeTo(null);
    // Mostrar el frame
    frame.setVisible(true);
  }

}
