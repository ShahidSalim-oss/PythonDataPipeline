package institute.dsti.shahid.salim.devops.invent.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import institute.dsti.shahid.salim.devops.invent.domain.Order;

public interface OrderRepository extends MongoRepository<Order, String> {


}