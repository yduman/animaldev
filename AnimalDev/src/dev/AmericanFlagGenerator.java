package dev;

import algoanim.exceptions.LineNotExistsException;
import algoanim.primitives.IntArray;
import algoanim.primitives.SourceCode;
import algoanim.primitives.generators.AnimationType;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.ArrayProperties;
import algoanim.properties.SourceCodeProperties;
import algoanim.util.Coordinates;
import algoanim.util.TicksTiming;
import algoanim.util.Timing;
import generators.framework.Generator;
import generators.framework.GeneratorType;
import generators.framework.properties.AnimationPropertiesContainer;

import java.awt.*;
import java.util.Hashtable;
import java.util.Locale;

/**
 *
 * @author Yadullah Duman <yadullah.duman@gmail.com>
 *
 */
public class AmericanFlagGenerator implements Generator {
    private Language language;

    private AmericanFlagGenerator(Language l) {
        language = l;
        language.setStepMode(true);
    }

    private static final String AFS_DESCRIPTION = "An American flag sort is an efficient, in-place variant of radix " +
            "sort that distributes items into hundreds of buckets. [...] With some optimizations, it is twice as fast " +
            "as quicksort. [...] The name comes by analogy with the Dutch national flag problem in the last step: " +
            "efficiently partition the array into many \"stripes\"." +
            "\n" +
            "American flag sort can only sort integers (or objects that can be interpreted as integers). " +
            "In-place sorting algorithms, including American flag sort, run without allocating a significant " +
            "amount of memory beyond that used by the original array. This is a significant advantage, both in " +
            "memory savings and in time saved copying the array." +
            "\n" +
            "American flag sort works by successively dividing a list of objects into buckets based on the first " +
            "digit of their base-N representation (the base used is referred to as the radix). When N is 2, each " +
            "object can be swapped into the correct bucket by using the Dutch national flag algorithm. " +
            "When N is larger, however, objects cannot be immediately swapped into place, because it is unknown " +
            "where each bucket should begin and end. American flag sort gets around this problem by making two " +
            "passes through the array. The first pass counts the number of objects that belong in each of the N " +
            "buckets. The beginning and end of each bucket in the original array is then computed as the sum of " +
            "sizes of preceding buckets. The second pass swaps each object into place.";

    private static final String AFS_SOURCE_CODE = "" +
            "public static void sort(int[] array) {\n" +
            "\t\tfinal int RADIX = 4;\n" +
            "\n" +
            "\t\tint[] counts = new int[RADIX];\n" +
            "\t\tfor (int num : array)\n" +
            "\t\t\tcounts[num % RADIX]++;\n" +
            "\n" +
            "\t\tint[] offsets = new int[RADIX];\n" +
            "\t\tfor (int i = 1; i < RADIX; i++)\n" +
            "\t\t\toffsets[i] = offsets[i - 1] + counts[i - 1];\n" +
            "\n" +
            "\t\tfor (int i = 0; i < RADIX; i++) {\n" +
            "\t\t\twhile (counts[i] > 0) {\n" +
            "\t\t\t\tint origin = offsets[i];\n" +
            "\t\t\t\tint from = origin;\n" +
            "\t\t\t\tint num = array[from];\n" +
            "\t\t\t\tarray[from] = -1;\n" +
            "\n" +
            "\t\t\t\tdo {\n" +
            "\t\t\t\t\tint to = offsets[num % RADIX]++;\n" +
            "\t\t\t\t\tcounts[num % RADIX]--;\n" +
            "\t\t\t\t\tswap(array, to, num);\n" +
            "\t\t\t\t\tfrom = to;\n" +
            "\t\t\t\t} while (from != origin);\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t}\n" +
            "\n" +
            "\tpublic static void swap(int[] array, int a, int b) {\n" +
            "\t\tint tmp = array[a];\n" +
            "\t\tarray[a] = array[b];\n" +
            "\t\tarray[b] = tmp;\n" +
            "\t}";

    private final static Timing defaultDuration = new TicksTiming(50);

