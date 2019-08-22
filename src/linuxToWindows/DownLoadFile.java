package linuxToWindows;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;
import java.util.zip.GZIPInputStream;

import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * 
 * @param dataServerIp ������IP
 * @param dataServerUsername �������û���
 * @param dataServerPassword ��������¼����
 * @param srcFile Ҫ���ص��ļ�·��
 * @param saveFile ����·��
 * @param port �˿ںţ�nullʱΪĬ�϶˿�
 */

public class DownLoadFile {
	
public static void downLoadFile(String dataServerIp,String dataServerUsername,String dataServerPassword,String srcFile,String saveFile) throws UnsupportedEncodingException{
	Connection conn = new Connection(dataServerIp);
	Session session = null;
	SCPClient client =null;
	//session=(Session) getObject(dataServerIp, dataServerUsername, dataServerPassword, 0, "session");
	//client=(SCPClient) getObject(dataServerIp, dataServerUsername, dataServerPassword, 0, "client");
	conn=getConn(dataServerIp, dataServerUsername, dataServerPassword, 22);
	try {
		session=conn.openSession();
		client=conn.createSCPClient();
		System.out.println("linux¼���ļ���������������");
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	if(download(srcFile, saveFile, session, client)){		


			System.out.println("�ļ�������ɣ�");
		
	} 
		session.close();
		conn.close();		
	
}


/**
 * ���ݲ�ͬ������õ���Ӧ������
 * @param ip
 * @param userName
 * @param pwd
 * @param port
 * @param whatWant ������client����session
 * @return
 */
public static Object getObject(String ip,String userName,String pwd,int port,String whatWant){
	Connection conn = new Connection(ip);
	Session session = null;
	SCPClient client =null;
	try {
		conn.connect();
		boolean isAuthenticated = conn.authenticateWithPassword(userName, pwd);
		session=conn.openSession();
		client = new SCPClient(conn);
		if (isAuthenticated == false) {
			throw new IOException("Authentication failed.�ļ�scp�����ݷ�����ʱ�����쳣");
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
	if (whatWant!=null&&whatWant.equals("session")) {
		return session;
	}if (whatWant!=null&&whatWant.equals("client")) {
		return client;
	}
	return null;
}
/**
 * �������
 * @param ip
 * @param userName
 * @param pwd
 * @param port
 * @return
 */
public static Connection getConn(String ip,String userName,String pwd,int port){
	Connection conn = new Connection(ip);
	boolean blag=false;
	try {
		conn.connect();
		boolean isAuthenticated = conn.authenticateWithPassword(userName, pwd);
		//session=conn.openSession();
		// client = new SCPClient(conn);
		if (isAuthenticated) {
			blag=true;
		}
		if (isAuthenticated == false) {
			throw new IOException("Authentication failed.�ļ�scp�����ݷ�����ʱ�����쳣");
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
	if (blag) {
		return conn;
	}else {
		return null;
	}
}
/**
 * 
 * @param srcFile Ҫ���ص��ļ�
 * @param saveFile ����Ŀ¼��������Ŀ¼
 * @param sessionSsh 
 * @param client
 * @return
 * @throws UnsupportedEncodingException
 */
public static boolean download(String srcFile, String saveFile, Session sessionSsh, SCPClient client
		) throws UnsupportedEncodingException {		
	
	boolean flag=false;
	// ���ļ�,ֱ������
	try {				
		
				client.get(srcFile, saveFile);
			} catch (Exception e) {
				
				flag=true;	
				e.printStackTrace();
			}
		
	return flag; 
}

//------------------------------------------------------------------------------------------------------
/** 
 * ��ѹtar.gz �ļ� 
 * @param file Ҫ��ѹ��tar.gz�ļ����� 
 * @param outputDir Ҫ��ѹ��ĳ��ָ����Ŀ¼�� 
 * @throws IOException 
 */  
public static void unTarGz(File file,String outputDir) throws IOException{  
	TarInputStream tarIn = null;  
	try{  
		tarIn = new TarInputStream(new GZIPInputStream(  
				new BufferedInputStream(new FileInputStream(file))),  
				1024 * 2);  

		createDirectory(outputDir,null);//�������Ŀ¼  

		TarEntry entry = null;  
		while( (entry = tarIn.getNextEntry()) != null ){  

			if(entry.isDirectory()){//��Ŀ¼
				entry.getName();
				createDirectory(outputDir,entry.getName());//������Ŀ¼  
			}else{//���ļ�
				File tmpFile = new File(outputDir + "/" + entry.getName());  
				createDirectory(tmpFile.getParent() + "/",null);//�������Ŀ¼  
				OutputStream out = null;  
				try{  
					out = new FileOutputStream(tmpFile);  
					int length = 0;  

					byte[] b = new byte[2048];  

					while((length = tarIn.read(b)) != -1){  
						out.write(b, 0, length);  
					}  

				}catch(IOException ex){  
					throw ex;  
				}finally{  

					if(out!=null)  
						out.close();  
				}  
			}
		}  
	}catch(IOException ex){  
		throw new IOException("��ѹ�鵵�ļ������쳣",ex);  
	} finally{  
		try{  
			if(tarIn != null){  
				tarIn.close();  
			}  
		}catch(IOException ex){  
			throw new IOException("�ر�tarFile�����쳣",ex);  
		}  
	}  
}
/** 
 * ����Ŀ¼ 
 * @param outputDir 
 * @param subDir 
 */  
public static void createDirectory(String outputDir,String subDir){     
	File file = new File(outputDir);  
	if(!(subDir == null || subDir.trim().equals(""))){//��Ŀ¼��Ϊ��  
		file = new File(outputDir + "/" + subDir);  
	}  
	if(!file.exists()){  
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		file.mkdirs();  
	}  
}
/*
public static void main(String[] args) {  
	  


    try {
		downLoadFile("10.208.11.78","icd","Huawei12#$","/home/icd/icddir/bin/X:/1/0/20190101/1404/0143166.V3","D://");
	} catch (UnsupportedEncodingException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
    
}  
*/

}