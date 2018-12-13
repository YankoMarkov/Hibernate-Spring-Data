package alararestaurant.service;

public interface ItemService {

    Boolean itemsAreImported();

    String readItemsJsonFile();

    String importItems(String items);
}
