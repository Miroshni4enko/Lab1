package model;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Date;
import java.util.Timer;

public class Task implements Serializable,Cloneable{
    private String title;
    private int interval;
    private boolean active ;
    private boolean repeat ;
    private Date start;
    private Date end;
    private Date time;
    private Timer timer;

    private static final Logger log = Logger.getLogger(Task.class);

    public Task(String title, Date time) {
        if(time == null){
            log.error("Field cannot be null");
            return;
        }
        this.title = title;
        this.time = new Date(time.getTime());
    }

    public Task(String title, Date start, Date end, int interval) {
        if(interval <= 0){
            log.error("Interval must be > 0");
            return;
        }
        if(start == null|end == null){
            log.error("Field cannot be null");
            return;
        }
        this.title = title;
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
        this.interval = interval;
        repeat = true;
    }

        public void setTitle(String title){
            this.title = title;
        }

        public void setTime(Date time){
            if(time == null) {
                log.error("Field cannot be null");
                return;
            }
            if(this.interval == 0) this.time = time;
            else {
                this.end = null;
                this.interval = 0;
                this.start = null;
                this.time = new Date(time.getTime());
                repeat = false;
            }
     }

        public void setTime(Date start,Date end,int interval){
            if(interval <= 0){
                log.error("Interval must be > 0");
                return;
            }
            if(start == null|end == null){
                log.error("Field cannot be null");
                return;
            }
            if(interval == 0) {
                this.time = null;
                this.end = new Date(end.getTime());
                this.interval = interval;
                this.start = new Date(start.getTime());
                repeat = true;
            }else {
                this.end = new Date(end.getTime());
                this.interval = interval;
                this.start = new Date(start.getTime());
                repeat = true;
          }
        }

        public void setActive(boolean active){
            this.active = active;
        }


    public Date getTime() {
        //if(this.interval == 0){ return(time);
        //}else
            return(time);
    }


    public Date getStartTime(){
        //if(this.interval == 0){ return(time);
        //}else
            return(start);
    }

    public Date getEndTime(){
       // if(this.interval == 0){ return (time);
        //}else
            return(end);}


    public String getTitle(){
            return(title);
        }


    public int getRepeatInterval(){
            return(interval);
        }

    public boolean isActive(){
            return(active);
        }

    public boolean isRepeated(){
            return(repeat);
        }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        if(timer == null) {
            log.error("Timer cannot be null");
            return;
        }
        this.timer = timer;
    }

    public boolean isTimer(){
        return (timer!=null);
    }

    public Date nextTimeAfter(Date curren){
        Date current = new Date(curren.getTime());
        Date data;
        if((active)&&(interval > 0)&&(current.before(this.end))){
            long i = start.getTime();
            while (i <= current.getTime()) {
                i += interval*1000;
            }
            if (i > end.getTime()) {
                return null;
            }
            return new Date(i);

        } else if((active)&&(interval == 0)&&(current.before(time))){
            data = new Date(this.time.getTime());
        } else
            data = null;
        return(data);
    }
    @Override
    public boolean equals (Object ob) {
        if (this == ob) return true;
        if (ob == null) return false;
        if (this.getClass() != ob.getClass()) return false;
        Task ob1 = (Task) ob;
        Task ob2 = this;
        if(ob1.isRepeated()) {
            return ( ob1.interval == ob2.interval && ob1.active == ob2.active && ob1.repeat == ob2.repeat  &&
                     ob1.title.equals(ob2.title) && ob1.end.equals(ob2.end) && ob1.start.equals(ob2.start) );}
        else{
            return (ob1.interval == ob2.interval && ob1.active == ob2.active && ob1.repeat == ob2.repeat  &&
                    ob1.title.equals(ob2.title) && ob1.time.equals(ob2.time) );
        }}


    @Override
    public int hashCode() {
        final int prime = 3;
        int result = 2;
        result = prime  + result*this.interval;
        result = prime * result + this.title.hashCode();
        if (this.interval >0)
            result = result + this.end.hashCode() + this.start.hashCode();
        else result = result  + this.time.hashCode();
        return result;
    }
    @Override
    public Task clone() {
        try {
            Task clone = (Task) super.clone();
            if(clone.isRepeated()) {
                clone.end = new Date(this.end.getTime());
                clone.start = new Date(this.start.getTime());}
            else
                clone.time  = new Date(this.time.getTime());

            return clone;
        } catch (CloneNotSupportedException e) {
            System.out.println("NOT");
            return this;
        }
    }

    @Override
    public String toString (){
        String S;
        if (this.isRepeated())
            S = "Task: " + this.getTitle() +" Start " + this.getStartTime() +" End " + this.getEndTime() +
            " Interval " + this.getRepeatInterval() + " Active " + this.isActive();
        else S = "Task: " + this.getTitle()  + " Time " + this.getTime()+ " Active " + this.isActive();
        return  S;
    }
}