package com.lambert.service;

import static cn.org.rapid_framework.generator.GeneratorConstants.JDBC_DRIVER;
import static cn.org.rapid_framework.generator.GeneratorConstants.JDBC_PASSWORD;
import static cn.org.rapid_framework.generator.GeneratorConstants.JDBC_SCHEMA;
import static cn.org.rapid_framework.generator.GeneratorConstants.JDBC_URL;
import static cn.org.rapid_framework.generator.GeneratorConstants.JDBC_USERNAME;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.lambert.entity.GeneratorJdbc;
import com.lambert.utls.IOUtil;
import com.lambert.utls.ZipUtil;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;
import cn.org.rapid_framework.generator.provider.db.table.TableFactory;
import cn.org.rapid_framework.generator.provider.db.table.model.Table;
import cn.org.rapid_framework.generator.util.ZipUtils;

@Service
public class DownLoadZipService {

	public String[] loadXml() {
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		File file = new File(path + "generator");
		String[] strfiles = null;
		if (file.isDirectory()) {
			strfiles = file.list();
		}
		return strfiles;
	}

	public void deleteXml(String delId) {
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		File file = new File(path + "generator" + File.separator + delId);
		if (file.exists())
			file.delete();
	}

	public GeneratorJdbc showXml(String id) {
		new GeneratorProperties(new String[] { "generator" + File.separator + id });
		GeneratorJdbc jdbc = new GeneratorJdbc();
		jdbc.setDriver(GeneratorProperties.getProperty(JDBC_DRIVER));
		jdbc.setSchema(GeneratorProperties.getProperty(JDBC_SCHEMA));
		jdbc.setUrl(GeneratorProperties.getProperty(JDBC_URL));
		jdbc.setPassword(GeneratorProperties.getProperty(JDBC_PASSWORD));
		jdbc.setUsername(GeneratorProperties.getProperty(JDBC_USERNAME));
		jdbc.setXml(id);
		List<Table> tables = TableFactory.getInstance().getAllTables();
		jdbc.setTables(tables);
		return jdbc;
	}

	public void generatorZipFile(String xml, String table, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		new GeneratorProperties(new String[] { "generator" + File.separator + xml });
		GeneratorFacade g = new GeneratorFacade();
		File templatePath = new File(
				Thread.currentThread().getContextClassLoader().getResource("").getPath() + "template");
		g.getGenerator().addTemplateRootDir(templatePath);
		// 删除生成器的输出目录//
		g.deleteOutRootDir();
		String filename = null;
		if (StringUtils.isEmpty(table)) {
			g.generateByAllTable();
			filename = "code.zip";
		} else {
			g.generateByTable(table);
			filename = "code_" + table + ".zip";
		}
		String ourdir = g.getGenerator().getOutRootDir();
		OutputStream out = response.getOutputStream();
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
		zip(out, ourdir);
		File f = new File(ourdir);
		if (f.exists())
			f.delete();
	}

	public static OutputStream zip(OutputStream out, String filePath) {
		File source = new File(filePath);
		ZipOutputStream zos = null;
		if (source.exists()) {
			try {
				zos = new ZipOutputStream(new BufferedOutputStream(out));
				addEntry("/", source, zos);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				IOUtil.closeQuietly(zos, out);
			}
		}
		return zos;
	}

	private static void addEntry(String base, File source, ZipOutputStream zos) throws IOException {
		// 按目录分级，形如：/aaa/bbb.txt
		String entry = base + source.getName();
		if (source.isDirectory()) {
			for (File file : source.listFiles()) {
				// 递归列出目录下的所有文件，添加文件Entry
				addEntry(entry + "/", file, zos);
			}
		} else {
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				byte[] buffer = new byte[1024 * 10];
				fis = new FileInputStream(source);
				bis = new BufferedInputStream(fis, buffer.length);
				int read = 0;
				zos.putNextEntry(new ZipEntry(entry));
				while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
					zos.write(buffer, 0, read);
				}
				zos.closeEntry();
			} finally {
				IOUtil.closeQuietly(bis, fis);
			}
		}
	}

	public void downXml(String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		File templatePath = new File(Thread.currentThread().getContextClassLoader().getResource("").getPath()
				+ "generator" + File.separator + id);
		ServletOutputStream out = response.getOutputStream();
		out.write(IOUtils.toByteArray(new FileInputStream(templatePath)));
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(id, "UTF-8"));
		out.close();
	}

	public void addXml(HttpServletRequest request, HttpServletResponse response, MultipartFile xml) throws IOException {
		String dir = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "generator";
		String fileName = xml.getOriginalFilename();
		FileUtils.writeByteArrayToFile(new File(dir, fileName), xml.getBytes());
		
	}
}
