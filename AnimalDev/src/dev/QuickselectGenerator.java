package dev;

import java.awt.Color;
import java.awt.Font;

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

/**
 * 
 * @author Yadullah Duman <yadullah.duman@gmail.com>
 *
 */
public class QuickselectGenerator {
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
		scProperties.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("Monospaced", Font.PLAIN, 12));
		scProperties.set(AnimationPropertiesKeys.HIGHLIGHTCOLOR_PROPERTY, Color.RED);
		scProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);

		// Create SourceCode: coordinates, name, display options, default properties
		SourceCode sourceCode = language.newSourceCode(new Coordinates(40, 140), "sourceCode", null, scProperties);

		// Add the lines to the SourceCode object
		// quickselect()
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

		// randomPivot()
		sourceCode.addCodeLine("\n", null, 0, null); // 16
		sourceCode.addCodeLine("public int randomPivot(int left, int right)", null, 0, null); // 17
		sourceCode.addCodeLine("{", null, 0, null); // 18
		sourceCode.addCodeLine("return left + (int)Math.floor(Math.random() * (right - left + 1));", null, 1, null); // 19
		sourceCode.addCodeLine("}", null, 0, null); // 20

		// partition()
		sourceCode.addCodeLine("\n", null, 0, null); // 21
		sourceCode.addCodeLine("public int partition(int[] array, int left, int right, int pivot)", null, 0, null); // 22
		sourceCode.addCodeLine("{", null, 0, null); // 23
		sourceCode.addCodeLine("int pivotValue = array[pivot];", null, 1, null); // 24
		sourceCode.addCodeLine("swap(array, pivot, right);", null, 1, null); // 25
		sourceCode.addCodeLine("int storeIndex = left;", null, 1, null); // 26
		sourceCode.addCodeLine("for (int i = left; i < right; i++)", null, 1, null); // 27
		sourceCode.addCodeLine("{", null, 1, null); // 28
		sourceCode.addCodeLine("if (array[i] < pivotValue)", null, 2, null); // 29
		sourceCode.addCodeLine("{", null, 2, null); // 30
		sourceCode.addCodeLine("swap(array, storeIndex, i);", null, 3, null); // 31
		sourceCode.addCodeLine("storeIndex++;", null, 3, null); // 32
		sourceCode.addCodeLine("}", null, 2, null); // 33
		sourceCode.addCodeLine("}", null, 1, null); // 34
		sourceCode.addCodeLine("return storeIndex;", null, 1, null); // 35
		sourceCode.addCodeLine("}", null, 0, null); // 36

		// swap()
		sourceCode.addCodeLine("\n", null, 0, null); // 37
		sourceCode.addCodeLine("public void swap(int[] array, int a, int b)", null, 0, null); // 38
		sourceCode.addCodeLine("{", null, 0, null); // 39
		sourceCode.addCodeLine("int tmp = array[a];", null, 1, null); // 40
		sourceCode.addCodeLine("array[a] = array[b];", null, 1, null); // 41
		sourceCode.addCodeLine("array[b] = tmp;", null, 1, null); // 42
		sourceCode.addCodeLine("}", null, 0, null); // 43
		
		language.nextStep();

		// Highlight all cells
		iArray.highlightCell(0, iArray.getLength() - 1, null, null);
		try {
			// start the algorithm
			// TODO: random value for n
			quickSelect(iArray, sourceCode, 0, (iArray.getLength() - 1), 1);
		} catch (LineNotExistsException e) {
			e.printStackTrace();
		}
		
		sourceCode.hide();
		iArray.hide();
		language.nextStep();
	}

	// counter for the number of pointers used
	private int pointerCounter = 0;
	private String ordinal = "";


	private int quickSelect(IntArray array, SourceCode code, int left, int right, int n) throws LineNotExistsException
	{	
		// highlight first line
		// line, column, use context color?, display options, duration
		code.highlight(0, 0, false);
		language.nextStep();
		
		code.toggleHighlight(0, 0, false, 1, 0);

		language.nextStep();
		code.toggleHighlight(1, 0, false, 2, 0);

		if (left == right) {
			language.nextStep();
			code.toggleHighlight(2, 0, false, 3, 0);
			System.out.println("The " + (n + 1) + ordinal + " smallest element is " + array.getData(n));
			return array.getData(n);
		}

		language.nextStep();
		code.toggleHighlight(2, 0, false, 4, 0);
		language.nextStep();
		for(;;) {
			code.toggleHighlight(4, 0, false, 6, 0);
			int pivot = randomPivot(left, right);

			pointerCounter++;
			ArrayMarkerProperties arrayPivotPointerProperties = new ArrayMarkerProperties();
			arrayPivotPointerProperties.set(AnimationPropertiesKeys.LABEL_PROPERTY, "pivot");
			arrayPivotPointerProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLUE);

			// Create ArrayMarker for 'pivot' and move him to pivot-position
			ArrayMarker pivotMarker = language.newArrayMarker(array, pivot, "pivot" + pointerCounter, null, arrayPivotPointerProperties);
			pivotMarker.move(pivot, null, defaultDuration);

			language.nextStep();
			code.unhighlight(6, 0, false);
			code.highlight(22, 0, false);

			// ------------------------ PARTITION ------------------------ //
			language.nextStep();
			code.unhighlight(22, 0, false);
			code.highlight(24, 0, false);
			int pivotValue = array.getData(pivot);

			language.nextStep();
			code.unhighlight(24, 0, false);
			code.highlight(25, 0, false);
			array.swap(pivot, right, null, defaultDuration);

			language.nextStep();
			code.unhighlight(25, 0, false);
			code.highlight(26, 0, false);
			int storeIndex = left;

			language.nextStep();
			code.unhighlight(26, 0, false);

			pointerCounter++;
			ArrayMarkerProperties arrayIPointerProperties = new ArrayMarkerProperties();
			arrayIPointerProperties.set(AnimationPropertiesKeys.LABEL_PROPERTY, "i");
			arrayIPointerProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.RED);

			// Create ArrayMarker for 'i': Array, current index, name, display options, properties
			ArrayMarker iMarker = language.newArrayMarker(array, left, "i" + pointerCounter, null, arrayIPointerProperties);

			for (int i = left; i < right; i++) {
				code.highlight(27, 0, false);
				iMarker.move(i, null, defaultDuration);

				language.nextStep();
				if (array.getData(i) < pivotValue) {
					code.unhighlight(27, 0, false);
					code.highlight(29, 0, false);
					array.swap(storeIndex, i, null, defaultDuration);
					storeIndex++;
				}
			}

			language.nextStep();
			code.unhighlight(27, 0, false);
			code.highlight(35, 0, false);

			language.nextStep();
			code.unhighlight(35, 0, false);
			// ------------------------ PARTITION ------------------------ //

			code.highlight(7, 0, false);
			pivot = storeIndex;
			pivotMarker.move(pivot, null, defaultDuration);

			language.nextStep();
			code.unhighlight(7, 0, false);

			if (n == pivot) {
				language.nextStep();
				code.highlight(8, 0, false);

				language.nextStep();
				code.unhighlight(8, 0, false);
				code.highlight(9, 0, false);

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
				System.out.println("The " + (n + 1) + ordinal + " smallest element is " + array.getData(n));

				language.nextStep();
				code.unhighlight(9, 0, false);

				return array.getData(n);
			}
			else if (n < pivot) {
				language.nextStep();
				code.highlight(10, 0, false);

				language.nextStep();
				code.unhighlight(10, 0, false);
				code.highlight(11, 0, false);

				language.nextStep();
				right = pivot - 1;

				language.nextStep();
				code.unhighlight(11, 0, false);
			}
			else {
				language.nextStep();
				code.highlight(12, 0, false);

				language.nextStep();
				code.unhighlight(12, 0, false);
				code.highlight(13, 0, false);

				language.nextStep();
				left = pivot + 1;

				language.nextStep();
				code.unhighlight(13, 0, false);
			}
			language.nextStep();
			code.highlight(14, 0, false);

			language.nextStep();
			code.highlight(15, 0, false);

			language.nextStep();
			array.unhighlightCell(left, right, null, null);

			language.nextStep();
			pivotMarker.hide();
		}
	}

	private int randomPivot(int left, int right) {
		return left + (int) Math.floor(Math.random() * (right - left + 1));
	}

	protected String getAlgorithmDescription() {
		return QUICKSELECT_DESCRIPTION;
	}

	protected String getAlgorithmCode() {
		return QUICKSELECT_SOURCE_CODE;
	}

	public String getAlgorithmName() {
		return "Quickselect (pivot = random)";
	}

	public static void main(String[] args) {
		Language language = Language.getLanguageInstance(AnimationType.ANIMALSCRIPT, "Quickselect",
				"Yadullah Duman", 640, 480);
		QuickselectGenerator quickselect = new QuickselectGenerator(language);
		int[] array = { 52, 13, 5, 12, 76, 1 };
		quickselect.select(array);
		System.out.println(language);
	}
}
