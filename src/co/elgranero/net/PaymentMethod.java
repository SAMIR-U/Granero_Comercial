package co.elgranero.net;

public class PaymentMethod {
    private Integer idPaymentMethod;
    private String paymentMethodName;
    public PaymentMethod() {
    }
    public PaymentMethod(Integer idPaymentMethod, String paymentMethodName) {
        this.idPaymentMethod = idPaymentMethod;
        this.paymentMethodName = paymentMethodName;
    }
    public Integer getIdPaymentMethod() {
        return idPaymentMethod;
    }
    public void setIdPaymentMethod(Integer idPaymentMethod) {
        this.idPaymentMethod = idPaymentMethod;
    }
    public String getPaymentMethodName() {
        return paymentMethodName;
    }
    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }
    
}
