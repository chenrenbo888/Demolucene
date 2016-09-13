package win.renboqieqie.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
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
	public void delIndex() throws IOException
	{
		IndexWriter indexWriter = LuceneUtil.getIndexWriter();
		
	}
	public void updateIndex()
	{
		
	}
	public List<Article> findIndex(String  keywords) throws Exception
	{
		IndexSearcher indexSearcher = LuceneUtil.getIndexSearch();
		//需要根据那几个字段进行检索……
		String fields[] ={"title","content"};
		QueryParser queryParser = new MultiFieldQueryParser(LuceneUtil.getMatchVersion(), fields, LuceneUtil.getAnalyzer());
		//不同的规则构造不同的子类……
		//title:keywords , content:keywords
		Query query = queryParser.parse(keywords);
		TopDocs topDocs = indexSearcher.search(query, 100);
		System.out.println("总记录数==total=="+topDocs.totalHits);
		//唯一标识
		ScoreDoc scoreDocs[] = topDocs.scoreDocs;
		Article article = null;
		List<Article> articlelist = new ArrayList<Article>();
		for(int i=0 ;i<scoreDocs.length;i++){
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
