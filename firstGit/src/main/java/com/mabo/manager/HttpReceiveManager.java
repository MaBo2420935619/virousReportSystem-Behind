package com.mabo.manager;

import com.mabo.controller.Controller;
import com.mabo.utils.LogUtil;
import com.mabo.utils.PropertyUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author mabo
 * @Description   用来接受http请求的工具类
 */
public class HttpReceiveManager {
    private ThreadPoolTaskExecutor taskExecutor;
    private  static LogUtil log=new LogUtil();
    private static ServerSocket serverSocket;
    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    /**
     * @Author mabo
     * @Description   初始化服务器socket
     */
    static {
        ApplicationContext context=new ClassPathXmlApplicationContext("spring-manager.xml");
        PropertyUtil propertyUtil = (PropertyUtil) context.getBean("propertyUtil");
        String serverPort =propertyUtil.get("config.properties", "serverPort");
        int port = Integer.parseInt(serverPort);
        try {
            serverSocket = new ServerSocket(port);
            log.info(port+"端口服务启动成功");
        } catch (IOException e) {
            e.printStackTrace();
            log.error(port+"已经被占用"+"服务启动失败");
        }
    }

    /**
     * @Author mabo
     * @Description   启动接受http请求
     */
    public  void startServer(){

        while (true) {//一直监听，直到受到停止的命令
            Socket socket = null;
            try {
                socket = serverSocket.accept();//如果没有请求，会一直hold在这里等待，有客户端请求的时候才会继续往下执行
                Socket finalSocket = socket;
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Controller controller=new Controller();
                        controller.receiveHttpRequestController(finalSocket);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
