package BusinessLayer;

import DAOs.RequestApprovalDAO;
import Repositories.IRequestApprovalRepository;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name= "employee_requests")
public class EmployeeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;
    @ManyToOne
    @JoinColumn
    private Member requestingUser;
    @Column(name="store_id")
    private int storeId;
    @ManyToOne
    @JoinColumn(name="candidate")
    private Member candidate;
    @Column(name="type")
    private int type;
    @Transient
    private IRequestApprovalRepository requestApprovalRepository=new RequestApprovalDAO();

    public EmployeeRequest(int storeId, Member requestingUser, Member candidate, int type){
        this.storeId=storeId;
        this.requestingUser=requestingUser;
        this.candidate=candidate;
        this.type=type;
    }

    public EmployeeRequest(){
    }

    public void addRequestApproval(List<Member> approvingMembers){
        for (Member m : approvingMembers) {
            RequestApproval offerApproval = new RequestApproval(requestId, m, -1);
            requestApprovalRepository.saveRequestApproval(offerApproval);
        }
    }

    public int getRequestId() {
        return requestId;
    }

    public Member getRequestingUser() {
        return requestingUser;
    }

    public int getStoreId() {
        return storeId;
    }

    public Member getCandidate() {
        return candidate;
    }

    public int getType() {
        return type;
    }

    public IRequestApprovalRepository getRequestApprovalRepository() {
        return requestApprovalRepository;
    }
}
