package institute.dsti.shahid.salim.devops.invent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import institute.dsti.shahid.salim.devops.invent.domain.Order;
import institute.dsti.shahid.salim.devops.invent.domain.OrderItem;
import institute.dsti.shahid.salim.devops.invent.exception.InValidOrderException;
import institute.dsti.shahid.salim.devops.invent.exception.OrderNotFoundException;
import institute.dsti.shahid.salim.devops.invent.repository.OrderRepository;

@Component
public class OrderService {
	
	public OrderService() {
		
	}

	@Autowired
	private OrderRepository orderRepository;
	
	public Order createOrder(Order order) throws InValidOrderException {
		checkOrderValidity(order);
		orderRepository.save(order);
		return order;		
	}
	
	public void updateOrder(Order order,String id) throws OrderNotFoundException, InValidOrderException {
		order(id);//check if order exits with the given Id
		checkOrderValidity(order);
		orderRepository.save(order);
	} 
	
	public List<Order> listOrder() {
		return orderRepository.findAll();		
	}
	
	public Order order(String id) throws OrderNotFoundException {
		Order order= orderRepository.findOne(id);	
		if(order==null ){
			throw new OrderNotFoundException();
		}
		return order;
	}
	
	public void deleteOrder(String id) throws OrderNotFoundException {
		order(id);//check if order exits with the given Id
		orderRepository.delete(id);
	}
	
	private void checkOrderValidity(Order order) throws InValidOrderException {
		boolean ordervalid=true;
		if(order.getUserId()==null) {
			ordervalid = false;
		}else if(order.getOrderItemList().isEmpty()) {
			ordervalid = false;
		}
		
		for(OrderItem orderItem:order.getOrderItemList()) {
			if(orderItem.getItemId()==null) {
				ordervalid = false;
				break;
			}else if (orderItem.getQuantity()<1){
				ordervalid = false;
				break;
			}
		}

		if(!ordervalid) {
			throw new InValidOrderException();
		}
	}
}
