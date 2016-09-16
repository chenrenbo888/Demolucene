package win.renboqieqie.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LogDocMergePolicy;
import org.apache.lucene.index.LogMergePolicy;
import org.apache.lucene.index.MergePolicy;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.RAMDirectory;

import com.sun.xml.internal.ws.util.xml.ContentHandlerToXMLStreamWriter;

import win.renboqieqie.util.Contants;
import win.renboqieqie.util.LuceneUtil;

public class TestOptimise {

	public void testOptimise1() throws Exception{
		Directory directory = FSDirectory.open(new File(Contants.INDEXURL));
		
		IndexWriterConfig conf = new IndexWriterConfig(LuceneUtil.getMatchVersion(),LuceneUtil.getAnalyzer());
		//在lucene 里面都是0配置  都是通过设置对象的参数来配置
		
		
		
		/**
		 * MergePolicy 设置合并规则……
		 */
		LogMergePolicy mergePolicy = new LogDocMergePolicy();
		/**
		 * 1mergeFactor
		 * 当这个值越小，更少的内存被运用，搜索的时候越快，创建缩影的时候越慢
		 */
		mergePolicy.setMergeFactor(3);
		conf.setMergePolicy(mergePolicy);
		IndexWriter indexWriter = new IndexWriter(directory,conf);
		
		
	}
	/**
	 * 排除停用词，被分词器过滤掉，词就不会被建立索引，索引文件变小，搜索更快
	 */
	public void testOptimise2(){
		
	}
	/**
	 * 将索引数据分区存放
	 */
	public void testOptimise3(){
		
	}
	/**
	 * 将索引放在内存当中
	 * @throws IOException 
	 * @throws Exception 
	 */
	public void testOptimise4() throws IOException, Exception{
		
		//索引放在一盘当中
		Directory directory1 =FSDirectory.open(new File(Contants.INDEXURL));
		
		IOContext ioContext = new IOContext();
		//索引放在内存当中
		Directory directory =new RAMDirectory(directory1,ioContext);
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		String fields [] = {"title"};
		QueryParser queryParser = new MultiFieldQueryParser(LuceneUtil.getMatchVersion(), fields, LuceneUtil.getAnalyzer());
		//不同的规则构造不同的子类……
		//title:keywords , content:keywords
		Query query = queryParser.parse("巨大");
		
		TopDocs topDocs = indexSearcher.search(query, 100);
		
		System.out.println(topDocs.totalHits);
	}
	/**
	 * 通过查询条件进行优化
	 */
	public void testOptions(){
		
	}
	
}
