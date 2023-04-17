package bridge;

public class ProxyBridge implements Bridge{
    private Bridge real = null;

    public void setRealBridge(Bridge implementation) {
        if (real == null)
            real = implementation;
    }
}
