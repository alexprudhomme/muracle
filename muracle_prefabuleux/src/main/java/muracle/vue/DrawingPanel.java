package muracle.vue;

import muracle.domaine.drawer.Afficheur;
import muracle.domaine.drawer.AfficheurElevationCote;
import muracle.domaine.drawer.AfficheurPlanSalle;
import muracle.utilitaire.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class DrawingPanel extends JPanel implements MouseWheelListener {
    private MainWindow mainWindow;
    private Fraction zoomFactor;
    private Fraction zoomInc;
    private CoordPouce posiCam;
    private CoordPouce posiCamTempo;
    private CoordPouce dimPlan;
    private final Color backgroundColor;
    private Point mouse_pt;
    private boolean clip;

    /**
     * @brief constructeur de la classe DrawingPanel
     * @param mainWindow:MainWindow
     */
    public DrawingPanel(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        dimPlan = null;
        try {
            zoomFactor = new Fraction(1, 2);
            zoomInc = new Fraction(0,1);
        }catch (Exception ignored){}
        posiCam = null;
        posiCamTempo = null;
        backgroundColor = new Color(89, 100, 124);
        addMouseWheelListener(this);
        mouse_pt = null;
        clip = false;
    }

    /**
     * @brief paint les components dans le jpanel
     * @param g: Graphics
     */
    public void paintComponent(Graphics g){
        if (mainWindow != null)
        {
            super.paintComponent(g);
            if (mainWindow.isDarkMode)
                setBackground(backgroundColor);
            else setBackground(Color.WHITE);
            Afficheur drawer;//= new Afficheur(mainWindow.controller, getSize());
            this.update();
            if (mainWindow.controller.isVueDessus()) {
                drawer = new AfficheurPlanSalle(mainWindow.controller, getSize());
            }
            else {
                drawer = new AfficheurElevationCote(mainWindow.controller, getSize());
            }

            try {
                drawer.draw(g,(double)zoomFactor.getDenum()/zoomFactor.getNum(),this.getSize(),posiCam,dimPlan);

            } catch (FractionError e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @brief update dimPlan
     */
    private void update(){
        if (mainWindow.controller.isVueDessus())
            try {
                this.dimPlan = mainWindow.controller.getSalleReadOnly().getDimension();
            }catch (FractionError ignored){}

        else if (mainWindow.controller.isVueCote())
            this.dimPlan = mainWindow.controller.getSelectedCoteReadOnly().getDimension();
    }

    /**
     * @brief update zoomFactor, dimPlan et posiCam
     */
    public void updateParametre(){
        resetZoomFactor();
        if (mainWindow.controller.isVueDessus())
            try {
                this.dimPlan = mainWindow.controller.getSalleReadOnly().getDimension();
            }catch (FractionError ignored){}

        else if (mainWindow.controller.isVueCote())
            this.dimPlan = mainWindow.controller.getSelectedCoteReadOnly().getDimension();

        try{
            this.posiCam = new CoordPouce(this.dimPlan.getX().div(2), this.dimPlan.getY().div(2));
        }catch (Exception ex){
            throw new Error("Erreur dans l'opdate des paramètre");
        }
    }

    /**
     * @brief calcule avec l'emplacement de la sourie la position dans le plan en Pouce
     * @param event :MouseEvent
     * @return CoordPouce de l'emplacement de la sourie
     */
    private CoordPouce coordPixelToPouceAll(MouseEvent event){
        try {

            Pouce posiX = new Pouce(0,this.getSize().width,2);
            posiX.mulRef(zoomFactor);
            posiX.mulRef(new Fraction(2* event.getX(),this.getSize().width).subRef(new Fraction(1,1)));
            posiX.addRef(posiCam.getX());

            Pouce posiY = new Pouce(0,this.getSize().height,2);
            posiY.mulRef(zoomFactor);
            posiY.mulRef(new Fraction(2* event.getY(),this.getSize().height).subRef(new Fraction(1,1)));
            posiY.addRef(posiCam.getY());
            return new CoordPouce(posiX,posiY);
        }catch (Exception ex){
            throw new Error("Erreur dans le calcul de coord");
        }
    }

    /**
     * @brief calcule la position de la souris dans le plan. Si click a l'extérieur du plan return null
     * @param event :MouseEvent
     * @return CoordPouce valide
     */
    public CoordPouce coordPixelToPouce(MouseEvent event){
        try {
            //System.out.println(dimPlan + " " + zoomFactor + getSize() + posiCam);

            CoordPouce coord = coordPixelToPouceAll(event);
            coord.round(128);
            double x = coord.getX().toDouble();
            double y = coord.getY().toDouble();
            if (x >= 0 && x <= dimPlan.getX().toDouble() && y >= 0 && y <= dimPlan.getY().toDouble())
                return coord;
            else
                return null;
        }catch (Exception ex){
            throw new Error("Erreur dans le calcul de coord");
        }
    }

    /**
     * @brief getteur de l'attribut mainWindow()
     * @return this.mainWindow
     */
    public MainWindow getMainWindow() {
        return mainWindow;
    }

    /**
     * @brief setteur du mainWindow
     * @param mainWindow: MainWindow
     */
    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * @brief getteur du facteurZoom
     * @return this.zoomFactor
     */
    public Fraction getZoomFactor() {
        return zoomFactor;
    }

    /**
     * @brief diminue le zoomFacteur par zoomInc
     */
    private void addZoomFactor() {
        if (zoomFactor.toDouble() <= 0.1){
            this.zoomFactor.setNum(1);
            this.zoomFactor.setDenum(10);
        }else {
            zoomFactor.subRef(zoomInc);
        }

    }

    /**
     * @brief augmente le zoomFacteur par zoomInc
     */
    private void subZoomFactor(){

        if (zoomFactor.toDouble() >= 5) {
            this.zoomFactor.setNum(5);
            this.zoomFactor.setDenum(1);
        } else {
            zoomFactor.addRef(zoomInc);
        }
    }

    /**
     * @brief remet le zoomFactor a 2 == 50%
     */
    public void resetZoomFactor() {
        this.zoomFactor.setNum(1);
        this.zoomFactor.setDenum(2);
    }

    /**
     * @brief set le zoomFactor par un poucentage
     * @param zoomFactor:int pourcentage de zoom
     */
    public void setZoomFactor(int zoomFactor){
        this.zoomFactor.setDenum(zoomFactor);
        this.zoomFactor.setNum(100);
        this.zoomFactor.simplifier();
    }

    /**
     * @brief calcule le zoomInc par rapport au zoom
     */
    private void calculZoomInc(){
        try {
            zoomInc = zoomFactor.mul(zoomFactor).mulRef(new Fraction(1, 10)).addRef(new Fraction(1,100)).round(1024);
        }catch (FractionError ignored){}
    }


    /**
     * @brief mouse drag fonction
     * @param e:MouseMotionEvent
     */
    public void moved(MouseEvent e){
        //clock++;
        if (clip) {
            Fraction zoom = zoomFactor.copy();
            Fraction dx = zoom.mul(mouse_pt.x-e.getX());
            Fraction dy = zoom.mul(mouse_pt.y-e.getY());
            posiCam.setX(posiCamTempo.getX().add(dx));
            posiCam.setY(posiCamTempo.getY().add(dy));

            //mouse_pt = cord;
            //clock = 0 ;
            this.repaint();
        }

    }

    public void press(MouseEvent e){
        //System.out.println("Cliped");
        clip = true;
        mouse_pt = e.getPoint();
        posiCamTempo = new CoordPouce(posiCam.getX().copy(), posiCam.getY().copy());
    }

    public void release(MouseEvent e){
        moved(e);
        //System.out.println("Uncliped");
        clip = false;
        mouse_pt = null;
        posiCamTempo = null;

    }

    /**
     * @brief event du mouseWheel
     * @param e:MouseWheelEvent
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(e.getWheelRotation() < 0) {
            calculZoomInc();
            addZoomFactor();

            try {
                if (zoomFactor.toDouble() == 5) {
                    this.posiCam = new CoordPouce(this.dimPlan.getX().div(2), this.dimPlan.getY().div(2));
                }else if (zoomFactor.toDouble() > 0.1) {

                    Pouce x = new Pouce(0,getSize().width,2);
                    x.mulRef(zoomInc);
                    x.mulRef(new Fraction(2*e.getX(),getSize().width).subRef(1));
                    this.posiCam.getX().addRef(x);

                    Pouce y = new Pouce(0,getSize().height,2);
                    y.mulRef(zoomInc);
                    y.mulRef(new Fraction(2*e.getY(),getSize().height).subRef(1));
                    this.posiCam.getY().addRef(y);
                }
            }catch (FractionError | PouceError ignored){}

            this.repaint();

        }
        else{
            //Dezoom
            calculZoomInc();
            subZoomFactor();
            if (zoomFactor.toDouble() >= 3)
                try {
                    this.posiCam = new CoordPouce(this.dimPlan.getX().div(2), this.dimPlan.getY().div(2));
                }catch (PouceError ignored){}
            this.repaint();
        }
    }

}
