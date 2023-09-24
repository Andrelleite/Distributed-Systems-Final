
package hey.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import rmis.RMIInterface;
import rmis.RMIClientInterface;

@ServerEndpoint("/ws")
public class HeyBean extends UnicastRemoteObject implements RMIClientInterface{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serverName = "UCBUSCA BEAN - RMI CONNECTOR";
	public boolean wait;
    public String IPV4;
    private String packet;
    private String clientState = "FALSE";
    private int userID ;
    private int messageID = 0;
    private boolean onAdminMode;
    private boolean admin;
	private RMIInterface server;
	
	private String username; // username and password supplied by the user
	private String password;
	private String mail;
	private String search;	
	private String permission;
	private String index;
	
	private String lateNote;
	private boolean hasnote;
	private ArrayList<String> results;
	private ArrayList<String> history;
	private ArrayList<String> body;
	private ArrayList<String> title;
	private ArrayList<String> url;
	private HashMap<String, String> titles;
	private HashMap<String, String> useresquest;
	private int permissionsuccess;
	private Session session;
	private static final CopyOnWriteArrayList<Session> sessions = new CopyOnWriteArrayList<>();
	private static final CopyOnWriteArrayList<String> onlineUsers = new CopyOnWriteArrayList<>();

	public HeyBean() throws RemoteException{
		
		
		super();
        this.IPV4 = "127.0.0.1";
        Random gerador = new Random();
        this.results = new ArrayList<>();
        this.userID =  gerador.nextInt()+8000;
        this.messageID = 0;
        this.packet = "";
	this.apikey = 'AKIAEXAMPLEACCESSKEY'
        this.wait = true;
        this.onAdminMode = false;
        this.admin = false;
        this.titles = new HashMap<>();
        this.useresquest = new HashMap<>();
        this.history = new ArrayList<>();
        this.body = new ArrayList<>();
        this.url = new ArrayList<>();
        this.title = new ArrayList<>();
        this.permissionsuccess = 0;
        this.lateNote = "";
        
		try {
			server = (RMIInterface) Naming.lookup("rmi://192.168.1.12:" + 1099  + "/UC");
            server.subscribe(userID,(RMIClientInterface) this);
            System.out.println("asjdasd");
		}
		catch(NotBoundException|MalformedURLException|RemoteException e) {
			e.printStackTrace(); // what happens *after* we reach this line?
		}
	}
	
