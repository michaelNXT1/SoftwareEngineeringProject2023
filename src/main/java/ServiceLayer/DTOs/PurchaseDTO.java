package ServiceLayer.DTOs;

import BusinessLayer.Product;
import BusinessLayer.Purchase;
import BusinessLayer.PurchaseProduct;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseDTO {

    private final Long purchaseId;
    private final LocalDateTime purchaseDateTime;
    private final List<PurchaseProductDTO> productDTOList;
    private final String username;

    public PurchaseDTO(Purchase p) {
        productDTOList = p.getProductList().stream().map(PurchaseProductDTO::new).collect(Collectors.toList());
        this.purchaseId=p.getId();
        this.purchaseDateTime=p.getPurchaseDateTime();
        this.username=p.getUsername();
    }

    public List<PurchaseProductDTO> getProductDTOList() {
        return productDTOList;
    }

    public void addProduct(PurchaseProductDTO p) {
        this.productDTOList.add(p);
    }



    public double getTotalPrice() {
        return Math.round(productDTOList.stream().mapToDouble(pp->pp.getPrice()*pp.getQuantity()).sum()*100.0)/100.0;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public LocalDateTime getPurchaseDateTime() {
        return purchaseDateTime;
    }

    public String getUsername() {
        return username;
    }
}
