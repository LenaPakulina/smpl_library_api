package com.api.smpl_library_api.repository;

import com.api.smpl_library_api.dto.BookDTO;
import com.api.smpl_library_api.model.Author;
import com.api.smpl_library_api.model.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookRepository extends ListCrudRepository<Book, Integer> {
    @Modifying
    @Query("delete from Book b where b.id=:fId")
    int delete(@Param("fId") Integer id);

    @Modifying
    @Query("""
        update Book b
        set b.title = :#{#book.title},
        b.year = :#{#book.year}
        where b.id=:#{#book.id}
        """)
    int update(@Param("book") Book book);

    @Query("""
            SELECT new com.api.smpl_library_api.dto.BookDTO(b.id, b.title, a.id, a.name)
            FROM AuthorOfBook as aob
                     JOIN Book b ON aob.bookId = b.id
                     JOIN Author a ON aob.authorId = a.id
            WHERE (b.title LIKE CONCAT('%',:fCriteria,'%') OR a.name LIKE CONCAT('%',:fCriteria,'%'))
            """)
    List<BookDTO> findByTitleContainingAndAuthor(@Param("fCriteria") String criteria);
}
