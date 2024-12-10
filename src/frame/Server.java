package frame;

import util.FileOperateForServer;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;  //监听客户端请求
    private Socket socket;      //与客户端建立连接
    private ObjectOutputStream objectOutputStream;  //发数据给客户端
    private ObjectInputStream objectInputStream;   //接受客户端数据
    private boolean canRun = true;
    private static FileOperateForServer fileOperateForServer = new FileOperateForServer();//实例化文件操作类

    public Server() throws Exception{
        //1.创建ServerSocket的对象，同时为服务器端注册端口，监听9999端口
        serverSocket = new ServerSocket(9999);
        System.out.println("服务端已经启动，等待客户端连接...");

        //2.使用serverSocket对象，调用一个accept方法，等待客户端的连接请求
        socket = serverSocket.accept();

        //3.创建输出输入流，发送对象给客户端/读取客户端发来的信息
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());

        run();
    }

    public void run() {
        try{
            while (canRun){
                String msg = objectInputStream.readUTF(); //接收消息
                //0:输了  1：ChToEg发送   2：EgToCh发送
                if (msg.equals("0")){
                    //输了
                    canRun = false;
                }else if (msg.equals("1")){
                    //发送ChToEg
                    objectOutputStream.writeObject(fileOperateForServer.RandomChToEg());
                    objectOutputStream.flush(); //刷新流
                }else if (msg.equals("2")){
                    objectOutputStream.writeObject(fileOperateForServer.RandomEgToCh());
                    objectOutputStream.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭
            try{
                if (objectOutputStream != null)objectOutputStream.close();
                if (objectInputStream != null) objectInputStream.close();
                if (socket != null) socket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Server();
    }
}