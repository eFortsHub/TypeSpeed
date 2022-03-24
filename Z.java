public class Z {
    public static void main(String a[]){

        Account s = new Account("1");
        Account t = new Account("4");
        
        try {
            transfer(s, t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    public static void transfer(Account source, Account target) throws InterruptedException {
        Account firstLock = target.id.compareTo(source.id)  > 0 ? target : source;
        Account secondLock = target.id.compareTo(source.id)  < 0 ? target : source;

        double sourceBalance = 0;
        double targetBalance = 0;

        synchronized (firstLock) {
            synchronized (secondLock) {
                while(sourceBalance <= 4 ) {
                    source.wait(1000);
                    System.out.println("waiting... ");
                    sourceBalance = sourceBalance+1; //get source balance from somewhere else realtime. for me im just updating
                }
                
                // Money tranfer logic
                targetBalance = 0; //get target balance from server or where you stored;
                targetBalance +=sourceBalance; 
                //send target balance where you want
                System.out.println("Money transferred");
                
                source.notifyAll();
            }   
        }
    }

    
}
class Account {
    String id;
    public Account(String id){
        this.id = id;
    }
}
