import java.sql.*;
import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.IOException; 

public class Connect 
{
   public Connection connect()
   {
        Connection conn = null;
        try
        {
            String url =  "jdbc:sqlite:TRY.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection has been succesfully established");
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return conn;
   }
   
    public void createTable(Connection x)
    {
        try
        {
            System.out.println("Creating tables");
            Statement CsvOrder = x.createStatement();
            CsvOrder.executeUpdate("PRAGMA foreign_keys = ON;");
            CsvOrder.executeUpdate("CREATE TABLE CUSTOMER (Account_Number INTEGER NOT NULL, Forename TEXT NOT NULL, Surname TEXT NOT NULL, email_Address TEXT NOT NULL, Character_Name TEXT NOT NULL, PRIMARY KEY(Account_Number, Character_Name), FOREIGN KEY (Character_Name) REFERENCES CUSTOMER_HAS_CHARACTER (Character_Name) ON DELETE CASCADE)");
            CsvOrder.executeUpdate("CREATE TABLE CUSTOMER_HAS_CHARACTER (Character_Name TEXT NOT NULL PRIMARY KEY, Character_Type TEXT NOT NULL, Level INTEGER NOT NULL, ExperiencePoints INTEGER NOT NULL, Max_Health INTEGER NOT NULL, Health INTEGER NOT NULL, AttackinScore INTEGER NOT NULL, DefenceScore INTEGER NOT NULL, StealthScore INTEGER NOT NULL, ManaScore INTEGER, Money_bank INTEGER NOT NULL, Money_wallet INTEGER NOT NULL, Account_Number INTEGER NOT NULL, FOREIGN KEY (Account_Number) REFERENCES CUSTOMER (Account_Number) ON DELETE CASCADE)");
            CsvOrder.executeUpdate("CREATE TABLE CHARACTER_HAS_ITEMS (Character TEXT NOT NULL, Item TEXT NOT NULL, Item_Type TEXT NOT NULL, WeaponType TEXT, Range TEXT, Price INTEGER NOT NULL, Quantity TEXT NOT NULL, DefendScore INTEGER, AttackScore INTEGER, HealingScore INTEGER, ManaScore INTEGER, SingleUse INTEGER, wearable INTEGER, worn INTEGER, BodyPart TEXT, Equipped INTEGER, Character_Name TEXT NOT NULL, PRIMARY KEY(Item, Character_Name), FOREIGN KEY (Character_Name) REFERENCES CUSTOMER_HAS_CHARACTER (Character_Name) ON DELETE SET NULL)");
            CsvOrder.executeUpdate("CREATE TABLE CHARACTER_HAS_COMBAT (BattleDate TEXT NOT NULL, BattleNo INTEGER NOT NULL, Attacker TEXT NOT NULL, Defender TEXT NOT NULL, Weapon TEXT NOT NULL, Result TEXT NOT NULL, Damage INTEGER NOT NULL, Character_Name TEXT NOT NULL, PRIMARY KEY(Character_Name, BattleNo, Damage, Result), FOREIGN KEY (Character_Name) REFERENCES CUSTOMER_HAS_CHARACTER (Character_Name) ON DELETE CASCADE)");
            CsvOrder.executeUpdate("CREATE TABLE CUSTOMER_HAS_BILL (Character_CreationDate TEXT NOT NULL, Character_Expiry_Date TEXT, Account_Number INTEGER NOT NULL, PRIMARY KEY(Character_CreationDate, Account_Number), FOREIGN KEY (Account_Number) REFERENCES CUSTOMER (Account_Number) ON DELETE CASCADE)");
	        System.out.println("Tables created successfully");
        }
        catch (SQLException e)
			{
	            System.out.println(e.getMessage());
	        }
    }

