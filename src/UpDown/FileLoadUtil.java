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
 * 从云端上传下载查询删除文件
 *
 * @author andy
 * @date 2016年8月31日
 */


/*首先你需要一个OSSClient，里面的keyId以及keySecret是你在开通服务的时候获得的;
 * OSS是没有文件夹这个概念的，所有元素都是以Object来存储，代码中的myKey或keys是你上传的Object的完整的键
 * 名称，比如你上传到 test/myWord/new.doc 里面，那么你的myKey就是“test/myWord/new.doc”，不管你本地
 * 的文件名称是什么，上传到云端之后的对象名称就是你的myKey的名称;OSS允许用户自定义Http header，在上传文件
 * 的方法中，设置了上传内容类型以及上传文件长度，之前看到一篇文章，是说这个云服务是按照量来计费的，所以需要告知
 * 它你要上传的内容长度，但是如果我不设置长度，貌似也能上传成功，原因还不清楚:

*/


public class FileLoadUtil {


	public static String url;
	 /**
     * 文件上传
     * @param myKey,代码中的myKey或keys是你上传的Object的完整的键名称，比如你上传到 test/myWord/new.doc 里面，那么你的myKey就是“test/myWord/new.doc”，
     * @param file 要上传的文件
     */
    public static void upLoadFileToALiyun(String myKey, File file) {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(OSSClientUtil.getEndpoint(), OSSClientUtil.getAccessKeyId(), OSSClientUtil.getAccessKeySecret());
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 设置上传内容类型
        meta.setContentType("audio/wav"); //https://baike.baidu.com/item/ContentType/1938445
        // 设置上传文件长度
        meta.setContentLength(file.length());
        // 上传文件
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
            ossClient.shutdown();// 关闭client
            
        }
        
    }

    /**
     * 文件下载
     * 
     * @param myKey
     * @param tempFileName 要下载的文件名
     * @throws Exception
     */
    public static void downloadFileFromALiyun(String myKey, String tempFileName) throws Exception {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(OSSClientUtil.getEndpoint(), OSSClientUtil.getAccessKeyId(), OSSClientUtil.getAccessKeySecret());
        InputStream inputStream = null;
        // 下载object到文件
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
            ossClient.shutdown();// 关闭client
           // FileUtil.copyInputStreamToFile(inputStream);
        }
    }

    // 查看Bucket中的Object。详细请参看“SDK手册 > Java-SDK > 管理文件”。
    public static void queryObject() {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(OSSClientUtil.getEndpoint(), OSSClientUtil.getAccessKeyId(), OSSClientUtil.getAccessKeySecret());
        try {
            // 要查找的文件
            ObjectListing objectListing = ossClient.listObjects(OSSClientUtil.getBucketName(), "yourObjectKey/");
            List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
            System.out.println("您有以下Object：");
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
            ossClient.shutdown();// 关闭client
        }
    }

    // 删除Object。详细请参看“SDK手册 > Java-SDK > 管理文件”。
    public static void deleteSingleObect(String myKey) {
        //创建OSSClient实例
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
            ossClient.shutdown();// 关闭client
        }
    }

    /**
     * 批量删除object
     */
    public static void deleteBatchObect(List<String> keys) {
        // 删除Object。详细请参看“SDK手册 > Java-SDK > 管理文件”。
        OSSClient ossClient = new OSSClient(OSSClientUtil.getEndpoint(), OSSClientUtil.getAccessKeyId(), OSSClientUtil.getAccessKeySecret());
        try {
            // 删除Objects
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(OSSClientUtil.getBucketName()).withKeys(keys));
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();// 关闭client
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
            ossClient.shutdown();// 关闭client
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