package UpDown;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

/**
 * ���ƶ��ϴ����ز�ѯɾ���ļ�
 *
 * @author andy
 * @date 2016��8��31��
 */


/*��������Ҫһ��OSSClient�������keyId�Լ�keySecret�����ڿ�ͨ�����ʱ���õ�;
 * OSS��û���ļ����������ģ�����Ԫ�ض�����Object���洢�������е�myKey��keys�����ϴ���Object�������ļ�
 * ���ƣ��������ϴ��� test/myWord/new.doc ���棬��ô���myKey���ǡ�test/myWord/new.doc���������㱾��
 * ���ļ�������ʲô���ϴ����ƶ�֮��Ķ������ƾ������myKey������;OSS�����û��Զ���Http header�����ϴ��ļ�
 * �ķ����У��������ϴ����������Լ��ϴ��ļ����ȣ�֮ǰ����һƪ���£���˵����Ʒ����ǰ��������Ʒѵģ�������Ҫ��֪
 * ����Ҫ�ϴ������ݳ��ȣ���������Ҳ����ó��ȣ�ò��Ҳ���ϴ��ɹ���ԭ�򻹲����:

*/


public class FileLoadUtil {


	public static String url;
	 /**
     * �ļ��ϴ�
     * @param myKey,�����е�myKey��keys�����ϴ���Object�������ļ����ƣ��������ϴ��� test/myWord/new.doc ���棬��ô���myKey���ǡ�test/myWord/new.doc����
     * @param file Ҫ�ϴ����ļ�
     */
    public static void upLoadFileToALiyun(String myKey, File file) {
        // ����OSSClientʵ��
        OSSClient ossClient = new OSSClient(OSSClientUtil.getEndpoint(), OSSClientUtil.getAccessKeyId(), OSSClientUtil.getAccessKeySecret());
        // �����ϴ�Object��Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // �����ϴ���������
        meta.setContentType("audio/wav"); //https://baike.baidu.com/item/ContentType/1938445
        // �����ϴ��ļ�����
        meta.setContentLength(file.length());
        // �ϴ��ļ�
        try {
            PutObjectResult m_Result = ossClient.putObject(OSSClientUtil.getBucketName(), myKey, file, meta);
            System.out.println(m_Result.getETag());
            url = "https://zhang45258.oss-cn-beijing.aliyuncs.com"+"/"+myKey;
            
        } catch (OSSException oe) {
            oe.printStackTrace();
            throw new OSSException();
        } catch (ClientException ce) {
            ce.printStackTrace();
            throw new ClientException();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();// �ر�client
            
        }
        
    }

    /**
     * �ļ�����
     * 
     * @param myKey
     * @param tempFileName Ҫ���ص��ļ���
     * @throws Exception
     */
    public static void downloadFileFromALiyun(String myKey, String tempFileName) throws Exception {
        // ����OSSClientʵ��
        OSSClient ossClient = new OSSClient(OSSClientUtil.getEndpoint(), OSSClientUtil.getAccessKeyId(), OSSClientUtil.getAccessKeySecret());
        InputStream inputStream = null;
        // ����object���ļ�
        try {
            OSSObject ossObject = ossClient.getObject(new GetObjectRequest(OSSClientUtil.getBucketName(), myKey));
            inputStream = ossObject.getObjectContent();
            String newStr = new String(tempFileName.getBytes(), "UTF-8");
            File f = new File(newStr);
            FileUtil.copyInputStreamToFile(inputStream, f);
        } catch (OSSException oe) {
            oe.printStackTrace();
            throw new Exception(oe.getErrorMessage());
        } catch (ClientException ce) {
            ce.printStackTrace();
            throw new Exception(ce.getErrorMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } finally {
            ossClient.shutdown();// �ر�client
           // FileUtil.copyInputStreamToFile(inputStream);
        }
    }

    // �鿴Bucket�е�Object����ϸ��ο���SDK�ֲ� > Java-SDK > �����ļ�����
    public static void queryObject() {
        // ����OSSClientʵ��
        OSSClient ossClient = new OSSClient(OSSClientUtil.getEndpoint(), OSSClientUtil.getAccessKeyId(), OSSClientUtil.getAccessKeySecret());
        try {
            // Ҫ���ҵ��ļ�
            ObjectListing objectListing = ossClient.listObjects(OSSClientUtil.getBucketName(), "yourObjectKey/");
            List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
            System.out.println("��������Object��");
            for (OSSObjectSummary object : objectSummary) {
                System.out.println("\t" + object.getKey());
            }
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();// �ر�client
        }
    }

    // ɾ��Object����ϸ��ο���SDK�ֲ� > Java-SDK > �����ļ�����
    public static void deleteSingleObect(String myKey) {
        //����OSSClientʵ��
        OSSClient ossClient = new OSSClient(OSSClientUtil.getEndpoint(), OSSClientUtil.getAccessKeyId(), OSSClientUtil.getAccessKeySecret());
        try {
            ossClient.deleteObject(OSSClientUtil.getBucketName(), myKey);
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();// �ر�client
        }
    }

    /**
     * ����ɾ��object
     */
    public static void deleteBatchObect(List<String> keys) {
        // ɾ��Object����ϸ��ο���SDK�ֲ� > Java-SDK > �����ļ�����
        OSSClient ossClient = new OSSClient(OSSClientUtil.getEndpoint(), OSSClientUtil.getAccessKeyId(), OSSClientUtil.getAccessKeySecret());
        try {
            // ɾ��Objects
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(OSSClientUtil.getBucketName()).withKeys(keys));
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();// �ر�client
        }
    }

    public static boolean doesObjectExist(String myKey) {
        OSSClient ossClient = new OSSClient(OSSClientUtil.getEndpoint(), OSSClientUtil.getAccessKeyId(), OSSClientUtil.getAccessKeySecret());
        boolean found = false;
        try {
            found = ossClient.doesObjectExist(OSSClientUtil.getBucketName(), myKey);
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();// �ر�client
        }
        return found;
    }
/*
    public static void main(String[] args) {

    	upLoadFileToALiyun("1.V3",new File("D://1/0/20190206/1404/0629184.V3"));
    	System.out.println(url);
    	
    	
    }
 */
}