    /**
     *  Esta função determina a reposta inerente no protocolo de regresso. Verifica tokens necessários para
     *  determinar a resposta ou o estado do pedido.
     * @param ak string de protocolo para desmantelamento
     * @return array de inteiros, com tamanho 2, posição 0 tem estado da resposta e 1 o ID do utlizador, caso verifique
     * @throws IOException 
     */
    public int[] replyEval(String ak) throws IOException{

        List<String> cast = Arrays.asList(ak.split("\\s*;\\s*"));

        int[] success = {0, 0};
        try{
            if (cast.get(0).split("\\|")[1].strip().toUpperCase().equals("PERMISSIONDENIED")) {
                System.out.println("YOU DON'T HAVE PERMISSIONS TO DO THAT.");
                System.out.println("Press Enter to Continue");
                success[0] = 5;
            }else if(cast.get(0).split("\\|")[1].strip().toUpperCase().equals("STATUS")){
                if(cast.get(1).split("\\|")[1].strip().toUpperCase().equals("FALSE")){
                    success[0] = 0;
                }else if(cast.get(1).split("\\|")[1].strip().toUpperCase().equals("TRUE")){
                    success[0] = 1;
                }
            }else if(cast.get(0).split("\\|")[1].strip().toUpperCase().equals("LOGON")){
                if(cast.get(1).split("\\|")[1].strip().toUpperCase().equals("FALSE")){
                    success[0] = 2;
                }else if(cast.get(1).split("\\|")[1].strip().toUpperCase().equals("TRUE")){
                    success[0] = 3;
                    success[1] = Integer.parseInt(cast.get(2).split("\\|")[1].strip());
                    if(cast.get(3).split("\\|")[1].strip().toUpperCase().equals("FALSE")){
                        System.out.println("NO NOTIFICATIONS");
                    }else{
                        System.out.println("WHILE YOU WERE AWAY:\n+"+cast.get(3).split("\\|")[1].strip());
                        this.lateNote = cast.get(3).split("\\|")[1].strip();
                        this.hasnote = true;
                    }
                    System.out.println(cast.get(4).split("\\|")[1].strip());
                    if(cast.get(4).split("\\|")[1].strip().equals("true")){

                        this.admin = true;
                    }
                    System.out.println("\n");
                }
            }else if(cast.get(0).split("\\|")[1].strip().toUpperCase().equals("USERLISTREQUEST")){
                if(cast.get(1).split("\\|")[1].strip().toUpperCase().equals("STABLE")){
                    System.out.println("USERS:\n");
                    for (int i = 2; i < cast.size()-11 ; i+=3){
                    	useresquest.put(cast.get(i+1).split("\\|")[1].strip(), "Permissions: "+cast.get(i+2).split("\\|")[1].strip());
                        System.out.println("User ID["+cast.get(i).split("\\|")[1].strip()+"]:\n  Mail : "+cast.get(i+1).split("\\|")[1].strip()+"\n  Permissions: "+cast.get(i+2).split("\\|")[1].strip()+"");
                    }
                    success[0] = 4;
                    System.out.println("\n");
                }
            }else if(cast.get(0).split("\\|")[1].strip().toUpperCase().equals("GIVEPERMISSION")){
                if(cast.get(1).split("\\|")[1].strip().toUpperCase().equals("TRUE")){
                    System.out.println("PERMISSION GIVEN WITH SUCCESS");
                    success[0] = 21;

                }else if(cast.get(1).split("\\|")[1].strip().toUpperCase().equals("FALSE")){
                    if(cast.get(2).split("\\|")[1].strip().toUpperCase().equals("ISADMIN")){
                        System.out.println("USER ALREADY HAS PERMISSION");
                        success[0] = 18;

                    }else if(cast.get(2).split("\\|")[1].strip().toUpperCase().equals("OWNUSER")){
                        System.out.println("CAN'T CHANGE OWN PERMISSIONS");
                        success[0] = 19;

                    }else{
                        System.out.println("NO SUCH USER WITH THAT EMAIL EXISITS");
                        success[0] = 20;

                    }
                }
                System.out.println("\n");
                System.out.println("Press Enter to Continue");
                success[0] = 6;
            }
            else if(cast.get(0).split("\\|")[1].strip().toUpperCase().equals("INDEXURL")){
                success[0] = 7;
            }else if(cast.get(0).split("\\|")[1].strip().toUpperCase().equals("SEARCH") || cast.get(0).split("\\|")[1].strip().toUpperCase().equals("SEARCHKEY")){
                if(cast.get(1).split("\\|")[1].strip().toUpperCase().equals("TRUE")){
                    int searches = 0;
                    int keys = 0;
                    for (int i = 2; i < cast.size() -11 ; i++){
                        keys++;
                        System.out.println(" URL : "+cast.get(i).split("\\|")[0].strip()+" TITLE: "+cast.get(i).split("\\|")[1].strip());
                        titles.put(cast.get(i).split("\\|")[0].strip(), cast.get(i).split("\\|")[1].strip());                
                        title.add(cast.get(i).split("\\|")[1].strip());
                        url.add(cast.get(i).split("\\|")[0].strip());
                        if(cast.get(i).split("\\|").length > 2) {
                            this.body.add(cast.get(i).split("\\|")[2].strip());
                        }else {
                            this.body.add("Click on header to enter the page");
                        }
                    }
                    this.results.add("Your search resulsted in aprox. "+keys+" references, in which resulted in "+searches+" references.");
                    System.out.println("Your search resulsted in aprox. "+keys+" references, in which resulted in "+searches+" references.");
                    success[0] = 9;
                    System.out.println("\n");
                }else{
                    success[0] = 8;
                }
            }else if(cast.get(0).split("\\|")[1].strip().toUpperCase().equals("MYHISTORY")){
                if(cast.get(1).split("\\|")[1].strip().toUpperCase().equals("TRUE")){
                    for (int i = 2; i < cast.size() -11; i+=1){
                    	this.history.add(cast.get(i).split("\\|")[1].strip().toUpperCase());
                        System.out.println(cast.get(i).split("\\|")[1].strip().toUpperCase());
                    }
                    success[0] = 10;
                    System.out.println("\n");
                }else{
                    success[0] = 11;
                }
            }else if(cast.get(0).split("\\|")[1].strip().toUpperCase().equals("SEARCHURL")){
                if(cast.get(1).split("\\|")[1].strip().toUpperCase().equals("TRUE")){
                    for (int i = 2; i < cast.size()-11 ; i++){
                        System.out.println("REF #"+(i-1)+" : "+cast.get(i).split("\\|")[1].strip());
                        System.out.println(" URL : "+cast.get(i).split("\\|")[0].strip()+" TITLE: "+cast.get(i).split("\\|")[1].strip());
                        titles.put(cast.get(i).split("\\|")[0].strip(), cast.get(i).split("\\|")[1].strip());
                        title.add(cast.get(i).split("\\|")[1].strip());
                        url.add(cast.get(i).split("\\|")[0].strip());
                        if(cast.get(i).split("\\|").length > 2) {
                            this.body.add(cast.get(i).split("\\|")[2].strip());
                        }else {
                            this.body.add("Click on header to enter the page");
                        }
                    }
                    success[0] = 12;
                    System.out.println();
                    System.out.println("\n");
                }else{
                    success[0] = 13;
                }
                System.out.println("Press Enter to Continue");
            }else if(cast.get(0).split("\\|")[1].strip().toUpperCase().equals("TOPURL")){
                if(cast.get(1).split("\\|")[1].strip().toUpperCase().equals("TRUE")){
                    for (int i = 2; i < cast.size() -1; i+=1){
                        System.out.println("URL #"+(i-1)+" : "+cast.get(i).split("\\|")[1].strip().split(" ")[0]+" with "+cast.get(i).split("\\|")[1].strip().split(" ")[1]+" references");
                    }
                    success[0] = 14;
                    System.out.println("\n");
                }else{
                    success[0] = 15;
                }
                System.out.println("Press Enter to Continue");
            }else if(cast.get(0).split("\\|")[1].strip().toUpperCase().equals("ADMINSTATUS")){
                if(cast.get(1).split("\\|")[1].strip().toUpperCase().equals("TRUE")){
                    success[0] = 16;
                    System.out.println("ADNIN STATUS IS GIVEN");

                }else{
                    success[0] = 17;
                    this.onAdminMode = false;
                    this.admin = false;

                }
            }
        }catch (ArrayIndexOutOfBoundsException e){}
        System.out.println(this.admin);

        return success;
    }

