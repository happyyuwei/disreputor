/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disreputor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

/**
 *
 * @author happy
 */
public class MainWindow extends JFrame {

    //app name
    final static public String APP_NAME = "Disreputor";
    final static private String LABEL_FORMAT = "%s（%d） -  %s";

    //available state
    final static public String STATE_AVAILABLE = "空闲";
    //connect state
    final static public String STATE_CONNECTION = "连接";
    //

    //image label
    final private JLabel image_label;

    //button area
    final private JButton last_image_button;
    final private JButton next_image_button;
    final private JButton spy_button;

    //logo image 
    final private Image avail_label;
    final private Image load_label;
    final private Image connect_label;

    //image icon
    private ImageIcon avail_icon;
    private ImageIcon load_icon;
    private ImageIcon connect_icon;
    
    //current image
    private Image current_img;

    //port
    final private int port;

    /**
     *
     * @param port
     * @throws java.lang.Exception
     */
    public MainWindow(int port) throws Exception {
        super(String.format(LABEL_FORMAT, APP_NAME, port, STATE_AVAILABLE));
        this.port = port;
        //set size
        super.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        //basic proporty
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //look and feel
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //layout
        super.setLayout(new BorderLayout());

        //main panel 
        this.image_label = new JLabel("", JLabel.CENTER);

        //console panel
        JPanel console_panel = new JPanel();
        console_panel.setLayout(new GridLayout(1, 3));
        //BUTTON FONT
        Font button_font = new Font(Font.DIALOG, Font.BOLD, 20);
        //initila button
        this.last_image_button = new JButton("上一张");
        this.last_image_button.setFont(button_font);
        this.next_image_button = new JButton("下一张");
        this.next_image_button.setFont(button_font);
        this.spy_button = new JButton("窥探");
        this.spy_button.setFont(button_font);
        //add to panel
        console_panel.add(this.last_image_button);
        console_panel.add(this.next_image_button);
        console_panel.add(this.spy_button);

        //add to main
        super.add(this.image_label, BorderLayout.CENTER);
        super.add(console_panel, BorderLayout.SOUTH);

        //other kinds of constructing
        this.avail_label = ImageIO.read(new File("./drawable/available.png"));
        this.load_label = ImageIO.read(new File("./drawable/loading.png"));
        this.connect_label = ImageIO.read(new File("./drawable/connect.png"));
        super.setVisible(true);
//        super.addComponentListener(new ComponentAdapter(){
//            @Override
//            public void componentResized(ComponentEvent e){
//                
//            }         
//        });
    }
    
    public void setSypListener(Net net){
        //add button listener
        this.spy_button.addActionListener((ActionEvent e) -> {
            net.sendRequest();
        });
    }

    /**
     * initial window
     */
    public void init() {
        //available logo
        this.load_icon = new ImageIcon(this.load_label.getScaledInstance(this.image_label.getWidth(), this.image_label.getHeight(), Image.SCALE_SMOOTH));
        this.avail_icon = new ImageIcon(this.avail_label.getScaledInstance(this.image_label.getWidth(), this.image_label.getHeight(), Image.SCALE_SMOOTH));
        this.connect_icon = new ImageIcon(this.connect_label.getScaledInstance(this.image_label.getWidth(), this.image_label.getHeight(), Image.SCALE_SMOOTH));
    }
    
    /**
     * 
     * @param img 
     */
    public void setImage(BufferedImage img){
        //change size
        this.current_img=img.getScaledInstance(this.image_label.getWidth(), this.image_label.getHeight(), Image.SCALE_SMOOTH);
        //add
        this.image_label.setIcon(new ImageIcon(this.current_img));
        //SET TITLE
        super.setTitle(String.format(LABEL_FORMAT, APP_NAME, port, STATE_CONNECTION));
    }

    /**
     *
     */
    public void setTitleAvailable() {
        //set title
        super.setTitle(String.format(LABEL_FORMAT, APP_NAME, port, STATE_AVAILABLE));
        this.image_label.setIcon(this.avail_icon);
    }

    /**
     *
     */
    public void setTitleConnection() {
        super.setTitle(String.format(LABEL_FORMAT, APP_NAME, port, STATE_CONNECTION));
        this.image_label.setIcon(this.connect_icon);
    }
    
    /**
     * 
     */
    public void setImageLoading() {
        this.image_label.setIcon(this.load_icon);
    }
    
    /**
     * 
     * @param percent 
     */
    public void setTitlePercent(int percent){
        super.setTitle(String.format(LABEL_FORMAT, APP_NAME, port, STATE_CONNECTION)+String.format(" -spying（%d%%）", percent));
    }

}
