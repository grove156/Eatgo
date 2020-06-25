package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RestaurantService {

    private static RestaurantRepository restaurantRepository;
    private MenuItemRepository menuItemRepository;
    private ReviewRepository reviewRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository,
                             ReviewRepository reviewRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.reviewRepository = reviewRepository;

    }
    public List<Restaurant> getRestaurants(){
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants;
    }
    public Restaurant getRestaurant(long id){
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(()->new RestaurantNotFoundException(id));

        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
        restaurant.setMenuItems(menuItems);
        List<Review> review = reviewRepository.findAllByRestaurantId(id);
        return restaurant;
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public static Restaurant updateRestaurant(long id, String name, String address) {
        //update restaurant...
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        restaurant.updateInformation(name, address);


        return restaurant;
    }
}
