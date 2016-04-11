package CompuChiqui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.UndoManager;

public final class JTextFieldPopup extends javax.swing.JTextField implements MouseListener, KeyListener{
    
    private JTextField campodetexto;
    private Dimension d = new Dimension(50,20);
    private String placeholder = "";  
    private Color phColor= new Color(0,0,0);
    private boolean band = true;
    JPopupMenu menuopciones = new JPopupMenu();
    JMenuItem copiar, pegar, cortar;

    /** Constructor de clase */
    public JTextFieldPopup(){
        super();
        setSize(d);
        setPreferredSize(d);
        setVisible(true);
        setMargin( new Insets(3,6,3,6));
        //atento a cambios 
        getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                band = (getText().length()>0) ? false:true ;
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                
                band = false;
            }

            @Override
            public void changedUpdate(DocumentEvent de) {}

        });
        
        UndoManager manager = new UndoManager();
        getDocument().addUndoableEditListener(manager);
        
        Action undoAction = new Deshacer(manager);
        Action redoAction = new Rehacer(manager);
        
        //ASIGNAR EVENTOS DE TECLADO CTRL-Z Y CTRL-Y - DESHACER Y REHACER
        registerKeyboardAction(undoAction, KeyStroke.getKeyStroke(
        KeyEvent.VK_Z, InputEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);
        registerKeyboardAction(redoAction, KeyStroke.getKeyStroke(
        KeyEvent.VK_Y, InputEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);
        
        copiar = new JMenuItem("Copiar", new ImageIcon(getClass().getResource("/Imagenes/copiar.png")));
        copiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copy();
            }
        });
        pegar = new JMenuItem("Pegar", new ImageIcon(getClass().getResource("/Imagenes/pegar.png")));
        pegar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paste();
            }
        });
        cortar = new JMenuItem("Cortar", new ImageIcon(getClass().getResource("/Imagenes/cortar.png")));
        cortar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cut();
            }
        });
        menuopciones.add(copiar);
        menuopciones.add(pegar);
        menuopciones.add(cortar);
        this.setComponentPopupMenu(menuopciones);
        campodetexto = getCampodetexto();
        addKeyListener(this);
    }
    
    public JTextFieldPopup(String place){
        setPlaceholder(place);
        setMargin( new Insets(3,6,3,6));
        setColumns(30);
    }

    public void setPlaceholder(String placeholder)
    {
        this.placeholder=placeholder;
    }

    public String getPlaceholder()
    {
        return placeholder;
    }

    public Color getPhColor() {
        return phColor;
    }

    public void setPhColor(Color phColor) {
        this.phColor = phColor;
    }   
    
     public JTextField getCampodetexto() {
        return campodetexto;
    }

    public void setCampodetexto(JTextField campodetexto) {
        this.campodetexto = campodetexto;
    }
    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //color de placeholder 
        g.setColor( new Color(phColor.getRed(),phColor.getGreen(),phColor.getBlue(),90));
        //dibuja texto
        g.drawString((band)?placeholder:"",
                     getMargin().left,
                     (getSize().height)/2 + getFont().getSize()/2 );
      }

    @Override
    public void keyTyped(KeyEvent e){
        if(campodetexto!=null){
            if(e.getKeyChar()==10 ){
                campodetexto.grabFocus();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