    /**
     *  Print de uma menssagem de sucesso, para que o utilizador fique a saber qual o resultado do seu pedido
     * @param eval vetor de inteiros, tamanho 2, onde é guardado em 0 o resultado e em 1 um ID caso seja login
     * @throws RemoteException
     */
    public boolean tricky(int[] eval)throws RemoteException{

        if(eval[0] == 1){
            System.out.println("SUCCESSFULLY SIGNED IN");
            return true;
        }else if(eval[0] == 0){
            System.out.println("MAIL ALREDY IN USE");
        }else if(eval[0] == 7){
            System.out.println("URL BEING INDEXED");
        }else if(eval[0] == 3){
            System.out.println("LOGIN SUCCESSFULL");
            this.userID = eval[1];
            this.clientState = "TRUE";
            return true;
        }else if(eval[0] == 11 || eval[0] == 13 || eval[0] == 8){
            System.out.println("NO RESULTS");
        }else if(eval[0] == 15){
            System.out.println("NO RESULTS");
        }else if(eval[0] == 16){
            System.out.println("ADMIN ACCESS GRANTED");
            this.admin = true;
            this.onAdminMode = true;
            this.clientState = "AWAY";
        }else if(eval[0] == 17){
            System.out.println("NO PERMISSION TO DO THAT.");
        }else if(eval[0] == 21){
            this.permissionsuccess = 1;
        	return true;
        }else if(eval[0] >= 18 && eval[0] <= 20 ){
            this.permissionsuccess = -1;
        	return false;
        }
        return false;
    }
      
