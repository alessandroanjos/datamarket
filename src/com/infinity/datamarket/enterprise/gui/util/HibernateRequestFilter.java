package com.infinity.datamarket.enterprise.gui.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;


public class HibernateRequestFilter implements Filter {

	 public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {  
		 RepositoryManagerHibernateUtil.getInstancia().currentSession();
		 chain.doFilter(req, resp);	
		 RepositoryManagerHibernateUtil.getInstancia().closeSession();
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}  
	
}
