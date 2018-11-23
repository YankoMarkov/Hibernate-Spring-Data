package softuni.gamestore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.gamestore.models.entities.Game;
import softuni.gamestore.models.entities.LoggedInUser;
import softuni.gamestore.models.entities.Order;
import softuni.gamestore.models.entities.User;
import softuni.gamestore.repositories.GameRepository;
import softuni.gamestore.repositories.LoggedInUserRepository;
import softuni.gamestore.repositories.OrderRepository;
import softuni.gamestore.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;
	private final LoggedInUserRepository loggedInUserRepository;
	private final GameRepository gameRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository, LoggedInUserRepository loggedInUserRepository, GameRepository gameRepository, UserRepository userRepository) {
		this.orderRepository = orderRepository;
		this.loggedInUserRepository = loggedInUserRepository;
		this.gameRepository = gameRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	@Transactional
	public String addProduct(String title) {
		StringBuilder result = new StringBuilder();
		Game game = this.gameRepository.findByTitle(title);
		
		List<LoggedInUser> loggedInUsers = this.loggedInUserRepository.findAll();
		Long userId = loggedInUsers.get(loggedInUsers.size() - 1).getUserId();
		User user = this.userRepository.findById(userId).orElse(null);
		
		List<Order> orders = this.orderRepository.findAll();
		
		if (orders.isEmpty()) {
			Order order = new Order();
			order.setBuyer(user);
			order.getProducts().add(game);
			result.append(String.format("%s added to cart", game.getTitle()));
			this.orderRepository.saveAndFlush(order);
		} else {
			for (Order order : orders) {
				if (order.getBuyer().equals(user)) {
					order.getProducts().add(game);
				} else {
					order.setBuyer(user);
					order.getProducts().add(game);
				}
			}
			result.append(String.format("%s added to cart", game.getTitle()));
		}
		return result.toString();
	}
	
	@Override
	public String removeProduct(String title) {
		StringBuilder result = new StringBuilder();
		Game game = this.gameRepository.findByTitle(title);
		
		List<LoggedInUser> loggedInUsers = this.loggedInUserRepository.findAll();
		Long userId = loggedInUsers.get(loggedInUsers.size() - 1).getUserId();
		User user = this.userRepository.findById(userId).orElse(null);
		
		List<Order> orders = this.orderRepository.findAll();
		
		if (orders.isEmpty()) {
			result.append("No orders");
			return result.toString();
		}
		for (Order order : orders) {
			if (order.getBuyer().equals(user)) {
				order.getProducts().remove(game);
				this.orderRepository.saveAndFlush(order);
			}
		}
		return String.format("%s removed from cart", game.getTitle());
	}
	
	@Override
	@Transactional
	public String addProductsToUser() {
		StringBuilder result = new StringBuilder();
		
		List<LoggedInUser> loggedInUsers = this.loggedInUserRepository.findAll();
		Long userId = loggedInUsers.get(loggedInUsers.size() - 1).getUserId();
		User user = this.userRepository.findById(userId).orElse(null);
		
		List<Order> orders = this.orderRepository.findAll();
		
		if (orders.isEmpty()) {
			result.append("No orders");
			return result.toString();
		}
		for (Order order : orders) {
			if (order.getBuyer().equals(user)) {
				for (Game game : order.getProducts()) {
					user.getOwnedGames().add(game);
				}
			}
		}
		this.userRepository.saveAndFlush(user);
		List<String> userGames = user.getOwnedGames().stream()
				.map(a -> String.format(" -%s", a.getTitle()))
				.collect(Collectors.toList());
		
		result.append("Successfully bought games:")
				.append("\n")
				.append(String.join(System.lineSeparator(), userGames));
		
		return result.toString();
	}
}
