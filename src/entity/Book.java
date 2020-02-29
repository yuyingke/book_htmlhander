package entity;

public class Book {

	private String name;
	private String title;
	private String bookcontext;
	private String next;
	
	public Book() {}
	public Book(String name, String title, String bookcontext, String next) {
		this.name = name;
		this.title = title;
		this.bookcontext = bookcontext;
		this.next = next;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBookcontext() {
		return bookcontext;
	}
	public void setBookcontext(String bookcontext) {
		this.bookcontext = bookcontext;
	}
	public String getNext() {
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	@Override
	public String toString() {
		return "Book [name=" + name + ", title=" + title + ", bookcontext=" + bookcontext + ", next=" + next + "]";
	}
	
}
