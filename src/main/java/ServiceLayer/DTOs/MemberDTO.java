package ServiceLayer.DTOs;

import BusinessLayer.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MemberDTO {
    private final String username;
    private List<PositionDTO> positions;
    private final ShoppingCartDTO shoppingCart;
    private final List<ProductDTO> searchResults;
    private final List<PurchaseDTO> purchaseHistory;

    public MemberDTO(Member member) {
            this.username = member.getUsername();
            this.positions = member.getPositions().stream().filter(p -> p.getPositionMember().getUsername().equals(this.username))
                .map(PositionDTO::new)
                .collect(Collectors.toList());
            this.shoppingCart = new ShoppingCartDTO(member.getShoppingCart());
            this.searchResults = member.getSearchResults().stream()
                    .map(ProductDTO::new)
                    .collect(Collectors.toList());
            this.purchaseHistory = member.getPurchaseHistory().getAllPurchases().stream()
                    .map(PurchaseDTO::new)
                    .collect(Collectors.toList());
    }
    public String getUsername() {
        return username;
    }

    public List<PositionDTO> getPositions() {
        return positions;
    }

    public ShoppingCartDTO getShoppingCart() {
        return shoppingCart;
    }

    public List<ProductDTO> getSearchResults() {
        return searchResults;
    }

    public List<PurchaseDTO> getPurchaseHistory() {
        return purchaseHistory;
    }
    @Override
    public String toString() {
        return "MemberDTO{" +
                "username='" + username + '\'' +
                ", positions=" + positions +
                ", shoppingCart=" + shoppingCart +
                ", searchResults=" + searchResults +
                ", purchaseHistory=" + purchaseHistory +
                '}';
    }
}
