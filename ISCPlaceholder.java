import java.util.*;
import java.util.Iterator;

public class ISCPlaceholder<T extends Comparable<T>>
    implements IterableSortedCollection<T> {

    private T value;

    private ArrayList<SongInterface> songs = new ArrayList<>();
    
    public boolean insert(T data)
	throws NullPointerException, IllegalArgumentException {
	value = data;
    songs.add((SongInterface) value);
	return true;
    }

    public boolean contains(Comparable<T> data) {
	return true;
    }

    public boolean isEmpty() {
	return false;
    }
    
    public int size() {
	return songs.size();
    }

    public void clear() {
    }

    public void setIterationStartPoint(Comparable<T> startPoint) {	
    }

    public Iterator<T> iterator() {
        return (Iterator<T>) songs.iterator();
    }
}
