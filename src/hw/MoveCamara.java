package src.hw;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JFrame;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import src.primitives.Point4;
import src.primitives.Matrix4x4;
import src.primitives.Vector4;

/**
 *
 * @author thetom
 */
public class MoveCamara extends JPanel implements KeyListener {

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

  Vector4 n;
  Vector4 u;
  Vector4 v;

  public MoveCamara() {
    super();

    vertices = new ArrayList<Point4>();
    edges = new ArrayList<Edge>();

    readObject("casita3D.txt");

    double[][] initialm = {
        { 1, 0, 0, centerX },
        { 0, 1, 0, centerY },
        { 0, 0, 1, centerZ },
        { 0, 0, 0, 1 }

    };
    this.transformnMatrix = new Matrix4x4(initialm);

    Vector4 from = new Vector4(0,0,centerZ,1);
    Vector4 lookAt = new Vector4(0,0,-centerZ,1);
    Vector4 up = new Vector4(0,1,0,1);

    this.n = Vector4.sub(from, lookAt);
    this.n = Vector4.normilize(this.n);

    this.u = Vector4.cross(up, this.n);
    this.u = Vector4.normilize(this.u);

    this.v = Vector4.cross(this.n, this.u);

    // Make the JPanel focusable
    setFocusable(true);
    // Request focus for the JPanel
    requestFocusInWindow();
    // Add this class as the KeyListener
    addKeyListener(this);
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

  public void alignReference() {
    Matrix4x4 camaraReference = setCamaraReference(new Vector4(0,0,centerZ,1));

    ArrayList<Point4> trasformedVertices = new ArrayList<>();


    for (Point4 v : this.vertices) {
      Point4 newVertex = Matrix4x4.mul(v, camaraReference);
      newVertex.normalizeW();
      trasformedVertices.add(newVertex);
    }

    this.vertices = trasformedVertices;
  }

  public Matrix4x4 setCamaraReference(Vector4 from) {
    double[][] m = {
        { this.u.getX(), this.u.getY(), this.u.getW(), Vector4.dot(Vector4.negative(this.u), from) },
        { this.v.getX(), this.v.getY(), this.v.getW(), Vector4.dot(Vector4.negative(this.v), from) },
        { this.n.getX(), this.n.getY(), this.n.getW(), Vector4.dot(Vector4.negative(this.n), from) },
        { 0, 0, 0, 1 }
    };

    return new Matrix4x4(m);
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

    System.out.println(this.transformnMatrix);

    this.g = g;
    transformObject();
    alignReference();
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
    } else if (key == KeyEvent.VK_0) {
    } else if (key == KeyEvent.VK_RIGHT) {
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
    MoveCamara ev = new MoveCamara();
    frame.add(ev);
    // Asignarle tamaño
    frame.setSize(ev.WIDTH, ev.HEIGHT);
    // Poner el frame en el centro de la pantalla
    frame.setLocationRelativeTo(null);
    // Mostrar el frame
    frame.setVisible(true);
  }
}