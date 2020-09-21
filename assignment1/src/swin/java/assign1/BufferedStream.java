package swin.java.assign1;

import java.io.*;
import java.util.*;

/**
 * @author Cameron Juhasz 102564401
 */

class BufferedStream extends Method
{
    private long fStartTime;

    public BufferedStream()
    {
        fCode = "BS";
    }

    public void Read(String aFileName, int aBufferSize, String[] aKeyWordList)
    {

        String lTextBreak = "\n";

        try(BufferedInputStream lIn = new BufferedInputStream(new FileInputStream(aFileName));
        		BufferedOutputStream lOut = new BufferedOutputStream(new FileOutputStream("output.txt"));)
        {
        

            fStartTime = System.nanoTime();

            int lBytesCount;
            byte[] lByteArray = new byte[aBufferSize];

            while ((lBytesCount = lIn.read(lByteArray)) != -1)
            {
                for (String s : aKeyWordList)
                {
                    if (containsWord(lByteArray, s))
                    {
                        s = "[ " + s + " ] -> ";

                        byte[] lBytes = s.getBytes();                   // writes keyword to file.
                        lOut.write(lBytes, 0, lBytes.length);

                        lOut.write(lByteArray, 0, lBytesCount);     // writes snippet to file.

                        lBytes = lTextBreak.getBytes();                 // writes break to file.
                        lOut.write(lBytes, 0, lBytes.length);
                    }
                }
            }
            lOut.close();
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
     * @param aByteArray: the byte buffer to be searched.
     * @param aWord: the word to be searched for.
     * @return returns true if the word is contained in the buffer, else false.
     */
    public boolean containsWord(byte[] aByteArray, String aWord)
    {
        String lBuffer = new String(aByteArray);
        return lBuffer.contains(aWord);
    }
}
