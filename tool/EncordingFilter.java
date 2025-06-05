package tool;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = {"/*"})
public class EncordingFilter implements Filter {


	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
//		resp.setContentType("text/html; charset=UTF-8");
		System.out.println("フィルタの前処理");
		
		chain.doFilter(req, resp);
		
		System.out.println("フィルタの後処理");
		
	}
	
	public void init(FilterConfig filterConfing) {}
		
	public void destroy() {}
		

}
