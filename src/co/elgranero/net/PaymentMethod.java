package co.elgranero.net;

public class PaymentMethod {

    private Integer id;
    private String Name;

    public PaymentMethod() {
    }

    public PaymentMethod(Integer idPaymentMethod, String paymentMethodName) {
        this.id = idPaymentMethod;
        this.Name = paymentMethodName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idPaymentMethod) {
        this.id = idPaymentMethod;
    }

    public String getName() {
        return Name;
    }

    public void setName(String paymentMethodName) {
        this.Name = paymentMethodName;
    }

    @Override
    public String toString() {
        return Name;
    }
}