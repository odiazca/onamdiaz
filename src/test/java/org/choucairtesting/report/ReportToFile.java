package org.choucairtesting.report;

import java.io.FileWriter;
import java.io.IOException;
import org.junit.Ignore;

/**
 * @author Victimizer
 *
 */

public class ReportToFile {
	@Ignore
	public static void print(String string, boolean mode ) {
        try {
        	String current = new java.io.File( "." ).getCanonicalPath();
            FileWriter writer = new FileWriter(current + "\\src\\test\\java\\org\\choucairtesting\\report\\Choucair-report.txt", mode);  
            writer.write(string);
            writer.write("\r\n");   
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }

}
