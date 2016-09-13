package win.renboqieqie.test;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import win.renboqieqie.bean.Article;
import win.renboqieqie.dao.LuceneDao;

public class JunitTest {

	private LuceneDao luceneDao = new LuceneDao();
	@Test
	public void addIndex() throws IOException{
		
		for(int i=0;i<50;i++)
		{
			Article article = new Article();
			
			article.setId(i);
			
			article.setTitle("第五百二十二章 巨大的爆炸");
			article.setContent("巨大的爆炸，在瞬间爆出的热能就将这里一切的建筑都溶解掉了，电浆弹的使用，使得这个区域被破坏得相当彻底，半个爱情海的沙滩已经被高温之下彻底变成了晶体的平面，裸露出来的海滩下处处都是升腾的热气。");
			article.setAuthor("跳舞");
			article.setLink("http://www.sqsxs.com/book/2/2375/2571922.html");
			luceneDao.addIndex(article);
		}
	}
	@Test
	public void testSearch() throws Exception{
		String keywords = "巨大";
		//根据title和content去检索    textfield 现在使用得是单字分词器
		//title：有
		//title
		List<Article> listArticles = luceneDao.findIndex(keywords,10,10);
		for(Article article:listArticles)
		{
			System.out.println(article.getId());
			System.out.println(article.getAuthor());
			System.out.println(article.getTitle());
			System.out.println(article.getContent());
			System.out.println(article.getLink());
		}
	}
	@Test
	public void del() throws IOException{
		luceneDao.delIndex("title", "跳舞");
	}
	@Test
	public void testUpdate() throws Exception{
		
		String fielName= "title";
		String fieldValue="巨";
		Article article = new Article();
		article.setAuthor("鸟");
		article.setContent("巨大的鸟在飞");
		article.setId(999);
		article.setLink("www.baidu.com");
		article.setTitle("巨大的鸟");
		luceneDao.updateIndex(fielName, fieldValue,article);
	}
}
