package swin.java.assign1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Cameron Juhasz 102564401
 */

class FileChannelIndirectByteBuffer extends Method
{
    private long fStartTime;

    public FileChannelIndirectByteBuffer()
    {
        fCode = "FCIBB";
    }

    public void Read(String aFileName, int aBufferSize, String[] aKeyWordList)
    {

        String lTextBreak = "\n";

        try(FileChannel lIn = new FileInputStream(aFileName).getChannel();
                FileChannel lOut = new FileOutputStream("output.txt").getChannel();)
        {
        	
            ByteBuffer lByteBuffer = ByteBuffer.allocate(aBufferSize);

            fStartTime = System.nanoTime();

            while ((lIn.read(lByteBuffer)) > 0)
            {
                for (String s : aKeyWordList)
                {
                    lByteBuffer.flip();

                    if (containsWord(lByteBuffer, s))
                    {
                        lByteBuffer.flip();

                        s = "[" + s + "] -> ";

                        byte[] lBytes = s.getBytes();           // writes keyword to file.
                        lOut.write(ByteBuffer.wrap(lBytes));


                        lOut.write(lByteBuffer);                // writes snippet to file.

                        lBytes = lTextBreak.getBytes();         // writes break to file.
                        lOut.write(ByteBuffer.wrap(lBytes));
                    }
                }

                lByteBuffer.clear();
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
     * @param aByteBuffer: the byte buffer to be searched.
     * @param aWord: the word to be searched for.
     * @return returns true if the word is contained in the buffer, else false.
     */

    public boolean containsWord(ByteBuffer aByteBuffer, String aWord)
    {
        String lBuffer = StandardCharsets.UTF_8.decode(aByteBuffer).toString();
        return lBuffer.contains(aWord);
    }
}
