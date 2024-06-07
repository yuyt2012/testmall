package io.ecp.testmall.cart.service;

import io.ecp.testmall.cart.entity.Cart;
import io.ecp.testmall.cart.entity.CartProduct;
import io.ecp.testmall.cart.entity.CartProductDTO;
import io.ecp.testmall.cart.entity.CartProductListDTO;
import io.ecp.testmall.cart.repository.CartProductRepository;
import io.ecp.testmall.cart.repository.CartRepository;
import io.ecp.testmall.member.entity.Member;
import io.ecp.testmall.member.repository.MemberRepository;
import io.ecp.testmall.product.entity.Product;
import io.ecp.testmall.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = false)
    public Long addCart(CartProductDTO cartProductDTO) {
        Member member = memberRepository.findByEmail(cartProductDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        Product product = productRepository.findById(cartProductDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        Cart cart = cartRepository.findByMemberId(member.getId())
                .orElse(null);

        if (cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        CartProduct savedCartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .orElse(null);
        if (savedCartProduct != null) {
            savedCartProduct.addQuantity(cartProductDTO.getQuantity());
            return savedCartProduct.getId();
        } else {
            CartProduct cartProduct = CartProduct.createCartProduct(cart, product, cartProductDTO.getQuantity());
            cartProductRepository.save(cartProduct);
            return cartProduct.getId();
        }
    }

    @Transactional(readOnly = false)
    public Long subCart(CartProductDTO cartProductDTO, String memberEmail) {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        Product product = productRepository.findById(cartProductDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        Cart cart = cartRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원의 장바구니가 존재하지 않습니다."));

        CartProduct savedCartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 장바구니에 존재하지 않습니다."));
        savedCartProduct.subQuantity(cartProductDTO.getQuantity());
        return savedCartProduct.getId();
    }

    public Page<CartProductListDTO> getCartProducts(Pageable pageable, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        Cart cart = cartRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원의 장바구니가 존재하지 않습니다."));
        return cartProductRepository.searchCartProduct(pageable, cart.getId());
    }

    @Transactional(readOnly = false)
    public void deleteCartProduct(String productName, Long userId) {
        Cart cart = cartRepository.findByMemberId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원의 장바구니가 존재하지 않습니다."));
        Product product = productRepository.findByName(productName)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        cartProductRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .ifPresent(cartProductRepository::delete);
    }
}
