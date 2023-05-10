package ServiceLayer.DTOs;

import BusinessLayer.Logger.SystemLogger;
import BusinessLayer.Member;
import BusinessLayer.Position;
import BusinessLayer.Store;

import java.util.stream.Collectors;

public class PositionDTO {
    private Store store;

    private Member assigner;

    public PositionDTO(Position p) {
        this.store = p.getStore();
        this.assigner = p.getAssigner();
    }
    public Store getStore() {
        return store;
    }

    public Member getAssigner() {
        return assigner;
    }
    @Override
    public String toString() {
        return "PositionDTO{" +
                "store=" + store.getStoreName() +
                ", assigner=" + assigner.getUsername() +
                '}';
    }


}
