/*
 * Programmer: Real Bert Magallanes
 * Email Address: realbertmagallanes@gmail.com
 */
package lwdp;

import java.sql.*;
import java.util.Calendar;
import javax.swing.*;

/**
 *
 * @author civic
 */
public class accountNoPattern {
    
    accountNoPattern(){};
    
    //RETURN THE CLASSIFICATION NAME
	public String getClassification(char i){
		String classification = "";
            switch (i) {
                case '1':
                    classification = "RESIDENTIAL";
                    break;
                case '2':
                    classification = "GOVERNMENT";
                    break;
                case '3':
                    classification = "COMMERCIAL";
                    break;
                case 'A':
                    classification = "COMMERCIAL A";
                    break;
                case 'B':
                    classification ="COMMERCIAL B";
                    break;
                case 'C':
                    classification = "COMMERCIAL C";
                    break;
                case '4':
                    classification = "BULK";
                    break;
                case '5':
                    classification = "RESIDENTIAL SEWER";
                    break;
                default:
                    classification = "COMM'L/IND'L SEWER";
                    break;
            }
		return classification;
	}
	
	//RETURN THE METER SIZE
	public String getMeterSize(char i){
		String meterSize = "";
            switch (i) {
                case '1':
                    meterSize = "3/8";
                    break;
                case '2':
                    meterSize = "1/2";
                    break;
                case '3':
                    meterSize = "3/4";
                    break;
                case '4':
                    meterSize = "1\"";
                    break;
                case '5':
                    meterSize = "1\"-1/4";
                    break;
                case '6':
                    meterSize = "1\"-1/2";
                    break;
                case '7':
                    meterSize = "2\"";
                    break;
                case '8':
                    meterSize = "2\"-1/2";
                    break;
                default:
                    meterSize = "3\"";
                    break;
            }
		return meterSize;
	}
	//RETURN THE CLASSIFICATION CODE NO.
	public String getClassificationValue(String i){
		String c = "";
            switch (i){
                case "RESIDENTIAL":
			c = "1";
                        break;
                case "GOVERNMENT":
			c = "2";
                        break;
                case "COMMERCIAL":
			c = "3";
                        break;
                case "COMMERCIAL A":
			c = "3A";
                        break;
                case "COMMERCIAL B":
			c = "3B";
                        break;
                case "COMMERCIAL C":
			c = "3C";
                        break;
                case "BULK":
			c = "4";
                        break;
                case "RESIDENTIAL SEWER":
			c = "5";
                        break;
                default:
			c = "6";
                        break;
            }
		return c;
	}
	
	//RETURN THE METER SIZE CODE NO.
	public int getMeterSizeValue(String i){
		int meterSize = 0;
            switch (i) {
                case "3/8":
                    meterSize = 1;
                    break;
                case "1/2":
                    meterSize = 2;
                    break;
                case "3/4":
                    meterSize = 3;
                    break;
                case "1\"":
                    meterSize = 4;
                    break;
                case "1\"-1/4":
                    meterSize = 5;
                    break;
                case "1\"-1/2":
                    meterSize = 6;
                    break;
                case "2\"":
                    meterSize = 7;
                    break;
                case "2\"-1/2":
                    meterSize = 8;
                    break;
                default:
                    meterSize = 9;
                    break;
            }
		return meterSize;
	}
        
