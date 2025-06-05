package tool;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"*.action"})
@MultipartConfig(location="")
public class FrontController extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException 
	{
		PrintWriter out = resp.getWriter();
		
		try {
			String path = req.getServletPath().substring(1);
			String name = path.replace(".a", "A").replace('/','.');
			Action action = (Action)Class.forName(name).getDeclaredConstructor().newInstance();
			String url = action.execute(req, resp);
			
			 if (url.startsWith("redirect:")) {
		            String redirectPath = url.substring("redirect:".length());
		            resp.sendRedirect(redirectPath);
		        } else {
		        	  req.getRequestDispatcher(url).forward(req, resp);
		        }
		}
		catch(Exception e) {
			e.printStackTrace(out);
		}
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		doPost(req, resp);
	}
	
	

}
