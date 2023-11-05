public class TestDatabase {
    public static void main(String[] args) {
        Database db = new Database();
        testAddNewEntryToDatabase(db);
    }

    static void testAddNewEntryToDatabase(Database db) {
        String newUser = "100000,something1@yahoo.com,665GVYHF,Customer";
        db.addToDatabase("users.csv", newUser);
        String newUserTwo = "100001,something2@yahoo.com,45354HVTFHT,Seller";
        db.addToDatabase("users.csv", newUserTwo);
    }
}
