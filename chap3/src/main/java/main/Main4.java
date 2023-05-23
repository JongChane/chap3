package main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import annotation.Article;
import annotation.MemberService;
import annotation.ReadArticleService;
import config.Appctx;
import xml.Member;
import xml.UpdateInfo;

public class Main4 {
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Appctx.class);
		ReadArticleService service =
				ctx.getBean("readArticleService",ReadArticleService.class);
		try {
			Article a1 = service.getArticleAndReadCnt(1);
			Article a2 = service.getArticleAndReadCnt(1);
			System.out.println("[main] a1==a2 : " + (a1 == a2));
			service.getArticleAndReadCnt(0);
		} catch(Exception e) {
			System.out.println("[main]" + e.getMessage());
		}
		System.out.println("\n UpdateMemberInfoTraceAspect 연습");
		MemberService ms = ctx.getBean("memberService",MemberService.class);
		ms.regist(new Member());
		ms.update(new Member(),"hong", new UpdateInfo());
		ms.delete("hong2", "test",new UpdateInfo());
	}
}
