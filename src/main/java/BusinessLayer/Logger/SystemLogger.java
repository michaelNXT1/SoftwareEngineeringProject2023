package BusinessLayer.Logger;



import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;



public class SystemLogger {

    private static final Logger logger = LogManager.getLogger(SystemLogger.class);

    public void info(String msg){
        logger.info(msg);
    }

    public void error(String msg){
        logger.error(msg);
    }
}
