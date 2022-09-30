package pl.slaw.cart;

public interface CartHandler {

    boolean canHandleCart(Cart cart);

    void sendToPrepare(Cart cart);

    default boolean isDeliveryFree(Cart cart){
        return cart.getOrdersCart().size() > 2;
    }
}
