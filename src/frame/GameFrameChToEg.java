package frame;

import util.ChtoEg;
import util.FileOperateForServer;
import util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class GameFrameChToEg extends GamePanel {
    private static JLabel lbChineseMeaning;
    private static JLabel lbFirstLetter;
    private static JLabel lbLastLetter;
    private static JTextField tfInput;
    private static JLabel lbFeedback;

    private static String CORRECT_ANSWER;
    private static String USER_INPUT;
    private static ChtoEg randomChToEg = new ChtoEg();
    private JFrame frame;
   // private static boolean checkFlag;

    public GameFrameChToEg() throws Exception {
        //设置界面
        super();
        frame = new JFrame("中文补词");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,600);

        GUIUtil.toCenter(frame);
        this.setBounds(0,0,400,600);
        frame.add(this);
        frame.setResizable(false);

        // 显示中文意思
        lbChineseMeaning = new JLabel();
        lbChineseMeaning.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        lbChineseMeaning.setBounds(20, 250, 340, 30);
        lbChineseMeaning.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lbChineseMeaning);


        // 显示首字母提示
        lbFirstLetter = new JLabel();
        lbFirstLetter.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        lbFirstLetter.setBounds(45, 150, 30, 30);
        lbFirstLetter.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lbFirstLetter);

        // 显示尾字母提示
        lbLastLetter = new JLabel();
        lbLastLetter.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        lbLastLetter.setBounds(305, 150, 30, 30);
        lbLastLetter.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lbLastLetter);

        // 输入框
        tfInput = new JTextField();
        tfInput.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        tfInput.setBounds(90, 150, 200, 30);
        tfInput.setHorizontalAlignment(JTextField.CENTER); // 文字居中显示
        this.add(tfInput);

        // 提示反馈标签
        lbFeedback = new JLabel("");
        lbFeedback.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        lbFeedback.setBounds(50, 400, 300, 30);
        lbFeedback.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lbFeedback);

        //增加回车键监听
        tfInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop(); //暂停计时器
                try {
                    checkInput();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        frame.setVisible(true); //最后调用

        new Thread(new Runnable() {         //为游戏
            @Override
            public void run() {
                try{
                    Run();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        //frame.dispose(); //关闭界面
    }

    //检查：更新得分并保存单词，显示提示
    @Override
    protected synchronized void checkInput() {
        CORRECT_ANSWER = randomChToEg.CORRECT_ANSWER;
        USER_INPUT = tfInput.getText();//获取用户输入
        if (USER_INPUT.equalsIgnoreCase("")) {  //没输入
            currentLife--;
            lbFeedback.setText("您没有回答，答案是" + randomChToEg.English);
            fileOperateForCustomer.answerWrong(randomChToEg.Total,false);
        }else if (USER_INPUT.equalsIgnoreCase(CORRECT_ANSWER)){   //答对了
            currentLife++;
            lbFeedback.setText("恭喜回答正确！");
            fileOperateForCustomer.answerCorrect(randomChToEg.Total);
        } else {
            currentLife-=2; //答错了
            lbFeedback.setText("回答错误，答案是" + randomChToEg.English);
            fileOperateForCustomer.answerWrong(randomChToEg.Total,true);
        }
        updateLifeDisplay();  //更新血量
        tfInput.setText("");  //清空输入框

        checkFlag = true; //已经完成检查
        notify(); //通知Run方法继续执行
    }


    public synchronized void Run() throws Exception {
        while (true){
            if (currentLife>0){
                checkFlag = false;  //将检查标志置为false，如果时间到/回答回车 置为true
                objectOutputStream.writeUTF("1");
                objectOutputStream.flush();//向server发送“1” ：server就发送一个类
                randomChToEg = (ChtoEg) objectInputStream.readObject();

                if (randomChToEg == null){
                    System.out.println("题目加载失败，请重试~");
                    continue;
                }

                //测试：
                System.out.println(randomChToEg.Total);

                //设置题目，清空反馈，开始计时
               // lbFeedback.setText("");   刷新时间太快看不到
                lbFirstLetter.setText(randomChToEg.FirstLetter);
                lbLastLetter.setText(randomChToEg.LastLetter);
                lbChineseMeaning.setText(randomChToEg.Chinese);
                tfInput.setText("");   //清空
                initTimer(); //计时
                timer.start();
                //actionListener等待用户输入，以及计时器为0的时候也触发检查更新事件
                    try{
                        wait();  //等待checkInput完成
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                // System.out.println("跳出死循环");
                stopTimer();
            } else{   //失败
                checkAndHandelFail();
                frame.dispose();
                return;      //防止一直死循环执行关闭操作，回到主函数
            }
        }
    }


}
