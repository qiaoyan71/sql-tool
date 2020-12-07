package com.qy;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *  @Description: 处理SQL的小工具
 *  @author: QY
 *  @Date: 20.12.4 15:35
 */
public class QySQL {

    private static final String END = "end";

    public static void main(String[] args) {
        convertSQL();
    }

    public static void convertSQL(){
        Scanner scn = new Scanner(System.in);
        int type = -1;
        int mode = 0;
        File file = null;
        List<String> array = new ArrayList<>();
        while(type == -1){
            System.out.println("欢迎使用QY-SQL V1.0小工具!");
            // 判断模式
            System.out.println("请输入模式: 1 - 控制台交互模式 ; 2 - 文件地址模式;按回车结束;");
            String input = scn.next();
            System.out.println("已输入:" + input);

            if(!"1".equals(input) && !"2".equals(input)){
                System.out.println("\t输入数据错误!请重新输入!");
                type = -1;
                continue;
            }

            if("2".equals(input)){
                mode = 2;
                System.out.println("请输入文件地址:");
                String next = scn.next();
                System.out.println("已输入:" + next);
                try {
                    file = new File(next);
                    array = FileUtils.readLines(file,"UTF-8");
                } catch (IOException e) {
                    System.out.println("\t输入数据错误!请重新输入!");
                    System.out.println("错误信息:" + e.getMessage());
                    type = -1;
                    continue;
                }
            }

            if("1".equals(input)){
                mode = 1;
            }

            // 判断格式
            System.out.println("请输入sql模式: 1 - 一般拼接 ; 2 - StringBuilder拼接 ; 3 - StringBuffer拼接 ; 4 - append拼接 ;按回车结束");
            String next = scn.next();
            System.out.println("已输入:" + next);

            try {
                type = Integer.parseInt(next);
                if(type != 1 && type != 2 && type != 3 && type != 4  && type != 5){
                    System.out.println("\t输入数据错误!请重新输入!");
                    type = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("\t输入数据错误!请重新输入!");
                type = -1;
            }
        }

        // handle mode1
        handleMode1(scn,mode,array);

        // join sql
        joinSql(array,type);

        // continue or end
        continueOrEnd(scn);
    }

    private static void handleMode1(Scanner scn,int mode, List<String> array) {
        if(mode == 1){
            System.out.println("请输入sql(可以多行),最后以[end]单行结束");
            String line;
            while (!END.equals(line = scn.nextLine())) {
                if(line!=null && line.trim().length()>0){
                    array.add(line);
                }
            }
        }
    }

    private static void continueOrEnd(Scanner scn) {
        System.out.println("是否继续进行: 1-继续,其他-停止");
        String in = scn.next();
        try {
            if(Integer.parseInt(in) == 1 ){
                convertSQL();
            }else{
                scn.close();
                System.out.format("\33[32;4m欢迎下次使用QY-SQL V1.0小工具!Bye....%n");
            }
        } catch (Exception e) {
            scn.close();
            System.out.format("\33[32;4m欢迎下次使用QY-SQL V1.0小工具!Bye....%n");
        }
    }

    private static void joinSql(List array,Integer type){
        switch (type){
            case 1 :
                array.forEach((a)-> System.out.println( "\" " + a + " \" + " ));
                break;
            case 2 :
                System.out.println("new StringBuilder()");
                array.forEach((a)-> {
                    System.out.println( ".append(\" " + a + " \")" );
                });
                System.out.println(".toString()");
                break;
            case 3 :
                System.out.println("new StringBuffer()");
                array.forEach((a)-> {
                    System.out.println( ".append(\" " + a + " \")" );
                });
                System.out.println(".toString()");
                break;
            case 4 :
                array.forEach((a)-> {
                    System.out.println( ".append(\" " + a + " \")" );
                });
                break;
            default:
                break;
        }
    }


    public static void printColor(String str) {
        // 背景颜色代号(41-46)
        // 前景色代号(31-36)
        //前景色代号和背景色代号可选，就是或可以写，也可以不写
        // 数字+m：1加粗；3斜体；4下划线
        // 格式：System.out.println("\33[前景色代号;背景色代号;数字m");
        Random backgroundRandom = new Random();
        Random fontRandom = new Random();
        int font = fontRandom.nextInt(6) + 31;
        int background = backgroundRandom.nextInt(6) + 41;
        System.out.format("前景色是%d,背景色是%d------\33[%d;%d;4m%s%n", font, background, font,background,str);
    }
}
