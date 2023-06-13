package AccaptanceTests.bridge;

public abstract class Driver {
    public static Bridge getBridge() throws Exception {
        ProxyBridge bridge = new ProxyBridge();

        //Uncomment this line
        bridge.setRealBridge(new Real());

        return bridge;
    }
}
