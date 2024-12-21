package frame;

import util.FileOperateForCustomer;
import util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GamePanel extends JPanel {
    //protected:当前类和所有子类都可以访问
    protected int currentLife = 10;  //生命值
    protected JProgressBar pbLife = new JProgressBar(0,10); //血条范围从0-10
    protected JLabel lbLife = new JLabel(); //显示生命值Label
    protected JLabel lbTimer = new JLabel();//显示计时器的标签

    protected Socket socket = null;
    protected ObjectOutputStream objectOutputStream;  //发数据给客户端
    protected ObjectInputStream objectInputStream;   //接受客户端数据
    protected FileOperateForCustomer fileOperateForCustomer = new FileOperateForCustomer();

    //private boolean canRun = true;
    protected static boolean checkFlag;
    private int timeLeft = 10;//剩余时间
    protected boolean TimerIsZero = false;
    protected Timer timer;//Swing Timer

    //两种界面共同部分
    public GamePanel() throws Exception {  //构造器
        //super(father,"游戏界面",true);  //new
        //JFrame frame = new JFrame("游戏界面");

        this.setLayout(null);  //不使用布局管理器
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUIUtil.toCenter(this);
        this.setBackground(Color.white);
        this.setSize(400,600);
        this.setVisible(true);

        this.add(pbLife);
        pbLife.setValue(currentLife);  //设置初始血量
        pbLife.setForeground(Color.RED);
        pbLife.setBackground(Color.LIGHT_GRAY);
        pbLife.setBounds(40,30,180,15);

        this.add(lbLife);
        lbLife.setFont(new Font("SimSun",Font.BOLD,14));
        lbLife.setBounds(250,30,100,20);
        lbLife.setForeground(Color.RED);
        lbLife.setText("当前分数：" + currentLife);

        //设置计时器标签
        this.add(lbTimer);
        lbTimer.setFont(new Font("Times New Roman",Font.BOLD,16));
        lbTimer.setForeground(Color.BLACK);

        // 将计时器标签放在血条下方并居中
        int timerWidth = 50;  // 计时器标签的宽度
        int timerHeight = 30; // 计时器标签的高度
        int timerX = 175; // 居中
        int timerY = pbLife.getY() + pbLife.getHeight() + 15;  // 放在血条下方，距离血条有一定间距
        lbTimer.setBounds(timerX, timerY, timerWidth, timerHeight);

        try{
            socket = new Socket("127.0.0.1",12345);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            JOptionPane.showMessageDialog(this,"做好准备！点击确定！");
        }catch (Exception e){
            javax.swing.JOptionPane.showMessageDialog(this,"游戏异常退出！服务器未能连接游戏界面！");
            System.exit(0);
        }

        // 初始化计时器
        initTimer();
        timer.start();
    }

    public void initTimer(){
        //TimerIsZero = false;
        timeLeft = 10;
        if (timer == null) {
            timer = new Timer(1000, e -> {
                if (timeLeft >= 0) {
                    lbTimer.setText(String.valueOf(timeLeft)); // 实时更新显示
                    timeLeft--;
                } else {
                    //TimerIsZero = true;
                    timer.stop(); // 倒计时结束时停止计时器
                    checkInput();  //倒计时为0的时候也触发检查事件
                }
            });
        }
    }

    protected void checkInput(){
        //让两个子类分别重写
        //检查：更新得分并保存单词，显示提示
    }

    public void stopTimer(){
        timer = null;//销毁
    }


    public void checkAndHandelFail() {
        //init();
        try {
            if (currentLife <= 0) {
                //计时器线程结束
                stopTimer();
                objectOutputStream.writeObject("0");  //给服务器发送失败
                objectOutputStream.flush();
                JOptionPane.showMessageDialog(this, "生命值耗尽，游戏失败！再接再厉哦~");
                //this.;  //关闭对话框
                try{     //关闭
                    if (objectOutputStream != null)objectOutputStream.close();
                    if (objectInputStream != null) objectInputStream.close();
                    if (socket != null) socket.close();
                    if (fileOperateForCustomer != null) fileOperateForCustomer.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(this, "检查时发生了错误，游戏无法继续！");
            e.printStackTrace();
        }
    }

    public void updateLifeDisplay() {
        pbLife.setValue(currentLife);
        lbLife.setText("当前分数：" + currentLife);
    }

}
