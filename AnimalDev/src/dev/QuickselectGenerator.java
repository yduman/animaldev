package dev;

import java.awt.Color;
import java.awt.Font;
import java.util.Hashtable;
import java.util.Locale;

import algoanim.exceptions.LineNotExistsException;
import algoanim.primitives.ArrayMarker;
import algoanim.primitives.IntArray;
import algoanim.primitives.SourceCode;
import algoanim.primitives.generators.AnimationType;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.ArrayMarkerProperties;
import algoanim.properties.ArrayProperties;
import algoanim.properties.SourceCodeProperties;
import algoanim.util.Coordinates;
import algoanim.util.TicksTiming;
import algoanim.util.Timing;
import generators.framework.Generator;
import generators.framework.GeneratorType;
import generators.framework.properties.AnimationPropertiesContainer;

/**
 * 
 * @author Yadullah Duman <yadullah.duman@gmail.com>
 *
 */
public class QuickselectGenerator implements Generator {
	private Language language;

	private QuickselectGenerator(Language l) {
		language = l;
		language.setStepMode(true);
	}

	private static final String QUICKSELECT_DESCRIPTION = "In computer science, quickselect is a selection algorithm " +
			"to find the kth smallest element in an unordered list. It is related to the quicksort sorting algorithm. " +
			"Like quicksort, it was developed by Tony Hoare, and thus is also known as Hoare's selection algorithm. " +
			"Like quicksort, it is efficient in practice and has good average-case performance, but has poor " +
			"worst-case performance. Quickselect and variants is the selection algorithm most often used in efficient " +
			"real-world implementations.\n" +
			"\n" +
			"Quickselect uses the same overall approach as quicksort, choosing one element as a pivot and " +
			"partitioning the data in two based on the pivot, accordingly as less than or greater than the pivot. " +
			"However, instead of recursing into both sides, as in quicksort, quickselect only recurses into one " +
			"side – the side with the element it is searching for. This reduces the average complexity " +
			"from O(n log n) to O(n).\n" +
			"\n" +
			"As with quicksort, quickselect is generally implemented as an in-place algorithm, and beyond selecting " +
			"the k'th element, it also partially sorts the data. See selection algorithm for further discussion " +
			"of the connection with sorting." +
			"\n" +
			"Worst case performance: O(n^2)\n" +
			"Best case performance: O(n)\n" +
			"Average case performance: O(n)" +
			"\n" +
			"source: https://en.wikipedia.org/wiki/Quickselect";

	private static final String QUICKSELECT_SOURCE_CODE = "public static int quickselect" +
			"(int[] array, int left, int right, int n) {\n" +
			"\t\tif (left == right)\n" +
			"\t\t\treturn array[left];\n" +
			"\n" +
			"\t\tfor (;;) {\n" +
			"\t\t\tint pivot = randomPivot(left, right);\n" +
			"\t\t\tpivot = partition(array, left, right, pivot);\n" +
			"\n" +
			"\t\t\tif (n == pivot)\n" +
			"\t\t\t\treturn array[n];\n" +
			"\t\t\telse if (n < pivot)\n" +
			"\t\t\t\tright = pivot - 1;\n" +
			"\t\t\telse\n" +
			"\t\t\t\tleft = pivot + 1;\n" +
			"\t\t}\n" +
			"\t}\n" +
			"\n" +
			"\tpublic static int partition(int[] array, int left, int right, int pivot) {\n" +
			"\t\tint pivotValue = array[pivot];\n" +
			"\t\tswap(array, pivot, right);\n" +
			"\t\tint storeIndex = left;\n" +
			"\n" +
			"\t\tfor (int i = left; i < right; i++) {\n" +
			"\t\t\tif (array[i] < pivotValue) {\n" +
			"\t\t\t\tswap(array, storeIndex, i);\n" +
			"\t\t\t\tstoreIndex++;\n" +
			"\t\t\t}\n" +
			"\t\t}\n" +
			"\t\tswap(array, right, storeIndex);\n" +
			"\t\treturn storeIndex;\n" +
			"\t}\n" +
			"\n" +
			"\tpublic static void swap(int[] array, int a, int b) {\n" +
			"\t\tint tmp = array[a];\n" +
			"\t\tarray[a] = array[b];\n" +
			"\t\tarray[b] = tmp;\n" +
			"\t}\n" +
			"\n" +
			"\tpublic static int randomPivot(int left, int right) {\n" +
			"\t\treturn left + (int) Math.floor(Math.random() * (right - left + 1));\n" +
			"\t}";
	
