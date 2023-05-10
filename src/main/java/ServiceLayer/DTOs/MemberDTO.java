package ServiceLayer.DTOs;

import BusinessLayer.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MemberDTO {
    private String username;
    private String email;
    private List<PositionDTO> positions = new LinkedList<>();
    private ShoppingCartDTO shoppingCart;
    private List<ProductDTO> searchResults;
    private List<PurchaseDTO> purchaseHistory;

    public MemberDTO(Member member) throws Exception {
        try {
            this.username = member.getUsername();
            this.email = member.getEmail();
            this.positions = member.getPositions().stream()
                    .map(PositionDTO::new)
                    .collect(Collectors.toList());
            this.shoppingCart = new ShoppingCartDTO(member.getShoppingCart());
            this.searchResults = member.getSearchResults().stream()
                    .map(ProductDTO::new)
                    .collect(Collectors.toList());
            this.purchaseHistory = member.getPpurchaseHistory().stream()
                    .map(PurchaseDTO::new)
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new Exception("Failed to create MemberDTO for member " + member.getUsername() + ": " + e.getMessage());
        }
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
