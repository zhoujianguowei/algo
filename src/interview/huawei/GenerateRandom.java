package interview.huawei;

import java.util.Scanner;
import java.util.TreeSet;

/**
 * [编程题]明明的随机数
 * 时间限制：1秒
 * <p>
 * 空间限制：32768K
 * <p>
 * 明明想在学校中请一些同学一起做一项问卷调查，为了实验的客观性，他先用计算机生成了N个1到1000之间的随机整数（N≤1000），对于其中重复的数字，只保留一个，把其余相同的数去掉，不同的数对应着不同的学生的学号。然后再把这些数从小到大排序，按照排好的顺序去找同学做调查。请你协助明明完成“去重”与“排序”的工作(同一个测试用例里可能会有多组数据，希望大家能正确处理)。
 * <p>
 * <p>
 * <p>
 * Input Param
 * <p>
 * n               输入随机数的个数
 * <p>
 * inputArray      n个随机整数组成的数组
 * <p>
 * <p>
 * Return Value
 * <p>
 * OutputArray    输出处理后的随机整数
 * <p>
 * <p>
 * <p>
 * 注：测试用例保证输入参数的正确性，答题者无需验证。测试用例不止一组。
 * <p>
 * <p>
 * <p>
 * <p>
 * 输入描述:
 * 输入多行，先输入随机整数的个数，再输入相应个数的整数
 * <p>
 * <p>
 * 输出描述:
 * 返回多行，处理后的结果
 * <p>
 * <p>
 * 输入例子1:
 * 11
 * 10
 * 20
 * 40
 * 32
 * 67
 * 40
 * 20
 * 89
 * 300
 * 400
 * 15
 * <p>
 * 输出例子1:
 * 10
 * 15
 * 20
 * 32
 * 40
 * 67
 * 89
 * 300
 * 400
 */
public class GenerateRandom {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            int[] nums = new int[n];
            TreeSet<Integer> sortSet = new TreeSet<>();
            int i = 0;
            while (i < n) {
                nums[i] = scanner.nextInt();
                sortSet.add(nums[i]);
                i++;
            }
            for (Integer num : sortSet) {
                System.out.println(num);
            }

        }
    }
}
