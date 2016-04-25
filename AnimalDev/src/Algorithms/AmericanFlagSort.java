package Algorithms;

public class AmericanFlagSort {

	public static void sort(int[] array) {
		final int M = 4;

		int[] counts = new int[M];
		for (int num : array)
			counts[num % M]++;

		int[] offsets = new int[M];
		for (int i = 1; i < M; i++)
			offsets[i] = offsets[i - 1] + counts[i - 1];

		for (int i = 0; i < M; i++) {
			while (counts[i] > 0) {
				int origin = offsets[i];
				int from = origin;
				int num = array[from];
				array[from] = -1;

				do {
					int to = offsets[num % M]++;
					counts[num % M]--;
					int tmp = array[to];
					array[to] = num;
					num = tmp;
					from = to;
				} while (from != origin);
			}
		}
	}
}
