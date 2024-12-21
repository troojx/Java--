package frame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 单例设计模式，确保一个类只有一个对象
 * 饿汉式：拿对象时，对象早就创建好了
 * 私有化构造器，定义一个变量记住对象，定义一个类方法返回对象
 */
public class Server {
    private static Server instance = null;
    private ServerSocket serverSocket;

    private Server() throws Exception {
        serverSocket = new ServerSocket(12345);
        System.out.println("服务端已经启动，等待客户端连接...");

        while (true){ //持续接受新的客户端连接
            Socket socket = serverSocket.accept();
            new Thread(new ServerHandler(socket)).start();//为每个连接创建一个新的处理线程
        }
    }

    public static Server getInstance() throws Exception{
        if (instance == null){
            instance = new Server();
        }
        return instance;
    }

    public static void main(String[] args) throws Exception {
        Server.getInstance();
    }
}
