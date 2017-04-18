package ru.ncedu.java.tasks;

import java.util.*;


public class StringFilterImpl implements StringFilter {
    public StringFilterImpl(){}
    private Set<String> set = new HashSet<>();

    @Override
    public void add(String s) {
        if(s == null){
            set.add(s);
        } else {
            set.add(s.toLowerCase());
        }
    }

    @Override
    public boolean remove(String s) {
        boolean flag = false;
        if(s == null){
            flag = set.remove(s);
        } else  {
           flag = set.remove(s.toLowerCase());
        }
        return flag;
    }

    @Override
    public void removeAll() {
        this.set.removeAll(set);
    }

    @Override
    public Collection<String> getCollection() {
        return set;
    }

    @Override
    public Iterator<String> getStringsContaining(String chars) {
        Set<String> newSet = new HashSet();
        if((chars == null) || (chars.length() == 0)){
            return this.getCollection().iterator();
        } else {
            for(String s : this.getCollection()){
                if(s == null){
                    continue;
                }
                if(s.contains(chars.toLowerCase())){
                    newSet.add(s);
                }
            }
        }
        return newSet.iterator();
    }

    @Override
    public Iterator<String> getStringsStartingWith(String begin) {
        Set<String> newSet = new HashSet();
        if((begin == null) || (begin.length() == 0)){
            return this.getCollection().iterator();
        } else {
            for(String s : this.getCollection()){
                if(s == null){
                    continue;
                }
                if(s.startsWith(begin.toLowerCase())){
                    newSet.add(s);
                }
            }
        }
        return newSet.iterator();
    }

    @Override
    public Iterator<String> getStringsByNumberFormat(String format) {
        Set<String> newSet = new HashSet();
        boolean flag = false;
        if((format == null) || (format.length() == 0)){
            return this.getCollection().iterator();
        } else {
            for(String s : this.getCollection()){
                if(s == null){
                    continue;
                }
                if(s.length() != format.length()){
                    continue;
                }
                for(int i = 0; i < format.length(); ++i){
                    if(format.charAt(i) == s.charAt(i)){
                        continue;
                    }
                    if(format.charAt(i)=='#' && (s.charAt(i)=='0' || s.charAt(i)=='1' || s.charAt(i)=='2' || s.charAt(i)=='3' || s.charAt(i)=='4'
                            || s.charAt(i)=='5' || s.charAt(i)=='6' || s.charAt(i)=='7' || s.charAt(i)=='8' || s.charAt(i)=='9')){
                        flag = true;
                        continue;
                    } else {
                        flag = false;
                        break;
                    }
                }
                if (flag == true){
                    newSet.add(s);
                }
            }
        }
        return newSet.iterator();
    }

    @Override
    public Iterator<String> getStringsByPattern(String pattern) {
        Set<String> newSet = new HashSet();
        ArrayList arr = new ArrayList();
        if( pattern == null || pattern.length() == 0){
            return this.getCollection().iterator();
        } else {
            for(int i = 0; i < pattern.length(); ++ i ){ //находим позиции *
                if(pattern.charAt(i) == '*'){
                    arr.add(i);
                }
            }

            for(String s : this.getCollection()){
                if(arr.size() == 0){
                    if(pattern.compareTo(s) == 0){
                        newSet.add(s);
                    }
                    continue;
                } else {
                    if(s == null){
                        continue;
                    }
                    if(arr.size() == 1){
                        if(pattern.charAt(0) == '*' && pattern.length()==1){// *
                            return this.getCollection().iterator();
                        }
                        if(pattern.charAt(0) == '*'){ // *...
                            if(s.endsWith(pattern.substring(1))){
                                newSet.add(s);
                            }
                            continue;
                        }
                        if((int)arr.get(0) == pattern.length()){//...*
                            if(s.substring(0, (int)arr.get(0)).compareTo(pattern.substring(0,(int)arr.get(0))) == 0){
                                newSet.add(s);
                            }
                            continue;
                        } else {//...*...
                            if(s.startsWith(pattern.substring(0,(int)arr.get(0))) && s.endsWith(pattern.substring((int)arr.get(0)+1))){
                                newSet.add(s);
                            }
                            continue;
                        }
                    } else {
                        if(pattern.charAt(0) == '*' && pattern.charAt(1) == '*' && pattern.length()==2){// **
                            return this.getCollection().iterator();
                        }
                        if(pattern.charAt(0) == '*' && pattern.charAt(1) == '*' && pattern.length() > 2){// **...
                            if(s.endsWith(pattern.substring((int)arr.get(1)+1))){
                                newSet.add(s);
                            }
                            continue;
                        }
                        if(pattern.charAt(pattern.length()-1) == '*' && pattern.charAt(pattern.length()-2) == '*' && pattern.length() > 2){// ...**
                            if(s.startsWith(pattern.substring(0,pattern.length()-2))){
                                newSet.add(s);
                            }
                            continue;
                        }
                        if(pattern.charAt(0) != '*' && pattern.charAt(pattern.length()-1)!= '*' && (int)arr.get(0) == ((int)arr.get(1)+1)){//...**...
                            if(s.startsWith(pattern.substring(0,(int)arr.get(0))) && s.endsWith(pattern.substring((int)arr.get(1)+1))){
                                newSet.add(s);
                            }
                            continue;
                        }
                        if(pattern.charAt(0)== '*' && pattern.charAt(pattern.length()-1) == '*' && pattern.length() > 2){//*...*
                            if(s.contains(pattern.substring(1,pattern.length()-1))){
                                newSet.add(s);
                            }
                            continue;
                        } else {//...*...*...
                            if( s.startsWith(pattern.substring(0,(int)arr.get(0))) && s.contains(pattern.substring((int)arr.get(0)+1,(int)arr.get(1)))
                                    && s.endsWith(pattern.substring((int)arr.get(1)+1))){
                                newSet.add(s);
                            }
                            continue;
                        }
                    }
                }
            }
        }
        return newSet.iterator();
    }

    public static void main(String[] args){
        StringFilter s = new StringFilterImpl();

        s.add("distribute");
        s.add("distr57567ibutem");
        s.add("1345.7");
        s.add("THREE");
        s.add("-24.7");
        s.add(null);

       Iterator<String> it = s.getStringsByPattern("d*tr*e");
        System.out.println("______________");
       while (it.hasNext()){
            System.out.println(it.next());
        }
    }
}
