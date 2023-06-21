package BusinessLayer;

import jakarta.persistence.*;

@Entity
@Table(name = "request_approval")
public class RequestApproval {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "request_id")
    private int requestId;
    @ManyToOne
    @JoinColumn(name = "employee")
    private Member employee;
    @Column
    private int response;

    public RequestApproval(int requestId, Member employee, int response) {
        this.requestId = requestId;
        this.employee = employee;
        this.response = response;
    }

    public RequestApproval(){
    }

    public Long getId() {
        return id;
    }

    public int getRequestId() {
        return requestId;
    }

    public Member getEmployee() {
        return employee;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }
}
