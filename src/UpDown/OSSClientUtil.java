package UpDown;

/**
 * OSSClient
 *
 * @author andy
 * @date 2016��8��31��
 */
public class OSSClientUtil {
    // ����OSS�������� endpoint���Ϻ�Ϊ��������region�밴ʵ�������д
    private static String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "LTAIuTdCxDvU3ide";
    private static String accessKeySecret = "bFXzVwcmbP25mccOC6p4qV8uo6EfZq";
    private static String bucketName = "zhang45258";
	public static String getEndpoint() {
		return endpoint;
	}
	public static void setEndpoint(String endpoint) {
		OSSClientUtil.endpoint = endpoint;
	}
	public static String getAccessKeyId() {
		return accessKeyId;
	}
	public static void setAccessKeyId(String accessKeyId) {
		OSSClientUtil.accessKeyId = accessKeyId;
	}
	public static String getAccessKeySecret() {
		return accessKeySecret;
	}
	public static void setAccessKeySecret(String accessKeySecret) {
		OSSClientUtil.accessKeySecret = accessKeySecret;
	}
	public static String getBucketName() {
		return bucketName;
	}
	public static void setBucketName(String bucketName) {
		OSSClientUtil.bucketName = bucketName;
	}

    //getter/setter������

}