package com.bookrental.modelmapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;

import com.bookrental.dto.BookAddRequest;
import com.bookrental.model.Book;

public class BookModelMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
//	MAP BookAddRequestToBook to Book skipping certain fields
	public Book BookAddRequestToBook(BookAddRequest bookAddRequest) {
		
		modelMapper.addMappings(new PropertyMap<BookAddRequest, Book>() {
			@Override
            protected void configure() {
				
				  skip(destination.getAuthors());
				  skip(destination.getCategory());
				  skip(destination.getBookTransactions());
			}
		});
		
		return modelMapper.map(bookAddRequest, Book.class);
	}

}
