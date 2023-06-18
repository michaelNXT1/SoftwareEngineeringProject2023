package BusinessLayer;

import jakarta.persistence.*;

@Entity
@Table(name = "offer_approval")
public class OfferApproval {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "offer_id")
    private int offerId;
    @ManyToOne
    @JoinColumn(name = "employee")
    private Member employee;
    @Column
    private int response;

    public OfferApproval(int offerId, Member employee, int response) {
        this.offerId = offerId;
        this.employee = employee;
        this.response = response;
    }

    public OfferApproval() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public void setEmployee(Member employee) {
        this.employee = employee;
    }

    public int getResponse() {
        return response;
    }

    public int getOfferId() {
        return offerId;
    }

    public Member getEmployee() {
        return employee;
    }

    public void setResponse(int response) {
        this.response = response;
    }
}
