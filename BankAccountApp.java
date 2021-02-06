//*****************************************
//   Name: Fausto Navarro
//   CTP 150 – Section 202
//   Project
//**************************************** 
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
/**
   Works with a SavingsAccount object to allow a user to manipulate an account while taking exceptions into account
*/
class BankAccountApp
{
   /**
      Allows the user to create a savings account and provides an interface to withdraw, deposit, and check the savings account balance.
   */   
   public static void main(String[] args) throws InvalidDepositAmount   
   {
      double balance = 0, interestRate = 0, monthlyCharge = 0;
      int option;
      boolean inpError = false, instError = false, r = false;
      Scanner kb = new Scanner(System.in);
      SavingsAccount savings = null;   
      
      do
      {     
         try
         {
            System.out.print("Enter an initial balance: ");
            balance = kb.nextDouble();
           
            System.out.print("Enter an interest rate: ");
            interestRate = kb.nextDouble();
           
            System.out.print("Enter an initial monthly charge: ");
            monthlyCharge = kb.nextDouble();  
            inpError = false;     
         }
         
         catch(InputMismatchException e)
         {
            inpError = true;
            System.out.println("Invalid data input. Reenter all data.");
            kb.next();
         }         
             
      } 
      while(inpError);   
      
      //Initial balance exception checker
      do
      {     
         try
         {
            savings = new SavingsAccount(balance, interestRate, monthlyCharge);
            instError = false;
         }
         
         catch(InvalidDepositAmount e)
         {
            instError = true;
            System.out.println("\n"+ e.getMessage());
            System.out.print("Enter a valid starting deposit amount: ");
            balance = kb.nextDouble();
         }      
      } 
      while(instError);   

      
      do       
      {
         System.out.println("\nOption Menu:\n1: Deposit\n2: Withdraw\n3: View balance\n4: Exit\n");
         System.out.print("Enter an option: ");
         
         
         option = kb.nextInt();   
         //Interface options
         switch(option)
         {
            case 1:
               //Exception handling for deposits
               System.out.print("\nEnter how much would you like to deposit: ");
               do
               {
                  try
                  {
                     savings.deposit(kb.nextDouble());
                     System.out.println("Deposit successful");
                     r = false;
                  }
                  catch(InvalidDepositAmount e)
                  {
                     r = true;
                     System.out.println(e.getMessage());
                     System.out.print("Reenter a valid amount: ");
                  } 
                  catch(InputMismatchException e)
                  {
                     r = true;
                     System.out.print("Invalid data input. Reenter a valid amount: ");
                     kb.next();
                  }
                  
               } 
               while(r);
               
               break;

            case 2:
               //Exception handling for withdrawals
               System.out.print("\nEnter how much would you like to withdraw: ");
               do
               {
                  try
                  {
                     savings.withdraw(kb.nextDouble());
                     System.out.println("Withdrawal successful");
                     r = false;
                  }
                  catch(InvalidWithdrawalAmount e)
                  {
                     r = true;
                     System.out.println(e.getMessage());
                     System.out.print("Reenter a valid amount: ");
                  } 
                  catch(InputMismatchException e)
                  {
                     r = true;
                     System.out.print("Invalid data input. Reenter a valid number amount: ");
                     kb.next();
                  }                  
               } 
               while(r);  
               
               break;
              
            case 3:
               System.out.println("\n" + savings);
               break;
               
            case 4:
               savings.monthlyProcess();
               break; 
         }
      } 
      while (option == 1 || option == 2 || option == 3);
                
      writeObj(savings);
      readObj();  
   }
  
   /**
      Serializes a SavingsAccount object, handling all possible IOExceptions as well
      @param savings A SavingsAccount object
   */
   public static void writeObj(SavingsAccount savings)    
   {
      ObjectOutputStream out = null;
      try
      {
         FileOutputStream file = new FileOutputStream("savingsAccount.dat");
         out = new ObjectOutputStream(file);
         out.writeObject(savings);
         System.out.println("Object saved.");
      }
      catch(IOException e)
      {
         System.out.print(e.getMessage());
      }     
   }
   
