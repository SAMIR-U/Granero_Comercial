package co.elgranero.net;

public class ProductPresentation {
    private Integer idPresentation;
    private Integer idProduct;
    private Double presentationPrice;
    private String presentationName;
    public ProductPresentation() {
    }
    public ProductPresentation(Integer idPresentation, Integer idProduct, Double presentationPrice,
            String presentationName) {
        this.idPresentation = idPresentation;
        this.idProduct = idProduct;
        this.presentationPrice = presentationPrice;
        this.presentationName = presentationName;
    }
    public Integer getIdPresentation() {
        return idPresentation;
    }
    public void setIdPresentation(Integer idPresentation) {
        this.idPresentation = idPresentation;
    }
    public Integer getIdProduct() {
        return idProduct;
    }
    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }
    public Double getPresentationPrice() {
        return presentationPrice;
    }
    public void setPresentationPrice(Double presentationPrice) {
        this.presentationPrice = presentationPrice;
    }
    public String getPresentationName() {
        return presentationName;
    }
    public void setPresentationName(String presentationName) {
        this.presentationName = presentationName;
    }
    
}
