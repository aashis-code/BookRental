package com.bookrental.model;

import java.time.LocalDate;

import com.bookrental.helper.RentType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookTransaction", uniqueConstraints = {})
public class BookTransaction {

	@Id
	@SequenceGenerator(name = "bookTransaction_gen", allocationSize = 1, sequenceName = "bookTransaction_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookTransaction_seq")
	private Integer id;

	private String code;

	private LocalDate fromDate;

	private LocalDate toDate;

	@Enumerated(EnumType.STRING)
	private RentType rentStatus;

	private Boolean activeClosed;

	@ManyToOne
	@JoinColumn(name = "memberId", foreignKey = @ForeignKey(name = "FK_bookTransaction_member"))
	private Member member;

	@ManyToOne
	@JoinColumn(name = "bookId", foreignKey = @ForeignKey(name = "FK_bookTransaction_book"))
	private Book book;

}
