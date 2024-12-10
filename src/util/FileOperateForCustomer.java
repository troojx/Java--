package util;

import java.io.*;

/**
 * 答对：保存在“已掌握单词.txt"
 * 答错：保存在“未掌握单词.txt”，并标注是答错还是没答
 */

public class FileOperateForCustomer {
    private static String fileKnownVocabulary = "已掌握单词.txt";
    private static String fileUnknownVocabulary = "未掌握单词.txt";

    //将文件写入流声明为静态变量
    private static Writer fwKnown;
    private static Writer fwUnknown;

    //因为只创建一个实例对象，所以放在构造函数和static代码块中效果相同，都只执行一次
    public FileOperateForCustomer() throws Exception {
        fwKnown = new FileWriter(fileKnownVocabulary,true); //追加模式
        fwUnknown = new FileWriter(fileUnknownVocabulary,true);
    }

    //答对：保存在“已掌握单词.txt"
    public static boolean answerCorrect(String total){
        try{
            fwKnown.write(total + "\r\n");
            fwKnown.flush();  //刷新缓冲区，确保内容写入文件
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //答错：保存在“未掌握单词.txt”，并标注是答错还是没答
    public static boolean answerWrong(String total,boolean answer){
        try {
            fwUnknown.write(((answer == true) ? "答错" : "没答") + " " + total + "\r\n");
            fwUnknown.flush();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void close(){
        try {
            if (fwUnknown!=null)fwUnknown.close();
            if (fwKnown!=null) fwKnown.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