    public void PopulateCust(Connection x)
    {
        String line;   
        try   
        { 
            BufferedReader br = new BufferedReader(new FileReader("Customers.csv"));
            br.readLine();  
            Statement CsvOrder = x.createStatement(); 
            while ((line = br.readLine()) != null)    
            {  
                String[] values = line.split(",");
                for(int i = 0; i<18; i++)
                {
                    if(values[i] == "")
                    {
                        values[i] = null;
                    } 
                }
                CsvOrder.executeUpdate("INSERT INTO CUSTOMER (Account_Number, Forename, Surname, email_Address, Character_Name) VALUES("+'"'+values[0]+'"'+","+'"'+values[1]+'"'+","+'"'+values[2]+'"'+","+'"'+values[4]+'"'+","+'"'+values[6]+'"'+")");
            }
            br.close(); 
        }   
        catch (IOException|SQLException ex)   
        {  
            System.out.println(ex.getMessage());  
        }   
            
    }

    public void PopulateChar(Connection x)
    {
        String line;   
        try   
        {   
            BufferedReader br = new BufferedReader(new FileReader("Customers.csv"));
            br.readLine();  
            Statement CsvOrder = x.createStatement(); 
            while ((line = br.readLine()) != null)    
            {  
                String[] values = line.split(",");
                for(int i = 0; i<18; i++)
                {
                    if(values[i] == "")
                    {
                        values[i] = "NULL";
                    } 
                }
                    CsvOrder.executeUpdate("INSERT INTO CUSTOMER_HAS_CHARACTER (Character_Name, Character_Type, Level, ExperiencePoints, Max_Health, Health, AttackinScore, DefenceScore, StealthScore, ManaScore, Money_Bank, Money_Wallet, Account_Number) VALUES("+'"'+values[6]+'"'+","+'"'+values[7]+'"'+","+'"'+values[8]+'"'+","+'"'+values[9]+'"'+","+'"'+values[10]+'"'+","+'"'+values[11]+'"'+","+'"'+values[12]+'"'+","+'"'+values[13]+'"'+","+'"'+values[14]+'"'+","+values[15]+","+'"'+values[16]+'"'+","+'"'+values[17]+'"'+","+'"'+values[0]+'"'+")");    
            }
            br.close();  
        }   
        catch (IOException|SQLException ex)   
        {  
            System.out.println(ex.getMessage());  
        } 
    }

