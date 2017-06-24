package com.lambert.controller;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lambert.entity.Person;
import com.lambert.service.PersonService;


@Controller
@RequestMapping("/person")
public class PersonController {

    @Resource
    private PersonService ps;
    
    
    //列表
    @RequestMapping("/listAll")
    public String listAll(Map<String,Object> model){
        List<Person> personList = ps.listAll();
        model.put("personList", personList);
        
        System.out.println(" listall hello");
        
        return "jPersonList";
    }
    
    //列表
    @RequestMapping("/listAllOther")
    public String listAllOther(Model model){
        List<Person> personList1 = ps.listAll();
        model.addAttribute(personList1);
        
        System.out.println(" listallother1 hello");
        
        return "jPersonList";
    }
    
    //转向新增页面
    @RequestMapping(value={"/tocreate"},method=RequestMethod.GET)
    public String tocreate(){
        
        return "jPersonCreate";
    }
    
    //转向修改页面
    @RequestMapping("/toupdate")
    public String toupdate(Integer id, Model model){
        Person p = ps.get(id);
        model.addAttribute(p);
        
        return "jPersonUpdate";
    }
    
    //保存
    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(  HttpServletRequest request,@Valid Person p, BindingResult br, MultipartFile photo) throws IOException{
        //错误处理
        if(br.hasErrors()){
            return "jPersonUpdate";
        }
        
        //上传图片
        ServletContext sc = request.getSession().getServletContext();
        String dir = sc.getRealPath("/upload/");
        String fileName = photo.getOriginalFilename();
        
        //防止图片覆盖
        Long _l = System.nanoTime();
        String _extName = fileName.substring(fileName.indexOf("."));
        fileName = _l + _extName;
        
        FileUtils.writeByteArrayToFile(new File(dir, fileName), photo.getBytes());
        
        p.setPhotoPath("/upload/"+fileName);        //指定图片保存路径
        
        ps.save(p);
        return "redirect:/person/listAll";
    }
    
    //删除
    @RequestMapping("/delete")
    public String delete(@RequestParam("delId")Integer id){
        ps.deleteById(id);
        return "redirect:/person/listAll";
    }
}