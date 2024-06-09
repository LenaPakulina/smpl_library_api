package com.api.smpl_library_api.repository;

import com.api.smpl_library_api.model.Author;
import com.api.smpl_library_api.model.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AuthorRepository extends ListCrudRepository<Author, Integer> {
    @Modifying
    @Query("delete from Author a where a.id= :fId")
    int delete(@Param("fId") Integer id);

    @Modifying
    @Query("""
        update Author a
        set a.name = :#{#author.name}
        where a.id= :#{#author.id}
        """)
    int update(@Param("author") Author author);

    @Modifying
    @Query(value = """
        insert into authors_of_books(book_id, author_id)
        values(:#{#book.id}, :#{#author.id})
        """, nativeQuery = true)
    void createConnectionAuthorToBook(@Param("author") Author author,
                                      @Param("book") Book book);
}
