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
		System.out.println("no："+no);
		System.out.println("txt："+txt);
		wright2txt(txt);//保存文本，用于word2vec模型的训练
		//将前台发过来的提问按照“$$$”分离成数组，每个元素是每次提问的问题
		String[] txtAddress=txt.split("\\$\\$\\$");
		//预处理
		List<String> keywordList = new ArrayList<String>();
		//1.长难句处理。关键词提取。利用投票的原理，让每一个词给它的邻居（术语称窗口）投赞成票,票的权重取决于自己的票数。
		if(txtAddress[0].length() >= 30){
			keywordList = HanLP.extractKeyword(txtAddress[0], 7);
			txt = L2S(keywordList);
		}else if (txtAddress[0].length() <= 6){
		//2.分词，词性标注，依存句法分析，语境分析
			keywordList = yujingfenxi(txtAddress);
			txt = L2S(keywordList);
		}else{
		//3.正常处理，分词
			 //CRFLexicalAnalyzer analyzer = new CRFLexicalAnalyzer();
			 txt = txtAddress[0];
		}
			System.out.println(txt);
		
			if(txt!=null){
		//下面发送文字，接收api接口返回的数据
			String params ="{\"no\":\""+no+"\",\"text\":\""+txt+"\"}";
			String data=Api.post("http://127.0.0.1:6088",params);
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.print(data);
			out.close();
		}
		
	}
	
	//分词，词性标注，依存句法分析，语境分析
	public static List<String> yujingfenxi(String[] txtAddress){
		CoNLLSentence sentence0 = HanLP.parseDependency(txtAddress[0]);//一步到位^_^依存句法分析
		//System.out.println(sentence0);
		List<String> txt = new ArrayList<String>();
		for (CoNLLWord word : sentence0) {
			txt.add(word.LEMMA);
		} //分词结果
		if(useLoop(sentence0, "主谓关系", "动宾关系")){//如果该句子有"主谓关系"或"动宾关系"，则直接返回分词结果
			return txt;
	    }else{
			//如果没有"主谓关系"或"动宾关系"，则判断其句式不完整，需要替换到上一句去
			//1.先倒数，找到上一句大于等于6个字的句，用于消解
			String txt1 = "";
			for(int i = 1;i<txtAddress.length;i++){
				if(txtAddress[i].length() > 6){
					txt1=txtAddress[i]; 
				}
			}
			if(txt1 == ""){ //如果没有大于6个字的话
				return txt;
			}
			//2.分析其依存句法,并做一个分词list
			CoNLLSentence sentence1 = HanLP.parseDependency(txt1);
			//System.out.println(sentence1);
			List<String> txt_new = new ArrayList<String>();
			for (CoNLLWord word : sentence1) {
				txt_new.add(word.LEMMA);
			} //分词结果
			//3.原句的核心关系的词的ID，词性
			int wordId = 1;
			String wordCPOSTAG = null;			
			for(CoNLLWord word : sentence0) {
				if (word.DEPREL.equals("核心关系")){
					wordId =  word.ID; 
					wordCPOSTAG =word.CPOSTAG;
				}
			}
			//4.找到应该替换的词的ID
			int wordId1 = 1;
			for(CoNLLWord word : sentence1) {
				if (word.CPOSTAG.equals(wordCPOSTAG)){//找到同词性的词
					wordId1 = word.ID;
	            }
			}
		//5.替换该句的词
			txt_new.set(wordId1-1, txt.get(wordId-1));
		return txt_new;
	    }
	}
	
	//判断依存句法分析结果是否包含某个值
	public static void wright2txt(String txt) {
		/* 保存文本，用于word2vec模型的训练
		 * 写入Txt文件
		*/  
			FileWriter fw = null;
			  try {
			  //如果文件存在，则追加内容；如果文件不存在，则创建文件
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

	
	//判断依存句法分析结果是否包含某个值
	public static boolean useLoop(CoNLLSentence sentence, String targetValue1, String targetValue2) {
        for (CoNLLWord word : sentence) {
            if (word.DEPREL.equals(targetValue1)|word.DEPREL.equals(targetValue2))
                return true;
        }
        return false;
    }
	
	
	
	
	//List转数组
	String[] Strings(List list){
		String strings[]=new String[list.size()];
		for(int i=0,j=list.size();i<j;i++){
			strings[i]=(String) list.get(i);
		}
		return strings;
	}
	//List拼成字符串
		String L2S(List list){
			String strings = "";
			for(int i=0,j=list.size();i<j;i++){
				strings=strings+(String) list.get(i);
			}
			return strings;
		}

}
