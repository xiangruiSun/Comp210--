package assn07;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String,String> passwordManager = new PasswordManager<>();

        // your code below
        System.out.println("Enter Master Password");
        String masterPassword = scanner.nextLine();

        // infinite loop to go back to "Enter master password"
        while(!passwordManager.checkMasterPassword(masterPassword)){
            System.out.println("Enter Master Password");
            masterPassword = scanner.nextLine();
        }

        // loop to read and execute commands until "Exit" is entered
        String command = scanner.nextLine();
        while(!command.equals("Exit")){
            if(command.equals("New password")){
                String wName = scanner.nextLine();
                String pName = scanner.nextLine();
                passwordManager.put(wName,pName);
                System.out.println("New password added");
            }

            if(command.equals("Get password")){
                String wName = scanner.nextLine();
                String pName = passwordManager.get(wName);
                if(pName==null){
                    System.out.println("Account does not exist");
                }
                else{
                    System.out.println(pName);
                }
            }

            if(command.equals("Delete account")){
                String wName = scanner.nextLine();
                String result = passwordManager.remove(wName);
                if(result==null){
                    System.out.println("Account does not exist");
                }
                else{
                    System.out.println("Account deleted");
                }
            }

            if(command.equals("Check duplicate password")){
                String pName = scanner.nextLine();
                List<String> list = passwordManager.checkDuplicate(pName);
                if (list.isEmpty()){
                    System.out.println("No account uses that password");
                }
                else{
                    System.out.println("Websites using that password:");
                    for(int i = 0;i < list.size();i++){
                        System.out.println(list.get(i));
                    }
                }
            }

            if(command.equals("Get accounts")){
                System.out.println("Your accounts:");
                Set<String> set = passwordManager.keySet();
                for(String element: set){
                    System.out.println(element);
                }
            }

            if(command.equals("Generate random password")){
                System.out.println("Generate random password");
                int length = scanner.nextInt();
                System.out.println(passwordManager.generatesafeRandomPassword(length));
            }

            if((!command.equals("Generate random password"))&&(!command.equals("Get accounts"))&&(!command.equals("Check duplicate password"))&&(!command.equals("Delete account"))&&(!command.equals("Get password"))&&(!command.equals("New password"))){
                System.out.println("Command not found");
            }
            command = scanner.nextLine();
        }
    }
}
