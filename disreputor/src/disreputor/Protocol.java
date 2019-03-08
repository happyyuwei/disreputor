/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disreputor;

/**
 *
 * @author happy
 */
public class Protocol {
    
    //协议格式
    //method\n
    //len\n
    //img
    
    //服务器给客户端发送图片
    final public static String REQUEST_SEND="REQUEST_SEND\n";
    //客户端返回图片
    final public static String RESPONSE_SEND="RESPONSE_SEND\n";
    //客户端提醒服务器
    final public static String ALARM="ALARM\n";
    
}
