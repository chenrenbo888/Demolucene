package win.renboqieqie.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.Test;

import win.renboqieqie.bean.Article;
import win.renboqieqie.util.ArticleUtils;
import win.renboqieqie.util.LuceneUtil;

/**
 * 使用lucene来操作索引库
 * 
 * @author Administrator
 *
 */
public class LuceneDao {
/**
 * 曾删改索引都是通过indexWriter对象来完成
 * @throws IOException 
 */
	@Test
	public void addIndex(Article article) throws IOException
	{
		
		IndexWriter indexWriter = LuceneUtil.getIndexWriter();
		Document document = ArticleUtils.articleToDocument(article);
		indexWriter.addDocument(document);
		indexWriter.close();
	}
	/**
	 * 删除索引，根据字段删除索引
	 * @param fielName
	 * @param fieldValue
	 * @throws IOException
	 */
	public void delIndex(String fielName,String fieldValue) throws IOException
	{
		IndexWriter indexWriter = LuceneUtil.getIndexWriter();
		
		Term term = new Term(fielName,fieldValue);
		//delete from table where condition
		indexWriter.deleteDocuments(term);
		
	}
	/**
	 * 先删除复合条件的，在添加一条
	 * @param fielName
	 * @param fieldValue
	 * @param article
	 * @throws Exception
	 */
	public void updateIndex(String fielName,String fieldValue,Article article) throws Exception
	{
		IndexWriter indexWriter = LuceneUtil.getIndexWriter();
		Term term = new Term(fielName,fieldValue);
		Document doc = ArticleUtils.articleToDocument(article);
		/**
		 * 1.设置更新的条件
		 * 
		 * 2.设置更新的内容
		 * 
		 */
		indexWriter.updateDocument(term, doc);;
	}
	public List<Article> findIndex(String  keywords,int start,int rows) throws Exception
	{
		IndexSearcher indexSearcher = LuceneUtil.getIndexSearch();
		//需要根据那几个字段进行检索……
		String fields[] ={"title","content"};
		QueryParser queryParser = new MultiFieldQueryParser(LuceneUtil.getMatchVersion(), fields, LuceneUtil.getAnalyzer());
		//不同的规则构造不同的子类……
		//title:keywords , content:keywords
		Query query = queryParser.parse(keywords);
		
		//检索索引目录，会把索引目录都读取一边……
		//检索复合query 前面N条记录……
		TopDocs topDocs = indexSearcher.search(query, start+rows);
		System.out.println("总记录数==total=="+topDocs.totalHits);
		//唯一标识
		ScoreDoc scoreDocs[] = topDocs.scoreDocs;
		Article article = null;
		List<Article> articlelist = new ArrayList<Article>();
		//int endResult = start+rows>scoreDocs.length?scoreDocs.length:start+rows;
		//在java中有一个类，比较连个值
		int endResult= Math.min(start+rows, scoreDocs.length);
		for(int i=start ;i<endResult;i++){
			//docID LUCENE 为每一个document的唯一表示。
			int docID= scoreDocs[i].doc;
			System.out.println(docID);
			article = new Article();
			Document document = indexSearcher.doc(docID);
			article.setId(Integer.parseInt(document.get("id")));
			article.setTitle(document.get("title"));
			article.setAuthor(document.get("author"));
			article.setContent(document.get("content"));
			article.setLink(document.get("link"));
			articlelist.add(article);
		}
		return articlelist;
	}
	public void getIndex()
	{
		
	}
}
