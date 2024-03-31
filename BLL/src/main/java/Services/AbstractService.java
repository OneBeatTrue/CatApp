package Services;

import Context.IDataContext;

public abstract class AbstractService {
    protected IDataContext context;
    protected AbstractService(IDataContext context) {
        if (context == null) {
            throw new NullPointerException("Null data context");
        }

        this.context = context;
    }
}
