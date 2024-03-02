package com.distribuida.appbooks.dtos;

import com.distribuida.appbooks.db.Book;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookDto {
    private Integer id;
    private String isbn;
    private String title;
    private Double price;

    private Integer authorId;
    private String authorName;

    public static BookDto from(Book obj) {
        BookDto ret = new BookDto();

        ret.setId(obj.getId());
        ret.setIsbn(obj.getIsbn());
        ret.setPrice(obj.getPrice());
        ret.setTitle(obj.getTitle());

        return ret;
    }


}
