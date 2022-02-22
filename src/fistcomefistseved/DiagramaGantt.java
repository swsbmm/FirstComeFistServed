/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fistcomefistseved;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.prefs.BackingStoreException;

/**
 *
 * @author felipe
 */
public class DiagramaGantt extends Canvas {

    public Color color;
    private int ancho_pincel;
    private Path2D shape;
    private double unidad;
    private procesador procesador;
    private int salto_de_linea;
    private Image dobleBuffer;
    private Graphics gAux;
    private Dimension dimAux;
    private Dimension dimCanvas;

    public DiagramaGantt(procesador p) {
        this.color = Color.white;
        this.setBackground(color.black);
        this.shape = new Path2D.Float();
        this.ancho_pincel = 2;
        this.unidad = 30;
        this.procesador = p;
        this.salto_de_linea = 30;
        dimCanvas = this.getSize();

    }

    public void dibujarLinea(Graphics2D pincel) {
        int inicio_x = 50;
        int altura_linea = 30;
        int altura_numeros = 15;
        pincel.setColor(Color.white);
        pincel.draw(new Line2D.Double(inicio_x, altura_linea, this.getWidth() - 5, altura_linea));
        int numero = 0;
        for (double i = inicio_x; i < this.getWidth(); i = i + unidad) {
            pincel.draw(new Line2D.Double(i, altura_linea - 10, i, this.getHeight()-10));
            pincel.drawString("" + numero, (int) i - 4, altura_numeros);
            numero++;
        }

    }

    public void dibujarProcesoEE(Graphics2D pincel) {
        int altura = 70;
        int separacion = 50;
        pincel.setPaint(Color.white);
        if (!procesador.getCola().isEmpty()) {
            pincel.fill(new Rectangle2D.Double(
                    separacion + procesador.getCola().peek().getTiempo_llegada() * unidad,
                    altura,
                    procesador.getCola().peek().getTiempo_comienzo() * unidad + procesador.getCola().peek().getTiempo_ejecucion() * unidad - procesador.getCola().peek().getTiempo_llegada() * unidad,
                    15
            ));
            pincel.drawString(procesador.getCola().peek().getNombre(), 20, altura + 15);

        }

    }

    public void dibujarBarras1(Graphics2D pincel) {
        int altura_inicial = 100;
        int altura_inicial1 = 100;
        int separacion = 50;
        pincel.setPaint(Color.red);
        if (!procesador.getCola_terminado().isEmpty()) {
            //TIEMPO DE EJECICION y TIEMPO BLOQUEADO
            for (proceso p : procesador.getCola_terminado()) {
                pincel.drawString(p.getNombre(), 20, altura_inicial + 15);
                int xx = (int) ((separacion + p.getTiempo_llegada() * unidad));
                for (int x = 0; x < p.getLista_estados().size(); x++) {
                    //System.out.println("LONGITUD: "+ p.getLista_estados().size());
                    if (p.getLista_estados().get(x) == "2") {
                        pincel.setPaint(Color.blue);
                    } else if(p.getLista_estados().get(x) == "5") {
                        pincel.setPaint(Color.red);
                    } else if(p.getLista_estados().get(x) == "7"){
                        pincel.setPaint(Color.cyan);
                    }else if(p.getLista_estados().get(x) == "1"){
                        pincel.setPaint(Color.YELLOW);
                    }
                    pincel.fill(new Rectangle2D.Double(
                            xx,
                            altura_inicial,
                            unidad,
                            15
                    ));
                    xx = (int) (xx + unidad);
                }
                pincel.setPaint(Color.red);
                altura_inicial = altura_inicial + salto_de_linea;
            }
        }
    }

    @Override
    public void update(Graphics g){
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        if(gAux == null || dimAux == null || dimCanvas.width != dimAux.width ||
                 dimCanvas.height != dimAux.height || 
                this.getWidth() != dimAux.width ||
                this.getHeight() != dimAux.height){
            dimAux = this.getSize();
            dobleBuffer = createImage(dimAux.width, dimAux.height);
            gAux = dobleBuffer.getGraphics();
        }
        Graphics2D draw = (Graphics2D) gAux;
        draw.setColor(color.black);
        draw.fillRect(0, 0, this.getWidth(), this.getHeight());
        draw.setStroke(new BasicStroke(ancho_pincel));
        draw.setColor(color);
        dibujarLinea(draw);
        dibujarProcesoEE(draw);
        dibujarBarras1(draw);
        draw.draw(this.shape);
        g.drawImage(dobleBuffer, 0, 0, this);
    }

}
