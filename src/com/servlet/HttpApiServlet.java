package com.servlet;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ai.Api;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;


public class HttpApiServlet extends HttpServlet {
	
			
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String no = request.getParameter("no");
		String txt = request.getParameter("txt");
		System.out.println("no��"+no);
		System.out.println("txt��"+txt);
		wright2txt(txt);//�����ı�������word2vecģ�͵�ѵ��
		//��ǰ̨�����������ʰ��ա�$$$����������飬ÿ��Ԫ����ÿ�����ʵ�����
		String[] txtAddress=txt.split("\\$\\$\\$");
		//Ԥ����
		List<String> keywordList = new ArrayList<String>();
		//1.���Ѿ䴦���ؼ�����ȡ������ͶƱ��ԭ����ÿһ���ʸ������ھӣ�����ƴ��ڣ�Ͷ�޳�Ʊ,Ʊ��Ȩ��ȡ�����Լ���Ʊ����
		if(txtAddress[0].length() >= 30){
			keywordList = HanLP.extractKeyword(txtAddress[0], 7);
			txt = L2S(keywordList);
		}else if (txtAddress[0].length() <= 6){
		//2.�ִʣ����Ա�ע������䷨�������ﾳ����
			keywordList = yujingfenxi(txtAddress);
			txt = L2S(keywordList);
		}else{
		//3.���������ִ�
			 //CRFLexicalAnalyzer analyzer = new CRFLexicalAnalyzer();
			 txt = txtAddress[0];
		}
			System.out.println(txt);
		
			if(txt!=null){
		//���淢�����֣�����api�ӿڷ��ص�����
			String params ="{\"no\":\""+no+"\",\"text\":\""+txt+"\"}";
			String data=Api.post("http://127.0.0.1:6088",params);
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.print(data);
			out.close();
		}
		
	}
	
	//�ִʣ����Ա�ע������䷨�������ﾳ����
	public static List<String> yujingfenxi(String[] txtAddress){
		CoNLLSentence sentence0 = HanLP.parseDependency(txtAddress[0]);//һ����λ^_^����䷨����
		//System.out.println(sentence0);
		List<String> txt = new ArrayList<String>();
		for (CoNLLWord word : sentence0) {
			txt.add(word.LEMMA);
		} //�ִʽ��
		if(useLoop(sentence0, "��ν��ϵ", "������ϵ")){//����þ�����"��ν��ϵ"��"������ϵ"����ֱ�ӷ��طִʽ��
			return txt;
	    }else{
			//���û��"��ν��ϵ"��"������ϵ"�����ж����ʽ����������Ҫ�滻����һ��ȥ
			//1.�ȵ������ҵ���һ����ڵ���6���ֵľ䣬��������
			String txt1 = "";
			for(int i = 1;i<txtAddress.length;i++){
				if(txtAddress[i].length() > 6){
					txt1=txtAddress[i]; 
				}
			}
			if(txt1 == ""){ //���û�д���6���ֵĻ�
				return txt;
			}
			//2.����������䷨,����һ���ִ�list
			CoNLLSentence sentence1 = HanLP.parseDependency(txt1);
			//System.out.println(sentence1);
			List<String> txt_new = new ArrayList<String>();
			for (CoNLLWord word : sentence1) {
				txt_new.add(word.LEMMA);
			} //�ִʽ��
			//3.ԭ��ĺ��Ĺ�ϵ�Ĵʵ�ID������
			int wordId = 1;
			String wordCPOSTAG = null;			
			for(CoNLLWord word : sentence0) {
				if (word.DEPREL.equals("���Ĺ�ϵ")){
					wordId =  word.ID; 
					wordCPOSTAG =word.CPOSTAG;
				}
			}
			//4.�ҵ�Ӧ���滻�Ĵʵ�ID
			int wordId1 = 1;
			for(CoNLLWord word : sentence1) {
				if (word.CPOSTAG.equals(wordCPOSTAG)){//�ҵ�ͬ���ԵĴ�
					wordId1 = word.ID;
	            }
			}
		//5.�滻�þ�Ĵ�
			txt_new.set(wordId1-1, txt.get(wordId-1));
		return txt_new;
	    }
	}
	
	//�ж�����䷨��������Ƿ����ĳ��ֵ
	public static void wright2txt(String txt) {
		/* �����ı�������word2vecģ�͵�ѵ��
		 * д��Txt�ļ�
		*/  
			FileWriter fw = null;
			  try {
			  //����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
			  File f=new File("C:\\train\\word2vec.txt");
			  fw = new FileWriter(f, true);
			  } catch (IOException e) {
			  e.printStackTrace();
			  }
			 PrintWriter pw = new PrintWriter(fw);
			 pw.println(txt);
			 pw.flush();
			 try {
			 fw.flush();
			 pw.close();
			 fw.close();
			 } catch (IOException e) {
			 e.printStackTrace();
			 }
    }

	
	//�ж�����䷨��������Ƿ����ĳ��ֵ
	public static boolean useLoop(CoNLLSentence sentence, String targetValue1, String targetValue2) {
        for (CoNLLWord word : sentence) {
            if (word.DEPREL.equals(targetValue1)|word.DEPREL.equals(targetValue2))
                return true;
        }
        return false;
    }
	
	
	
	
	//Listת����
	String[] Strings(List list){
		String strings[]=new String[list.size()];
		for(int i=0,j=list.size();i<j;i++){
			strings[i]=(String) list.get(i);
		}
		return strings;
	}
	//Listƴ���ַ���
		String L2S(List list){
			String strings = "";
			for(int i=0,j=list.size();i<j;i++){
				strings=strings+(String) list.get(i);
			}
			return strings;
		}

}
