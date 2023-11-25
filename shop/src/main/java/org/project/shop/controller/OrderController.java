package org.project.shop.controller;

import com.querydsl.core.Tuple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.project.shop.config.ScriptUtils;
import org.project.shop.domain.*;
import org.project.shop.service.KakaoPayService;
import org.project.shop.service.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.project.shop.domain.QCartItem.cartItem;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class OrderController {
    private final OrderServiceImpl orderServiceImpl;
    private final MemberServiceImpl memberServiceImpl;
    private final ItemServiceImpl itemServiceImpl;
    private final CartServiceImpl cartServiceImpl;
    private final CartItemServiceImpl cartItemServiceImpl;
    private final OrderItemServiceImpl orderItemServiceImpl;
    private final KakaoPayService kakaoPayService;

    @GetMapping(value = "")
    public String createForm(Model model) {
        List<Member> members = memberServiceImpl.findAllMember();
        List<Item> items = itemServiceImpl.findItems();
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "order/orderForm";
    }

    @PostMapping(value = "")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId, @RequestParam("count") int count) {
        orderServiceImpl.order(memberId, itemId, count);
        return "redirect:/orders";
    }


    @GetMapping(value = "/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch
                                    orderSearch, Model model) {
        List<Order> orders = orderServiceImpl.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @PostMapping(value = "/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderServiceImpl.cancelOrder(orderId);
        return "redirect:/orders";
    }

    @GetMapping(value = "/orderList")
    public String orderListByMember(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = ((UserDetails) principal).getUsername();

        Member findMember = memberServiceImpl.findByUserId(username);
        List<Order> findAllOrder = orderServiceImpl.findByMemberIdAfterPayment(findMember.getId());
        if (findAllOrder.isEmpty()) {
            model.addAttribute("allOrder", findAllOrder);
        } else {
            List<List<Item>> paymentList = new ArrayList<>();
                for (Order order : findAllOrder) {
                List<OrderItem> allOrder = orderItemServiceImpl.findOrderItemByOrderId(order.getId());
                List<Item> findItems = OrderItem.findItems(allOrder);
                paymentList.add(findItems);
            }

            model.addAttribute("allOrder", findAllOrder);
        }


        return "order/orderList";
    }


    @GetMapping(value = "/payment")
    @Transactional
    public String orderPayment(Model model) throws IOException{
        /*
            view로 넘겨야 할 것들
            1. 구매자에 대한 정보(Member, Address)
            2. 구매 상품에 대한 정보(cartItem)
         */

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = ((UserDetails) principal).getUsername();

        Member findMember = memberServiceImpl.findByUserId(username);
        Cart findCart = cartServiceImpl.findByMemberId(findMember.getId());
        List<CartItem> findCartItems = cartItemServiceImpl.findByCartId(findCart.getId());
        System.out.println("findCartItems = " + findCartItems);

        if (findCartItems.isEmpty()) {
            return "redirect:/";
        } else {
            List<Integer> newSplitList = new ArrayList<>();
            List<CartItem> paymentCartItems = findCartItems;


            int deliveryFee = Math.round(((float) CartItem.getTotalCount(findCartItems) / 5)) * 3500;
            int totalPrice = CartItem.getTotalPrice(paymentCartItems);
            int totalCount = CartItem.getTotalCount(paymentCartItems);
            int paymentPrice = deliveryFee + totalPrice;

            System.out.println("findMember.toString() = " + findMember.toString());
            System.out.println("paymentCartItems = " + paymentCartItems);
            
            model.addAttribute("index", "te,st");
            model.addAttribute("member", findMember);
            model.addAttribute("cartItems", paymentCartItems);
            model.addAttribute("deliveryFee", deliveryFee);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("paymentPrice", paymentPrice);
            model.addAttribute("totalPrice", totalPrice);
            return "order/payment";

        }



    }
}