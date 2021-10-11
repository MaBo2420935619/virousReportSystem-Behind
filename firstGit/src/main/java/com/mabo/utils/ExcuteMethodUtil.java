package com.mabo.utils;

import com.mabo.manager.SystemLogManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * @Author mabo
 * @Description   用于反射执行哪个方法的工具类
 * @Description   更具方法名称，执行对应方法，传入参数都必须装在map中
 */

public class ExcuteMethodUtil {
    private static LogUtil log=new LogUtil();
    private static   PropertyUtil properties=new PropertyUtil();
    private  static String[] requestClassName;
    private static ApplicationContext context;
    private static String startLogSystem ;
    private static SystemLogManager systemLogManager;

    public void setSystemLogManager(SystemLogManager systemLogManager) {
        this.systemLogManager = systemLogManager;
    }

    static {
        startLogSystem=  properties.get("config.properties", "startLogSystem");
        String requestClassNames = properties.get("request.properties", "requestClassName");
        requestClassName = requestClassNames.split("&");
        //获取spring工厂内的该类对象
        context=new ClassPathXmlApplicationContext("spring-manager.xml");
    }
    public static Object excuteMethod(String path, Map map, Socket socket){
        String methodName = properties.get("request.properties", path);
        Object invoke=null;
        boolean noThisMethod=true;
        for (String className: requestClassName) {
            // 包名+类名
            Class clazz = null;
            try {
                //根据类名全路径找到class
                String beanClass = "com.mabo.controller."+className;
                clazz = Class.forName(beanClass);
                //获取所在类的对象
                Object obj = null;
                try {
                    className= toLowerCaseFirstOne(className);
                    obj = context.getBean(className);
                    //获取方法
                    Method method = null;
                    long startTime = TimeUtil.startTime();
                    try {
                        method = clazz.getMethod(methodName,Map.class);
                        try {
                            //执行方法
                            invoke = method.invoke(obj, map);
                            long diffMs = TimeUtil.diffMs(startTime);
                            noThisMethod=false;
                            StringBuilder msg=new StringBuilder();
                            msg.append("com.mabo.");
                            msg.append(className);
                            msg.append(".");
                            msg.append(methodName);
                            msg.append("()方法执行成功,用时");
                            msg.append(diffMs);
                            msg.append("ms");
                            log.info(msg.toString());
                            if (startLogSystem.equals("true")){
                                systemLogManager.setSystemLog(socket,path,"成功",diffMs);

                            }
                            break;
                        } catch (IllegalAccessException e) {
                            long diffMs = TimeUtil.diffMs(startTime);
//                            e.printStackTrace();
                            StringBuilder message = new StringBuilder();
                            message.append("com.mabo.");
                            message.append(className);
                            message.append(".");
                            message.append(methodName);
                            message.append("()方法执行成功,用时");
                            message.append(diffMs);
                            message.append("ms");
                            System.out.println(message);
                            StringBuilder exception = new StringBuilder();
                            exception.append("ExceptionMessageFrom:    ");
                            exception.append(e.getMessage());
                            System.out.println(exception);
                            log.error(e.getMessage());
                            log.info(exception.toString());
                            //是否启用日志系统
                            if (startLogSystem.equals("true")){
                                systemLogManager.setSystemLog(socket,path,"失败",diffMs);
                            }
                        } catch (InvocationTargetException e) {
                        }
                    } catch (NoSuchMethodException e) {
                    }
                }
                catch (Exception e) {
                }
            } catch (ClassNotFoundException e) {
            }
        }
        if (noThisMethod){
            StringBuilder message = new StringBuilder();
            message.append(methodName);
            message.append("()方法不存在");
            System.out.println(message);
            log.error(message.toString());
        }
        return  invoke;
    }
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
