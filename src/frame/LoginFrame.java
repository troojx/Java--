package frame;

import util.Conf;
import util.FileOperateForLogin;
import util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener {
    //private Icon welcomeIcon = new ImageIcon("goodbye.jpg");
    private JLabel lbWelcome = new JLabel("<html>goodbye,<br>language barriers</html>");
    private JLabel lbAccount = new JLabel("输入账号:)");
    private JTextField tfAccount = new JTextField(15);
    private JLabel lbPassword = new JLabel("输入密码:)");
    private JPasswordField pfPassword = new JPasswordField(15);
    private JButton btLogin = new JButton("登录");
    private JButton btRegister = new JButton("注册");
    private JButton btExit = new JButton("退出");

    public LoginFrame(){
        /*********************界面初始化****************************/
        super("Duo's been waiting!");
        // 使用 GridBagLayout 进行布局
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);  // 设置控件之间的间距

        // 欢迎标签
        lbWelcome.setFont(new Font("Federasyon Bold", Font.BOLD, 20)); // 设置字体样式和大小
        lbWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // 设置跨越两列
        this.add(lbWelcome, gbc);

        // 账号标签
        lbAccount.setFont(new Font("SimSun", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;  // 让账号标签占一列
        this.add(lbAccount, gbc);

        // 账号输入框
        tfAccount.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(tfAccount, gbc);

        // 密码标签
        lbPassword.setFont(new Font("SimSun", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(lbPassword, gbc);

        // 密码输入框
        pfPassword.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(pfPassword, gbc);

        // 登录按钮
        btLogin.setFont(new Font("SimSun", Font.BOLD, 16));
        btLogin.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;  // 登录按钮跨越两列
        this.add(btLogin, gbc);

        // 注册按钮
        btRegister.setFont(new Font("SimSun", Font.BOLD, 16));
        btRegister.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;  // 注册按钮跨越两列
        this.add(btRegister, gbc);

        // 退出按钮
        btExit.setFont(new Font("SimSun", Font.BOLD, 16));
        btExit.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;  // 退出按钮跨越两列
        this.add(btExit, gbc);

        // 设置窗口属性
        this.setSize(400, 600);
        GUIUtil.toCenter(this);  // 居中显示
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        /****************增加监听**************/
        btLogin.addActionListener(this);
        btExit.addActionListener(this);
        btRegister.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btLogin){        //登录界面
            String account = tfAccount.getText();
            String password = new String(pfPassword.getPassword());
            FileOperateForLogin.getInfoByAccount(account);
            if (Conf.account == null || !Conf.password.equals(password)){
                JOptionPane.showMessageDialog(this,"登录失败");
                return;
            }
            JOptionPane.showMessageDialog(this,"登录成功！快来背单词！");
            this.dispose();
            try {
                new OperationFrame();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }else if(e.getSource()==btRegister){
            this.dispose();
            new RegisterFrame();
        }else{
            JOptionPane.showMessageDialog(this,"记得快点回来背单词！！");
            System.exit(0);
        }
    }
}
