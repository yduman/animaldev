/*
 * QuickselectGenerator.java
 * Yadullah Duman, 2016 for the Animal project at TU Darmstadt.
 * Copying this file for educational purposes is permitted without further authorization.
 */
package generators.searching;

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
 * @author Yadullah Duman
 *
 */
public class QuickselectGenerator implements Generator {
	private Language language;
	private ArrayProperties arrayProperties;
	private ArrayMarkerProperties kSmallestProps;
	private ArrayMarkerProperties storeIndexProps;
	private ArrayMarkerProperties loopPointerProps;
	private ArrayMarkerProperties pivotPointerProps;
	private SourceCodeProperties scProperties;
	private int[] array;
	private int kSmallest;
	private int pointerCounter = 0;
	private String ordinal;
	public final static Timing defaultDuration = new TicksTiming(30);

	public QuickselectGenerator() {
	}
	
	public QuickselectGenerator(Language language) {
		this.language = language;
		language.setStepMode(true);
	}

	public static final String QUICKSELECT_DESCRIPTION = "In computer science, quickselect is a selection algorithm " +
			"to find the k-th smallest element in an unordered list. It is related to the quicksort sorting algorithm. " +
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
			"Average case performance: O(n)\n" +
			"\n" +
			"source: https://en.wikipedia.org/wiki/Quickselect";

	public static final String QUICKSELECT_SOURCE_CODE = ""
			+ "// kSmallest = 0 equals 1st smallest\n"
			+ "public int quickselect (int[] array, int left, int right, int kSmallest) {\n" +
			"    if (left == right) \n" +
			"        return array[left]; \n" +
			"\n" +
			"    for (;;) { \n" +
			"        int pivot = randomPivot(left, right); \n" +
			"        pivot = partition(array, left, right, pivot); \n" +
			"\n" +
			"        if (kSmallest == pivot) \n" +
			"            return array[kSmallest]; \n" +
			"        else if (kSmallest < pivot) \n" +
			"            right = pivot - 1; \n" +
			"        else \n" +
			"            left = pivot + 1; \n" +
			"    } \n" +
			"} \n" +
			" \n" +
			"public int partition(int[] array, int left, int right, int pivot) { \n" +
			"    int pivotValue = array[pivot]; \n" +
			"    swap(array, pivot, right); \n" +
			"    int storeIndex = left; \n" +
			"\n" +
			"    for (int i = left; i < right; i++) { \n" +
			"        if (array[i] < pivotValue) { \n" +
			"            swap(array, storeIndex, i); \n" +
			"            storeIndex++; \n" +
			"        } \n" +
			"    } \n" +
			"    swap(array, right, storeIndex); \n" +
			"    return storeIndex; \n" +
			"} \n" +
			"\n" +
			"public void swap(int[] array, int a, int b) { \n" +
			"    int tmp = array[a]; \n" +
			"    array[a] = array[b]; \n" +
			"    array[b] = tmp; \n" +
			"}\n" +
			"\n" +
			"public int randomPivot(int left, int right) { \n" +
			"    return return left + (int) Math.floor(Math.random() * (right - left + 1));\n" +
			"}";

	/**
	 * initializing all properties, creating all primitives and executing the algorithm
	 *
	 * @param array - our array we are working with
     */
	public void start(int[] array)
	{
		arrayProperties = new ArrayProperties();
		arrayProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);
		arrayProperties.set(AnimationPropertiesKeys.FILL_PROPERTY, Color.WHITE);
		arrayProperties.set(AnimationPropertiesKeys.FILLED_PROPERTY, Boolean.TRUE);
		arrayProperties.set(AnimationPropertiesKeys.ELEMENTCOLOR_PROPERTY, Color.BLACK);
		arrayProperties.set(AnimationPropertiesKeys.ELEMHIGHLIGHT_PROPERTY, Color.RED);
		arrayProperties.set(AnimationPropertiesKeys.CELLHIGHLIGHT_PROPERTY, Color.YELLOW);

		IntArray iArray = language.newIntArray(new Coordinates(20, 100), array, "intArray", null, arrayProperties);
		language.nextStep();

