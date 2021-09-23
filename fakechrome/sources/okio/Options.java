package okio;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

public final class Options extends AbstractList<ByteString> implements RandomAccess {
    final ByteString[] byteStrings;
    final int[] trie;

    private Options(ByteString[] byteStringArr, int[] iArr) {
        this.byteStrings = byteStringArr;
        this.trie = iArr;
    }

    public static Options of(ByteString... byteStringArr) {
        if (byteStringArr.length == 0) {
            return new Options(new ByteString[0], new int[]{0, -1});
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(byteStringArr));
        Collections.sort(arrayList);
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList2.add(-1);
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            arrayList2.set(Collections.binarySearch(arrayList, byteStringArr[i2]), Integer.valueOf(i2));
        }
        if (((ByteString) arrayList.get(0)).size() == 0) {
            throw new IllegalArgumentException("the empty byte string is not a supported option");
        }
        int i3 = 0;
        while (i3 < arrayList.size()) {
            ByteString byteString = (ByteString) arrayList.get(i3);
            int i4 = i3 + 1;
            int i5 = i4;
            while (i5 < arrayList.size()) {
                ByteString byteString2 = (ByteString) arrayList.get(i5);
                if (!byteString2.startsWith(byteString)) {
                    continue;
                    break;
                } else if (byteString2.size() == byteString.size()) {
                    throw new IllegalArgumentException("duplicate option: " + byteString2);
                } else if (((Integer) arrayList2.get(i5)).intValue() > ((Integer) arrayList2.get(i3)).intValue()) {
                    arrayList.remove(i5);
                    arrayList2.remove(i5);
                } else {
                    i5++;
                }
            }
            i3 = i4;
        }
        Buffer buffer = new Buffer();
        buildTrieRecursive(0, buffer, 0, arrayList, 0, arrayList.size(), arrayList2);
        int[] iArr = new int[intCount(buffer)];
        for (int i6 = 0; i6 < iArr.length; i6++) {
            iArr[i6] = buffer.readInt();
        }
        if (buffer.exhausted()) {
            return new Options((ByteString[]) byteStringArr.clone(), iArr);
        }
        throw new AssertionError();
    }

    private static void buildTrieRecursive(long j, Buffer buffer, int i, List<ByteString> list, int i2, int i3, List<Integer> list2) {
        int i4;
        int i5;
        int i6 = i2;
        if (i6 >= i3) {
            throw new AssertionError();
        }
        for (int i7 = i6; i7 < i3; i7++) {
            if (list.get(i7).size() < i) {
                throw new AssertionError();
            }
        }
        ByteString byteString = list.get(i2);
        ByteString byteString2 = list.get(i3 - 1);
        int i8 = -1;
        if (i == byteString.size()) {
            i8 = list2.get(i6).intValue();
            i6++;
            byteString = list.get(i6);
        }
        if (byteString.getByte(i) != byteString2.getByte(i)) {
            int i9 = 1;
            for (int i10 = i6 + 1; i10 < i3; i10++) {
                if (list.get(i10 - 1).getByte(i) != list.get(i10).getByte(i)) {
                    i9++;
                }
            }
            long intCount = j + ((long) intCount(buffer)) + 2 + ((long) (i9 * 2));
            buffer.writeInt(i9);
            buffer.writeInt(i8);
            for (int i11 = i6; i11 < i3; i11++) {
                byte b = list.get(i11).getByte(i);
                if (i11 == i6 || b != list.get(i11 - 1).getByte(i)) {
                    buffer.writeInt(b & 255);
                }
            }
            Buffer buffer2 = new Buffer();
            int i12 = i6;
            while (i12 < i3) {
                byte b2 = list.get(i12).getByte(i);
                int i13 = i12 + 1;
                int i14 = i13;
                while (true) {
                    if (i14 >= i3) {
                        i5 = i3;
                        break;
                    } else if (b2 != list.get(i14).getByte(i)) {
                        i5 = i14;
                        break;
                    } else {
                        i14++;
                    }
                }
                if (i13 == i5 && i + 1 == list.get(i12).size()) {
                    buffer.writeInt(list2.get(i12).intValue());
                } else {
                    buffer.writeInt((int) ((((long) intCount(buffer2)) + intCount) * -1));
                    buildTrieRecursive(intCount, buffer2, i + 1, list, i12, i5, list2);
                }
                i12 = i5;
            }
            buffer.write(buffer2, buffer2.size());
            return;
        }
        int min = Math.min(byteString.size(), byteString2.size());
        int i15 = i;
        int i16 = 0;
        while (i15 < min && byteString.getByte(i15) == byteString2.getByte(i15)) {
            i16++;
            i15++;
        }
        long intCount2 = j + ((long) intCount(buffer)) + 2 + ((long) i16) + 1;
        buffer.writeInt(-i16);
        buffer.writeInt(i8);
        int i17 = i;
        while (true) {
            i4 = i + i16;
            if (i17 >= i4) {
                break;
            }
            buffer.writeInt(byteString.getByte(i17) & 255);
            i17++;
        }
        if (i6 + 1 != i3) {
            Buffer buffer3 = new Buffer();
            buffer.writeInt((int) ((((long) intCount(buffer3)) + intCount2) * -1));
            buildTrieRecursive(intCount2, buffer3, i4, list, i6, i3, list2);
            buffer.write(buffer3, buffer3.size());
        } else if (i4 != list.get(i6).size()) {
            throw new AssertionError();
        } else {
            buffer.writeInt(list2.get(i6).intValue());
        }
    }

    @Override // java.util.List, java.util.AbstractList
    public ByteString get(int i) {
        return this.byteStrings[i];
    }

    public final int size() {
        return this.byteStrings.length;
    }

    private static int intCount(Buffer buffer) {
        return (int) (buffer.size() / 4);
    }
}
