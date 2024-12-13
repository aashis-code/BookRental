package com.bookrental.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book", uniqueConstraints = { @UniqueConstraint(columnNames = { "isbn" }) })
public class Book extends BaseEntityDelete {

	@Id
	@SequenceGenerator(name = "book_seq_gen", allocationSize = 1, sequenceName = "book_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq_gen")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "number_of_pages")
	private Integer numberOfPages;

	@Column(name = "isbn")
	private String isbn;

	@Column(name = "rating")
	private Double rating;

	@Column(name = "stock_count")
	private Integer stockCount;

	@Column(name = "published_date")
	private LocalDate publishedDate;

	@Column(name = "photo")
	private String photo;

	@ManyToMany
	@JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_book_author_book")), inverseJoinColumns = @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "FK_book_author_author")))
	@JsonManagedReference
	private List<Author> authors;

	@ManyToOne
	@JoinColumn(name = "categoryId", foreignKey = @ForeignKey(name = "FK_book_category"))
	@JsonManagedReference
	private Category category;

	@OneToMany(mappedBy = "book")
	private List<BookTransaction> bookTransactions;

}
