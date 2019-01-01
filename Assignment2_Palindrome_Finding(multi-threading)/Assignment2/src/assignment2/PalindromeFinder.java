package assignment2;

import java.io.*;
import java.util.*;

/**
 *
 * @author    Muhammad Ahsan Anjum Butt
 * @email     l134169@lhr.nu.edu.pk
 * @section   CS-B
 * @function  Loads dictionary words from words.txt, makes bagOfTasks from it, creates PalindromeWriter and worker
 *              threads, and assigns them work of finding palindrome words
 */

public class PalindromeFinder
{
    //  [1]
    public static final String CURRENT_WORKING_DIRECTORY = ( new File("").getAbsolutePath() ) + "/";
    
    private static HashMap<Integer, ArrayList<String>> bagOfTasks;
    
    //  number of worker threads 'w'
    private static Integer w;
        
    //  try reading words from dictionary file
    //  and make bagOfTasks from it.
    private static void readWordsFromFile()
    {
        File dictFile;
        FileReader reader;
        BufferedReader buffReader = null;
        try
        {
            dictFile = new File( CURRENT_WORKING_DIRECTORY + "words.txt" );
            reader = new FileReader(dictFile);
            buffReader = new BufferedReader(reader);
            
            bagOfTasks = new HashMap<Integer, ArrayList<String>>();
            String temp;
            while ((temp = buffReader.readLine()) != null)
            {
                temp = temp.trim();
                ArrayList<String> sameLenWords = bagOfTasks.get(temp.length());
                if(sameLenWords != null)
                    sameLenWords.add(temp);
                else
                {
                    ArrayList<String> newLenWords = new ArrayList<String>();
                    newLenWords.add(temp);
                    bagOfTasks.put(temp.length(), newLenWords);
                }
            }
            
        }
        catch( IOException e )
        {
            //  if error in IO, then inform about error and exit
            System.out.println("ERROR! Exception thrown while reading words from dictionary file!");
            System.out.println("Aborting main program!");
            System.exit(1);
        }
        finally
        {
            if(buffReader != null)
            {
                try {   buffReader.close(); }
                catch( IOException e )
                {  System.out.println("ERROR! Exception thrown while closing dictionary file!");   }
            }
        }
        
        //  sort the tasks in bagOfTasks so that, later on,
        //  binary search can be used on them for finding palindrome words
        for(ArrayList<String> sameLenWords:bagOfTasks.values())
        {
            sameLenWords.sort(null);
        }
        
    }   //  end readWordsFromFile()
    
    public static void main(String[] args)
    {
        System.out.println("This program finds palindrome words from a dictionary using w worker threads, and writem them and palindrome count of workers and total, into a result.txt!");
        
        //  if command-line argument for w is not convertible to integer, abort program
        try
        {
            w = Integer.parseInt(args[0]);
        }
        catch( NumberFormatException e )
        {
            System.out.println("ERROR! The command-line argument passed for w is '" + args[0] + "', and is not a valid integer!");
            System.out.println("Aborting main program!");
            return;
        }

        readWordsFromFile();
        
        //  if w is not in range [1, numberOfTasks], then abort
        if( w < 1 || w > bagOfTasks.size() )
        {
            System.out.println("ERROR! w = " + w + ", whereas it should be in range [1, " + bagOfTasks.size() + "]");
            System.out.println("Aborting main program!");
            return;
        }
        
        System.out.println("Command-line argument for w is:  " + w);

        ArrayList<Object> sharedArr = new ArrayList<Object>();
   
        try
        {
            //  start PalindromeWriter thread
            PalindromeWriter writer = new PalindromeWriter(sharedArr, w);
            writer.start();

            //  start w number of PalindromeWorker threads
            PalindromeWorker[] workers = new PalindromeWorker[w];
            for(int j = 0; j < w; j++)
            {
                workers[j] = new PalindromeWorker(bagOfTasks, sharedArr);
                workers[j].start();
            }

            //  make main thread wait for PalindromeWriter to finish
            writer.join();
        }
        catch( InterruptedException e )
        {
            //  Abort program if main thread execution was interrupted
            System.out.println("ERROR! Main thread's execution interrupted!");
            System.out.println("Aborting main program!");
        }
        
    }   //  end main()

}

//  REFERENCES
//  [1] https://stackoverflow.com/questions/3153337/get-current-working-directory-in-java


//  TEST CASES

//      TEST CASE 1:
//
//          command-line arguments: 0
//
//          Output on console:
//
//              This program finds palindrome words from a dictionary using w worker threads, and writem them and palindrome count of workers and total, into a result.txt!
//              ERROR! w = 0, whereas it should be in range [1, 21]
//              Aborting main program!


//      TEST CASE 2:
//
//          command-line argument: 10
//
//          Output on console:
//
//              This program finds palindrome words from a dictionary using w worker threads, and writem them and palindrome count of workers and total, into a result.txt!
//              Command-line argument for w is:  10
//
//          Output file is result_10.txt


//      TEST CASE 3:
//
//          command-line argument: 21
//
//          Output on console:
//
//              This program finds palindrome words from a dictionary using w worker threads, and writem them and palindrome count of workers and total, into a result.txt!
//              Command-line argument for w is:  21
//
//          Output file is result_21.txt


//      TEST CASE 4:
//
//          command-line argument: 23
//
//          Output on console:
//
//              This program finds palindrome words from a dictionary using w worker threads, and writem them and palindrome count of workers and total, into a result.txt!
//              ERROR! w = 23, whereas it should be in range [1, 21]
//              Aborting main program!


//      TEST CASE 5:
//
//          command-line argument: ab
//
//          Output on console:
//
//              This program finds palindrome words from a dictionary using w worker threads, and writem them and palindrome count of workers and total, into a result.txt!
//              The command-line argument passed for w is 'ab', and is not a valid integer!
//              Aborting main program!