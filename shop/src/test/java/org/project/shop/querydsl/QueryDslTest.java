package org.project.shop.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
<<<<<<< HEAD
import org.junit.jupiter.api.AfterEach;
=======
>>>>>>> 5045eca287e3ad1d06c9c6b68101e6e126cf919a
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.project.shop.domain.*;
<<<<<<< HEAD
import org.project.shop.repository.*;
=======
import org.project.shop.repository.CartItemRepository;
import org.project.shop.repository.CartItemRepositoryImpl;
import org.project.shop.repository.ItemRepositoryImpl;
import org.project.shop.repository.MemberRepositoryImpl;
>>>>>>> 5045eca287e3ad1d06c9c6b68101e6e126cf919a
import org.project.shop.service.ItemServiceImpl;
import org.project.shop.service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.project.shop.domain.QMember.member;

@SpringBootTest
@Transactional
public class QueryDslTest {
    @Autowired
    private MemberServiceImpl memberServiceImpl;
    @Autowired
    private MemberRepositoryImpl memberRepositoryImpl;

    @Autowired
    private ItemRepositoryImpl itemRepositoryImpl;

    @Autowired
    private ItemServiceImpl itemServiceImpl;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private CartItemRepositoryImpl cartItemRepositoryImpl;

<<<<<<< HEAD
    @Autowired
    private CartRepositoryImpl cartRepositoryImpl;


    public List<Item> createItem() {
        List<Item> items = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            String name = "name" + Integer.toString(i);
            int price = (int) (Math.random() * 30000) + 10000;
            int stockQuantity = (int) (Math.random() * 100);
            items.add(new Item(name, price, stockQuantity));
        }
        return items;
    }

    @DisplayName("특정 아이디를 가진 Member 조회")
    @Test
    public void queryTest() {
=======
    public List<Member> createMember(){
>>>>>>> 5045eca287e3ad1d06c9c6b68101e6e126cf919a
        List<Member> members = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            String id = "id" + Integer.toString(i);
            String pw = "pw" + Integer.toString(i);
            String name = "name" + Integer.toString(i);
            members.add(new Member(id, pw, name));
        }
<<<<<<< HEAD
=======
        return members;
    }

    public List<Item> createItem(){
        List<Item> items = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            String name = "name" + Integer.toString(i);
            int price = (int) (Math.random() * 30000) + 10000;
            int stockQuantity = (int) (Math.random() * 100);
            items.add(new Item(name, price, stockQuantity));
        }
        return items;
    }



    @DisplayName("특정 아이디를 가진 Member 조회")
    @Test
    public void queryTest(){
        List<Member> members = createMember();
>>>>>>> 5045eca287e3ad1d06c9c6b68101e6e126cf919a

        String findId = "id3";
        for (Member member : members) {
            memberRepositoryImpl.save(member);
        }

        List<Member> findAllMember = memberRepositoryImpl.findAllMember();
        assertThat(findAllMember.size()).isEqualTo(10);

        Member findMember = memberRepositoryImpl.findByUserId(findId);
        assertThat(findMember.getUserId()).isEqualTo("id3");
    }

    @DisplayName("모든 아이템 조회")
    @Test
    public void findAllItemTest() {
        List<Item> items = createItem();
        for (Item item : items) {
            itemRepositoryImpl.save(item);
        }
        List<Item> findAllItems = itemRepositoryImpl.findAllItem();

        assertThat(items.size()).isEqualTo(findAllItems.size());
    }


    @DisplayName("다중 조건 테스트")
    @Test
    public void findByCartIdAndItemIdTest() {
        Cart cart = new Cart();
        Item item = new Item();

        CartItem cartItem = CartItem.createCartItem(cart, item, 5);
        cartItemRepositoryImpl.save(cartItem);

        CartItem findCartItem = cartItemRepositoryImpl.findByCartIdAndItemId(cart.getId(), item.getId());
        assertThat(findCartItem.getCount()).isEqualTo(5);
    }


    @DisplayName("MemberID로 CartItem 컬럼 찾기")
    @Test
    public void findByMemberId() {
        Member member1 = new Member("id1", "pw1");
        member1.setName("id1");
        memberRepositoryImpl.save(member1);

        Member member2 = new Member("id2", "pw2");
        memberRepositoryImpl.save(member2);

        Cart cart1 = new Cart();
        cart1.setMember(member1);
        cartRepositoryImpl.save(cart1);

        Cart cart2 = new Cart();
        cart2.setMember(member2);
        cartRepositoryImpl.save(cart2);

        String name = "name" + Integer.toString(1);
        int price = (int) (Math.random() * 30000) + 10000;
        int stockQuantity = (int) (Math.random() * 100);

        Item item = new Item(name, price, stockQuantity);
        itemRepositoryImpl.save(item);

        CartItem cartItem1 = new CartItem(cart1, item, 5);
        cartItemRepositoryImpl.save(cartItem1);

        CartItem cartItem2 = new CartItem(cart2, item, 5);
        cartItemRepositoryImpl.save(cartItem2);

        // 전체 CartItem 조회 테스트
        List<CartItem> allCartItem = cartItemRepositoryImpl.findAllCartItem();
        assertThat(allCartItem.size()).isEqualTo(2);


        // 특정 CartItem 조회 테스트
        CartItem findCartItem = cartItemRepositoryImpl.findById(cartItem1.getId());
        assertThat(findCartItem.getId()).isEqualTo(cartItem1.getId());

        // 특정 Cart로 CartItem 조회 테스트

        
        // 특정 Member로 CartItem 조회 테스트
        Member findMember1 = memberRepositoryImpl.findByUserId("id1");
        List<Member> allMember = memberRepositoryImpl.findAllMember();


        Cart findCart = cartRepositoryImpl.findByMemberId(findMember1.getId());
        List<CartItem> findCartItems = cartItemRepositoryImpl.findByCartId(findCart.getId());
        System.out.println("findCart = " + findCart.toString());

        List<CartItem> findByMemberCartItem = cartItemRepositoryImpl.findByCartMemberId(findCart.getId());
        for (CartItem cartItem : findByMemberCartItem) {
            System.out.println("cartItem.toString() = " + cartItem.toString());
        }
        assertThat(findByMemberCartItem.size()).isEqualTo(1);

    }

    @DisplayName("모든 아이템 조회")
    @Test
    public void findAllItemTest() {
        List<Item> items = createItem();
        for (Item item : items) {
            itemRepositoryImpl.save(item);
        }
        List<Item> findAllItems = itemRepositoryImpl.findAllItem();

        assertThat(items.size()).isEqualTo(findAllItems.size());
    }




    @DisplayName("다중 조건 테스트")
    @Test
    public void findByCartIdAndItemIdTest(){
        Cart cart = new Cart();
        Item item = new Item();

        CartItem cartItem = CartItem.createCartItem(cart, item, 5);
        cartItemRepositoryImpl.save(cartItem);

        CartItem findCartItem = cartItemRepositoryImpl.findByCartIdAndItemId(cart.getId(), item.getId());
        assertThat(findCartItem.getCount()).isEqualTo(5);

    }
}
