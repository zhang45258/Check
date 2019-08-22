package UpDown;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;


public class FileUtil {

	public static FileOutputStream openOutputStream(File file) throws IOException {  

        if (file.exists()) {  

            if (file.isDirectory()) {  

                throw new IOException("File'" + file + "' exists but is a directory");  

            }  

            if (file.canWrite() == false) {  

                throw new IOException("File '" + file + "' cannot be written to");  

            }  

        } else {  

            File parent = file.getParentFile();  

            if (parent != null &&parent.exists() == false) {  

                if (parent.mkdirs() ==false) {  

                    throw new IOException("File '" + file + "' could not be created");  

                }  

            }  

        }  

        return new FileOutputStream(file);  

    }  
	
	
	
	public static void copyInputStreamToFile(InputStream source, File destination) throws IOException{  

        try {  

            FileOutputStream output =openOutputStream(destination);  

            try {  

                IOUtils.copy(source,output);  

            } finally {  

               IOUtils.closeQuietly(output);  

            }  

        } finally {  

            IOUtils.closeQuietly(source);  

        }  

    }



	
	
	
}
