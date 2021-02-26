package search;

/**
 * 二分查找算法实现
 *
 * @author zhoubenjin
 * @date 2021-01-31
 */
public class BinarySearch {
    public static int binarySearch(int[] sortElements, int target) {
        int lowIndex = 0;
        int highIndex = sortElements.length - 1;
        int mid;
        while (lowIndex <= highIndex) {
            mid = (highIndex + lowIndex) / 2;
            if (target > sortElements[mid]) {
                lowIndex = mid + 1;
            } else if (target == sortElements[mid]) {
                return mid;
            } else {
                highIndex = mid - 1;
            }
        }
        System.out.println("lowIndex=" + lowIndex + ",highIndex=" + highIndex);
        return highIndex + 1;
    }

    public static void main(String[] args) {
        int arr[] = {1, 3, 5, 7, 9};
        int searchIndex = binarySearch(arr, 10);
        System.out.println("searchIndex="+searchIndex);

    }
}
