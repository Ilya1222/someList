package ua.nure.shevchenkoi.practice2;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Created by DELL on 19.12.2018.
 */
public class MyListImpl implements MyList , ListIterable {
    private static final int DEFAULT_SIZE = 7;
    private int size;
    private Object[] array = new Object[DEFAULT_SIZE];

    private void resize(int newLength) {
        Object[] newArray = new Object[newLength];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }


    @Override
    public String toString() {
        StringBuilder newContainer = new StringBuilder();
        if (size == 0) {
            newContainer.append("[]");
        } else {
            for (int i = 0; i < size; i++) {
                newContainer.append("[" + array[i] + ", ]");
            }
        }
        return newContainer.toString().replaceAll("]\\[", "").replaceAll(", ]", "]");
    }

    @Override
    public void add(Object e) {
        if (size == array.length - 1) {
            resize(array.length + 5);
        }
        array[size++] = e;

    }

    @Override
    public void clear() {
        Object[] newArray = new Object[DEFAULT_SIZE];
        array = newArray;
        size = 0;

    }

    private void removeForIndex(int index) {
        index-=1;
        for (int i = index ; i < size; i++){
            array[i] = array[i + 1];
        }
        array[size] = null;
        size--;

    }

    @Override
    public boolean remove(Object o) {
        int index = 0;
        boolean isRemove = false;
        if (o == null) {
            for (int i = 0; i < size; i++) {
                index++;
                if (o == array[i]) {
                    isRemove = true;

                    break;
                } else {
                    isRemove = false;
                }

            }

         removeForIndex(index);

        } else {
            for (int i = 0; i < size; i++) {
                index++;
                if (o.equals(array[i])) {
                    isRemove = true;
                    break;

                } else {
                    isRemove = false;
                }

            }
            removeForIndex(index);
        }
      return isRemove;
    }


    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[size];
        System.arraycopy(array, 0, newArray, 0, size);
        return newArray;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == o) {
                return true;
            }
        }
        return false;
    }

    private static boolean search(Object[] o, Object el) {
        for (int i = 0; i < o.length; i++) {
            if (el.equals(o[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(MyList c) {
        Object[] current = new Object[size];
        System.arraycopy(array, 0, current, 0, size);
        Object[] newArray = c.toArray();
        int length = newArray.length;
        int count = 0;
        for (int i = 0; i < newArray.length; i++) {
            if (search(current, newArray[i])) {
                count++;
            }
        }
        return length == count;
    }

    @Override
    public Iterator<Object> iterator() {
        return new IteratorImpl();
    }

    @Override
    public ua.nure.shevchenkoi.practice2.ListIterator listIterator() {
        return new ListIteratorImpl();
    }


    private class IteratorImpl implements Iterator <Object> {
        int indicator;
        int last;
        boolean flag;

        @Override
        public boolean hasNext() {
            return indicator != size;
        }

        @Override
        public Object next() {
            int next = indicator;
            if (next >= size) {
                throw new NoSuchElementException();
            }
            Object[] newArray = MyListImpl.this.array;
            if (next >= newArray.length){
                throw new NoSuchElementException();
            }
            indicator = next + 1;
            last = next;
            flag = true;
            return newArray[last];
        }


        @Override
        public void remove() {
            if (!flag) {
                throw new IllegalStateException();
            }
            int goal = indicator;
            MyListImpl.this.removeForIndex(goal);
            indicator = indicator - 1;
            flag = false;

        }


    }


    private class ListIteratorImpl extends IteratorImpl implements ListIterator  {


        @Override
        public boolean hasPrevious() {
            return  indicator!=0;
        }

        @Override
        public Object previous() {
            flag=true;
            int reversNext = indicator;
            if(reversNext<0){
                throw new NoSuchElementException();
            }
            Object [] newArray =  MyListImpl.this.array;
            if (reversNext>=newArray.length){
                throw new NoSuchElementException();
            }
            indicator=reversNext-1;
            last = reversNext-1;
            return newArray[last];
        }

        @Override
        public void set(Object e) {
            if (flag){
                MyListImpl.this.array[indicator] = e;
                flag=false;
            }

        }
    }

}
