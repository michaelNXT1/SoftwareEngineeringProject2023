package BusinessLayer;

abstract public class Discount  {
    protected Product product;

    public Discount(Product product) {
        this.product = product;
    }
}
