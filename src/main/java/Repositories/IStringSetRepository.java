package Repositories;

import java.util.Set;

public interface IStringSetRepository {
    void addString(String string);
    void removeString(String string);
    Set<String> getAllStrings();
}