   /**
      Deserializes a SavingsAccount object handling all ClassNotFoundExceptions and IOExceptions
   */ 
   public static void readObj() 
   {
      SavingsAccount savings = null;
      ObjectInputStream in = null;
      try
      {
         FileInputStream file = new FileInputStream("savingsAccount.dat");
         in = new ObjectInputStream(file);
         savings = (SavingsAccount)in.readObject(); 
         System.out.println("Object retrieved"); 
         System.out.printf("\nBalance: $%,.2f", savings.getBalance());
      }
      catch(IOException e)
      {
         System.out.println(e.getMessage());
      }
      catch(ClassNotFoundException e)
      {
         System.out.println(e.getMessage());
      }

   }    
}

/**
   An exception detailing when an invalid deposit is made
*/
class InvalidDepositAmount extends Exception
{
   /**
      Constructor passes a message to the Exception class's constructor so it can
      be displayed using getMessage() method
   */ 
   public InvalidDepositAmount(double deposit)
   {     
      super("Error: invalid deposit of $" + deposit + ". Deposit must be > 0 and < 10,000.");
   }
}

/**
   An exception detailing when an invalid withdrawal is made
*/
class InvalidWithdrawalAmount extends Exception
{
   /**
      Constructor passes a message to the Exception class's constructor so it can
      be displayed using getMessage() method
      @param withdrawal The withdrawal amount
      @param balance The balance
   */
   public InvalidWithdrawalAmount(double withdrawal, double balance)
   {
      super("Error: invalid withdrawal of $" + withdrawal + ". Withdrawal must be < 10000, > 0, and less than current balance of " + balance);
   }
} 

/**
   Stores and processes information about a bank account
*/
class BankAccount implements Serializable
{
   private double balance, interestRate, monthlyServiceCharges;
   private int deposits, withdrawals;
   
   /**
      Constructor accepts interestRate and interest rate parameters and sets them equal to the corresponding fields
      @param balance The account balance
      @param interestRate The annual interest rate
      @exception InvalidDepositAmount When the deposit amount is negative or greater than $10,000
   */
   public BankAccount(double balance, double interestRate) throws InvalidDepositAmount
   { 
      if(balance < 0 || balance > 10000)
      {
         throw new InvalidDepositAmount(balance);
      }
      else
         this.balance = balance;  
      this.interestRate = interestRate;
   }
   
   /**
      Constructor accepts interestRate and interest rate parameters and sets them equal to the corresponding fields
      @param balance The balance of the account
      @param interestRate The annual interest rate
      @param monthlyServiceCharges The amount of monthly service charge
      @exception InvalidDepositAmount When the deposit amount is negative or greater than $10,000
   */
   public BankAccount(double balance, double interestRate, double monthlyServiceCharges) throws InvalidDepositAmount
   {
      if(balance < 0 || balance > 10000)
         throw new InvalidDepositAmount(balance);
      else
         this.balance = balance;         
      this.interestRate = interestRate;
      this.monthlyServiceCharges = monthlyServiceCharges;
   }  
   
   /** 
      Mutator sets the interest rate equal to the argument passed into the method
      @param interestRate A new interest rate
   */
   public void setInterestRate(double interestRate)
   {
      this.interestRate = interestRate;
   }

   /** 
      Mutator sets the monthly service charge equal to the argument passed into the method
      @param monthlyServiceCharges A new monthly service charges
   */    
   public void setMonthlyServiceCharges(double monthlyServiceCharges)
   {
      this.monthlyServiceCharges = monthlyServiceCharges;
   }
   
   /**
      Accessor returns the bank account balance
      @return The bank account balance
   */
   public double getBalance()
   {
      return balance;
   }
   
   /**
      Accessor returns the bank account interest rate
      @return The monthly interest rate
   */
   public double getInterestRate()
   {
      return interestRate;
   }

   /**
      Accessor returns the bank account monthly service charge
      @return The dollar amount of monthly service charges
   */
   public double getMonthlyServiceCharges()
   {
      return monthlyServiceCharges;
   }
   
   /**
      Accessor returns the number of bank account deposits
      @return The number of bank account deposits
   */
   public int getDeposits()
   {
      return deposits;
   }
   
   /**
      Accessor returns the number of bank account withdrawals
      @return The number of bank account withdrawals
   */
   public int getWithdrawals()
   {
      return withdrawals;
   }
   
   /**
      Adds the argument to the account balance and increments the number of account deposits
      @param deposit An amount added to the balance
      @exception InvalidDepositAmount When the deposit amount is negative or greater than $10,000
   */
   public void deposit(double deposit) throws InvalidDepositAmount
   {
      if(deposit < 0 || deposit > 10000)
         throw new InvalidDepositAmount(deposit);
      else         
      {
         balance = balance + deposit;
         deposits++;
      }
   }
   
