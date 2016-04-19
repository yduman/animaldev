package dev;

import java.awt.Color;
import java.awt.Font;

import algoanim.exceptions.LineNotExistsException;
import algoanim.primitives.ArrayMarker;
import algoanim.primitives.IntArray;
import algoanim.primitives.SourceCode;
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
public class QuickselectAPIGenerator {
	private Language language;
	
	public QuickselectAPIGenerator(Language l) {
		language = l;
		language.setStepMode(true);
	}
	
	private static final String QUICKSELECT_DESCRIPTION = "this will be the qs decription later";
	private static final String QUICKSELECT_SOURCE_CODE = "this will be the qs source code later";
	
	public final static Timing defaultDuration = new TicksTiming(30);
	
	public void select(int[] array)
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


	private int quickSelect(IntArray array, SourceCode code, int left, int right, int n) throws LineNotExistsException
	{	
		// highlight first line
		// line, column, use context color?, display options, duration
		code.highlight(0, 0, false);
		language.nextStep();
		
		code.toggleHighlight(0, 0, false, 1, 0);

		// TODO: vielleicht left und right marker loeschen
		pointerCounter++;
		ArrayMarkerProperties arrayLeftPointerProperties = new ArrayMarkerProperties();
		arrayLeftPointerProperties.set(AnimationPropertiesKeys.LABEL_PROPERTY, "left");
		arrayLeftPointerProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);

		// Create ArrayMarker for 'left': Array, current index, name, display options, properties
		ArrayMarker leftMarker = language.newArrayMarker(array, 0, "left" + pointerCounter, null, arrayLeftPointerProperties);

		pointerCounter++;
		ArrayMarkerProperties arrayRightPointerProperties = new ArrayMarkerProperties();
		arrayRightPointerProperties.set(AnimationPropertiesKeys.LABEL_PROPERTY, "right");
		arrayRightPointerProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);

		// Create ArrayMarker for 'right'
		ArrayMarker rightMarker = language.newArrayMarker(array, array.getLength() - 1, "right", null, arrayRightPointerProperties);
		
		language.nextStep();
		code.toggleHighlight(1, 0, false, 2, 0);

		if (left == right) {
			language.nextStep();
			code.toggleHighlight(2, 0, false, 3, 0);
			// TODO: return Fall ?
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

			// ************* PARTITION ************* //
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
				System.out.println("The " + (n + 1) + " smallest element is " + array.getData(n));
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
				rightMarker.move(right, null, defaultDuration);
			}
			else {
				language.nextStep();
				code.highlight(12, 0, false);

				language.nextStep();
				code.unhighlight(12, 0, false);
				code.highlight(13, 0, false);

				language.nextStep();
				left = pivot + 1;
				leftMarker.move(left, null, defaultDuration);
			}
		}
	}

	private int randomPivot(int left, int right) {
		return left + (int) Math.floor(Math.random() * (right - left + 1));
	}
}
