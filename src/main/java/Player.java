public class Player {
    public String login = null;
    public String password = "xxx";
    public int rank = 0;

    public Player(String login) {
        this.login = login;
    }

    public Player(String login, String password, int rank) {
        this.login = login;
        this.password = password;
        this.rank = rank;
    }
}