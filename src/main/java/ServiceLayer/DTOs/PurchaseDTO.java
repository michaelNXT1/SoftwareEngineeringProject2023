package ServiceLayer.DTOs;

import BusinessLayer.Purchase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseDTO {

    private final int purchaseId;
    private final LocalDateTime purchaseDateTime;
    private final List<PurchaseProductDTO> productDTOList;

    public PurchaseDTO(Purchase p) {
        productDTOList = p.getProductList().stream().map(PurchaseProductDTO::new).collect(Collectors.toList());
        this.purchaseId=p.getPurchaseId();
        this.purchaseDateTime=p.getPurchaseDateTime();
    }

    public List<PurchaseProductDTO> getProductDTOList() {
        return productDTOList;
    }

    public void addProduct(PurchaseProductDTO p) {
        this.productDTOList.add(p);
    }

    public double getTotalPrice() {
        return productDTOList.stream().mapToDouble(pp->pp.getPrice()*pp.getQuantity()).sum();
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public LocalDateTime getPurchaseDateTime() {
        return purchaseDateTime;
    }
}
