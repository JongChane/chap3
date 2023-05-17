package annotation;

import org.springframework.stereotype.Component;


public interface ReadArticleService {
	Article getArticleAndReadCnt(int id) throws Exception;
}
