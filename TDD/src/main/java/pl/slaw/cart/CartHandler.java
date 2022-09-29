package pl.slaw.cart;

public interface CartHandler {

    boolean canHandleCart(Cart cart);

    void sendToPrepare(Cart cart);
}
