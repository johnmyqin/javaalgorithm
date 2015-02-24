package ssj.algorithm;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import ssj.algorithm.collections.BitSet;
import ssj.algorithm.collections.HashSet;
import ssj.algorithm.collections.TreeMap;
import ssj.algorithm.math.MathUtil;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by shenshijun on 14-12-20.
 */
public class ArrayUtil {

    public static <T> void swap(T[] array, int left, int right) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(left, right, array.length);

        T temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    public static void swap(int[] array, int left, int right) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(left, right, array.length);

        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    public static void swap(double[] array, int left, int right) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(left, right, array.length);

        double temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    public static void swap(long[] array, int left, int right) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(left, right, array.length);

        long temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }


    public static void swap(short[] array, int left, int right) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(left, right, array.length);

        short temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    public static void swap(char[] array, int left, int right) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(left, right, array.length);

        char temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    public static <T> void swapRange(T[] source, int start, int end, int len) {
        Preconditions.checkNotNull(source);
        Preconditions.checkPositionIndexes(start, end, source.length);

        for (int i = 0; i < len; i++) {
            swap(source, start + i, end + i);
        }
    }

    public static <T> void reverse(T[] source, int start, int end) {
        Preconditions.checkNotNull(source, "source");
        Preconditions.checkPositionIndexes(start, end, source.length);
        for (int i = start; i < (end - start + 1) / 2 + start; i++) {
            swap(source, i, end + start - i);
        }
    }

    public static <T extends Comparable<T>> void sort(T[] arr) {
        sort(Comparable::compareTo, arr, 0, arr.length - 1);
    }

    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        sort(comparator, arr, 0, arr.length - 1);
    }

    private static <T> void sort(Comparator<T> comparator, T[] arr, int start, int end) {
        Preconditions.checkNotNull(arr, "arr should not be null");
        Preconditions.checkNotNull(comparator, "comparator should not be null");
        int length = end - start + 1;
        if (length < 2) {
            return;
        }
        int par_index = partition(comparator, arr, start, end);
        sort(comparator, arr, start, par_index - 1);
        sort(comparator, arr, par_index + 1, end);
    }


    private static <T extends Comparable<T>> int partition(T[] arr, int start, int end) {
        return partition(Comparable::compareTo, arr, start, end);
    }

    public static <T> int partition(Comparator<T> comparator, T[] arr, int start, int end) {
        int par_index = MathUtil.randInt(start, end);
        ArrayUtil.swap(arr, start, par_index);
        par_index = start;
        for (int i = start + 1; i <= end; i++) {
            if (comparator.compare(arr[i], arr[start]) <= 0) {
                par_index++;
                ArrayUtil.swap(arr, par_index, i);
            }
        }
        ArrayUtil.swap(arr, start, par_index);
        return par_index;
    }


    /**
     * 经典选择算法，选出第k小数，复杂O(N)
     *
     * @param arr
     * @param k
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> T select(T[] arr, int k) {
        Preconditions.checkNotNull(arr);
        Preconditions.checkPositionIndex(k, arr.length);
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int par_index = partition(arr, low, high);
            if (par_index == k) {
                return arr[par_index];
            } else if (par_index > k) {
                high = par_index - 1;
            } else {
                low = par_index + 1;
            }
        }
        return null;
    }

    /**
     * 二分搜索
     *
     * @param arr
     * @param ele
     * @param <T>
     * @return
     */
    public <T extends Comparable<T>> int binarySearch(T[] arr, T ele) {
        Preconditions.checkNotNull(arr);
        Preconditions.checkNotNull(ele);
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            int com_res = arr[middle].compareTo(ele);
            if (com_res == 0) {
                return middle;
            } else if (com_res > 0) {
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }
        return -1;
    }

    /**
     * 归并排序
     *
     * @param arr
     * @param comparator
     * @param <T>
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        Preconditions.checkNotNull(arr);
        Preconditions.checkNotNull(comparator);
        mergeSort(arr, comparator, 0, arr.length - 1);
    }

    private static <T> void mergeSort(T[] arr, Comparator<T> comparator, int start, int end) {
        if (start < end) {
            int middle = (end + start) / 2;
            mergeSort(arr, comparator, start, middle);
            mergeSort(arr, comparator, middle + 1, end);
            merge(arr, start, middle, end, comparator);
        }
    }

    private static <T> void merge(T[] arr, int start, int middle, int end, Comparator<T> comparator) {
        int front_start = start;
        int end_start = middle + 1;
        int cur_pos = 0;
        T[] helper = (T[]) new Object[end - start + 1];
        while (front_start <= middle && end_start <= end) {
            if (comparator.compare(arr[front_start], arr[end_start]) <= 0) {
                helper[cur_pos++] = arr[front_start++];
            } else {
                helper[cur_pos++] = arr[end_start++];
            }
        }

        while (front_start <= middle) {
            helper[cur_pos++] = arr[front_start++];
        }

        while (end_start <= end) {
            helper[cur_pos++] = arr[end_start++];
        }
        System.arraycopy(helper, 0, arr, start, helper.length);
    }

    /**
     * 计数排序
     *
     * @param arr
     */
    public static void countSort(int[] arr, int start, int end) {
        Preconditions.checkNotNull(arr);
        Preconditions.checkArgument(start <= end);
        int[] result = new int[arr.length];
        int range = end - start + 1;
        int[] helper = new int[range];
        for (int i : arr) {
            helper[i - start]++;
        }

        for (int i = 1; i < range; i++) {
            helper[i] = helper[i - 1] + helper[i];
        }

        for (int i : arr) {
            result[helper[i - start] - 1] = i;
            helper[i - start]--;
        }
        System.arraycopy(result, 0, arr, 0, arr.length);
    }

    public static <T> void radixSort(T[] arr, Comparator<T> comparator) {
        //todo 完成基数排序
    }

    public static <T> T[] leftRotate(T[] raw_array, int start, int end, int rot_index) {
        Preconditions.checkNotNull(raw_array);
        Preconditions.checkPositionIndexes(start, end, raw_array.length);
        Preconditions.checkPositionIndex(rot_index, raw_array.length);
        ArrayUtil.reverse(raw_array, start, rot_index);
        ArrayUtil.reverse(raw_array, rot_index + 1, end);
        ArrayUtil.reverse(raw_array, start, end);
        return null;
    }

    public static <T extends Comparable<T>> T[] smallestK(T[] arr) {
        //TODO 最小的k个数
        // https://github.com/julycoding/The-Art-Of-Programming-By-July/blob/master/ebook/zh/02.01.md
        return null;
    }

    /**
     * * 使用BitSet实现的位图排序，其实就是桶排序的一个变种
     * 这个算法只能用于Int排序，因为要排序的元素会作为BitSet中使用的索引
     * <p>
     * 1，必须是取值范围比较小，不然需要大量的空间。
     * 2，没有重复，因为BitSet只有True和False
     * 原地排序，因此改变了原来数组中的内容
     *
     * @param un_sort_list
     * @return
     */
    public static int[] bitMapSort(int[] un_sort_list) {
        Preconditions.checkNotNull(un_sort_list);
        BitSet bit_set = new BitSet(un_sort_list.length / 8);
        for (int x : un_sort_list) {
            if (x < 0) {
                throw new IllegalArgumentException("list need to be sorted should not have negative number");
            }
            if (bit_set.get(x)) {
                throw new IllegalArgumentException("should have not repeat in array");
            }
            bit_set.set(x);
        }

        int sorted_index = 0;
        for (int i = 0; i < bit_set.length(); i++) {
            if (bit_set.get(i)) {
                un_sort_list[sorted_index++] = i;
            }
        }
        return un_sort_list;
    }

    /**
     * 从一个已经旋转过的数组中寻找最小值
     *
     * @param arr
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> T minInRotate(T[] arr) {
        Preconditions.checkNotNull(arr);
        if (arr.length == 0) {
            return null;
        }
        int start = 0;
        int end = arr.length - 1;
        int mid = start;
        while (arr[start].compareTo(arr[end]) >= 0) {
            if (end - start == 1) {
                mid = end;
                break;
            }
            mid = (start + end) / 2;
            if (arr[mid].compareTo(arr[start]) == 0 && arr[mid].compareTo(arr[end]) == 0) {
                return min(arr);
            }
            if (arr[mid].compareTo(arr[start]) >= 0) {
                start = mid;
            } else if (arr[mid].compareTo(arr[end]) <= 0) {
                end = mid;
            }
        }
        return arr[mid];
    }

    public static <T extends Comparable<T>> T min(T[] arr) {
        Preconditions.checkNotNull(arr);
        if (arr.length == 0) {
            return null;
        }

        T min_value = arr[0];
        for (T ele : arr) {
            if (min_value.compareTo(ele) < 0) {
                min_value = ele;
            }
        }
        return min_value;
    }


    public static <T> T[] group(T[] arr, Function<T, Integer> func) {
        Preconditions.checkNotNull(arr);
        Preconditions.checkNotNull(func);
        TreeMap<Integer, HashSet<T>> static_map = new TreeMap<>();
        for (T ele : arr) {
            int group_num = func.apply(ele);
            HashSet<T> set = static_map.get(group_num);
            if (set == null) {
                set = new HashSet<>();
                static_map.set(group_num, set);
            }
            set.add(ele);
        }
        Iterator<HashSet<T>> iterator = static_map.valueIterator();
        int pos = 0;
        while (iterator.hasNext()) {
            for (T ele : iterator.next()) {
                arr[pos++] = ele;
            }
        }
        return arr;
    }

}



