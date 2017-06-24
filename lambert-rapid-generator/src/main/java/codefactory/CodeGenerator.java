package codefactory;

import cn.org.rapid_framework.generator.GeneratorFacade;

public class CodeGenerator {
	public  String getPath(){
		Class.class.getClass().getResource("/").getPath();
		String path = getClass().getResource(".").getPath();
		return path;
	}
	public static void main(String[] args) throws Exception {
		String path = Class.class.getClass().getResource("/").getPath();
		String templatePath = path+"/template";
		GeneratorFacade g = new GeneratorFacade();
		g.getGenerator().addTemplateRootDir(templatePath);
		// 删除生成器的输出目录//
		g.deleteOutRootDir();
		// 通过数据库表生成文件
//		g.generateByTable("user", "blog");
		g.generateByAllTable();
		// 自动搜索数据库中的所有表并生成文件,template为模板的根目录
		// g.generateByAllTable();
		// 按table名字删除文件
		// g.deleteByTable("table_name", "template");
	}

}
