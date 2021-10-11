package com.mabo.controller;

import com.alibaba.fastjson.JSONObject;
import com.mabo.utils.ExcuteMethodUtil;
import com.mabo.utils.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author mabo
 * @Description   读取请求对象，根据不同请求，做出反应
 */
public class Controller {
    private LogUtil log=new LogUtil();

    public JSONObject getControllerMap(String message,Socket socket){
        try {
            String[] s = message.split(" ");
            String result=s[1];
            Map map= null;
            String path= null;
            String pathAndParam[]=result.split("\\?");
            map = new HashMap();
            path = pathAndParam[0];
            map.put("path",path);
            if (pathAndParam.length>1){
                String parame[]=null;
                if (pathAndParam[1].contains("&")){
                    parame=pathAndParam[1].split("&");
                }
                if (pathAndParam[1].contains("&&")){
                    parame=pathAndParam[1].split("&&");
                }
                else {
                    String datas[]=pathAndParam[1].split("=");
                    if (datas.length>1){
                        map.put(datas[0],datas[1]);
                    }
                }
                try {
                    for (String a: parame) {
                        String datas[]=a.split("=");
                        if (datas.length>1){
                            map.put(datas[0],datas[1]);
                        }
                    }
                } catch (Exception e) {
                }
            }
            Object o = ExcuteMethodUtil.excuteMethod(path, map,socket);
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(o);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(message+"接受的请求解析错误");
        }
        return null;
    }

    public void receiveHttpRequestController( Socket socket ){
        log.info("客户"+socket.toString()+"已连接");
        BufferedReader bufferedReader = null;//获取输入流(请求)
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(),"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while (true) {
            try {
                if (!((line = bufferedReader.readLine()) != null
                                && !line.equals(""))) break;
            } catch (IOException e) {
                e.printStackTrace();
            }//得到请求的内容，注意这里作两个判断非空和""都要，只判断null会有问题
            stringBuilder.append(line).append("<br>");
        }
        String result = stringBuilder.toString();
        //对浏览器的转义字符进行解码 否则中文乱码
        result = URLDecoder.decode(result);
//        log.info("HttpReceiveUtil接收到的请求为:"+result);
        Controller controller=new Controller();
        //开始接收请求并且处理
        JSONObject json = controller.getControllerMap(result,socket);
        //业务处理结束，返回数据
        PrintWriter printWriter = null;//这里第二个参数表示自动刷新缓存
        try {
            printWriter = new PrintWriter(
                    socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter.println("HTTP/1.1 200 OK");
        printWriter.println("Content-Type:text/html;charset=utf-8");
        printWriter.println("Access-Control-Allow-Origin: *");
        printWriter.println();
        //请求头结束

        //返回json数据
        printWriter.print(json);
//        log.info("返回数据"+json);
        printWriter.close();
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
