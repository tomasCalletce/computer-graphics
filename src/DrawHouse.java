package src;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;
import javax.swing.JFrame;

/**
 *
 * @author htrefftz
 */
public class DrawHouse
    extends JPanel
    implements MouseListener {

  Line2D.Double linea1;

  private int[][] points = {
      { 100, 100 },
      { 200, 100 },
      { 200, 200 },
      { 150, 250 },
      { 100, 200 }
  };
  int height = 500;
  int width = 500;

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.BLUE);
    for (int[] point : points) {
      Ellipse2D.Double ellipse = new Ellipse2D.Double(point[0], point[1], 6, 6);
      g2d.fill(ellipse);
    }

  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
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
