package kr.or.ddit.service;

import kr.or.ddit.vo.ArticleVO;

public interface ArticleService {

	String getArticleNo();

	int createPost(ArticleVO articelVO);

	ArticleVO detail(String articleNo);

}
