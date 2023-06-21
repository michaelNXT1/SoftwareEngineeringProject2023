package Repositories;

import BusinessLayer.EmployeeRequest;

import java.util.List;

public interface IRequestRepository {

    void updateRequest(EmployeeRequest offer);

    void saveRequest(EmployeeRequest offer);

    void deleteRequest(EmployeeRequest offer);

    List<EmployeeRequest> getAllRequests();

    void addAllRequests(List<EmployeeRequest> offerList);

    void clear();
}
