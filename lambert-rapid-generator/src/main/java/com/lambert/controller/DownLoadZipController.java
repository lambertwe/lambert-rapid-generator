package com.lambert.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.lambert.entity.GeneratorJdbc;
import com.lambert.service.DownLoadZipService;

@Controller
@RequestMapping("/download")
public class DownLoadZipController {
	
	@Autowired
	private DownLoadZipService  downLoadZipService;
	
	@RequestMapping("/loadXml")
	public String loadXml(Map<String,Object> model){
		String[] result = downLoadZipService.loadXml();
		 model.put("result", result);
		return "download";
	}
	@RequestMapping("/deleteXml")
	public String deleteXml(String delId){
		downLoadZipService.deleteXml(delId);
		return  "redirect:/download/loadXml";
	}
	@RequestMapping("/showXml")
	public String showXml(String id,Map<String,Object> model){
		GeneratorJdbc result = downLoadZipService.showXml(id);
		model.put("data", result);
		return "downloadview";
	}
	@RequestMapping("/tables")
	public void tables(String xml,String table,HttpServletRequest request,HttpServletResponse response) throws Exception{
		downLoadZipService.generatorZipFile(xml,table,request,response);
	}
	
	@RequestMapping("/downXml")
	public void downXml(String id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		downLoadZipService.downXml(id,request,response);
	}
	
	@RequestMapping("/addXml")
	public String addXml(HttpServletRequest request,HttpServletResponse response, MultipartFile xml) throws Exception{
		downLoadZipService.addXml(request,response,xml);
		return "redirect:/download/loadXml";
	}
	
}
