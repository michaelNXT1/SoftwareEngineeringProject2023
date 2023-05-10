package ServiceLayer.DTOs;

import BusinessLayer.Product;
import BusinessLayer.Purchase;
import BusinessLayer.PurchaseProduct;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseDTO {

    private List<PurchaseProductDTO> productDTOList;

    public PurchaseDTO(Purchase p) {
        productDTOList = p.getProductList().stream().map(PurchaseProductDTO::new).collect(Collectors.toList());
    }

    public List<PurchaseProductDTO> getProductDTOList() {
        return productDTOList;
    }

    public void addProduct(PurchaseProductDTO p) {
        this.productDTOList.add(p);
    }

    public PurchaseDTO() {
        this.productDTOList = new LinkedList<>();
    }

    public double getTotalPrice() {
        return productDTOList.stream().mapToDouble(pp->pp.getPrice()*pp.getQuantity()).sum();
    }

}
