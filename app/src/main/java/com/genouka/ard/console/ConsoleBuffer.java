package com.genouka.ard.console;

import android.widget.TextView;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ConsoleBuffer {
	//private char[] buffer;
	private int amountPopulated;

    private String aa;
    private int fb;
	
	public ConsoleBuffer(int bufferSize)
	{
		//buffer = new char[bufferSize];
		amountPopulated = 0;
        fb=bufferSize;
        aa="";
	}
	
	public synchronized void append(byte[] asciiData, int offset, int length)
	{
        /*
		if (amountPopulated + length > buffer.length)
		{
			// Move the old data backwards 
            
			System.arraycopy(buffer,
					length,
					buffer,
					0,
					amountPopulated - length);
            
            
            
			amountPopulated -= length;
		}
		
		for (int i = 0; i < length; i++)
		{
			buffer[amountPopulated++] = (char)asciiData[offset+i];
            
		}
        */
        aa+=new String(asciiData,StandardCharsets.UTF_8);
        int i=aa.length();
        if(i>fb) aa=aa.substring(i-fb);
	}
	
	public synchronized void updateTextView(TextView textView)
	{
		//textView.setText(buffer, 0, amountPopulated);
        textView.setText(aa);
	}
    
}
