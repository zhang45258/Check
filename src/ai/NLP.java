package ai;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;

public class NLP {
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
	public static boolean useLoop(CoNLLSentence sentence, String targetValue1, String targetValue2) {
        for (CoNLLWord word : sentence) {
            if (word.DEPREL.equals(targetValue1)|word.DEPREL.equals(targetValue2))
                return true;
        }
        return false;
    }

	
	
	public static void main(String[] args) throws IOException {
		String[] txtAddress = {"那开车后呢？","开车前多久可以办理退票？"};
		List<String> yujingfenxi =yujingfenxi(txtAddress);
		System.out.println(yujingfenxi);
		
		/*
		
		CoNLLSentence sentence = HanLP.parseDependency("车票");
		//CoNLLSentence sentence1 = HanLP.parseDependency("开车前多久可以办理退票吗？");
		//CoNLLSentence sentence2 = HanLP.parseDependency("那开车后呢？");
		CoNLLWord[] a = sentence.getWordArray();

		System.out.println(a[0]);
		//System.out.println(sentence1);
        //System.out.println(sentence2);
        // 可以方便地遍历它
        for (CoNLLWord word : sentence)
        {System.out.println("-----------------------");
        	System.out.println("ID:        "+word.ID);
        	System.out.println("CPOSTAG:   "+word.CPOSTAG);
        	System.out.println("LEMMA:     "+word.LEMMA);
        	System.out.println("DEPREL:    "+word.DEPREL);
        	System.out.println("NAME:      "+word.NAME);
        	/*
        	System.out.println("HEAD.LEMMA:   "+word.HEAD.LEMMA);
        	System.out.println("HEAD.CPOSTAG: "+word.HEAD.CPOSTAG);
        	System.out.println("HEAD.DEPREL : "+word.HEAD.DEPREL);
        	System.out.println("HEAD.ID:      "+word.HEAD.ID);
        	System.out.println("HEAD.NAME     :"+word.HEAD.NAME);
        	System.out.println("HEAD.POSTAG   :"+word.HEAD.POSTAG);
        	System.out.println("HEAD.hashCode():"+word.HEAD.hashCode());
        	
        	System.out.println("HEAD.toString():"+word.HEAD.toString());
        }
    */
	}

}
