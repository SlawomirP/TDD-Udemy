package pl.slaw.account;

public class Account {

    private boolean active;
    private Address defaultDeliveryAddress;
    private String email;

    //dodajemy nowy konstruktor z jednym parametrem i dodajemy tam warunek
    //jezeli adres rozny od null to odrazu ma byc active
    public Account(Address defaultDeliveryAddress) {
        this.defaultDeliveryAddress = defaultDeliveryAddress;
        if (defaultDeliveryAddress != null) {
            activate();
        } else {
            this.active = false;
        }
    }

    public Account() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    public boolean isActive() {
        return this.active;
    }

    public Address getDefaultDeliveryAddress() {
        return defaultDeliveryAddress;
    }

    public void setDefaultDeliveryAddress(Address defaultDeliveryAddress) {
        this.defaultDeliveryAddress = defaultDeliveryAddress;
    }

    public void setEmail(String email) {
        if (email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        ) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Wrong email format");
        }
    }

    public String getEmail() {
        return this.email;
    }
}
