package frame;

import util.Conf;
import util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperationFrame extends JFrame implements ActionListener{
    /*******定义各控件*********/
    private String welcomeMsg = "<html>hi, "+Conf.name+"<br>Change the world,<br>one word at a time.</html>";
    private JLabel lbWelcome = new JLabel(welcomeMsg);
    private JButton btChToEg = new JButton("中文补词");
    private JButton btEgToCh = new JButton("英文选义");
    private JButton btModifyInfo = new JButton("修改个人信息");
    private JButton btExit = new JButton("退出");

    private ModifyDialog modifyDialog = null;
    /********界面初始化*********/
    public OperationFrame() throws Exception {
        this.setLayout(new GridBagLayout()); // 使用 GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();

        // 设置边距和其他布局选项
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50); // 设置边距：上、左、下、右

        // 欢迎信息
        gbc.gridx = 0;
        gbc.gridy = -1;
        gbc.gridwidth = 1;
        lbWelcome.setFont(new Font("Federasyon Bold", Font.BOLD, 20)); // 设置字体样式和大小
        lbWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lbWelcome, gbc);

        // 中文补词按钮
        gbc.gridy = 1;
        btChToEg.setFont(new Font("SimSun", Font.BOLD, 18)); // 设置按钮字体
        btChToEg.setPreferredSize(new Dimension(200, 50)); // 设置按钮大小
        btChToEg.setFocusPainted(false);
        this.add(btChToEg, gbc);

        // 英文选义按钮
        gbc.gridy = 2;
        btEgToCh.setFont(new Font("SimSun", Font.BOLD, 18)); // 设置按钮字体
        btEgToCh.setPreferredSize(new Dimension(200, 50)); // 设置按钮大小
        btEgToCh.setFocusPainted(false);
        this.add(btEgToCh, gbc);

        // 修改个人信息按钮
        gbc.gridy = 3;
        btModifyInfo.setFont(new Font("SimSun", Font.BOLD, 18)); // 设置按钮字体
        btModifyInfo.setPreferredSize(new Dimension(200, 50)); // 设置按钮大小
        btModifyInfo.setFocusPainted(false);
        this.add(btModifyInfo, gbc);

        // 退出按钮
        gbc.gridy = 4;
        btExit.setFont(new Font("SimSun", Font.BOLD, 18)); // 设置按钮字体
        btExit.setPreferredSize(new Dimension(200, 50)); // 设置按钮大小
        btExit.setFocusPainted(false);
        this.add(btExit, gbc);

        this.setSize(400,600);
        GUIUtil.toCenter(this); //居中
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        /********增加监听********/
        btChToEg.addActionListener(this);
        btEgToCh.addActionListener(this);
        btModifyInfo.addActionListener(this);
        btExit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btChToEg){
            try {
                new GameFrameChToEg();
            } catch (Exception exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(this, "打开中文补义失败！");
            }
        }else if (e.getSource() == btEgToCh){
            try {
                new GameFrameEgToCh();
            } catch (Exception exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(this, "打开英文选词失败！");
            }
        }else if (e.getSource() == btModifyInfo){
            try {
                if (modifyDialog == null || !modifyDialog.isVisible()){
                    modifyDialog = new ModifyDialog(this);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(this, "打开修改界面失败！");
            }
        }else if (e.getSource() == btExit){
            JOptionPane.showMessageDialog(this, Conf.name+"记得下次再来背单词哦！");
            System.exit(0);
        }
    }

}
