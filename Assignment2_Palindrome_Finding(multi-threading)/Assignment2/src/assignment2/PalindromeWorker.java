package assignment2;

import java.util.*;

/**
 *
 * @author    Muhammad Ahsan Anjum Butt
 * @email     l134169@lhr.nu.edu.pk
 * @section   CS-B
 * @function  Finds palindrome words and puts them in shared ArrayList named sharedArr
 */

public class PalindromeWorker extends Thread
{
    private static Integer ID_Counter = 0;
    
    //  this worker's ID  
    private Integer ID;
    
    private HashMap<Integer, ArrayList<String>> bagOfTasks;
    private ArrayList<Object> sharedArr;
    
    public PalindromeWorker(HashMap<Integer, 
                            ArrayList<String>> bagOfTasks, 
                            ArrayList<Object> sharedArr)
    {
        this.ID = ID_Counter++;
        this.bagOfTasks = bagOfTasks;
        this.sharedArr = sharedArr;
    }

    @Override
    public String toString()
    {
        return "Worker" + ID;
    }
    
    //  lock bagOfTasks and take a task out of it, then release the lock
    private ArrayList<String> getATaskFromBag()
    {
        ArrayList<String> currTask;
        synchronized(bagOfTasks)
        {
            try
            {
                Integer currKey = bagOfTasks.keySet().iterator().next();
                currTask = bagOfTasks.get(currKey);
                bagOfTasks.remove(currKey);
            }
            catch(Exception e)
            {
                return null;
            }
        }
        return currTask;
    }
    
    @Override
    public void run()
    {
        Integer palindromeCount = 0;
        StringBuilder temp = new StringBuilder();
        
        while( !bagOfTasks.isEmpty() )
        {
            ArrayList<String> currTask;
            
            currTask = getATaskFromBag();
            
            if(currTask != null)
            {
                while( !currTask.isEmpty() )
                {
                    //  take a word from currTask and take its reverse as well.
                    String word = currTask.get(0);
                    temp.append(word);
                    String reversedWord = temp.reverse().toString();
                    temp.delete(0, temp.length());

                    //  if the word and its reverse are equal, its a palindrome word,
                    //  so, lock sharedArr and write word/its reverse to sharedArr
                    if( word.equals( reversedWord ) )
                    {
                        synchronized(sharedArr)
                        {
                            sharedArr.add(word);
                            sharedArr.notify();
                        }
                        ++palindromeCount;
                    }
                    else
                    {
                        //  if word and its reverse are not equal,
                        //  then search the reverse in currTask
                        int indexOfReversedWord = Collections.binarySearch(currTask, reversedWord);
                        if( indexOfReversedWord >= 0 )
                        {
                            //  if indexOfReversedWord >= 0, then the reversed word is present in currTask,
                            //  so, word and its reverse are 2 palindromes, hence locking sharedArr 
                            //  and adding the words to sharedArr 
                            synchronized(sharedArr)
                            {
                                sharedArr.add(word);
                                sharedArr.add(reversedWord);
                                sharedArr.notify();
                            }
                            currTask.remove( indexOfReversedWord );
                            palindromeCount += 2;
                        }
                    }
                    currTask.remove(0);
                    
                }   //  end while(!currTask.isEmpty())
                
            }   //  end if(currTask != null)
            
        }   //  end while(!bagOfTasks.isEmpty())

        //  Write this worker thread's total counted/found palindromes along with its ID.
        //  Note that String objects are added when words are added to sharedArr,
        //  and Integer objects are added to sharedArr when a thread exits
        //  and writes its total counted/found palindromes and ID. 
        synchronized(sharedArr)
        {
            sharedArr.add(ID);
            sharedArr.add(palindromeCount);
            sharedArr.notify();
        }
        //  In PalindromeWriter, type of the objects put in sharedArr will be used to 
        //  check if a worker thread has put a word, or its ID-and-palindrome-count pair.

    }   //  end run()

}
