package swin.java.assign1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author Cameron Juhasz 102564401
 */

class ProgrammerManagedByteArray extends Method
{
    private long fStartTime;

    public ProgrammerManagedByteArray()
    {
        fCode = "PMBA";
    }

    public void Read(String aFileName, int aBufferSize, String[] aKeyWordList)
    {

        String lTextBreak = "\n";

        try (FileInputStream lin = new FileInputStream(aFileName);
        		FileOutputStream lout = new FileOutputStream("output.txt");)
        {
            

            fStartTime = System.nanoTime();

            byte[] lByteArray = new byte[aBufferSize];

            int lBytesCount;

            while ((lBytesCount = lin.read(lByteArray)) != -1)
            {
                for (String s : aKeyWordList)
                {
                    if (containsWord(lByteArray, s))
                    {
                        s = "[" + s + "] -> ";

                        byte[] lBytes = s.getBytes();                       // writes keyword to file.
                        lout.write(lBytes, 0, lBytes.length);

                        lout.write(lByteArray, 0, lBytesCount);         	// writes snippet to file.

                        lBytes = lTextBreak.getBytes();                     // writes break to file.
                        lout.write(lBytes, 0, lBytes.length);
                    }
                }
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        long fTotalTime = System.nanoTime() - fStartTime;

        System.out.println("Execution Successful!");
        System.out.println("Total Time: " + (fTotalTime / 1000000.0) + " msec");

        HashMap<String,ArrayList<String>> lSnippetHashMap = createHashMap(aKeyWordList, aBufferSize);       // keyword - snippet hashmap.

        printResults(fCode, aBufferSize, fTotalTime);
    }

    /**
     * searches buffer for a given word.
     *
     * @param aByteArray: the byte array to be searched.
     * @param aWord: the word to be searched for.
     * @return returns true if the word is contained in the buffer, else false.
     */

    public boolean containsWord(byte[] aByteArray, String aWord)
    {
        String lBuffer = new String(aByteArray);        // convert byte array to string.
        return lBuffer.contains(aWord);
    }
}