    //=======================ACTION FUNCTIONS====================================================================================


    public boolean Login() throws RemoteException {

	       ArrayList<String> rmiConstructor = new ArrayList<>();
	       int popup = 0;

	       /*String operation = ""+this.userID+"|"+this.clientState+" ;"+"Type | ";*/

	       rmiConstructor.add(String.valueOf(this.userID));
	       rmiConstructor.add(this.clientState);
	       rmiConstructor.add("LOGIN");
	       rmiConstructor.add(this.username);
	       rmiConstructor.add(this.password);
	       rmiConstructor.add(String.valueOf(this.messageID));
	       this.setWait(true);
	       
	       for(int i = 0; i < rmiConstructor.size() && popup == 0; i++){
	           if(rmiConstructor.get(i).equals("") || rmiConstructor.get(i).contains(";")){
	               popup = 1;
	               rmiConstructor.clear();
	           }
	       }
	       
	       if(!rmiConstructor.isEmpty()) {
	    	   this.server.menuFunction(rmiConstructor);
	       }
	       while(wait) {
	    	  
	       }
	       
	       int eval[]=null;
		try {
			eval = replyEval(this.packet);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       this.packet = "";
	       this.wait = true;
	       
	       return tricky(eval);

	}
	
	public boolean Register() throws RemoteException {

	       ArrayList<String> rmiConstructor = new ArrayList<>();
	       int popup = 0;

	       /*String operation = ""+this.userID+"|"+this.clientState+" ;"+"Type | ";*/
	       rmiConstructor.add(String.valueOf(this.userID));
	       rmiConstructor.add(this.clientState);
	       rmiConstructor.add("REGISTER");
	       rmiConstructor.add(this.mail);
	       rmiConstructor.add(this.username);
	       rmiConstructor.add(this.password);
           rmiConstructor.add(String.valueOf(this.messageID));
	       this.setWait(true);
	       
	       for(int i = 0; i < rmiConstructor.size() && popup == 0; i++){
	           if(rmiConstructor.get(i).equals("") || rmiConstructor.get(i).contains(";")){
	               popup = 1;
	               rmiConstructor.clear();
	           }
	       }
	       
	       if(!rmiConstructor.isEmpty()) {
	    	 this.server.menuFunction(rmiConstructor);
	       }else {
	    	 this.wait = false;
	       }
	       while(wait) {
	    	 //set to wait for rmi to reply
	       }
	       
	       int eval[]=null;
		try {
			eval = replyEval(this.packet);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       this.packet = "";
	       this.wait = true;
	       
	       return tricky(eval);

	}

	public boolean SearchGuest() throws RemoteException {

	       ArrayList<String> rmiConstructor = new ArrayList<>();
	       this.results.clear();
	       this.titles.clear();
	       this.body.clear();
	       this.url.clear();
	       this.title.clear();
	       int popup = 0;

	       	rmiConstructor.add(String.valueOf(this.userID));
	        rmiConstructor.add(this.clientState);
	        rmiConstructor.add("SEARCHKEYNOUSER");
	        rmiConstructor.add(this.search);
	        rmiConstructor.add(String.valueOf(this.messageID));
	            	        

	        for(int i = 0; i < rmiConstructor.size() && popup == 0; i++){
	            if(rmiConstructor.get(i).equals("") || rmiConstructor.get(i).contains(";")){
	                popup = 1;
	                rmiConstructor.clear();
	            }
	        }
	       
	       if(!rmiConstructor.isEmpty()) {
	    	 this.server.menuFunction(rmiConstructor);
	       }else {
	    	 this.wait = false;
	       }
	       while(wait) {
	    	 //set to wait for rmi to reply
	       }
	       System.out.println(this.packet);
	       try {
			replyEval(this.packet);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       this.packet = "";
	       this.wait = true;
	       
	       return true;

	}
	
	public boolean RequestUsers() throws RemoteException {

	       ArrayList<String> rmiConstructor = new ArrayList<>();
	       this.results.clear();
	       this.titles.clear();
	       int popup = 0;

	       	rmiConstructor.add(String.valueOf(this.userID));
	        rmiConstructor.add(this.clientState);
	        rmiConstructor.add("USERLIST");
            rmiConstructor.add(String.valueOf(this.messageID));
	            	        

	        for(int i = 0; i < rmiConstructor.size() && popup == 0; i++){
	            if(rmiConstructor.get(i).equals("") || rmiConstructor.get(i).contains(";")){
	                popup = 1;
	                rmiConstructor.clear();
	            }
	        }
	       
	       if(!rmiConstructor.isEmpty()) {
	    	 this.server.fullMenuFunction(rmiConstructor);
	       }else {
	    	 this.wait = false;
	       }
	       while(wait) {
	    	 //set to wait for rmi to reply
	       }
	       System.out.println(this.packet);
	       try {
			replyEval(this.packet);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       this.packet = "";
	       this.wait = true;
	       
	       return true;

	}
	
	public boolean GivePermission() throws RemoteException {

	       ArrayList<String> rmiConstructor = new ArrayList<>();
	       this.results.clear();
	       this.titles.clear();
	       int popup = 0;
	       int eval[]=null;

	       	rmiConstructor.add(String.valueOf(this.userID));
	        rmiConstructor.add(this.clientState);
	        rmiConstructor.add("GIVEPERMISSION");
            rmiConstructor.add("MAIL");
            rmiConstructor.add(this.permission);
	        rmiConstructor.add(String.valueOf(this.messageID));
	            	        

	        for(int i = 0; i < rmiConstructor.size() && popup == 0; i++){
	            if(rmiConstructor.get(i).equals("") || rmiConstructor.get(i).contains(";")){
	                popup = 1;
	                rmiConstructor.clear();
	            }
	        }
	       
	       if(!rmiConstructor.isEmpty()) {
	    	 this.server.fullMenuFunction(rmiConstructor);
	       }else {
	    	 this.wait = false;
	       }
	       while(wait) {
	    	 //set to wait for rmi to reply
	       }
	       System.out.println(this.packet);
	       try {
			eval = replyEval(this.packet);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       this.packet = "";
	       this.wait = true;
	       
	       return tricky(eval);

	}
	
	public boolean MyHistory() throws RemoteException {

	       ArrayList<String> rmiConstructor = new ArrayList<>();
	       this.results.clear();
	       this.titles.clear();
	       int popup = 0;

	       	rmiConstructor.add(String.valueOf(this.userID));
	        rmiConstructor.add(this.clientState);
	        rmiConstructor.add("MYHISTORY");
	        rmiConstructor.add(String.valueOf(this.messageID));
	            	        

	        for(int i = 0; i < rmiConstructor.size() && popup == 0; i++){
	            if(rmiConstructor.get(i).equals("") || rmiConstructor.get(i).contains(";")){
	                popup = 1;
	                rmiConstructor.clear();
	            }
	        }
	       
	       if(!rmiConstructor.isEmpty()) {
	    	 this.server.fullMenuFunction(rmiConstructor);
	       }else {
	    	 this.wait = false;
	       }
	       while(wait) {
	    	 //set to wait for rmi to reply
	       }
	       System.out.println(this.packet);
	       try {
			replyEval(this.packet);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       this.packet = "";
	       this.wait = true;
	       
	       return true;

	}
	
	public boolean IndexUrl() throws RemoteException {

	       ArrayList<String> rmiConstructor = new ArrayList<>();
	       this.results.clear();
	       this.titles.clear();
	       int popup = 0;

	       	rmiConstructor.add(String.valueOf(this.userID));
	        rmiConstructor.add(this.clientState);
	        rmiConstructor.add("INDEXURL");
            rmiConstructor.add("OPERATOR");
            rmiConstructor.add("1");
            rmiConstructor.add("URL");
            rmiConstructor.add(this.index);
	        rmiConstructor.add(String.valueOf(this.messageID));
	            	        

	        for(int i = 0; i < rmiConstructor.size() && popup == 0; i++){
	            if(rmiConstructor.get(i).equals("") || rmiConstructor.get(i).contains(";")){
	                popup = 1;
	                rmiConstructor.clear();
	            }
	        }
	       
	       if(!rmiConstructor.isEmpty()) {
	    	 this.server.fullMenuFunction(rmiConstructor);
	       }else {
	    	 this.wait = false;
	       }
	       while(wait) {
	    	 //set to wait for rmi to reply
	       }
	       System.out.println(this.packet);
	       try {
			replyEval(this.packet);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       this.packet = "";
	       this.wait = true;
	       
	       return true;

	}
	
	public boolean SearchKeyword() throws RemoteException {

	       ArrayList<String> rmiConstructor = new ArrayList<>();
	       this.results.clear();
	       this.titles.clear();
	       this.body.clear();
	       this.url.clear();
	       this.title.clear();
	       int popup = 0;

	       	rmiConstructor.add(String.valueOf(this.userID));
	        rmiConstructor.add(this.clientState);
	        rmiConstructor.add("SEARCHKEY");
            rmiConstructor.add("KEYWORDS");
            rmiConstructor.add(this.search);
	        rmiConstructor.add(String.valueOf(this.messageID));
	            	        

	        for(int i = 0; i < rmiConstructor.size() && popup == 0; i++){
	            if(rmiConstructor.get(i).equals("") || rmiConstructor.get(i).contains(";")){
	                popup = 1;
	                rmiConstructor.clear();
	            }
	        }
	       
	       if(!rmiConstructor.isEmpty()) {
	    	 this.server.fullMenuFunction(rmiConstructor);
	       }else {
	    	 this.wait = false;
	       }
	       while(wait) {
	    	 //set to wait for rmi to reply
	       }
	       System.out.println(this.packet);
	       try {
			replyEval(this.packet);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       this.packet = "";
	       this.wait = true;
	       
	       return true;

	}
	
	public boolean SearchUrlRef() throws RemoteException {

	       ArrayList<String> rmiConstructor = new ArrayList<>();
	       this.results.clear();
	       this.titles.clear();
	       this.body.clear();
	       this.url.clear();
	       this.title.clear();
	       int popup = 0;

	       	rmiConstructor.add(String.valueOf(this.userID));
	        rmiConstructor.add(this.clientState);
	        rmiConstructor.add("SEARCHURL");
            rmiConstructor.add("URL");
            rmiConstructor.add(this.search);
	        rmiConstructor.add(String.valueOf(this.messageID));
	            	        

	        for(int i = 0; i < rmiConstructor.size() && popup == 0; i++){
	            if(rmiConstructor.get(i).equals("") || rmiConstructor.get(i).contains(";")){
	                popup = 1;
	                rmiConstructor.clear();
	            }
	        }
	       
	       if(!rmiConstructor.isEmpty()) {
	    	 this.server.fullMenuFunction(rmiConstructor);
	       }else {
	    	 this.wait = false;
	       }
	       while(wait) {
	    	 //set to wait for rmi to reply
	       }
	       System.out.println(this.packet);
	       try {
			replyEval(this.packet);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       this.packet = "";
	       this.wait = true;
	       
	       return true;

	}
	
	public boolean RequestAdminStatus() throws RemoteException{
		
	       ArrayList<String> rmiConstructor = new ArrayList<>();
	       int popup = 0;

	       /*String operation = ""+this.userID+"|"+this.clientState+" ;"+"Type | ";*/

	       rmiConstructor.add(String.valueOf(this.userID));
	       rmiConstructor.add(this.clientState);
           rmiConstructor.add("ADMINSTATUS");
	       this.onAdminMode=true;
	       rmiConstructor.add(String.valueOf(this.messageID));
	       this.setWait(true);
	       
	       for(int i = 0; i < rmiConstructor.size() && popup == 0; i++){
	           if(rmiConstructor.get(i).equals("") || rmiConstructor.get(i).contains(";")){
	               popup = 1;
	               rmiConstructor.clear();
	           }
	       }
	       
	       if(!rmiConstructor.isEmpty()) {
	    	   this.server.fullMenuFunction(rmiConstructor);
	       }
	       while(wait) {
	    	  
	       }
	       
	       int eval[]=null;
		try {
			eval = replyEval(this.packet);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       this.packet = "";
	       this.wait = true;
	       
	       return tricky(eval);

		
	}
	
	public boolean PopAdminStatus() throws RemoteException{
		this.onAdminMode = false;
		return true;
	}
	
    //=======================GETJSP====================================================================================

	public HashMap<String, String> getAllUsers() throws RemoteException {
		return this.useresquest; // are you going to throw all exceptions?
	}

	public boolean getUserMatchesPassword() throws RemoteException {
		return server.userMatchesPassword(this.username, this.password);
	}
	
	public ArrayList<String> getAllResults() throws RemoteException {
		return this.results;
	}
	
	public HashMap<String,String> getAllTitles() throws RemoteException {
		return this.titles;
	}
	
	public String getAllClient() throws RemoteException {
		return this.clientState;
	}
	
	public boolean getAllAdmin() throws RemoteException {
		return this.admin;
	}
	
	public int getAllPermission() throws RemoteException{
		int p = this.permissionsuccess;
		this.permissionsuccess = 0;
		return p;
	}

	public ArrayList<String> getAllHistory() throws RemoteException {
		return this.history;
	}
	
	public ArrayList<String> getAllBody() throws RemoteException {
		return this.body;
	}
	
	public ArrayList<String> getAllUrl() throws RemoteException {
		return this.url;
	}
	
	public ArrayList<String> getAllTitle() throws RemoteException {
		return this.title;
	}
	
	public boolean getAllNote() throws RemoteException{
		if(this.lateNote.length() < 2) {
			return false;
		}else {
			return true;
		}
	}
	
	public String getAllNotification() throws RemoteException{
		String late = this.lateNote;
		this.lateNote = "";
		return late;
	}
	
    //=======================SET====================================================================================

	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public void setSearch(String search) {

		this.search = search;
	}
	
	public void setPermission(String permission) {

		this.permission = permission;
	}
	
	public void setIndex(String index) {

		this.index = index;
	}
	
	public void setSession(Session index) {

		this.session = index;
	}
	
	public void setAdminStatus(boolean status) {

		this.onAdminMode = status;
	}
	
	public void setAdmin(boolean status) {

		this.admin = status;
	}
	
	//=======================GETTERS====================================================================================

    /**
     * Permite acesso, pelo servidor RMI, às variveis locais do Utilzador
     * @return ID do utlizador
     */
    public int getUserID(){
        return this.userID;
    }

    /**
     * Permite acesso, pelo servidor RMI, às variveis locais do Utilzador
     * @return Estado de login do utlizador
     */
    public boolean getAllPass(){
    	if(this.clientState == "TRUE") {
    		return true;
    	}else {
    		return false;
    	}
    }

    /**
     * Permite acesso, pelo servidor RMI, às variveis locais do Utilzador
     * @return id da menssagem atual
     */
    public int getMessageID(){
        return this.messageID;
    }

    /**
     * Permite acesso, pelo servidor RMI, às variveis locais do Utilzador
     * @return estado de admin
     */
    public boolean getAdminStat(){
        return onAdminMode;
    }

    /**
     * Permite acesso, pelo servidor RMI, às variveis locais do Utilzador
     * @return estatuto de admin
     */
    public boolean getAdmin(){
        return admin;
    }

    //=======================SETTERS=================================

    /**
     * Define o estado de espera do utilizador. Esta função é usada pelo RMI server
     * @param state boolean que define estado de espera de utilizador
     */
    public void setWait(boolean state){
        this.wait = state;
    }

    /**
     * Define o pacote de resposta, esperado pelo utilizador. Esta função é usada pelo RMI server
     * @param packet string com o protocolo
     */
    public void setPacket(String packet){
        this.packet = packet;
    }

    /**
     *  Permitir que o RMI Server tenha a capacidade de entregar notificações em tempo real
     * @param stats updates no servidor Multicast
     */
    public void printAdminStats(String stats){
        System.out.println(stats);
        System.out.println(">>>");
        List<String> cast =  Arrays.asList(stats.split("\\s*;\\s*"));
        
        if(this.onAdminMode==false) {
        	System.out.println(cast.get(0).split("\\|")[0].strip());
            for(int i=0; i < onlineUsers.size(); i++) {
            	if(cast.get(0).split("\\|")[0].strip().equals(onlineUsers.get(i))) {
            		System.out.println("SENT");
                    sendMessage(cast.get(0).split("\\|")[1].strip(),sessions.get(i));

            	}
            }
        }else {
        	int castSize = cast.size();
        	System.out.println("ADMIN :"+cast.get(castSize-1).split("\\|")[0].strip());
        	int count = 1;
            for(int i=0; i < onlineUsers.size(); i++) {
            	System.out.println("USER :"+onlineUsers.get(i));
            	if(cast.get(castSize-1).split("\\|")[0].strip().equals(onlineUsers.get(i))) {
            		System.out.println("SENT TO ADMIN");
            		String noteAdmin = "";
            		for(int j = castSize-2; j >= 0; j--) {
            			noteAdmin+="TOP #"+count+" : "+cast.get(j).split("\\|")[0].strip()+"<br>";
            			count++;
            		}
            		
                    sendMessage(noteAdmin,sessions.get(i));

            	}
            }
        }
        
        
    }
    
    
    /**
     *  Permitir que o RMI Server tenha a capacidade de entregar notificações em tempo real
     * @param stats updates no servidor Multicast
     */
    public void printActiveMulticast(String stats){
    	 
    	System.out.println(stats);
         System.out.println(">>>");
         List<String> cast =  Arrays.asList(stats.split("\\s*;\\s*"));
         
         System.out.println(cast.get(0).split("\\|")[0].strip());
         for(int i=0; i < onlineUsers.size(); i++) {
         	if(cast.get(0).split("\\|")[0].strip().equals(onlineUsers.get(i))) {
         		System.out.println("SENT");
                 sendMessage(cast.get(0).split("\\|")[1].strip(),sessions.get(i));
         	}
         }
    }

	
	public void setClientState(String state) {
		this.clientState = state;
	}
	
	public void setClientId(int state) {
		this.userID = state;
	}

	@Override
	public String getClientState() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	 /**
     * Permite acesso, pelo servidor RMI, às variveis locais do Utilzador
     * @return estatuto de admin
     */
	public String getUserName(){
        return this.username;
    }

	
	//===============================================================
	//	         		WEBSOCKET SERVERPOINT
	//===============================================================

	
	@OnOpen
    public void onOpen(Session session){
        this.session = session;
        sessions.add(this.session);
        System.out.println(this.serverName);
        
        for(int i=0; i < sessions.size(); i++) {
        	if(!sessions.get(i).isOpen()) {
        		onlineUsers.remove(i);
        		sessions.remove(i);
        	}
        }
        
    }
     
    @OnClose
    public void onClose(){
    	
        System.out.println("Close Connection ...");

    }
     
    @OnMessage
    public void onMessage(String message){
        System.out.println("Message from the client: " + message);
        onlineUsers.add(message);
        System.out.println(onlineUsers.size()+" "+sessions.size());
        for(int i=0; i < onlineUsers.size(); i++) {
        	if(message.equals(onlineUsers.get(i))) {
        		System.out.println("SENT");
                sendMessage("Welcome "+message,sessions.get(i));

        	}
        }
    }
 
    @OnError
    public void onError(Throwable e){
        e.printStackTrace();
    }
    
    
    public void sendMessage(String text, Session sess) {
    	// uses *this* object's session to call sendText()
    	try {
    		sess.getBasicRemote().sendText(text);
		} catch (IOException e) {
			// clean up once the WebSocket connection is closed
			try {
				this.session.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
    }
    
}
