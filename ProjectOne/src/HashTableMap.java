import java.util.LinkedList;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")

public class HashTableMap <KeyType, ValueType> implements MapADT<KeyType, ValueType>
{
    public static class Entry <KeyType, ValueType>
    {
        private KeyType key; // storing key
        private ValueType value; // storing value

        private Entry()
        {

        }

        private Entry(KeyType key, ValueType value)
        {
            this.key = key;
            this.value = value;
        }

        // packaging the Entry class

        public void setKey(KeyType key)
        {
            this.key = key;
        }

        public void setValue(ValueType value)
        {
            this.value = value;
        }

        public KeyType getKey()
        {
            return key;
        }

        public ValueType getValue()
        {
            return value;
        }

    }
    // the maximum capacity in hash table
    private int capacity;
    // the current hashtable size
    private int entrySize;
    // A array of double linked list which storing the hashtable
    private LinkedList< Entry<KeyType, ValueType> >[] hashList;
    // storing load factor
    private double loadFactor;

    public HashTableMap(int initialCapacity)
    {
        // limiting initialCapacity
        if(initialCapacity <= 0)
        {
            throw new IllegalArgumentException("Illegal Capacity: InitialCapacity can't be 0 or negative");
        }

        // initializing HashTableMap class
        capacity = initialCapacity;
        hashList=new LinkedList[initialCapacity];
        entrySize=0;
        loadFactor=0.8;
        for(int i=0;i<initialCapacity;i++)
        {
            hashList[i]= new LinkedList<>();
        }

    }

    public HashTableMap()
    {
        this(10);
    } // with default capacity = 10

    public boolean put(KeyType key, ValueType value)
    {
        // making sure that key and value are not null
        if(key==null || value ==null)
        {
            throw new IllegalArgumentException("IllegalArgumentException: key or value is null");
        }
        //judging the repetitive key
        if(containsKey(key))
        {
            return false;
        }
        else
        {
            // putting Entry to HashTableMap
            Entry<KeyType, ValueType> newPair= new Entry<>(key, value);
            int index = getHashIndex(key);
            hashList[index].add(newPair);
            entrySize++;
            // changing load factor
            // resizing and rehashing if load factor >=0.85
            changeLoadFactor();
            if(isOversize())
            {
                helper();
            }
            return true;
        }
    }

    public ValueType get(KeyType key) throws NoSuchElementException
    {
        int index =getHashIndex(key);
        // judging the hashtable[index] is empty or not
        if(hashList[index].isEmpty())
        {
            throw new NoSuchElementException("NoSuchElementException: The value of index is null");
        }
        // finding the value of hashtable[index]
        for(Entry<KeyType, ValueType> e:hashList[index])
        {
            if(e.getKey().equals(key))
            {
                return e.getValue();
            }
        }
        throw new NoSuchElementException("NoSuchElementException: No such value");
    }

    // returning the current hash table size
    public int size()
    {
        return entrySize;
    }


    public boolean containsKey(KeyType key)
    {
        int index=getHashIndex(key);
        //whether hashList[index] is empty
        if(hashList[index].isEmpty())
        {
            return false;
        }
        //finding the key and value
        for(Entry<KeyType, ValueType> e:hashList[index])
        {
            if(e.getKey().equals(key))
            {
                return true;
            }

        }
       return false;
    }


    public ValueType remove(KeyType key)
    {
        int index=getHashIndex(key);
        for(Entry<KeyType, ValueType> e:hashList[index])
        {
            if(e.getKey().equals(key))
            {
                ValueType tmp=e.getValue();
                hashList[index].remove();
                entrySize--;
                return tmp;
            }
        }
        return null;
    }


    public void clear()
    {
        hashList=new LinkedList[capacity];
        entrySize=0;
    }

    private int getHashIndex(KeyType key)
    {
        return Math.abs(key.hashCode()) % (capacity-1);
    }

    private void helper()
    {
        LinkedList<Entry<KeyType, ValueType>>[] tmp = hashList.clone();
        capacity*=2;
        hashList = new LinkedList[capacity];
        entrySize=0;
        for(int i=0;i<capacity;i++)
        {
            hashList[i]=new LinkedList<>();
        }
        for (LinkedList<Entry<KeyType, ValueType>> entries : tmp) {
            if (entries != null)
            {
                while (entries.size() != 0) {
                    Entry<KeyType, ValueType> e = entries.pop();
                    put(e.getKey(), e.getValue());
                }
            }
        }
    }

    private boolean isOversize()
    {
        return loadFactor >= 0.85;
    }

    private void changeLoadFactor()
    {
        loadFactor=((double)entrySize)/((double)capacity);
    }
}
