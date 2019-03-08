/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disreputor.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;

/**
 *
 * @author happy
 */
public class ClientWindow extends JWindow {

    final private JLabel label;
    final private double width_percent = 0.1;
    final private double height_percent = 0.1;

    final private ImageIcon empty_icon;
    final private ImageIcon connect_icon;

    /**
     *
     * @throws Exception
     */
    public ClientWindow() throws Exception {
        super();
        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        //size
        int width = (int) (screen_size.width * width_percent);
        int height = (int) (screen_size.height * height_percent);
        //bounds
        super.setBounds(height, height, width, height);
        super.setAlwaysOnTop(true);
        //look and feel
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //set background
        super.setBackground(new Color(255, 255, 255, 0));
        //layout
        super.setLayout(new BorderLayout());

        //label
        label = new JLabel("", JLabel.CENTER);
        //create icon
        this.empty_icon = new ImageIcon(ImageIO.read(new File("./drawable/bat-empty.png")).getScaledInstance(width, height, Image.SCALE_SMOOTH));
        this.connect_icon = new ImageIcon(ImageIO.read(new File("./drawable/bat-connect.png")).getScaledInstance(width, height, Image.SCALE_SMOOTH));
        //empty init
        this.label.setIcon(empty_icon);

        //add to panel
        super.add(this.label, BorderLayout.CENTER);
        super.setVisible(true);

        //menu
        JPopupMenu menu = new JPopupMenu();
        //close item
        JMenuItem close_item = new JMenuItem("关闭");
        //close listener
        close_item.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        //add to menu
        menu.add(close_item);
        //listener
        this.label.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                //鼠标右键,弹出菜单
                if (e.getButton() == 3) {
                    menu.show(label, e.getX(), e.getY());
                }
            }  
        });
        //drag window
        this.label.addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e){
                ClientWindow.super.setLocation(e.getX(), e.getYOnScreen());
            }
        });
    }
    
    public void setEmptyIcon(){
        this.label.setIcon(empty_icon);
    }
    
    public void setConnectIcon(){
        this.label.setIcon(connect_icon);
    }

}
