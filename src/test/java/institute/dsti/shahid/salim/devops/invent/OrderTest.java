package institute.dsti.shahid.salim.devops.invent;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import institute.dsti.shahid.salim.devops.invent.domain.Order;
import institute.dsti.shahid.salim.devops.invent.exception.InValidOrderException;
import institute.dsti.shahid.salim.devops.invent.service.OrderService;

@RunWith(SpringRunner.class)
public class OrderTest {

	@Test
	public void shouldRetrieveAnOrder() {
		OrderService orderService = new OrderService();
		Order order = new Order();
		order.setUserId("myuserid");
		try {
			orderService.createOrder(order);
			fail("Exception was not thrown");
		} catch (Exception e) {
			assertThat(e.getClass().getName(), is(InValidOrderException.class.getName()));
		}
	}
}
