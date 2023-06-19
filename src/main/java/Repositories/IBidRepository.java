package Repositories;

import BusinessLayer.Bid;

import java.util.List;

public interface IBidRepository {

    void updateBid(Bid bid);

    void saveBid(Bid bid);

    void deleteBid(Bid bid);

    List<Bid> getAllBids();

    void addAllBids(List<Bid> bidList);

    void clear();
}
