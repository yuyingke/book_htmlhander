package dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import entity.Book;

@Repository
public interface BookDao {

	public int insertBook(Book book);
	
	public int deleteBook(String title);
	
	public int updateBook(Book book);
	
	public Book getBook(String title);
	
	public List<Book> findBook(String title);
}
