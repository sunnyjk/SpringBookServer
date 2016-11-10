package com.cjon.book.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cjon.book.dto.BookDTO;

public class BookDAO {

	public String select(BookDTO entity) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String result = null;
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost:3306/library";
			String user = "jQuery";
			String password = "jQuery";
			con = DriverManager.getConnection(url, user, password);
			
			String sql = "select bisbn, btitle, bauthor, bprice, bimgurl from book "
					+ "where btitle like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + entity.getBtitle() + "%");
			
			rs = pstmt.executeQuery();
			
			
			JSONArray arr = new JSONArray();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("title", rs.getString("btitle"));
				obj.put("author", rs.getString("bauthor"));
				obj.put("price", rs.getInt("bprice"));
				obj.put("img", rs.getString("bimgurl"));
				
				arr.add(obj);
			}
			
			result = arr.toJSONString();
			
		} catch(Exception e){
			
		} finally {
			try{
				rs.close();
				pstmt.close();
				con.close();
			} catch(Exception e2){
				
			}
		}
		
		return result;
	}

}
