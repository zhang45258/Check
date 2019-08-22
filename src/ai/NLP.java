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
	public static boolean useLoop(CoNLLSentence sentence, String targetValue1, String targetValue2) {
        for (CoNLLWord word : sentence) {
            if (word.DEPREL.equals(targetValue1)|word.DEPREL.equals(targetValue2))
                return true;
        }
        return false;
    }

	
	
	public static void main(String[] args) throws IOException {
		String[] txtAddress = {"�ǿ������أ�","����ǰ��ÿ��԰�����Ʊ��"};
		List<String> yujingfenxi =yujingfenxi(txtAddress);
		System.out.println(yujingfenxi);
		
		/*
		
		CoNLLSentence sentence = HanLP.parseDependency("��Ʊ");
		//CoNLLSentence sentence1 = HanLP.parseDependency("����ǰ��ÿ��԰�����Ʊ��");
		//CoNLLSentence sentence2 = HanLP.parseDependency("�ǿ������أ�");
		CoNLLWord[] a = sentence.getWordArray();

		System.out.println(a[0]);
		//System.out.println(sentence1);
        //System.out.println(sentence2);
        // ���Է���ر�����
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
