package Repositories;

import BusinessLayer.Offer;

import java.util.List;

public interface IOfferRepository {

    void updateOffer(Offer offer);

    void saveOffer(Offer offer);

    void deleteOffer(Offer offer);

    List<Offer> getAllOffers();

    void addAllOffers(List<Offer> offerList);

    void clear();
}
