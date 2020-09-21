package swin.java.assign1;

/**
 * @author Cameron Juhasz 102564401
 */

public class KeywordSearch 
{

	private static final int METHOD_COUNT = 4;
	public static Method[] fMethods;

	/**
	 *
	 * @param args: [0] = name of method, [1] = buffer size, [2] = input file name
	 */
	public static void main(String[] args) 
	{

		// args contains:
		//  [0] - name of method.
		//  [1] - buffer size.
		//  [2] - text file name.

		InitMethods(); 				// init methods in an array.
		
		if (args.length < 3) 
		{
			System.out.println("Usage: run <method-code> <buffersize> <file-name>");
			System.exit(1);
		}

		
		final int lBufferSize = Integer.parseInt(args[1]);		// allocate BufferSize to local variable

		final String lFileName = args[2];							// allocate Filename to local variable


		Method lRequestedMethod = null; 					// default requested method to null for exception management.
		String lMethod = args[0]; 


		// find which method the user would like to execute.
		for (int i = 0; i < METHOD_COUNT; i++)
		{
			if (fMethods[i].fCode.equals(lMethod))
			{
				// the method the user would like to execute.
				lRequestedMethod = fMethods[i];
			}
		}

		// check if the method exists.
		if (lRequestedMethod == null)
		{
			System.out.println("Method, " + lMethod + ", does not exist. Methods are case sensitive");
		}

		// hard coded key words to be searched.
		String[] keyWords = {"model", "view"};

		// user requested method will read from the allocated file using the allocated buffer size
		lRequestedMethod.Read(lFileName, lBufferSize, keyWords);


	}

	/**
	 * creates an instance of each method and puts them in an array.
	 */
	private static void InitMethods()
	{
		fMethods = new Method[METHOD_COUNT];
		fMethods[0] = new FileChannelDirectByteBuffer();
		fMethods[1] = new FileChannelIndirectByteBuffer();
		fMethods[2] = new BufferedStream();
		fMethods[3] = new ProgrammerManagedByteArray();	
	}

}