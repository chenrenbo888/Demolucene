package win.renboqieqie.util;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import win.renboqieqie.bean.Article;

/**
 * article 的转换类
 * @author Administrator
 *
 */
public class ArticleUtils {
	/**
	 * 将article装换成document
	 * 将article的值设置到document中去
	 * @param article
	 * @return
	 */
	public static Document articleToDocument(Article article){
		Document document = new Document();
		
		IntField idfield = new IntField("id",article.getId(),Store.YES);
		
		StringField authorfield= new StringField("author",article.getAuthor(),Store.YES);
		
		StringField urlfield = new StringField("link",article.getLink(),Store.YES);
		
		TextField titlefield = new TextField("title",article.getTitle(),Store.YES);
		
		TextField contentfield = new TextField("content",article.getContent(),Store.YES);
	
		//设置权重
		//titlefield.setBoost(4f);
		document.add(idfield);
		document.add(authorfield);
		document.add(urlfield);
		document.add(titlefield);
		document.add(contentfield);
		return document;
	}
}
