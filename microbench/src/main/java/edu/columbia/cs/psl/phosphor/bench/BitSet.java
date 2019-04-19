package edu.columbia.cs.psl.phosphor.bench;

public class BitSet {

    // The number of bits that can be packed into a long.
    private final static int BITS_PER_PACKET = 1 << 6;
    // Array of bit-packed longs. Each bit in a packet represents a particular element in the set. If a bit is 1 then
    // the element represented by that bit is present in the set, otherwise the element in is absent.
    // The if the bit at the position representing a particular element is 1 then that
    private long[] packets;

    /* Creates a new set that can have up to the specified maximum number of elements. */
    public BitSet(int maxElements) {
        this.packets = new long[maxElements/BITS_PER_PACKET + (maxElements%BITS_PER_PACKET == 0 ? 0 : 1)];
    }

    /* Creates a new set that is a copy of the specified set. */
    public BitSet(BitSet set) {
        this.packets = set.packets.clone();
    }

    /* Returns a copy of this set. */
    public BitSet copy() {
        return new BitSet(this);
    }

    /* Adds the element represented by the bit at the specified index to the set.*/
    public void add(int bitIndex) {
        // The index of the packet the specified bit index is located in
        int packetIndex = bitIndex/BITS_PER_PACKET;
        // The mask to isolate the specified bit in the packet it is located in
        int mask = 1 << bitIndex%BITS_PER_PACKET;
        packets[packetIndex] |= mask;
    }

    /* Returns whether or not the element represented by the bit at the specified index is in the set. */
    public boolean contains(int bitIndex) {
        // The index of the packet the specified bit index is located in
        int packetIndex = bitIndex/BITS_PER_PACKET;
        // The mask to isolate the specified bit in the packet it is located in
        int mask = 1 << bitIndex%BITS_PER_PACKET;
        return (packets[packetIndex] & mask) == 1;
    }

    /* Adds all of the elements in the specified other set to this set. */
    public void union(BitSet other) {
        if(other.packets.length > this.packets.length) {
            long[] temp = this.packets;
            this.packets = other.packets.clone();
            for(int i = 0; i < temp.length; i++) {
                this.packets[i] |= temp[i];
            }
        } else {
            for(int i = 0; i < other.packets.length; i++) {
                this.packets[i] |= other.packets[i];
            }
        }
    }

    /* Returns a new BitSet that represents the union of the specified sets. */
    public static BitSet union(BitSet set1, BitSet set2) {
        if(set1.packets.length > set2.packets.length) {
            BitSet result = new BitSet(set1);
            result.union(set2);
            return result;
        }  else {
            BitSet result = new BitSet(set2);
            result.union(set1);
            return result;
        }
    }
}
