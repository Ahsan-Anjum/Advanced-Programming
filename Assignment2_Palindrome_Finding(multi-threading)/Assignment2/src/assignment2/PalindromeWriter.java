package assignment2;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author    Muhammad Ahsan Anjum Butt
 * @email     l134169@lhr.nu.edu.pk
 * @section   CS-B
 * @function  Takes palindrome words and ID-and-palindrome-count pairs put by worker threads,
 *              from sharedArr and writes them to result.txt
 */

public class PalindromeWriter extends Thread
{
    private ArrayList<Object> sharedArr;
    private int w;
    
    public PalindromeWriter(ArrayList<Object> sharedArr, int w)
    {
        this.sharedArr = sharedArr;
        this.w = w;
    }
    
    @Override
    public String toString()
    {
        return "PalindromeWriter";
    }
    
    @Override
    public void run()
    {
        File resultFile;
        FileWriter writer = null;
        try
        {
            resultFile = new File(PalindromeFinder.CURRENT_WORKING_DIRECTORY + "result.txt");
            writer = new FileWriter(resultFile);
        }
        catch( IOException e )
        {
            System.out.println("ERROR! Could not create result.txt file!");
            System.out.println("Exiting PalindromeWriter thread!");
            return;
        }
        
        //  contains ID-and-palindrome-count pairs for finished worker threads
        ArrayList<Integer[]> palindromeCountsOfWorkers = new ArrayList<>();
        
        int numWorkersRunning = w;
        int totalPalindromeCount = 0;
        
        //  keep PalindromeWriter alive till all worker threads finish working
        while(numWorkersRunning > 0)
        {
            synchronized(sharedArr)
            {
                boolean exceptionThrown = false;
                try
                {
                    //  wait for worker threads to put palindromes / ID-and-palindrome-count-pair in sharedArr
                    while( sharedArr.isEmpty() )
                        sharedArr.wait();

                    while( !sharedArr.isEmpty() )
                    {
                        Object temp = sharedArr.remove(0);
                        
                        //  if temp is an Integer object, then worker thread 
                        //  put ID and palindrome-count as Integer objects,
                        //  consecutively in sharedArr
                        if(temp instanceof Integer)
                        {
                            Integer ID = (Integer)temp, palindromeCount = (Integer)sharedArr.remove(0);
                            
                            Integer[] palindromeCountAndID = new Integer[2];
                            palindromeCountAndID[0] = ID;
                            palindromeCountAndID[1] = palindromeCount;
                            
                            palindromeCountsOfWorkers.add( palindromeCountAndID );
                            
                            totalPalindromeCount += palindromeCount;
                            
                            //  since a worker threads writes its ID-and-palindrome-count pair when it finishes,
                            //  so, when a pair is found, a worker has finished working, hence decrementing
                            numWorkersRunning--;
                        }
                        else if(temp instanceof String)
                        {
                            //  if temp is a String object, then worker thread put a palindrome word in sharedArr
                            String palindromeWord = (String)temp;
                            writer.write(palindromeWord + "\n");
                            writer.flush();
                        }
                    }

                }
                catch( InterruptedException e )
                {
                    System.out.println("ERROR! PalindromeWriter interrupted!");
                    System.out.println("Exiting PalindromeWriter thread!");
                    exceptionThrown = true;
                    return;
                }
                catch( IOException e )
                {
                    System.out.println("ERROR! Exception thrown while writing palindromes to result.txt!");
                    System.out.println("Exiting PalindromeWriter thread!");
                    exceptionThrown = true;
                    return;
                }
                finally
                {
                    //  if exception was thrown, release file handler (and will return afterwards)
                    if( exceptionThrown )
                    {
                        try {   writer.close(); }
                        catch(IOException e) {  System.out.println("ERROR! Exception thrown while closing result.txt!");    }
                
                    }
                }
                
            }   //  end synchronized(sharedArr)

        }   //  end while(numWorkersRunning > 0)
            
        
        //  write worker threads' IDs and palindrome-counts, and exit
        try
        {
            for( Integer[] palindromeCountAndID : palindromeCountsOfWorkers )
                writer.write("\nWorker name: Thread " 
                             + palindromeCountAndID[0] 
                             + ",  palindrome_count: " 
                             + palindromeCountAndID[1] + "\n");
            
            writer.write("\nTotal count for Palindromes = " + totalPalindromeCount);
        }
        catch(IOException e)
        {
            System.out.println("ERROR! Exception thrown while writing worker threads' palindrome counts to results.txt");
            System.out.println("Exiting PalindromeWriter thread!");
        }
        finally
        {
            try {   writer.close(); }
            catch(IOException e) {  System.out.println("ERROR! Exception thrown while closing result.txt!");    }
        }
        
        
    }   //  end run()

}
