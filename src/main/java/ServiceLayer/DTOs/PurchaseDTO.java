package ServiceLayer.DTOs;

import BusinessLayer.Product;
import BusinessLayer.Purchase;
import BusinessLayer.PurchaseProduct;

import java.util.LinkedList;
import java.util.List;

public class PurchaseDTO {

    private List<PurchaseProductDTO> productDTOList;

    public PurchaseDTO(List<PurchaseProductDTO> productDTOList) {
        this.productDTOList = productDTOList;
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

    public static PurchaseDTO fromPurchaseToPurchaseDTO(Purchase p){
        List<PurchaseProduct> productsList = p.getProductList();
        List<PurchaseProductDTO> productDTOList = new LinkedList<>();
        for(PurchaseProduct product:productsList){
            PurchaseProductDTO purchaseProductDTO = PurchaseProductDTO.fromPurchaseToPurchaseDTO(product);
            productDTOList.add(purchaseProductDTO);
        }
        return new PurchaseDTO(productDTOList);
    }

}
