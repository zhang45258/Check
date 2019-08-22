package com.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    // �ϴ��ļ��洢Ŀ¼
    private static final String UPLOAD_DIRECTORY = "upload";
 
    // �ϴ�����
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
 
    /**
     * �ϴ����ݼ������ļ�
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

     
            // �����ϴ�����
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // �����ڴ��ٽ�ֵ - �����󽫲�����ʱ�ļ����洢����ʱĿ¼��
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            // ������ʱ�洢Ŀ¼
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
     
            ServletFileUpload upload = new ServletFileUpload(factory);
             
            // ��������ļ��ϴ�ֵ
            upload.setFileSizeMax(MAX_FILE_SIZE);
             
            // �����������ֵ (�����ļ��ͱ�����)
            upload.setSizeMax(MAX_REQUEST_SIZE);
            
            // ���Ĵ���
            upload.setHeaderEncoding("UTF-8"); 

            // ������ʱ·�����洢�ϴ����ļ�
          
            String uploadPath = "C:/MyEclipse 2017 CI/Check/WebRoot/";
           
             
     
     
            try {
                // ���������������ȡ�ļ�����
                
                List<FileItem> formItems = upload.parseRequest(request);
     
                if (formItems != null && formItems.size() > 0) {
                    // ����������
                    for (FileItem item : formItems) {
                        // �����ڱ��е��ֶ�
                        if (!item.isFormField()) {
                            String fileName = "jiexu.xls";
                            String filePath = uploadPath +  fileName;
                            File storeFile = new File(filePath);
                            // �ڿ���̨����ļ����ϴ�·��
                            System.out.println(filePath);
                            // �����ļ���Ӳ��
                            item.write(storeFile);
                            request.setAttribute("message","�ļ��ϴ��ɹ�!");
                        }
                    }
                }
            } catch (Exception ex) {
                request.setAttribute("message",
                        "������Ϣ: " + ex.getMessage());
            }
            // ��ת�� message.jsp
            getServletContext().getRequestDispatcher("/Uploadmessage.jsp").forward(
                    request, response);
        }
    }