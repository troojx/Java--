package frame;

import util.ChtoEg;
import util.EgtoCh;
import util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

public class GameFrameEgToCh extends GamePanel{
    private static JLabel lbEnglish;
    private static JButton[] optionButtons;
    private static JButton selectedButton;
    private static JLabel lbFeedback;
    private JFrame frame;
    private static String CORRECT_ANSWER;
    private static EgtoCh randomEgToCh = new EgtoCh();

    public GameFrameEgToCh() throws Exception {
        super();
        frame = new JFrame("英文选义");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,600);

        this.setBounds(0,0,400,600);
        GUIUtil.toCenter(frame);
        frame.add(this);
        frame.setResizable(false);

        // 显示英文单词的标签
        lbEnglish = new JLabel();
        lbEnglish.setFont(new Font("Microsoft YaHei", Font.BOLD, 24));
        lbEnglish.setBounds(50, 100, 300, 40);
        lbEnglish.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lbEnglish);

        // 提示反馈标签
        lbFeedback = new JLabel("");
        lbFeedback.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        lbFeedback.setBounds(50, 450, 300, 30);
        lbFeedback.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lbFeedback);

        // 创建按钮数组
        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
            optionButtons[i].setBounds(30, 170 + i * 60, 340, 50);
            optionButtons[i].setFocusPainted(false); //避免正确答案不一样有提示
            optionButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedButton = (JButton) e.getSource();
                    checkInput();
                }
            });
            this.add(optionButtons[i]);
        }



        frame.setVisible(true);

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
    }

    @Override
    protected synchronized void checkInput() {
        CORRECT_ANSWER = randomEgToCh.correctChinese;
        if (selectedButton == null) { //没选择
            currentLife--;
            lbFeedback.setText("您没有回答，答案是" + randomEgToCh.correctChinese);
            fileOperateForCustomer.answerWrong(randomEgToCh.total, false);
        } else if (selectedButton.getText().equals(CORRECT_ANSWER)) {   //回答正确
            currentLife++;
            lbFeedback.setText("恭喜回答正确！");
            fileOperateForCustomer.answerCorrect(randomEgToCh.total);
        } else {       //答错了
            currentLife -= 2;
            lbFeedback.setText("回答错误，答案是" + randomEgToCh.correctChinese);
            fileOperateForCustomer.answerWrong(randomEgToCh.total, true);
        }

        updateLifeDisplay();  //更新血量
        notify(); //通知Run方法继续执行
    }

    public synchronized void Run() throws Exception {
        while (true){
            if (currentLife>0){
                selectedButton = null;  //置为空，方便判断有没有选择
                objectOutputStream.writeUTF("2");
                objectOutputStream.flush();//向server发送“2” ：server就发送一个类
                randomEgToCh = (EgtoCh) objectInputStream.readObject();

                if (randomEgToCh == null){
                    System.out.println("题目加载失败，请重试~");
                    continue;
                }

                //测试：
                System.out.println(randomEgToCh.total);

                //随机将正确答案放在一个按钮，剩下的依次放
                Random r = new Random();
                int random = r.nextInt(4);
                optionButtons[random].setText(randomEgToCh.correctChinese);
                int j=0;
                for (int i = 0; i < 4; i++) {
                    if (i!=random){      //没有设置过
                        optionButtons[i].setText(randomEgToCh.wrongChinese[j++]);
                    }
                }
                lbEnglish.setText(randomEgToCh.English);

                //设置题目，开始计时
                initTimer(); //计时
                //timer.start();
                //actionListener等待用户输入，以及计时器为0的时候也触发检查更新事件
                try{
                    wait();  //等待checkInput完成
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                // System.out.println("跳出死循环");
                stopTimer();
            } else{   //失败
                objectOutputStream.writeUTF("0");
                checkAndHandelFail();
                frame.dispose();
                return;      //防止一直死循环执行关闭操作，回到主函数
            }
        }
    }
}
