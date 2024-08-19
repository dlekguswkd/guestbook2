package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;

public class GuestbookDao {
	
   // 필드
   private Connection conn = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;

   private String driver = "com.mysql.cj.jdbc.Driver";
   private String url = "jdbc:mysql://localhost:3306/phonebook_db";
   private String id = "phonebook";
   private String pw = "phonebook";
	
	//생성자
	//기본생성자 사용 (그래서 생략)
   
	
	//메소드 gs
   	//필드값을 외부에서 사용하면 안됨 (그래서 생략)
   
	
   //메소드 일반
   // DB연결 메소드
   private void getConnection() {
      try {
         // 1. JDBC 드라이버 (Oracle) 로딩
         Class.forName(driver);

         // 2. Connection 얻어오기
         conn = DriverManager.getConnection(url, id, pw);

      } catch (ClassNotFoundException e) {
         System.out.println("error: 드라이버 로딩 실패 - " + e);

      } catch (SQLException e) {
         System.out.println("error:" + e);
      }
   }

   // 자원정리 메소드
   private void close() {
      // 5. 자원정리
      try {
         if (rs != null) {
            rs.close();
         }
         if (pstmt != null) {
            pstmt.close();
         }
         if (conn != null) {
            conn.close();
         }
      } catch (SQLException e) {
         System.out.println("error:" + e);
      }
   }
   
   
	//사람 정보 삭제하기
	public int deleteGuest(int no, String password) {

		int count = -1; 
		
		this.getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "";
			query += " delete from person ";
			query += " where no = ? ";	// 겹치지 않는 정보를 줘야 내가 원하는것만 지워짐
			query += " and password = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			pstmt.setInt(2, password);

			// 실행
			count = pstmt.executeUpdate();


			// 4.결과처리
			System.out.println(count + "건 삭제");

			
		}  catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
		
	}
	
   
   //사람 정보 수정하기 1명
   public int updateGuest(GuestVo guestVo) {
	   
	   int count = -1;
	   
	   System.out.println("dao 수정");
	   System.out.println(guestVo);
	   
	   this.getConnection();
	   
	   try {
		   // 3. SQL문 준비 / 바인딩 / 실행
		   // SQL문 준비
		   String query="";
		   query += " update person ";
		   query += " set name = ?, ";
		   query += "	  password = ?, ";
		   query += "     content = ? ";
		   query += "     reg_date = null ";
		   query += " where no = ? ";

		   
		   // 바인딩
		   pstmt = conn.prepareStatement(query);
		   pstmt.setString(1, guestVo.getName());
		   pstmt.setString(2, guestVo.getPassword());
		   pstmt.setString(3, guestVo.getContent());
		   pstmt.setInt(4, guestVo.getNo());
		   
		   
		   // 실행
		   count = pstmt.executeUpdate();

		   
		   // 4. 결과처리
		   System.out.println(count +" 수정");

		      
	   } catch (SQLException e) {
		   System.out.println("error:" + e);
	   }
	   
	   this.close();
	   
	   return count;
	   
   }
   
   
   
   //사람 1명 정보 가져오기 (수정) -----------------------------------------------------
   public GuestVo getGuestOne(int no) {
	   
	   GuestVo personVo = null;  
	   
	   this.getConnection();
	   
	   try {
		   // 3. SQL문 준비 / 바인딩 / 실행
		   // SQL문 준비
		   String query="";
		   query += " select person_id, ";
		   query += "		name, ";
		   query += "        hp, ";
		   query += "        company ";
		   query += " from person ";
		   query += " where person_id = ? ";

		   
		   // 바인딩
		   pstmt = conn.prepareStatement(query);
		   pstmt.setInt(1, no);
		   
		   
		   // 실행
		   rs = pstmt.executeQuery();
	
		   
		   // 4. 결과처리
		   rs.next();
		   
		   int personId = rs.getInt("person_id");
		   String name = rs.getString("name");
		   String hp = rs.getString("hp");
		   String company = rs.getString("company");
		   
		   personVo = new GuestVo(personId, name, hp, company);
		      
	   } catch (SQLException e) {
		   System.out.println("error:" + e);
	   }
	   
	   this.close();
	   
	   return personVo;
	   
   }
   
     
   //사람정보 저장 (등록)
   public int insertGuest(GuestVo guestVo) {
	   
	   int count = -1;
	   
	   this.getConnection();
	   
	   try {
		   // 3. SQL문 준비 / 바인딩 / 실행
		   // SQL문 준비
		   String query="";
		   query += " insert into guest ";
		   query += " values(null, ?, ?, ?, now()) ";

		   
		   // 바인딩
		   pstmt = conn.prepareStatement(query);
		   pstmt.setString(1, guestVo.getName());
		   pstmt.setString(2, guestVo.getPassword());
		   pstmt.setString(3, guestVo.getContent());

		   
		   // 실행
		   count = pstmt.executeUpdate();

		   
		   // 4. 결과처리
		   //한건 등록됐다 확인하는 창이지만 굳이 볼필요 없기때문에 작성하지 않아도 상관없음

		      
	   } catch (SQLException e) {
		   System.out.println("error:" + e);
	   }
	   
	   this.close();
	   
	   System.out.println(count + "건 등록");
	   
	   return count;
	   
   }
   
   
   
   //리스트 가져오기
   public List<GuestVo> getGuestList() {
	   
	   List<GuestVo> guestList = new ArrayList<GuestVo>();
	   
	   this.getConnection();
	   
	   try {
		   // 3. SQL문 준비 / 바인딩 / 실행
		   // SQL문 준비
		   String query = "";
		   query += " select no, ";
		   query += "		 name, ";
		   query += "        password, ";
		   query += "        content ";
		   query += "        reg_date ";
		   query += " from guest ";
		   
		   // 바인딩
		   pstmt = conn.prepareStatement(query);
		   
		   // 실행
		   rs = pstmt.executeQuery();
		   
		   // 4. 결과처리
		   while (rs.next()) {
			   int id = rs.getInt("no");
			   String name = rs.getString("name");
			   String password = rs.getString("password");
			   String content = rs.getString("content");
			   String reg_date = rs.getString("reg_date");
			   
			   GuestVo personVo = new GuestVo(id, name, password, content, reg_date);
			  
			   guestList.add(guestVo);
			   
		   }
		      
	   } catch (SQLException e) {
		   System.out.println("error:" + e);
	   }
	  
	   this.close();
	   
	   return guestList;
	   
   }
   
}
