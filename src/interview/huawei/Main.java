package interview.huawei;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 输入一个字符串，求出该字符串包含的字符集合

 输入描述:
 每组数据输入一个字符串，字符串最大长度为100，且只包含字母，不可能为空串，区分大小写。

 输出描述:
 每组数据一行，按字符串原有的字符顺序，输出字符集合，即重复出现并靠后的字母不输出。

 输入例子1:
 abcqweracb

 输出例子1:
 abcqwer
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        while(scanner.hasNext()){
            String str=scanner.nextLine();
            Set<Character> set=new HashSet<>();
            StringBuilder result=new StringBuilder();
            for(int i=0;i<str.length();i++){
                if(!set.contains(str.charAt(i))){
                    result.append(str.charAt(i));
                    set.add(str.charAt(i));
                }
            }
            System.out.println(result);
        }
    }
}
