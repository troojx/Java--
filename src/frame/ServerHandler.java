package frame;

import util.FileOperateForServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerHandler implements Runnable{
    private Socket socket;      //与客户端建立连接
    private ObjectOutputStream objectOutputStream;  //发数据给客户端
    private ObjectInputStream objectInputStream;   //接受客户端数据
    private static FileOperateForServer fileOperateForServer = new FileOperateForServer();//实例化文件操作类

    public ServerHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            while (true){
                String msg = objectInputStream.readUTF(); //接收消息
                //0:输了  1：ChToEg发送   2：EgToCh发送
                if (msg.equals("0")){
                    //输了
                    break;
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
}
