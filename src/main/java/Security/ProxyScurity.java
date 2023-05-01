package Security;

public class ProxyScurity implements SecurityAdapter{

    private SecurityUtils realSec;

    public ProxyScurity(SecurityUtils realSec){
        this.realSec = realSec;
    }
    @Override
    public boolean authenticate(String username, String password) {
        if(realSec!=null){
            return this.realSec.authenticate(username, password);
        }
        return true;
    }
}
