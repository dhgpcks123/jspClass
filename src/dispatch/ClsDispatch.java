package dispatch;

import javax.servlet.*;
import javax.servlet.http.*;

import controller.*;

import java.util.*;
import java.io.*;


/**
 * 이 클래스는 확장자가 .cls로 요청이 되는 경우 Dispatch시킬 서블릿 클래스이다.
 * @author	오혜찬
 * @since	2020.11.02
 * @version v.1.0
 */


// url 패턴 매핑은 web.xml에서 정의
public class ClsDispatch extends HttpServlet {
	/*
	 	할 일
	 		1. 요청 내용과 클래스의 인스턴스를 매핑할 맵이 필요하다.
	 			우리는 요청내용과 요청내용에 맞는 클래스의 경로는
	 			properties 파일로 관리할 예정이다.
	 			properties는 맵의 일종으로
	 			키 값을 사용하는 요청내용을 안다면 클래스의 경로도 알 수 있게 된다.
	 */
	private HashMap<String, ClsMain> map;
	
	public void init(ServletConfig config) throws ServletException {
		/* init:초기화. 값 초기화? 값 초기값셋팅한다는거잖아.
			이 함수는 서버가 기동된 후
			.cls로 끝나는 요청이 왔을 경우 최초 딱 한번만 실행되는 함수
			최초로 이 서블릿이 시작되면 준비된 properties 파일을 읽어서
			이것을 이용해서 실제 실행가능한 클래스로 요청 내용과 클래스객체를 매핑 해놔야 한다.
			
			파일에서 직접 읽어서 Map으로 만들어야 하므로
			Properties라는 클래스를 이용해서 작업하자.
		 */
		
		Properties prop = new Properties();
		FileInputStream fin = null;
		try {
			// 할 일
			// 먼저 읽어올 파일의 경로를 알아낸다
			String spath = this.getClass().getResource("").getPath();
				System.out.println("### Clsdispatch spath : " + spath);
			fin = new FileInputStream(spath + "../resources/clsProperties.properties");
			prop.load(fin);
				System.out.println("### Clsdispatch prop-/main.cls : " + prop.get("/main.cls"));
			
		} catch (Exception e) {
			System.out.println("#### .cls init 실패 ####");
			e.printStackTrace();
		} finally {
			try {
				fin.close();
			} catch (IOException e) {}
		}
		// 문자열로 만들어진 맵을 이용해서 실제 사용가능한 map을 초기화 주자
		map = new HashMap<String, ClsMain>();
		
		//먼저 키 값만 뽑아온다.
		Set set = prop.keySet();
		ArrayList list = new ArrayList(set);
		for(Object o  : list) {
			//키 값을 문자열로 강제 형변환
			String skey = (String) o;
			// 키 값을 이용해서 경로 뽑아오고
			String classPath = (String) prop.get(skey);
			
			//실제 실행가능한 클래스로 변환해준다.
			try {
				Class tmp =Class.forName(classPath);
				ClsMain cls = (ClsMain) tmp.newInstance();
				
				//map에 추가해준다
				map.put(skey, cls);
			} catch (Exception e) {
			}
		}
	}
	
	// 실제 요청 내용에 맞는 처리를 담당하는 함수
	public void service(HttpServletRequest req, HttpServletResponse resp)
			 throws ServletException, IOException {
		
		// 할일
		// 1.	요청내용을 알아낸다 <== .cls로 끝나는 모든 요청이 이 함수에서 실행되므로
		//								요청내용에 맞는 실행을 찾아서 해줘야 하겠다.
		String full = req.getRequestURI();
		// 2. full <== /jspcls/xxxx~~/xxx~~.cls
			System.out.println("### dispatch full : " + full);
		String realPath = full.substring(full.indexOf('/',1));
			System.out.println("### dispatch realPath : " + realPath);
		
		// 3. 요청 내용에 맞는 실제 실행할 클래스를 가져온다.
		ClsMain cls = map.get(realPath);
		System.out.println(cls);
		Boolean bool = null;
		/*
		 	bool == null	: 비동기 통신 처리
		 	bool == false	: forward
		 	bool == true	: redirect
		 */
		String view = cls.exec(req, resp);
		try {
			bool = (Boolean)req.getAttribute("isRedirect");
			
		} catch (Exception e) {}
		
		if(bool == null) {
			// 이 경우는 비동기 통신이므로 반환되는 문자열을 응답문서로 만들어준다.
			PrintWriter pw = resp.getWriter();
			pw.print(view);
		} else if(bool) {
			//redirect 시켜야 하는 경우
			resp.sendRedirect(view);
		} else if(!bool) {
			//forward 시켜야 하는 경우
			String prefix = "/WEB-INF/views/";
			String surffix = ".jsp";
			RequestDispatcher rd = req.getRequestDispatcher(prefix+view+surffix);
			rd.forward(req, resp);
		}
		
	}
}


