	private final static Timing defaultDuration = new TicksTiming(30);

	/**
	 * running the algorithm
	 * @param array - our array we are working with
     */
	private void select(int[] array)
	{
		// generate an ArrayProperty
		ArrayProperties arrayProperties = new ArrayProperties();

		// set the visual properties
		arrayProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);
		arrayProperties.set(AnimationPropertiesKeys.FILL_PROPERTY, Color.WHITE);
		arrayProperties.set(AnimationPropertiesKeys.FILLED_PROPERTY, Boolean.TRUE);
		arrayProperties.set(AnimationPropertiesKeys.ELEMENTCOLOR_PROPERTY, Color.BLACK);
		arrayProperties.set(AnimationPropertiesKeys.ELEMHIGHLIGHT_PROPERTY, Color.RED);
		arrayProperties.set(AnimationPropertiesKeys.CELLHIGHLIGHT_PROPERTY, Color.YELLOW);

		// Create Array: coordinates, data, name, display options, default properties
		IntArray iArray = language.newIntArray(new Coordinates(20, 100), array, "intArray", null, arrayProperties);
		language.nextStep();

		// generate a SourceCodeProperty
		SourceCodeProperties scProperties = new SourceCodeProperties();

		// set the visual properties
		scProperties.set(AnimationPropertiesKeys.CONTEXTCOLOR_PROPERTY, Color.BLUE);
		scProperties.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("Consolas", Font.BOLD, 12));
		scProperties.set(AnimationPropertiesKeys.HIGHLIGHTCOLOR_PROPERTY, Color.BLUE);
		scProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);

		// Create SourceCode: coordinates, name, display options, default properties
		SourceCode sourceCode = language.newSourceCode(new Coordinates(40, 140), "sourceCode", null, scProperties);

		// Add the lines to the SourceCode object
		sourceCode.addCodeLine("public int quickSelect(int[] array, int left, int right, int n)", null, 0, null); // 0
		sourceCode.addCodeLine("{", null, 0, null); // 1
		sourceCode.addCodeLine("if (left == right)", null, 1, null); // 2
		sourceCode.addCodeLine("return array[left];", null, 2, null); // 3
		sourceCode.addCodeLine("for (;;)", null, 1, null); // 4
		sourceCode.addCodeLine("{", null, 1, null); // 5
		sourceCode.addCodeLine("int pivot = randomPivot(left, right);", null, 2, null); // 6
		sourceCode.addCodeLine("pivot = partition(array, left, right, pivot);", null, 2, null); // 7
		sourceCode.addCodeLine("if (n == pivot)", null, 2, null); // 8
		sourceCode.addCodeLine("return array[n];", null, 3, null); // 9
		sourceCode.addCodeLine("else if (n < pivot)", null, 2, null); // 10
		sourceCode.addCodeLine("right = pivot - 1;", null, 3, null); // 11
		sourceCode.addCodeLine("else", null, 2, null); // 12
		sourceCode.addCodeLine("left = pivot + 1;", null, 3, null); // 13
		sourceCode.addCodeLine("}", null, 1, null); // 14
		sourceCode.addCodeLine("}", null, 0, null); // 15

		sourceCode.addCodeLine("public int randomPivot(int left, int right)", null, 0, null); // 16
		sourceCode.addCodeLine("{", null, 0, null); // 17
		sourceCode.addCodeLine("return left + (int)Math.floor(Math.random() * (right - left + 1));", null, 1, null); // 18
		sourceCode.addCodeLine("}", null, 0, null); // 19

		sourceCode.addCodeLine("public int partition(int[] array, int left, int right, int pivot)", null, 0, null); // 20
		sourceCode.addCodeLine("{", null, 0, null); // 21
		sourceCode.addCodeLine("int pivotValue = array[pivot];", null, 1, null); // 22
		sourceCode.addCodeLine("swap(array, pivot, right);", null, 1, null); // 23
		sourceCode.addCodeLine("int storeIndex = left;", null, 1, null); // 24
		sourceCode.addCodeLine("for (int i = left; i < right; i++)", null, 1, null); // 25
		sourceCode.addCodeLine("{", null, 1, null); // 26
		sourceCode.addCodeLine("if (array[i] < pivotValue)", null, 2, null); // 27
		sourceCode.addCodeLine("{", null, 2, null); // 28
		sourceCode.addCodeLine("swap(array, storeIndex, i);", null, 3, null); // 29
		sourceCode.addCodeLine("storeIndex++;", null, 3, null); // 30
		sourceCode.addCodeLine("}", null, 2, null); // 31
		sourceCode.addCodeLine("}", null, 1, null); // 32
		sourceCode.addCodeLine("swap(array, right, storeIndex);", null, 1, null); // 33
		sourceCode.addCodeLine("return storeIndex;", null, 1, null); // 34
		sourceCode.addCodeLine("}", null, 0, null); // 35

		sourceCode.addCodeLine("public void swap(int[] array, int a, int b)", null, 0, null); // 36
		sourceCode.addCodeLine("{", null, 0, null); // 37
		sourceCode.addCodeLine("int tmp = array[a];", null, 1, null); // 38
		sourceCode.addCodeLine("array[a] = array[b];", null, 1, null); // 39
		sourceCode.addCodeLine("array[b] = tmp;", null, 1, null); // 40
		sourceCode.addCodeLine("}", null, 0, null); // 41
		
		language.nextStep();

		// Highlight all cells
		iArray.highlightCell(0, iArray.getLength() - 1, null, null);
		try {
			quickSelect(iArray, sourceCode, 0, (iArray.getLength() - 1), randomKSmallest(0, iArray.getLength() - 1));
		} catch (LineNotExistsException e) {
			e.printStackTrace();
		}
		
		sourceCode.hide();
		iArray.hide();
		language.nextStep();
	}

	// counter for the number of pointers used
	private int pointerCounter = 0;
	private String ordinal;

	/**
	 * the quickselect algorithm wrapped by the ANIMAL API
	 * @param array - the array we are working with
	 * @param code - the source code ANIMAL is working with
	 * @param left - the left boundary of the array
	 * @param right - the right boundary of the array
	 * @param n - the k smallest element you are searching for (0 = 1st smallest)
	 * @return k smallest element of the array
	 * @throws LineNotExistsException
     */
	private int quickSelect(IntArray array, SourceCode code, int left, int right, int n) throws LineNotExistsException
	{
		switch (n + 1) {
			case 1:
				ordinal = "st";
				break;
			case 2:
				ordinal = "nd";
				break;
			case 3:
				ordinal = "rd";
				break;
			default:
				ordinal = "th";
		}

		code.highlight(0, 0, false);
		language.nextStep();

		code.unhighlight(0, 0, false);
		language.nextStep();

		code.highlight(2, 0, false);

		if (left == right) {
			return array.getData(left);
		}

		language.nextStep();
		code.unhighlight(2, 0, false);
		code.highlight(4, 0, false);

		pointerCounter++;
		ArrayMarkerProperties pivotPointerProps = new ArrayMarkerProperties();
		pivotPointerProps.set(AnimationPropertiesKeys.LABEL_PROPERTY, "pivot");
		pivotPointerProps.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLUE);
		pivotPointerProps.set(AnimationPropertiesKeys.LONG_MARKER_PROPERTY, true);

		pointerCounter++;
		ArrayMarkerProperties kSmallestProps = new ArrayMarkerProperties();
		kSmallestProps.set(AnimationPropertiesKeys.LABEL_PROPERTY, (n + 1) + ordinal + " smallest");
		kSmallestProps.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.RED);
		kSmallestProps.set(AnimationPropertiesKeys.LONG_MARKER_PROPERTY, true);

		for (;;) {
			language.nextStep();
			code.unhighlight(4, 0, false);
			code.highlight(6, 0, false);

			int pivot = randomPivot(left, right);
			ArrayMarker pivotMarker = language.newArrayMarker(array, pivot, "pivot" + pointerCounter, null, pivotPointerProps);
			pivotMarker.move(pivot, null, defaultDuration);

			language.nextStep();
			code.unhighlight(6, 0, false);
			code.highlight(7, 0, false);

			pivot = partition(array, left, right, pivot, code);

			language.nextStep();
			code.unhighlight(33, 0, false);
			code.highlight(7, 0, false);

			pivotMarker.move(pivot, null, defaultDuration);

			language.nextStep();
			code.unhighlight(7, 0, false);
			code.highlight(8, 0, false);

			ArrayMarker kSmallestMarker = language.newArrayMarker(array, pivot, "kSmallest" + pointerCounter, null, kSmallestProps);
			kSmallestMarker.hide();

			if (n == pivot) {
				pivotMarker.hide();
				kSmallestMarker.move(pivot, null, defaultDuration);
				kSmallestMarker.show();
			}

			if (n == pivot) {
				language.nextStep();
				pivotMarker.hide();
				code.unhighlight(8, 0, false);
				code.highlight(9, 0, false);
				code.unhighlight(9, 0, false);

				kSmallestMarker.hide();

				System.out.println("The " + (n + 1) + ordinal + " smallest element is " + array.getData(n));
				return array.getData(n);

			} else if (n < pivot) {
				language.nextStep();
				code.unhighlight(8, 0, false);
				code.highlight(10, 0, false);

				language.nextStep();
				code.unhighlight(10, 0, false);
				code.highlight(11, 0, false);

				right = pivot - 1;

				code.unhighlight(11, 0, false);
			} else {
				language.nextStep();
				code.unhighlight(8, 0, false);
				code.highlight(12, 0, false);

				language.nextStep();
				code.unhighlight(12, 0, false);
				code.highlight(13, 0, false);

				left = pivot + 1;

				code.unhighlight(13, 0, false);
			}
			language.nextStep();
			array.unhighlightCell(0, array.getLength() - 1, null, null);
			array.highlightCell(left, right, null, null);
			pivotMarker.hide();
		}
	}

	/**
	 *
	 * @param array - the array we are working with
	 * @param left - the left boundary of the array
	 * @param right - the right boundary of the array
	 * @param pivot - the pivot element
	 * @param code - the source code ANIMAL is working with
     * @return storeIndex
     */
	private int partition(IntArray array, int left, int right, int pivot, SourceCode code) {
		language.nextStep();
		code.unhighlight(7, 0, false);
		code.highlight(20, 0, false);

		language.nextStep();
		code.unhighlight(20, 0, false);
		code.highlight(22, 0, false);

		int pivotValue = array.getData(pivot);

		language.nextStep();
		code.unhighlight(22, 0, false);
		code.highlight(23, 0, false);

		language.nextStep();
		code.unhighlight(23, 0, false);
		code.highlight(36, 0, false);

		swap(array, pivot, right);

		language.nextStep();
		code.unhighlight(36, 0, false);
		code.highlight(24, 0, false);

		int storeIndex = left;

		pointerCounter++;
		ArrayMarkerProperties storeIndexProps = new ArrayMarkerProperties();
		storeIndexProps.set(AnimationPropertiesKeys.LABEL_PROPERTY, "storeIndex");
		storeIndexProps.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);
		storeIndexProps.set(AnimationPropertiesKeys.SHORT_MARKER_PROPERTY, true);
		ArrayMarker storeIndexMarker = language.newArrayMarker(array, storeIndex, "storeIndex" + pointerCounter, null, storeIndexProps);

		language.nextStep();

		pointerCounter++;
		ArrayMarkerProperties loopPointerProps = new ArrayMarkerProperties();
		loopPointerProps.set(AnimationPropertiesKeys.LABEL_PROPERTY, "i");
		loopPointerProps.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.MAGENTA);
		ArrayMarker loopMarker = language.newArrayMarker(array, left, "i" + pointerCounter, null, loopPointerProps);

		code.unhighlight(24, 0, false);
		code.highlight(25, 0, false);

		for (int i = left; i < right; i++) {
			language.nextStep();
			loopMarker.move(i, null, defaultDuration);

			if (array.getData(i) < pivotValue) {
				language.nextStep();
				code.unhighlight(25, 0, false);
				code.highlight(27, 0, false);

				language.nextStep();
				code.unhighlight(27, 0, false);
				code.highlight(29, 0, false);

				language.nextStep();
				code.unhighlight(29, 0, false);
				code.highlight(36, 0, false);

				swap(array, storeIndex, i);

				language.nextStep();
				code.unhighlight(36, 0, false);
				code.highlight(30, 0, false);

				storeIndex++;
				storeIndexMarker.move(storeIndex, null, defaultDuration);

				code.unhighlight(30, 0, false);
			}
		}

		language.nextStep();
		loopMarker.hide();
		code.highlight(36, 0, false);
		swap(array, right, storeIndex);

		language.nextStep();
		code.unhighlight(36, 0, false);
		code.highlight(33, 0, false);
		storeIndexMarker.hide();
		return storeIndex;
	}

	/**
	 *
	 * @param array - the array we are working with
	 * @param a - element A you want to swap with element B
	 * @param b - element B you want to swap with elemen A
     */
	private void swap(IntArray array, int a, int b) {
		array.swap(a, b, null, defaultDuration);
	}

	/**
	 *
	 * @param left - the left boundary of the array
	 * @param right - the right boundary of the array
     * @return a random pivot in the boundary left <= pivot <= right
     */
	private int randomPivot(int left, int right) {
		return left + (int) Math.floor(Math.random() * (right - left + 1));
	}

	/**
	 *
	 * @param left - the left boundary of the array
	 * @param right - the right boundary of the array
     * @return a random n for k smallest element
     */
	private int randomKSmallest(int left, int right) {
		return left + (int) Math.floor(Math.random() * (right - left + 1));
	}

	/**
	 * - init language
	 * - create quickselect object
	 * - create array you want to work with
	 * - run the algorithm
	 * - print AnimalScript
     */
	public static void main(String[] args) {
		Language language = Language.getLanguageInstance(AnimationType.ANIMALSCRIPT, "Quickselect", "Yadullah Duman", 640, 480);
		QuickselectGenerator quickselect = new QuickselectGenerator(language);
		int[] array = { 100, 90, 80, 70, 10, 60, 50, 40, 30, 20 };
		quickselect.select(array);
		System.out.println(language);
	}

	public String generate(AnimationPropertiesContainer animationPropertiesContainer, Hashtable<String, Object> hashtable) {
		return null;
	}

	public String getAlgorithmName() {
		return "Quickselect";
	}

	public String getAnimationAuthor() {
		return "Yadullah Duman";
	}

	public String getCodeExample() {
		return QUICKSELECT_SOURCE_CODE;
	}

	public Locale getContentLocale() {
		return Locale.US;
	}

	public String getDescription() {
		return QUICKSELECT_DESCRIPTION;
	}

	public String getFileExtension() {
		return ".asu";
	}

	public GeneratorType getGeneratorType() {
		return new GeneratorType(GeneratorType.GENERATOR_TYPE_SEARCH);
	}

	public String getName() {
		return "Quickselect";
	}

	public String getOutputLanguage() {
		return Generator.JAVA_OUTPUT;
	}

	public void init() {
	}
}
