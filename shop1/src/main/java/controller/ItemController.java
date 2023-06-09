package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ModelAndViewMethodReturnValueHandler;

import logic.Item;
import logic.ShopService;
//POJO 방식 : 순수 자바소스. 다른 클래스(인터페이스)와 연관이 없음.
@Controller	//@Component + Controller 기능
@RequestMapping("item") //http://localhost:8080/shop1/item/*
public class ItemController {
	@Autowired	// ShopService 객체 주입.
	private ShopService service;
	//http://localhost:8080/shop1/item/list
	@RequestMapping("list")
	public ModelAndView list() {  //get, post 방식에 상관없이 호출
		//ModelAndView : Model + view
		//				 view에 전송할 데이터 + view 설정
		// view 설정이 안 된 경우 : url과 동일. item/list 뷰로 설정
		ModelAndView mav = new ModelAndView();
		//itemList : item 테이블의 모든 정보를 Item List로 저장
		List<Item> itemList = service.itemList();
		mav.addObject("itemList",itemList); //데이터 저장 view : item/list
		return mav;
	}
	//http://localhost:8080/shop1/item/datail?id=1
	@GetMapping({"detail","update","delete"})
	public ModelAndView detail(Integer id) {
		//id = id 파라미터의 값.
		//매개변수 이름과 같은 이름의 파라미터값을 자동으로 저장함.
		ModelAndView mav = new ModelAndView();
		Item item = service.getItem(id);
		mav.addObject("item",item);
		return mav;
	}
	@GetMapping("create") //GET 방식 요청, 화면출력
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView();
		mav.addObject(new Item());
		return mav;
	}
	@PostMapping("create") //POST 방식 요청, 파일업로드 + db에 테이터 저장
	public ModelAndView register(@Valid Item item, BindingResult bresult,
			HttpServletRequest request) {
		// request : 요청객체 주입.
		//item의 프로퍼티와 파라미터값을 비교하여 같은 이름의 값을 item 객체에 저장
		//@Valid : item 객체에 입력된 내용을 유효성 검사함. => bresult 검사 결과 저장
		ModelAndView mav = new ModelAndView("item/create"); //view 이름 설정
		if(bresult.hasErrors()) { //@Valid 프로에 의해서 입력 데이터에 오류가 있는 경우 
			mav.getModel().putAll(bresult.getModel());
			return mav; // item객체 + 에러메세지
		}
		service.itemCreate(item, request); //상품 db등록, 이미지 업로드 
		mav.setViewName("redirect:list");
		return mav;
	}
	/*
	 * @RequestMapping("update") public ModelAndView update(Integer id) { //id=파라미터
	 * 값 ModelAndView mav = new ModelAndView(); Item item = service.getItem(id);
	 * mav.addObject("item",item); return mav; }
	 */
	
	/*
	 * 1. 입력값 유효성 검증
	 * 2. db의 내용 수정. 파일 업로드
	 * 3. update 완료 후 list 요청 후 화면출력 
	 */
	@PostMapping("update")
	public ModelAndView update(@Valid Item item, BindingResult bresult,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("item/update");
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		service.itemUpdate(item,request);
		mav.setViewName("redirect:list");
		return mav;
	}
	@PostMapping("delete")
	public String delete(Integer id) {
		service.itemDelete(id);
		return "redirect:list";
	}
}
