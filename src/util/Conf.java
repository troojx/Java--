package util;

/**
 * 将各个模块之间需要共享的数据保存在Conf类的静态变量中
 * 静态变量一旦赋值，在另一个时刻访问，仍然是这个值，因此，可以用静态变量来传递数据
 */
public class Conf {
    public static String account;
    public static String password;
    public static String name;
}
