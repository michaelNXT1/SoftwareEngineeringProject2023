package ServiceLayer.DTOs;

import BusinessLayer.Logger.SystemLogger;
import BusinessLayer.Member;
import BusinessLayer.Position;
import BusinessLayer.Store;

import java.util.stream.Collectors;

public class PositionDTO {
    private final StoreDTO store;
    private final MemberDTO assigner;
    private final String positionName;

    public PositionDTO(Position p) {
        this.store = new StoreDTO(p.getStore());
        this.assigner = p.getAssigner() == null ? null : new MemberDTO(p.getAssigner());
        this.positionName = p.getPositionName();
    }

    public StoreDTO getStore() {
        return store;
    }

    public MemberDTO getAssigner() {
        return assigner;
    }

    public String getPositionName() {
        return positionName;
    }
}
