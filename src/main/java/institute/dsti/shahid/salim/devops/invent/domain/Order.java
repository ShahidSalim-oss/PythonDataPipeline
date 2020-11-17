package institute.dsti.shahid.salim.devops.invent.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Order {
	@Id
	private String id;
	private String userId;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<OrderItem> orderItemList = new ArrayList();
}
