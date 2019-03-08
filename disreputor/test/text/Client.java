/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package text;
import disreputor.client.*;
/**
 *
 * @author happy
 */
public class Client {
    public static void main(String[] args) throws Exception{
        //client window
                ClientWindow c = new ClientWindow();
                //client net
                ClientNet net = new ClientNet();
                net.setWindow(c);
                //start client
                
        net.run("localhost",6677);
    }
}
