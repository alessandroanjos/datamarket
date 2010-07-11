package com.infinity.datamarket.enterprise.gui.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;


public class HibernateRequestFilter implements Filter {

	 public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {  
//		 RepositoryManagerHibernateUtil.getInstancia().currentSession();
   	    try {
			 chain.doFilter(req, resp);	
		} catch (Exception e) {
			e.printStackTrace();

			RequestDispatcher rd =  filterConfig.getServletContext().
			getRequestDispatcher("/jsp/erro.jsp");
			req.setAttribute("exception", e);
			rd.forward(req, resp);
			
		}finally{
			 RepositoryManagerHibernateUtil.getInstancia().closeSession();	
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
	private FilterConfig filterConfig;
	private static final long serialVersionUID = 1L;
	
}
