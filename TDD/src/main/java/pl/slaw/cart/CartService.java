package pl.slaw.cart;

import pl.slaw.order.OrderStatus;

public class CartService {

    private CartHandler cartHandler; //obiekt interfejsu

    public CartService(CartHandler cartHandler) {
        this.cartHandler = cartHandler;
    }

    //zwracanie koszyka - to bedzie testowane !!!
    Cart processCart(Cart cart) {
        if (cartHandler.canHandleCart(cart)) {
            cartHandler.sendToPrepare(cart);
            cart.getOrdersCart().forEach(order -> order.changeOrderStatus(OrderStatus.PREPARING));
            return cart;
        } else {
            cart.getOrdersCart().forEach(order -> order.changeOrderStatus(OrderStatus.REJECTED));
            return cart;
        }
    }
}
