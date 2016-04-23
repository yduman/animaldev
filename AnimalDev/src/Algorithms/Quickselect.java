package Algorithms;

public class Quickselect {

	public Quickselect() {
	}

	public static int quickselect(int[] array, int left, int right, int n) {
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

	public static void swap(int[] array, int a, int b) {
		int tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
	}

	public static int randomPivot(int left, int right) {
		return left + (int) Math.floor(Math.random() * (right - left + 1));
	}
}