package CompuChiqui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.UndoManager;

public final class JTextFieldPopup extends javax.swing.JTextField implements MouseListener, KeyListener, FocusListener{

    private JComponent campodetexto;
    private JComponent arriba, abajo;
    private Dimension d = new Dimension(50, 20);
    private String placeholder = "";
    private Color phColor = new Color(0, 0, 0);
    private boolean validar = false;
    private boolean band = true;
    private boolean SOLONUMEROS = false;
    JPopupMenu menuopciones = new JPopupMenu();
    JMenuItem copiar, pegar, cortar;

    /**
     * Constructor de clase
     */
    public JTextFieldPopup() {
        super();
        setSize(d);
        setPreferredSize(d);
        setVisible(true);
        setMargin(new Insets(3, 6, 3, 6));
        //atento a cambios 
        getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                band = (getText().length() > 0) ? false : true;
            }

            @Override
            public void insertUpdate(DocumentEvent e) {

                band = false;
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
            }

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
            public void actionPerformed(ActionEvent e) {
                cut();
            }
        });
        menuopciones.add(copiar);
        menuopciones.add(pegar);
        menuopciones.add(cortar);
        this.setComponentPopupMenu(menuopciones);
        addKeyListener(this);
        addFocusListener(this);
    }

    public JTextFieldPopup(String place) {
        setPlaceholder(place);
        setMargin(new Insets(3, 6, 3, 6));
        setColumns(30);
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public Color getPhColor() {
        return phColor;
    }

    public void setPhColor(Color phColor) {
        this.phColor = phColor;
    }

    public void setCampodetexto(JComponent campodetexto) {
        this.campodetexto = campodetexto;
    }
    
    public boolean isValidar() {
        return validar;
    }

    public void setValidar(boolean validar) {
        this.validar = validar;
    }
    
    public void setArriba(JComponent arriba) {
        this.arriba = arriba;
    }

    @Override
    public String getText(){
        if(isValidar())
            return (null==super.getText()||super.getText().isEmpty())?"0":super.getText();
        else
            return super.getText();
    }

    public void setAbajo(JComponent abajo) {
        this.abajo = abajo;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //color de placeholder 
        g.setColor(new Color(phColor.getRed(), phColor.getGreen(), phColor.getBlue(), 90));
        //dibuja texto
        g.drawString((band) ? placeholder : "",
                getMargin().left,
                (getSize().height) / 2 + getFont().getSize() / 2);
    }

    @Override
    public void keyTyped(KeyEvent e){
        if(isSOLONUMEROS()){
            char t = e.getKeyChar();
            if (Character.isLetter(t)) {
                e.consume();
                getToolkit().beep();
            }
        }        
        if (campodetexto != null) {
            if (e.getKeyChar() == 10){
                if(isValidar()){
                    if(!this.getText().isEmpty()){
                        campodetexto.grabFocus();
                        this.setBorder(campodetexto.getBorder());
                    }else{
                        this.setBorder(new LineBorder(new Color(209,72,54), 2));
                    }
                }else{
                    campodetexto.grabFocus();
                }                
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP){
            if(arriba!=null){
                arriba.grabFocus();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            if(abajo!=null){
                abajo.grabFocus();
            }
        }
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
    
    @Override
    public void focusGained(FocusEvent e) {
        setBorder(new LineBorder(new Color(0,102,255), 2));
    }

    @Override
    public void focusLost(FocusEvent e) {
        setBorder(new LineBorder(Color.gray, 1));
    }

    public Double getDouble() {
        try {
            return Double.parseDouble(this.getText());
        } catch (Exception e) {
            return 0.0;
        }
    }

    public int getInt() {
        try {
            return Integer.parseInt(this.getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public long getLong() {
        try {
            return Long.parseLong(this.getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public float getFloat() {
        try {
            return Float.parseFloat(this.getText());
        } catch (Exception e) {
            return 0;
        }
    }         

    public boolean isSOLONUMEROS() {
        return SOLONUMEROS;
    }

    public void setSOLONUMEROS(boolean SOLONUMEROS) {
        this.SOLONUMEROS = SOLONUMEROS;
    }

}
