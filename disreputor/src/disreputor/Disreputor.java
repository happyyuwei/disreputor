/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disreputor;

import java.util.Arrays;
import java.text.*;
import disreputor.client.*;

/**
 *
 * @author happy
 */
public class Disreputor {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here

//        
//        
//Net net=new Net(6677);
//ImageBridge bridge=new ImageBridge(6677);
//window.setSypListener(net);
//net.setImage_bridge(bridge);
//net.setMain_window(window);
//window.init();
//net.run();
        if (args[0].equals("-s")) {
            if (args.length >= 2) {
                //parse port
                int port = Integer.parseInt(args[1]);
                //main window
                MainWindow window = new MainWindow(port);
                //create net instance
                Net net = new Net(port);
                ImageBridge bridge = new ImageBridge(port);
                //set spy listener
                window.setSypListener(net);
                //set bridge
                net.setImage_bridge(bridge);
                //set window
                net.setMain_window(window);
                //initial window
                window.init();
                //run instance
                net.run();
            }
        } else if (args[0].equals("-c")) {
            if (args.length >= 3) {

                //ip and port
                String ip = args[1];
                int port = Integer.parseInt(args[2]);
                while (true) {
                    //所有错误会在这里捕获并重新开始
                    try {
                        //client window
                        ClientWindow c = new ClientWindow();
                        //client net
                        ClientNet net = new ClientNet();
                        net.setWindow(c);
                        //start client
                        net.run(ip, port);
                    } catch (Exception exc) {
                        exc.printStackTrace();
                    }
                    //3秒重新开始
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}