    public void PopulateItem(Connection x)
    {
        String line;   
        try   
        {   
            BufferedReader br = new BufferedReader(new FileReader("Items.csv"));
            BufferedReader gr = new BufferedReader(new FileReader("Customers.csv"));
            br.readLine();
            gr.readLine();
            Statement CsvOrder = x.createStatement();
            String line2 = gr.readLine();
            String[] values2 = line2.split(","); 
            while ((line = br.readLine()) != null)    
            {  
                String[] values = line.split(",",-1);
                for(int i = 0; i<16; i++)
                {
                    if(values[i] == "")
                    {
                        values[i] = "NULL";
                    }
                }
                if(values[3] == "NULL")
                {
                    CsvOrder.executeUpdate("INSERT INTO CHARACTER_HAS_ITEMS (Character,	Item, Item_Type, WeaponType, Range, Price, Quantity, DefendScore, AttackScore, HealingScore, ManaScore, SingleUse, wearable, worn, BodyPart, Equipped, Character_Name) VALUES("+'"'+values[0]+'"'+","+'"'+values[1]+'"'+","+'"'+values[2]+'"'+","+values[3]+","+values[4]+","+'"'+values[5]+'"'+","+'"'+values[6]+'"'+","+values[7]+","+values[8]+","+values[9]+","+values[10]+","+values[11]+","+values[12]+","+values[13]+","+'"'+values[14]+'"'+","+values[15]+","+'"'+values[0]+'"'+")");
                }
                else if(values[14] == "NULL")
                {
                    CsvOrder.executeUpdate("INSERT INTO CHARACTER_HAS_ITEMS (Character,	Item, Item_Type, WeaponType, Range, Price, Quantity, DefendScore, AttackScore, HealingScore, ManaScore, SingleUse, wearable, worn, BodyPart, Equipped, Character_Name) VALUES("+'"'+values[0]+'"'+","+'"'+values[1]+'"'+","+'"'+values[2]+'"'+","+'"'+values[3]+'"'+","+values[4]+","+'"'+values[5]+'"'+","+'"'+values[6]+'"'+","+values[7]+","+values[8]+","+values[9]+","+values[10]+","+values[11]+","+values[12]+","+values[13]+","+values[14]+","+values[15]+","+'"'+values[0]+'"'+")");
                }
                else if(values[14] == "NULL" && values[3] == "NULL")
                {
                    CsvOrder.executeUpdate("INSERT INTO CHARACTER_HAS_ITEMS (Character,	Item, Item_Type, WeaponType, Range, Price, Quantity, DefendScore, AttackScore, HealingScore, ManaScore, SingleUse, wearable, worn, BodyPart, Equipped, Character_Name) VALUES("+'"'+values[0]+'"'+","+'"'+values[1]+'"'+","+'"'+values[2]+'"'+","+values[3]+","+values[4]+","+'"'+values[5]+'"'+","+'"'+values[6]+'"'+","+values[7]+","+values[8]+","+values[9]+","+values[10]+","+values[11]+","+values[12]+","+values[13]+","+values[14]+","+values[15]+","+'"'+values[0]+'"'+")");
                }
                else
                {
                    CsvOrder.executeUpdate("INSERT INTO CHARACTER_HAS_ITEMS (Character,	Item, Item_Type, WeaponType, Range, Price, Quantity, DefendScore, AttackScore, HealingScore, ManaScore, SingleUse, wearable, worn, BodyPart, Equipped, Character_Name) VALUES("+'"'+values[0]+'"'+","+'"'+values[1]+'"'+","+'"'+values[2]+'"'+","+'"'+values[3]+'"'+","+values[4]+","+'"'+values[5]+'"'+","+'"'+values[6]+'"'+","+values[7]+","+values[8]+","+values[9]+","+values[10]+","+values[11]+","+values[12]+","+values[13]+","+'"'+values[14]+'"'+","+values[15]+","+'"'+values[0]+'"'+")");
                }
            }
            br.close(); 
            gr.close(); 
        }   
        catch (IOException|SQLException ex)   
        {  
            System.out.println(ex.getMessage());  
        } 
    }

    public void PopulateCombat(Connection x)
    {
        String line;   
        try   
        {   
            BufferedReader br = new BufferedReader(new FileReader("Combat.csv"));
            br.readLine(); 
            Statement CsvOrder = x.createStatement(); 
            while ((line = br.readLine()) != null)    
            {
                String[] values = line.split(",");
                CsvOrder.executeUpdate("INSERT INTO CHARACTER_HAS_COMBAT (BattleDate, BattleNo, Attacker, Defender, Weapon, Result, Damage, Character_Name) VALUES("+'"'+values[0]+'"'+","+'"'+values[1]+'"'+","+'"'+values[2]+'"'+","+'"'+values[3]+'"'+","+'"'+values[4]+'"'+","+'"'+values[5]+'"'+","+'"'+values[6]+'"'+","+'"'+values[2]+'"'+")"); 
            }
            br.close();  
        }   
        catch (IOException|SQLException ex)   
        {  
            System.out.println(ex.getMessage());  
        } 
    }

