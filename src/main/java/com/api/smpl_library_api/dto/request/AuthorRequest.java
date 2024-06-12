package com.api.smpl_library_api.dto.request;

import com.api.smpl_library_api.dto.UserDTO;
import com.api.smpl_library_api.model.Author;
import lombok.Data;
import org.apache.catalina.User;

@Data
public class AuthorRequest {

    private Author author;

    private UserDTO userDTO;
}