        public String getConcessionairesNumber(String zone,String Classification,int size){
            mySQLconnect mc = new mySQLconnect();
            String number = "";
        
            String sql = "SELECT COUNT(*) AS `Number` FROM `concessionaires_tbl` WHERE acn LIKE '"+zone+"1-"+Classification+""+size+"-%'";
            try {
                Statement statement = mc.connect().createStatement();
                ResultSet rs = statement.executeQuery(sql);
                
                if(rs.next()){
                    int n = Integer.parseInt(rs.getString("Number"));
                    int add = n + 1;
                    if(add < 10){
                        number = "00"+add;
                    }else if(add < 100){
                        number = "0"+add;
                    }else{
                        number = ""+add;
                    }
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(new JPanel(), "Database read error", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog(new JPanel(), "Check the server if ON", "Server Offline", JOptionPane.ERROR_MESSAGE);
            } finally {
                mc.disconnect();
            }
            return number;
        }
        
        public boolean hasMeter(String acn){
            queries mc = new queries();
            boolean isNotError = false;
            mc.InsertUpdatequery("UPDATE `concessionaires_tbl` SET `hasMeter`= '1' WHERE acn ='"+acn+"'");
            if(mc.isQueryError() == true){
                isNotError = false;
            }else{
                isNotError  = true;
            }
            
            
            return isNotError;
        }
        
        public boolean insertConcessionaires(String acn,String name,String gd,String md){
            queries mc = new queries();
            boolean isNotError = false;
            //System.out.println(acn+" "+name+" "+gd+" "+md);
            mc.InsertUpdatequery("INSERT INTO `concessionaires_tbl`(`acn`, `name`, `guaranteeDeposit`, `hasMeter`, `meterDeposit`) VALUES ('"+acn+"','"+name+"','"+gd+"','0','"+md+"')");
            if(mc.isQueryError() == true){
                isNotError = false;
            }else{
                isNotError = true;
            }
            
            return isNotError;
        }
        
        public boolean connectionStatus(String mn,int s){
            queries mc = new queries();
            boolean isNotError = false;
            mc.InsertUpdatequery("UPDATE `meter_tbl` SET `connection_status`= '"+s+"' WHERE acn ='"+mn+"'");
            if(mc.isQueryError() == true){
                isNotError = false;
            }else{
                isNotError = true;
            }
            
            return isNotError;
        }
        
        public void insertWaterMeter(String mn,String a,String c,String acn){
            queries mc = new queries();
            
            
            mc.InsertUpdatequery("INSERT INTO `meter_tbl`(`meterNum`, `address`, `connection_status`, `acn`) VALUES ('"+mn+"','"+a+"','"+c+"','"+acn+"')");
            
        }
        
        public boolean updateMeterDetails(String mn,String address,String CStatus,String acn){
            queries mc = new queries();
            boolean isNotError = false;
            mc.InsertUpdatequery("UPDATE `meter_tbl` SET `meterNum`='"+mn+"',`address`='"+address+"',`connection_status`='"+CStatus+"'" +
                            "WHERE `acn` = '"+acn+"'");
            
            if(mc.isQueryError() == true){
                isNotError = false;
            }else{
                isNotError = true;
            }
            
            return isNotError;
        }

        public boolean updateConcessionairesDetails(String acn,String name,String guarantee,String meter){
            queries mc = new queries();
            boolean isNotError = false;
            mc.InsertUpdatequery("UPDATE `concessionaires_tbl` SET `name` = '"+name+"',`guaranteeDeposit` = '"+guarantee+"',`meterDeposit` = '"+meter+"'"
                    + "WHERE acn = '"+acn+"'");
            isNotError = mc.isQueryError() != true;
            
            return isNotError;
        }
        
        //1/2 size
	protected double getOneHalfMeteredSales(String cl,int cubic){
		double mc = 0;
		if(cl.equalsIgnoreCase("Residential")){
			
				if(cubic < 11){
					mc = 210;
				}else if(cubic < 21){
					mc = ((cubic - 10) * 22.2) + 210;
				}else if(cubic < 31){
					mc = ((cubic - 20) * 24.8)+ 432;
				}else if(cubic < 41){
					mc = ((cubic - 30) * 27.45) + 680;
				}else{
					mc = ((cubic - 40) * 30.15) + 954.50;
				}
		}else if(cl.equalsIgnoreCase("Government")){		
			
				if(cubic < 11){
					mc = 210;
				}else if(cubic < 21){
					mc = (cubic - 10) * 22.2 + 210;
				}else if(cubic < 31){
					mc = (cubic - 20) * 24.8 + 432;
				}else if(cubic < 41){
					mc = (cubic - 30) * 27.45 + 680;
				}else{
					mc = (cubic - 40) * 30.15 + 954.50;
				}
		}else if(cl.equalsIgnoreCase("Commercial")){
			
				double a = 0;
				double b = 0;
				double c = 0;
				if(cubic < 11){
					mc = 420;
				}else if(cubic < 21){
					a = cubic - 10;
					b = 22.2 * 2;
					c = a * b;
					mc = c + 420;
				}else if(cubic < 31){
					a = cubic - 20;
					b = 24.8 * 2;
					c = a * b;
					mc = c + 864;
				}else if(cubic < 41){
					a = cubic - 30;
					b = 27.45 * 2;
					c = a * b;
					mc = c + 1360;
				}else{
					a = cubic - 40;
					b = 30.15 * 2;
					c = a * b;
					mc = c  + 1959;
				}
		}else if(cl.equalsIgnoreCase("Commercial A")){
			
				if(cubic < 11){
					mc = 367.5;
				}else if(cubic < 21){
					mc = ((cubic - 10) * (22.2 * 1.75) ) + 367.5;
				}else if(cubic < 31){
					mc = ((cubic - 20) * (24.8 * 1.75) )+ 756;
				}else if(cubic < 41){
					mc = ((cubic - 30) * (27.45 * 1.75) ) + 1190;
				}else{
					mc = ((cubic - 40) * (30.15 * 1.75) ) + 1670;
				}
		}else if(cl.equalsIgnoreCase("Commercial B")){
			
				if(cubic < 11){
					mc = 315;
				}else if(cubic < 21){
					mc = ((cubic - 10) * (22.2 * 1.50) ) + 315;
				}else if(cubic < 31){
					mc = ((cubic - 20) * (24.8 * 1.50) )+ 648;
				}else if(cubic < 41){
					mc = ((cubic - 30) * (27.45 * 1.50) ) + 1020;
				}else{
					mc = ((cubic - 40) * (30.15 * 1.50) ) + 1431.5;
				}
		}else if(cl.equalsIgnoreCase("Commercial C")){
			
				if(cubic < 11){
					mc = 262.5;
				}else if(cubic < 21){
					mc = ((cubic - 10) * (22.2 * 1.25) ) + 262.5;
				}else if(cubic < 31){
					mc = ((cubic - 20) * (24.8 * 1.25) )+ 540;
				}else if(cubic < 41){
					mc = ((cubic - 30) * (27.45 * 1.25) ) + 850;
				}else{
					mc = ((cubic - 40) * (30.15 * 1.25) ) + 1193;
				}
		}else{
				if(cubic < 11){
					mc = 630;
				}else if(cubic < 21){
					mc = ((cubic - 10) * (22.2 * 3) ) + 630;
				}else if(cubic < 31){
					mc = ((cubic - 20) * (24.8 * 3) )+ 1296;
				}else if(cubic < 41){
					mc = ((cubic - 30) * (27.45 * 3) ) + 2040;
				}else{
					mc = ((cubic - 40) * (30.15 * 3) ) + 2863.5;
				}
				
		}
		return mc;
	}
	
	//3/4 size
	protected double getThreeFourthMeteredSales(String cl,int cubic){
		double mc = 0;
		if(cl.equalsIgnoreCase("Residential")){
			
				if(cubic < 11){
					mc = 336;
				}else if(cubic < 21){
					mc = ((cubic - 10) * 22.2) + 336;
				}else if(cubic < 31){
					mc = ((cubic - 20) * 24.8)+ 558;
				}else if(cubic < 41){
					mc = ((cubic - 30) * 27.45) + 806;
				}else{
					mc = ((cubic - 40) * 30.15) + 1080.5;
				}
		}else if(cl.equalsIgnoreCase("Government")){		
			
				if(cubic < 11){
					mc = 336;
				}else if(cubic < 21){
					mc = ((cubic - 10) * 22.2) + 336;
				}else if(cubic < 31){
					mc = ((cubic - 20) * 24.8)+ 558;
				}else if(cubic < 41){
					mc = ((cubic - 30) * 27.45) + 806;
				}else{
					mc = ((cubic - 40) * 30.15) + 1080.5;
				}
		}else if(cl.equalsIgnoreCase("Commercial")){
			
				double a = 0;
				double b = 0;
				double c = 0;
				if(cubic < 11){
					mc = 672;
				}else if(cubic < 21){
					a = cubic - 10;
					b = 22.2 * 2;
					c = a * b;
					mc = c + 672;
				}else if(cubic < 31){
					a = cubic - 20;
					b = 24.8 * 2;
					c = a * b;
					mc = c + 1116;
				}else if(cubic < 41){
					a = cubic - 30;
					b = 27.45 * 2;
					c = a * b;
					mc = c + 1612;
				}else{
					a = cubic - 40;
					b = 30.15 * 2;
					c = a * b;
					mc = c  + 2211;
				}
		}else if(cl.equalsIgnoreCase("Commercial A")){
			
				if(cubic < 11){
					mc = 588;
				}else if(cubic < 21){
					mc = ((cubic - 10) * (22.2 * 1.75) ) + 588;
				}else if(cubic < 31){
					mc = ((cubic - 20) * (24.8 * 1.75) )+ 976.5;
				}else if(cubic < 41){
					mc = ((cubic - 30) * (27.45 * 1.75) ) + 1410.5;
				}else{
					mc = ((cubic - 40) * (30.15 * 1.75) ) + 1890.5;
				}
		}else if(cl.equalsIgnoreCase("Commercial B")){
			
				if(cubic < 11){
					mc = 504;
				}else if(cubic < 21){
					mc = ((cubic - 10) * (22.2 * 1.50) ) + 504;
				}else if(cubic < 31){
					mc = ((cubic - 20) * (24.8 * 1.50) )+ 837;
				}else if(cubic < 41){
					mc = ((cubic - 30) * (27.45 * 1.50) ) + 1209;
				}else{
					mc = ((cubic - 40) * (30.15 * 1.50) ) + 1620.5;
				}
		}else if(cl.equalsIgnoreCase("Commercial C")){
			
				if(cubic < 11){
					mc = 420;
				}else if(cubic < 21){
					mc = ((cubic - 10) * (22.2 * 1.25) ) + 420;
				}else if(cubic < 31){
					mc = ((cubic - 20) * (24.8 * 1.25) )+ 697.5;
				}else if(cubic < 41){
					mc = ((cubic - 30) * (27.45 * 1.25) ) + 1007.5;
				}else{
					mc = ((cubic - 40) * (30.15 * 1.25) ) + 1350.5;
				}
		}else{
				if(cubic < 11){
					mc = 1008;
				}else if(cubic < 21){
					mc = ((cubic - 10) * (22.2 * 3) ) + 1008;
				}else if(cubic < 31){
					mc = ((cubic - 20) * (24.8 * 3) )+ 1674;
				}else if(cubic < 41){
					mc = ((cubic - 30) * (27.45 * 3) ) + 2418;
				}else{
					mc = ((cubic - 40) * (30.15 * 3) ) + 3241.5;
				}
				
		}
		return mc;
	}
	
	//1"
	protected double getOneInchMeteredSales(String cl,int cubic){
		double mc = 0;
		if(cl.equalsIgnoreCase("Residential")){
			
				if(cubic < 11){
					mc = 672;
				}else if(cubic < 21){
					mc = ((cubic - 10) * 22.2) + 672;
				}else if(cubic < 31){
					mc = ((cubic - 20) * 24.8)+ 894;
				}else if(cubic < 41){
					mc = ((cubic - 30) * 27.45) + 1142;
				}else{
					mc = ((cubic - 40) * 30.15) + 1416.5;
				}
		}else if(cl.equalsIgnoreCase("Government")){		
			
				if(cubic < 11){
					mc = 672;
				}else if(cubic < 21){
					mc = ((cubic - 10) * 22.2) + 672;
				}else if(cubic < 31){
					mc = ((cubic - 20) * 24.8)+ 894;
				}else if(cubic < 41){
					mc = ((cubic - 30) * 27.45) + 1142;
				}else{
					mc = ((cubic - 40) * 30.15) + 1416.5;
				}
		}else if(cl.equalsIgnoreCase("Commercial")){
			
				double a = 0;
				double b = 0;
				double c = 0;
				if(cubic < 11){
					mc = 1344;
				}else if(cubic < 21){
					a = cubic - 10;
					b = 22.2 * 2;
					c = a * b;
					mc = c + 1344;
				}else if(cubic < 31){
					a = cubic - 20;
					b = 24.8 * 2;
					c = a * b;
					mc = c + 1788;
				}else if(cubic < 41){
					a = cubic - 30;
					b = 27.45 * 2;
					c = a * b;
					mc = c + 2284;
				}else{
					a = cubic - 40;
					b = 30.15 * 2;
					c = a * b;
					mc = c  + 2883;
				}
		}else if(cl.equalsIgnoreCase("Commercial A")){
			
				if(cubic < 11){
					mc = 1176;
				}else if(cubic < 21){
					mc = ((cubic - 10) * (22.2 * 1.75) ) + 1176;
				}else if(cubic < 31){
					mc = ((cubic - 20) * (24.8 * 1.75) )+ 1564.5;
				}else if(cubic < 41){
					mc = ((cubic - 30) * (27.45 * 1.75) ) + 1998.5;
				}else{
					mc = ((cubic - 40) * (30.15 * 1.75) ) + 2478.5;
				}
		}else if(cl.equalsIgnoreCase("Commercial B")){
			
				if(cubic < 11){
					mc = 1008;
				}else if(cubic < 21){
					mc = ((cubic - 10) * (22.2 * 1.50) ) + 1008;
				}else if(cubic < 31){
					mc = ((cubic - 20) * (24.8 * 1.50) )+ 1341;
				}else if(cubic < 41){
					mc = ((cubic - 30) * (27.45 * 1.50) ) + 1713;
				}else{
					mc = ((cubic - 40) * (30.15 * 1.50) ) + 2124.5;
				}
		}else if(cl.equalsIgnoreCase("Commercial C")){
			
				if(cubic < 11){
					mc = 840;
				}else if(cubic < 21){
					mc = ((cubic - 10) * (22.2 * 1.25) ) + 840;
				}else if(cubic < 31){
					mc = ((cubic - 20) * (24.8 * 1.25) )+ 1117.5;
				}else if(cubic < 41){
					mc = ((cubic - 30) * (27.45 * 1.25) ) + 1427.50;
				}else{
					mc = ((cubic - 40) * (30.15 * 1.25) ) + 1770.5;
				}
		}else{
				if(cubic < 11){
					mc = 2016;
				}else if(cubic < 21){
					mc = ((cubic - 10) * (22.2 * 3) ) + 2016;
				}else if(cubic < 31){
					mc = ((cubic - 20) * (24.8 * 3) )+ 2682;
				}else if(cubic < 41){
					mc = ((cubic - 30) * (27.45 * 3) ) + 3426;
				}else{
					mc = ((cubic - 40) * (30.15 * 3) ) + 4249.5;
				}
				
		}
		return mc;
	}

            //set Reading Count minimun 1 and max is 2 because next is disconnection
        protected void update_meterReadingCount(String mn,int r){
            queries mc = new queries();
            
            mc.InsertUpdatequery("UPDATE `meter_tbl` SET `reading_count`="+r+" WHERE meterNum = '"+mn+"'");
            
        }
        
        protected int getReadingCOunt(String mn){
            mySQLconnect mc = new mySQLconnect();
        
            int r = 0;
        
        
            String sql = "SELECT COUNT(*) as 'rc' FROM `reading_tbl` WHERE meterNum = "+mn+"";
            try {
                Statement statement = mc.connect().createStatement();
                ResultSet rs = statement.executeQuery(sql);

                if(rs.next())
                {        
                    r = rs.getInt(1);
                }


            } catch (SQLException e) {
                JOptionPane.showMessageDialog(new JPanel(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println(e.getMessage());
            } finally {
                mc.disconnect();
            }
            return r;
        }
        
        protected int getOtherPaymentIDCount(){
            mySQLconnect mc = new mySQLconnect();
        
            int r = 0;
        
        
            String sql = "SELECT COUNT(*) as 'rc' FROM `payable_tbl`";
            try {
                Statement statement = mc.connect().createStatement();
                ResultSet rs = statement.executeQuery(sql);

                if(rs.next())
                {        
                    r = rs.getInt(1);
                }


            } catch (SQLException e) {
                JOptionPane.showMessageDialog(new JPanel(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println(e.getMessage());
            } finally {
                mc.disconnect();
            }
            return r+1;
        }
        
        protected int getReadingID(){
            mySQLconnect mc = new mySQLconnect();
        
            int r = 0;
        
        
            String sql = "SELECT COUNT(*) as 'rc' FROM `reading_tbl`";
            try {
                Statement statement = mc.connect().createStatement();
                ResultSet rs = statement.executeQuery(sql);

                if(rs.next())
                {        
                    r = rs.getInt(1);
                }


            } catch (SQLException e) {
                JOptionPane.showMessageDialog(new JPanel(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println(e.getMessage());
            } finally {
                mc.disconnect();
            }
            return r+1;
        }
        
        protected int getORNumber(){
            mySQLconnect mc = new mySQLconnect();
        
            int r = 0;
        
        
            String sql = "SELECT COUNT(*) as 'rc' FROM `payment_tbl`";
            try {
                Statement statement = mc.connect().createStatement();
                ResultSet rs = statement.executeQuery(sql);

                if(rs.next())
                {        
                    r = rs.getInt(1);
                }


            } catch (SQLException e) {
                JOptionPane.showMessageDialog(new JPanel(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println(e.getMessage());
            } finally {
                mc.disconnect();
            }
            return r+1;
        }
        
        protected int getBillingID(){
            mySQLconnect mc = new mySQLconnect();
        
            int r = 0;
        
        
            String sql = "SELECT COUNT(*) as 'rc' FROM `billing_tbl`";
            try {
                Statement statement = mc.connect().createStatement();
                ResultSet rs = statement.executeQuery(sql);

                if(rs.next())
                {        
                    r = rs.getInt(1);
                }


            } catch (SQLException e) {
                JOptionPane.showMessageDialog(new JPanel(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println(e.getMessage());
            } finally {
                mc.disconnect();
            }
            return r+1;
        }
        protected void insertMeterReading(int id,String f,String t,String prev,String pres,String cu,String ms,String penalty,String mn){
            queries mc = new queries();
            String[] monthName = {"January", "February",
            "March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December"};

            Calendar cal = Calendar.getInstance();
            String monthYear = monthName[cal.get(Calendar.MONTH)]+" "+cal.get(Calendar.YEAR);
            
            mc.InsertUpdatequery("INSERT INTO `reading_tbl`(`ID`, `from`, `to`, `previous`, `present`, `cubicMeterConsumed`, `meteredSales`, `penalty`, `meterNum`) VALUES ("+id+",'"+f+"','"+t+"','"+prev+"','"+pres+"','"+cu+"','"+ms+"','"+penalty+"','"+mn+"')");
            if(mc.isQueryError() == false){
                JOptionPane.showMessageDialog(new JPanel(), "READING AS OF "+monthYear+"\n\nFrom: "+f+" To: "+t+"\n\nPresent: "+pres+"\nPreveious: "+prev+"\nCu. m Consumed: "+cu+"\nWater Sales: "+ms+"\t Penalty: "+penalty+"\n\nMeter No: "+mn, "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }

        
        protected boolean insertBill(int bID,char uID,String mm,String pID,String rID){
            queries mc = new queries();
            boolean f = false;
            String ID;
            if(bID < 10){
                ID = "0000"+bID;
            }else if(bID < 100){
                ID = "000"+bID;
            }else if(bID < 1000){
                ID = "00"+bID;
            }else if(bID < 10000){
                ID = "0"+bID;
            }else{
                ID = ""+bID;
            }
            mc.InsertUpdatequery("INSERT INTO `billing_tbl`(`ID`, `status`, `meterMaintenance`, `userID`, `readingID`, `isPayed`) VALUES ('"+ID+"','"+uID+"','"+mm+"','"+pID+"','"+rID+"',0)");
            f = mc.isQueryError() != true;
            
            return f;
        }

        protected void savePayments(String OR,String bID,String uID,String insFee,String materials,String reconFee,String serFee,String transFee,String acn,String date_payed){
            queries mc = new queries();
           
            
            mc.InsertUpdatequery("INSERT INTO `payment_tbl`(ORnum,billID,userID,installationFee,materials,reconnectionFee,serviceFee,transferFee,acn,date_payed) VALUES('"+OR+"','"+bID+"','"+uID+"','"+insFee+"','"+materials+"','"+reconFee+"','"+serFee+"','"+transFee+"','"+acn+"','"+date_payed+"')");
            if(mc.isQueryError() != true){
                setPayed(bID);
            }else{
                JOptionPane.showMessageDialog(new JPanel(),"Error","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        
        private void setPayed(String bID){
            queries mc = new queries();
           
            mc.InsertUpdatequery("UPDATE `billing_tbl` SET isPayed='1' WHERE ID='"+bID+"'");

        }
        
}
