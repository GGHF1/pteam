package com.store.pteam.controller;
import com.store.pteam.model.Cart;
import com.store.pteam.model.Game;
import com.store.pteam.model.Order;
import com.store.pteam.model.OrderItem;
import com.store.pteam.model.User;
import com.store.pteam.service.CartService;
import com.store.pteam.service.GameService;
import com.store.pteam.service.OrderItemService;
import com.store.pteam.service.OrderService;
import com.store.pteam.service.GameLibraryService;

import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;


@Controller
public class StoreController {

    private final GameService gameService;
    private final CartService cartService;
    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final GameLibraryService gameLibraryService;

    public StoreController(GameService gameService, CartService cartService, OrderItemService orderItemService, OrderService orderService, GameLibraryService gameLibraryService) {
        this.gameService = gameService;
        this.cartService = cartService;
        this.orderItemService = orderItemService;
        this.orderService = orderService;
        this.gameLibraryService = gameLibraryService;
    }

    @GetMapping(value = "/")
    public String index(Model model) {
        List<Game> games = gameService.getAllGames();
        model.addAttribute("games", games);
        return "index";
    }
    @GetMapping("/main")
    public String mainPage(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        List<Game> games = gameService.getAllGames();
        model.addAttribute("games", games);
        boolean hasPendingOrders = orderService.hasPendingOrders(loggedInUser);
        model.addAttribute("hasPendingOrders", hasPendingOrders);

        return "main";
    }

    @GetMapping("/store")
    public String getAllGames(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        List<Game> games = gameService.getAllGames();
        Map<Long, Boolean> purchasedMap = new HashMap<>();

        if (loggedInUser != null) {
            // If user is logged in, check if each game is in the user's library
            for (Game game : games) {
                boolean purchased = gameLibraryService.isGameInLibrary(loggedInUser, game);
                purchasedMap.put(game.getId(), purchased);
            }
        }

        model.addAttribute("games", games);
        model.addAttribute("purchasedMap", purchasedMap);
        model.addAttribute("isLoggedIn", loggedInUser != null);

        return "store"; 
    }

    @PostMapping("/addToCart/{gameId}")
public String addToCart(@PathVariable Long gameId, HttpSession session) {
    // Retrieve logged-in user from session
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    
    // If user is not logged in, redirect to login page
    if (loggedInUser == null) {
        return "redirect:/login";
    }

    // Check if the game is already in the cart or a pending order
    Game game = gameService.getGameById(gameId);
    if (game != null) {
        // Check if the game is already in the cart
        List<Cart> cartItems = cartService.getCartItems(loggedInUser);
        boolean isInCart = cartItems.stream()
                                .anyMatch(cart -> cart.getGame().getId().equals(gameId));

        // Check if the game is in any pending orders
        boolean isInPendingOrder = orderService.getPendingOrders(loggedInUser).stream()
                                        .flatMap(order -> order.getOrderItems().stream())
                                        .anyMatch(orderItem -> orderItem.getGame().getId().equals(gameId));

        if (isInCart || isInPendingOrder) {
            // Game is already in cart or pending order, redirect back to store
            return "redirect:/store?gameAdded=true";
        } else {
            // Proceed with adding the game to cart
            Cart cartItem = new Cart();
            cartItem.setUser(loggedInUser);
            cartItem.setGame(game);
            cartItem.setPrice(game.getPrice().setScale(2, RoundingMode.HALF_UP));

            // Add the cart item to the cart
            cartService.addToCart(cartItem);
        }
    }

    return "redirect:/store";
}


    @GetMapping("/cart/items")
    @ResponseBody
    public List<Cart> getCartItems(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Handle the case where the user is not logged in
            return Collections.emptyList();
        }

        return cartService.getCartItems(loggedInUser);
    }

    @PostMapping("/cart/remove/{cartItemId}")
    @ResponseBody
    public void removeFromCart(@PathVariable Long cartItemId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            cartService.removeFromCart(cartItemId);
        }
        
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect to login page if user is not logged in
        }

        // Retrieve the pending order for the logged-in user
        Order order = orderService.getPendingOrder(loggedInUser);
        if (order == null || order.getStatus().equalsIgnoreCase("success")) {
            // If there is no existing order or the existing order is already successful,
            // create a new order
            order = new Order();
            order.setUser(loggedInUser);
            order.setTotalPrice(BigDecimal.ZERO); // Initialize total price to zero
            orderService.createOrder(order);
        }

        // Retrieve the existing total price of the order
        BigDecimal totalPrice = order.getTotalPrice();

        // Retrieve the cart items for the logged-in user
        List<Cart> cartItems = cartService.getCartItems(loggedInUser);
        if (!cartItems.isEmpty()) {
            // Iterate through cart items and add them to the order
            for (Cart cartItem : cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setGame(cartItem.getGame());
                // You may need to set quantity or other attributes here

                // Save order item
                orderItemService.addToOrder(orderItem);

                // Update total price of the order
                totalPrice = totalPrice.add(cartItem.getPrice()); // Add the cart item price to total price
            }

            // Set the updated total price of the order
            order.setTotalPrice(totalPrice);

            // Clear the cart after successful checkout
            cartService.clearCart(loggedInUser);
        }
        else {
            return "error"; 
        }

        // Retrieve all order items associated with the pending order
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrder(order);

        // Add the created order and its details to the model
        model.addAttribute("order", order);
        model.addAttribute("cartItems", orderItems); // Pass all order items to the view

        // Redirect to the order details page
        return "order_details";
    }


    @PostMapping("/order/delete/{orderId}")
    public String deleteOrder(@PathVariable("orderId") Long orderId) {
        orderService.deleteOrder(orderId);
        return "redirect:/store";
    }

    @GetMapping("/checkout")
    public String viewCheckout(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect to login page if user is not logged in
        }

        // Retrieve the pending orders for the logged-in user
        List<Order> pendingOrders = orderService.getPendingOrders(loggedInUser);

        // Filter out orders with the "success" status
        pendingOrders = pendingOrders.stream()
                .filter(order -> !order.getStatus().equalsIgnoreCase("success"))
                .collect(Collectors.toList());

        if (!pendingOrders.isEmpty()) {
            // Assuming you want to display details for the first pending order
            Order order = pendingOrders.get(0);

            // Retrieve all order items associated with the pending order
            List<OrderItem> orderItems = orderItemService.getOrderItemsByOrder(order);
            model.addAttribute("order", order);
            model.addAttribute("cartItems", orderItems); // Pass all order items to the view
        }
        return "order_details";
    }
}
