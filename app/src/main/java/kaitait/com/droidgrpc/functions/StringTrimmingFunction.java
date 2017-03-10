package kaitait.com.droidgrpc.functions;

import io.reactivex.functions.Function;

/**
 * @author Alex Royds
 */

public class StringTrimmingFunction implements Function<String, String>
{
    @Override
    public String apply(String string) throws Exception
    {
        return string.trim();
    }
}
