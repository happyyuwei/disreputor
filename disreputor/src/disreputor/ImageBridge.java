/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disreputor;
import java.io.*;
import java.util.*;
import java.awt.image.*;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
/**
 *
 * @author happy
 */
public class ImageBridge {
    
    //save dir
    final private String root="./save/";
    
    //port
    final private  String dir;
    
    //date format
    final private SimpleDateFormat date_format=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    
    /**
     * 
     * @param port 
     */
    public ImageBridge(int port){
        this.dir=root+port+"/";
        //get file
        File file=new File(this.dir);
        //以端口号命名文件夹
        if(file.exists()==false){
            file.mkdir();
        }
    }
    
    /**
     * 
     * @param img 
     * @throws java.lang.Exception 
     */
    public void save(BufferedImage img) throws Exception{
        //create file name
        String file=this.date_format.format(new Date());
        file=this.dir+file+".jpg";
        //save image
        ImageIO.write(img, "jpg", new File(file));     
    }
    
    
}
