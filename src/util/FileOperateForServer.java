package util;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * 预加载词汇表，存储到ArrayList,方便使用下标访问
 * 返回一组中英+三个中文：英文选义
 * 返回一组中英：中文补词
 */

public class FileOperateForServer {
    private static String fileVocabularyName = "wordlist.txt";
    private static Random random = new Random();
    public static ArrayList<String> vocabulary;
    public static int SizeOfVocab;

    //构造函数：预加载，将单词加载到字符串数组中
    public FileOperateForServer(){
        vocabulary = new ArrayList<>(3500);
        try(BufferedReader br = new BufferedReader(new FileReader(fileVocabularyName))){
            String line;//每一行读取的数据
            while((line = br.readLine())!=null){
                vocabulary.add(line);
            }
            SizeOfVocab = vocabulary.size();
        }catch(Exception ex){
            //异常处理
            JOptionPane.showMessageDialog(null,"读入词汇表操作异常");
            System.exit(0);
        }
    }

    //返回一组中英：中文补词
    public static ChtoEg RandomChToEg(){
        ChtoEg chtoeg = new ChtoEg();
        int randomInt = random.nextInt(SizeOfVocab);
        chtoeg.Total = vocabulary.get(randomInt);
        String splitStr[] = chtoeg.Total.split("=");
        chtoeg.English = splitStr[0];
        int len = splitStr[0].length();
        chtoeg.FirstLetter = Character.toString(chtoeg.English.charAt(0));
        chtoeg.LastLetter = Character.toString(chtoeg.English.charAt(len-1));
        chtoeg.CORRECT_ANSWER = chtoeg.English.substring(1,len-1);
        chtoeg.Chinese = splitStr[1];

        if (chtoeg == null) System.out.println("RandomChToEg returned null.");
        return chtoeg;
    }

    //返回一组中英+三个中文：英文选义
    public static EgtoCh RandomEgToCh(){
        EgtoCh egtoch = new EgtoCh();
        int RandomInt = random.nextInt(SizeOfVocab);
        egtoch.total = vocabulary.get(RandomInt);
        String splitStr[] = egtoch.total.split("=");
        egtoch.English = splitStr[0];
        egtoch.correctChinese = splitStr[1];
        for (int i = 0; i < 3; i++) {
            RandomInt = random.nextInt(SizeOfVocab);
            egtoch.wrongChinese[i] = vocabulary.get(RandomInt).split("=")[1];
        }
        return egtoch;
    }
}

