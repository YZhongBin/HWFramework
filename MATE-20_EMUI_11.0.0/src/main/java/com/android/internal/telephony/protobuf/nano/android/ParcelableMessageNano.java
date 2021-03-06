package com.android.internal.telephony.protobuf.nano.android;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.protobuf.nano.MessageNano;

public abstract class ParcelableMessageNano extends MessageNano implements Parcelable {
    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        ParcelableMessageNanoCreator.writeToParcel(getClass(), this, out);
    }
}
