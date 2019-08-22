package ai;
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStreamWriter;  
import java.net.HttpURLConnection;  
import java.net.URL;  
  
public class Api {  
  
    final static String url = "http://10.208.10.20:6088";  
    final static String params = "{\"no\":\"12345111\",\"text\":\"��ã���ӭ�µ簥�������|�ң������Ǹ���һ�£����Ǹղţ����Ǹ��������Ǹ��������ϵ�������һ�ǵ������г��Ͼ�����Ҵ�������9000���Ǹ�|�ߴ������Ǹ�5��49��ʼ���ղ�������ĸ����Σ��㿴һ�¾���ĳ��������������ڼ����Ǹ���268762687���𣿶ԶԶ���һ�ǵ���2687�����г��������𣿶Զ�2687�������Դ�02912306�����ĵ绰���ð�����ϵ�г���ѯһ�£���û��029412306�ţ�52912306|�ã�лллл���������õĸ�л�����ټ�\"}";  
  
    /** 
     * ����HttpPost���� 
     *  
     * @param strURL 
     *            �����ַ 
     * @param params 
     *            json�ַ���,����: "{ \"id\":\"12345\" }" ;���������������˫����<br/> 
     * @return �ɹ�:����json�ַ���<br/> 
     */  
    public static String post(String strURL, String params) {  
    	
        try {  
            URL url = new URL(strURL);// ��������  
            HttpURLConnection connection = (HttpURLConnection) url  
                    .openConnection();  
            connection.setDoOutput(true);  
            connection.setDoInput(true);  
            connection.setUseCaches(false);  
            connection.setInstanceFollowRedirects(true);  
            connection.setRequestMethod("POST"); // ��������ʽ  
            connection.setRequestProperty("Accept", "application/json"); // ���ý������ݵĸ�ʽ  
            connection.setRequestProperty("Content-Type", "application/json"); // ���÷������ݵĸ�ʽ  
            connection.connect();  
            OutputStreamWriter out = new OutputStreamWriter(  
                    connection.getOutputStream(), "UTF-8"); // utf-8����  
            out.append(params);
            out.flush();  
            out.close();  
            // ��ȡ��Ӧ  
            int length = (int) connection.getContentLength();// ��ȡ����  
            InputStream is = connection.getInputStream();  
            if (length != -1) {  
                byte[] data = new byte[length];  
                byte[] temp = new byte[512];  
                int readLen = 0;  
                int destPos = 0;  
                while ((readLen = is.read(temp)) > 0) {  
                    System.arraycopy(temp, 0, data, destPos, readLen);  
                    destPos += readLen;  
                }  
                String result = new String(data, "UTF-8"); // utf-8����  
                System.out.println(result);  
                return result;  
            }  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return "error"; // �Զ��������Ϣ  
    }  
  
    public static void main(String[] args) {  
        post(url, params);  
    }  
  
}  