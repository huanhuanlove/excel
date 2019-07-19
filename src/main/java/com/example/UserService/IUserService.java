package com.example.UserService;

import com.example.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {

    public List<User> findAll();

    public void addUser(User user);

    public void importExcel(MultipartFile userExcel, String userExcelFileName);
}
