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

@WebFilter(urlPatterns = {"/user/*","/userAccount/*"})
public class UserFilter implements Filter {
	
	public void init(FilterConfig filterConfig) throws ServletException {
        // 初期化処理（必要なら）
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        
        String path = httpRequest.getRequestURI();
        
        // ログインページのURLを除外する
        if (path.endsWith("/login.jsp")) {
            chain.doFilter(request, response);
            return;
        }
        // ログインページエラーのURLを除外する
        else if (path.endsWith("/loginError.jsp")) {
            chain.doFilter(request, response);
            return;
        }
        //ログインのアクションを除外
        else if(path.endsWith("/UserLoginAction")) {
        	chain.doFilter(request, response);
        	  return;
        }
        //ログインのアクションを除外
        else if(path.endsWith("/UserLogin.action")) {
        	chain.doFilter(request, response);
        	  return;
        }
        
        // トップページのURLを除外する
        if(path.endsWith("/top.jsp")) {
            chain.doFilter(request, response);
            return;
        }
        //イベント検索ページのURLを除外する
        if(path.endsWith("/search.jsp")) {
            chain.doFilter(request, response);
            return;
        }
        // イベント詳細ページのURLを除外する
        if(path.endsWith("/EventDeteail.jsp")) {
            chain.doFilter(request, response);
            return;
        }
        // 口コミ投稿一覧ページのURLを除外する
        if(path.endsWith("/Reviews.jsp")) {
            chain.doFilter(request, response);
            return;
        }
        // 会員登録ページのURLを除外する
        if(path.endsWith("/register.jsp")) {
            chain.doFilter(request, response);
            return;
        }

        // 会員登録確認ページのURLを除外する
        if(path.endsWith("/registerConfirm.jsp")) {
            chain.doFilter(request, response);
            return;
        }
        // 会員登録確認ページのURLを除外する
        if(path.endsWith("/registerError.jsp")) {
            chain.doFilter(request, response);
            return;
        }
        if(path.endsWith("/deleteAccountSuccess.jsp")) {
            chain.doFilter(request, response);
            return;
        }
        
        //会員登録アクション除外
        if(path.endsWith("/UserAccountInsertAction")) {
        	chain.doFilter(request, response);
        	 return;
        }
        else if(path.endsWith("/UserAccountInsert.action")) {
            	chain.doFilter(request, response);
            	 return;
        }
        
        if(path.endsWith("/UserAccountValidate.action")) {
        	chain.doFilter(request,response);
        	 return;
        }else if(path.endsWith("/UserAccountValidateAction")) {
        	chain.doFilter(request,response);
        	 return;
        }
        
        // セッションから管理者アカウント情報を取得
        HttpSession session = httpRequest.getSession();
        if (session == null || session.getAttribute("useraccount") == null) {
            // ログインしていない場合は管理者ログインページへリダイレクト
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/user/top.jsp");
        } else {
            // ログインしている場合はリクエストをそのまま次のフィルターに渡す
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        // フィルター終了処理（必要なら）
    }
}
