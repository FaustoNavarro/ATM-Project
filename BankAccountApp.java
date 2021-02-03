//*****************************************
//   Name: Fausto Navarro
//   CTP 150 – Section 202
//   Project
//**************************************** 
import java.util.*;
import java.io.*;
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
      @psrsm savings A SavingsAccount object
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