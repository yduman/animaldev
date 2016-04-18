package dev;

import java.awt.Color;
import java.awt.Font;

import algoanim.exceptions.LineNotExistsException;
import algoanim.primitives.IntArray;
import algoanim.primitives.SourceCode;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
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
	
	public void select(int[] array) {
		ArrayProperties arrayProperties = new ArrayProperties();
		// setting visual properties
		arrayProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);
		arrayProperties.set(AnimationPropertiesKeys.FILL_PROPERTY, Color.WHITE);
		arrayProperties.set(AnimationPropertiesKeys.FILLED_PROPERTY, Boolean.TRUE);
		arrayProperties.set(AnimationPropertiesKeys.ELEMENTCOLOR_PROPERTY, Color.BLACK);
		arrayProperties.set(AnimationPropertiesKeys.ELEMHIGHLIGHT_PROPERTY, Color.RED);
		arrayProperties.set(AnimationPropertiesKeys.CELLHIGHLIGHT_PROPERTY, Color.YELLOW);
		
		// creating array object
		IntArray iArray = language.newIntArray(new Coordinates(20, 100), array, "intArray", null, arrayProperties);
		language.nextStep();
		
		SourceCodeProperties scProperties = new SourceCodeProperties();
		scProperties.set(AnimationPropertiesKeys.CONTEXTCOLOR_PROPERTY, Color.BLUE);
		scProperties.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("Monospaced", Font.PLAIN, 12));
		scProperties.set(AnimationPropertiesKeys.HIGHLIGHTCOLOR_PROPERTY, Color.RED);
		scProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);
		
		SourceCode sourceCode = language.newSourceCode(new Coordinates(40, 140), "sourceCode", null, scProperties);
		sourceCode.addCodeLine("public int quickSelect(int[] array, int left, int right, int n)", null, 0, null);
		sourceCode.addCodeLine("{", null, 0, null);
		sourceCode.addCodeLine("if (left == right)", null, 1, null);
		sourceCode.addCodeLine("return array[left];", null, 2, null);
		sourceCode.addCodeLine("for (;;)", null, 1, null);
		sourceCode.addCodeLine("{", null, 1, null);
		sourceCode.addCodeLine("int pivot = randomPivot(left, right);", null, 2, null);
		sourceCode.addCodeLine("pivot = partition(array, left, right, pivot);", null, 2, null);
		sourceCode.addCodeLine("if (n == pivot)", null, 2, null);
		sourceCode.addCodeLine("return array[n];", null, 3, null);
		sourceCode.addCodeLine("else if (n < pivot)", null, 2, null);
		sourceCode.addCodeLine("right = pivot - 1;", null, 3, null);
		sourceCode.addCodeLine("else", null, 2, null);
		sourceCode.addCodeLine("left = pivot + 1;", null, 3, null);
		sourceCode.addCodeLine("}", null, 1, null);
		sourceCode.addCodeLine("}", null, 0, null);
		
		sourceCode.addCodeLine("\n", null, 0, null);
		sourceCode.addCodeLine("public int randomPivot(int left, int right)", null, 0, null);
		sourceCode.addCodeLine("{", null, 0, null);
		sourceCode.addCodeLine("return left + (int)Math.floor(Math.random() * (right - left + 1));", null, 1, null);
		sourceCode.addCodeLine("}", null, 0, null);
		
		sourceCode.addCodeLine("\n", null, 0, null);
		sourceCode.addCodeLine("public int partition(int[] array, int left, int right, int pivot)", null, 0, null);
		sourceCode.addCodeLine("{", null, 0, null);
		sourceCode.addCodeLine("int pivotValue = array[pivot];", null, 1, null);
		sourceCode.addCodeLine("swap(array, pivot, right);", null, 1, null);
		sourceCode.addCodeLine("int storeIndex = left;", null, 1, null);
		sourceCode.addCodeLine("for (int i = left; i < right; i++)", null, 1, null);
		sourceCode.addCodeLine("{", null, 1, null);
		sourceCode.addCodeLine("if (array[i] < pivotValue)", null, 2, null);
		sourceCode.addCodeLine("{", null, 2, null);
		sourceCode.addCodeLine("swap(array, storeIndex, i);", null, 3, null);
		sourceCode.addCodeLine("storeIndex++;", null, 3, null);
		sourceCode.addCodeLine("}", null, 2, null);
		sourceCode.addCodeLine("}", null, 1, null);
		sourceCode.addCodeLine("return storeIndex;", null, 1, null);
		sourceCode.addCodeLine("}", null, 0, null);
		
		sourceCode.addCodeLine("\n", null, 0, null);
		sourceCode.addCodeLine("public void swap(int[] array, int a, int b)", null, 0, null);
		sourceCode.addCodeLine("{", null, 0, null);
		sourceCode.addCodeLine("int tmp = array[a];", null, 1, null);
		sourceCode.addCodeLine("array[a] = array[b];", null, 1, null);
		sourceCode.addCodeLine("array[b] = tmp;", null, 1, null);
		sourceCode.addCodeLine("}", null, 0, null);
		
		language.nextStep();
		
		iArray.highlightCell(0, iArray.getLength() - 1, null, null);
		try {
			quickSelect(iArray, sourceCode, 0, (iArray.getLength() - 1));
		} catch (LineNotExistsException e) {
			e.printStackTrace();
		}
		
		sourceCode.hide();
		iArray.hide();
		language.nextStep();
	}
	
	private int pointerCounter = 0;

	private void quickSelect(IntArray array, SourceCode code, int left, int right) throws LineNotExistsException
	{	
		// highlight first line
		// line, column, use context color?, display options, duration
		code.highlight(0, 0, false);
		language.nextStep();
		
		
	}
}
