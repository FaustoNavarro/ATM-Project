//*****************************************
//   Name: Fausto Navarro
//   CTP 150 – Section 202
//   Project
//**************************************** 
/**
   An exception detailing when an invalid deposit is made
*/
public class InvalidDepositAmount extends Exception
{
   public InvalidDepositAmount(double deposit)
   {
      /**
         Constructor passes a message to the Exception class's constructor so it can
         be displayed using getMessage() method
      */      
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
   */
   public InvalidWithdrawalAmount(double withdrawal, double balance)
   {
      super("Error: invalid withdrawal of $" + withdrawal + ". Withdrawal must be < 10000, > 0, and less than current balance of " + balance);
   }
} 

   