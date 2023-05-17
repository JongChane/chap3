package annotation;

import org.springframework.stereotype.Component;


public interface ArticleDao {
	void insert(Article article);
	void updateReadCount(int id, int i);
}
