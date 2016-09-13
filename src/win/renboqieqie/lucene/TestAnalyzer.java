package win.renboqieqie.lucene;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 分词器
 * @author Administrator
 *
 */
public class TestAnalyzer {
	
	public static void main(String[] args) throws Exception{
		//标准分词法
		//Analyzer analyzer =new StandardAnalyzer(Version.LUCENE_44);
		//二分法分词
		//Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_44);
		//Analyzer是一个抽象类，我们能不能改造它，来定义自己的分词规则……
		//有没有第三方已经实现好了，直接拿来用，google的download,第三方开发的工具包
		//github是哪个第三方的包
		//第三方的中文分词器，庖丁分词器，特点：扩展新的词；自定义停用词
		
		//2012FF_FUL
		Analyzer analyzer = new IKAnalyzer();
		//自定义扩展词
		
		String keyWord = "lucene 是java传智播客的一个全文检索的高大上的工具包仁波切切";
		testAnalzyer(analyzer,keyWord);
	}
	public static void testAnalzyer(Analyzer analyzer,String keyWord) throws Exception{
		System.out.println("当前使用的分词器：" + analyzer.getClass().getSimpleName());
		TokenStream tokenStream = (TokenStream) analyzer.tokenStream("content", new StringReader(keyWord));
	    tokenStream.addAttribute(CharTermAttribute.class);
	    tokenStream.reset();
	    while (tokenStream.incrementToken()) {
	       CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
	          System.out.println(new String(charTermAttribute.toString()));
	   }
		
	}
}
