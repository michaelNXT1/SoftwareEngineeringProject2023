package BusinessLayer.Repositories;

import BusinessLayer.Position;
import BusinessLayer.Repositories.Interfaces.IPositionRepository;

import java.util.LinkedList;
import java.util.List;

public class PositionRepository implements IPositionRepository {
    private final List<Position> positions;

    public PositionRepository() {
        this.positions = new LinkedList<>();
    }

    @Override
    public void addPosition(Position position) {
        positions.add(position);
    }

    @Override
    public void removePosition(Position position) {
        positions.remove(position);
    }

    @Override
    public List<Position> getAllPositions() {
        return positions;
    }
    // Implement other methods as needed
}

