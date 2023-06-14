package Repositories;

import BusinessLayer.Position;

import java.util.List;

public interface IPositionRepository {
    void clear();
    void addPosition(Position position);
    void removePosition(Position position);
    List<Position> getAllPositions();

    List<Position> getPositionsByMember(String username);
    // Add other methods as needed
}