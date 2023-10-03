package org.project.shop.controller;

import lombok.RequiredArgsConstructor;
import org.project.shop.auth.PrincipalDetails;
import org.project.shop.domain.Cart;
import org.project.shop.domain.CartItem;
import org.project.shop.domain.Item;
<<<<<<< HEAD
import org.project.shop.repository.CartItemRepositoryImpl;
=======
>>>>>>> 5045eca287e3ad1d06c9c6b68101e6e126cf919a
import org.project.shop.service.CartServiceImpl;
import org.project.shop.service.ItemServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/cart")
public class CartController {
    private final CartServiceImpl cartServiceImpl;
    private final ItemServiceImpl itemServiceImpl;
    private final CartItemRepositoryImpl cartItemRepositoryImpl;

<<<<<<< HEAD

//    @GetMapping(value = "/add")
//    public String addCartGet(Model model){
//        return "/cart/add";
//    }

    @PostMapping(value = "/add")
    @ResponseBody
    public boolean addCartItem(@RequestParam Map<String, Object> params, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long itemId = Long.parseLong((String) params.get("itemId"));
        int quantity = Integer.parseInt((String) params.get("quantity"));
        String username = principalDetails.getUsername();

        Item findItem = itemServiceImpl.findOneItem(itemId);
        Cart cart = new Cart(findItem.getPrice());

        CartItem orderCartItem = new CartItem();
        orderCartItem.setCount(quantity);
        orderCartItem.setItem(findItem);
        orderCartItem.setCart(cart);

=======

//    @GetMapping(value = "/add")
//    public String addCartGet(Model model){
//        return "/cart/add";
//    }

    @PostMapping(value = "/add")
    @ResponseBody
    public boolean addCartItem(@RequestParam Map<String, Object> params, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long itemId = Long.parseLong((String) params.get("itemId"));
        int quantity = Integer.parseInt((String) params.get("quantity"));
        String username = principalDetails.getUsername();

        Item findItem = itemServiceImpl.findOneItem(itemId);
        Cart cart = new Cart(findItem.getPrice());

        CartItem orderCartItem = new CartItem();
        orderCartItem.setCount(quantity);
        orderCartItem.setItem(findItem);
        orderCartItem.setCart(cart);

>>>>>>> 5045eca287e3ad1d06c9c6b68101e6e126cf919a
        // 장바구니 버튼을 클릭하면 재고를 확인
        // 재고를 확인후 장바구니 엔티티에 추가
        // CartItem에
        System.out.println("method called");
<<<<<<< HEAD
        System.out.println("username = " + username);
=======
>>>>>>> 5045eca287e3ad1d06c9c6b68101e6e126cf919a
        if (itemServiceImpl.checkStockQuantity(itemId, quantity)) {
            itemServiceImpl.orderItem(itemId, quantity);
            Long resultId = cartServiceImpl.addCart(orderCartItem, username);
            return true;

<<<<<<< HEAD
        } else {
=======
        }else{
>>>>>>> 5045eca287e3ad1d06c9c6b68101e6e126cf919a
            return false;
        }
    }

    @GetMapping(value = "/list")
    public String cartList(Model model) {
        return "cart/cart_list";
    }
}
