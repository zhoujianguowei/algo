package tree;

/**
 * 堆算法的具体实现，该算法按照小顶堆的方式进行实现
 *
 * @param <T>
 */
public class Heap<T extends Comparable> {

    /**
     * 向下调整完全二叉树（近似堆，只有节点i的值不满足小顶堆的定义），
     *
     * @param nums
     * @param i        不平衡的节点
     * @param endIndex 调整的堆的最后一个元素的索引
     */
    public void putDownTree(T[] nums, int i, int endIndex) {
        int j = 2 * i + 1;
        //获取左右子树最小的那个节点
        while (j <= endIndex) {
            if (j + 1 <= endIndex) {
                if (nums[j].compareTo(nums[j + 1]) > 0) {
                    j = j + 1;
                }
            }
            if (nums[i].compareTo(nums[j]) <= 0) {
                break;
            } else {
                //交换两个节点
                T temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                i = j;
                j = 2 * i + 1;
            }
        }
    }

    /**
     * 构建小顶堆
     *
     * @param nums
     */
    public void constructSmallHeap(T[] nums) {
        for (int i = (nums.length - 2) / 2; i >= 0; i--) {
            putDownTree(nums, i, nums.length - 1);
        }
    }
}
