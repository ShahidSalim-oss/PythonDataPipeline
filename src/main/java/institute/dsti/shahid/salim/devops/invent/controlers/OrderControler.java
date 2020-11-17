package institute.dsti.shahid.salim.devops.invent.controlers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import institute.dsti.shahid.salim.devops.invent.domain.Order;
import institute.dsti.shahid.salim.devops.invent.domain.OrderItem;
import institute.dsti.shahid.salim.devops.invent.exception.InValidOrderException;
import institute.dsti.shahid.salim.devops.invent.exception.OrderNotFoundException;
import institute.dsti.shahid.salim.devops.invent.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "DSTI-Invent", description = "Here you can find all the available API endpoints for managing orders")
public class OrderControler {

	@Autowired
	private OrderService orderService;
	@Autowired EntityLinks entityLinks;


	/**
	 * This method is for testing only during development
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/orders-test")
	public Order createOrderGet() {
		Order order = new Order();
		order.setUserId(UUID.randomUUID().toString());
		for (int i = 0; i <= 5; i++) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemId("i-" + i);
			orderItem.setQuantity(i + 1);
			order.getOrderItemList().add(orderItem);
		}
		return createOrder(order).getBody();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/orders")
	public ResponseEntity<Order> createOrder(Order order) {
		Order neworder;
		try {
			neworder = orderService.createOrder(order);
		} catch (InValidOrderException e) {
			return (ResponseEntity<Order>) ResponseEntity.badRequest();
		}
		Link link = entityLinks.linkToSingleResource(Order.class, neworder.getId()).expand();
		return ResponseEntity.created(java.net.URI.create(link.getHref())).build();
	}

	@ApiOperation(value = "Get Order",tags = {"Order"})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Item created"),
	@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(method = RequestMethod.GET, value = "/orders/{id}")
	public ResponseEntity<Order> order(@PathVariable(value = "id") String id) {
		Order order;
		try {
			order = orderService.order(id);
		} catch (OrderNotFoundException e) {
			return (ResponseEntity<Order>) ResponseEntity.notFound();
		}
		return ResponseEntity.ok(order);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/orders")
	public ResponseEntity<List<Order>> orders() {
		List<Order> orderList = orderService.listOrder();
		return ResponseEntity.ok(orderList);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/orders/{id}")
	public ResponseEntity updateOrder(Order order,@PathVariable(value = "id") String id) {
		try {
			orderService.updateOrder(order,id);
		} catch (OrderNotFoundException | InValidOrderException e) {
			return (ResponseEntity<Order>) ResponseEntity.notFound();
		}
		return (ResponseEntity) ResponseEntity.accepted();
	}

}
