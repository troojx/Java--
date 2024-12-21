package frame;

import util.Conf;
import util.FileOperateForLogin;
import util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//模态对话框
public class ModifyDialog extends JDialog implements ActionListener {
    /***********************定义各控件*****************************/
    private JLabel lbMsg = new JLabel("你的账号为:)");
    private JLabel lbAccount = new JLabel(Conf.account);
    private JLabel lbPassword1 = new JLabel("输入密码:)");
    private JPasswordField pfPassword1 = new JPasswordField(Conf.password,10);
    private JLabel lbPassword2 = new JLabel("确认密码:)");
    private JPasswordField pfPassword2 = new JPasswordField(Conf.password,10);
    private JLabel lbName = new JLabel("修改姓名:)");
    private JTextField tfName = new JTextField(Conf.name,10);
    private JButton btModify = new JButton("修改");
    private JButton btExit = new JButton("去背单词");
    public ModifyDialog(JFrame frm){
        /***********************界面初始化*************************/
        super(frm,"修改个人信息",true);
        // 使用 GridBagLayout 布局
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // 设置控件之间的间距
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 设置控件的字体
        Font labelFont = new Font("SimSun", Font.BOLD, 14);
        Font buttonFont = new Font("SimSun", Font.BOLD, 16);

        // 布局：账号信息
        lbMsg.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(lbMsg, gbc);

        lbAccount.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        this.add(lbAccount, gbc);

        // 设置密码1输入框
        lbPassword1.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(lbPassword1, gbc);

        pfPassword1.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        this.add(pfPassword1, gbc);

        // 设置密码2输入框
        lbPassword2.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(lbPassword2, gbc);

        pfPassword2.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        this.add(pfPassword2, gbc);

        // 设置姓名输入框
        lbName.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(lbName, gbc);

        tfName.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        this.add(tfName, gbc);

        // 修改按钮
        btModify.setFont(buttonFont);
        btModify.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;  // 跨越两列
        this.add(btModify, gbc);

        // 关闭按钮
        btExit.setFont(buttonFont);
        btExit.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 0;
        gbc.gridy = 5;
        this.add(btExit, gbc);

        // 设置对话框的属性
        this.setSize(350, 300);
        GUIUtil.toCenter(this);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        /**********************增加监听****************************/
        btModify.addActionListener(this);
        btExit.addActionListener(this);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btModify) {
            String password1 = new String(pfPassword1.getPassword());
            String password2 = new String(pfPassword2.getPassword());
            if (!password1.equals(password2)) {
                JOptionPane.showMessageDialog(this, "两个密码不相同");
                return;
            }
            String name = tfName.getText();

            //System.out.println("zhi xing dao zhe li le");
            //将新的值存入静态变量
            Conf.password = password1;
            Conf.name = name;
            try {
                FileOperateForLogin.updateCustomer(Conf.account, password1, name);
                JOptionPane.showMessageDialog(this, "修改成功，快去背单词吧！");
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, "修改发生错误");
                exception.printStackTrace();
            }
        } else {
            //JOptionPane.showMessageDialog(this,"记得回来背单词哦！");
            this.dispose();
        }
    }
}