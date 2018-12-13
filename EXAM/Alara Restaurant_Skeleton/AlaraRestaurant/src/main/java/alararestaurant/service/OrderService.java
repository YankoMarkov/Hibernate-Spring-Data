package alararestaurant.service;

public interface OrderService {

    Boolean ordersAreImported();

    String readOrdersXmlFile();

    String importOrders();

    String exportOrdersFinishedByTheBurgerFlippers();
}