		scProperties = new SourceCodeProperties();
		scProperties.set(AnimationPropertiesKeys.CONTEXTCOLOR_PROPERTY, Color.BLUE);
		scProperties.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("SansSerif", Font.BOLD, 12));
		scProperties.set(AnimationPropertiesKeys.HIGHLIGHTCOLOR_PROPERTY, Color.BLUE);
		scProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);
		
		pointerCounter++;
		pivotPointerProps = new ArrayMarkerProperties();
		pivotPointerProps.set(AnimationPropertiesKeys.LABEL_PROPERTY, "pivot");
		pivotPointerProps.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLUE);
		pivotPointerProps.set(AnimationPropertiesKeys.LONG_MARKER_PROPERTY, true);

		pointerCounter++;
		kSmallestProps = new ArrayMarkerProperties();
		kSmallestProps.set(AnimationPropertiesKeys.LABEL_PROPERTY, (kSmallest + 1) + ordinal + " smallest");
		kSmallestProps.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.RED);
		kSmallestProps.set(AnimationPropertiesKeys.LONG_MARKER_PROPERTY, true);
		
		pointerCounter++;
		storeIndexProps = new ArrayMarkerProperties();
		storeIndexProps.set(AnimationPropertiesKeys.LABEL_PROPERTY, "storeIndex");
		storeIndexProps.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);
		storeIndexProps.set(AnimationPropertiesKeys.SHORT_MARKER_PROPERTY, true);
		
		pointerCounter++;
		loopPointerProps = new ArrayMarkerProperties();
		loopPointerProps.set(AnimationPropertiesKeys.LABEL_PROPERTY, "i");
		loopPointerProps.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.MAGENTA);

		SourceCode sourceCode = language.newSourceCode(new Coordinates(40, 140), "sourceCode", null, scProperties);
		sourceCode.addCodeLine("public int quickSelect(int[] array, int left, int right, int kSmallest)", null, 0, null); // 0
		sourceCode.addCodeLine("{", null, 0, null); // 1
		sourceCode.addCodeLine("if (left == right)", null, 1, null); // 2
		sourceCode.addCodeLine("return array[left];", null, 2, null); // 3
		sourceCode.addCodeLine("for (;;)", null, 1, null); // 4
		sourceCode.addCodeLine("{", null, 1, null); // 5
		sourceCode.addCodeLine("int pivot = randomPivot(left, right);", null, 2, null); // 6
		sourceCode.addCodeLine("pivot = partition(array, left, right, pivot);", null, 2, null); // 7
		sourceCode.addCodeLine("if (kSmallest == pivot)", null, 2, null); // 8
		sourceCode.addCodeLine("return array[kSmallest];", null, 3, null); // 9
		sourceCode.addCodeLine("else if (kSmallest < pivot)", null, 2, null); // 10
		sourceCode.addCodeLine("right = pivot - 1;", null, 3, null); // 11
		sourceCode.addCodeLine("else", null, 2, null); // 12
		sourceCode.addCodeLine("left = pivot + 1;", null, 3, null); // 13
		sourceCode.addCodeLine("}", null, 1, null); // 14
		sourceCode.addCodeLine("}", null, 0, null); // 15
		sourceCode.addCodeLine("public int randomPivot(int left, int right)", null, 0, null); // 16
		sourceCode.addCodeLine("{", null, 0, null); // 17
		sourceCode.addCodeLine("return return left + (int) Math.floor(Math.random() * (right - left + 1));", null, 1, null); // 18
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

		iArray.highlightCell(0, iArray.getLength() - 1, null, null);
			
		quickSelect(iArray, sourceCode, 0, (iArray.getLength() - 1), this.kSmallest);
		
		sourceCode.hide();
		iArray.hide();
		language.nextStep();
	}

	/**
	 * the quickselect algorithm wrapped up by the ANIMAL API
	 *
	 * @param array - the array we are working with
	 * @param code - the source code ANIMAL is working with
	 * @param left - the left boundary of the array
	 * @param right - the right boundary of the array
	 * @param kSmallest - the k smallest element you are searching for (0 = 1st smallest)
	 * @return k smallest element of the array
	 * @throws LineNotExistsException
     */
	public int quickSelect(IntArray array, SourceCode code, int left, int right, int kSmallest)
	{
		switch (kSmallest + 1) {
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

			if (kSmallest == pivot) {
				pivotMarker.hide();
				kSmallestMarker.move(pivot, null, defaultDuration);
				kSmallestMarker.show();
			}

			if (kSmallest == pivot) {
				language.nextStep();
				pivotMarker.hide();
				
				code.unhighlight(8, 0, false);
				code.highlight(9, 0, false);
				code.unhighlight(9, 0, false);

				kSmallestMarker.hide();
				return array.getData(kSmallest);

			} else if (kSmallest < pivot) {
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
	public int partition(IntArray array, int left, int right, int pivot, SourceCode code) {
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
		ArrayMarker storeIndexMarker = language.newArrayMarker(array, storeIndex, "storeIndex" + pointerCounter, null, storeIndexProps);

		language.nextStep();
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
	public void swap(IntArray array, int a, int b) {
		array.swap(a, b, null, defaultDuration);
	}

	/**
	 * 
	 * @param left - left border of array
	 * @param right - right border of array
	 * @return a random pivot element
	 */
	public int randomPivot(int left, int right) {
		return left + (int) Math.floor(Math.random() * (right - left + 1));
	}

	
//	public static void main(String[] args) {
//		Language language = Language.getLanguageInstance(AnimationType.ANIMALSCRIPT, "Quickselect", "Yadullah Duman", 640, 480);
//		QuickselectGenerator quickselect = new QuickselectGenerator();
//		int[] array = { 100, 90, 80, 70, 10, 60, 50, 40, 30, 20 };
//		quickselect.init();
//		quickselect.select(array);
//		// System.out.println(language);
//	}

	public String generate(AnimationPropertiesContainer props, Hashtable<String, Object> primitives) {
		kSmallestProps 		= (ArrayMarkerProperties)props.getPropertiesByName("kSmallestProps");
		storeIndexProps 	= (ArrayMarkerProperties)props.getPropertiesByName("storeIndexProps");
		loopPointerProps 	= (ArrayMarkerProperties)props.getPropertiesByName("loopPointerProps");
		arrayProperties 	= (ArrayProperties)props.getPropertiesByName("arrayProperties");
		pivotPointerProps	= (ArrayMarkerProperties)props.getPropertiesByName("pivotPointerProps");
		scProperties 		= (SourceCodeProperties)props.getPropertiesByName("scProperties");
		array 				= (int[])primitives.get("array");
		kSmallest			= (int)primitives.get("kSmallest");

		init();
		start(array);
		
		return language.toString();
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
		return "asu";
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
		language = Language.getLanguageInstance(AnimationType.ANIMALSCRIPT, this.getAlgorithmName(), this.getAnimationAuthor(), 800, 600);
		language.setStepMode(true);
	}
}
