package win.renboqieqie.lucene;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import win.renboqieqie.util.LuceneUtil;

public class TestScore {
	
	public static void main(String[] args) throws Exception {
		//内容一样得分一样
		//人工干预得分
		testSearcher("大");
	}
	public static void testSearcher(String keywords) throws Exception{
		
		IndexSearcher indexSearcher = LuceneUtil.getIndexSearch();
		//需要根据那几个字段进行检索……
		String fields[] ={"title"};
		QueryParser queryParser = new MultiFieldQueryParser(LuceneUtil.getMatchVersion(), fields, LuceneUtil.getAnalyzer());
		//不同的规则构造不同的子类……
		//title:keywords , content:keywords
		Query query = queryParser.parse(keywords);
		
		//检索索引目录，会把索引目录都读取一边……
		//检索复合query 前面N条记录……
		TopDocs topDocs = indexSearcher.search(query, 100);
		System.out.println("总记录数==total=="+topDocs.totalHits);
		//唯一标识
		ScoreDoc scoreDocs[] = topDocs.scoreDocs;
		for(ScoreDoc scoreDoc:scoreDocs){
			System.out.println("文档编号，lucene的唯一标识"+scoreDoc.doc+"=====得分======"+scoreDoc.score);
		}
	}
}
