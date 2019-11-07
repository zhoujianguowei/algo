package interview.huawei;

import java.util.Scanner;

/**
 * [编程题]进制转换
 * 时间限制：1秒
 * <p>
 * 空间限制：32768K
 * <p>
 * 写出一个程序，接受一个十六进制的数，输出该数值的十进制表示。（多组同时输入 ）
 * <p>
 * <p>
 * 输入描述:
 * 输入一个十六进制的数值字符串。
 * <p>
 * <p>
 * 输出描述:
 * 输出该数值的十进制字符串。
 * <p>
 * <p>
 * 输入例子1:
 * 0xA
 * <p>
 * 输出例子1:
 * 10
 */
public class RadixTransform {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        while(scanner.hasNext()){
            String hexS=scanner.nextLine();
            hexS=hexS.replaceFirst("(0x|0X)","");
            int dec=Integer.valueOf(hexS,16);
            System.out.println(dec);
        }
    }
}
