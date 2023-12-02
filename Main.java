import java.util.*;
import java.time.*;
import java.math.*;
public class Main {
 static void check(boolean condition){if(!condition)throw new AssertionError();}

 record Entry(String account,long cents){}
 record Payment(String tenant,String key,List<Entry> entries){}
 static class Ledger {
  final Map<String,Payment> requests=new HashMap<>();final Map<String,Long> balances=new HashMap<>();final List<Payment> outbox=new ArrayList<>();
  synchronized boolean post(Payment p){
   if(p.tenant()==null||p.tenant().isBlank()||p.key()==null||p.key().isBlank())throw new IllegalArgumentException("identity");
   p=new Payment(p.tenant(),p.key(),List.copyOf(p.entries()));String id=p.tenant()+"/"+p.key();
   if(requests.containsKey(id)){if(!requests.get(id).equals(p))throw new IllegalArgumentException("idempotency conflict");return false;}
   long sum=0;for(Entry e:p.entries()){if(e.account()==null||e.account().isBlank())throw new IllegalArgumentException("account");sum=Math.addExact(sum,e.cents());}
   if(p.entries().size()<2||sum!=0)throw new IllegalArgumentException("unbalanced");
   Map<String,Long> staged=new HashMap<>(balances);
   for(Entry e:p.entries())staged.merge(p.tenant()+"/"+e.account(),e.cents(),Math::addExact);
   balances.clear();balances.putAll(staged);requests.put(id,p);outbox.add(p);return true;
  }
 }
 public static void main(String[] args){
  Ledger l=new Ledger();Payment p=new Payment("a","1",List.of(new Entry("cash",100),new Entry("clearing",-100)));
  check(l.post(p));check(!l.post(p));check(l.outbox.size()==1);check(l.balances.get("a/cash")==100);
  try{l.post(new Payment("a","2",List.of(new Entry("cash",1),new Entry("clearing",0))));throw new AssertionError();}catch(IllegalArgumentException expected){}
  check(l.outbox.size()==1);check(l.post(new Payment("b","1",p.entries())));
  try{l.post(new Payment("a","1",List.of(new Entry("cash",2),new Entry("clearing",-2))));throw new AssertionError();}catch(IllegalArgumentException expected){}
  System.out.println("PASS: balance, idempotency, conflict and tenant boundary");
 }
}
