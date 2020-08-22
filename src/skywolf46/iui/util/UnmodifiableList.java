package skywolf46.iui.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class UnmodifiableList<T> implements List<T> {
    private List<T> original;

    public UnmodifiableList(List<T> orig) {
        this.original = orig;
    }

    @Override
    public int size() {
        return original.size();
    }

    @Override
    public boolean isEmpty() {
        return original.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return original.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return original.iterator();
    }

    @Override
    public Object[] toArray() {
        return original.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return original.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return original.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public T get(int index) {
        return original.get(index);
    }

    @Override
    public T set(int index, T element) {
        return null;
    }

    @Override
    public void add(int index, T element) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return original.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return original.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return original.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return original.listIterator();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return original.subList(fromIndex, toIndex);
    }
}
