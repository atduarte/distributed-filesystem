package Controllers;

import Controllers.DependencyInjection;

public class InjectableThread extends Thread
{
    protected DependencyInjection di;

    public InjectableThread(DependencyInjection di)
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
