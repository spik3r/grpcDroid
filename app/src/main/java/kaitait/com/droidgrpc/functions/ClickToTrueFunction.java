package kaitait.com.droidgrpc.functions;

public class ClickToTrueFunction implements io.reactivex.functions.Function<Object, Boolean>
{
    @Override
    public Boolean apply(Object o) throws Exception
    {
        return true;
    }
}
