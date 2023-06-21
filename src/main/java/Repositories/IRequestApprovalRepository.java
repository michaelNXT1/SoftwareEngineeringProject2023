package Repositories;

import BusinessLayer.RequestApproval;

import java.util.List;

public interface IRequestApprovalRepository {

    void updateRequestApproval(RequestApproval offerApproval);

    void saveRequestApproval(RequestApproval offerApproval);

    void deleteRequestApproval(RequestApproval offerApproval);

    List<RequestApproval> getAllRequestApprovals();

    void addAllRequestApprovals(List<RequestApproval> offerApprovalList);

    void clear();
}
