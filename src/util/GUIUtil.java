package util;

import java.awt.*;

public class GUIUtil {
    //数传入的参数不是 JFrame，而是其父类Component
    //为了扩大本函数的适用范围，让其适用于所有 Component 的子类
    public static void toCenter(Component comp){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle rec = ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
        //界面居中的坐标计算方法
        comp.setLocation(((int)rec.getWidth()-comp.getWidth())/2 , ((int)rec.getHeight()-comp.getHeight())/2);
    }
}
