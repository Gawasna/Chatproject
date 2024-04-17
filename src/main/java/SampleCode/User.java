package SampleCode;
public class User {
    private String username;
    private AccountType accountType;
    //private ChatType chatType;
    
    public User(String username, AccountType accountType/*, ChatType chatType*/) {
        this.username = username;
        this.accountType = accountType;
        //this.chatType = chatType;
    }

    public String getUsername() {
        return username;
    }

    public AccountType getAccountType() {
        return accountType;
    }
    
    /*public ChatType getChatType () {
        return chatType;
    }
    */

    public enum AccountType {
        DATABASE_LOGIN,
        GUEST_LOGIN
    }
    
    /*public enum ChatType {
        GENERAL_CHAT,
        PRIVATE_CHAT
    }
    */
}