    private void sort(int[] array) {
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

        sourceCode.addCodeLine("public void americanFlagSort(int[] array) {", null, 0, null); // 0
        sourceCode.addCodeLine("final int RADIX = 10;", null, 1, null); // 1
        sourceCode.addCodeLine("int[] counts = new int[RADIX];", null, 1, null); // 2
        sourceCode.addCodeLine("for (int num : array)", null, 1, null); // 3
        sourceCode.addCodeLine("counts[num % RADIX]++;", null, 2, null); // 4
        sourceCode.addCodeLine("int[] offsets = new int[RADIX];", null, 1, null); // 5
        sourceCode.addCodeLine("for (int i = 1; i < RADIX; i++)", null, 1, null); // 6
        sourceCode.addCodeLine("offsets[i] = offsets[i - 1] + counts[i - 1];", null, 2, null); // 7
        sourceCode.addCodeLine("for (int i = 0; i < RADIX; i++) {", null, 1, null); // 8
        sourceCode.addCodeLine("while (counts[i] > 0) {", null, 2, null); // 9
        sourceCode.addCodeLine("int origin = offsets[i];", null, 3, null); // 10
        sourceCode.addCodeLine("int from = origin;", null, 3, null); // 11
        sourceCode.addCodeLine("int num = array[from];", null, 3, null); // 12
        sourceCode.addCodeLine("array[from] = -1;", null, 3, null); // 13
        sourceCode.addCodeLine("do {", null, 3, null); // 14
        sourceCode.addCodeLine("int to = offsets[num % RADIX]++;", null, 4, null); // 15
        sourceCode.addCodeLine("counts[num % RADIX]--;", null, 4, null); // 16
        sourceCode.addCodeLine("int tmp = array[to];", null, 4, null); // 17
        sourceCode.addCodeLine("num = tmp;", null, 4, null);
        sourceCode.addCodeLine("array[to] = num;", null, 4, null);
        sourceCode.addCodeLine("num = tmp;", null, 4, null);
        sourceCode.addCodeLine("from = to;", null, 4, null); // 18
        sourceCode.addCodeLine("} while (from != origin);", null, 3, null); // 19
        sourceCode.addCodeLine("}", null, 2, null); // 20
        sourceCode.addCodeLine("}", null, 1, null); // 21
        sourceCode.addCodeLine("}", null, 0, null); // 22

        language.nextStep();
        // Highlight all cells
        iArray.highlightCell(0, iArray.getLength() - 1, null, null);

        try {
            americanFlagSort(iArray);
        } catch (LineNotExistsException e) {
            e.printStackTrace();
        }

        sourceCode.hide();
        iArray.hide();
        language.nextStep();
    }

    private void americanFlagSort(IntArray array) throws LineNotExistsException {
        // TODO: implement correct algorithm
    }

    public static void main(String[] args) {
        Language language = Language.getLanguageInstance(AnimationType.ANIMALSCRIPT, "American Flag Sort",
                "Yadullah Duman", 640, 480);
        AmericanFlagGenerator americanFlag = new AmericanFlagGenerator(language);
        int[] array = { 100, 90, 80, 60, 10, 50, 40, 30, 20 };
        americanFlag.sort(array);
        System.out.println(language);
    }

    public String generate(AnimationPropertiesContainer animationPropertiesContainer, Hashtable<String, Object> hashtable) {
        return null;
    }

    public String getAlgorithmName() {
        return "American Flag Sort";
    }

    public String getAnimationAuthor() {
        return "Yadullah Duman";
    }

    public String getCodeExample() {
        return AFS_SOURCE_CODE;
    }

    public Locale getContentLocale() {
        return Locale.US;
    }

    public String getDescription() {
        return AFS_DESCRIPTION;
    }

    public String getFileExtension() {
        return ".asu";
    }

    public GeneratorType getGeneratorType() {
        return new GeneratorType(GeneratorType.GENERATOR_TYPE_SORT);
    }

    public String getName() {
        return "American Flag Sort";
    }

    public String getOutputLanguage() {
        return Generator.JAVA_OUTPUT;
    }

    public void init() {
    }
}
