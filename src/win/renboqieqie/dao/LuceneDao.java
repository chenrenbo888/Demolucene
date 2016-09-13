package win.renboqieqie.dao;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

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
	public void addIndex(Article article) throws IOException
	{
		IndexWriter indexWriter = LuceneUtil.getIndexWriter();
		Document document = ArticleUtils.articleToDocument(article);
		indexWriter.addDocument(document);
		indexWriter.close();
	}
	public void delIndex()
	{
		
	}
	public void updateIndex()
	{
		
	}
	public void findIndex()
	{
		
	}
	public void getIndex()
	{
		
	}
}
