package src.hw;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import src.primitives.Point3;
import src.primitives.Matrix3x3;

import javax.swing.JPanel;
import javax.swing.JFrame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author thetom
 */
public class Draw
    extends JPanel implements KeyListener {

  int height = 500;
  int width = 500;

  double[][] instructions;

  public Draw(double[][] instructions) {
    super();
    setBackground(Color.WHITE);

    this.instructions = instructions;

    double[][] initialTransformationMatrix = {
        { 1, 0, width / 2 },
        { 0, -1, height / 2 },
        { 0, 0, 1 }
    };
    Matrix3x3 initialTrasform = new Matrix3x3(initialTransformationMatrix);
    trasformPoints(initialTrasform);

    // Make the JPanel focusable
    setFocusable(true);
    // Request focus for the JPanel
    requestFocusInWindow();
    // Add this class as the KeyListener
    addKeyListener(this);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    int startOfLineInstructions = (int) instructions[0][0] + 2;

    for (int i = startOfLineInstructions; i < instructions.length; i++) {
      int point0Location = (int) instructions[i][0];
      int point1Location = (int) instructions[i][1];

      double[] point0Cordinates = instructions[point0Location + 1];
      double[] point1Cordinates = instructions[point1Location + 1];

      Point3 point0 = new Point3(point0Cordinates[0], point0Cordinates[1], 1);
      Point3 point1 = new Point3(point1Cordinates[0], point1Cordinates[1], 1);

      g2d.drawLine((int) point0.getX(), (int) point0.getY(), (int) point1.getX(), (int) point1.getY());
    }
  }

  private void trasformPoints(Matrix3x3 traformationMatrix) {
    int numberOfPoints = (int) instructions[0][0];

    for (int i = 1; i <= numberOfPoints; i++) {
      Point3 point = new Point3(instructions[i][0], instructions[i][1], 1);

      Point3 newPoint = Matrix3x3.mul(point, traformationMatrix);

      instructions[i] = new double[] { newPoint.getX(), newPoint.getY() };
    }
  }

  private static double[][] readPointsFromFile(String filename) {
    List<double[]> pointsList = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] numbers = line.split(" ");
        double[] point = new double[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
          point[i] = Integer.parseInt(numbers[i]);
        }
        pointsList.add(point);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    double[][] pointsArray = new double[pointsList.size()][];
    pointsArray = pointsList.toArray(pointsArray);
    return pointsArray;
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    if (key == KeyEvent.VK_UP) {
      double[][] moveUp = {
          { 1, 0, 0 },
          { 0, 1, -10 },
          { 0, 0, 1 }
      };
      Matrix3x3 moveUpMatrix = new Matrix3x3(moveUp);
      trasformPoints(moveUpMatrix);
      repaint();
    } else if (key == KeyEvent.VK_DOWN) {
      double[][] moveDown = {
          { 1, 0, 0 },
          { 0, 1, 10 },
          { 0, 0, 1 }
      };
      Matrix3x3 moveDownMatrix = new Matrix3x3(moveDown);
      trasformPoints(moveDownMatrix);
      repaint();
    } else if (key == KeyEvent.VK_0) {
      Point3 center = calculateCenter();
      double angle = Math.toRadians(-5);

      double[][] moveToCenter = {
          { 1, 0, -center.getX() },
          { 0, 1, -center.getY() },
          { 0, 0, 1 }
      };
      Matrix3x3 moveToCenterMatrix = new Matrix3x3(moveToCenter);
      trasformPoints(moveToCenterMatrix);

      double[][] rotate = {
          { Math.cos(angle), -Math.sin(angle), 0 },
          { Math.sin(angle), Math.cos(angle), 0 },
          { 0, 0, 1 }
      };
      Matrix3x3 rotateMatrix = new Matrix3x3(rotate);
      trasformPoints(rotateMatrix);

      double[][] moveBack = {
          { 1, 0, center.getX() },
          { 0, 1, center.getY() },
          { 0, 0, 1 }
      };
      Matrix3x3 moveBackMatrix = new Matrix3x3(moveBack);
      trasformPoints(moveBackMatrix);

      repaint();
    } else if (key == KeyEvent.VK_RIGHT) {
      Point3 center = calculateCenter();
      double angle = Math.toRadians(5);

      double[][] moveToCenter = {
          { 1, 0, -center.getX() },
          { 0, 1, -center.getY() },
          { 0, 0, 1 }
      };
      Matrix3x3 moveToCenterMatrix = new Matrix3x3(moveToCenter);
      trasformPoints(moveToCenterMatrix);

      double[][] rotate = {
          { Math.cos(angle), -Math.sin(angle), 0 },
          { Math.sin(angle), Math.cos(angle), 0 },
          { 0, 0, 1 }
      };
      Matrix3x3 rotateMatrix = new Matrix3x3(rotate);
      trasformPoints(rotateMatrix);

      double[][] moveBack = {
          { 1, 0, center.getX() },
          { 0, 1, center.getY() },
          { 0, 0, 1 }
      };
      Matrix3x3 moveBackMatrix = new Matrix3x3(moveBack);
      trasformPoints(moveBackMatrix);

      repaint();
    }
  }

  private Point3 calculateCenter() {
    double sumX = 0, sumY = 0;
    int numberOfPoints = (int) instructions[0][0];
    for (int i = 1; i <= numberOfPoints; i++) {
      sumX += instructions[i][0];
      sumY += instructions[i][1];
    }
    return new Point3(sumX / numberOfPoints, sumY / numberOfPoints, 1);
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  public static void main(String[] args) {
    double[][] instructions = Draw
        .readPointsFromFile("/Users/tomascalle/Documents/GitHub/computer-graphics/src/hw/draw.txt");
    // Crear un nuevo Frame
    JFrame frame = new JFrame("Eventos del Mouse");
    // Al cerrar el frame, termina la ejecución de este programa
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Agregar un JPanel que se llama Points (esta clase)
    Draw ev = new Draw(instructions);
    frame.add(ev);
    // Asignarle tamaño
    frame.setSize(ev.width, ev.height);
    // Poner el frame en el centro de la pantalla
    frame.setLocationRelativeTo(null);
    // Mostrar el frame
    frame.setVisible(true);
  }

}
