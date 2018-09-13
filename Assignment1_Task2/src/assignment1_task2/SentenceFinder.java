
/**
 *
 * @author   Muhammad Ahsan Anjum Butt
 * @email l134169@lhr.nu.edu.pk
 * @section CS-B
 */
import java.util.ArrayList;
import java.util.Scanner;

public class SentenceFinder
{

    private static int min, max;
    private static String text = "";

    private static void takeInput(String[] arg)
    {
	// if no arguments passed through command-line, take input using Scanner
	// otherwise use the command-line arguments
	if (arg.length == 0)
	{
	    Scanner scanner = new Scanner(System.in);
	    System.out.print("Enter Min value:    ");
	    min = scanner.nextInt();
	    System.out.println("Value entered for Min is:    " + min + "\n");

	    System.out.print("Enter Max value:    ");
	    max = scanner.nextInt();
	    System.out.println("Value entered for Max is:    " + max + "\n");

	    System.out.print("Enter text (enter empty line when done entering text):    ");
	    String temp;
	    scanner.nextLine();
	    while ((temp = scanner.nextLine()) != null && !temp.isEmpty())
	    {
		text = text + '\n' + temp;
	    }
	    text = text.trim();
//          text = "Black is white. Day is night. Understanding is ignorance.\n" + "Truth is fiction. Safety is danger.";
	    System.out.println("Text entered is:    " + text + "\n");
	}
	else
	{
	    min = Integer.parseInt(arg[1]);
	    max = Integer.parseInt(arg[2]);
	    for (int i = 3; i < arg.length; i++)
	    {
		text = arg[i] + ' ';
	    }
	    text = text.trim();
	}
    }

    private static String[] parse()
    {
	// replace EOL characters with blanks
	text = text.replace('\n', ' ');

	ArrayList<String> sentences = new ArrayList<String>();
	for (int i = 0; i < text.length(); i++)
	{
	    int sentenceEndIndex = i;
	    char currChar;

	    //  keep going until a period, question mark,
	    //  or exclamation mark is encountered
	    while (sentenceEndIndex < text.length()
		   && !((currChar = text.charAt(sentenceEndIndex)) == '.' || currChar == '!' || currChar == '?'))
	    {
		sentenceEndIndex++;
	    }

	    //  once a period, question mark, or exclamation mark is found,
	    //  keep going until a whitespace is encountered
	    while (sentenceEndIndex < text.length() && !Character.isWhitespace(text.charAt(sentenceEndIndex)))
	    {
		sentenceEndIndex++;
	    }

	    //  the substring text.substring(i, sentenceEndIndex) contains a complete sentence,
	    //  and may have a blank behind it
	    //  so, trim() is called on it to remove that blank. Then add it to ArrayList of sentences
	    sentences.add(text.substring(i, sentenceEndIndex).trim());

	    //  set i to the first position after the end of sentence,
	    //  so that after increment by for-loop, i starts after the whitespace encountered
	    i = sentenceEndIndex;
	}
	//  return String array of sentences
	return sentences.toArray(new String[sentences.size()]);
    }

    private static String[] findSentenceSequences(String[] sentences)
    {
	ArrayList<String> finalOutputs = new ArrayList<String>();
	for (int firstSentenceIndex = 0; firstSentenceIndex < sentences.length; firstSentenceIndex++)
	{
	    //	start length with length of sentence currently on index firstSentenceIndex
	    int length = sentences[firstSentenceIndex].length(), lastSentenceIndex = firstSentenceIndex + 1;

	    //	keep adding length of sentences to length while length < min
	    //	and lastSentenceIndex is not out of array index range
	    while (lastSentenceIndex < sentences.length && length < min)
	    {
		//    the +1 in length is for the assumed blanks in between sentences
		length += sentences[lastSentenceIndex].length() + 1;
		lastSentenceIndex++;
	    }

	    //	once length is >= min, keep adding sentences until either index lastSentenceIndex
	    //	reaches end of array, and length < max
	    while (lastSentenceIndex < sentences.length && length < max)
	    {
		//    the +1 in length is for the assumed blanks in between sentences
		length += sentences[lastSentenceIndex].length() + 1;
		lastSentenceIndex++;
	    }

	    //	at this point, length >= max; but wait,
	    //	if length > max, then the last added sentence, and its length, need to be discarded.
	    if (length > max)
	    {
		lastSentenceIndex--;
		//    the +1 in length is for the assumed blanks in between sentences
		length -= sentences[lastSentenceIndex].length() + 1;
	    }

	    //	whatever sentence(s) is in the sentence sequence at this point, should have length >= min
	    //	otherwise our criteria is not met and we discard the sequence
	    if (length >= min)
	    {
		//  packing the sentences of the sequence as <sentence1><blank><sentence2>...
		StringBuffer sentenceSequence = new StringBuffer(sentences[firstSentenceIndex]);
		for (int k = firstSentenceIndex + 1; k < lastSentenceIndex; k++)
		{
		    sentenceSequence.append(' ').append(sentences[k]);
		}
		finalOutputs.add(sentenceSequence.toString());

		//  setting firstSentenceIndex such that it starts,
		//  in the next iteration of for-loop, after the last sentence
		//  included in our current sentence sequence
		firstSentenceIndex = lastSentenceIndex - 1;
	    }
	}
	return finalOutputs.toArray(new String[finalOutputs.size()]);
    }

    public static void main(String[] arg)
    {
	takeInput(arg);

	String[] sentences = parse();

	String[] sentenceSeqs = findSentenceSequences(sentences);

	for (String sentenceSeq : sentenceSeqs)
	{
	    System.out.println(sentenceSeq);
	}
    }
}
