package xml;

import org.springframework.stereotype.Component;


public interface ReadArticleService {
	Article getArticleAndReadCnt(int id) throws Exception;
}
