package com.cjon.book.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cjon.book.dto.BookDTO;
import com.cjon.book.service.BookSearchKeywordList;
import com.cjon.book.service.BookService;

@Controller
public class BookController {

	@RequestMapping("/bookList")
	// JSONP 방식으로 JSON 데이터를 보낼 것.
	public void getBookList(HttpServletRequest request, HttpServletResponse response){
		String keyword = request.getParameter("keyword");
		String callback = request.getParameter("callback");
		BookDTO dto = new BookDTO();
		dto.setBtitle(keyword);
		
		System.out.println("BookController 들어옴. keyword: " + keyword);
		
		// 서비스 객체 생성
		BookService service = new BookSearchKeywordList();
		String result = service.execute(dto);
		
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(callback + "(" + result + ")");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
