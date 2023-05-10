package application.views;

import BusinessLayer.Product;
import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.DTOs.ShoppingCartDTO;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "ShoppingCart",layout = MainLayout.class)
public class ShoppingCart extends VerticalLayout {
    private Header myBag;
    private Header total;
    private Grid<ProductDTO> productGrid;
    ShoppingCartDTO shoppingCart;
    List<ProductDTO> productDTOList;
    private MarketController marketController;


    @Autowired
    public ShoppingCart(){
        this.marketController = MarketController.getInstance();
        shoppingCart = marketController.getShoppingCart(MainLayout.getSessionId());
        productDTOList = shoppingCart.getProducts();
        productGrid = new Grid<>(ProductDTO.class, false);
        productGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        productGrid.addColumn(ProductDTO::getProductName).setHeader("Product Name")
                .setAutoWidth(true);
        productGrid.addColumn(ProductDTO::getProductId).setHeader("ProductId")
                .setAutoWidth(true);
        productGrid.addColumn(ProductDTO::getPrice).setHeader("Price")
                .setAutoWidth(true);
        productGrid.addColumn(ProductDTO::getCategory).setHeader("Category")
                .setAutoWidth(true);
        productGrid.addColumn(ProductDTO::getAmount).setHeader("Amount")
                .setAutoWidth(true);
        productGrid.addColumn(this::getStoreName).setHeader("Store Name");
        productGrid.addColumn(ProductDTO::getDescription).setHeader("Description")
                .setAutoWidth(true);
        productGrid.addColumn(
                new ComponentRenderer<>(Button::new, (button, product) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> this.removeProduct(product));
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                })).setHeader("Manage");

        productGrid.setItems(productDTOList);


    }
    private void removeProduct(ProductDTO product) {
        if (product == null)
            return;
        productDTOList.remove(product);
        marketController.removeProductFromCart(MainLayout.getSessionId(),product.getStoreId(),product.getProductId());
        this.refreshGrid();
    }
    private String getStoreName(ProductDTO p){
        return marketController.getStore(MainLayout.getSessionId(), p.getStoreId()).getStoreName();
    }
    private void refreshGrid() {
        if (productDTOList.size() > 0) {
            productGrid.setVisible(true);
            productGrid.getDataProvider().refreshAll();
        } else {
            productGrid.setVisible(false);
        }
    }

}
