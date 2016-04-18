package Algorithms;

/**
 * quickselect is a selection algorithm to find the kth smallest element in an
 * unordered list. Like quicksort, it is efficient in practice and has good
 * average-case performance, but has poor worst-case performance. Quickselect
 * and variants is the selection algorithm most often used in efficient
 * real-world implementations.
 * 
 * Quickselect uses the same overall approach as quicksort, choosing one element
 * as a pivot and partitioning the data in two based on the pivot, accordingly
 * as less than or greater than the pivot. However, instead of recursing into
 * both sides, as in quicksort, quickselect only recurses into one side – the
 * side with the element it is searching for. This reduces the average
 * complexity from O(n log n) (in quicksort) to O(n) (in quickselect).
 *
 * As with quicksort, quickselect is generally implemented as an in-place
 * algorithm, and beyond selecting the kth element, it also partially sorts the
 * data. See selection algorithm for further discussion of the connection with
 * sorting.
 * 
 * This is an implementation of: https://en.m.wikipedia.org/wiki/Quickselect
 */
public class Quickselect {

	public Quickselect() {
	}

	/**
	 * this is the iterative approach of the algorithm
	 * 
	 * @param array
	 *            - the array we are working with
	 * @param left
	 *            - the left border of the array
	 * @param right
	 *            - the right border of the array
	 * @param n
	 *            - the n-th smalles element we are searching for (0 equals the
	 *            1st!)
	 * @return the n-th smallest element
	 */
	public static int selectIterative(int[] array, int left, int right, int n) {
		if (left == right)
			return array[left];

		for (;;) {
			int pivot = randomPivot(left, right);
			pivot = partition(array, left, right, pivot);

			if (n == pivot)
				return array[n];
			else if (n < pivot)
				right = pivot - 1;
			else
				left = pivot + 1;
		}
	}

	/**
	 * this is the recursive approach of the algorithm
	 * 
	 * @param array
	 *            - the array we are working with
	 * @param left
	 *            - the left border of the array
	 * @param right
	 *            - the right border of the array
	 * @param n
	 *            - the n-th smalles element we are searching for (0 equals the
	 *            1st!)
	 * @return the n-th smallest element
	 */
	public static int selectRecursive(int[] array, int left, int right, int n) {
		if (left == right)
			return array[left];

		int pivot = randomPivot(left, right);
		pivot = partition(array, left, right, pivot);

		if (n == pivot)
			return array[n];
		else if (n < pivot)
			return selectRecursive(array, left, pivot - 1, n);
		else
			return selectRecursive(array, pivot + 1, right, n);
	}

	/**
	 * 
	 * In quicksort, there is a subprocedure called partition that can, in
	 * linear time, group a list (ranging from indices left to right) into two
	 * parts, those less than a certain element, and those greater than or equal
	 * to the element. Here is code that performs a partition about the element
	 * list[pivotIndex]
	 * 
	 * @param array
	 *            - the array we are working with
	 * @param left
	 *            - the left border of the array
	 * @param right
	 *            - the right border of the array
	 * @param pivot
	 *            - the pivot element which is randomly chosen
	 * @return
	 */
	public static int partition(int[] array, int left, int right, int pivot) {
		int pivotValue = array[pivot];
		swap(array, pivot, right);
		int storeIndex = left;

		for (int i = left; i < right; i++) {
			if (array[i] < pivotValue) {
				swap(array, storeIndex, i);
				storeIndex++;
			}
		}
		swap(array, right, storeIndex);
		return storeIndex;
	}

	/**
	 * swap two array elements
	 * 
	 * @param array
	 *            - the array we are working with
	 * @param a
	 *            - first index
	 * @param b
	 *            - second index
	 */
	public static void swap(int[] array, int a, int b) {
		int tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
	}

	/**
	 * chooses a random pivot
	 * 
	 * @param left
	 *            - the left border of the array
	 * @param right
	 *            - the right border of the array
	 * @return a random pivot
	 */
	public static int randomPivot(int left, int right) {
		return left + (int) Math.floor(Math.random() * (right - left + 1));
	}
}