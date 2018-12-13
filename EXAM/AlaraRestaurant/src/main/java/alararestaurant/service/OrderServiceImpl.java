package alararestaurant.service;

import alararestaurant.common.Constants;
import alararestaurant.domain.dtos.bindings.order.ItemImportDto;
import alararestaurant.domain.dtos.bindings.order.OrderImportDto;
import alararestaurant.domain.dtos.bindings.order.OrderRootDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Item;
import alararestaurant.domain.entities.Order;
import alararestaurant.domain.entities.OrderItem;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.repository.OrderItemRepository;
import alararestaurant.repository.OrderRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import alararestaurant.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
	private final String ORDER_IMPORT_FILE_PATH = "src/main/resources/files/orders.xml";
	private final OrderRepository orderRepository;
	private final EmployeeRepository employeeRepository;
	private final OrderItemRepository orderItemRepository;
	private final ItemRepository itemRepository;
	private final FileUtil fileUtil;
	private final ValidationUtil validationUtil;
	private final XmlParser xmlParser;
	private final ModelMapper modelMapper;
	
	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository, EmployeeRepository employeeRepository, OrderItemRepository orderItemRepository, ItemRepository itemRepository, FileUtil fileUtil, ValidationUtil validationUtil, XmlParser xmlParser, ModelMapper modelMapper) {
		this.orderRepository = orderRepository;
		this.employeeRepository = employeeRepository;
		this.orderItemRepository = orderItemRepository;
		this.itemRepository = itemRepository;
		this.fileUtil = fileUtil;
		this.validationUtil = validationUtil;
		this.xmlParser = xmlParser;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public Boolean ordersAreImported() {
		return this.orderRepository.count() > 0;
	}
	
	@Override
	public String readOrdersXmlFile() throws IOException {
		return this.fileUtil.readFile(this.ORDER_IMPORT_FILE_PATH);
	}
	
	@Override
	public String importOrders() throws JAXBException, FileNotFoundException {
		StringBuilder result = new StringBuilder();
		OrderRootDto orderRootDto = this.xmlParser.parseXml(OrderRootDto.class, this.ORDER_IMPORT_FILE_PATH);
		
		for (OrderImportDto orderImportDto : orderRootDto.getOrderImportDtos()) {
			Employee employee = this.employeeRepository.findByName(orderImportDto.getEmployee()).orElse(null);
			if (!this.validationUtil.isValid(orderImportDto) || employee == null) {
				result.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
				continue;
			}
			Order order = this.modelMapper.map(orderImportDto, Order.class);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			LocalDateTime dateTime = LocalDateTime.parse(orderImportDto.getDateTime(), formatter);
			order.setDateTime(dateTime);
			order.setEmployee(employee);
			Set<OrderItem> orderItems = new HashSet<>();
			boolean exist = true;
			for (ItemImportDto itemImportDto : orderImportDto.getItemRootDto().getItemImportDtos()) {
				Item item = this.itemRepository.findByName(itemImportDto.getName()).orElse(null);
				if (item == null) {
					exist = false;
					break;
				}
				this.orderRepository.saveAndFlush(order);
				OrderItem orderItem = new OrderItem();
				orderItem.setItem(item);
				orderItem.setOrder(order);
				orderItem.setQuantity(itemImportDto.getQuantity());
				this.orderItemRepository.saveAndFlush(orderItem);
				orderItems.add(orderItem);
			}
			if (!exist) {
				continue;
			}
			order.setOrderItems(orderItems);
			this.orderRepository.saveAndFlush(order);
			result.append(String.format("Order for %s on %s added",
					order.getCustomer(), orderImportDto.getDateTime())).append(System.lineSeparator());
		}
		return result.toString().trim();
	}
	
	@Override
	public String exportOrdersFinishedByTheBurgerFlippers() {
		StringBuilder result = new StringBuilder();
		List<Order> orders = this.orderRepository.findAll();
		
		System.out.println();
		
		return null;
	}
}
