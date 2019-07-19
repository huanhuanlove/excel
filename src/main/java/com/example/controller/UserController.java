package com.example.controller;

import com.example.UserService.IUserService;
import com.example.excel.ExcelUtil;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 导入导出Excel
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;


    /**
     *  跳到用戶列表
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String findAll(Model model){
        List<User> list = userService.findAll();
        model.addAttribute("userList",list);
        return "list";
    }


    /**
     *  下載Excel导入模板模板
     */
    @RequestMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response){
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "p_w_upload;filename=" + new String("导入用戶Excel格式.xls".getBytes(),"ISO8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();
            ExcelUtil.exportTemplateExcel(outputStream);
            if(outputStream != null){
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     *  导出所有用戶到Excel
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response){
        List<User> userList = null;
        try {
            //1、查找用户列表
            userList = userService.findAll();
            //2、导出
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "p_w_upload;filename=" + new String("用户列表.xls".getBytes(),"ISO8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();
            ExcelUtil.exportUserExcel(userList, outputStream);
            if(outputStream != null){
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  从Excel导入到数据库
     */
    @RequestMapping("/importExcel")
    public String importExcel(Model model, MultipartFile userExcel){
        if (userExcel.isEmpty()) {
            model.addAttribute("msg","上传失败，请选择文件");
            return "redirect:/user/list";
        }
        String userExcelFileName = userExcel.getOriginalFilename(); //获取文件名
        //1、获取文件，并判断是否为excel文件
        if(userExcel != null && userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
            System.out.println(userExcelFileName);
            //2、导入
            userService.importExcel(userExcel, userExcelFileName);
        }
        return "redirect:/user/list";
    }




    /************************************************************************************************/
    /*************************************    断点续传功能    ***************************************/
    /************************************************************************************************/


    @RequestMapping("/update")
    public String fileUpdate(){

        return null;
    }

}
