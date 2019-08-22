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
 * @param dataServerIp 服务器IP
 * @param dataServerUsername 服务器用户名
 * @param dataServerPassword 服务器登录密码
 * @param srcFile 要下载的文件路径
 * @param saveFile 保存路径
 * @param port 端口号，null时为默认端口
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
		System.out.println("linux录音文件服务器连接正常");
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	if(download(srcFile, saveFile, session, client)){		


			System.out.println("文件下载完成！");
		
	} 
		session.close();
		conn.close();		
	
}


/**
 * 根据不同的需求得到相应的连接
 * @param ip
 * @param userName
 * @param pwd
 * @param port
 * @param whatWant 必须是client或者session
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
			throw new IOException("Authentication failed.文件scp到数据服务器时发生异常");
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
 * 获得连接
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
			throw new IOException("Authentication failed.文件scp到数据服务器时发生异常");
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
 * @param srcFile 要下载的文件
 * @param saveFile 保存目录，必须是目录
 * @param sessionSsh 
 * @param client
 * @return
 * @throws UnsupportedEncodingException
 */
public static boolean download(String srcFile, String saveFile, Session sessionSsh, SCPClient client
		) throws UnsupportedEncodingException {		
	
	boolean flag=false;
	// 是文件,直接下载
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
 * 解压tar.gz 文件 
 * @param file 要解压的tar.gz文件对象 
 * @param outputDir 要解压到某个指定的目录下 
 * @throws IOException 
 */  
public static void unTarGz(File file,String outputDir) throws IOException{  
	TarInputStream tarIn = null;  
	try{  
		tarIn = new TarInputStream(new GZIPInputStream(  
				new BufferedInputStream(new FileInputStream(file))),  
				1024 * 2);  

		createDirectory(outputDir,null);//创建输出目录  

		TarEntry entry = null;  
		while( (entry = tarIn.getNextEntry()) != null ){  

			if(entry.isDirectory()){//是目录
				entry.getName();
				createDirectory(outputDir,entry.getName());//创建空目录  
			}else{//是文件
				File tmpFile = new File(outputDir + "/" + entry.getName());  
				createDirectory(tmpFile.getParent() + "/",null);//创建输出目录  
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
		throw new IOException("解压归档文件出现异常",ex);  
	} finally{  
		try{  
			if(tarIn != null){  
				tarIn.close();  
			}  
		}catch(IOException ex){  
			throw new IOException("关闭tarFile出现异常",ex);  
		}  
	}  
}
/** 
 * 构建目录 
 * @param outputDir 
 * @param subDir 
 */  
public static void createDirectory(String outputDir,String subDir){     
	File file = new File(outputDir);  
	if(!(subDir == null || subDir.trim().equals(""))){//子目录不为空  
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
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
    
}  
*/

}