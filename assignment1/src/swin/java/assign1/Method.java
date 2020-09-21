package swin.java.assign1;

import java.io.*;
import java.util.*;

/**
 * @author Cameron Juhasz 102564401
 */

public abstract class Method
{
    public String fCode;

    /**
     * reads from a text file containing text, it will then apply the given NIO method to
     * read from the file and write to another file, writing only snippets of the buffer size
     * to the output file that contain any of the given keywords. Only the time it takes
     * read and write is recorded. Every function includes
     *
     * @param aFileName: the name of the input file. eg. sample.txt
     * @param aBufferSize: the size of the buffer in bytes.
     * @param aKeyWordList: a list of keywords to be searched for.
     *
     * @throws IOException if there is an error with the IO
     * @throws java.io.FileNotFoundException if the file does not exist in the directory
     */
    abstract public void Read(String aFileName, int aBufferSize, String[] aKeyWordList);

    /**
     * Creates a hashmap that stores the snippets from in output file. Line 56 Prevents
     * issue with break in output. Scans next line to the snippet if the current snippet is less then
     * the buffer size and there is another line after current, as the last line scanned is usually
     * less then the buffer size as well.
     *
     * @param aKeyWordList: list of key words that are stored in the output file.
     * @param aBufferSize: the size of the buffer in bytes.
     *
     * @throws IOException if there is an error with the IO
     *
     * @return HashMap with snippets.
     */
    public HashMap<String,ArrayList<String>> createHashMap(String[] aKeyWordList, int aBufferSize)
    {
        HashMap<String,ArrayList<String>> lOutput = new HashMap<String,ArrayList<String>>();
        try
        {
            for (String lKeyword : aKeyWordList) {
                ArrayList<String> lTempList = new ArrayList<String>();
                Scanner lScanner = new Scanner(new File("output.txt"));
                while (lScanner.hasNext()) {
                    String lSnippet = lScanner.nextLine();

                    if (lSnippet.length() < aBufferSize && lScanner.hasNext())
                    {
                        lSnippet += lScanner.nextLine();
                    }

                    String[] lSplitSnippet = lSnippet.split("->");

                    String lKey = lSplitSnippet[0].replaceAll("[^a-zA-Z0-9+]", "");
                    if (lKey.equals(lKeyword))
                    {
                        lSnippet = lSplitSnippet[1];
                        lTempList.add(lSnippet);
                    }
                }
                if (!lTempList.isEmpty())
                {
                    lOutput.put(lKeyword, lTempList);
                }
            }
            System.out.println(lOutput);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return lOutput;
    }

    /**
     * prints the results of the search to the result file. Including the search type,
     * buffer size and time taken.
     *
     * @param aCode: the code of search type.
     * @param aBufferSize: the buffer size.
     * @param aTotalTime: the time it took to read and write.
     *
     * @throws IOException if there is an error with the IO
     *
     */
    public void printResults (String aCode, int aBufferSize, long aTotalTime)
    {
        try
        {
            String lOutput = "Search Method: " + aCode + ", Buffer Size: " + aBufferSize + " bytes, Time: " + aTotalTime / 1000000.0 + " msec.\n";
            System.out.println(lOutput);
            PrintWriter lOut = new PrintWriter(new FileWriter(new File("result.txt"), true));

            lOut.append(lOutput);
            lOut.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
