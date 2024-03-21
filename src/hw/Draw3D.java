package src.hw;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.JFrame;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import src.primitives.Point4;
import src.primitives.Matrix3x3;
import src.primitives.Matrix4x4;

/**
 *
 * @author thetom
 */
public class Draw3D
    extends JPanel implements KeyListener {

  static final int WIDTH = 800;
  static final int HEIGHT = 600;

  Matrix4x4 transformnMatrix;

  Graphics g;

  double centerX;
  double centerY;
  double centerZ;
  double projectionDistance;

  ArrayList<Point4> vertices;
  public ArrayList<Edge> edges;

  public Draw3D() {
    super();

    double[][] initialm = {
        { 1, 0, 0, 0 },
        { 0, 1, 0, 0 },
        { 0, 0, 1, 0 },
        { 0, 0, 0, 1 }
    };
    this.transformnMatrix = new Matrix4x4(initialm);

    vertices = new ArrayList<Point4>();
    edges = new ArrayList<Edge>();

    readObject("casita3D.txt");

    System.out.println("x center:" + centerX);
    System.out.println("y center:" + centerY);
    System.out.println("z center:" + centerZ);

    // Make the JPanel focusable
    setFocusable(true);
    // Request focus for the JPanel
    requestFocusInWindow();
    // Add this class as the KeyListener
    addKeyListener(this);

    printVerticesAndEndges();
  }

  public void printVerticesAndEndges() {
    System.out.println("Vertices:");
    for (Point4 p : vertices) {
      System.out.println(p);
    }

    System.out.println("Edges:");
    for (Edge e : edges) {
      System.out.println(e);
    }
  }

  public void readObject(String filename) {
    try {
      // read the number of vertices and then the vertices
      Scanner in = new Scanner(new File(filename));
      int n = in.nextInt();
      for (int i = 0; i < n; i++) {
        double x = in.nextDouble();
        double y = in.nextDouble();
        double z = in.nextDouble();
        vertices.add(new Point4(x, y, z, 1));
      }
      // read the number of edges and then the edge indices
      n = in.nextInt();
      for (int i = 0; i < n; i++) {
        int start = in.nextInt();
        int end = in.nextInt();
        edges.add(new Edge(start, end));
      }
      // read the center of the object
      // rotations and scaling are done with respect to the center
      this.centerX = in.nextInt();
      this.centerY = in.nextInt();
      this.centerZ = in.nextInt();
      // read the Z coordinate of the the projection plane.
      // Since the camera is at the origin looking into the negative
      // z axis, the projection plane is at z = -projectionDistance
      this.projectionDistance = in.nextInt();
    } catch (Exception e) {
      System.out.println("Error reading file");
    }
  }

  public void projectObject() {
    ArrayList<Point4> projectedVertices = new ArrayList<>();

    double[][] pm = {
        { 1, 0, 0, 0 },
        { 0, 1, 0, 0 },
        { 0, 0, 1, 0 },
        { 0, 0, 1 / this.projectionDistance, 0 }
    };
    Matrix4x4 proyectionMatrix = new Matrix4x4(pm);

    for (Point4 v : this.vertices) {

      Point4 newVertex = Matrix4x4.mul(v, proyectionMatrix);

      newVertex.normalizeW();

      projectedVertices.add(newVertex);
    }

    this.vertices = projectedVertices;
  }

  public void draw() {
    for (Edge e : edges) {
      Point4 v1 = this.vertices.get(e.start);
      Point4 v2 = this.vertices.get(e.end);
      int x1 = (int) v1.getX();
      int y1 = (int) v1.getY();
      int x2 = (int) v2.getX();
      int y2 = (int) v2.getY();
      this.drawOneLine(x1, y1, x2, y2);
    }
  }

  public void transformObject() {
    ArrayList<Point4> transforedVertices = new ArrayList<>();

    for (Point4 v : this.vertices) {
      Point4 newVertex = Matrix4x4.mul(v, this.transformnMatrix);
      transforedVertices.add(newVertex);
    }

    this.vertices = transforedVertices;
  }

  public void drawOneLine(int x1, int y1, int x2, int y2) {
    x1 = x1 + WIDTH / 2;
    y1 = HEIGHT / 2 - y1;
    x2 = x2 + WIDTH / 2;
    y2 = HEIGHT / 2 - y2;
    g.drawLine(x1, y1, x2, y2);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    this.g = g;

    transformObject();

    projectObject();

    draw();
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    if (key == KeyEvent.VK_UP) {
      double[][] moveUp = {
          { 1, 0, 0, 0 },
          { 0, 1, 0, 10 },
          { 0, 0, 1, 0 },
          { 0, 0, 0, 1 }
      };
      this.transformnMatrix = new Matrix4x4(moveUp);
      repaint();
    } else if (key == KeyEvent.VK_DOWN) {
      double[][] moveUp = {
          { 1, 0, 0, 0 },
          { 0, 1, 0, -10 },
          { 0, 0, 1, 0 },
          { 0, 0, 0, 1 }
      };
      this.transformnMatrix = new Matrix4x4(moveUp);
      repaint();
    } else if (key == KeyEvent.VK_0) {

    } else if (key == KeyEvent.VK_RIGHT) {
      double angle = Math.toRadians(5);

      double[][] translateToOrigin = {
          { 1, 0, 0, -centerX },
          { 0, 1, 0, -centerY },
          { 0, 0, 1, -centerZ },
          { 0, 0, 0, 1 }
      };
      this.transformnMatrix = new Matrix4x4(translateToOrigin);
      transformObject();

      double[][] rotate = {
          { 1, 0, 0, 0 },
          { 0, Math.cos(angle), -Math.sin(angle), 0 },
          { 0, Math.sin(angle), Math.cos(angle), 0 },
          { 0, 0, 0, 1 }
      };
      this.transformnMatrix = new Matrix4x4(rotate);
      transformObject();

      double[][] translateBack = {
          { 1, 0, 0, centerX },
          { 0, 1, 0, centerY },
          { 0, 0, 1, centerZ },
          { 0, 0, 0, 1 }
      };
      this.transformnMatrix = new Matrix4x4(translateBack);

      repaint();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  public static void main(String[] args) {
    // Crear un nuevo Frame
    JFrame frame = new JFrame("Eventos del Mouse");
    // Al cerrar el frame, termina la ejecución de este programa
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Agregar un JPanel que se llama Points (esta clase)
    Draw3D ev = new Draw3D();
    frame.add(ev);
    // Asignarle tamaño
    frame.setSize(ev.WIDTH, ev.HEIGHT);
    // Poner el frame en el centro de la pantalla
    frame.setLocationRelativeTo(null);
    // Mostrar el frame
    frame.setVisible(true);
  }
}