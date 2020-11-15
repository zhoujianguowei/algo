package interview.huawei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 最长回文串
 */
public class LongestPalindrome {
    /**
     * 采用动态规划方法实现，基于基本事实：
     * 定义dp[i][j]=1 (其中i<j)标识索引i到j是回文串，那么具有以下事实
     * dp[i][j]=0如果s[i]!=s[j]，如果dp[i+1][j-1]=1并且s[i]=s[j]那么两者相等
     * @param s
     * @return
     */
    public static List<String> getLongestPalindrome(String s) {
        int length = s.length();
        int dp[][] = new int[length][length];
        int i, j;
        int maxLength = 0;
        for (i = 0; i < length; i++) {
            Arrays.fill(dp[i], 0);
            dp[i][i] = 1;
            maxLength = Math.max(1, maxLength);
            if (i + 1 < length && s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = 1;
                maxLength = Math.max(maxLength, 2);
            }
            j = 1;
            //以dp[i][i]为中心点扩张
            for (; i - j >= 0 && i + j < length; j++) {
                if (dp[i - j + 1][i + j - 1] == 1 && s.charAt(i - j) == s.charAt(i + j)) {
                    dp[i - j][i + j] = 1;
                    maxLength = Math.max(maxLength, 2 * j + 1);
                    continue;
                }
                break;
            }
            //以dp[i][i+1]为中心点扩张
            if (i + 1 < length && dp[i][i + 1] == 1) {
                j = 1;
                for (; i - j >= 0 && i + 1 + j < length; j++) {
                    if (dp[i - j + 1][i + j] == 1 && s.charAt(i - j ) == s.charAt(i + j+1)) {
                        dp[i - j][i + j + 1] = 1;
                        maxLength = Math.max(2 * j + 2, maxLength);
                        continue;
                    }
                    break;
                }
            }
        }
        List<String> result = new ArrayList<>();
        for (i = 0; i < length - maxLength + 1; i++) {
            if (dp[i][i + maxLength - 1] == 1) {
                result.add(s.substring(i, i + maxLength));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> resultList=getLongestPalindrome("pmnnmckkkkkabccba");
        for(String result:resultList ){
            System.out.println(result);
        }
    }
}
