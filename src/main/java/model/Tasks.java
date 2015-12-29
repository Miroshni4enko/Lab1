package model;


import java.util.*;

public class Tasks {
    static public Iterable<Task> incoming(Iterable<Task> tasks, Date start, Date end){
        List<Task> incoming = new LinkedList<Task>();
        Iterator<Task> iterator = tasks.iterator();
        Date timeBetween;
        Task current = null;
        while (iterator.hasNext() ) {
            current = iterator.next();
            timeBetween = current.nextTimeAfter(start);
            if (((timeBetween != null)&&timeBetween.compareTo(end)<=0)){
                incoming.add(current);
            }
        }

            return incoming;
    }

    static  public SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end){
        SortedMap<Date, Set<Task>>  incoming =  new TreeMap<Date, Set<Task>>();
        Iterator<Task> iterator = tasks.iterator();
        Date timeBetween = null;
        Task current = null;
        while (iterator.hasNext() ) {
            current = iterator.next();
            timeBetween = current.nextTimeAfter(start);

            if ((timeBetween != null)&& (timeBetween.compareTo(end)<=0)){
                while ( timeBetween.compareTo(end) <= 0){
                    if(incoming.containsKey(timeBetween)){
                        Set<Task>  mac = incoming.get(timeBetween);
                        mac.add(current);
                        incoming.put(new Date(timeBetween.getTime()),mac);
                    }
                    else{
                        Set<Task> app = new HashSet<Task>();
                        app.add(current);

                        incoming.put(new Date(timeBetween.getTime()), app);
                    }
                    if(current.isRepeated()&&timeBetween.compareTo(current.getEndTime()) <= 0) {
                        timeBetween.setTime(timeBetween.getTime() + current.getRepeatInterval()*1000);
                    } else break;
                    //if (timeBetween == null) break;

                }
            }
        }

        return incoming;
    }
}


