package interview.huawei;

import java.util.Scanner;

/**
 * 题目描述
 * 功能:输入一个正整数，按照从小到大的顺序输出它的所有质数的因子（如180的质数因子为2 2 3 3 5 ）
 * <p>
 * 最后一个数后面也要有空格
 * <p>
 * 详细描述：
 * <p>
 * <p>
 * 函数接口说明：
 * <p>
 * public String getResult(long ulDataInput)
 * <p>
 * 输入参数：
 * <p>
 * long ulDataInput：输入的正整数
 * <p>
 * 返回值：
 * <p>
 * String
 * <p>
 * <p>
 * <p>
 * 输入描述:
 * 输入一个long型整数
 * <p>
 * 输出描述:
 * 按照从小到大的顺序输出它的所有质数的因子，以空格隔开。最后一个数后面也要有空格。
 * <p>
 * 示例1
 * 输入
 * 复制
 * 180
 * 输出
 * 复制
 * 2 2 3 3 5
 */
public class FactorSep {
    public static void getResult(long ulDataInput) {
        for (int i = 2; i <= ulDataInput; i++) {
            if (ulDataInput % i == 0) {
                System.out.print(i + " ");
                getResult(ulDataInput / i);
                break;
            }
        }

    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            long num = scan.nextLong();
            getResult(num);
        }
    }
}