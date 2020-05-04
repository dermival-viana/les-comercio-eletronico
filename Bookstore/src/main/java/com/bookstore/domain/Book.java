package com.bookstore.domain;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;


import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Book extends DomainEntity{

	private static final long serialVersionUID = 1L;
	

	private String title;
	private String author;
	private String publisher;
	private String publicationDate;
	private String language;
	private int numberOfPages;
	private String format;
	private int isbn;
	private double height;
	private double widht;
	private double weight;
	private double depth;
	private double listPrice;
	private double ourPrice;
	private boolean active=true;

	
	@OneToOne
	private PriceGroup priceGroup;
	
	@Column(columnDefinition="text")
	private String description;
	private int inStockNumber;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.REFRESH	, fetch = FetchType.EAGER)
	@JoinTable(name = "BOOK_CATEGORY", joinColumns = {
			@JoinColumn(name = "book_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "category_id", referencedColumnName = "id") })
	private Set<Category> category = new HashSet<Category>();
	
	@Transient
	private MultipartFile bookImage;
	
	
	@OneToMany(mappedBy = "book")
	@JsonIgnore
	private List<BookToCartItem> bookToCartItemList;
	
	
	public Book() {

	}

	public Book(Long id) {
		super(id);

	}


	public Book(String title) {
		super();
		this.title = title;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	
	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidht() {
		return widht;
	}

	public void setWidht(double widht) {
		this.widht = widht;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getDepth() {
		return depth;
	}

	public void setDepth(double depth) {
		this.depth = depth;
	}

	
	public Set<Category> getCategory() {
		return category;
	}



	public void setCategory(Set<Category> category) {
		this.category = category;
	}



	public double getListPrice() {
		return listPrice;
	}

	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}

	public double getOurPrice() {
		return ourPrice;
	}

	public void setOurPrice(double ourPrice) {
		this.ourPrice = ourPrice;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getInStockNumber() {
		return inStockNumber;
	}

	public void setInStockNumber(int inStockNumber) {
		this.inStockNumber = inStockNumber;
	}

	public MultipartFile getBookImage() {
		return bookImage;
	}

	public void setBookImage(MultipartFile bookImage) {
		this.bookImage = bookImage;
	}

	public List<BookToCartItem> getBookToCartItemList() {
		return bookToCartItemList;
	}

	public void setBookToCartItemList(List<BookToCartItem> bookToCartItemList) {
		this.bookToCartItemList = bookToCartItemList;
	}

	public PriceGroup getPriceGroup() {
		return priceGroup;
	}

	public void setPriceGroup(PriceGroup priceGroup) {
		this.priceGroup = priceGroup;
	}

		
	
}
