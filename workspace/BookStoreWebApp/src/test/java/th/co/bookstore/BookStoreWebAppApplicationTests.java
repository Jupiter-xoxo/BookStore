package th.co.bookstore;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import th.co.bookstore.model.BookVo;
import th.co.bookstore.service.RestApiBookService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookStoreWebAppApplicationTests {

	@Autowired
	private RestApiBookService restApiBookService;
	
	@Test
	public void contextLoads() throws Exception {
//		try {
//
//			List<BookVo> vo = restApiBookService.callRestBookRecommendationApi();
//			System.out.println(vo.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
