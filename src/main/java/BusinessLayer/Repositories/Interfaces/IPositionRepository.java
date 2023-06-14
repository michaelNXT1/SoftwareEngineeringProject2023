package BusinessLayer.Repositories.Interfaces;

import BusinessLayer.Position;

import java.util.List;

public interface IPositionRepository {
    void addPosition(Position position);
    void removePosition(Position position);
    List<Position> getAllPositions();
    // Add other methods as needed
}