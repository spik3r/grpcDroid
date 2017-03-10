package kaitait.com.droidgrpc.utils;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Bundle builder borrowed from the Conductor example app
 */
public class BundleBuilder
{
    private final Bundle bundle;
    
    public BundleBuilder(Bundle bundle)
    {
        this.bundle = bundle;
    }
    
    public BundleBuilder PutAll(Bundle bundle)
    {
        this.bundle.putAll(bundle);
        return this;
    }
    
    public BundleBuilder PutBoolean(String key, boolean value)
    {
        bundle.putBoolean(key, value);
        return this;
    }
    
    public BundleBuilder PutBooleanArray(String key, boolean[] value)
    {
        bundle.putBooleanArray(key, value);
        return this;
    }
    
    public BundleBuilder PutDouble(String key, double value)
    {
        bundle.putDouble(key, value);
        return this;
    }
    
    public BundleBuilder PutDoubleArray(String key, double[] value)
    {
        bundle.putDoubleArray(key, value);
        return this;
    }
    
    public BundleBuilder PutLong(String key, long value)
    {
        bundle.putLong(key, value);
        return this;
    }
    
    public BundleBuilder PutLongArray(String key, long[] value)
    {
        bundle.putLongArray(key, value);
        return this;
    }
    
    public BundleBuilder PutString(String key, String value)
    {
        bundle.putString(key, value);
        return this;
    }
    
    public BundleBuilder PutStringArray(String key, String[] value)
    {
        bundle.putStringArray(key, value);
        return this;
    }
    
    public BundleBuilder PutBundle(String key, Bundle value)
    {
        bundle.putBundle(key, value);
        return this;
    }
    
    public BundleBuilder PutByte(String key, byte value)
    {
        bundle.putByte(key, value);
        return this;
    }
    
    public BundleBuilder PutByteArray(String key, byte[] value)
    {
        bundle.putByteArray(key, value);
        return this;
    }
    
    public BundleBuilder PutChar(String key, char value)
    {
        bundle.putChar(key, value);
        return this;
    }
    
    public BundleBuilder PutCharArray(String key, char[] value)
    {
        bundle.putCharArray(key, value);
        return this;
    }
    
    public BundleBuilder PutCharSequence(String key, CharSequence value)
    {
        bundle.putCharSequence(key, value);
        return this;
    }
    
    public BundleBuilder PutCharSequenceArray(String key, CharSequence[] value)
    {
        bundle.putCharSequenceArray(key, value);
        return this;
    }
    
    public BundleBuilder PutCharSequenceArrayList(String key, ArrayList<CharSequence> value)
    {
        bundle.putCharSequenceArrayList(key, value);
        return this;
    }
    
    public BundleBuilder PutInt(String key, int value)
    {
        bundle.putInt(key, value);
        return this;
    }
    
    public BundleBuilder PutIntArray(String key, int[] value)
    {
        bundle.putIntArray(key, value);
        return this;
    }
    
    public BundleBuilder PutFloat(String key, float value)
    {
        bundle.putFloat(key, value);
        return this;
    }
    
    public BundleBuilder PutFloatArray(String key, float[] value)
    {
        bundle.putFloatArray(key, value);
        return this;
    }
    
    public BundleBuilder PutIntegerArrayList(String key, ArrayList<Integer> value)
    {
        bundle.putIntegerArrayList(key, value);
        return this;
    }
    
    public BundleBuilder PutParcelable(String key, Parcelable value)
    {
        bundle.putParcelable(key, value);
        return this;
    }
    
    public BundleBuilder PutParcelableArray(String key, Parcelable[] value)
    {
        bundle.putParcelableArray(key, value);
        return this;
    }
    
    public BundleBuilder PutParcelableArrayList(String key, ArrayList<? extends Parcelable> value)
    {
        bundle.putParcelableArrayList(key, value);
        return this;
    }
    
    public BundleBuilder PutSerializable(String key, Serializable value)
    {
        bundle.putSerializable(key, value);
        return this;
    }
    
    public BundleBuilder PutShort(String key, short value)
    {
        bundle.putShort(key, value);
        return this;
    }
    
    public BundleBuilder PutShortArray(String key, short[] value)
    {
        bundle.putShortArray(key, value);
        return this;
    }
    
    public BundleBuilder PutSparseParcelableArray(
            String key,
            SparseArray<? extends Parcelable> value)
    {
        bundle.putSparseParcelableArray(key, value);
        return this;
    }
    
    public BundleBuilder PutStringArrayList(String key, ArrayList<String> value)
    {
        bundle.putStringArrayList(key, value);
        return this;
    }
    
    public Bundle Build()
    {
        return bundle;
    }
    
}