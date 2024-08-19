package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestVo;


@WebServlet("/gbc")	// ~/guestbook2/gbc
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	// Controller 접수받는일 (업무구분)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//action이 뭔지 알아야함
		String action = request.getParameter("action");
		System.out.println(action);
		
		if ("addList".equals(action)) { 	// action.equals("list")인것을 null을 피하기위해 반대로 씀(주소에 ?~안쓰는 상황)

			//접수
			System.out.println("리스트요청");
			
			//db데이터 가져오기
			GuestbookDao guestbookDao = new GuestbookDao();
			List<GuestVo> guestList = guestbookDao.getGuestList();
		
			
			//화면그리기 --> 포워드
			//request 에 리스트 주소 넣기
			request.setAttribute("guestList", guestList);
			
			//포워드 (webapp에서 시작)
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/addList.jsp");
			rd.forward(request, response);
						
			
		}else if ("insert".equals(action)) {		//등록 -------------------
			
			//접수 
			System.out.println("등록 요청, 데이터 4개 저장해줘");
			
			//나머지 파라미터 꺼내서 guestVo 로  묶기
			String name = request.getParameter("name");
			String pw = request.getParameter("password");
			String content = request.getParameter("content");
			String regDate = request.getParameter("regDate");
			
			//guestVo에서 4개만 있는 생성자 만들어주기
			GuestVo guestVo = new GuestVo(name, pw, content, regDate);
			
			
			//Dao를 메모리에 올리기
			GuestbookDao guestbookDao = new GuestbookDao();
			
			//insertPerson(personVo) 사용해서 db에 저장
			guestbookDao.insertGuest(guestVo);
			
			
			//리다이렉트 (반복할사이트 넣어주기)
			response.sendRedirect("/guestbook2/gbc?action=addList");
			
			
		}else if ("deleteForm".equals(action) ) {		// 삭제폼 -------------------
			
			System.out.println("삭제폼 요청");
			
			//파라미터 꺼내기
			// String 이면 상관없지만 숫자 int 이기 때문에 Integer.parseInt로 싸주기 (다 문자형이기 때문)
			int no = Integer.parseInt(request.getParameter("no"));
			
			//생성자
			GuestVo guestVo = new GuestVo(no);

			//화면그리기 --> 포워드
			//request 에 리스트 주소 넣기
			request.setAttribute("guestVo", guestVo);

			//포워드 (webapp에서 시작)
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/deleteForm.jsp");
			rd.forward(request, response);
			
			
			
			
		}else if ("delete".equals(action) ) {		// 삭제 -------------------
			
			System.out.println("삭제");
			
			//파라미터 꺼내기
			// String 이면 상관없지만 숫자 int 이기 때문에 Integer.parseInt로 싸주기 (다 문자형이기 때문)
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			
			//삭제하기
			//Dao를 메모리에 올린다
			GuestbookDao guestbookDao = new GuestbookDao();
			
			//guestbookDao를 통해서 삭제 delete를 시킨다
			guestbookDao.deleteGuest(no, password);
			
			//리다이렉트 시킨다
			response.sendRedirect("/guestbook2/gbc?action=addList");
			
			
		}else {					// action 없을때 -------------------
			System.out.println("action 없음");
		}
				
	}
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
