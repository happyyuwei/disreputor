/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disreputor.client;
import disreputor.Protocol;
import java.io.*;
import java.net.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.ImageIO;
/**
 *
 * @author happy
 */
public class ClientNet {
    
    //robot to screenshot
    final private Robot robot;
    //screen size
    final private Rectangle rectangle;
    //output stream
    private BufferedOutputStream out;
    //logo
    private ClientWindow window;
    
    
    /**
     * 
     * @throws Exception 
     */
    public ClientNet() throws Exception{
        //capture robot
        this.robot=new Robot();
        //screen rectangle
        this.rectangle=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    }
    
    /**
     * 
     * @param ip
     * @param port 
     */
    public void run(String ip, int port){
        while(true){
            try{
                //core connect
                this.runCore(ip, port);
            }catch(Exception exc){
                this.window.setEmptyIcon();
                System.out.println("connect reset, trying again in 5 seconds.");
                //5秒重新请求一次
                try{
                    Thread.sleep(5000);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 
     * @param ip
     * @param port
     * @throws Exception 
     */
    public void runCore(String ip, int port) throws Exception{
        //create client 
        Socket client=new Socket(ip,port);
        System.out.println("connected");
        this.window.setConnectIcon();
        //get input stream
        BufferedInputStream in=new BufferedInputStream(client.getInputStream());
        //output stream
        this.out=new BufferedOutputStream(client.getOutputStream());
        //receive buffer
        byte[] buffer=new byte[1024];
        //receive
        while(true){
            int len=in.read(buffer);
            String request_order=new String(buffer,0,len);
            System.out.println("request order");
            //receive the request send
            if(request_order.equals(Protocol.REQUEST_SEND)){
                System.out.println("shotscreen");
                //screenshot
                this.screenshot();
            }
        }
    }
    
    /**
     * 截取屏幕
     * @throws Exception 
     */
    public void screenshot() throws Exception{
        //screen
        BufferedImage screen=this.robot.createScreenCapture(this.rectangle);
        //send
        this.send(screen);
        
    }
    /**
     * 发送图片
     * @param img
     * @throws IOException 
     */
    public void send (BufferedImage img) throws IOException{
        //image to array
        ByteArrayOutputStream arry_stream=new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", arry_stream);
        byte[] bits=arry_stream.toByteArray();
        //分装，加入数据头
        byte[] head=Protocol.RESPONSE_SEND.getBytes();
        //获取图片大小
        byte[] len=String.valueOf(bits.length+"\n").getBytes();
        //创建数组
        byte[] msg=new byte[head.length+len.length+bits.length];
        //添加头
        System.arraycopy(head, 0, msg, 0, head.length);
        System.arraycopy(len, 0, msg, head.length, len.length);
        //添加图片
        System.arraycopy(bits, 0, msg, head.length+len.length, bits.length);
        //发送数据流
        out.write(msg);
        out.flush();
        System.out.println("send ok,img len="+bits.length);
    }

    /**
     * @param window the window to set
     */
    public void setWindow(ClientWindow window) {
        this.window = window;
    }
}
