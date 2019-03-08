/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disreputor;

import java.net.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

/**
 *
 * @author happy
 */
public class Net {

    //port
    final private int port;

    //output stream
    private BufferedOutputStream out;

    //bridge
    private ImageBridge image_bridge;

    //main window
    private MainWindow main_window;

    /**
     *
     * @param port
     */
    public Net(int port) {
        this.port = port;
    }

    /**
     *
     * @throws Exception
     */
    public void run() throws Exception {
        //initial server socket
        ServerSocket server = new ServerSocket(this.port);
        System.out.println("start server, port=" + this.port);
        //set title available
        this.main_window.setTitleAvailable();
        while (true) {
            try {
                //get a client
                Socket client = server.accept();
                System.out.println("accept client");
                this.main_window.setTitleConnection();
                //in out stream
                BufferedInputStream in = new BufferedInputStream(client.getInputStream());
                out = new BufferedOutputStream(client.getOutputStream());

                //消息缓存区
                byte[] buffer = new byte[1024 * 1024 * 10];
                //图片缓存
                byte[] img_buffer = null;
                //当前接受图片位置
                int current_point = 0;
                //listen
                while (true) {
                    //reveive
                    int len = in.read(buffer);
                    //对方断开连接
                    if (len == -1) {
                        break;
                    }
                    byte[] msg = new byte[len];
                    System.arraycopy(buffer, 0, msg, 0, len);
                    System.out.println("msg len=" + msg.length);
                    int head_start = this.indexOf(buffer, Protocol.RESPONSE_SEND.getBytes());
                    System.out.println("head start=" + head_start);
                    //合法数据头
                    if (head_start >= 0) {
                        //split method\nlen\nimg...
                        String image_size_str = this.getLength(msg);
                        //to int
                        int image_size = Integer.parseInt(image_size_str);
                        //create image_buffer
                        img_buffer = new byte[image_size];
                        System.out.println("start reveiving image, len=" + image_size);
                        //头长度， +1 为 结尾\n
                        int head_len = Protocol.RESPONSE_SEND.length() + image_size_str.length() + 1;
                        System.out.println("head len=" + head_len);
                        //拷贝部分图片
                        int part_len = len - head_len;
                        if (part_len > 0) {
                            System.out.println("part=" + part_len);
                            //copy
                            System.arraycopy(msg, head_len, img_buffer, current_point, part_len);
                            current_point = current_point + part_len;
                            //show log
                            System.out.println("current received len=" + current_point);
                            this.main_window.setTitlePercent(current_point/img_buffer.length);
                        }
                    } //若不包含，则为剩下部分
                    else {
                        if (img_buffer != null) {
                            //copy
                            System.arraycopy(msg, 0, img_buffer, current_point, msg.length);
                            //update point
                            current_point = current_point + msg.length;
                            System.out.println("current received len=" + current_point);
                            this.main_window.setTitlePercent(current_point/img_buffer.length);
                        }
                    }
                    //如果接收结束，还原图片
                    if (img_buffer != null && current_point >= img_buffer.length) {
                        System.out.println("receive ok");
                        //解码图片
                        ByteArrayInputStream image_stream = new ByteArrayInputStream(img_buffer);
                        BufferedImage img = ImageIO.read(image_stream);
                        this.main_window.setImage(img);
                        this.image_bridge.save(img);
                        //初始化缓存
                        img_buffer = null;
                        current_point = 0;
                    }
                }
                client.close();
            } catch (Exception exc) {
                //exc.printStackTrace();
            } finally {
                System.out.println("client discunnect");
                this.main_window.setTitleAvailable();
            }
        }
    }

    public void sendRequest(){
        try{
            out.write(Protocol.REQUEST_SEND.getBytes());
            out.flush();
        }catch(IOException exc){
            exc.printStackTrace();
        }
    }
    
    /**
     *
     * @param array
     * @param sub
     * @return
     */
    public int indexOf(byte[] array, byte[] sub) {
        String str_array = new String(array);
        String str_sub = new String(sub);
        return str_array.indexOf(str_sub);
    }

    /**
     *
     * @param array
     * @return
     */
    public String getLength(byte[] array) {

        return new String(array).split("\n", 3)[1];

    }

    /**
     * @param image_bridge the image_bridge to set
     */
    public void setImage_bridge(ImageBridge image_bridge) {
        this.image_bridge = image_bridge;
    }

    /**
     * @param main_window the main_window to set
     */
    public void setMain_window(MainWindow main_window) {
        this.main_window = main_window;
    }

}
