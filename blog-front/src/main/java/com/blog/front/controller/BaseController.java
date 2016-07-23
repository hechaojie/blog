package com.blog.front.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class BaseController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		return null;
	}
	
	public void write(HttpServletResponse response,String text){
		PrintWriter writer = null;
		try {
			response.setHeader("Pragma", "no-cache");	// HTTP/1.0 caches might not implement Cache-Control and might only implement Pragma: no-cache
	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expires", 0);
	        
			response.setContentType("text/html; charset=UTF-8" );
	        writer = response.getWriter();
	        writer.write(text);
	        writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (writer != null)
				writer.close();
		}
	}
	
	public void writePlain(HttpServletResponse response,String text){
		PrintWriter writer = null;
		try {
			response.setHeader("Pragma", "no-cache");	// HTTP/1.0 caches might not implement Cache-Control and might only implement Pragma: no-cache
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			
			response.setContentType("text/plain; charset=UTF-8" );
			writer = response.getWriter();
			writer.write(text);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (writer != null)
				writer.close();
		}
	}

}
