package BusinessLayer;

import jakarta.persistence.*;

@Entity
@Table(name = "offer_approval")
public class OfferApproval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "offer_id")
    private int offerId;
    @ManyToOne
    @JoinColumn(name = "employee")
    private Member employee;
    @Column
    private boolean response;

    public OfferApproval(int offerId, Member employee, boolean response) {
        this.offerId = offerId;
        this.employee = employee;
        this.response = response;
    }

    public OfferApproval() {

    }

    public int getOfferId() {
        return offerId;
    }

    public Member getEmployee() {
        return employee;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }
}
