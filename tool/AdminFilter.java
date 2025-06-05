package tool;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/admin/*","/adminAccount/*"})
public class AdminFilter implements Filter {
	
	public void init(FilterConfig filterConfig) throws ServletException {
        // 初期化処理（必要なら）
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        
        // ログインページのURLを除外する
        String path = httpRequest.getRequestURI();
        if (path.endsWith("/adminLogin.jsp")) {
            chain.doFilter(request, response);
            return;
        }else if (path.endsWith("/adminLoginError.jsp")) 
        {
             chain.doFilter(request, response);
             return;
        }else if (path.endsWith("/adminLogountError.jsp")) 
        {
            chain.doFilter(request, response);
            return;
        }else if(path.endsWith("/AdminLoginAction")) {
        	chain.doFilter(request, response);
        	 return;
        }else if(path.endsWith("/AdminLogin.action")) {
        	chain.doFilter(request, response);
        	 return;
        }
        
        
        
        // セッションから管理者アカウント情報を取得
        HttpSession session = httpRequest.getSession();
        if (session == null || session.getAttribute("adminaccount") == null) {
            // ログインしていない場合は管理者ログインページへリダイレクト
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/adminLogin.jsp");
        } else {
            // ログインしている場合はリクエストをそのまま次のフィルターに渡す
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        // フィルター終了処理（必要なら）
    }
}
