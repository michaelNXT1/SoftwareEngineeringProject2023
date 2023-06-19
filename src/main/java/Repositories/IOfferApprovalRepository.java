package Repositories;

import BusinessLayer.OfferApproval;

import java.util.List;

public interface IOfferApprovalRepository {

    void updateOfferApproval(OfferApproval offerApproval);

    void saveOfferApproval(OfferApproval offerApproval);

    void deleteOfferApproval(OfferApproval offerApproval);

    List<OfferApproval> getAllOfferApprovals();

    void addAllOfferApprovals(List<OfferApproval> offerApprovalList);

    void clear();

}