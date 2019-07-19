package com.example.UserService.impl;

import com.example.UserService.IUserService;
import com.example.mapper.UserMapper;
import com.example.model.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    /**
     *  从Excel导入到数据库
     * @param userExcel  xls文件
     * @param userExcelFileName 文件名
     */
    public void importExcel(MultipartFile userExcel, String userExcelFileName) {
        try {
            FileInputStream fileInputStream = (FileInputStream) userExcel.getInputStream();
            boolean is03Excel = userExcelFileName.matches("^.+\\.(?i)(xls)$");
            Workbook workbook = is03Excel ? new HSSFWorkbook(fileInputStream) : new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            if(sheet.getPhysicalNumberOfRows()>2){
                User user = null;
                for(int k=2;k<sheet.getPhysicalNumberOfRows();k++)
                {
                    Row row = sheet.getRow(k);
                    user = new User();
                    //用户名
                    Cell cell0 = row.getCell(0);
                    user.setUsername(cell0.getStringCellValue());
                    //账号
                    Cell cell1 = row.getCell(1);
                    user.setPassword(cell1.getStringCellValue());

                    //5、保存
                    userMapper.addUser(user);
                }
                workbook.close();
                fileInputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
