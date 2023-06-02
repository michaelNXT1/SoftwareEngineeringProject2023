package ServiceLayer.DTOs;

import BusinessLayer.Member;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MemberDTO {
    private final String username;
    private final String email;
    private List<PositionDTO> positions = new LinkedList<>();
    private final ShoppingCartDTO shoppingCart;
    private final List<ProductDTO> searchResults;
    private final List<PurchaseDTO> purchaseHistory;

    public MemberDTO(Member member) {
            this.username = member.getUsername();
            this.email = member.getEmail();
            this.positions = member.getPositions().getAllPositions().stream().map(PositionDTO::new).collect(Collectors.toList());
            this.shoppingCart = new ShoppingCartDTO(member.getShoppingCart());
            this.searchResults = member.getSearchResults().getAllProducts().stream()
                    .map(ProductDTO::new)
                    .collect(Collectors.toList());
            this.purchaseHistory = member.getPurchaseHistory().getAllPurchases().stream()
                    .map(PurchaseDTO::new)
                    .collect(Collectors.toList());
    }
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
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
                ", email='" + email + '\'' +
                ", positions=" + positions +
                ", shoppingCart=" + shoppingCart +
                ", searchResults=" + searchResults +
                ", purchaseHistory=" + purchaseHistory +
                '}';
    }
}
