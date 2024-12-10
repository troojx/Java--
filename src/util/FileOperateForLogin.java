package util;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.Properties;

public class FileOperateForLogin {
    private static String fileName = "Login.properties";
    private static Properties pps;

    //静态代码负责载入 Login.properties 中的数据。
    static {
        pps = new Properties();
        FileReader reader = null;
        try{
            reader = new FileReader(fileName);
            pps.load(reader);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"登录文件操作异常");
            System.exit(0);
        }finally {
            try {
                reader.close();
            } catch (Exception e) { }
        }
    }
  public static void getInfoByAccount(String acc){
        String LogInfo = pps.getProperty(acc);
        if (LogInfo!=null) {
            String[] infos = LogInfo.split("#");
            Conf.account = acc;
            Conf.password = infos[0];
            Conf.name = infos[1];
        }
    }

    public static void updateCustomer(String account,String password,String name) throws Exception {
        pps.setProperty(account, password+"#"+name);
        //pps.store(new FileWriter(fileName,true),"");
        listInfo();
    }

    private static void listInfo(){
        PrintStream ps = null;
        try{
            ps = new PrintStream(fileName);
            pps.list(ps);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Login.properties操作异常");
            System.exit(0);
        }finally {
            try {
                ps.close();
            } catch (Exception E) {
            }
        }
    }


    public static void main(String[] args) throws Exception{
        FileOperateForLogin fo= new FileOperateForLogin();
        fo.getInfoByAccount("20240901");
        System.out.println(Conf.account);
        System.out.println(Conf.name);
        System.out.println(Conf.password);

        updateCustomer("20240901","123456","zzz");
    }

}