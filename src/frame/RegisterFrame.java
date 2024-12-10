package frame;

import util.Conf;
import util.FileOperateForLogin;
import util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame implements ActionListener {
    /************************定义各控件****************************/
    private JLabel lbAccount = new JLabel("输入账号:)");
    private JTextField tfAccount = new JTextField(15);
    private JLabel lbPassword1 = new JLabel("输入密码:)");
    private JPasswordField pfPassword1 = new JPasswordField(15);
    private JLabel lbPassword2 = new JLabel("确认密码:)");
    private JPasswordField pfPassword2 = new JPasswordField(15);
    private JLabel lbName = new JLabel("输入昵称:)");
    private JTextField tfName = new JTextField(15);
    private JButton btRegister = new JButton("注册");
    private JButton btLogin = new JButton("登录");
    private JButton btExit = new JButton("退出");

    public RegisterFrame(){
        /********************界面初始化************************/
        super("注册");
        // 使用 GridBagLayout 布局
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // 设置控件之间的间距

        // 设置控件的字体
        Font labelFont = new Font("SimSun", Font.BOLD, 14);
        Font buttonFont = new Font("SimSun", Font.BOLD, 16);

        // 布局：账号输入框和标签
        lbAccount.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(lbAccount, gbc);

        tfAccount.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(tfAccount, gbc);

        // 密码输入框1和标签
        lbPassword1.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(lbPassword1, gbc);

        pfPassword1.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(pfPassword1, gbc);

        // 密码输入框2和标签
        lbPassword2.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(lbPassword2, gbc);

        pfPassword2.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(pfPassword2, gbc);

        // 昵称输入框和标签
        lbName.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(lbName, gbc);

        tfName.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 3;
        this.add(tfName, gbc);

        // 注册按钮
        btRegister.setFont(buttonFont);
        btRegister.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;  // 跨越两列
        this.add(btRegister, gbc);

        // 登录按钮
        btLogin.setFont(buttonFont);
        btLogin.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;  // 跨越两列
        this.add(btLogin, gbc);

        // 退出按钮
        btExit.setFont(buttonFont);
        btExit.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;  // 跨越两列
        this.add(btExit, gbc);

        // 设置窗口属性
        this.setSize(400, 600);
        GUIUtil.toCenter(this);  // 居中显示
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        /********************增加监听************************************/
        btLogin.addActionListener(this);
        btRegister.addActionListener(this);
        btExit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btRegister){
            String password1 = new String(pfPassword1.getPassword());
            String password2 = new String(pfPassword2.getPassword());
            if (!password1.equals(password2)){
                JOptionPane.showMessageDialog(this,"两次密码不一样哦！");
                return;
            }
            String account = tfAccount.getText();
            FileOperateForLogin.getInfoByAccount(account);
            if (Conf.account!=null){
                JOptionPane.showMessageDialog(this,"用户已经注册");
                return;
            }
            String name = tfName.getText();
            try {
                FileOperateForLogin.updateCustomer(account,password1,name);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this,"把欧尼注册信息失败");
                exception.printStackTrace();
            }
        }else if(e.getSource() == btLogin){
            this.dispose();
            new LoginFrame();
        }else {
            JOptionPane.showMessageDialog(this,"记得下次来背单词哦！！");
            System.exit(0);
        }
    }
}
