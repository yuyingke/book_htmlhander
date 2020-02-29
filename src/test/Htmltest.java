package test;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.BookDao;
import entity.Book;

import http.Http;

public class Htmltest {

	
	@Test
	public void testsqlsessiontemplate() {
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring_config.xml");
		BookDao bookDao = (BookDao) applicationContext.getBean("bookDao");
		/*Book book = new Book("1", "http", "2345797977665","");
		bookDao.deleteBook(book.getTitle());
		bookDao.insertBook(book);*/
		urltest(bookDao);
	}
	
	public String urlget(String url) {
		//Http http = new Http("http://www.zzdxsw.org/yuanzun/7526719.html");
		Http http = new Http(url);
		String date = http.doget();
		//System.err.println(date);
		return date;
	}
	
	//@Test
	public String htmlhandler(String date, BookDao bookDao) {
		Document document = Jsoup.parse(date);
		
		Elements elements = document.select("div[class=book reader]");

		Book book = new Book();
		String[] paths = elements.select("div[class=p]").text().split("\\>");
		book.setName(paths[1].trim());
		book.setTitle(elements.select("div[class=content]").select("h1").text());
		book.setBookcontext(elements.select("div[class=showtxt]").text());
		/*System.out.println("名字：" + paths[1].trim());
		System.out.println("章节：" + elements.select("div[class=content]").select("h1").text());
		System.out.println("内容：" + elements.select("div[class=showtxt]").text());*/
		elements = elements.select("div[class=page_chapter]").select("li").select("a");
		List<String> link = new ArrayList<String>();
		for(Element element : elements) {
			link.add(element.select("a").attr("href"));			
		}
		book.setNext(link.get(2));
		//System.out.println("下一章：" + link.get(2));
		//System.out.println(book.toString());
		
		if(bookDao.getBook(book.getTitle()) == null) {
			bookDao.insertBook(book);
		}else {
			System.out.println("已经存在");
		}
		
		return link.get(2);
	}
	
	//@Test
	public void urltest(BookDao bookDao) {
		String host = "https://www.ddbiquge.cc";
		String nexthtml = "/chapter/323_14655509.html";
		//nexthtml = urlget("https://www.ddbiquge.cc/chapter/323_42682527.html");
		//htmlhandler(nexthtml, bookDao);
		//System.out.println(nexthtml);
		//System.out.println(test(nexthtml).equals("/book/323.html"));

		while(true){
			nexthtml = htmlhandler(urlget(host + nexthtml), bookDao);
			if(nexthtml.equals("/book/323.html")) break;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*String[] infos = info.split("\\| ");
		for(String str : infos) {
			System.out.println(str);
		}*/
	}
}
