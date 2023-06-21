package ServiceLayer.DTOs;

import BusinessLayer.EmployeeRequest;
import BusinessLayer.RequestApproval;

import java.util.HashMap;
import java.util.Map;

public class EmployeeRequestDTO {
    public final int requestId;
    public final MemberDTO requestingUser;
    public final MemberDTO candidate;
    public final Map<MemberDTO, Integer> approvingMembers;

    public EmployeeRequestDTO(EmployeeRequest employeeRequest){
        this.requestId=employeeRequest.getRequestId();
        this.requestingUser=new MemberDTO(employeeRequest.getRequestingUser());
        this.candidate=new MemberDTO(employeeRequest.getCandidate());
        approvingMembers=new HashMap<>();
        for(RequestApproval ra: employeeRequest.getRequestApprovalRepository().getAllRequestApprovals().stream().filter(requestApproval -> requestApproval.getRequestId()== employeeRequest.getRequestId()).toList())
            approvingMembers.put(new MemberDTO(ra.getEmployee()), ra.getResponse());
    }

    public int getRequestId() {
        return requestId;
    }

    public MemberDTO getRequestingUser() {
        return requestingUser;
    }

    public MemberDTO getCandidate() {
        return candidate;
    }

    public Map<MemberDTO, Integer> getApprovingMembers() {
        return approvingMembers;
    }
}
