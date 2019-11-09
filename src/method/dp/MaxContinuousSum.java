package method.dp;

import com.bjzhou.assist.utils.ArrayUtil;

import java.util.Random;

/**
 * 求解连续数组的和的最大值，实现方式一共有三中：
 * 1.暴力破解法，两重循环，求解所有数组区间的和，获得最大值，算法时间复杂度O(N^2)
 * 2.分治法：二分方法，计算左边界和的最大值，右边界和的最大值，以及左右边界和的最大值，最后获得最大
 * 3.动态规划算法：详见代码实现
 *
 * @author bjzhou
 * @date 2019-11-07
 */
public class MaxContinuousSum {
    /**
     * 暴力破解法
     *
     * @param nums
     * @return
     */
    public static int bruteMethod(int[] nums) {
        int maxSum = nums[0];
        int i, j;
        for (i = 0; i < nums.length; i++) {
            int sum = 0;
            for (j = i; j < nums.length; j++) {
                sum += nums[j];
                maxSum = Math.max(maxSum, sum);
            }
        }
        return maxSum;
    }

    /**
     * 分治法
     *
     * @param nums
     * @param left
     * @param right
     * @return
     */
    public static int divideMethod(int[] nums, int left, int right) {
        if (left >= right) {
            return nums[left];
        }
        int mid = (left + right) / 2;
        int leftMaxSum = divideMethod(nums, left, mid);
        int rightMaxSum = divideMethod(nums, mid + 1, right);
        int midToLeftMaxSum = nums[mid];
        int midToRightMaxSum = nums[mid + 1];
        int midSum = nums[mid];
        for (int i = mid - 1; i >= left; i--) {
            midSum += nums[i];
            midToLeftMaxSum = Math.max(midSum, midToLeftMaxSum);
        }
        midSum = nums[mid + 1];
        for (int i = mid + 2; i <= right; i++) {
            midSum += nums[i];
            midToRightMaxSum = Math.max(midSum, midToRightMaxSum);
        }
        int leftToRightMaxSum = midToLeftMaxSum + midToRightMaxSum;
        return Math.max(leftMaxSum, Math.max(leftToRightMaxSum, rightMaxSum));
    }

    /**
     * 动态规划方法的实现，基本思想：
     * 定义opt(n)为目标解，基本子问题划分思想，对于opt(i),如果元素nums[i]不加入序列和计算，那么opt(i)=opt(i-1)；
     * 如果加入序列和计算，那么opt(i)的值应该是opt(i-1)序列和加上最右边界（不包含）开始一直到i序列的和sum，
     * 只有sum>0或者sum比opt(i-1)大的情况下才有意义。
     *
     * @param nums
     * @return
     */
    public static int dpMethod(int nums[]) {
        int maxSum = nums[0];
        int n = nums.length;
        int sum = nums[0];
        for (int i = 1; i < n; i++) {
            maxSum = Math.max(maxSum, sum);
            if (sum < 0) {
                sum = nums[i];
                maxSum = Math.max(maxSum, nums[i]);
            } else {
                sum += nums[i];
                maxSum = Math.max(maxSum, sum);
            }
        }
        return maxSum;
    }

    public static int refDpMethod(int[] arr) {
        int n = arr.length;
        int sum = arr[0];
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            sum = Math.max(sum + arr[i], arr[i]);
            if (sum >= max) {
                max = sum;
            }
        }

        return max;

    }

    /**
     * 产生伪随机数，数字分布范围在lowerBound到upperBound之间（都是包含）
     *
     * @param n
     * @param lowerBound
     * @param upperBound
     * @return
     */
    public static int[] generateRandomNumbers(int n, int lowerBound, int upperBound) {
        int[] nums = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            nums[i] = lowerBound + random.nextInt(upperBound - lowerBound);
        }
        return nums;
    }

    public static void main(String[] args) {
        int[] nums = generateRandomNumbers(200030, -2209, 20350);
//        System.out.println(ArrayUtil.traverseArray(nums, " "));
        long bench = System.currentTimeMillis();
        System.out.println(bruteMethod(nums));
        System.out.println("brute cost time:" + (System.currentTimeMillis() - bench));
        bench = System.currentTimeMillis();
        System.out.println(divideMethod(nums, 0, nums.length - 1));
        System.out.println("binary cost time:" + (System.currentTimeMillis() - bench));
        bench = System.currentTimeMillis();
        System.out.println(dpMethod(nums));
        System.out.println("dp cost time:" + (System.currentTimeMillis() - bench));
//        System.out.println(nums.getClass().equals(int[].class));
        System.out.println(refDpMethod(nums));
    }

}