   /**
      Subtracts the argument to the account balance and increments the number of account withdrawals
      @param withdrawal An amount taken out of the balance
      @exception InvalidWithdrawalAmount When the withdrawal amount is negative, greater than $10,000,
         or greater than the balance 
   */
   public void withdraw(double withdrawal) throws InvalidWithdrawalAmount
   {
      if(withdrawal < 0 || withdrawal > 10000 || withdrawal > balance)
         throw new InvalidWithdrawalAmount(withdrawal, balance);
      else         
      {
         balance = balance - withdrawal;
         withdrawals++;
      }      
   }
   
   /**
      Updates balance by calculating the interest eared by the account and adding it to the balance
   */
   public void calcInterest()
   {
      double monthlyInterestRate = interestRate / 12;
      double interest = monthlyInterestRate * balance;
      balance += interest;
   }
   
   /**
      Subtracts service charges, calculates monthly interests, and resets withdrawals/deposits
   */
   public void monthlyProcess()
   {
      balance = balance - monthlyServiceCharges;
      calcInterest();
      withdrawals = deposits = 0;
      monthlyServiceCharges = 0;
   }
   
   /** 
      Displays all relevant information about the account
      @return A string containing balance, number of deposits, and number of withdrawals
   */
   public String toString()
   {
      DecimalFormat d = new DecimalFormat("$#.00");
      String str = "Balance: " + d.format(balance) 
         + "\nNumber of deposits: " + deposits
         + "\nNumber of withdrawals: " + withdrawals;
         
      return str;
   }
}

/**
   Details a savings account with all the attributes of a bank account and an additional
   status indicator
*/
class SavingsAccount extends BankAccount implements Serializable
{
   boolean status;
   final double BALANCE_CAP = 25; 
   final int WITHDRAWALCAP = 4; 
   
   /**
      Constructor accepts interestRate and interest rate parameters and sets them equal to the corresponding fields
      @param balance The balance of the account
      @param interestRate The annual interest rate
      @param monthlyServiceCharges The amount of monthly service charge
      @exception InvalidDepositAmount When the deposit amount is negative or greater than $10,000

   */
   public SavingsAccount(double balance, double interestRate, double monthlyServiceCharges) throws InvalidDepositAmount
   {
      super(balance, interestRate, monthlyServiceCharges);      
      checkStatus();
   }  
      
   /**
      Accessor for status variable
      @return The status of the account (active vs inactive)
   */
   public boolean getStatus()
   {
      return status;
   }
   
   /**
      Method determines if an account is inactive before a withdrawal is made
      @param withdrawal The amount of money being withdrawn
      @exception InvalidWithdrawalAmount When the withdrawal amount is negative, greater than $10,000,
         or greater than the balance    
   */
   
   @Override 
   public void withdraw(double withdrawal) throws InvalidWithdrawalAmount
   {
      checkStatus();
      if(!status)
         super.withdraw(withdrawal);
      checkStatus();
   }
   
   /**
      Method adds the deposit amount to the balance and checks the status
      @param deposit The amount of money being deposited
      @exception InvalidDepositAmount When the deposit amount is negative or greater than $10,000
   */
   @Override 
   public void deposit(double deposit) throws InvalidDepositAmount
   {
      super.deposit(deposit);
      checkStatus();
      if(!status)
         System.out.println("Account reactivated");
   }
   /**
      Method checks if the account is active or inactive
   */
   public void checkStatus()
   {
      if(getBalance() < BALANCE_CAP)
      {
         status = true;
         System.out.println("Balance is less than $" + BALANCE_CAP + ". The account is inactive.");
      }
      else
         status = false;
         
   }
   /**
      Monthly process adjusts the service charge depending on the number of withdrawals as well as checking the status
   */
   @Override
   public void monthlyProcess()
   {
      if(getWithdrawals() > WITHDRAWALCAP)
      {
         int overLimit = getWithdrawals() - WITHDRAWALCAP;
         setMonthlyServiceCharges(getMonthlyServiceCharges() + overLimit);
      } 
      
      super.monthlyProcess();
      checkStatus();
   }

   /** 
      Displays all relevant information about the savings account
      @return A string containing balance, number of deposits, and number of withdrawals
   */
   @Override
   public String toString()
   {
      String str = super.toString();
      if(status)
         str = "Balance is less than $" + BALANCE_CAP + ". The account is inactive.\n" + str; 
      return str;
   } 
} 


   