    public void PopulateBill(Connection x)
    {
        String line;   
        try   
        {   
            BufferedReader br = new BufferedReader(new FileReader("Customers.csv"));
            br.readLine();  
            Statement CsvOrder = x.createStatement(); 
            while ((line = br.readLine()) != null)    
            {  
                String[] values = line.split(",");
                for(int i = 0; i<18; i++)
                {
                    if(values[i] == "")
                    {
                        values[i] = "NULL";
                    } 
                }
                if(values[5] == "NULL")
                {
                    CsvOrder.executeUpdate("INSERT INTO CUSTOMER_HAS_BILL (Character_CreationDate, Character_Expiry_Date, Account_Number) VALUES("+'"'+values[4]+'"'+","+values[5]+","+'"'+values[0]+'"'+")");   
                }
                else
                {
                    CsvOrder.executeUpdate("INSERT INTO CUSTOMER_HAS_BILL (Character_CreationDate, Character_Expiry_Date, Account_Number) VALUES("+'"'+values[4]+'"'+","+'"'+values[5]+'"'+","+'"'+values[0]+'"'+")");
                }
            }
            br.close();  
        }   
        catch (IOException|SQLException ex)   
        {  
            System.out.println(ex.getMessage());  
        } 
    }

    public void MySelector(Connection x)
    {
        try
        {
            Statement SelectOrder = x.createStatement();
            ResultSet SelectAns;
            System.out.println("Query 1");
            SelectAns = SelectOrder.executeQuery("SELECT Attacker, COUNT(Result) AS Hit_Count FROM CHARACTER_HAS_COMBAT WHERE Result = \"Hit\" OR Result = \"Victory\" GROUP BY Attacker ORDER BY Result DESC LIMIT 5");
            while(SelectAns.next())
            {
                System.out.println(SelectAns.getString("Attacker") + " " + SelectAns.getString("Hit_Count"));
            }
            System.out.println("\nQuery 2");
            SelectAns = SelectOrder.executeQuery("SELECT Attacker, COUNT(Attacker) AS Total_Attack_Count FROM CHARACTER_HAS_COMBAT GROUP BY Attacker ORDER BY COUNT(Attacker)");
            while(SelectAns.next())
            {
                System.out.println(SelectAns.getString("Attacker") + " " + SelectAns.getString("Total_Attack_Count"));
            }
            System.out.println("\nQuery 3");
            SelectAns = SelectOrder.executeQuery("SELECT Attacker FROM CHARACTER_HAS_COMBAT GROUP BY Attacker ORDER BY COUNT(Attacker) DESC");
            while(SelectAns.next())
            {
                System.out.println(SelectAns.getString("Attacker"));
            }
            System.out.println("\nQuery 4");
            SelectAns = SelectOrder.executeQuery("SELECT forename, surname FROM CUSTOMER GROUP BY forename HAVING COUNT(Account_Number)>=5");
            while(SelectAns.next())
            {
                System.out.println(SelectAns.getString("forename") + " " + SelectAns.getString("surname"));//Noticed that forename and surname are incorrect in given csv files should be other way round, going to leave code as is
            }
            System.out.println("\nQuery 5");
            SelectAns = SelectOrder.executeQuery("SELECT Item FROM CHARACTER_HAS_ITEMS WHERE Item_Type = \"Weapon\" GROUP BY Item HAVING COUNT(Item)>=10");
            while(SelectAns.next())
            {
                System.out.println(SelectAns.getString("Item"));
            }
            System.out.println("\nTotal Victories Count");
            SelectAns = SelectOrder.executeQuery("SELECT Attacker, COUNT(Result) AS Victories FROM CHARACTER_HAS_COMBAT WHERE Result = \"Victory\" GROUP BY Attacker ORDER BY COUNT(Result) DESC");
            while(SelectAns.next())
            {
                System.out.println(SelectAns.getString("Attacker") + " " + SelectAns.getString("Victories"));
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args)
   {
        Connect myC = new Connect();
        Connect myX = new Connect();
        Connection X = myX.connect();
        Connection C = myC.connect();
        myC.createTable(C);
        myC.PopulateCust(X);
        myC.PopulateBill(X);
        myC.PopulateChar(X);
        myC.PopulateCombat(X);
        myC.PopulateItem(X);
        System.out.println("Tables populated successfully\n");
        myX.MySelector(X);
        try 
        {
	        if (C != null)
            {
                C.close();
            }
            if (X != null)
            {
                X.close();
            }
        } 
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }  
    }
}
