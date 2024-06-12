package com.api.smpl_library_api.dto.request;

import com.api.smpl_library_api.dto.UserDTO;
import com.api.smpl_library_api.model.Author;
import com.api.smpl_library_api.model.Book;
import lombok.Data;

@Data
public class BookRequest {

    private Book book;

    private UserDTO userDTO;
}
