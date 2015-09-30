package Peer;

/**
 * Created by atduarte on 23-03-2014.
 */
public class Injectable
{
    protected DependencyInjection di;

    public Injectable(DependencyInjection di)
    {
        this.di = di;
    }

    public DependencyInjection getDi() {
        return di;
    }

    public void setDi(DependencyInjection di) {
        this.di = di;
    }

}
