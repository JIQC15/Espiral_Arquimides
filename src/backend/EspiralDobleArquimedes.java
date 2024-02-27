//Joseph Quintero
//Realiza el espiral de Arquimides con un Thread para controlar el tiempo.

package backend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EspiralDobleArquimedes extends JPanel implements Runnable {

    private Thread hilo;
    private BufferedImage bufferImage;

    private final int alturaPanel;
    private final int anchuraPanel;
    private double anguloInicial;
    private final double aumentoAngulo;
    private double radioInicial;
    private final double aumentoRadio;
    private final double vueltasTotales;
    private int centroPanel_X;
    private int centroPanel_Y;

    public EspiralDobleArquimedes() {
        alturaPanel = 700;
        anchuraPanel = 700;
        anguloInicial = 0;
        aumentoAngulo = 0.1;
        radioInicial = 2;
        aumentoRadio = 0.324;
        vueltasTotales = 200 * Math.PI;
        centroPanel_X = anchuraPanel / 2;
        centroPanel_Y = alturaPanel / 2 - 15;
        this.setPreferredSize(new Dimension(anchuraPanel, alturaPanel));
        this.bufferImage = new BufferedImage(anchuraPanel, alturaPanel, BufferedImage.TYPE_INT_ARGB);
        this.hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(bufferImage, 0, 0, null);
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    private void giroEspiralIzquierda(Graphics2D g2D, double angulo, double radio) {
        g2D.setColor(Color.RED);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = (int) (centroPanel_X + radio * Math.cos(angulo));
        int y = (int) (centroPanel_Y + radio * Math.sin(angulo));

        int bufferX = (int) (centroPanel_X + (radio + aumentoRadio) * Math.cos(angulo + aumentoAngulo));
        int bufferY = (int) (centroPanel_Y + (radio + aumentoRadio) * Math.sin(angulo + aumentoAngulo));

        g2D.drawLine(x, y, bufferX, bufferY);
        g2D.drawLine((int) (centroPanel_X - radioInicial), centroPanel_Y, (int) (centroPanel_X + radioInicial), centroPanel_Y); // 10 es el radio inicial
    }

    private void giroEspiralDerecha(Graphics2D g2D, double angulo, double radio) {
        g2D.setColor(Color.BLUE);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = (int) (centroPanel_X + radio * Math.cos(angulo) * -1);
        int y = (int) (centroPanel_Y + radio * Math.sin(angulo) * -1);

        int bufferX = (int) (centroPanel_X + (radio + aumentoRadio) * Math.cos(angulo + aumentoAngulo) * -1);
        int bufferY = (int) (centroPanel_Y + (radio + aumentoRadio) * Math.sin(angulo + aumentoAngulo) * -1);

        g2D.drawLine(x, y, bufferX, bufferY);
        g2D.drawLine((int) (centroPanel_X - radioInicial), centroPanel_Y, (int) (centroPanel_X + radioInicial), centroPanel_Y); // 10 es el radio inicial
    }

    @Override
    public void run() {
        double angulo = anguloInicial;
        double radio = radioInicial;

        while (angulo <= vueltasTotales) {
            Graphics2D g2 = (Graphics2D) bufferImage.getGraphics();

            giroEspiralIzquierda(g2, angulo, radio);
            giroEspiralDerecha(g2, angulo, radio);

            g2.dispose();
            repaint();

            angulo += aumentoAngulo;
            radio += aumentoRadio;

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Espiral de Arquimides");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new EspiralDobleArquimedes());
        frame.pack();

        frame.setVisible(true);
    }
}
