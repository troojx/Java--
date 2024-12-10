package util;

import frame.GamePanel;

/**
 * 事件
 */

public class TimeCounterThread implements Runnable{
    //2.定义一个类变量记住类的一个对象
    private static TimeCounterThread Counter = new TimeCounterThread();
    private int timeLeft;

    //1.私有化构造器
    private TimeCounterThread(){}

    //3.定义一个类方法，返回对象
    public static TimeCounterThread getCounter(){
        return Counter;
    }

    @Override
    public void run() {

        while (true){
            //等待1秒
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }





}


/*   public static void main(String[] args) {
        //创建任务对象
        Runnable counter = new TimeCounterThread();//多态
        //把任务对象交给一个线程对象处理
        new Thread(counter).start();
    }  */
    /**static单例设计模式：保证一个类只能有一个对象--单例类
     * 饿汉式：拿对象时，对象早就创建好了
     * 写法：1.把类的构造器私有
     *      2.定义一个类变量记住类的一个对象
     *      3.定义一个类方法，返回对象
     */

    /**
     * 多线程方法二：任务implements Runnable
     *      1.implements Runnable接口
     *      2.重写run
     *      3.main中定义线程，将对象传入线程
     */

